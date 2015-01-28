
 
 function inicioInformeProcesoSeco(){
        
       var generarPDFDevolucionProcSeco = function(){
           var codigoProceso = campo.obtener("codigoProceso");
           var url="/SCV_Portlet/GenerarPdfServlet?opcion=3&codigoProceso="+codigoProceso;
           window.open(url,"_blank");
       };
       
      /*Cargar checklist*/

      
      
      cargarTemplateVerificacionInfoCaracterizacion();
      generarGrillaArchivosSubidos();
      
            
      /*Eventosr*/
      $("#guardarInfoProcesoSeco").on("click",enviarFormProcesoSeco);         
      $("#laboratorioProcesoSeco").on("change",obtenerInformacionLabProceso);         
      $("#consultorProcesoSeco").on("change",obtenerInformacionConProceso);         
      $("#cargarArchivosProceso").on("click", abrirModalCargaArchivoPcsoSeco);
      $("#tipoDevolProcesoSeco").on("change", mostrarInfoDevolProcesoSeco);
      $("#verArchProceso").on("click", abrirModalDescargarArchivoPcsoSeco);  
      $("#linkDevolucionPdf").on("click",generarPDFDevolucionProcSeco);
      
      $("#fechaEntregaProcesoSeco, #fechaRadicacionProcesoSeco, #fechaDevolProcesoSeco, #fechaEntDevolProcesoSeco").kendoDatePicker({
         format: "dd/MM/yyyy"
      });
     
     /*Cargar Archivos*/
      $("#filesProcesoSeco").kendoUpload({
                async: {
                    saveUrl: servlet.cargaArchivos.subir+"?opcion=3&codigo="+campo.obtener("codigoProceso"),
                    removeUrl: servlet.cargaArchivos.eliminarAnexosInformes,
                    autoUpload: true
                }
      });
      
      
     urlLab = servlet.consultar.laboratorios;
     var xhrLab = campo.lista.crear("laboratorioProcesoSeco",urlLab,{opcion:1});
    
     urlConsl = servlet.consultar.consultores;
     var xhrConsul = campo.lista.crear("consultorProcesoSeco",urlConsl, {opcion:1});
    
      //Cuando las listas esten cargadas
      $.when(xhrLab,xhrConsul).done(function(){
         mostrarInfoProcesoSeco();

      });
      validacionesKendo();
        //Inicializa el validador
	$("#div_contendorInformacionProceso").kendoValidator({
            rules: {
              
                    /*Evita la validación incorrecta del formato de la fecha*/
                    date: function (input) {
                        var attr = input.attr("data-role");
                        if( attr == "datepicker" && input.val() != ''){ 
                            var fecha = kendo.parseDate(input.val(), "dd/MM/yyyy");
                            return fecha instanceof Date;
                        }
                        return true;
                    },
                    lesserdate: function(input){
                        
                         if (input.is("[data-lesserdate-field]") && input.val() != "") {                                    
                                    var date = input.data("kendoDatePicker").value();
                                    var otherDate = $("[name='" + input.data("lesserdateField") + "']").data("kendoDatePicker").value();
                                    return otherDate == null || otherDate.getTime() > date.getTime();
                                }

                                return true;
                        
                    },
                    greaterdate: function (input) {
                                if (input.is("[data-greaterdate-field]") && input.val() != "") {                                    
                                    var date = input.data("kendoDatePicker").value();
                                    var otherDate = $("[name='" + input.data("greaterdateField") + "']").data("kendoDatePicker").value();
                                    return otherDate == null || otherDate.getTime() < date.getTime();
                                }

                                return true;
                   }
            },
            messages:{
                 required:"Ingrese un valor para este campo",
                 date: "Fecha ingresada no valida",
                 greaterdate:"La fecha de entrega debe ser Mayor que la fecha de radicación ",
                 lesserdate: "La fecha de radicación debe ser menor que la fecha de entrega"
            }
        });
}


var enviarFormProcesoSeco = function(){
     
    var validador = $("#div_contendorInformacionProceso").data("kendoValidator");
    

    if (validador.validate()) {
            guardarInformeProcesoSeco();

    }
    
};

var mostrarLinkPdfDev = function(){
    var fechaDevolucioProcSeco = campo.obtener("fechaDevolProcesoSeco");
    if(fechaDevolucioProcSeco !== null && fechaDevolucioProcSeco !==""){
        document.getElementById("linkDevolucionPdf").style.display = 'block';
    }
}
      
var mostrarInfoDevolProcesoSeco = function(){

        if(this.value != ''){
            $("#contenedorInfoDevolucionProcesoSeco").slideDown("500");
        }else{
            $("#contenedorInfoDevolucionProcesoSeco").slideUp("500");
        }
    
};

var abrirModalCargaArchivoPcsoSeco = function(){
  
   var kendoModalBox =  modalBox.abrir("contenedorCargaArchivosProcesoSeco",null,"500px","Cargar Archivos", onCloseModalCargaArchivosPs);  
       kendoModalBox.open();
};


var abrirModalDescargarArchivoPcsoSeco = function(){
  
   var kendoModalBox =  modalBox.abrir("contenedorArchivosSubidos",null,"650px","Archivos Cargados");  
       kendoModalBox.open();
};


var onCloseModalCargaArchivosPs = function(){
    
  generarGrillaArchivosSubidos();
};

var obtenerInformacionConProceso = function(){
         
    var urlConsl = servlet.consultar.consultores;
    if(this.value != '' && this.value != undefined){  
             var xhr = $.post(urlConsl,{opcion:2, codigo:this.value});

             xhr.done(function(response,status,xhr){
                 $("#contenedorInfoBasicaCon").slideDown('500',function(){
                          $("#infoConTel").html(response.telefono1);
                          $("#infoConDir").html(response.direccion);

                 });
             });
             
      }else{
             $("#contenedorInfoBasicaCon").slideUp('500');
          
      }
    
};

var obtenerInformacionLabProceso = function(){
    
    var  urlLab = servlet.consultar.laboratorios;
    
    if(this.value != '' && this.value != undefined){
            var xhr = $.post(urlLab,{opcion:2, codigo:this.value});
    
            xhr.done(function(response,status,xhr){
                    
                $("#contenedorInfoBasicaLab").slideDown('500',function(){
                     $("#infoLabTel").html(response.telefono1);
                    $("#infoLabDir").html(response.direccion);
                 
                });
            });
      }else{
             $("#contenedorInfoBasicaLab").slideUp('500');
          
      }
    
};

var obtenerParametrosProcesoSeco = function(){
    
    var parametros = {};
    
    parametros = {       

            codigoProceso : campo.obtener("codigoProceso"),
            laboratorioProcesoSeco : campo.obtener("laboratorioProcesoSeco"),
            consultorProcesoSeco : campo.obtener("consultorProcesoSeco"),
            fechaEntregaProcesoSeco: campo.obtener("fechaEntregaProcesoSeco"),
            fechaRadicacionProcesoSeco: campo.obtener("fechaRadicacionProcesoSeco"),
            tipoDevolProcesoSeco: campo.obtener("tipoDevolProcesoSeco"),
            fechaEntDevolProcesoSeco : campo.obtener("fechaEntDevolProcesoSeco"),
            fechaDevolProcesoSeco : campo.obtener("fechaDevolProcesoSeco"),
            observacionDevolProsesoSeco : campo.obtener("observacionDevolProsesoSeco"),
            observacionesProcesoSeco: campo.obtener("observacionesProcesoSeco")
    };
    
    return parametros;
    
};

var obtenerParametrosVerificacionProcesoSeco = function(){
     
    var arreglo = [] ; 
    $(".containerRequisito").each(function(index,obj){
            var $obj = $(this);
            var $codigo = $obj.find("input[type='hidden']");
            var $checkeo = $obj.find("input[type='checkbox']");
            var respCheckeo ="NO";
            var rqsto = {};
              
            rqsto.codigo = $codigo.val();
            if( $checkeo.attr("checked") == "checked"){
                respCheckeo = "SI";
            }
            rqsto.checkeado = respCheckeo;
            arreglo.push(rqsto);

     });
       
    return arreglo;  
};


var mostrarInfoProcesoSeco = function(){
    var url = servlet.consultar.informacionProcesoSeco;
    var parametros = {
               codigoProceso : campo.obtener("codigoProceso"),
               opcion: 1
    };
    
    if(campo.obtener("codigoProceso") != ''){
        
          var xhrMostrarInfo = $.ajax({
                       type:"POST",
                       data: parametros,
                       url: url,
                       cache :false
                   });
                   
           xhrMostrarInfo.done(function(response,status,xhr){
               if(Object.keys(response).length > 0){
                   
                        campo.asignar("fechaEntregaProcesoSeco", response.fechaEntregaProcesoSeco);                
                        campo.asignar("fechaRadicacionProcesoSeco", response.fechaRadicacionProcesoSeco);
                        campo.asignar("laboratorioProcesoSeco", response.laboratorioProcesoSeco);
                        campo.asignar("consultorProcesoSeco", response.consultorProcesoSeco);
                        /*Devoluvion*/
                        campo.asignar("tipoDevolProcesoSeco",response.tipoDevolProcesoSeco); 
                        campo.asignar("fechaEntDevolProcesoSeco",response.fechaEntDevolProcesoSeco);
                        campo.asignar("fechaDevolProcesoSeco",response.fechaDevolProcesoSeco);    
                        campo.asignar("observacionDevolProsesoSeco",response.observacionDevolProsesoSeco);
                        campo.asignar("observacionesProcesoSeco",response.observacionesProcesoSeco);
                        if(response.tipoDevolProcesoSeco != '' && response.tipoDevolProcesoSeco != null){ //si hay un tipo de devolución deberá mostrar los demás campos
                           $("#contenedorInfoDevolucionProcesoSeco").show();
                       }
                       mostrarLinkPdfDev();
               }
                  
               
           });
           
            
   }
   
};

var guardarInformeProcesoSeco = function(){
    
    var xhrRequisito;
    var xhrInfoProceso;
    
    xhrRequisito = guardarRegistroVerificacionProcesoSeco();
    xhrInfoProceso = guardarRegistroInfoGeneralProcesoSeco();
    
    //se verifica que ambos este almacenados existosamente
    $.when(xhrRequisito, xhrInfoProceso).done(function(fn1,fn2){
          
         if(fn1[1] == 'success' && fn2[1] == 'success'){
              var res = fn2[0].resultado ;
               switch(res ){

                case 1 :alert("El registro ha sido almacenado exitosamente.");
                          elmto.habilitar('guardarInfoProcesoSeco');
                          mostrarLinkPdfDev();
                     break;
                 case 2 : alert("El tipo de informe no es correcto");
                     break;
                 case 0  : alert("Error no es posible almacernar la infomación.");
               }
            
          }
    });
    
    $.when(xhrRequisito, xhrInfoProceso).fail(function(requisitos, infoGeneral){
        
            alert("Hubo un problema en el momento de almacenar la información. Intente nuevamente.");
            elmto.habilitar('guardarInfoProcesoSeco');                                
    });
    
};

var guardarRegistroInfoGeneralProcesoSeco = function(){
    
    var url = servlet.insertar.informeProcesoSeco;
    var parametros = obtenerParametrosProcesoSeco();
    
    if(parametros != null && parametros != undefined){
        
         
        elmto.deshabilitar('guardarInfoProcesoSeco');
        var xhr = $.ajax({
                       type:"POST",
                       data: parametros,
                       url: url,
                       cache :false
                   });

        
    }
    
    return xhr;
};


 var guardarRegistroVerificacionProcesoSeco = function(){

            var url = servlet.insertar.verificarRequisitos;
            var parametros = {};
                parametros.respuestas = $.toJSON(obtenerParametrosVerificacionProcesoSeco());
                parametros.codigoProceso = campo.obtener("codigoProceso");
               
               if(parametros.codigoProceso != ''){
                    
                     elmto.deshabilitar('guardarInfoProcesoSeco');
                     var xhr = $.ajax({
                                    type:"POST",
                                    data: parametros,
                                    url: url,
                                    cache :false
                                });                      
              } 
              
              return xhr;

};

function DescargarArchivoProcesoSeco(codigo){
    var codigoProceso = campo.obtener("codigoProceso");
    var codigo = codigo;
    var url = servlet.cargaArchivos.descargar;
        url += "?opcion=2&codigoArchivo="+codigo+"&codigoProceso="+codigoProceso;
    window.open(url, "_blank"); 
    
};

function eliminarAnexoProcesoSeco(codigo){
    
    var url = servlet.cargaArchivos.eliminarAnexosInformes;
    var xhrEliminar;
    var parametros = {
                       codigo : codigo,
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

var generarGrillaArchivosSubidos = function (){

    var url = servlet.consultar.archivosInformesCargados; 
    
    var parametros = { 
            opcion:1,
            codigoProceso: campo.obtener("codigoProceso")           
      };
    
    
    var dataSource = new kendo.data.DataSource(
                        
                        {                             
                            transport: {
                                    read: {
                                        url:url,
                                        data:parametros, 
                                        dataType: "json"
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

    $("#grillaArchivosSubido").kendoGrid({
                                          
                        autoSync: true,
                       
                        dataSource: dataSource,                        
                        columns:
                         [                            
                            //Columnas de la Grilla
                                                     
                            { field:"nombreArchivo", title:"Archivo", width:"80px" },
                                                    
                            { command:  [//Botones de la ultima columna
                                          { 
                                             name:"download",
                                             className: "DescargaProcesoSeco",
                                             text:"Descargar",
                                             click: function(e){
                                                 
                                                  var tr = $(e.target).closest("tr"); // Se obtine todo el tr que se va a editar          
                                                  var data = this.dataItem(tr); 
                                                  
                                                  var codigo = data.id;                                                 
                                                  DescargarArchivoProcesoSeco(codigo);

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
                                eliminarAnexoProcesoSeco(codigo);                           

                        }
                                
     });

};