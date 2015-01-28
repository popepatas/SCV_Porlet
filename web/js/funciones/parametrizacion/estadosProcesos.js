
$(document).on("ready", inicio);

function inicio(){
    
    //Inicializamos la pantalla
    
    
    //Inicializa el validador
       
    
    $("#div_estados_proceso").kendoValidator();

    $("#modificar").click(actualizarRegistro);
    $("#consultar").click(generarGrilla);
    
     elmto.deshabilitar("codigo");
}

/*
 * Función envia al backend
 * @retrun vacio
 */

function actualizarRegistro(){
     
    var validador = $("#div_estados_proceso").data("kendoValidator");
    var status     = $(".status");
    var cod = campo.obtener("codigo");
     
      if(cod != ""){
                if (validador.validate()) {

                           var parametros = {
                                               codigo: campo.obtener("codigo"),                        
                                               descripcion: campo.obtener("descripcion")
                                            };

                            var url = servlet.actualizar.estadosProcesos;
                            elmto.deshabilitar('modificar');
                            $.ajax(
                                     {
                                         type:"GET",
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
               }else{
                   alert("Debe editar algún estado del proceso.");
               }
}


function limpiarFormulario(){
    
    campo.limpiar('descripcion');
    campo.limpiar('codigo');
    
    
}

function generarGrilla(){

    var url = servlet.consultar.estadosProcesos  + "?opcion=1" ; 
    
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

    $("#grillaEstadosProceso").kendoGrid({
                        height: 430,
                        width:800,
                        autoSync: true,
                       
                        dataSource: dataSource,                        
                        columns:
                         [
                            
                           // { field: "codigo", title: "Codigo", width: "160px" },
                            { field: "descripcion", title: "Descripción", width: "160px" },
                            { field: "abreviatura", title: "Abreviatura", width: "160px" },
                            
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
                        }
                        
                    
                           
     });

}

function mostrarRegistro(parametros){
    
     var url = servlet.consultar.estadosProcesos;
    
      $.ajax(
              {
                  type:"POST",
                  data: parametros,
                  url: url,
                  cache :false,
                  success: function(data){                    
                      elmto.habilitar("codigo");               
                      campo.asignar('codigo',data.codigo);
                      campo.asignar('descripcion',data.descripcion);
                      
                      
                  }
              }
           );
    
}


