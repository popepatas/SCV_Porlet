

function inicioProgramacionMonitoreo(mostrarRegistroMonitoreo, parametrosMonitoreo, opcion){
        
    /*Parametros*/    
        var opn  = opcion || 0 ;  //Este parametro permite que se defina si es para registrar o para actualizar
              
    $("#duraccionMonitoreo").on('keypress', validarEscritura);   
    /*Funciones*/
    validacionesKendo();
       
    
    /*cargar Listas*/
     var urlLab = servlet.consultar.laboratorios;
     var xhrLab = campo.lista.crear("laboratorioMonitoreo",urlLab,{opcion:1});
    
     var urlConsl = servlet.consultar.consultores;
     var xhrConsul = campo.lista.crear("consultorMonitoreo",urlConsl, {opcion:1});
    
       $.when(xhrLab,xhrConsul).done(function(){
            /*Solo se ejecuta si se edita desde consultar cliente*/ 
            if(opn == 1){
                    
                        mostrarRegistroMonitoreo(parametrosMonitoreo);
             }else{
                
                cargarTemplateMonitoreo();
                var kendoModalBox = modalBox.abrir("modalBox",null,"1000px","Monitoreo", null);                                
                    kendoModalBox.open();
             }

        });
    
    
    /*DatePicker*/
    $("#fechaMonitoreo").kendoDatePicker({
         format:"dd/MM/yyyy"
    });
    
    $("#duraccionMonitoreo").on("keyup",function(){
       return campo.numerico(event); 
    });
    
     /*timePicker*/
    $("#horaInicioMonitoreo,#horaFinMonitoreo").kendoTimePicker();
   
    /*Eventos*/
    $("#GuardaMonitoreo").on("click", enviarFormularioMonitoreo);
    $("#cancelarProgMonitoreo").on("click", cancelarMonitoreo );
    

    
    /*Validador*/
    $("#div_registrar_monitoreo").kendoValidator({
        
            rules: {
                date: function (input) {
                    var attr = input.attr("data-role");
                    if( attr == "datepicker"){ 
                        var fecha = kendo.parseDate(input.val(), "dd/MM/yyyy");
                        return fecha instanceof Date;
                    }
                    return true;
                },
                 hora:function(input){
                        var validar = true; 
                        var valor; 
                        var attr = input.attr("data-campo");
                        if(attr === "hora"){
                            valor = input.val();                        
                            validar = campo.hora.validar(valor);
                            
                        }
                        
                        return validar;
                 },
                 /*horaMayor :function(input){
                     
                        var validar = true;                         
                        var attr = input.attr("data-campo");
                        
                           if(attr === "hora"){
                                 var dataType = input.data("campoHoramayor");
                                 if(dataType != undefined && dataType != '' ){                                     
                                    var horaInicial = $("#"+dataType).data("kendoTimePicker").value();
                                    var horaFinal = input.data("kendoTimePicker");                        
                                    
                                     if(horaFinal > horaInicial){
                                        validar =  true;
                                         
                                     }else{
                                         validar = false;
                                     }
                                 }
                          }
                        
                        return validar;
                     
                 },*/
                 listaAct:function(input){
                     var validar = true;
                     var attrName = input.attr("name");
                    
                            //seleccionamos todas las actividades 
                       if(attrName == "actividadesMonitoreo") { 
                            var actividades = $("[name='actividadesMonitoreo']");
                                actividades.each(function(index,obj){

                                    if(obj.value == ''){ //si alguna no tiene valor seleccionado se genera la alerta
                                        
                                        validar = false;
                                    }
                             });
                         }
                                            
                     return validar;
                 }
            },
            messages:{
                listaAct:" Debe seleccionar una actividad ecónomica por cada Punto de vertimiento."
            }
        });
        
        
        
}


function validarEscritura() {
   
   return campo.numerico(event);
}

 var cancelarMonitoreo = function(){
    
      var kendoModalBox =  $("#modalBox").data("kendoWindow");
          kendoModalBox.close();
     
};



 var  enviarFormularioMonitoreo = function(){
    
    var validador = $("#div_registrar_monitoreo").data("kendoValidator");
    var status     = $(".status");
    
    if(validador.validate()){ 
        
            
                
                guardarRegistroMonitoreo(); 
                
            
            
      
    }


};

function obtenerParametroMonitoreo(){
    
    var ArrayPuntos = [];
    
    /*Obtiene los puntos de vertimiento*/
    $(".contenedorPunto").each(function(index,obj){

            var $this = $(this);
            var campos =  $this.find('input, select');
            var punto= {};

            campos.each(function(indexCampo, objCampo){
                    objCampo.type === "hidden" ? punto.codigo = objCampo.value :  punto.actividad = objCampo.value;
            });
        ArrayPuntos.push(punto);

    });
       
    //Obtiene parametros de la pantalla.
    var parametros = {                            
        codigoProceso  : campo.obtener("codigoProceso"),
        fechaMonitoreo : campo.obtener("fechaMonitoreo"),
        laboratorioMonitoreo : campo.obtener("laboratorioMonitoreo"),
        consultorMonitoreo : campo.obtener("consultorMonitoreo"),
        horaInicioMonitoreo: campo.obtener("horaInicioMonitoreo"),
        horaFinMonitoreo : campo.obtener("horaFinMonitoreo"),
        puntosVertimiento:   $.toJSON(ArrayPuntos),
        observacionesReprogramacion : campo.obtener("observacionesReprogramacion"),
        duracionMonitoreo : campo.obtener("duraccionMonitoreo")
    };
                      
    return parametros;
}

function guardarRegistroMonitoreo(){
    
       var url = servlet.insertar.GuardaMonitoreo;    
       var parametros = obtenerParametroMonitoreo();                 

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
                generarGrillaMonitoreo();
                
                $("#GuardaMonitoreo").val("Guardar");
                var kendoModalBox = $("#modalBox").data("kendoWindow");                                
                kendoModalBox.close();
                //limpiarFormularioMonitoreo();
                campo.asignar("informo", true);
                alert(texto);
       });
       
      xhrGuardar.fail(function(data,status,xhr){
             
                var texto = "Error al almacenar el registro";
                 alert(texto);
       });
}

   function limpiarFormularioMonitoreo(){
        campo.limpiar("codigoProceso");
        campo.limpiar("fechaMonitoreo");
        campo.limpiar("laboratorioMonitoreo");
        campo.limpiar("consultorMonitoreo");
        campo.limpiar("horaInicioMonitoreo");
        campo.limpiar("horaFinMonitoreo");
        campo.limpiar("duraccionMonitoreo");
          
   } 
