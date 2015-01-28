
$(document).on("ready", inicio);

function inicio(){
 
    
    //Se inicializan los botones
    pntlla.iniciar();


    $("#guardar").on("click",enviarFormulario);
    $("#modificar").on("click",actualizarRegistro);
    $("#consultar").on("click",generarGrilla);
    $("#limpiar").on("click",limpiarFormulario);

    $("#tipoPersona").on("change", ocultarApellido);

    $("#documento").on("keypress", validarEscritura); 
 	
    //Inicializa el validador
	$("#div_tecnicos").kendoValidator(
        {
            rules: {
                valTipoPersona:function(input){
                    
                        var tipoDoc = campo.obtener("tipoDocumento");
                        var id    = input.attr('id');
                        
                    if(id == 'apellidos' && tipoDoc != 3 && input.val() == ''){
                            return false;
                     }
                     return true;
                    
                },
                /*
                 * Función valida los diferentes tipos de escritura dependiendo del tipo de documento
                 * @retrun vacio
                 */
                 validacionNit: function (input) {

                        var tipoDoc = campo.obtener("tipoDocumento");
                        var id    = input.attr('id');

                        var val = null ;

                        if (input.is("[data-validacionNit-msg]") && input.val() != "" && tipoDoc == 2){   
                            val = campo.validarNit(id);
                            return val;
                        }

                        return true;
                }
            }
        }
    );
    
   


}


/*
 * Función valida los diferentes tipos de escritura dependiendo del tipo de documento
 * @retrun vacio
 */
function validarEscritura(){

   var tipoDocumento = campo.obtener("tipoDocumento");

   if( tipoDocumento == 2){
        return campo.nit(event,this);

    }else{
        return campo.numerico(event);
    }


}

/*
 * Función envia al backend
 * @retrun vacio
 */

function enviarFormulario(){

	var validador = $("#div_tecnicos").data("kendoValidator");
        var status     = $(".status");

		if (validador.validate()) {
                        guardarRegistro();  
                }

}

function obtenerParametros(){ 
 var parametros = {    
                            codigo: campo.obtener("codigo"),
                            tipoPersona: campo.obtener("tipoPersona"),
                            nombres: campo.obtener("nombres"),
                            apellidos : campo.obtener("apellidos"),
                            tipoDocumento : campo.obtener("tipoDocumento"),
                            documento: campo.obtener("documento"),
                            estado: campo.obtener("estadoTec")
                            
                            
                        };
   return parametros;
}
 function limpiarFormulario(){
        campo.limpiar("codigo");
        campo.limpiar("tipoPersona");
        campo.limpiar("nombres");
        campo.limpiar("apellidos");
        campo.limpiar("tipoDocumento");
        campo.limpiar("documento");
        campo.limpiar("estadoTec");
 }  
 
function guardarRegistro(){
    
     var url = servlet.insertar.tecnicos;
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

function actualizarRegistro(){
    
  var validador = $("#div_tecnicos").data("kendoValidator");
    if (validador.validate()) {
        
            var parametros = obtenerParametros();

            var url = servlet.actualizar.tecnicos;

            elmto.deshabilitar('modificar');
            var xhr = $.ajax(
                           {
                               type:"POST",
                               data: parametros,
                               url: url,
                               cache :false                  
                           }
                        );

           xhr.done(function(data){
                   alert("El registro ha sido actualizado exitosamente.");
                   limpiarFormulario();
                   pntlla.iniciar();
                   elmto.habilitar('modificar');
                   generarGrilla();
           });
   }
    
}

function mostrarRegistro(parametros){
    
     var url = servlet.consultar.tecnicos;
    
      var xhr = $.ajax(
                        {
                            type:"POST",
                            data: parametros,
                            url: url,
                            cache :false
                        }
                     );
             
        xhr.done(function(data){ 

                campo.asignar("codigo",data.codigo);
                campo.asignar("tipoPersona",data.tipoPersona);
                campo.asignar("nombres",data.nombres);
                campo.asignar("apellidos",data.apellidos);
                campo.asignar("tipoDocumento",data.tipoDocumento);
                campo.asignar("documento",data.documento);
                campo.asignar("estadoTec",data.estado);                                           
                pntlla.iniciar(); 

        });
    
}

/*
 * Permite en eliminar un registro de la BD
 * @retrun vacio
 */

function eliminarRegistro(parametros){
    
    var url = servlet.eliminar.tecnicos;
    
    var grid = $("#grillaTecnicos").data("kendoGrid");
     if(confirm("¿Esta seguro que desea eliminar este registro?")){   
        $.ajax(
                {
                    type:"POST",
                    data: parametros,
                    url: url,
                    cache :false,
                    success: function(data){
                        
                        if(data.error ==  0){
                            grid.cancelChanges();  
                            alert("El campo no puede ser eliminado, por que ya esta asignado en el sistema");
                        }

                    }
                }
             );
     }
 }

/*
 * Función que oculta el campo apellido cuando es perosna juridica
 * @retrun vacio
 */
function ocultarApellido(){

    var $contenedor = $('#contenedorApellidos');

    if(this.value ==  3 ){
       $contenedor.slideUp();
       $("#apellidos").removeAttr('required');

    }else{
        $contenedor.slideDown();
        $("#apellidos").attr('required',true);
    }
}

function generarGrilla(){

    var url = servlet.consultar.tecnicos; 
    
    var dataSource = new kendo.data.DataSource(
                        
                        {                             
                            transport: {
                                    read: {
                                        url:url,
                                        data:{opcion:1,
                                            codigo: campo.obtener("codigo"),
                                            tipoPersona: campo.obtener("tipoPersona"),
                                            nombres: campo.obtener("nombres"),
                                            apellidos : campo.obtener("apellidos"),
                                            tipoDocumento : campo.obtener("tipoDocumento"),
                                            documento: campo.obtener("documento"),
                                            estado: campo.obtener("estadoTec")
                                            }, 
                                        dataType: "json",
                                        type:'POST',
                                        cache:false
                                    }
                                },
                                batch: true,
                                pageSize: 20,
                                schema: {
                                       model: {
                                          id: "codigo",
                                          fields: {
                                                
                                                descripcion : {editable : false},
                                                documento : {editable : false},
                                                tipoDocumento : {editable : false},
                                                documento : {editable : false},
                                                estado : {editable : false}
                                                
                                            }
                                        }
                                }
                                
                       }
            
            );

    $("#grillaTecnicos").kendoGrid({
                        height: 430,
                        width:800,
                        autoSync: true,                       
                        dataSource: dataSource,                        
                        columns:
                         [
                            {field : "descripcion" , title : "Nombre" , width: "160px"},
                            {field : "tipoDocumento" , title : "Tipo Documento" , width: "170px"},
                            {field : "documento" , title : "Documento" , width: "160px"},
                            {field : "tipoPersona" , title : "Tipo Persona" , width: "160px"},
                            {field : "estado" , title : "Estado" , width: "160px"},                           
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
