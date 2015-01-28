
$(document).on("ready", inicio);

function inicio(){
 	
    //Se inicializan los botones
    pntlla.iniciar();

    //Se inicializa el validador
    $("#div_unidades_medidas").kendoValidator();

    $("#guardar").on("click",enviarFormulario);
    
    $("#modificar").on("click",actualizarRegistro);
    
    $("#consultar").on("click",generarGrilla);
    
    $("#limpiar").on("click",limpiarFormulario);

}

function enviarFormulario(){

	var validador = $("#div_unidades_medidas").data("kendoValidator");
        var status     = $(".status");

		 if (validador.validate()) {
                        
                        var parametros = {                                                        
                            descripcion: campo.obtener("descripcion")
                        };
                        
                        guardarRegistro(parametros);
                        
                        
                } else {
                 
                }

}


function guardarRegistro(parametros){
    
     var url = servlet.insertar.undMedida;
     
     $.ajax(
              {
                  type:"GET",
                  data: parametros,
                  url: url,
                  cache :false,
                  success: function(data){
                      
                      resultadoOperacion(data,"El registro ha sido almacenado exitosamente.");
                    
                  }
              }
           );
    
}

function limpiarFormulario(){
    
    campo.limpiar('descripcion');
    campo.limpiar('codigo');
    
    
}


function actualizarRegistro(){
    var parametros = {
                        codigo: campo.obtener("codigo"),                        
                        descripcion: campo.obtener("descripcion")
                     };
                     
     var url = servlet.actualizar.undMedida;
     elmto.deshabilitar('modificar');
     $.ajax(
              {
                  type:"GET",
                  data: parametros,
                  url: url,
                  cache :false,
                  success: function(data){
                       resultadoOperacion(data,"El registro ha sido actualizado exitosamente.");
                      
                  }
              }
           );
    
    
}


function resultadoOperacion(data,texto){
    
      if(data.error == 1){
                alert(texto);
                limpiarFormulario();
                pntlla.iniciar();
                elmto.habilitar('modificar');
                generarGrilla();

            }else if(data.error == 2){

               alert("La descripción Ingresada ya ha sido ingresada.");
                 elmto.habilitar('modificar');

            }else if(data.error == 0){

                alert("Hubo un error Inesperado.");
                 elmto.habilitar('modificar');
            }
}

function generarGrilla(){

        var url = servlet.consultar.undMedida  + "?opcion=1" ; 

        var grilla = $("#grillaUnidadMedida").data('kendoGrid'); 

        if(grilla != undefined){
           grilla.destroy();
        }
    
    // Sirver para establecer los parametros que se van a enviar o recibir al backend
    var dataSource = new kendo.data.DataSource(
                        
                        {    
                            //Se establece a donde y que sele va a enviar al backend
                            transport: {
                                    read: {
                                        url:url,
                                        data:{descripcion: campo.obtener("descripcion") }, 
                                        dataType: "json",
                                        cache:false
                                    }
                                },
                                batch: true,
                                pageSize: 20,
                                // que es lo que se va a recibir del backend
                                schema: {
                                        model: {
                                          id: "codigo",
                                          fields: {
                                             codigo: { editable: false, nullable: true},
                                             descripcion: { editable: false }
                                                                                   
                                            }
                                        }
                                }
                                
                       }
            
            );

    $("#grillaUnidadMedida").kendoGrid({
                        height: 430,
                        width:800,
                        autoSync: true,
                       
                        dataSource: dataSource,                        
                        columns:
                         [
                            
                            { field: "descripcion", title: "Descripción", width: "160px" },
                           
                            { command:  [//Botones de la ultima columna
                                          { //Boton editar
                                             name:"edit",
                                             text:"Editar",
                                             click: function(e){
                                                 
                                                 // Se obtine todo el tr que se va a editar          
                                                  var tr = $(e.target).closest("tr"); 
                                                  var data = this.dataItem(tr); 
                                                
                                                  var parametros = {
                                                       codigo : data.codigo,
                                                       opcion : 2
                                                  };
                                                     mostrarRegistro(parametros);  
                                             }
                                          },
                                          {//Boton Eliminar 
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
                        //Funcion eliminar
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
    
     var url = servlet.consultar.undMedida;
    
      $.ajax(
              {
                  type:"POST",
                  data: parametros,
                  url: url,
                  cache :false,
                  success: function(data){                    
                                       
                      campo.asignar('codigo',data.codigo);
                      campo.asignar('descripcion',data.descripcion);
                      
                      pntlla.iniciar() ; 
                  }
              }
           );
    
}

/*
 * Permite en eliminar un registro de la BD
 * @retrun vacio
 */

function eliminarRegistro(parametros){
    
    var url = servlet.eliminar.undMedida;
    //Te trae el datatype
    var grid = $("#grillaUnidadMedida").data("kendoGrid");
    
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