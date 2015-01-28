/**
 *
 * @author jmrincon
 */
var iniciarHistorialDagma = function(codigo){
    $("#fechaRadicado").kendoDatePicker({
         format:"dd/MM/yyyy"
    });
    
    urlSubir = servlet.cargaArchivos.subir+"?opcion=4&codigo=0";
    urlEliminar = "/SCV_Portlet/EliminarArchivoServlet";
    
    $("#files").kendoUpload({
        async: {
            saveUrl: urlSubir,
            removeUrl: urlEliminar,
            autoUpload: true
        }
    });
    
   var ocultarAñadir = function(){
        elmto.ocultar('guardarHistorialDagma');
        elmto.mostrar('actualizarHistorialDagma');
   } 
   
   var ocultarActualizar = function(){
        elmto.ocultar('actualizarHistorialDagma');
        elmto.mostrar('guardarHistorialDagma');
   }

     var actualizarHistorialDagma = function (){       
               
        var parametros = {
           radicado : campo.obtener("radicado"),
           fechaRadicado : campo.obtener("fechaRadicado"),
           codigoProceso : codigo,
           tipoRadicado : campo.obtener("tipoRadicado"),
           codigoHistorial : campo.obtener("codigoHistorial"),
           observacion : campo.obtener("observacionDagma")
       };
       var url = servlet.actualizar.historialDagma;
         var xhrGuardar = $.ajax(
                 {
                     type:"POST",
                     data: parametros,
                     url: url,
                     cache :false
                 }
              );

         xhrGuardar.done(function(data,status,xhr){
                    var error,codigo;
                    var texto = "El registro ha sido actualizado exitosamente.";
                    alert(texto);
                    generarGrillaHistorialDagma();
                    limpiarHistorialDagma();
                    ocultarActualizar();

           });

         xhrGuardar.fail(function(data,status,xhr){

                    var texto = "Error al actualizar el registro";
                    alert(texto);
           });
           
           
     };
   
   var enviarHistorialDagma = function (){
       
       var parametros = {
           radicado : campo.obtener("radicado"),
           fechaRadicado : campo.obtener("fechaRadicado"),
           codigoProceso : codigo,
           tipoRadicado : campo.obtener("tipoRadicado"),
           observacion : campo.obtener("observacionDagma")
       };
       
            var url = servlet.insertar.historialDagma;
            var xhrGuardar = $.ajax(
                     {
                         type:"POST",
                         data: parametros,
                         url: url,
                         cache :false
                     }
                  );

            xhrGuardar.done(function(data,status,xhr){
                    var error,codigo;
                    var texto = "El registro ha sido almacenado exitosamente.";
                    alert(texto);
                    generarGrillaHistorialDagma();
                    limpiarHistorialDagma();

           });

          xhrGuardar.fail(function(data,status,xhr){

                    var texto = "Error al almacenar el registro";
                    alert(texto);
           });
   }

    var limpiarHistorialDagma = function(){
        campo.limpiar("radicado");
        campo.limpiar("fechaRadicado");
        campo.limpiar("tipoRadicado");
        campo.limpiar("observacionDagma");
        $(".k-upload-files").remove();
    }
    
    var cancelarHistorialDagma = function(){
    
      var kendoModalBox =  $("#modalBoxHistorial").data("kendoWindow");
          kendoModalBox.close();
     
     };
     
     
    function generarGrillaHistorialDagma(){

    var url = servlet.consultar.historialDagma;
    
    var dataSource = new kendo.data.DataSource(
                        
                        {                             
                            transport: {
                                    read: {
                                        url:url,
                                        data:{codigoProceso:codigo, opcion:1}, 
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
    
function mostrarDagma(parametros){
     ocultarAñadir();
     var url = servlet.consultar.historialDagma;
    
      $.ajax(
              {
                  type:"POST",
                  data: parametros,
                  url: url,
                  cache :false,
                  success: function(data){                    
                      campo.asignar("fechaRadicado",data[0].fechaRadicado.substring(0,11));
                      campo.asignar("radicado",data[0].radicado);
                      campo.asignar("tipoRadicado",data[0].tipoRadicado);
                      campo.asignar("observacionDagma", data[0].observacion);
                  }
              }
           );
    
}







 $("#grillaHistorialDagma").kendoGrid({
                        height: 330,
                        width:700,
                        autoSync: true,                       
                        dataSource: dataSource,                        
                        columns:
                         [
                            {field : "codigo" , title : "CODIGO" , width: "100px"},
                            {field : "procesoVertimiento" , title : "PROCESO" , width: "100px"},
                            {field : "fechaRadicado" , title : "FECHA RADICACION" , width: "100x"},
                            {field : "radicado" , title : "RADICADO" , width: "100px"},                          
                            { command:  [//Botones de la ultima columna
                                          { 
                                             name:"edit",
                                             text:"Editar",
                                             click: function(e){
                                                 
                                                  var tr = $(e.target).closest("tr"); // Se obtine todo el tr que se va a editar          
                                                  var data = this.dataItem(tr); 
                                                  var parametros = {
                                                       codigo : data.codigo,
                                                       opcion : 2
                                                  };
                                                     campo.asignar("codigoHistorial", data.id);
                                                     mostrarDagma(parametros);  
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
                                
                                var codigo = e.model.id; 
                                var parametros = {
                                                    codigoHistorial :  codigo                                                        
                                                 };
                                                 console.log("Entra");
                           deleteDagma(parametros);                          

                        }
                    
                           
     });

}

generarExcelHistorialDagma = function(){
    
    var url = servlet.consultar.excelHistorialDagma;
            
    var parametros = {
                       codigoProceso: codigo
                    };
                    
    $.post( url, parametros, abrirExcelHistorialDagma);
};

abrirExcelHistorialDagma = function(){

    document.location.href = '/SCV_Portlet/sources/HistorialDagma.xls';

};

function deleteDagma(parametros){

        var url = servlet.eliminar.historialDagma;
        console.log(url);
         if(confirm("¿Esta seguro que desea eliminar este registro?")){  
             var grid = $("#grillaHistorialDagma").data("kendoGrid");
             console.log("Entra Confirm");
            $.ajax(
                    {
                        type:"POST",
                        data: parametros,
                        url: url,
                        cache :false,
                        success: function(data){                        

                            if(data[0].error ==  0){
                                grid.cancelChanges();  
                                alert("Error eliminando el registro");
                            }

                        },
                        error:function(){
                            masterField.cancelDelete();
                        }
                    }
                 );
         }
     }
    ocultarActualizar();
    $("#guardarHistorialDagma").on("click",enviarHistorialDagma);
    $("#limpiarHistorial").on("click",limpiarHistorialDagma);
    $("#consultarHistorial").on("click", generarGrillaHistorialDagma);
    $("#actualizarHistorialDagma").on("click",actualizarHistorialDagma);
    $("#generarExcelHistorial").on("click",generarExcelHistorialDagma);
};



var generarGrillaArchivosSubidos = function (){

    var url = servlet.consultar.archivosInformesCargados; 
    
    var parametros = { 
            opcion:1,
            codigoProceso: campo.obtener("codigoProceso")           
      };
    
    
    var dataSource = new kendo.data.DataSource(
                        
                        {                             
                            transport: {
                                    read: {
                                        url:url,
                                        data:parametros, 
                                        dataType: "json"
                                    }
                                },
                                batch: true,
                                pageSize: 100,
                                serverPaging: true,
                                schema: {
                                        model: {
                                          id: "codigo",
                                          fields: {
                                                nombreArchivo: { editable: false }                                                                                   
                                            }
                                        }
                                }
                                
                       }
            
            );
    
    
    var abrirModalDescargarArchivoHistDagma = function(){

       var kendoModalBox =  modalBox.abrir("contenedorArchivosSubidos",null,"650px","Archivos Cargados");  
           kendoModalBox.open();
    };


    $("#grillaArchivosSubido").kendoGrid({
                                          
                        autoSync: true,
                       
                        dataSource: dataSource,                        
                        columns:
                         [                            
                            //Columnas de la Grilla
                                                     
                            { field:"nombreArchivo", title:"Archivo", width:"80px" },
                                                    
                            { command:  [//Botones de la ultima columna
                                          { 
                                             name:"download",
                                             className: "DescargaProcesoSeco",
                                             text:"Descargar",
                                             click: function(e){
                                                 
                                                  var tr = $(e.target).closest("tr"); // Se obtine todo el tr que se va a editar          
                                                  var data = this.dataItem(tr); 
                                                  
                                                  var codigo = data.id;                                                 
                                                  DescargarArchivoProcesoSeco(codigo);

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
                        //Funcion eliminar
                        remove: function(e){
                            
                                var codigo = e.model.id; 
                                eliminarAnexoProcesoSeco(codigo);                           

                        }
                                
     });

};