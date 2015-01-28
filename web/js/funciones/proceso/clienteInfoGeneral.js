/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var mostrarInfoClienteProceso = function (codigo){
    
        var url = servlet.consultar.cliente;
        var parametros = {
                            codigo : codigo,
                            opcion : 2
                         };

        var xhr = $.ajax(
                       {
                           type:"POST",
                           data: parametros,
                           url: url,
                           cache :false
                       }
                    );
        //Cuando la información haya sido cargada
        xhr.done(function(response,status,xhr){
            
                var data = response;
                var xhrListaCiiu;

               
                xhrListaCiiu = campo.lista.crear("ciiu",servlet.consultar.actEconomica,{});
                xhrListaCiiu.done(function(){
                    campo.asignar("ciiu",data.actividadEconomica);
                    inicioClientesInfoGeneral();//Se inicializan las validaciones del formulario
                });


                campo.direccion.asignar("completaDireccion",data.direccion);
                campo.asignar("telefonoDos",data.telefono2);
                //campo.asignar("estadoVertimiento",data.estadoVertimiento);
                campo.asignar("razon",data.razonSocial);
                campo.asignar("representante",data.repLegal);
                campo.asignar("telefono",data.telefono);                            

                campo.asignar("emailSecundario",data.email2);
                campo.asignar("codigo",data.codigo);
                campo.asignar("comuna",data.comuna);
                campo.asignar("usoServicio",data.usoServicio);
                campo.asignar("email",data.email);
                campo.asignar("nit",data.nit);
                campo.asignar("barrio",data.barrio);                            

                campo.asignar("pagina",data.web);
                
           });
              
};

function obtenerParametrosInfoCliente(){
      var parametros = {                                                        
                            codigo: campo.obtener("codigo"), 
                            nit :campo.obtener("nit"),
                            razonSocial: campo.obtener("razon"),
                            ciiu: campo.obtener("ciiu"),                           
                            direccion: campo.direccion.obtener("completaDireccion"),
                            barrio: campo.obtener("barrio"),
                            comuna : campo.kendo.NumericTextBox("comuna").value(),
                            telefono : campo.kendo.NumericTextBox("telefono").value(),
                            telefono2 : campo.kendo.NumericTextBox("telefonoDos").value(),
                            usoServicio : campo.obtener("usoServicio"),
                            correo: campo.obtener("email"),
                            correo2: campo.obtener("emailSecundario"),
                            web: campo.obtener("pagina"),
                            representanteLegal:  campo.obtener("representante") ,
                            estadoUltVertimiento: 0
                            
                        };
         return parametros;
    
}

function inicioClientesInfoGeneral(){
 	
   
    
   //Se inicializa el validador
   $("#div_cliente").kendoValidator();

        
    $("#ciclo").kendoNumericTextBox({
        format: "#",
        min: 0
        
    });
    
    $("#nit").on("keypress", function(){
        
      return  campo.nit(event, this);
        
    });
    
    $("#sector").kendoNumericTextBox({
        format: "#",
        min: 0
        
    });
    
     $("#pozo").kendoNumericTextBox({
        format: "#",
        min: 0        
     });
    
     $("#consumo").kendoNumericTextBox({
        format: "#",
        min: 0        
     });
    
      $("#telefono").kendoNumericTextBox({
        format: "#",
        min: 0
        
      });
    
      $("#telefonoDos").kendoNumericTextBox({
        format: "#",
        min: 0
        
      });
    
       $("#comuna").kendoNumericTextBox({
        format: "#",
        min: 0
        
    });

    
     

}



function actualizarRegistroCliente(){
    var parametros =  obtenerParametrosInfoCliente();
                     
     var url = servlet.actualizar.cliente;     
     var xhrActualizar  = $.ajax(
                                    {
                                        type:"POST",
                                        data: parametros,
                                        url: url,
                                        cache :false
                                    }
                                 );
    
        return xhrActualizar;
    
}