

function inicioCrearVisita(){
    
    validacionesKendo();
    
    urlTec = servlet.consultar.tecnicos;
    campo.lista.crear("tecnicos",urlTec,{opcion:1});
    
    urlMot = servlet.consultar.motivosVisitas;
    campo.lista.crear("motivos",urlMot, {opcion:1});
    
    
    $("#fechaVisita").kendoDatePicker({
         format:"dd/MM/yyyy"
    });
    
    
    $("#limpiarCrearVisitas").on("click",limpiarFormularioCrearVisita);
    $("#cancelarCrearVisitas").on("click", cancelarCrearVisita);
    
    $("#crearVisita").on("click",enviarFormularioCrearVisita);
    
     $("#div_crear_visita").kendoValidator({
        
            rules: {
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

function limpiarFormularioCrearVisita(){
    
    
    campo.limpiar("motivos");
    campo.limpiar("observaciones");
    campo.limpiar("tecnicos");
    
    
}

function cancelarCrearVisita(){
    $("#modalBox2").data("kendoWindow").close();
}

function enviarFormularioCrearVisita(){
    
    var validador = $("#div_crear_visita").data("kendoValidator");
    var status     = $(".status");
     
    guardarRegistroCrearVisita();


}
function obtenerParametroCrearVisita(){
    
    var parametros = {                            
        tecnicos: campo.obtener("tecnicos"),
        motivos: campo.obtener("motivos"),
        proceso: campo.obtener("codigoProceso"),
        observaciones: campo.obtener("observaciones"),
        fechaVisita: campo.obtener("fechaVisita"),
        tipoVisita:campo.obtener("tipoVisita")
    };
                      
    return parametros;
}

function guardarRegistroCrearVisita(){
    
       var url = servlet.insertar.visitas;    
       var parametros = obtenerParametroCrearVisita();                 

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
                          var resultado = data[0].resultado;
                           
                           switch(resultado){
                                case 1 : texto = 'La visita fue registrada exitosamente.';
                                         onCloseCrearVisitas();
                                break;
                                
                               case 2 : texto ='El proceso ingresado no existe.';
                                break;
                              
                           }
                            
                            
                        }
                         
                         $("#calendar").data("fullCalendar").refetchEvents();
                         alert(texto);                         
                         cancelarCrearVisita();
                         
                     });
    
}

var onCloseCrearVisitas = function(){
    
    var grilla = $("#grillaVisitas").data('kendoGrid'); 
    mostrarInformacionInfoGenral();
    if(grilla != undefined){
      generarGrillaVisitas();
    }
    
    
};