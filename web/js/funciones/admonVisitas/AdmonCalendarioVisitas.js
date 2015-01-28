  var mostrarCalendario = function(){
                var titulo = "Visitas programadas de "+ $("#razonSocial").html();
                var html = "";
                $("#modalBox").html(html);
                var kendoModalBox = modalBox.abrir("modalBox",null,"1000px",titulo, null);                                
                kendoModalBox.open();
                calendario();
        };
        
 $(document).on("ready", calendario);
 
 var calendario = function (){
     
        var cont = 0;	
	var headerOptions =  {
                                left: 'prev,next today', //			    
                                center: 'title',	
                                right: 'month'
                            };

	var calendarAgenda = jQuery('#calendar').fullCalendar({

				header: headerOptions,
				/*View Options*/
				defaultView : 'month',
	            /*Agenda Options*/
				slotMinutes: 60,
				allDaySlot: false,
				/*Text/Time Customization*/
				//timeFormat : 'H(:m)',
	            /*Selection*/ 
				selectable: false, // Propiedad que permite seleccionar uno o varios dias
				selectHelper: true, // Propiedad que permite visualizar con mayor claridad lo que se esta seleccionando
				select: function(start, end, allDay,jsEvent) {					 
									 	
					

				},					
			   
				events: {
                                    url: servlet.consultar.fechasVisita,
			            type: 'POST',
			            data: {
			                codigoProceso : campo.obtener("codigoProceso"),
                                        clase:"event-vista"
			            }

					},
			/*Clicking & Hovering*/
                        eventClick : function(event, element){
					
					

			},
                        dayClick : function( date, allDay, jsEvent, view) {
				fechaClickeada = campo.fecha.formatear(date);
                                
                                cargarRegistrarVisita(fechaClickeada);
			        
                               
                        }
				
			});   

			     
	        
			 
};    

var generarGrillaVisitas = function(){

    var url = servlet.consultar.visitasPorProceso+"?opcion=1" ; 
    
    var parametros = {
                       codigoProceso: campo.obtener("codigoProcesoVisita"),
                       tipoVisita : campo.obtener("tipoVisita"),
                       fechaInicio : campo.obtener("fechaInicio"),
                       fechaFin : campo.obtener("fechaFin"),
                       estadoVisita : campo.obtener("estadoVisita")
                    };
    
    // Sirver para establecer los parametros que se van a enviar o recibir al backend
    var dataSource = new kendo.data.DataSource(
                        
                        {    
                            //Se establece a donde y que sele va a enviar al backend
                            transport: {
                                    read: {
                                        url:url,
                                         data:parametros, 
                                        dataType: "json",
                                        type:"POST"
                                    }
                                },
                                batch: true,
                                pageSize: 20,
                                serverPaging:true,
                                // que es lo que se va a recibir del backend
                                schema: {
                                        total: function(response) {  
                                            if(response.length>0){
                                               return response[0].total;
                                            }
                                        },
                                        model: {
                                          id: "codigo",
                                          fields: {
                                             codigo: { editable: false, nullable: true},
                                             codigoProceso :{ editable:false },
                                             nombre: { editable: false },
                                             apellidos: { editable: false, nullable: true},
                                             fecha_visita: { editable: false },
                                             motivo: { editable: false, nullable: true},
                                             estado: { editable: false },
                                             resultado: { editable: false },
                                             tipovisita: { editable: false },
                                             razon_social:{editable:false},
                                             contrato: {editable:false}
                                                                                  
                                            }
                                        }
                                }
                                
                       }
            
            );

    $("#grillaVisitas").kendoGrid({
                        height: 430,
                        width:800,
                        autoSync: true,
                       
                        dataSource: dataSource,                        
                        columns:
                         [  
                            { field: "codigoProceso", title: "Código Proceso", width: "80px" },
                            { field: "contrato", title: "Contrato", width: "100px" },
                            { field: "razon_social", title: "Cliente", width: "200px" },
                            { field: "nombre", title: "Técnico", 
                               template:"#= nombre# #= apellidos# ", width: "200px" },                            
                            { field: "fecha_visita", title: "Fecha de visita", width: "160px" }, 
                            { field: "tipovisita", title: "Tipo Visita", width: "160px" }, 
                            { field: "motivo", title: "Motivo", width: "160px" }, 
                            { field: "resultado", title: "Resultado", width: "160px" }, 
                            { field: "estado", title: "Estado", width: "160px" },
                           
                            { command:  [//Botones de la ultima columna
                                          { //Boton editar
                                             name:"edit",
                                             text:"Editar",
                                             click: function(e){
                                                 
                                                 // Se obtine todo el tr que se va a editar          
                                                  var tr = $(e.target).closest("tr"); 
                                                  var data = this.dataItem(tr); 
                                                
                                                  var param = {
                                                       codigo : data.id,
                                                       opcion : 2
                                                  };
                                                     vistaActualizarVisita(param);  
                                             }
                                          } ,                                     
                                          { //Boton Registrar Resultado
                                             name:"Resultado",
                                             text:"Resultado",
                                             click:  vistaResultVisita  
                                             
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
                                              

                        }
                    
                           
     });



};
