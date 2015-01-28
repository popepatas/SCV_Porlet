
$(document).on("ready", inicio);

function inicio(){
 

    //Se inicializan los botones
    pntlla.iniciar();

 	
    //Inicializa el validador
	$("#div_tipos_paramfiscoquim").kendoValidator();
        $("#guardar").click(enviarFormulario);
        $("#modificar").click(actualizarRegistro);
        $("#consultar").click(generarGrilla);

    

}

/*
 * Función envia al backend
 * @retrun vacio
 */

function enviarFormulario(){

	var validador = $("#div_tipos_paramfiscoquim").data("kendoValidator");
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
    
     var url = servlet.insertar.tipoParmetros;
     
     $.ajax(
              {
                  type:"GET",
                  data: parametros,
                  url: url,
                  cache :false,
                  success: function(data){
                      alert(msg.guardar.exito);
                      limpiarFormulario();
                      generarGrilla();
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
                     
     var url = servlet.actualizar.tipoParmetros;
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
                      pntlla.iniciar();
                      elmto.habilitar('modificar');
                      generarGrilla();
                  }
              }
           );
    
    
}


function generarGrilla(){

    var url = servlet.consultar.tipoParmetros  + "?opcion=1" ; 
    
    var dataSource = new kendo.data.DataSource(
                        
                        {                             
                            transport: {
                                    read: {
                                        url:url,
                                        data:{descripcion: campo.obtener("descripcion")}, 
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
                                             descripcion: { editable: false }
                                                                                   
                                            }
                                        }
                                }
                                
                       }
            
            );

    $("#grillaTiposParamFisicoquimicos").kendoGrid({
                        height: 430,
                        width:800,
                        autoSync: true,
                       
                        dataSource: dataSource,                        
                        columns:
                         [
                            
                            { field: "descripcion", title: "Descripción", width: "160px" },
                           
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

function mostrarRegistro(parametros){
    
     var url = servlet.consultar.tipoParmetros;
    
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
    
    var url = servlet.eliminar.tipoParmetros;
    
    var grid = $("#grillaTiposParamFisicoquimicos").data("kendoGrid");
     if(confirm("¿Esta seguro que desea eliminar este registro?")){   
        $.ajax(
                {
                    type:"POST",
                    data: parametros,
                    url: url,
                    cache :false,
                    success: function(data){
                        console.log(data);

                        if(data[0].error ==  0){
                            grid.cancelChanges();  
                            alert("El campo no puede ser eliminado, por que ya esta asignado en el sistema");
                        }

                    }
                }
             );
     }else{
         grid.cancelChanges();  
     }
   
   
}