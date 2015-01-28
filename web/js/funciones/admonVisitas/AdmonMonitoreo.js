
$(document).on("ready",inicioConsultarMonitoreoAdmon);

function inicioConsultarMonitoreoAdmon()
{
    
    
    /*DatePicker*/
    $("#fechaFinalMonitoreo").kendoDatePicker({
         format:"dd/MM/yyyy"
    });
    
    
    $("#grillaMonitoreo").on("click","#guardarSupervision", enviarSupervision);
    
    /*DatePicker*/
    $("#fechaInicialMonitoreo").kendoDatePicker({
         format:"dd/MM/yyyy"
    });
    
    $("#bntProgramarMonitoreo").hide();//.on("click", cargarProgramarMonitoreo);
    $("#contenedorCodigoProcesoMonitoreo,#contenedorContratoMonitoreo,#contenedorNitMonitoreo,#contenedorRazonSocialMonitoreo,#contenedorComunaMonitoreo,#contenedorDireccionMonitoreo,#contenedorEstadoMonitoreoAdmon").show();
    
    $("#consultarMonitoreo").click(seleccionarGrillaMonitoreoAdmon);
    $("#limpiarMonitoreo").click(limpiarFormularioAdmonMonitoreo);
   
   
   function cargarProgramarMonitoreo(parametrosMonitoreo,opcion){
       
         var parametrosMonitoreo = parametrosMonitoreo || undefined;
         
        $("#modalBox").load("../proceso/ProgramarMonitoreo.jsp",function(response, status, xhr){
                
                xhr.done(function(){
                        //Pasamos la función mostrar y los parametros para que sean procesados
                        inicioProgramacionMonitoreo(mostrarRegistroMonitoreo, parametrosMonitoreo,opcion);
                      
                        
                });
        });
   };
        
 };    
 var  limpiarFormularioAdmonMonitoreo = function(){
     
    
    campo.limpiar("fechaInicialMonitoreo");
    campo.limpiar("fechaFinalMonitoreo");    
    campo.limpiar("codigoProcesoMonitoreo");
    campo.limpiar("contratoMonitoreo");    
    campo.limpiar("nitMonitoreo");
    campo.limpiar("razonSocialMonitoreo");    
    campo.limpiar("comunaMonitoreo");
    campo.limpiar("direccionMonitoreo");
    campo.limpiar("estadoMonitoreoAdmon");
};
 
 var  mostrarRegistroMonitoreo = function(parametrosMonitoreo){
                     
            var url = servlet.consultar.monitoreos;
            var titulo = "Monitoreo";
             var xhrMonitoreo;
             
             if(parametrosMonitoreo != undefined){
                     
                 xhrMonitoreo = $.post(url, parametrosMonitoreo);
                 
                 xhrMonitoreo.done(function(data) {
                     
                         campo.asignar("fechaMonitoreo",data[0].fechaMonitoreo.substring(0,11));
                         campo.asignar("laboratorioMonitoreo", data[0].laboratorio);
                         campo.asignar("consultorMonitoreo", data[0].consultor);
                         campo.asignar("horaInicioMonitoreo", data[0].horaInicial);
                         campo.asignar("horaFinMonitoreo", data[0].horaFinal); 
                         campo.asignar("duraccionMonitoreo", data[0].duracion);
                         $("#contenedorObservacionReprogramacion").slideDown();
                         $("#GuardaMonitoreo").val("Reprogramar");
                         
                         var kendoModalBox = modalBox.abrir("modalBox",null,"1000px",titulo, null);                                
                              kendoModalBox.open();
                   });
               }

    
    };
    
 var obtenetParametrosMonitoreoAdmon = function(){
     
      var paramteros = {
                            opcion:3,                             
                            codigoProceso: campo.obtener("codigoProcesoMonitoreo"),
                            contrato: campo.obtener("contratoMonitoreo"),
                            nit: campo.obtener("nitMonitoreo"),
                            razonSocial: campo.obtener("razonSocialMonitoreo"),
                            comuna: campo.obtener("comunaMonitoreo"),
                            fechaInicialMonitoreo: campo.obtener("fechaInicialMonitoreo"),
                            fechaFinalMonitoreo: campo.obtener("fechaFinalMonitoreo"),
                            direccion:campo.obtener("direccionMonitoreo"),
                            estado: campo.obtener("estadoMonitoreoAdmon"),
                            codigo: ""
                            
                            
                       };
                       
      return paramteros;
     
 }   
 

var enviarSupervision = function(){
    
     var parametros = {
             monitoreos: checkSeleccionados("superChecked"),
             tecnico:campo.obtener("tecnicosSupervision")
     };
    var cadena = "";
    cadena = parametros.monitoreo == "[]" ? '- Debe seleccionar al menos un monitoreo \n':'';
    cadena += parametros.tecnico == '' ? '- Debe seleccionar a un técnico para realizar la supervisión \n':'';
    
    if(cadena == ""){
        
            var xhrSupervision = $.ajax({
                                        type:"POST",
                                        datatype:"json",
                                        url: servlet.insertar.supervisiones ,
                                        data:parametros,
                                        cache:false        
                                    });

               xhrSupervision.done(function(respose,status,xhr){

               }); 
      }else{
          alert(cadena);
          
      }
    
    

};
 
 
 var seleccionarGrillaMonitoreoAdmon = function(){
   
    if(campo.obtener("estadoMonitoreoAdmon") == 1){
        
         generarGrillaMonitoreoAdmonSinSup();
         $("#contenedorTecnicoSupervision").show();                
         campo.lista.crear("tecnicosSupervision",servlet.consultar.tecnicos,{opcion:1});
          
        
    }else{
        $("#contenedorTecnicoSupervision").hide();
        generarGrillaMonitoreoAdmon();
     }
 };
    
    
var abrirRegistroResultadoMonitoreo = function(e){

            var tr = $(e.target).closest("tr"); // Se obtine todo el tr que se va a editar          
            var data = this.dataItem(tr); 

            var parametros = {
                 codigo : data.codigo,
                 opcion : 2
            };
               
     $("#modalBox").load("ResultadoSupervision.jsp",function(response,status,xhr){
         
            xhr.done(function(){
                
                
                
              var xhrSupervision = $.ajax({
                                        type:"POST",
                                        datatype:"json",
                                        url: servlet.consultar.monitoreos ,
                                        data:parametros,
                                        cache:false        
                                    });

               xhrSupervision.done(function(respose,status,xhr){
                   var data =  respose[0];
                   
                     if(respose.length > 0){
                         
                            var xrhTecnico  = campo.lista.crear("tecnicosSupMonitoreo", servlet.consultar.tecnicos,{opcion:1});
                                xrhTecnico.done(function(){
                                    campo.lista.asignar("tecnicosSupMonitoreo",data.tecnicoSup);
                                });
                                
                                  campo.asignar("estuvoSupMonitoreo",data.estuvoSup);
                                  if(data.estuvoSup === 'SI'){
                                      $("#contenedorDatoSupMonitoreo").show();
                                  }
                                  campo.asignar("ObservacionesSupMonitoreo",data.observacionSup);
                                  campo.asignar("codigoSupMonitoreo",data.codigo);
                                  iniciarResultadoSupervision();
                                  
                            if(data.tecnicoSup != '' && data.tecnicoSup != null && data.tecnicoSup != undefined){
                                 $("#div_registrar_resultado_monitoreo").show();
                                  var kendoModalBox = modalBox.abrir("modalBox",null,'650px','Registrar Resultado Supervisión',null);
                                      kendoModalBox.center();                                      
                                      kendoModalBox.open();
                             }else{
                                 alert("Este monitoreo no tiene asignado una supervisión.");
                             }
                     }else{
                         alert("Este monitoreo no tiene asignado una supervisión.");
                     }
               }); 
                
                
                
                
            });
         
         
     });
    
    
    
 };
    
    
 var obtenerDataSourceMonitoreoAdmon = function(){
     
        var url = servlet.consultar.monitoreos;
   
    var paramteros = obtenetParametrosMonitoreoAdmon();
    
    var dataSource = new kendo.data.DataSource(
                        
                        {                             
                            transport: {
                                    read: {
                                        url:url,
                                        data: paramteros, 
                                        dataType: "json",
                                        type:"POST"
                                    }
                                },
                                batch: true,
                                pageSize: 30,
                                serverPaging:true,
                                schema: {
                                        model: {
                                          id: "contrato",
                                          fields: {
                                                codigo : {editable: false},
                                                procesoVertimiento : {editable: false},
                                                fechaMonitoreo: {editable: false},
                                                horaInicial: {editable: false},
                                                horaFinal: {editable: false},
                                                consultor     : {editable: false},
                                                laboratorio: {editable: false},
                                                estado: {editable: false},
                                                tecnicoSup: {editable: false},
                                                observSup: {editable: false},
                                                nit: {editable: false},
                                                razonSocial: {editable: false},
                                                contrato : {editable: false},
                                                duracion : {editable: false}

                                            }
                                        }
                                }
                                
                       }
            
            );

     return dataSource;
     
     
 };   
 

 
 var generarGrillaMonitoreoAdmon = function(){
    
    var dataSource = obtenerDataSourceMonitoreoAdmon();
    var $grilla  = $("#grillaMonitoreo");
    var grilla = $("#grillaMonitoreo").data('kendoGrid'); 
    
    if(grilla != undefined){
       grilla.destroy();
       $grilla.empty();
    }

    $("#grillaMonitoreo").kendoGrid({
                        
                        width:800,
                        autoSync: true,
                       
                        dataSource: dataSource,  
                        
                        columns:
                         [
                           
                            {field : "procesoVertimiento" , title : "Código Proceso" , width: "90px"},
                            {field : "contrato" , title : "contrato" , width: "120px"},
                            {field : "nit" , title : "nit" , width: "130px"},
                            {field : "razonSocial" , title : "Razón Social" , width: "180px"},
                            {field : "estado" , title : "Estado" , width: "110px"},
                            {field : "fechaMonitoreo" , title : "Fecha Monitoreo" , width: "110px"},
                            {field : "horaInicial" , title : "Hora Inicial" , width: "160px"},
                            {field : "horaFinal" , title : "Hora Final" , width: "110px"},
                            {field : "duracion" , title : "Duración" , width: "110px"},                            
                            {field : "consultor" , title : "Consultor" , width: "160px"},
                            {field : "laboratorio" , title : "Laboratorio" , width: "110px"},
                            {field : "tecnicoSup" , title : "Técnico que Supervisó" , width: "110px"},
                            {field : "observSup" , title : "observSup" , width: "200px"},
                            { command:  [ //Botones de la ultima columna
                                          { 
                                             name:"edit",
                                             text:"Registrar Resultado",
                                             click: abrirRegistroResultadoMonitoreo 
                                                
                                             
                                          }                                       
                                        ],
                              title: "", 
                              width: "200px" 
                            }
                        ],
                       pageable: {
                           refresh: true
                       },                        
                       editable:{
                          
                          confirmation: false
                        },
                        remove: function(e){
                            
                                var contrato = e.model.contrato; 
                                var parametros = {
                                                    contrato :  contrato                                                        
                                                 };
                                                 
                                eliminarRegistro(parametros);                           

                        }
                    
                           
     });

};



 var generarGrillaMonitoreoAdmonSinSup = function(){
    
    var dataSource = obtenerDataSourceMonitoreoAdmon();
    var $grilla  = $("#grillaMonitoreo");
    var grilla = $("#grillaMonitoreo").data('kendoGrid'); 
    
    if(grilla != undefined){
       grilla.destroy();
       $grilla.empty();
    }
    $("#grillaMonitoreo").kendoGrid({
                        height: 430,
                        width:800,
                        autoSync: true,
                       
                        dataSource: dataSource, 
                        toolbar: [
                            { template:'<input type="button" id="guardarSupervision" class="cv-btnGuardar" value="Guardar Supervisión">'}
                        ], 
                        columns:
                         [
                           
                            {field : "procesoVertimiento" , title : "Código Proceso" , width: "90px"},
                            {field : "contrato" , title : "contrato" , width: "120px"},
                            {field : "nit" , title : "nit" , width: "130px"},
                            {field : "razonSocial" , title : "Razón Social" , width: "180px"},
                            {field : "estado" , title : "Estado" , width: "110px"},
                            {field : "fechaMonitoreo" , title : "Fecha Monitoreo" , width: "110px"},
                            {field : "horaInicial" , title : "Hora Inicial" , width: "160px"},
                            {field : "horaFinal" , title : "Hora Final" , width: "110px"},
                            {field : "duracion" , title : "Duración" , width: "110px"},                            
                            {field : "consultor" , title : "Consultor" , width: "160px"},
                            {field : "laboratorio" , title : "Laboratorio" , width: "110px"},
                            {field : "tecnicoSup" , title : "Técnico que Supervisó" , width: "110px"},
                            {field : "observSup" , title : "observSup" , width: "200px"},
                            {   title:  "<input id='checkAll' type='checkbox' class='check-box' />",
                                template: "<input value=\"#=procesoVertimiento#\" type=\"checkbox\"  name=\"superChecked\" />",    
                                width:"35px"
                            },
                            { command:  [ //Botones de la ultima columna
                                          { 
                                             name:"edit",
                                             text:"Editar Monitoreo"
                                          }                                       
                                        ],
                              title: "", 
                              width: "200px" 
                            }
                        ],
                       pageable: {
                           refresh: true
                       },                        
                       editable:{
                          
                          confirmation: false
                        },
                        remove: function(e){
                            
                                var contrato = e.model.contrato; 
                                var parametros = {
                                                    contrato :  contrato                                                        
                                                 };
                                                 
                                eliminarRegistro(parametros);                           

                        }
                    
                           
     });

};


var iniciarResultadoSupervision = function(){
    
    
    $("#guadarResultadoSup").on("click",guardarResultadoSupervision);
    
    $("#estuvoSupMonitoreo").on("change",function(){
       
        if(this.value == 'SI'){
            $("#contenedorDatoSupMonitoreo").slideDown("500");
            
        }else{
            $("#contenedorDatoSupMonitoreo").slideUp("500");            
        }
        
    });
    
    $("#archivosSupMonitoreo").kendoUpload({
              async: {
                  saveUrl: servlet.cargaArchivos.subir+"?opcion=5&codigo="+campo.obtener("codigoSupMonitoreo"),
                  removeUrl: servlet.cargaArchivos.eliminarAnexosMonitoreo,
                  autoUpload: true
              }
      });
    
    
    
};

var guardarResultadoSupervision = function(){
    
    var parametros = {
                        tecnico:campo.obtener("tecnicosSupMonitoreo"),
                        estuvo:campo.obtener("estuvoSupMonitoreo"),
                        observacion:campo.obtener("ObservacionesSupMonitoreo"),
                        codigo:campo.obtener("codigoSupMonitoreo")
                     };
                     
    var xhrSupervision = $.ajax({
                                    type:"POST",
                                    datatype:"json",
                                    url: servlet.insertar.resultRupervisiones ,
                                    data:parametros,
                                    cache:false        
                                });
                                
     xhrSupervision.done(function(response){
         console.log(response);
         if(response.error = 1){
              alert("El resultado fue almacenado exitosamente.");
              generarGrillaMonitoreoAdmon();
              modalBox.cerrar("modalBox");            
             
         }else if(response.error = 2){
              alert("El resultado ya ha sido registrado.");
              
         }else if(response.error = 3){
             alert("El proceso ya ha sido finalizado.");
             
         }else{
            alert("Se ha presentado un error inesperado.");
            
         }
         
     });

    
    
};


