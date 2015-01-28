

//info general

function informacionGeneral(){
    
    
    $("#finalizarProceso").on("click", finalizarProceso);
    elmto.deshabilitar("informo");
   
    $("#contenedorBotonesCliente").remove();
    
    $("#tipoInforme").on("change", mostrarTabsProceso);
    //DatePicker
        $("#fechaAsesoria").kendoDatePicker({
            format:"dd/MM/yyyy"
        });
        
    //Tabs     
     $("#TabsContainer").kendoTabStrip({
            animation:  {
                open: {
                    effects: "fadeIn"
                }
            }
       });
    
    //Eventos
        $("#guardar").on("click", enviarFormularioGeneral); 
        $("#requiereVisita").on("click", mostrarEnlace);
        $("#progVisita").on("click",{tipoVisita:1},mostrarCalendario);

     	
    //Inicializa el validador
	$("#div_crear_proceso").kendoValidator(
		{
			rules: {
                /*
                 * Función valida los diferentes tipos de escritura dependiendo del tipo de documento
                 * @retrun vacio
                 */
                 validacionNit: function (input) {

                        
                        var id    = input.attr('id');

                        var val = null ;

                        if (input.is("[data-validacionNit-msg]") && input.val() != ""){   
                            val = campo.validarNit(id);
                            return val;
                        }

                        return true;
                }
            }
        });
        
        mostrarInformacionInfoGenral();
   
}
 
    // Funciones  
    
       var finalizarProceso = function(){
       
           if(confirm("¿Esta seguro que desea Finalizar este proceso?")){
                        var url = servlet.finalizar.procesoVertimiento;
                        var parametros = {
                                   codigoProceso : campo.obtener("codigoProceso")                       
                        };

                        var xhr = $.ajax({
                                                type:"POST",
                                                data: parametros,
                                                url: url,
                                                cache :false
                                            });

                        xhr.done(function(response, status, xhr){
                                console.log(response);
                                var resp = response.respuesta;
                                var texto = "Data no found";
                                switch(resp){
                                    case 1 : texto = "El proceso fue finalizado con exito";
                                        break;
                                    case 2 : texto = "El proceso tiene un tipo de informe incorrecto";
                                        break;
                                    case 3 : texto = "El proceso no tiene informe diligenciado";
                                        break;
                                    case 4 : texto = "El proceso no tiene el estado proceso adecuado para finalizar";
                                        break;
                                   case 5 : texto = "El proceso  tiene el informe parcialmente diligenciado";
                                        break;
                                    case 6 : texto = "El proceso tiene una visita pendiente";
                                        break;
                                   case 7 : texto = "Data no found";
                                        break;



                                }
                                alert(texto);
                                document.location.reload();
                        });
              }
       };
       
       var mostrarTabsProceso = function(){
           
           var informe = campo.obtener("tipoInforme");
           
           if(informe == '4'){
               
               $('#Tab5').fadeOut('fast');
               $('#Tab3, #Tab4').fadeIn('fast');
               
           }else if(informe == '3'){
               
               $('#Tab3, #Tab4').fadeOut('fast');
               $('#Tab5').fadeIn('fast');
               
           }else{
               
               $('#Tab3, #Tab4, #Tab5').fadeOut('fast');
           }
           
           
       };
       
        // Se carga la información incial del formulario
        var mostrarInformacionInfoGenral = function(){
            
            var url = servlet.consultar.InfoGeneral;
            var parametros = {
                       codigoProceso : campo.obtener("codigoProceso"),
                       opcion:2
            };
            
            var xhr = $.ajax({
                                    type:"POST",
                                    data: parametros,
                                    url: url,
                                    cache :false
                                });
                                
            xhr.done(function(response, status, xhr){
                    var data = response[0];
                    
                    if(response.length > 0){    
                       
                        $("#contrato").html(data.contrato);
                        /*Se carga la Información del cliente*/
                            $("#razonSocial").html(data.razonSocial);
                            $("#Infonit").html(data.nit);
                            $("#codigoCliente").val(data.codigoCliente);
                            
                           mostrarInfoClienteProceso(data.codigoCliente);
                          //Listas Dinamicas
                           var xhrTipoInfo = campo.lista.crear("tipoInforme",servlet.consultar.tiposInformeVertimientos,{opcion:1});    
                           var xhrTipoCon  = campo.lista.crear("tipoContacto",servlet.consultar.TiposContacto,{opcion:1});  

                            xhrTipoInfo.done(function(){
                                campo.asignar("tipoInforme", parseInt(data.tipoInforme)); 
                                mostrarTabsProceso();//Se muestran los Tabs
   
                            });
                            xhrTipoCon.done(function(){
                                campo.asignar("tipoContacto",parseInt(data.tipoContacto));
                            });

                           campo.asignar("informo",campo.convertirBool(data.informo));
                           campo.asignar("codigoAsesoria",data.codigoAsesoria );                       
                           campo.asignar("contacto",data.personaContacto);
                           campo.asignar("asunto",data.asunto);
                           campo.asignar("fechaAsesoria",data.fechaAsesoria);
                           campo.asignar("requiereVisita",campo.convertirBool(data.requiereVisita));
                           campo.asignar("estadoProceso",data.estadoProceso);
                        if(campo.obtener("requiereVisita")){  
                            $("#contenedorEnlaceProgramarVisita").show();
                        }
                        cerrarProcesoInformacionGeneral();
                 }else{
                     campo.lista.crear("tipoInforme",servlet.consultar.tiposInformeVertimientos,{opcion:1});    
                     campo.lista.crear("tipoContacto",servlet.consultar.TiposContacto,{opcion:1});  

                 }
            });
            
         };
        
        var mostrarEnlace = function(){
            
            var $ContenedorEnlace = $("#contenedorEnlaceProgramarVisita");
             if(this.checked === true){
                 $ContenedorEnlace.slideDown(500);
                 
             }else if(this.checked === false){
                 $ContenedorEnlace.slideUp(500);
               
             }
            
        };
        
        var mostrarCalendario = function(event){
            
                var tvisita = event.data.tipoVisita;
                campo.asignar("tipoVisita", tvisita);
                var titulo = "Visitas programadas de "+ $("#razonSocial").html();
                var html = "<div id='contenedorCalendario'><div id='calendar' style='width:800px'></div></div>";
                
                $("#modalBox").html(html);
                var kendoModalBox = modalBox.abrir("modalBox",null,"850px",titulo, null, calendario); 
                kendoModalBox.open();
                //calendario();
        };
       

        var enviarFormularioGeneral =  function(){

                    var validador  = $("#div_unidades_medidas").data("kendoValidator");
                    var status     = $(".status");

                // if (validador.validate()) {
                        if(campo.obtener("codigoAsesoria")  == ''){
                            guardarRegistro();
                        }else{
                            actualizarRegistro();
                        }

                   // }
        };
       var obtenerParametros = function(){

                var parametros = {                                                        
                                      tipoInforme: campo.obtener("tipoInforme"),               
                                      tipoContacto: campo.obtener("tipoContacto"),
                                      contacto: campo.obtener("contacto"),
                                      asunto: campo.obtener("asunto"),
                                      fechaAsesoria: campo.obtener("fechaAsesoria"),
                                      informo: campo.check("informo"),
                                      requiereVisita:  campo.check("requiereVisita"),
                                      codigoProceso: campo.obtener("codigoProceso")
                                      
                                  };
                                  
                 return parametros;
          };
       
       var guardarRegistro = function(){

                var url = servlet.insertar.InfoGeneral;
                var parametros = obtenerParametros();
               
                if(parametros.codigoProceso != ''){
                    
                     elmto.deshabilitar('guardar');
                     var xhrGuardarInfoGeneral = $.ajax({
                                                        type:"POST",
                                                        data: parametros,
                                                        url: url,
                                                        cache :false
                                                    });
                                                    
                     var xhrActInfoCliente = actualizarRegistroCliente(); /*Archivo:clienteInfoGeneral*/

                      $.when(xhrGuardarInfoGeneral, xhrActInfoCliente).done(function(){
                             
                              alert("El registro ha sido almacenado exitosamente.");
                              elmto.habilitar('guardar');  
                           
                      });
              } 

        };
        
        
      var actualizarRegistro = function (){
                    var parametros = obtenerParametros();
                     ;
                    var url = servlet.actualizar.InfoGeneral;
                    elmto.deshabilitar('guardar');                    
                    var  xhrActualizarInfoGeneral =  $.ajax(
                               {
                                   type:"POST",
                                   data: parametros,
                                   url: url,
                                   cache :false
                               }
                            );

                     var xhrActInfoCliente = actualizarRegistroCliente(); /*Archivo:clienteInfoGeneral*/

                      $.when(xhrActualizarInfoGeneral, xhrActInfoCliente).done(function(fn1,fn2){
                             
                             console.log(fn1);
                             console.log(fn2);
                            var data2 = fn2[0];
                            
                             if(data2.error != 1){
                                 
                                     if(data2.error == 2){
                                          alert("El NIT registrado ya ha sido almacenado.");

                                      }else{
                                          alert("Hubo un problema al almacenar la información."); 
                                      }
                             }else{
                                 
                                  alert("El registro ha sido actualizado exitosamente.");
                             }
                             
                            
                              elmto.habilitar('guardar');  
                           
                      });
            };
            
            
/* Se carga el formulario de registrar visita y se le asignan sus validaciones*/            
 var cargarRegistrarVisita = function(fecha){
     
     var url = "RegistrarVisita.jsp";
     var titulo = "Registrar Visita";
       $("#modalBox2").load(url,function(response,status,xhr){
           
           xhr.done(function(){
                
                inicioCrearVisita();//ubicación --> crearVisita.js
                campo.asignar("fechaVisita",fecha);
               titulo += " - Fecha: " + campo.obtener("fechaVisita");
                var kendoModalBox = modalBox.abrir("modalBox2",null,"600px",titulo, null);                                
                kendoModalBox.open();
           });
           
           
           
       }); 
     
 };


 var calendario = function (){
        var cont = 0;	
	var headerOptions =  {
                                left: 'prev,next today', //			    
                                center: 'title',	
                                right: 'month'
                            };

	 calendarAgenda = jQuery('#calendar').fullCalendar({

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
				events: {
                                    url: servlet.consultar.fechasVisita,
			            type: 'POST',
			            data: {
			                codigoProceso : campo.obtener("codigoProceso"),
                                        clase:"event-vista"
			            },
                                    cache:false

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
