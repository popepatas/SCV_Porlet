function inicioManejoLodos(){
    
    $("#volumenLodos").kendoNumericTextBox({
          format: "#"
    });
    
    $("#recoleccionLodos").kendoDatePicker({
         format:"dd/MM/yyyy"
    });
    
    var mostrarCamposLodos = function (event){
    
        var div = event.data.div;
        var $div = $("#"+div);
        
         if(this.value == "NO"){
             $div.slideDown(500);
             this.value = "SI"
         }else if(this.value == "SI"){
             $div.slideUp(500);
             limpiarCamposChck(div);
             this.value = "NO"
         }    
    
    };
    
    var asignarValorChckBx = function (event){
    
         if(this.value == "SI"){
             this.value = "NO";
         }else{
             this.value = "SI";
         }    
    
    };
    
    var limpiarCamposChck = function(div){
        
        if(div === 'contenedorExtraRecolecta'){
            var volumenLodo = $("#volumenLodos").data("kendoNumericTextBox");
            volumenLodo.value("");  
            campo.limpiar("recoleccionLodos");
            campo.limpiar("frecuenciaLodos");
        }else if(div === 'contenedorExtraDispone'){
            campo.limpiar("sitioDispoLodos");
        }
        
    };
    
     var cancelarLodos = function(){
    
      var kendoModalBox =  $("#modalBox2").data("kendoWindow");
          kendoModalBox.close();
     
     };
     
    function limpiarLodos(){

        campo.limpiar("nombreEmpresaLodos");
        campo.limpiar("recolectaLodos");
        
        var volumenLodo = $("#volumenLodos").data("kendoNumericTextBox");
        volumenLodo.value("");  
        
        campo.limpiar("recoleccionLodos");
        campo.limpiar("frecuenciaLodos");
        campo.limpiar("transporteLodos");
        campo.limpiar("disponeLodos");
        campo.limpiar("sitioDispoLodos");
        
        $("#contenedorExtraRecolecta").slideUp(500);
        $("#contenedorExtraDispone").slideUp(500);
        
        $("#modificarLodos").hide();
        $("#guardarLodos").show();

    }
    
    var obtenerParametrosLodos =  function(){
        
        var parametros = { nombreEmpresaLodos: campo.obtener("nombreEmpresaLodos"),
                            recolectaLodos : campo.obtener("recolectaLodos"),
                            volumenLodos : campo.obtener("volumenLodos"),
                            recoleccionLodos : campo.obtener("recoleccionLodos"),
                            frecuenciaLodos : campo.obtener("frecuenciaLodos"),
                            transporteLodos : campo.obtener("transporteLodos"),
                            disponeLodos : campo.obtener("disponeLodos"),
                            sitioDispoLodos : campo.obtener("sitioDispoLodos"),
                            codigoProceso : campo.obtener("codigoProceso")
                        };
        return parametros;
    };
    
    function enviarLodos(){

           var url = servlet.insertar.manejoLodos;    
           var parametros = obtenerParametrosLodos();                 

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
                    limpiarLodos();
                    generarGrillaLodos();
                    
                    var texto = "El registro ha sido almacenado exitosamente.";
                    alert(texto);

           });

          xhrGuardar.fail(function(data,status,xhr){

                    var texto = "Error al almacenar el registro";
                    alert(texto);
           });
    }
    
    
    function eliminarLodos(parametros){

        var url = servlet.eliminar.manejarLodos;
        
         if(confirm("¿Esta seguro que desea eliminar este registro?")){  
             var grid = $("#grillaLodos").data("kendoGrid");
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
     
     var actualizarLodo = function(){
           
           var url = servlet.actualizar.manejoLodos;    
           
           var parametros = {
                 nombreEmpresaLodos: campo.obtener("nombreEmpresaLodos"),
                 recolectaLodos : campo.obtener("recolectaLodos"),
                 volumenLodos : campo.obtener("volumenLodos"),
                 recoleccionLodos : campo.obtener("recoleccionLodos"),
                 frecuenciaLodos : campo.obtener("frecuenciaLodos"),
                 transporteLodos : campo.obtener("transporteLodos"),
                 disponeLodos : campo.obtener("disponeLodos"),
                 sitioDispoLodos : campo.obtener("sitioDispoLodos"),
                 codigoProceso : campo.obtener("codigoProceso"),
                 codigoLodo : campo.obtener("codigoLodo")
             };              

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
                    limpiarLodos();
                    generarGrillaLodos();
                    var texto = "El registro ha sido actualizado exitosamente.";
                    alert(texto);
                    

           });

          xhrGuardar.fail(function(data,status,xhr){

                    var texto = "Error al actualizar el registro";
                    alert(texto);
           });
     };
     
     
     function mostrarLodos(parametros){
    
     var url = servlet.consultar.manejoLodos;
       limpiarLodos();   
     $("#modificarLodos").show();
     $("#guardarLodos").hide();
    
      $.ajax(
              {
                  type:"POST",
                  data: parametros,
                  url: url,
                  cache :false,
                  success: function(data){    
                      
                        
                                       
                      campo.asignar('codigo',data[0].codigo);
                      campo.asignar('nombreEmpresaLodos',data[0].nombreEmpresa);
                      
                      
                      var bolRecolecta = true;
                      if(data[0].recolecta === 'NO'){ 
                          bolRecolecta = false;
                      }else{
                          $("#contenedorExtraRecolecta").slideDown(500); 
                      }
                      $('#recolectaLodos').prop('checked', bolRecolecta);
                      
                      var bolTransporte = true;
                      if(data[0].transporte === 'NO') bolTransporte = false;
                      $('#transporteLodos').prop('checked', bolTransporte);
                      
                      
                      var bolDispone = true;
                      if(data[0].dispone === 'NO'){ 
                          bolDispone = false;
                      }else{
                          $("#contenedorExtraDispone").slideDown(500); 
                      }
                      $('#disponeLodos').prop('checked', bolDispone);
                      
                      
                      var volumenLodo = $("#volumenLodos").data("kendoNumericTextBox");
                      volumenLodo.value(data[0].volumen) ;  
                      
                      var fecha = data[0].fecha;
                      
                      if( fecha != null && fecha != '' && fecha != undefined ){
                            campo.asignar('recoleccionLodos',data[0].fecha.substring(0,11));
                       }
                            campo.asignar('frecuenciaLodos',data[0].frecuencia);
                            campo.asignar('sitioDispoLodos',data[0].sitio);
                    
                      
                      //pntlla.iniciar() ; 
                  }
              }
           );
    
}

    
    function generarGrillaLodos(){

    var url = servlet.consultar.manejoLodos; 
    var codigo = campo.obtener("codigoProceso");
    
    var dataSource = new kendo.data.DataSource(
                        
                        {                             
                            transport: {
                                    read: {
                                        url:url,
                                        data:{opcion:1, codigo:codigo}, 
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

    $("#grillaLodos").kendoGrid({
                        height: 330,
                        width:800,
                        autoSync: true,                       
                        dataSource: dataSource,                        
                        columns:
                         [
                           
                            {field : "nombreEmpresa" , title : "EMPRESA" , width: "100px"},
                            {field : "recolecta" , title : "RECOLECTA" , width: "100x"},
                            {field : "transporte" , title : "TRANSPORTE" , width: "100px"},
                            {field : "dispone" , title : "DISPONE" , width: "100px"},                           
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
                                                     campo.asignar("codigoLodo", data.id);
                                                     mostrarLodos(parametros);  
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
                                                    codigo :  codigo                                                        
                                                 };
                           eliminarLodos(parametros);                          

                        }
                    
                           
     });

}
     $("#recolectaLodos").on("change",{div:"contenedorExtraRecolecta"},mostrarCamposLodos);     
     $("#disponeLodos").on("change",{div:"contenedorExtraDispone"},mostrarCamposLodos);
     $("#transporteLodos").on("change",asignarValorChckBx);
     $("#cancelarLodos").on("click", cancelarLodos );
     $("#limpiarLodos").on("click",limpiarLodos);
     $("#guardarLodos").on("click",enviarLodos);
     $("#consultarLodos").on("click",generarGrillaLodos);
     $("#modificarLodos").on("click",actualizarLodo);
     $("#modificarLodos").hide();
     
};