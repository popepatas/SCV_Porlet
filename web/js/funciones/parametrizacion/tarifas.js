
$(document).on("ready", inicio);

function inicio(){
 	
   
    //Se inicializa el validador
    $("#div_tarifas").kendoValidator();

    $("#modificar").click(enviarFormulario);
    
    
    
    mostrarDBO();
    mostrarSST();
 

}

function enviarFormulario(){

	var validador = $("#div_tarifas").data("kendoValidator");
        var status     = $(".status");

		 if (validador.validate()) {
                        
                        actualizarRegistro();
                        
                        
                } else {

                }

}

function actualizarRegistro(){
                         
     var url = servlet.actualizar.tarifas;
     
     var parametros = {
                        sst: campo.obtener("sst"),                        
                        dbo: campo.obtener("dbo")
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



function mostrarDBO(parametros){
    
     var url = servlet.consultar.tarifas;     
    
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
                                       
                      campo.asignar('dbo',data.valor);
                     
                  }
              }
           );
    
}


function mostrarSST(parametros){
    
     var url = servlet.consultar.tarifas;     
    
    var parametros  = {
                opcion : 2,
                codigo : 2        
    }
    
      $.ajax(
              {
                  type:"POST",
                  data: parametros,
                  url: url,
                  cache :false,
                  success: function(data){                  
                                       
                      campo.asignar('sst',data.valor);
                     
                  }
              }
           );
    
}