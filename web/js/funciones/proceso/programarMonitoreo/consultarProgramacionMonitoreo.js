inicioConsultarMonitoreo = function()
{
    
    
    /*DatePicker*/
    $("#fechaFinalMonitoreo").kendoDatePicker({
         format:"dd/MM/yyyy"
    });
    
     $("#cancelarProgMonitoreo").on("click", cancelarMonitoreo );
     
     $("#limpiarMonitoreo").on("click", function(){
         campo.limpiar("fechaInicialMonitoreo");
         campo.limpiar("fechaFinalMonitoreo");
     });
    
    /*DatePicker*/
    $("#fechaInicialMonitoreo").kendoDatePicker({
         format:"dd/MM/yyyy"
    });
    
    $("#bntProgramarMonitoreo").on("click", cargarProgramarMonitoreo);
    
    $("#consultarMonitoreo").click(generarGrillaMonitoreo);
            
    var obtenerPuntosMonitoreo = function(codigoMonitoreo){
        
        var url = servlet.consultar.puntosMonitoreo;
        var actividades = $('select[name=actividadesMonitoreo]');
        
        var xhrPuntosMonitoreo = $.post( url,{codigoMonitoreo:codigoMonitoreo});        
                
            xhrPuntosMonitoreo.done(function(data,status){

                    $.each( data, function( index1, valor ) {

                        var valorDataBase = valor.actividadEconomica;

                        $.each( actividades, function( index2, obj ) {

                            if(index1 === index2){
                                var opciones = obj.options;

                                for (var i= 0; i < opciones.length; i++) {

                                    var valorOpcion = opciones[i].value;

                                    if (valorOpcion == valorDataBase) {

                                        opciones[i].selected= true;
                                    }
                                }
                            }

                        });
                
            });  

        });
    };
    
     mostrarRegistroMonitoreo = function(parametrosMonitoreo){
                     
            var url = servlet.consultar.monitoreos;
            var titulo = "Monitoreo";
            var xhrMonitoreo;
            var estado = parametrosMonitoreo.estado;
             
             if(parametrosMonitoreo != undefined){
                     
                 xhrMonitoreo = $.post(url, parametrosMonitoreo);
                 
                 xhrMonitoreo.done(function(data) {
                     
                        
                        campo.asignar("informo", true);
                         
                         campo.asignar("fechaMonitoreo",data[0].fechaMonitoreo.substring(0,11));
                         campo.asignar("laboratorioMonitoreo", data[0].laboratorio);
                         campo.asignar("consultorMonitoreo", data[0].consultor);
                         campo.asignar("horaInicioMonitoreo", data[0].horaInicial);
                         campo.asignar("horaFinMonitoreo", data[0].horaFinal);
                         campo.asignar("observacionesReprogramacion", data[0].observacion);
                         campo.asignar("duraccionMonitoreo", data[0].duracion);
                         
                         $("#contenedorObservacionReprogramacion").slideDown();
                         $("#GuardaMonitoreo").val("Reprogramar");
                         
                         if(estado === 'CANCELADO'){        
                             
                             var horaIn = $("#horaInicioMonitoreo").data("kendoTimePicker");
                                 horaIn.enable(false);
                                 
                             var horaFin = $("#horaInicioMonitoreo").data("kendoTimePicker");
                                 horaFin.enable(false);
                             
                             var fechaMon = $("#fechaMonitoreo").data("kendoDatePicker");     
                                 fechaMon.enable(false);
                                
                                elmto.deshabilitar("laboratorioMonitoreo");
                                elmto.deshabilitar("consultorMonitoreo");
                                
                                $("#GuardaMonitoreo").remove();
                                                            
                         }
                         
                         
                         var kendoModalBox = modalBox.abrir("modalBox",null,"1000px",titulo, null);                                
                              kendoModalBox.open();
                          
                   });
               }
             cargarTemplateMonitoreo(obtenerPuntosMonitoreo,parametrosMonitoreo.codigo);
    
    };
    
          
 
};

    function cargarProgramarMonitoreo(parametrosMonitoreo,opcion){
       
         var parametrosMonitoreo = parametrosMonitoreo || undefined;
         
         $("#modalBox").load("ProgramarMonitoreo.jsp",function(response, status, xhr){
                
               /* xhr.done(function(){*/
                        //Pasamos la función mostrar y los parametros para que sean procesados
                        inicioProgramacionMonitoreo(mostrarRegistroMonitoreo, parametrosMonitoreo,opcion);
                        
                /*});*/
         });
   };
   

   function generarGrillaMonitoreo(){
    
    var url = servlet.consultar.monitoreos;
    
    var grilla = $("#grillaMonitoreo").data('kendoGrid'); 
    
    if(grilla != undefined){
       grilla.destroy();
    }
    
    var dataSource = new kendo.data.DataSource(
                        
                        {                             
                            transport: {
                                    read: {
                                        url:url,
                                        data:{
                                            opcion:1, 
                                            codigoProceso : campo.obtener("codigoProceso"),
                                            fechaInicialMonitoreo: campo.obtener("fechaInicialMonitoreo"),
                                            fechaFinalMonitoreo: campo.obtener("fechaFinalMonitoreo")
                                            
                                        }, 
                                        dataType: "json",
                                        type:"POST",
                                        cache:false
                                    }
                                },
                                batch: true,
                                pageSize: 20,
                                schema: {
                                        model: {
                                          id: "contrato",
                                          fields: {
                                                 codigo : {editable: false},
                                                 procesoVertimiento : {editable: false},
                                                 fechaMonitoreo : {editable: false},
                                                 horaInicial : {editable: false},
                                                 horaFinal : {editable: false},
                                                 consultor : {editable: false},
                                                 laboratorio : {editable: false},
                                                 estado : {editable: false},
                                                 duracion : {editable: false}
                                                                         
                                            }
                                        }
                                }
                                
                       }
            
            );


    $("#grillaMonitoreo").kendoGrid({
                        height: 430,
                        width:800,
                        autoSync: true,
                       
                        dataSource: dataSource,                        
                        columns:
                         [
                           
                            
                            
                            {field : "fechaMonitoreo" , title : "Fecha Monitoreo" , width: "110px"},
                            {field : "horaInicial" , title : "Hora Inicial" , width: "160px"},
                            {field : "horaFinal" , title : "Hora Final" , width: "110px"},
                            {field : "duracion" , title : "Duración" , width: "110px"},                            
                            {field : "consultor" , title : "Consultor" , width: "160px"},
                            {field : "laboratorio" , title : "Laboratorio" , width: "110px"},
                            {field : "estado" , title : "Estado" , width: "110px"},
                           
                            { command:  [ //Botones de la ultima columna
                                          { 
                                             name:"edit",
                                             text:"Editar",
                                             click: function(e){
                                                 
                                                    var tr = $(e.target).closest("tr"); // Se obtine todo el tr que se va a editar          
                                                    var data = this.dataItem(tr); 

                                                    var parametros = {
                                                         codigo : data.codigo,
                                                         opcion : 2,
                                                         estado : data.estado
                                                    };
                                                    
                                                    cargarProgramarMonitoreo(parametros,1);  
                                                       
                                                   
                                             }
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
                        remove: function(e){
                            
                                var contrato = e.model.contrato; 
                                var parametros = {
                                                    contrato :  contrato                                                        
                                                 };
                                                 
                                eliminarRegistro(parametros);                           

                        }
                    
                           
     });

}