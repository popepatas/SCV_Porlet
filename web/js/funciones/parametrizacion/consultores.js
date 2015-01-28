
$(document).on("ready", inicio);

function inicio(){
 

    //Se inicializan los botones
    pntlla.iniciar();

    //Se crea un campo numerico
    $("#telefono1,#telefono2").kendoNumericTextBox({
            format: "#",
            min: 0

    });
 
   

    //Inicializa el validador
	$("#div_laboratorios_consultores").kendoValidator(
        {
            rules: {
               
                 validacionDireccion: function(obj){//Se crea una regla personalizada
                       return campo.direccion.validar(obj,false);
                 }
            }
        }
    );

    $("#guardar").on("click",enviarFormulario);
    $("#consultar").on("click",generarGrilla);
    $("#modificar").on("click",actualizarRegistro);
    $("#limpiar").on("click", limpiarFormulario);

    

}

/*
 * Función  que valida que la escritura no tenga espacios
 * @retrun vacio
 */

function validarEscritura(){
    return campo.sinEspacios(event,this,true);
}

/*
 * Función envia al backend
 * @retrun vacio
 */

function enviarFormulario(){

	var validador = $("#div_laboratorios_consultores").data("kendoValidator");
        var status     = $(".status");

               if (validador.validate()) {  
                   
                        guardarRegistro();
                        
                } else {
                 
                }

}


function guardarRegistro(){
    
     var url = servlet.insertar.consultores;
     
     var parametros = {                                                        
                            
                            nombre : campo.obtener("nombre"),
                            apellidos : campo.obtener("apellidos"),                            
                            direccion : campo.direccion.obtener("completaDireccion"),
                            telefono1 : campo.kendo.NumericTextBox("telefono1").value(),
                            telefono2 : campo.kendo.NumericTextBox("telefono2").value(),
                            identificacion : campo.obtener("identificacion"),
                            correo : campo.obtener("email")
                      };
     
     $.ajax(
              {
                  type:"POST",
                  data: parametros,
                  url: url,
                  cache :false,
                  success: function(data){
                      alert("El registro ha sido almacenado exitosamente.");
                      limpiarFormulario();
                      generarGrilla();
                  }
              }
           );
    
}

function limpiarFormulario(){
    
   
    campo.limpiar("nombre");
    campo.limpiar("apellidos");
    campo.direccion.limpiar("completaDireccion");    
    campo.kendo.NumericTextBox("telefono1").value("");
    campo.kendo.NumericTextBox("telefono2").value("");
    campo.limpiar("identificacion");
    campo.limpiar("email");   
    campo.limpiar('codigo');
    
   pntlla.iniciar();
    
    
}


function actualizarRegistro(){
    
       var parametros = {                                                        
                            
                            nombre : campo.obtener("nombre"),
                            apellidos : campo.obtener("apellidos"),                            
                            direccion : campo.direccion.obtener("completaDireccion"),
                            telefono1 : campo.kendo.NumericTextBox("telefono1").value(),
                            telefono2 : campo.kendo.NumericTextBox("telefono2").value(),
                            identificacion : campo.obtener("identificacion"),
                            correo : campo.obtener("email"),
                            codigo : campo.obtener("codigo")
                      };
                     
     var url = servlet.actualizar.consultores;
     
     elmto.deshabilitar('modificar');
     $.ajax(
              {
                  type:"POST",
                  data: parametros,
                  url: url,
                  cache :false,
                  success: function(data){
                      alert("El registro ha sido actualizado exitosamente.");
                      limpiarFormulario();                      
                      elmto.habilitar('modificar');
                      generarGrilla();
                  }
              }
           );
    
    
}


function mostrarRegistro(parametros){
    
     var url = servlet.consultar.consultores;
    
      $.ajax(
              {
                  type:"POST",
                  data: parametros,
                  url: url,
                  cache :false,
                  success: function(data){                    
                                       
                        campo.asignar("codigo",data.codigo);
                        campo.asignar("nombre",data.nombre);
                        campo.asignar("identificacion",data.identificacion);
                        campo.asignar("apellidos",data.apellidos);
                        campo.direccion.asignar("completaDireccion",data.direccion);
                        campo.kendo.NumericTextBox("telefono1").value(data.telefono1);
                        campo.kendo.NumericTextBox("telefono2").value(data.telefono2);
                        campo.asignar("email",data.correo);                                                
                        pntlla.iniciar(); 
                        
                  }
              }
           );
    
}

/*
 * Permite en eliminar un registro de la BD
 * @retrun vacio
 */

function eliminarRegistro(parametros){
    
    var url = servlet.eliminar.consultores;
    
    var grid = $("#grillaConsultores").data("kendoGrid");
     if(confirm("¿Esta seguro que desea eliminar este registro?")){   
        $.ajax(
                {
                    type:"POST",
                    data: parametros,
                    url: url,
                    cache :false,
                    success: function(data){
                        
                        if(data[0].error ==  0){
                            grid.cancelChanges();  
                            alert("El campo no puede ser eliminado, por que ya esta asignado en el sistema");
                        }

                    }
                }
             );
     }
 }
   



function generarGrilla(){

    var url = servlet.consultar.consultores; 
    
    var dataSource = new kendo.data.DataSource(
                        
                        {                             
                            transport: {
                                    read: {
                                        url:url,
                                        data:{opcion:1,
                                            nombre : campo.obtener("nombre"),
                                            apellidos : campo.obtener("apellidos"),                            
                                            direccion : campo.direccion.obtener("completaDireccion"),
                                            telefono1 : campo.kendo.NumericTextBox("telefono1").value(),
                                            telefono2 : campo.kendo.NumericTextBox("telefono2").value(),
                                            identificacion : campo.obtener("identificacion"),
                                            correo : campo.obtener("email")
                        },
                                        dataType: "json",
                                        type:'POST',
                                        cache:false
                                    }
                                },
                                batch: true,
                                pageSize: 20,
                                schema: {
                                       model: {
                                          id: "codigo",
                                          fields: {
                                                
                                                nombre : {editable : false},
                                                identificacion : {editable : false},
                                                direccion : {editable : false},
                                                telefono1 : {editable : false},
                                                telefono2 : {editable : false},
                                                correo : {editable : false},
                                                apellidos : {editable : false}
                                                
                                            }
                                        }
                                }
                                
                       }
            
            );

    $("#grillaConsultores").kendoGrid({
                        
                        width:800,
                        autoSync: true,
                       
                        dataSource: dataSource,                        
                        columns:
                         [
                            {field : "nombre" , title : "Nombre" , width: "160px"},
                            {field : "apellidos" , title : "Apellidos" , width: "170px"},
                            {field : "identificacion" , title : "Identificación" , width: "160px"},
                            {field : "direccion" , title : "Dirección" , width: "160px"},
                            {field : "telefono1" , title : "Telefono" , width: "160px"},
                            {field : "telefono2" , title : "Telefono 2" , width: "160px"},
                            {field : "correo" , title : "E-mail" , width: "160px"},                            
                           
                            { command:  [//Botones de la ultima columna
                                          { 
                                             name:"edit",
                                             text:"Editar",
                                             click: function(e){
                                                 
                                                  var tr = $(e.target).closest("tr"); // Se obtine todo el tr que se va a editar          
                                                  var data = this.dataItem(tr); 
                                                
                                                  var parametros = {
                                                       codigo : data.id,
                                                       opcion : 2
                                                  };
                                                     mostrarRegistro(parametros);  
                                             }
                                          },
                                          { 
                                            name:"destroy", 
                                            text:"Eliminar"                                           
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
                        remove: function(e){
                            
                                var codigo = e.model.id; 
                                var parametros = {
                                                    codigo :  codigo                                                        
                                                 };
                                                 
                                eliminarRegistro(parametros);                           

                        }
                    
                           
     });

}
