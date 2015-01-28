$(document).on("ready", inicio);

function inicio(){
 	
   
    //Se inicializa el validador
    $("#div_tiempo_backup").kendoValidator();

    $("#modificar").click(enviarFormulario);
    
    
    
    mostrarTiempo();
 

}

function enviarFormulario(){

	var validador = $("#div_tiempo_backup").data("kendoValidator");
        var status     = $(".status");

		 if (validador.validate()) {
                        
                        actualizarRegistro();
                        
                        
                } else {
                 
                }

}

function actualizarRegistro(){
                         
     var url = servlet.actualizar.tiempoBackup;
     
     var parametros = {
                        codigo: campo.obtener("codigo"),                        
                        tiempo: campo.obtener("time")
                     };
     
     
     elmto.deshabilitar('modificar');
     $.ajax(
              {
                  type:"POST",
                  data: parametros,
                  url: url,
                  cache :false,
                  success: function(data){
                      
                      alert("El registro ha sido actualizado exitosamente.");
                      elmto.habilitar('modificar');
                      
                  }
              }
           );
    
    
}



function mostrarTiempo(parametros){
    
     var url = servlet.consultar.tiempoBackup;     
    
    var parametros  = {
                opcion : 2,
                codigo : 1        
    }
    
      $.ajax(
              {
                  type:"POST",
                  data: parametros,
                  url: url,
                  cache :false,
                  success: function(data){                  
                      $("#time").val(data.tiempo);
                      campo.asignar('codigo', data.codigo);
                  }
              }
           );
    
}
