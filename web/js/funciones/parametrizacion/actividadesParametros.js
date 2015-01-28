
$(document).on("ready", inicio);

function inicio(){
 

    //Se inicializan los botones
    pntlla.iniciar();
 	
    //Inicializa el validador
    
    $("#div_actividades_parametros").kendoValidator();

    //Inicializa el select2
    //$("#parametro").select2();

    
    $("#guardar").on("click",enviarFormulario);
    
    $("#modificar").on("click",actualizarRegistro);
    
    $("#consultar").on("click",generarGrilla);
    
    $("#limpiar").on("click",limpiarFormulario);
    
    
    var urlAct = servlet.consultar.actEconomica;    
        campo.lista.crear("actividad",urlAct,{});
   
    var urlParam = servlet.consultar.paramFisQuim;
        campo.lista.crear("parametro",urlParam,{opcion:1});
    //Inizializa campo numericos

    $("#rangoInicial").kendoNumericTextBox({        
        min: 0        
    });

    $("#rangoFinal").kendoNumericTextBox({        
        min: 0        
    });
    
}


/*
 * Función envia al backend
 * @retrun vacio
 */

function enviarFormulario(){

	var validador = $("#div_actividades_parametros").data("kendoValidator");
        var status     = $(".status");
        
        

		 if (validador.validate()) {
                        var parametros = {    
                            
                            mayorInicial: campo.check("mayorRangoInicial"),
                            mayorFinal: campo.check("mayorRangoFinal"),
                            rangoFinal : campo.kendo.NumericTextBox("rangoFinal").value(),
                            rangoInicial : campo.kendo.NumericTextBox("rangoInicial").value(),
                            parametro: campo.obtener("parametro"),
                            actividad: campo.obtener("actividad"),
                            mostrarRango: campo.obtener("mostrarRango")
                            
                        };
                        guardarRegistro(parametros);
                } else {
                   
                }

}

function guardarRegistro(parametros){
    
     var url = servlet.insertar.actvParam;
     
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

function resultadoOperacion(data,texto){
    
      if(data.error == 1){
                alert(texto);
                
                limpiarFormulario();
                pntlla.iniciar();
                elmto.habilitar('modificar');
                generarGrilla();

            }else if(data.error == 2){

               alert("Ya ha sido asignado el parámetro fisicoquímico a la actividad económica seleccionada.");
                 elmto.habilitar('modificar');

            }else if(data.error == 0){

                alert("Hubo un error Inesperado.");
                 elmto.habilitar('modificar');
            }
}

function limpiarFormulario(){
    
      
   campo.limpiar("mayorRangoInicial");
   campo.limpiar("mayorRangoFinal");
   campo.kendo.NumericTextBox("rangoFinal").value("");
   campo.kendo.NumericTextBox("rangoInicial").value("");
   campo.limpiar("parametro");
   campo.limpiar("actividad");
   campo.limpiar("codigo");
   campo.limpiar("mostrarRango");
    
    
}


function actualizarRegistro(){
    var parametros = {
                            mayorInicial: campo.check("mayorRangoInicial"),
                            mayorFinal: campo.check("mayorRangoFinal"),
                            rangoFinal : campo.kendo.NumericTextBox("rangoFinal").value(),
                            rangoInicial : campo.kendo.NumericTextBox("rangoInicial").value(),
                            parametro: campo.obtener("parametro"),
                            actividad: campo.obtener("actividad"),
                            codigo: campo.obtener("codigo"),
                            mostrarRango: campo.obtener("mostrarRango")
                     };
                     
     var url = servlet.actualizar.actvParam;
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


function generarGrilla(){
    
     var grilla = $("#grillaActParamFiscoQuim").data('kendoGrid'); 
    
    if(grilla != undefined){
       grilla.destroy();
    }

    var url = servlet.consultar.actvParam; 
    var dataSource = new kendo.data.DataSource(
                        
                        {                             
                            transport: {
                                    read: {
                                        url:url,
                                        data:{
                                            mayorInicial: campo.check("mayorRangoInicial"),
                                            mayorFinal: campo.check("mayorRangoFinal"),
                                            rangoFinal : campo.kendo.NumericTextBox("rangoFinal").value(),
                                            rangoInicial : campo.kendo.NumericTextBox("rangoInicial").value(),
                                            parametro: campo.obtener("parametro"),
                                            actividad: campo.obtener("actividad"),
                                            mostrarRango: campo.obtener("mostrarRango"),
                                            opcion : 1
                                        }, 
                                        dataType: "json",
                                        cache:false
                                    }
                                },
                                batch: true,
                                pageSize: 30,
                                schema: {
                                        model: {
                                          codAct: "codActividad",
                                          codPar: "codParametro",
                                          fields: {                                               
                                                actividad :  { editable: false },
                                                parametro  :  { editable: false },
                                                rangoInicial  :  { editable: false },
                                                rangoFinal  :  { editable: false },
                                                mayorFinal  :  { editable: false },
                                                mayorInicial  :  { editable: false },
                                                codActividad  :  { editable: false, nullable: true },
                                                codParametro :  { editable: false, nullable: true },
                                                codCiiu : {editable:false},
                                                mostrarRango  :  { editable: false, nullable: true }
                                                                                   
                                            }
                                        }
                                }
                                
                       }
            
            );

    $("#grillaActParamFiscoQuim").kendoGrid({                        
                        width:800,
                        autoSync: true,
                       
                        dataSource: dataSource,                        
                        columns:
                         [
                           
                           
                         
                            { field: "codCiiu", title: "CIIU", width: "90px" },
                            { field: "actividad", title: "Actividad Económica", width: "190px" },
                            { field: "parametro", title: "Parametro Fisicoquímico", width: "100px" },                            
                            {    title: "Rango", width: "100px",
                                template:"#= rangoInicial# - #=rangoFinal#"},                            
                            { field: "mayorInicial", title: "Mayor Rango Inicial", width: "100px" },
                            { field: "mayorFinal", title: "Mayor Rango Final", width: "100px" },
                            { field: "mostrarRango", title: "Visualizar Rango", width: "100px" },
                               { command:  [//Botones de la ultima columna
                                          { 
                                             name:"edit",
                                             text:"Editar",
                                             click: function(e){
                                                 
                                                  var tr = $(e.target).closest("tr"); // Se obtine todo el tr que se va a editar          
                                                  var data = this.dataItem(tr); 
                                               
                                                  var parametros = {
                                                       actividad : data.codActividad,
                                                       parametro  : data.codParametro,
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
                            
                                var parametros = {
                                                    actividad : e.model.codActividad,
                                                    parametro  : e.model.codParametro                                                     
                                                 };
                                                 
                                eliminarRegistro(parametros);                           

                        }
                    
                           
     });

}

function mostrarRegistro(parametros){
    
     var url = servlet.consultar.actvParam;
    
      $.ajax(
              {
                  type:"POST",
                  data: parametros,
                  url: url,
                  cache :false,
                  success: function(data){                    
                      res = campo.convertirBool(data[0].mayorInicial);
                      campo.asignar("mayorRangoInicial", res);
                      campo.asignar("mayorRangoFinal", campo.convertirBool(data[0].mayorFinal));
                      campo.asignar('actividad',data[0].codActividad);
                      campo.asignar('parametro',data[0].codParametro);
                      campo.kendo.NumericTextBox('rangoInicial').value(data[0].rangoInicial);
                      campo.kendo.NumericTextBox('rangoFinal').value(data[0].rangoFinal);
                      campo.asignar('codigo', data[0].codigo);
                      campo.asignar("mostrarRango", data[0].mostrarRango);
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
    
    var url = servlet.eliminar.actvParam;
    
    var grid = $("#grillaActParamFiscoQuim").data("kendoGrid");
     if(confirm("¿Esta seguro que desea eliminar este registro?")){   
        $.ajax(
                {
                    type:"POST",
                    data: parametros,
                    url: url,
                    cache :false,
                    success: function(data){
                        console.log(data);

                        if(data.error ==  0){
                            grid.cancelChanges();  
                            alert("El campo no puede ser eliminado, por que ya esta asignado en el sistema");
                        }

                    }
                }
             );
     }
   
   
}