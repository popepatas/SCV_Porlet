
$(document).on("ready", inicio);

function inicio(){
 

    //Se inicializan los botones
    pntlla.iniciar();

 	
    //Inicializa el validador
    $("#div_Adicionar_Usuarios").kendoValidator();

    $("#guardar").on("click",enviarFormulario);
    $("#consultar").on("click",generarGrilla);
    $("#modificar").on("click",actualizarRegistro);
    $("#limpiar").on("click",limpiarFormulario);   
  
    campo.lista.crear("rol",servlet.consultar.roles ,"");
}

/*
 * Función envia al backend
 * @retrun vacio
 */

function enviarFormulario(){
    
    var validador  =  $("#div_Adicionar_Usuarios").data("kendoValidator");
    var status     = $(".status");

		if (validador.validate()) {
                        
                        guardarRegistro();
                     
                } 

}
function obtenerParametros(){
    
       var parametros = {                            
                            descripcion: campo.obtener("descripcion"),
                            rol : campo.obtener("rol"),
                            codigo: campo.obtener("codigo")
                        };
    return parametros;
}
function guardarRegistro(){
    
     var url = servlet.insertar.usuarios;
     var parametros = obtenerParametros();
     
    var xhr = $.ajax(
              {
                  type:"POST",
                  data: parametros,
                  url: url,
                  cache :false                 
              }
           );
     xhr.done(function(response,status,xhr){
         
              if(response.error == 1){
                      alert("El registro ha sido almacenado exitosamente.");
                      limpiarFormulario();
                      generarGrilla();
                      
               }else if(response.error == 2){
                    alert("El usuario que esta intentando ingresar ya existe.");
                    
               }else if(response.error == 0){
                    alert("El registro no puedo ser almacenado exitosamente.");
               }
                      
    });
    
}

function limpiarFormulario(){
    
    campo.limpiar('descripcion');
    campo.limpiar('codigo');
    campo.limpiar('rol');
    pntlla.iniciar();
    
   
}


function actualizarRegistro(){

    var validador = $("#div_Adicionar_Usuarios").data("kendoValidator");
    var status     = $(".status");
   
     if (validador.validate()) {
         
             var parametros = obtenerParametros();

             var url = servlet.actualizar.usuarios;
             
             elmto.deshabilitar('modificar');
             
             var xhr =   $.ajax(
                          {
                              type:"POST",
                              data: parametros,
                              url: url,
                              cache :false
                          }
                       );
               
               xhr.done( function(response,status,xhr){
                         if(response.error == 1){
                                  alert("El registro ha sido actualizado exitosamente.");
                                  limpiarFormulario();
                                 
                                  elmto.habilitar('modificar');
                                  pntlla.iniciar();
                                  generarGrilla();
                                  
                           }else if(response.error == 0){
                                alert("Se ha presentado un problema inesperado.");
                           }
                        });
        }  
    
}


function generarGrilla(){

    var url = servlet.consultar.usuarios;
    
    var dataSource = new kendo.data.DataSource(
                        
                        {                             
                            transport: {
                                    read: {
                                        url:url,
                                        data:{
                                            descripcion: campo.obtener("descripcion")
                                        }, 
                                        dataType: "json",
                                        cache:false
                                    }
                                },
                                batch: true,
                                pageSize: 20,
                                schema: {
                                        model: {
                                          id: "codigo",
                                          fields: {
                                             codigo: { editable: false, nullable: true},
                                             usuario: { editable: false },
                                             rol:{editable:false},
                                             descripcion: {editable:false}
                                            }
                                        }
                                }
                                
                       }
            
            );

    $("#grillaAdicionarUsuario").kendoGrid({
                        height: 430,
                        width:800,
                        autoSync: true,
                       
                        dataSource: dataSource,                        
                        columns:
                         [
                            
                            { field: "descripcion", title: "Descripción", width: "160px" },
                            { field: "rol", title: "Rol", width: "160px" },
                            { command:  [//Botones de la ultima columna
                                          { 
                                             name:"edit",
                                             text:"Editar",
                                             click: function(e){
                                                 
                                                  var tr = $(e.target).closest("tr"); // Se obtine todo el tr que se va a editar          
                                                  var data = this.dataItem(tr); 
                                                
                                                  var parametros = {
                                                       codigo : data.id
                                                  };
                                                     mostrarRegistro(parametros);  
                                             }
                                          },
                                          { 
                                            name:"destroy", 
                                            text:"Eliminar",
                                            click:  function(){
                                                  
                                                  var tr = $(e.target).closest("tr"); // Se obtine todo el tr que se va a editar          
                                                  var data = this.dataItem(tr); 
                                                  
                                                    var parametros = {
                                                            codigo : data.id
                                                        };

                                                     eliminarRegistro(parametros,tr);
                                                 }
                                          }                                          
                                        ],
                              title: "", 
                              width: "200px" 
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

function mostrarRegistro(parametros){
    
     var url = servlet.consultar.usuarios;
    
    var xhr = $.ajax(
                    {
                        type:"POST",
                        data: parametros,
                        url: url,
                        cache :false
                    }
                 );
         xhr.done(function(response,status,xhr){
                                          
                      campo.asignar('codigo',response[0].codigo);
                      campo.asignar('descripcion',response[0].descripcion);
                      campo.asignar('rol',response[0].idRol);                      
                      pntlla.iniciar() ; 
                  });
    
}

/*
 * Permite en eliminar un registro de la BD
 * @retrun vacio
 */

function eliminarRegistro(parametros){
    
    var url = servlet.eliminar.usuarios;
    
    var grid = $("#grillaAdicionarUsuario").data("kendoGrid");
     if(confirm("¿Esta seguro que desea eliminar este registro?")){   
          var xhr =  $.ajax(
                        {
                            type:"POST",
                            data: parametros,
                            url: url,
                            cache :false
                        }
                     );
           xhr.done(function(data){
                        
                if(data.error ==  0){
                    grid.cancelChanges();  
                    alert("El campo no puede ser eliminado, por que ya esta asignado en el sistema");
                }
           });
     }
   
   
}