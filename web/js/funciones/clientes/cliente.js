
//$(document).on("ready", inicio);

function inicioClientes(){
 	
    //Se inicializan los botones
     
     $("#ciiu").select2();

    if(campo.obtener("ciiu") == ''){
      campo.lista.crear("ciiu",servlet.consultar.actEconomica,{});  
     }
   //Se inicializa el validador
   validacionesKendo();
   $("#formclientes").kendoValidator({
            rules: {
               
                 validacionDireccion: function(obj){//Se crea una regla personalizada
                       return campo.direccion.validar(obj);
                 }
             },
             messages:{
                 
                required:"Ingrese un valor para este campo",          
                date:"Fecha Ingresada no es valida. ",
                validarListaParam: "No debe seleccionar parámetros fisicoquímicos repetidos"
            
                 
             }
    });

    $("#guardar").on("click",enviarFormulario);    
    $("#modificar").on("click",actualizarRegistro);
    $("#cancelar").on("click",cerrarKendoModalBoxCliente);
        
   
    
    /*$("#nit").on("keypress", function(){
        
      return  campo.nit(event, this);
        
    });*/
  
    
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


function cargarFormCrearProceso(){
      
    
    var nitCliente = campo.obtener("nit");
    
    $("#modalBox2").load("../proceso/CrearProceso.jsp",function(response,status,xhrFormCrearProceso){
            
            xhrFormCrearProceso.done(function(){
                
                inicioCrearProceso();        
                campo.asignar("nitProceso",nitCliente);
                elmto.deshabilitar("nitProceso");
                cerrarKendoModalBoxCliente();
                abrirModalBoxCrearProceso();
                
            });
            
            
            xhrFormCrearProceso.fail(function(){
                
              alert("Falló la carga de la pantalla de proceso");
                
            });
        
    });
}


function cerrarKendoModalBoxCliente(){
    $("#modalBox").data("kendoWindow").close();
    
}

function obtenerParametros(){
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
function enviarFormulario(){

	var validador = $("#div_cliente").data("kendoValidator");
        var status     = $(".status");

		 if (validador.validate()) {
                         
                        guardarRegistro();
                } else {
                 
                }

}


function guardarRegistro(){
    
     var url = servlet.insertar.cliente;
     var parametros = obtenerParametros();
     
     var xhrInsertCliente = $.ajax(
                             {
                                 type:"POST",
                                 data: parametros,
                                 url: url,
                                 cache :false
                             }
                           );
         
       xhrInsertCliente.done(function(data,status, xhr){
           
                if(data.error == 1){

                   alert("El registro ha sido almacenado exitosamente.");                      
                   generarGrilla();
                   cargarFormCrearProceso();
                   limpiarFormularioCrearCliente();

               }else if(data.error == 2){
                   alert("El NIT registrado ya ha sido almacenado.");
                   
               }else{
                   alert("Hubo un problema al almacenar la información."); 
               }
                      
                

       });
    
}

function limpiarFormularioCrearCliente(){
    
    
    campo.limpiar('codigo');
    campo.limpiar("nit");       
    campo.limpiar("razon");
    campo.limpiar("ciiu");  
    var ciuuSelect = $("#ciiu").data("select2");
        ciuuSelect.val("");
    campo.direccion.limpiar("completaDireccion");   
    campo.limpiar("barrio");
    campo.kendo.NumericTextBox("comuna").value("");
    campo.kendo.NumericTextBox("telefono").value("");
    campo.kendo.NumericTextBox("telefonoDos").value("");
    campo.limpiar("usoServicio");
    campo.limpiar("email");
    campo.limpiar("emailSecundario");
    campo.limpiar("pagina");
    campo.limpiar("representante");
    
}


function actualizarRegistro(){
    var parametros =  obtenerParametros();
                     
     var url = servlet.actualizar.cliente;
     elmto.deshabilitar('modificar');
     var xhrActualizar  = $.ajax(
                                    {
                                        type:"POST",
                                        data: parametros,
                                        url: url,
                                        cache :false
                                    }
                                 );
    
        xhrActualizar.done(function(data){
            
              if(data.error == 1){

                      alert("El registro ha sido actualizado exitosamente.");
                      limpiarFormulario();
                      pntlla.iniciar();
                      elmto.habilitar('modificar');
                      generarGrilla();
                      cerrarKendoModalBoxCliente();

               }else if(data.error == 2){
                   alert("El NIT registrado ya ha sido almacenado.");
                   
               }else{
                   alert("Hubo un problema al almacenar la información."); 
               }
            
                    
        });
    
}

