

var inicioActualizarVisita = function(parametros){
     
    $("#limpiarActVisitasAct").on("click",limpiarFormularioActVisita);
    $("#cancelarActVisitasAct").on("click", cancelarActVisita);
     
    urlTec = servlet.consultar.tecnicos;
    campo.lista.crear("tecnicosAct",urlTec,{opcion:1});
    
    urlMot = servlet.consultar.motivosVisitas;
    campo.lista.crear("motivosAct",urlMot, {opcion:1});
    
    var actualizarRegistroVisita = function(){

    var validador = $("#div_actualizar_visita").data("kendoValidator");
    var status     = $(".status");
   
     if (validador.validate()) {
     

                var parametros = {
                          tecnicos: campo.obtener("tecnicosAct"),
                          motivos: campo.obtener("motivosAct"),
                          observaciones: campo.obtener("observacionesAct"),
                          fechaVisita: campo.obtener("fechaVisitaAct"),
                          codigo: campo.obtener("codigo")
                                 };

                 var url = servlet.actualizar.visitas;
                 $.ajax(
                          {
                              type:"GET",
                              data: parametros,
                              url: url,
                              cache :false,
                              success: function(data){
                                  alert("El registro ha sido actualizado exitosamente.");
                                  onCloseCrearVisitas();
                                  cancelarActVisita();
                              }
                          }
                       );
               
               
              
        }  
    
    };

    
    function mostrarRegistroVisita(parametros){

         var url = "/SCV_Portlet/SeleccionarVisitas?opcion=2";
         
          $.ajax(
                  {
                      type:"POST",
                      data: parametros,
                      url: url,
                      cache :false,
                      success: function(data){
                          if(data[0].estado === "6" || data[0].estado === "4"){
                              $("#actualizarVisitaAct").hide();
                              $("#limpiarActVisitasAct").hide();
                              $('#tecnicosAct').prop('disabled', true);
                              $('#motivosAct').prop('disabled', true);
                              $('#observacionesAct').prop('disabled', true);
                              $('#fechaVisitaAct').prop('disabled', true);
                          }
                          campo.asignar("tecnicosAct", data[0].tecnico);
                          campo.asignar("motivosAct", data[0].motivo);
                          campo.asignar("observacionesAct", data[0].observaciones);
                          campo.asignar("codigo",data[0].codigo );
                          
                          //Manipulamos la fecha para dar con el formato correcto.
                          var fecha = new Date(data[0].fechaCita.substring(0,11));
                          var fechaFinal = fecha.getDate() +'/' + (fecha.getMonth() + 1) + '/' + fecha.getFullYear();
                          campo.asignar("fechaVisitaAct", fechaFinal);
                          
                      }
                  }
               );

    }
    
    function limpiarFormularioActVisita(){
    
        campo.limpiar("tecnicosAct");
        campo.limpiar("motivosAct");
        campo.limpiar("observacionesAct");
        campo.limpiar("fechaVisitaAct");
    }

    
    function cancelarActVisita(){
        $("#modalBox").data("kendoWindow").close();
    }
    
    $("#actualizarVisitaAct").on("click", actualizarRegistroVisita);
    
    $("#fechaVisitaAct").kendoDatePicker({
        format:"dd/MM/yyyy"
    });

    $("#div_actualizar_visita").kendoValidator({
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
    
    
    urlTec = servlet.consultar.tecnicos;
    var tcnco = campo.lista.crear("tecnicosAct",urlTec,{opcion:1});    
    
    urlMot = servlet.consultar.motivosVisitas;
    var mtvo = campo.lista.crear("motivosAct",urlMot, {opcion:1});
    
    $.when(mtvo, tcnco).done(function(){
        mostrarRegistroVisita(parametros);
    });
    
    
    
  
    
    
};

