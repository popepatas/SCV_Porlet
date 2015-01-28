
function inicioVerificarCaracterizacion(){
        
        cargarTemplateVerificacionInfoCaracterizacion();
        $("#tipoDevolCaracterizacion").on("change", mostrarInfoDevolCaracterizacion);
        $("#guardarVerfCaracterizacion").on("click",guardarRegistroVerificacionInfoCaract);
        $("#pdfDevolucionCaract").on("click",generarPDFDevolucionCaracterizacion);
        
    $("#fechaDevolCaracterizacion, #fechaEntDevolCaracterizacion").kendoDatePicker({
        format: "dd/MM/yyyy"
    });
        
};
var generarPDFDevolucionCaracterizacion = function(){
    var codigoProceso = campo.obtener("codigoProceso");
    var url="/SCV_Portlet/GenerarPdfServlet?opcion=3&codigoProceso="+codigoProceso;
    window.open(url,"_blank");      
};

var mostrarLinkPdfDevCaracterizacion = function(){
    var fechaDevolCaracterizacion = campo.obtener("fechaDevolCaracterizacion");
    if(fechaDevolCaracterizacion !== null && fechaDevolCaracterizacion !==""){
        $( "#pdfDevolucionCaract" ).show();
    }
}

var mostrarInfoDevolCaracterizacion = function(){

    if(this.value != ''){
        $("#contenedorInfoDevolucionCaracterizacion").slideDown("500");
    }else{
        $("#contenedorInfoDevolucionCaracterizacion").slideUp("500");
    }

};

function mostrarDevolucionCaracterizacion(){
    
     var url = servlet.consultar.devolucion;
    
      $.ajax(
              {
                  type:"POST",
                  data: { codigoProceso : campo.obtener("codigoProceso") },
                  url: url,
                  cache :false,
                  success: function(data){                    
                    if(data !== "" && typeof data !== "undefined" && data !== null && data.length !== 0){
                      if(data.tipoDevolCaracterizacion !== null && data.tipoDevolCaracterizacion !== "" && typeof data.tipoDevolCaracterizacion !== "undefined"){
                          campo.asignar("tipoDevolCaracterizacion",data.tipoDevolCaracterizacion);
                          mostrarInfoDevolCaracterizacion();
                      }
                      if(data.fechaEntDevolCaracterizacion !== null && data.fechaEntDevolCaracterizacion !== "" && typeof data.fechaEntDevolCaracterizacion !== "undefined"){
                          campo.asignar("fechaEntDevolCaracterizacion",data.fechaEntDevolCaracterizacion);
                      }
                      if(data.observacionDevolCaracterizacion !== null && data.observacionDevolCaracterizacion !== "" && typeof data.observacionDevolCaracterizacion !== "undefined"){
                          campo.asignar("observacionDevolCaracterizacion",data.observacionDevolCaracterizacion);
                      }
                      if(data.fechaDevolCaracterizacion !== null && data.fechaDevolCaracterizacion !== "" && typeof data.fechaDevolCaracterizacion !== "undefined"){
                          campo.asignar("fechaDevolCaracterizacion",data.fechaDevolCaracterizacion);
                          mostrarLinkPdfDevCaracterizacion();
                      }
                  }
                       
                }
              }
           );
    
}
var obtenerParametrosDevolCaracterizacion = function(){
    
    var parametros = {
        fechaDevolCaracterizacion : campo.obtener("fechaDevolCaracterizacion"),
        observacionDevolCaracterizacion : campo.obtener("observacionDevolCaracterizacion"),
        fechaEntDevolCaracterizacion : campo.obtener("fechaEntDevolCaracterizacion"),
        tipoDevolCaracterizacion : campo.obtener("tipoDevolCaracterizacion"),
        codigoProceso : campo.obtener("codigoProceso")
    }
    
    return parametros;
};

var enviarDevolucionCaracterizacion = function(){
    var parametros = obtenerParametrosDevolCaracterizacion();
    var url = servlet.insertar.devolucion;
    $.post(url,parametros,function(){
        mostrarLinkPdfDevCaracterizacion();
    });
}

var obtenerParametrosVerificacionCaract = function(){
     
    var arreglo = [] ; 
    $(".containerRequisito").each(function(index,obj){
            var $obj = $(this);
            var $codigo = $obj.find("input[type='hidden']");
            var $checkeo = $obj.find("input[type='checkbox']");
            var respCheckeo ="NO";
            var rqsto = {};
              
            rqsto.codigo = $codigo.val();
            if( $checkeo.attr("checked") == "checked"){
                respCheckeo = "SI";
            }
            rqsto.checkeado = respCheckeo;
            arreglo.push(rqsto);

     });
       
    return arreglo;  
};

    
   var guardarRegistroVerificacionInfoCaract = function(){

                var url = "/SCV_Portlet/InsertarVerificacionInfoCaracterizacion";
                var parametros = {};
                    parametros.respuestas = $.toJSON(obtenerParametrosVerificacionCaract());
                    parametros.codigoProceso = campo.obtener("codigoProceso");
               
                if(parametros.codigoProceso != ''){
                     elmto.deshabilitar('guardarVerfCaracterizacion');
                     enviarDevolucionCaracterizacion();
                      var xhr = $.ajax({
                                    type:"POST",
                                    data: parametros,
                                    url: url,
                                    cache :false
                                });

                      xhr.done(function(response,status,xhr){
                              alert("El registro ha sido almacenado exitosamente.");
                              elmto.habilitar('guardarVerfCaracterizacion');                                
                      });
              } 

        };
        
