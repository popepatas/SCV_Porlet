
$(document).on("ready", inicio);

function inicio(){
 

    //Se inicializan los botones
     pntlla.iniciar();
    
    //
      $("#consultar").on("click",generarGrilla);

    //Se crea un campo numerico
 	$("#codigoCiiu").kendoNumericTextBox({
 		format: "#",
 		min: 0
 		
 	});

    //Inicializa el validador
	$("#div_actividad_economica").kendoValidator();

    $("#guardar").click(enviarFormulario);
    $("#modificar").click(actualizarRegistro);
    $("#limpiar").on("click",limpiarFormulario);

    

}

/*
 * Función envia al backend
 * @retrun vacio
 */

function enviarFormulario(){
    
    var validador = $("#div_actividad_economica").data("kendoValidator");
    var status     = $(".status");

		 if (validador.validate()) {
                             
                        var parametros = {                            
                            codigoCiiu: campo.obtener("codigoCiiu"),
                            descripcion: campo.obtener("descripcion")
                        };
                        
                        guardarRegistro(parametros);
                     
                } else {
                   
                }

}

function guardarRegistro(parametros){
    
     var url = "/SCV_Portlet/InsertarActEconomica";
     
   var xhrEconomica =  $.ajax(
                            {
                                type:"GET",
                                data: parametros,
                                url: url,
                                cache :false,
                                success: function(data){

                                }
                            }
                         );
   
 
    xhrEconomica.done(function(data,status,xhr){
        
            if(data.error == 1){
                      alert("El registro ha sido almacenado exitosamente.");
                      limpiarFormulario();
                      generarGrilla();
                      elmto.habilitar('modificar');

              }else if(data.error == 2){
                   alert("Esta Ingresando una descripción que ya existe."); 
                   elmto.habilitar('modificar');

              }else if(data.error == 3){
                   alert("Esta Ingresando un CIIU que ya existe."); 
                   elmto.habilitar('modificar');

              }
        
    });
        
    
}


function limpiarFormulario(){
    
    campo.limpiar('descripcion');
    campo.limpiar('codigo');
    campo.kendo.NumericTextBox('codigoCiiu').value("");
    
}


function actualizarRegistro(){
    var parametros = {
                        codigo: campo.obtener("codigo"),
                        codigoCiiu: campo.obtener("codigoCiiu"),
                        descripcion: campo.obtener("descripcion")
                     };
                     
     var url = "/SCV_Portlet/ActualizarActEconomica";
     elmto.deshabilitar('modificar');
     $.ajax(
              {
                  type:"GET",
                  data: parametros,
                  url: url,
                  cache :false,
                  success: function(data){
                    
                          if(data.error == 1){
                              
                                  alert("El registro ha sido actualizado exitosamente.");
                                    limpiarFormulario();   
                                    elmto.habilitar('modificar');
                                   generarGrilla();
                                   pntlla.iniciar();                                   

                          }else if(data.error == 2){
                               alert("Esta Ingresando una descripción que ya existe."); 
                                elmto.habilitar('modificar');

                          }else if(data.error == 3){
                               alert("Esta Ingresando un CIIU que ya existe."); 
                               elmto.habilitar('modificar');

                          }
                      
                  }
              }
           );
    
    
}


function generarGrilla(){

    var urlBase = "/SCV_Portlet/"; 
    
    var grilla = $("#grillaActEconomica").data('kendoGrid'); 
    
    if(grilla != undefined){
       grilla.destroy();
    }
    
    var dataSource = new kendo.data.DataSource(
                        
                        {                             
                            transport: {
                                    read: {
                                        url:urlBase+"SeleccionarActEconomica?opcion=1",
                                        data:{codigoCiiu:campo.obtener("codigoCiiu"),
                                              descripcion: campo.obtener("descripcion")}, 
                                        dataType: "json",
                                        cache:false
                                    }
                                },
                                batch: true,
                                pageSize: 30,
                                schema: {
                                        model: {
                                          id: "codigo",
                                          fields: {
                                             codigo: { editable: false, nullable: true},
                                             descripcion: { editable: false },
                                             codigo_ciiu: { editable: false }                                         
                                            }
                                        }
                                }
                                
                       }
            
            );

    $("#grillaActEconomica").kendoGrid({
                        
                        width:800,
                        autoSync: true,
                       
                        dataSource: dataSource,                        
                        columns:
                         [
                            { field: "codigo_ciiu", title:"Código CIIU", width: "100px" },
                            { field: "descripcion", title: "Descripción", width: "200px" },                            
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
                              width: "180px" 
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
    
     var url = "/SCV_Portlet/SeleccionarActEconomica";
    
      $.ajax(
              {
                  type:"POST",
                  data: parametros,
                  url: url,
                  cache :false,
                  success: function(data){
                    
                  var codigo_ciiu = $("#codigoCiiu").data("kendoNumericTextBox");
                      codigo_ciiu.value(data.codigo_ciiu) ;                       
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
    
    var url = "/SCV_Portlet/EliminarActEconomica";
    var grid = $("#grillaActEconomica").data("kendoGrid");
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
