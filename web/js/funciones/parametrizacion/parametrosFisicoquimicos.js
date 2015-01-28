
$(document).on("ready", inicio);

function inicio(){
 

    //Se inicializan los botones
    pntlla.iniciar();

 	
    //Inicializa el validador
    $("#div_parametros_fisicoquimicos").kendoValidator();

    $("#guardar").on("click",enviarFormulario);
    
    $("#modificar").on("click",actualizarRegistro);
    
    $("#consultar").on("click",generarGrilla);
    
    $("#limpiar").on("click",limpiarFormulario);
    
    
      //Inizializa lista
    var urlUndMedida = servlet.consultar.undMedida;    
    campo.lista.crear("unidades",urlUndMedida,{opcion:1});
    
    var urlTipoParam = servlet.consultar.tipoParmetros; 
    campo.lista.crear("tipoParametro",urlTipoParam,{opcion:1});
   
    

}

/*
 * Función envia al backend
 * @retrun vacio
 */

function enviarFormulario(){

	var validador = $("#div_parametros_fisicoquimicos").data("kendoValidator");
        var status     = $(".status");

		 if (validador.validate()) {
                            var parametros = {                                                        
                            descripcion: campo.obtener("descripcion"),
                            unidades: campo.obtener("unidades"),
                            tipoParametro: campo.obtener("tipoParametro")
                        };
                        guardarRegistro(parametros);
                } else {
                   
                }

}



function guardarRegistro(parametros){
    
     var url = servlet.insertar.paramFisQuim;
     
    var xhr = $.ajax(
                {
                    type:"GET",
                    data: parametros,
                    url: url,
                    cache :false
                }
             );
     

    xhr.done(function(data){
        
           if(data.error == 1){
             alert("El registro ha sido almacenado exitosamente.");
             limpiarFormulario();
             generarGrilla();
           }else if(data.error == 3){
               
               alert("El parámetro que esta intentando ingresar ya se encuentra registrado en el sistema.");
               
           }
           
    });
    
}

function limpiarFormulario(){
    
    campo.limpiar('descripcion');
    campo.limpiar('codigo');
    campo.limpiar("unidades");
    campo.limpiar("tipoParametro");
    
    pntlla.iniciar();
}


function actualizarRegistro(){
    
    var parametros = {
                        codigo: campo.obtener("codigo"),                        
                        descripcion: campo.obtener("descripcion"),
                        unidades: campo.obtener("unidades"),
                        tipoParametro: campo.obtener("tipoParametro")
                     };
                     
     var url = servlet.actualizar.paramFisQuim;
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


function generarGrilla(){

    var url = servlet.consultar.paramFisQuim  + "?opcion=1" ; 
    
    
    var grilla = $("#grillaParamFisicoquimicos").data('kendoGrid'); 
    
    if(grilla != undefined){
       grilla.destroy();
    }
    
    var dataSource = new kendo.data.DataSource(
                        
                        {                             
                            transport: {
                                    read: {
                                            url:url,
                                            data:{
                                                descripcion: campo.obtener("descripcion"),
                                                unidades: campo.obtener("unidades"),
                                                tipoParametro: campo.obtener("tipoParametro")
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
                                             descripcion: { editable: false },
                                             tipoParamFisicoquimico: { editable: false },
                                             unidadMedida: { editable: false }
                                                                                   
                                            }
                                        }
                                }
                                
                       }
            
            );

    $("#grillaParamFisicoquimicos").kendoGrid({
                        
                        width:600,
                        autoSync: true,
                        dataSource: dataSource,                        
                        columns:
                         [
                            
                            { field: "descripcion", title: "Descripción", width: "160px" },
                            { field: "tipoParamFisicoquimico", title: "Tipo Parametro", width: "160px" },
                            { field: "unidadMedida", title: "Unidades", width: "160px" },
                           
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
    
     var url = servlet.consultar.paramFisQuim;
    
      $.ajax(
              {
                  type:"POST",
                  data: parametros,
                  url: url,
                  cache :false,
                  success: function(data){                    
                                       
                      campo.asignar('codigo',data.codigo);
                      campo.asignar('descripcion',data.descripcion);                       
                      campo.asignar("unidades", data.unidadMedida);
                      campo.asignar("tipoParametro", data.tipoParamFisicoquimico);
                      
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
    
    var url = servlet.eliminar.paramFisQuim;
    
    var grid = $("#grillaParamFisicoquimicos").data("kendoGrid");
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
                            alert(msg.eliminar.error);
                        }

                    }
                }
             );
     }
   
   
   }