
$(document).on("ready", inicio);

function inicio(){
 

   // Se inicializan los botones
    pntlla.iniciar();

//    Se crea un campo numerico
    $("#telefono1,#telefono2").kendoNumericTextBox({
            format: "#",
            min: 0

    });
  
    $("#vigencia").kendoDatePicker({
         format: "dd/MM/yyyy"
    });
       
   
    
  

    $("#resolucion").on("keypress",validarEscritura);

    //Inicializa el validador
	$("#div_laboratorios").kendoValidator(
        {
            rules: {
               
                 validacionDireccion: function(obj){//Se crea una regla personalizada
                       return campo.direccion.validar(obj);
                 },

                date: function (input) {
                    var attr = input.attr("data-role");
                    if( attr == "datepicker"){ 
                        var fecha = kendo.parseDate(input.val(), "dd/MM/yyyy");
                        return fecha instanceof Date;
                    }
                    return true;
                },
                
                validarListaParam: function(obj){
                     
                     var result = true;
                     var name = obj.attr("name");
                     var valor = obj.val();
                     var id = obj.attr("id");
                     
                        if(name === "ParamFisQuim"){
                            
                              $("[name='ParamFisQuim']").each(function(index, objLista){
                                  
                                  if(valor == objLista.value && id != $(objLista).attr("id")){                                      
                                      result = false;                                     
                                  }
                                  
                              });
                                

                        }
                            
                        return result;
                     
                 }
            },
            messages:{
                date:"Fecha Ingresada no es valida. ",
                validarListaParam: "No debe seleccionar parámetros fisicoquímicos repetidos"
            }
        }
    );

    $("#guardar").on("click",enviarFormulario);
    $("#consultar").on("click",generarGrilla);
    $("#modificar").on("click",actualizarRegistro);
    $("#limpiar").on("click",limpiarFormulario);

    crearListaParam();
    

}

/*
 * Función  que valida que la escritura no tenga espacios
 * @retrun vacio
 */

function validarEscritura(){
    return campo.sinEspacios(event,this,true);
}


function crearListaParam(){
    
    var xhrParam;
    
    xhrParam =  campo.lista.crear("ParamFisQuim",servlet.consultar.paramFisQuim,{opcion:1});
    
    xhrParam.done(function(response,status,xhr){
    
        $("#contenedorParametrosAcreditados").masterField({
                                                       nameIgualId:false,
                                                       onDelete:function(event){
                                                           /*console.log(event);
                                                           console.log(this);
                                                           alert("eliminar");
                                                           */
                                                       }

                                                    });
     });

    
}

/*
 * Función envia al backend
 * @retrun vacio
 */
function enviarFormulario(){

	var validador = $("#div_laboratorios").data("kendoValidator");
        var status     = $(".status");

               if (validador.validate()){  
                   
                        guardarRegistro();
                        
                } else {
                 
                }

}
function obtenerParametros(){
      
    var masterParam =  $("#contenedorParametrosAcreditados").data("plugin_masterField");
    var parametros = {                                                        
                            
                            nombre : campo.obtener("nombre"),                            
                            contactos : campo.obtener("personaContacto"),
                            direccion : campo.direccion.obtener("completaDireccion"),
                            telefono1 : campo.kendo.NumericTextBox("telefono1").value(),
                            telefono2 : campo.kendo.NumericTextBox("telefono2").value(),                            
                            correo : campo.obtener("email"),
                            resolucion : campo.obtener("resolucion"),
                            vigencia : campo.obtener("vigencia"),
                            paramAcreditados : $.toJSON(masterParam.getValues())
                            
                      };
                      
       return parametros;
}

function guardarRegistro(){
    
     var url = servlet.insertar.laboratorios;  
     
     var parametros = obtenerParametros();
     
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
    
   var masterParam =  $("#contenedorParametrosAcreditados").data("plugin_masterField");
    campo.limpiar("nombre");    
    campo.limpiar("personaContacto");
    campo.direccion.limpiar("completaDireccion");    
    campo.kendo.NumericTextBox("telefono1").value("");
    campo.kendo.NumericTextBox("telefono2").value("");
    campo.limpiar("email");
    campo.limpiar("resolucion");
    campo.limpiar("vigencia");
    campo.limpiar('codigo');
    
    campo.limpiar('ParamFisQuim');
    masterParam.reset();
    
    
}


function actualizarRegistro(){
      var masterParam =  $("#contenedorParametrosAcreditados").data("plugin_masterField");
      var parametros = {                                                        
                            
                            nombre : campo.obtener("nombre"),                            
                            contactos : campo.obtener("personaContacto"),
                            direccion : campo.direccion.obtener("completaDireccion"),
                            telefono1 : campo.kendo.NumericTextBox("telefono1").value(),
                            telefono2 : campo.kendo.NumericTextBox("telefono2").value(),                            
                            correo : campo.obtener("email"),
                            resolucion : campo.obtener("resolucion"),
                            vigencia : campo.obtener("vigencia"),
                            codigo : campo.obtener("codigo"),                            
                            paramAcreditados : $.toJSON(masterParam.getValues())
                            
                      };
  
                     
     var url = servlet.actualizar.laboratorios;
     
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

function mostrarRegistro(parametros){
    
     var url = servlet.consultar.laboratorios;
     var masterParam =  $("#contenedorParametrosAcreditados").data("plugin_masterField");
     var xhrLab =  $.ajax(
              {
                  type:"POST",
                  data: parametros,
                  url: url,
                  cache :false
              }
           );
   
     var urlAcrd = servlet.consultar.acreditacion;
     var xhrParamAcrdtcn = 
              $.ajax(
              {
                  type:"POST",
                  data: parametros,
                  url: urlAcrd,
                  cache :false
              }
           );
   
   
     $.when(xhrLab,xhrParamAcrdtcn).done(function(fn1,fn2){                    
                        var lab = fn1[0];
                        var acrd = fn2[0];
                        console.log(fn1);
                        console.log(fn2);
                        campo.asignar("codigo",lab.codigo);
                        campo.asignar("nombre",lab.nombre);
                        campo.asignar("personaContacto",lab.contacto);
                        campo.direccion.asignar("completaDireccion",lab.direccion);
                        campo.kendo.NumericTextBox("telefono1").value(lab.telefono1);
                        campo.kendo.NumericTextBox("telefono2").value(lab.telefono2);
                        campo.asignar("email",lab.correo);
                        campo.asignar("resolucion",lab.resolucion);
                        campo.asignar("vigencia",lab.vigencia);
                        masterParam.setValues(acrd);
                        pntlla.iniciar() ; 
                  });
    
}

/*
 * Permite en eliminar un registro de la BD
 * @retrun vacio
 */

function eliminarRegistro(parametros){
    
    var url = servlet.eliminar.laboratorios;
    
    var grid = $("#grillaLaboratorios").data("kendoGrid");
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

    var url = servlet.consultar.laboratorios; 
    
    var dataSource = new kendo.data.DataSource(
                        
                        {                             
                            transport: {
                                    read: {
                                        url:url,
                                        data:{opcion:1,
                                                nombre : campo.obtener("nombre"),                            
                                                contactos : campo.obtener("personaContacto"),
                                                direccion : campo.direccion.obtener("completaDireccion"),
                                                telefono1 : campo.kendo.NumericTextBox("telefono1").value(),
                                                telefono2 : campo.kendo.NumericTextBox("telefono2").value(),                            
                                                correo : campo.obtener("email"),
                                                resolucion : campo.obtener("resolucion"),
                                                vigencia : campo.obtener("vigencia")
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
                                                
                                                nombre : {editable : false},
                                                contacto : {editable : false},
                                                direccion : {editable : false},
                                                telefono1 : {editable : false},
                                                telefono2 : {editable : false},
                                                correo : {editable : false},
                                                resolucion : {editable : false},
                                                vigencia : {editable : false}                                                                                
                                            }
                                        }
                                }
                                
                       }
            
            );

    $("#grillaLaboratorios").kendoGrid({
                        height: 430,
                        width:1000,
                        autoSync: true,
                       
                        dataSource: dataSource,                        
                        columns:
                         [
                           
                            {field : "nombre" , title : "Nombre" , width: "160px"},
                            {field : "contacto" , title : "Persona de Contacto" , width: "170px"},
                            {field : "direccion" , title : "Dirección" , width: "160px"},
                            {field : "telefono1" , title : "Telefono" , width: "160px"},
                            {field : "telefono2" , title : "Telefono 2" , width: "160px"},
                            {field : "correo" , title : "E-mail" , width: "160px"},
                            {field : "resolucion" , title : "Resolución" , width: "160px"},
                            {field : "vigencia" , title : "Vigencia" , width: "160px"},
                           
                            { command:  [ //Botones de la ultima columna
                                          { 
                                             name:"edit",
                                             text:"Editar",
                                             click: function(e){
                                                 
                                                  var tr = $(e.target).closest("tr"); // Se obtine todo el tr que se va a editar          
                                                  var data = this.dataItem(tr); 
                                                  var codigo = data.id; ;// e.model.id; 
                                                  var parametros = {
                                                       codigo : codigo,
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
