
$(document).on("ready", inicio);

function inicio(){
 

    //Se inicializan los botones
    
       pntlla.iniciar();

       $("#guardar").on("click",enviarFormulario);
       $("#consultar").on("click",generarGrilla);
       $("#modificar").on("click",actualizarRegistro);
       $("#limpiar").on("click",limpiarFormulario);

       campo.lista.crear("tipoInforme",servlet.consultar.tiposInformeVertimientos,{opcion:3});
       
       
      $("#div_documentos_requeridos").kendoValidator();
      
}

/*
 * Función envia al backend
 * @retrun vacio
 */

function enviarFormulario(){
    
    var validador = $("#div_documentos_requeridos").data("kendoValidator");
    var status     = $(".status");

		if (validador.validate()) {  
                   
                        guardarRegistro();
                        
                } else {
                 
                }

}

function obtenerParametros(){
    
   var parametros = {                                                        
                            codigo : campo.obtener("codigo"),
                            descripcion : campo.obtener("descripcion"),
                            tipoInforme: campo.obtener("tipoInforme")
                      };
                      
    
    return parametros;
}


function guardarRegistro(){
    
     var url = servlet.insertar.documentoRequerido;
     
     
     var parametros = obtenerParametros();
                           
                           
     var xhr = $.ajax(
              {
                  type:"POST",
                  data: parametros,
                  url: url,
                  cache :false                 
              }
           );
   
     xhr.done(function(data){
                      alert("El registro ha sido almacenado exitosamente.");
                      limpiarFormulario();
                      generarGrilla();
      });
    
}

function limpiarFormulario(){
            
      
    campo.limpiar("descripcion");
    campo.limpiar("tipoInforme");
    campo.limpiar("codigo");
    
}


function actualizarRegistro(){
  
      var parametros = obtenerParametros();
  
                     
     var url = servlet.actualizar.documentoRequerido;
     
     elmto.deshabilitar('modificar');
     
     var xhr = $.ajax(
                    {
                        type:"POST",
                        data: parametros,
                        url: url,
                        cache :false
                    }
                 );
         
    xhr.done( function(data){
                alert("El registro ha sido actualizado exitosamente.");
                limpiarFormulario();
                elmto.habilitar('modificar');
                pntlla.iniciar();
                generarGrilla();
                
            });
}

function mostrarRegistro(parametros){
    
     
    var url = servlet.consultar.documentoRequerido;
    var xhr =  $.ajax(
              {
                  type:"POST",
                  data: parametros,
                  url: url,
                  cache :false
              }
    );
    
     xhr.done(function(data){        
         
            campo.asignar("descripcion",data.descripcion);
            campo.asignar("tipoInforme",data.codTipoInforme);
            campo.asignar("codigo",data.codigo); 
            pntlla.iniciar();
            
     });
    
}

/*
 * Permite en eliminar un registro de la BD
 * @retrun vacio
 */

function eliminarRegistro(parametros){
    
    var url = servlet.eliminar.documentoRequerido;
    
    var grid = $("#grillaDocumentosRequeridos").data("kendoGrid");
    
     if(confirm("¿Esta seguro que desea eliminar este registro?")){   
                var xhr =  $.ajax(
                            {
                                type:"POST",
                                data: parametros,
                                url: url,
                                cache :false
                            });

                xhr.done(function(data){                        

                                    if(data[0].error ==  0){
                                        grid.cancelChanges();  
                                        alert("El campo no puede ser eliminado, por que ya esta asignado en el sistema");
                                    }

                });
                
                xhr.fail(function(data){ 


               });
               
    }
    
}

function generarGrilla(){

    var url = servlet.consultar.documentoRequerido; 
    
    var grilla = $("#grillaDocumentosRequeridos").data('kendoGrid'); 
    
    if(grilla != undefined){
       grilla.destroy();
    }
    
    
    var dataSource = new kendo.data.DataSource(
                        
                        {                             
                            transport: {
                                    read: {
                                        url:url,
                                        data:{opcion:1,
                                            descripcion : campo.obtener("descripcion"),
                                            tipoInforme: campo.obtener("tipoInforme")
                                        }, 
                                        dataType: "json",
                                        type:"POST",
                                        cache:false
                                    }
                                },
                                batch: true,
                                pageSize: 20,
                                schema: {
                                        model: {
                                          id: "codigo",
                                          fields: {
                                                
                                                codigo : {editable : false},
                                                descripcion : {editable : false},
                                                tipoInforme:{editable:false},
                                                codTipoInforme:{editable:false}
                                                
                                                
                                            }
                                        }
                                }
                                
                       }
            
            );

    $("#grillaDocumentosRequeridos").kendoGrid({
                        height: 430,
                        width:1000,
                        autoSync: true,
                       
                        dataSource: dataSource,                        
                        columns:
                         [
                            {field : "descripcion" , title : "Descripción" , width: "170px"},                           
                            {field : "tipoInforme" , title : "Tipo de Informe" , width: "170px"}, 
                            { command:  [ //Botones de la ultima columna
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


