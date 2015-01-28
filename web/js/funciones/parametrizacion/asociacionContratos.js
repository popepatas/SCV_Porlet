''
$(document).on("ready", inicio);

function inicio(){
 
   var url="";
   var $contenedor;
    //Se inicializan los botones
     botonesPuntos();
 

    
  $("#guardar").on("click",enviarFormulario);
  $("#consultar").on("click",generarGrilla); 
  $("#modificar").on("click",actualizarRegistro);
  $("#limpiar").on("click",limpiarFormulario);
  
  $("#contenedorAsignacionContrato").masterField(
                                                 {
                                                    nameIgualId:false,
                                                    onAfterDelete:function(event){
                                                        
                                                        var codigo = this.find("[data-mfd-name='codigo']").val();
                                                                                                                 
                                                        return eliminarContratoAsociado(codigo);
                                                                                                            
                                                        
                                                    }
                                                    
                                                 });
  
    
   $contenedor = $("#div_asignacion_contratos");   
   $contenedor.on("keypress","#contrato,#contratoAsignado", validarEscrituraNumerico);
   //Inicializa el validador
   $contenedor.kendoValidator({
       
       rules:{
           ValidarContratoPadre: function(obj){
               var valor,contrato;
               
               if(obj.is("[data-mfd-name=contratoAsignado]") && obj.val() != ""  ){
                    valor = obj.val() ;
                    contrato = campo.obtener("contrato");
                  return valor != contrato ;
                  
               }
               return true;
           },
            ValidarContratoHijo: function(obj){
                     
                     var result = true;
                     var name = obj.attr("name");
                     var valor = obj.val();
                     var id = obj.attr("id");                     
                     
                        if(obj.is("[data-mfd-name=contratoAsignado]")){
                            
                              $("[name='contratoAsignado']").each(function(index, objLista){
                                  
                                  if(valor == objLista.value && id != $(objLista).attr("id") ){                                      
                                      result = false;                                     
                                  }
                                  
                              });
                                

                        }
                            
                        return result;
                     
                 },
           required:function(obj){
                if(obj.val() == "" && obj.attr("type") != 'hidden'){
                 return false;
                }
                return true;
           }
       },
       messages:{
           ValidarContratoPadre: "No puede asignar un contrato padre como un contrato hijo.",
           ValidarContratoHijo: "Este contrato ya ha sido registrado en esta lista.",
           required:"Ingrese un valor para este campo"
       }
      
       
   });
    
   
   $contenedor.on("blur","#contratoAsigando", validarContratoPrincipal);
   
   //Inizializa autocompletar
   
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

function validarContratoPrincipal(){
    
    // Si la función es llamada desde el evento Blur
    if(event){
        
        if(this.value == campo.obtener("contrato")){
            
            alert("No puede asignar un contrato padre como hijo");
        }
        
    }
    var array = campo.obtenerxNombre('contratoAsignado');
    
    for(var i = 0; i <array.length;i++){
        
    }
    
}

function validarEscrituraNumerico () {
   
   return campo.numerico(event);
}

/*
 * Función envia al backend
 * @retrun vacio
 */
function enviarFormulario(){

	var validador = $("#div_asignacion_contratos").data("kendoValidator");
        var status     = $(".status");

		if (validador.validate()) {  
                   
                        guardarRegistro();
                        
                }

}



function obtenerParametro(){
    
     var masterField =  $("#contenedorAsignacionContrato").data('plugin_masterField');
     
     var parametros = {                                                        
                            contratosAsignados   : $.toJSON(masterField.getValues()),
                            contrato : campo.obtener("contrato")
                            
                      };
                      
    return parametros;
}


function guardarRegistro(){
    
     var url = servlet.insertar.asigContrato;
    
     var parametros = obtenerParametro();                 
                 
     $.ajax(
              {
                  type:"POST",
                  data: parametros,
                  url: url,
                  cache :false,
                  success: function(data){
                       if (data.length == 0){
                                alert("El registro ha sido almacenado exitosamente.");
                                limpiarFormulario();
                                botonesPuntos();                     
                                generarGrilla();
                       }else{
                           
                           if(data[0].error == 0){
                               alert("Se ha presentado un error inesperado.");
                           }else{
                               
                               var tam = data.length;
                               var cadena = "";
                                  
                                    for(var i=0; i < tam; i++  ){
                                    
                                          if (data[i].error == 2){
                                              
                                              cadena =  " - El contrato número " + data[i].contrato +" No puede ser un contrato padre, debido a que ya se encuentra asociado.";
                                                    
                                          }else if(data[i].error == 4){
                                              cadena +=  " - El contrato número " + data[i].contrato + " No puede ser asignado como hijo. \n";
                                          }
                                    }
                               
                                 alert(cadena);  
                               
                               
                           }
                           
                           
                       }
                  }
              }
           );
    
}

function limpiarFormulario(){
            
    var masterField =  $("#contenedorAsignacionContrato").data('plugin_masterField');
        masterField.reset();
    campo.limpiar("contratoAsignado");
    campo.limpiar("contrato"); 
    campo.limpiar("act"); 
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
  
      var parametros = obtenerParametro();
  
                     
     var url = servlet.actualizar.asigContrato;
     
     
     $.ajax(
              {
                  type:"POST",
                  data: parametros,
                  url: url,
                  cache :false,
                  success: function(data){
                      
                      if (data.length == 0){
                                alert("El registro ha sido actualizado exitosamente.");
                                limpiarFormulario();
                                botonesPuntos();                     
                                generarGrilla();
                       }else{
                           
                           if(data[0].error == 0){
                               alert("Se ha presentado un error inesperado.");
                           }else{
                               
                               var tam = data.length;
                               var cadena = "";
                                  
                                    for(var i=0; i < tam; i++  ){
                                    
                                          if (data[i].error == 2){
                                              
                                              cadena =  " - El contrato número " + data[i].contrato +" No puede ser un contrato padre, debido a que ya se encuentra asociado.";
                                                    
                                          }else if(data[i].error == 4){
                                              cadena +=  " - El contrato número " + data[i].contrato + " No puede ser asignado como hijo. \n";
                                          }
                                    }
                               
                                 alert(cadena);  
                               
                               
                           }
                           
                           
                       }
                      
                  }
              }
           );
    
}

function mostrarRegistro(parametros){
    
     var url = servlet.consultar.asigContrato;
     var masterField =  $("#contenedorAsignacionContrato").data('plugin_masterField');
         
    
      $.ajax(
              {
                  type:"POST",
                  data: parametros,
                  url: url,
                  cache :false,
                  success: function(data){                   
                          
                        campo.asignar("contrato",data.contrato);
                        campo.asignar("act","actualizar");
                        masterField.reset();
                        masterField.setValues(data.contratos);
                        botonesPuntos();
                      
                      
                  }
              }
           );
    
}



function eliminarContratoAsociado(codigo){
    
   if(codigo != ""  ){
            var url = servlet.eliminar.asigContrato;
            var parametros = {
                  opcion:2,
                  codigo:codigo
            };
            var grid = $("#grillaAsociacionContratos").data("kendoGrid");
                if(confirm("¿Esta seguro que desea eliminar este Contrato?")){

                        var xhrEliminar = $.ajax({
                                    type:"POST",
                                    dataType:"json",
                                    data: parametros,
                                    url: url,
                                    cache :false
                                });

                          xhrEliminar.done(function(data,status, xhr){

                                if(data[0].error ==  0){                        
                                    alert("El campo no puede ser eliminado, por que ya esta asignado en el sistema");
                                    var parametros = {
                                            contrato : campo.obtener("contrato"),
                                            opcion : 2
                                       };
                                       mostrarRegistro(parametros); 

                                }else{

                                    alert("Eliminación realizada con exito");
                                    generarGrilla();
                                }

                  });

                  return true;
                }else{

                    return false;
                }
    }else{
        
        return true;
    }
    
}

/*
 * Permite en eliminar un registro de la BD
 * @retrun vacio
 */

function eliminarRegistro(parametros){
    
    var url = servlet.eliminar.asigContrato;
    
    var grid = $("#grillaAsociacionContratos").data("kendoGrid");
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
  
    var grilla = $("#grillaAsociacionContratos").data('kendoGrid'); 
    
    if(grilla != undefined){
       grilla.destroy();
    }
    
    var url = servlet.consultar.asigContrato; 
    
    var dataSource = new kendo.data.DataSource(
                        
                        {                             
                            transport: {
                                    read: {
                                        url:url,
                                        data:{opcion:1,
                                              contrato : campo.obtener("contrato")
                                            }, 
                                        dataType: "json",
                                        type:"POST",
                                        cache:false
                                    }
                                },
                                batch: true,
                                pageSize: 30,
                                schema: {
                                        model: {
                                          id: "contrato",
                                          fields: {
                                                codigo : {editable: false},
                                                contrato : {editable : false},
                                                asociaciones : {editable : false}                                                                              
                                            }
                                        }
                                }
                                
                       }
            
            );

    $("#grillaAsociacionContratos").kendoGrid({                        
                        width:800,
                        autoSync: true,
                       
                        dataSource: dataSource,                        
                        columns:
                         [
                           
                            {field : "contrato" , title : "Contrato" , width: "160px"},
                            {field : "asociaciones" , title : "Contratos Asociados" , width: "110px"},
                           
                            { command:  [ //Botones de la ultima columna
                                          { 
                                             name:"edit",
                                             text:"Editar",
                                             click: function(e){
                                                 
                                                  var tr = $(e.target).closest("tr"); // Se obtine todo el tr que se va a editar          
                                                  var data = this.dataItem(tr); 
                                                
                                                  var parametros = {
                                                       contrato : data.id,
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
                            
                                var contrato = e.model.contrato; 
                                var parametros = {
                                                    contrato :  contrato,
                                                    opcion: 1
                                                 };
                                                 
                                eliminarRegistro(parametros);                           

                        }
                    
                           
     });

}


