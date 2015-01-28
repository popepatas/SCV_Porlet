//$(document).on("ready", inicioCrearProceso);

function inicioCrearProceso(){
 

    //Se inicializan los botones
    
    $("#crearProcesoVertimiento").on("click",enviarFormularioCrearProceso);
    $("#contrato").on("blur",buscarContratosCliente);
   
    $("#fechaCreacion").kendoDatePicker({
         format: "dd/MM/yyyy"
    });

    $("#cicloContrato").kendoNumericTextBox({       
        format:"#",
        min: 0        
    });
   $("#pozoContrato").kendoNumericTextBox({       
        min: 0        
    });

    var url="";

    $("#contrato").on('keypress', validarEscrituraNumero);
    $("#nitProceso").on('keypress', validarEscrituraNit);
     	
    //Inicializa el validador
	$("#div_crear_proceso").kendoValidator(
		{
			rules: {
                /*
                 * Función valida los diferentes tipos de escritura dependiendo del tipo de documento
                 * @retrun vacio
                 */
                 /*validacionNit: function (input) {

                        
                        var id    = input.attr('id');

                        var val = null ;

                        if (input.is("[data-validacionNit-msg]") && input.val() != ""){   
                            val = campo.validarNit(id);
                            return val;
                        }

                        return true;
                },*/
                /*Evita la validación incorrecta del formato de la fecha*/
                date: function (input) {
                    var attr = input.attr("data-role");
                    if( attr == "datepicker"){ 
                        var fecha = kendo.parseDate(input.val(), "dd/MM/yyyy");
                        return fecha instanceof Date;
                    }
                    return true;
                }
            }
        });
    
   


}



/*
 * Función envia al backend
 * @retrun vacio
 */

function enviarFormularioCrearProceso(){
    var validador = $("#div_crear_proceso").data("kendoValidator");
    var status     = $(".status");
        
		 if (validador.validate()) {  
                   
                        guardarRegistroCrearProceso();
                        
                } 
}


function obtenerParametroCrearProceso(){
    
     var parametros = {                                                        
                            nit   : campo.obtener("nitProceso"),
                            contrato : campo.obtener("contrato"),
                            fechaCreacion: campo.obtener("fechaCreacion"),
                            ciclo: campo.obtener("cicloContrato"),
                            pozo: campo.obtener("pozoContrato"),
                            sector: campo.obtener("sectorContrato"),
                            consumo: campo.obtener("consumoContrato"),
                            direccion: ""
                            
                                    
                            
                      };
                      
    return parametros;
}


function guardarRegistroCrearProceso(){
    
       var url = servlet.insertar.procesoCV;    
       var parametros = obtenerParametroCrearProceso();                 

       var xhrGuardar = $.ajax(
                 {
                     type:"POST",
                     data: parametros,
                     url: url,
                     cache :false
                 }
              );
        xhrGuardar.done(function(data,status,xhr){
                var error,codigo;
                var texto = "No data Found";
                        if(data.length > 0){
                           error = data[0].error;
                           codigo = data[0].codigoProceso;
                           
                           switch(error){
                              /* case 1 : texto = 'Si registra el proceso pero el contrato esta asignado a mas de 1 cliente';
                                break;
                                
                               case 2 : texto ='Si registra el proceso pero solo esta asignado a un cliente';
                                break;*/
                                
                                case 3 : texto ='El número de contrato ingresado se encuentra asociado a un contrato principal.';
                                break;
                                
                                case 4 : texto ='El contrato ingresado no existe.';
                                break;
                                
                                case 5 : texto ='El cliente ingresado no existe.';
                                break;
                                
                                default: texto = 'El registro ha sido almacenado exitosamente.';
                               
                           }
                           if(codigo>0){
                               document.location.href="../proceso/Proceso.jsp?proceso="+codigo+"#infoPro";
                           }
                            
                            
                        }
            
                         alert(texto);
                     });
    
}


function buscarContratosCliente(){
    
    //Obtenemos el url del servlet
    var url = servlet.consultar.cliente;
    var parametros =  {
                        contrato : campo.obtener("contrato"),
                        opcion   : 3 
                        
                      };
           //verificamos que el contrato no este vacio           
    if(parametros.contrato != ''){
        $(".overlay").css("display","");
            $.ajax(
                      {
                          type:"POST",
                          data: parametros,
                          url: url,
                          cache :false,
                          success: function(data){
                              var response =data[0];
                              $(".k-temp-text").remove();
                              if(response){
                                    campo.asignar("nitProceso",response.nit);
                                    $("#nitProceso").after("<div class='k-temp-text'>"+response.razonSocial+"</div>");
                                    
                              }
                               $(".overlay").css("display","none");
                          }
                      }
                   );
       }else{
           $(".k-temp-text").remove(); //removemos el nombre de la razon social si la hay
       }
    
}

/*
 * Función que valida que solo se ingresen números
 * @retrun vacio
 */

function validarEscrituraNumero () {
   
   return campo.numerico(event);
}

/*
 * Función que valida que lo ingresado tenga formato NIT
 * @retrun vacio
 */

function validarEscrituraNit () {
   
   return campo.nit(event,this);
}