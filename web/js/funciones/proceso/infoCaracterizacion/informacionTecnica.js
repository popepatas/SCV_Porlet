function inicioInformacionTecnicaCarat(){
    
    cargarTemplateInfoTecnicaCaracterizacion();
    
    
    
    
    //Eventos    
     $(document).on("blur","[name='valorInforme']",validarCumplimientoNorma); 
     $("#contenedorEvalPuntosVertimiento").on("blur",".DBO, .SST, .caudalJornada, .jordanadaProductivaHoras, .jordanadaProductivaDia",calcularCargasPuntosVertimientos);
     $("#preTratamiento").on("change",{div:"contenedorCualPreTratamiento"},mostrarCamposCual);     
     $("#generacionLodos").on("change",{div:"contenedorCualGeneracionLodos"},mostrarCamposCual);
     $("#guardarInfoTecCaract").on("click", guardarInfoTecnica);
     $("#cualGeneracionLodos").on("change",mostrarOtroTipoLodos);
     $("#verArchInfoTec").on("click", abrirModalDescargarArchivoInfoTec);
     $("#cargarArchivosInfoTec").on("click", abrirModalCargaArchivoInfoTec); 
     $("#pdfIncumplimiento").on("click",generarPDFIncumplimiento);
     $("#linkLodos").on("click",cargarManejoDeLodos);
     
    //Uploader    
    $("#filesInfoTec").kendoUpload({
        async: {
            saveUrl: servlet.cargaArchivos.subir+"?opcion=2&codigo="+campo.obtener("codigoProceso"),
            removeUrl: servlet.cargaArchivos.eliminarAnexosLodos,
            autoUpload: true
        }
    });
    
    $("#contenedorRegistroInformePuntos").on("keypress",".campoEntero",function(event){
           return campo.numerico(event);
     });
    
     $("#contenedorRegistroInformePuntos").on("keypress",".campoDecimal",function(event){
           var valor;
           
           if(event.which == 8){
                     return true;
            }
           
            if (event.which){
                 
                 valor = this.value + String.fromCharCode(event.which);
                 valor = valor != '0.' ? valor : valor + '1' ;
                 
                 return campo.decimal(valor);

            }else if(event.keyCode){
                 valor =  this.value + String.fromCharCode(event.which);
                 valor = valor != '0.' ? valor : valor + '1' ;
                 
                 return campo.decimal(valor);
            }
           
     });
     
     
}

var mostrarBotonGenerarPdf = function(){
  var url = servlet.consultar.contarParamIncumplidos;
  var parametros = { codigoProceso:campo.obtener("codigoProceso")}
    $.post(url, parametros, function( data ) {
        if(data.cantidad > 0){
            elmto.mostrar("pdfIncumplimiento");
        }else{
            elmto.ocultar("pdfIncumplimiento");
        }
    });
};

var generarPDFIncumplimiento = function(){
    var codigoProceso = campo.obtener("codigoProceso");
    var url="/SCV_Portlet/GenerarPdfServlet?opcion=2&codigoProceso="+codigoProceso;
    window.open(url,"_blank");   
};


var abrirModalCargaArchivoInfoTec = function(){

   var kendoModalBox =  modalBox.abrir("contenedorCargaArchivosInfoTec",null,"500px","Cargar Archivos", onCloseModalCargaArchivosPs);  
       kendoModalBox.open();
};
var abrirModalDescargarArchivoInfoTec = function(){
  
   var kendoModalBox =  modalBox.abrir("contenedorArchivosSubidosInfoTec",null,"650px","Archivos Cargados");  
       kendoModalBox.open();
       generarGrillaArchivosSubidosInfoTec();
};
   var mostrarOtroTipoLodos = function(){
     
        if(this.value == 3){
           $("#contenedorCualGeneracionLodosOtros").slideDown();
           
        }else{
           $("#contenedorCualGeneracionLodosOtros").slideUp(); 
        }
       
   };
   
function guardarInfoTecnica(){
    
       enviarLodosInfoTec();
       var url = servlet.insertar.informacionTecnica;    
       var puntos = obtenerParametrosPuntosVertimientos();                 
       var parametros = { puntos: $.toJSON(puntos)};
       var xhrGuardar = $.ajax(
                 {
                     type:"POST",
                     data: parametros,
                     url: url,
                     cache :false
                 }
              );
        
       var xhrTasa  = guardarTasaRetributiva();
      
       $.when(xhrGuardar,xhrTasa).done(function(){
                var error,codigo;
                mostrarBotonGenerarPdf();
                var texto = "El registro ha sido almacenado exitosamente.";
                alert(texto);
       });
       
      
       
      $.when(xhrGuardar,xhrTasa).fail(function(data,status,xhr){
             
                var texto = "Error al almacenar el registro";
                 alert(texto);
       });
       
       
}

/*Se valida que se cumpla la norma segun el rango establecido para el parametro*/
var validarCumplimientoNorma = function(){
    
    var $this = $(this);
    var valor = parseFloat(this.value);
    //Se obtiene el padre
    var $padre = $this.parents(".valoresParmaInfo");
    //Se obtinen el div donde estan los rangos del parametro
    var $rangos = $padre.prev().prev(); 
    //Se selecciona el campo no se indica si cumple o no
    var $cumple = $padre.next().next();
    var inicial = parseFloat($rangos.find(".rangoIncialParam").val());
    var final = parseFloat( $rangos.find(".rangoFinalParam").val());
    
    if(valor != '' && valor != undefined && !isNaN(valor)){
        
            if( valor < inicial || valor> final){
                $cumple.find("input").val("NO");
            }else{
                $cumple.find("input").val("SI");
            }
            
    }else if(!isNaN(valor)){ 
        $cumple.find("input").val("SIN DATOS");
    }
    
};

var mostrarCamposCual = function (event){
    
    var div = event.data.div;
    var $div = $("#"+div);
    
     if(this.value == "SI"){
         $div.slideDown(500);
     }else{
           $div.slideUp(500);
     }    
    
};

var obtenerParametrosPuntosVertimientos = function(){
    
        puntos = new Array();


    var parametros = {};
     /*Se recorren todos los puntos de vertimiento*/
    $(".contenedorPunto").each(function(){

                var thisPunto = this;               
                var jornadas = new Array();
                var punto = {};
                
                var $thisPunto = $(thisPunto);
                var $ContentInfoBasica = $thisPunto.find(".contendorinformacionBasica");
                var $Jornadas = $thisPunto.find(".contendorJornadas .Jornadas");

        /*Obtiene la información basica del Punto de vertimiento*/
        $ContentInfoBasica.find("input, textarea").each(function(index, obj) {
              var $this= $(this) ;
              var nombreInfoBasica = $this.attr("name") ;
              /*Se añade cada parametro a objecto punto*/
              if( nombreInfoBasica != "" && nombreInfoBasica != undefined){
                JsonDinamico(punto, $this.attr("name") ,$this.val());
              }

        });


        /*Buscamos la jornadas que tiene el Punto de vertimiento*/
         $Jornadas.each(function(index, obj) {


                        var tabla = new Array;
                        var parametros = {};
                        var campoJornada = {};
                        var $thisJornada= $(this) ;


                    /*Buscamos la información basica de la jornada*/
                    $thisJornada.find(".contenedorInfoJornada input").each(function(index,obj){
                            var $this= $(this) ;
                            var nombreCampo = $this.attr("name");
                            /*se añade cada parametro a un objecto campoJornada*/
                            if( nombreCampo != "" && nombreCampo != undefined ){
                                  JsonDinamico(campoJornada, $this.attr("name") ,$this.val());
                            }

                    });
                    /*Buscamos todos las columnas donde estan los parametros*/
                    $thisJornada.find("table tbody tr").each(function(index, tr){

                                var $thisTr = $(this);
                                var parametros = {};

                                if(index >= 0){

                                     /*Buscamos los inputs que contienen las respuestas */
                                        $thisTr.find("input, select").each(function(index, obj) {    
                                               
                                               var nombreParam =  $(obj).attr("name");
                                               
                                             /*Se añaden los parametros a un objeto Parametro*/
                                            if( nombreParam != "" && nombreParam != undefined){
                                              JsonDinamico(parametros, $(obj).attr("name") ,$(obj).val());
                                            }

                                        });
                                     /*Se insertan todos los paramteros de una fila dentro de un array tabla*/
                                     tabla.push(parametros);
                                }

                        });
                        /*Para cada jornada se crea una asociacion tabla donde se almacenan los valores de las filas de la tabla*/
                        campoJornada.tabla = tabla;
                        
                        /*Se añade toda la información corresponidente a una jordana dentro de un array Jornadas*/
                        jornadas.push(campoJornada);

            });

            
             punto.jornadas = jornadas;

             puntos.push(punto);
        });
        
        return puntos;
};


var calcularCargasPuntosVertimientos = function(){
    
    puntos = new Array();
    var totalCargasSST = 0;
    var totalCargasDBO = 0;

    var parametros = {};
     /*Se recorren todos los puntos de vertimiento*/
    $(".contenedorPunto").each(function(){

                var thisPunto = this;  
                var $thisPunto = $(thisPunto);
                var $ContentInfoBasica = $thisPunto.find(".contendorinformacionBasica");
                var $Jornadas = $thisPunto.find(".contendorJornadas .Jornadas");
                
                var diasMes = 0;
                var horasDia = 0;
                var caudalPromedio = 0;
                var totalPuntoCargasSST = 0;
                var totalPuntoCargasDBO = 0;
                var contJornada = 0;
                
                /*Obtenemos los objectos jQuery */
                var $diasMes = $ContentInfoBasica.find("[name='jordanadaProductivaDia']");
                var $horasDia = $ContentInfoBasica.find("[name='jordanadaProductivaHoras']");
                
                          
                 if( $diasMes.val() != '' ){
                     diasMes = $diasMes.val();
                     $diasMes.removeClass("cmpVacioCarterizacion");
                 }else{
                     diasMes = 0;
                     $diasMes.addClass("cmpVacioCarterizacion");
                 }
                  
                 if( $horasDia.val() != '' ){
                     horasDia = $horasDia.val();
                     $horasDia.removeClass("cmpVacioCarterizacion");
                 }else{
                     horasDia = 0;
                     $horasDia.addClass("cmpVacioCarterizacion");
                 }
            
       
        /*Buscamos la jornadas que tiene el Punto de vertimiento*/
         $Jornadas.each(function(index, obj) {


                  
                    var $thisJornada= $(this) ;
                    var $cargaDBO;
                    var $cargaSST;
                    var $caudalJornada;
                    var cargaDBO =0;
                    var cargaSST =0;
                    var valorDBO = 0;
                    var valorSST = 0;
                    var hora = $thisJornada.find("[name='horaInicio']").val();
                    /*if( hora != ''){
                       contJornada ++;
                    }*/


                    /*Buscamos la información basica de la jornada*/
                    $cargaDBO = $thisJornada.find(".contenedorInfoJornada .cargaDBO");
                    $cargaSST = $thisJornada.find(".contenedorInfoJornada .cargaSST");
                    $caudalJornada = $thisJornada.find(".contenedorInfoJornada .caudalJornada");
                     
                    
                    var $inputDBO  = $thisJornada.find(".DBO");
                    var $inputSST  = $thisJornada.find(".SST");
                    
                    if($caudalJornada.val() != ''){
                        caudalPromedio = $caudalJornada.val();
                         contJornada ++;
                        
                    }else{
                        caudalPromedio = 0;
                    }

                    /*Se añaden los parametros a un objeto Parametro*/
                     if(  $inputDBO.val() != ''){

                         valorDBO = $inputDBO.val();                         
                         $inputDBO.removeClass("cmpVacioCarterizacion");

                     }else if($caudalJornada.val() != ''){
                         $inputDBO.addClass("cmpVacioCarterizacion");
                     }
                     
                     if(  $inputSST.val() != ''){

                         valorSST = $inputSST.val();                         
                         $inputSST.removeClass("cmpVacioCarterizacion");

                     }else if($caudalJornada.val() != ''){
                         $inputSST.addClass("cmpVacioCarterizacion");
                     }
                     
                   
                /*Se calculan las cargas de cada jornada de un mismo punto*/
                cargaDBO = parseFloat(valorDBO) * parseFloat(caudalPromedio) * 0.0864 *(parseFloat(horasDia)/24)*(parseFloat(diasMes));
                cargaSST = parseFloat(valorSST) * parseFloat(caudalPromedio) * 0.0864 *(parseFloat(horasDia)/24)*(parseFloat(diasMes));
                
             
                $cargaDBO.val(cargaDBO.toFixed(5));
                $cargaSST.val(cargaSST.toFixed(5));
                
                /*Se van sumando las cargas de las jornadas del punto*/    
                totalPuntoCargasDBO +=  cargaDBO;
                totalPuntoCargasSST +=  cargaSST;
            });
            
            /*Se calculan las cargas finales de todas las Jornadas*/ 
            totalPuntoCargasSST = totalPuntoCargasSST/contJornada;
            totalPuntoCargasDBO = totalPuntoCargasDBO/contJornada;
            
            if(isNaN(totalPuntoCargasSST))
            {
                totalPuntoCargasSST=0;
            }
             if(isNaN(totalPuntoCargasDBO))
            {
                totalPuntoCargasDBO=0;
            }
            /*Se suman las cargas totales de todos los puntos*/       
            totalCargasSST += totalPuntoCargasSST;
            totalCargasDBO += totalPuntoCargasDBO;
            
        });
        
        campo.asignar("CargaTotaDBO",totalCargasDBO.toFixed(5));
        campo.asignar("CargaTotalSST",totalCargasSST.toFixed(5));
        
};

var cargarManejoDeLodos = function(fecha){
     var url = "ManejarLodos.jsp";
     var titulo = "Manejar Lodos";
       $("#modalBox2").load(url,function(response,status,xhr){
           
           xhr.done(function(){
                inicioManejoLodos();
                var kendoModalBox = modalBox.abrir("modalBox2",null,"700px",titulo, null);                                
                kendoModalBox.open();
           });
       });
   };
  
   var enviarLodosInfoTec = function(){
       var parametros = { 
           diasAlMes : campo.obtener("diasAlMes"),
           horasAlDia : campo.obtener("horasAlDia"),
           preTratamiento : campo.obtener("preTratamiento"),
           cualPreTratamiento : campo.obtener("cualPreTratamiento"),
           generacionLodos : campo.obtener("generacionLodos"),
           cualGeneracionLodos : campo.obtener("cualGeneracionLodos"),
           codigoProceso : campo.obtener("codigoProceso"),
           cualOtroGeneracionLodos : campo.obtener("cualGeneracionLodosOtros")
       };
       
       var url = servlet.insertar.insertarLodos;
       
       var xhrGuardar = $.ajax(
                     {
                         type:"POST",
                         data: parametros,
                         url: url,
                         cache :false
                     }
                  );
   }
   
   
   function eliminarAnexoInfoTec(codigo){
    
    var url = servlet.cargaArchivos.eliminarAnexosLodos;
    var xhrEliminar;
    var parametros = {
                       codigoArchivo : codigo,
                       codigoProceso : campo.obtener("codigoProceso")
                     };
    //Te trae el datatype
    var grid = $("#grillaArchivosSubido").data("kendoGrid");
    
     if(confirm("¿Esta seguro que desea eliminar este registro?")){   
         
           xhrEliminar = $.ajax({
                                type:"POST",
                                data: parametros,
                                url: url,
                                cache :false
                            });
            
            xhrEliminar.done(function(data){
                        
                        if(data.error ==  0){
                            grid.cancelChanges();  
                            alert("No se pudo eliminar el registro.");
                        }

             });
     }
};
   
   var generarGrillaArchivosSubidosInfoTec = function (){

    var url = servlet.consultar.archivosLodosInfoTec; 
    
    var parametros = { 
            codigoProceso: campo.obtener("codigoProceso")           
      };
    
    
    var dataSource = new kendo.data.DataSource(
                        
                        {                             
                            transport: {
                                    read: {
                                        url:url,
                                        data:parametros, 
                                        dataType: "json",
                                        cache:false
                                    }
                                },
                                batch: true,
                                pageSize: 100,
                                serverPaging: true,
                                schema: {
                                        model: {
                                          id: "codigo",
                                          fields: {
                                                nombreArchivo: { editable: false }                                                                                   
                                            }
                                        }
                                }
                                
                       }
            
            );

    $("#grillaArchivosSubidoInfoTec").kendoGrid({
                                          
                        autoSync: true,
                       
                        dataSource: dataSource,                        
                        columns:
                         [                            
                            //Columnas de la Grilla
                                                     
                            { field:"nombreArchivo", title:"Archivo", width:"80px" },
                                                    
                            { command:  [//Botones de la ultima columna
                                          {  
                                             text:"Descargar",
                                             click: function(e){
                                                 
                                                  var tr = $(e.target).closest("tr"); // Se obtine todo el tr que se va a editar          
                                                  var data = this.dataItem(tr); 
                                                  
                                                  var codigo = data.id;                                                 
                                                  descargarArchivoInfoTec(codigo);

                                             }
                                          },
                                          { 
                                            name:"destroy", 
                                            text:"Eliminar"                                           
                                          }                                          
                                        ],
                              title: "", 
                              width: "150px" 
                            }
                        ],
                       pageable: {
                           refresh: true
                       },                        
                       editable:{
                          
                          confirmation: false
                        },
                        //Funcion eliminar
                        remove: function(e){
                            
                                var codigo = e.model.id; 
                                eliminarAnexoInfoTec(codigo);                           

                        }
                                
     });

};
function descargarArchivoInfoTec(codigo){
    
    var codigo = codigo;
    var url = servlet.cargaArchivos.descargar;
        url += "?opcion=5&codigoArchivo="+codigo;
    window.open(url, "_blank"); 
    generarGrillaArchivosSubidosInfoTec();
    
};
