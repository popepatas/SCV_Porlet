
$(document).on("ready", inicio);

function inicio(){
 

    //Se inicializan los botones
    
   botonesPuntos();
    
   $("#guardar").on("click",enviarFormulario);
   $("#consultar").on("click",generarGrilla);
   $("#modificar").on("click",actualizarRegistro);
   $("#limpiar").on("click",limpiarFormulario);
   
   $("#contenedorFormPuntos").masterField(
                       {
                        nameIgualId:true,   
                        classContenedor:"bordePuntosVertimiento",
                        onAfterDelete: function(){
                             var codigo = this.find("[data-mfd-name='codigo']").val();
                            
                           
                        }
                     }
    );
    

   

    var url="";

    $("#div_puntos_vertimientos").on('keypress','#contrato' ,validarEscritura);

     
 	
    //Inicializa el validador
	$("#div_puntos_vertimientos").kendoValidator();
    
   


}

function botonesPuntos(){
    if(campo.obtener("act") != ''){
       elmto.mostrar("modificar");
       elmto.ocultar("guardar");
   }else{
        elmto.ocultar("modificar");
        elmto.mostrar("guardar");
   }
    
}

function validarEscritura() {
   
   return campo.numerico(event);
}
/*
 * Función envia al backend
 * @retrun vacio
 */

function enviarFormulario(){

    var validador = $("#div_puntos_vertimientos").data("kendoValidator");
    var status     = $(".status");

		     if (validador.validate()) {  
                   
                        guardarRegistro();
                        
                } else {
                 
                }

}


function guardarRegistro(){
    
     var url = servlet.insertar.puntosVertimiento;
     var masterField =  $("#contenedorFormPuntos").data('plugin_masterField');
     
     var parametros = {                                                        
                            puntos   : $.toJSON(masterField.getValues()),
                            contrato : campo.obtener("contrato")
                            
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
            
    var masterField =  $("#contenedorFormPuntos").data('plugin_masterField');
        masterField.reset();
        
    campo.limpiar("contrato"); 
    campo.limpiar("act"); 
    campo.limpiar("ubicacion");
    campo.limpiar("latitud");
    campo.limpiar("longitud");
    campo.limpiar("estado");
    campo.limpiar("observacion");
    campo.limpiar("tipoEstructura");
   
        resetPantalla();
    
}

function resetPantalla(){
    
     var codigo = document.getElementById('act').value;

    if(codigo != ""){
        elmto.ocultar('guardar');
        elmto.mostrar('modificar');



    }else{
        elmto.mostrar('guardar');
        elmto.ocultar('modificar');

    }
    
}


function actualizarRegistro(){
    
     var masterField =  $("#contenedorFormPuntos").data('plugin_masterField');
     var parametros = {                                                        
                            puntos   : $.toJSON(masterField.getValues()),
                            contrato : campo.obtener("contrato")
                            
                      };
  
                       
     var url = servlet.actualizar.puntosVertimiento;
     
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
    
     var url = servlet.consultar.puntosVertimiento;
     var masterField =  $("#contenedorFormPuntos").data('plugin_masterField');
         
    
      $.ajax(
              {
                  type:"POST",
                  data: parametros,
                  dataType:"html",
                  url: url,
                  cache :false,
                  success: function(data){  
                      

                        var json = $.parseJSON(data);
                        campo.asignar("contrato",json.contrato);
                        campo.asignar("act","actualizar");
                        botonesPuntos();
                        masterField.reset();                        
                        masterField.setValues(json.puntos);
                      
                      
                  }
              }
           );
    
}

/*
 * Permite en eliminar un registro de la BD
 * @retrun vacio
 */

function eliminarRegistro(parametros){
    
    var url = servlet.eliminar.puntosVertimiento;
    
    var grid = $("#grillaPuntoVertimiento").data("kendoGrid");
    
      
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

                    },
                    error:function(){
                        
                    }
                }
             );
     }
 }
   
function eliminarPunto(parametros){
    
    var url = servlet.eliminar.puntosVertimiento;
    var masterField =  $("#contenedorFormPuntos").data('plugin_masterField');
    
    var grid = $("#grillaPuntoVertimiento").data("kendoGrid");
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

                    },
                    error:function(){
                        masterField.cancelDelete();
                    }
                }
             );
     }
 }


function generarGrilla(){

    var url = servlet.consultar.puntosVertimiento; 
    
    var grilla = $("#grillaPuntoVertimiento").data('kendoGrid'); 
    
    if(grilla != undefined){
       grilla.destroy();
    }
    
    
    var dataSource = new kendo.data.DataSource(
                        
                        {                             
                            transport: {
                                    read: {
                                        url:url,
                                        data:{opcion:1, contrato : campo.obtener("contrato")}, 
                                        dataType: "json",
                                        type:"POST",
                                        cache:false
                                    }
                                },
                                batch: true,
                                pageSize: 20,
                                schema: {
                                        model: {
                                          id: "contrato",
                                          fields: {                                                
                                                contrato : {editable : false},
                                                descripcion : {editable : false}                                                                              
                                            }
                                        }
                                }
                                
                       }
            
            );

    $("#grillaPuntoVertimiento").kendoGrid({
                        height: 430,
                        width:1000,
                        autoSync: true,
                       
                        dataSource: dataSource,                        
                        columns:
                         [
                           
                            {field : "contrato" , title : "Contrato" , width: "160px"},
                            {field : "puntos" , title : "Puntos de Vertimientos" , width: "110px"},
                           
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


