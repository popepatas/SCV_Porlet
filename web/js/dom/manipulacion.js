

$(document).on("ready", inicio_manipulacion);


function inicio_manipulacion() {
	
	// Mensajes de validación 
        validacionesKendo();
        hashSelectMenuParametrizacion();
        scrollTo(0,0);
        visitasPendientes();
        setInterval(function(){ visitasPendientes(); },50000);
        //eventos
        $("#notificacion").on("click", abrirVisitasPendientes);
}

function abrirVisitasPendientes(){
    
    document.location.href="/SCV_Portlet/visitas/AdmonVisitas.jsp#AdmonVisitas";
    
}

function validacionesKendo(){
     
        //Campos de texto
	$(".cmpRequerido").attr({validationMessage : "Ingrese un valor para este campo"});
	//Campos listas 
	$(".lstRequerido").attr({validationMessage : "Seleccione una opci&oacute;n de la lista"});

        //Campos que sean NIT
	$(".cmpNit").attr({'data-validacionNit-msg' : "No corresponde a la escritura de un NIT"});
       
       //Campos Dirección
	$(".cmpDireccion").attr({'data-validacionDireccion-msg' : "Dirección incompleta"});
       
       //Campos E-mail
 	$(".cmpEmail").attr({"data-email-msg": "Email ingresado  no valido"});
        
       //Campos con Datepicker    
        $(".cmpFecha").attr({"data-date-msg": "Fecha ingresada  no valida"});
        
       //Campos con Timepicker    
        $(".cmpTiempo").attr({"data-hora-msg": "Hora ingresada  no valida"});
        
        $(".cmpRangoTiempo").attr({"data-horaMayor-msg": "La hora final ingresada debe ser mayor a la hora inicial"});
        
    
}

function hashSelectMenuParametrizacion (){
    
    var hash = window.location.hash;
   
    switch(hash){
        
       
        case "#infoPro": $(".scvProceso").addClass("selectedMenu");
            break;
        case '#acteconomicas' :
                              $(".scvParm").addClass("selectedMenu");
                              $("#acteconomicas").addClass("Selected");
             break;
         case '#actParm' :
                              $(".scvParm").addClass("selectedMenu");
                              $("#actParm").addClass("Selected");
             break;
         case '#AsoContrato' :
                              $(".scvParm").addClass("selectedMenu");
                              $("#asoContrato").addClass("Selected");
             break;
        case '#docRequerido' :
                              $(".scvParm").addClass("selectedMenu");
                              $("#docRequerido").addClass("Selected");
             break;
        case '#estadoProc' :
                              $(".scvParm").addClass("selectedMenu");
                              $("#estadopr").addClass("Selected");
             break;
        case '#consultores' :
                              $(".scvParm").addClass("selectedMenu");
                              $("#consultores").addClass("Selected");
             break;
        case '#laboratorio' :
                              $(".scvParm").addClass("selectedMenu");
                              $("#laboratorio").addClass("Selected");
             break;
        case '#motivo' :
                              $(".scvParm").addClass("selectedMenu");
                              $("#motivo").addClass("Selected");
        break;     
        case '#puntos' :
                              $(".scvParm").addClass("selectedMenu");
                              $("#puntos").addClass("Selected");
        break;  
        case '#paramFisQuim' :
                              $(".scvParm").addClass("selectedMenu");
                              $("#paramFisQuim").addClass("Selected");
        break;  
        case '#tarifa' :
                              $(".scvParm").addClass("selectedMenu");
                              $("#tarifa").addClass("Selected");
        break;  
        case '#tecnico' :
                              $(".scvParm").addClass("selectedMenu");
                              $("#tecnico").addClass("Selected");
        break;  
        case '#tipoInfo' :
                              $(".scvParm").addClass("selectedMenu");
                              $("#tipoInfo").addClass("Selected");
        break;  
        case '#tiempo' :
                              $(".scvParm").addClass("selectedMenu");
                              $("#tiempo").addClass("Selected");
        break;  
         case '#contacto' :
                              $(".scvParm").addClass("selectedMenu");
                              $("#contacto").addClass("Selected");
        break; 
        case '#tipoparam' :
                              $(".scvParm").addClass("selectedMenu");
                              $("#tipoparam").addClass("Selected");
        break; 
        case '#unidad' :
                              $(".scvParm").addClass("selectedMenu");
                              $("#unidad").addClass("Selected");
        break; 
        //consultarProceso
        case '#consultarProceso' :
                              $(".scvProceso").addClass("selectedMenu");
                              $("#consultarProceso").addClass("Selected");
        break;  
        case '#crearProceso' :
                              $(".scvProceso").addClass("selectedMenu");
                              $("#crearProceso").addClass("Selected");
        break; 
        case '#Proceso' :
                              $(".scvProceso").addClass("selectedMenu");     
        break;
       /*Modulo de clientes*/      
        case '#crearCliente' : $(".scvCliente").addClass("selectedMenu");
                               $(".crearCliente").addClass("Selected");
             break;
             
        case '#consultarCliente' : $(".scvCliente").addClass("selectedMenu");
                                   $(".consultarCliente").addClass("Selected");
             break;
         case '#clientesContrato' : $(".scvCliente").addClass("selectedMenu");
                                   $(".clientesContrato").addClass("Selected");
             break;
             
        case '#AdmonVisitas' : $(".scvVisita").addClass("selectedMenu");
                                   $(".AdmonVisitas").addClass("Selected");
             break;
        case '#AdmonMonitoreo':  $(".scvVisita").addClass("selectedMenu");
                                   $(".AdmonMonitoreo").addClass("Selected");
             break;
         case '#Addpermisos': $(".scvPermisos").addClass("selectedMenu");
                              $(".Addpermisos").addClass("Selected");
             break;
         case '#AddUser': $(".scvPermisos").addClass("selectedMenu");
                              $(".AddUser").addClass("Selected");
             break;
          /*Reportes*/
        case '#consultarReporte1':
            $(".scvReporte").addClass("selectedMenu");
            $(".consultarReporte1").addClass("Selected");
            break;
        case '#consultarReporte2':
            $(".scvReporte").addClass("selectedMenu");
            $(".consultarReporte2").addClass("Selected");
            break;
        case '#consultarReporte3':
            $(".scvReporte").addClass("selectedMenu");
            $(".consultarReporte3").addClass("Selected");
            break;
        case '#consultarReporte4':
            $(".scvReporte").addClass("selectedMenu");
            $(".consultarReporte4").addClass("Selected");
            break;
        case '#consultarReporte5':
            $(".scvReporte").addClass("selectedMenu");
            $(".consultarReporte5").addClass("Selected");
            break;
        
        /*Dashboard*/
        case '#consultarDashboard1':
            $(".scvDashboard").addClass("selectedMenu");
            $(".consultarDashboard1").addClass("Selected");
            break;
        case '#consultarDashboard2':
            $(".scvDashboard").addClass("selectedMenu");
            $(".consultarDashboard2").addClass("Selected");
            break;
        case '#consultarDashboard3':
            $(".scvDashboard").addClass("selectedMenu");
            $(".consultarDashboard3").addClass("Selected");
            break;
        case '#consultarDashboard4':
            $(".scvDashboard").addClass("selectedMenu");
            $(".consultarDashboard4").addClass("Selected");
            break;
        case '#consultarDashboard5':
            $(".scvDashboard").addClass("selectedMenu");
            $(".consultarDashboard5").addClass("Selected");
            break;
        case '#consultarDashboard6':
            $(".scvDashboard").addClass("selectedMenu");
            $(".consultarDashboard6").addClass("Selected");
            break;    
        case '#consultarReporte6':
            $(".scvReporte").addClass("selectedMenu");
            $(".consultarReporte6").addClass("Selected");
            break;
        case '#consultarReporte7':
            $(".scvReporte").addClass("selectedMenu");
            $(".consultarReporte7").addClass("Selected");
            break;
            
        case '#ReporteDagma':
          $(".scvReporte").addClass("selectedMenu");
          $(".ReporteDagma").addClass("Selected");
         break;
         
        default: $(".scvInicio").addClass("selectedMenu");
            break;
        
        
    }
  
   
}

function visitasPendientes(){
    
    var url = servlet.consultar.visitasPorProceso;
    var parametros = {
          opcion:4 ,
          codigoProceso:""
    };
    
    
    var xhr = $.ajax({
                    url:url,
                    dataType:"json",
                    cache:false,
                    data:parametros,
                    type:"POST",
                    global :false
                });
                
    xhr.done(function(response){
        
      if(response != null){         
      
            var contador = response.count;        
            if(contador > 0){
                contador = contador<10?"0"+contador:contador;
               var $divAlert =  $("#notificacion");
               $divAlert.show();

               $divAlert.html(contador);            
              /*  Tinycon.setOptions({
                    width: 10,
                    height: 9,
                    font: '10px arial',
                    colour: '#ffffff',
                    background: '#870000',
                    fallback: true
                });

                Tinycon.setBubble(parseInt(contador));*/
            }
        }
        
        
        
    });
    
}
