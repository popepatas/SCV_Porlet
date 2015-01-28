
 var validarEstadoCierre = function(){
     
     var estado = campo.obtener("estadoProceso");
     
     if(estado == "10" ||  estado == "11" || estado == "5" || estado == "3"){
         return true;
     }else{
         return false;
     }
 };

 var cerrarProcesoInformacionGeneral= function (){
      
      var validar = validarEstadoCierre();
      if(validar){
          $("#finalizarProceso").hide();
           var k_fecha = $("#fechaAsesoria").data("kendoDatePicker"); 
               k_fecha.enable(false);
          $("#div_contenedorInformaciónGeneral").find("input, select, textarea").addClass("cv-disabled");
            elmto.deshabilitar("tipoInforme");               
            elmto.deshabilitar("tipoContacto");
            elmto.deshabilitar("contacto");
            elmto.deshabilitar("asunto");            
            elmto.deshabilitar("informo");
            elmto.deshabilitar("requiereVisita");
            elmto.deshabilitar("codigoProceso");
            $("#guardar").hide();
            
       }
        
 };
var cerrarProcesoVerificar= function (){
     
       var validar = validarEstadoCierre();
      if(validar){
          
           $("[name='cheackeado']").attr("disabled", true);
            
       }
        
 };
 
 var cerrarProcesoInfoTecnica= function (){
     
      var validar = validarEstadoCierre();
      if(validar){
          
            $('#files').attr("disabled",true);
            $('.campoDecimal').attr("disabled",true);
            $('.observParamPunto ').attr("disabled",true);
            $(".campoEntero ").attr("disabled", true);
            $("[name='jordanadaProductivaObsev']").attr("disabled", true);
            $("[name='horaInicio']").attr("disabled", true);
            $("[name='horaFin']").attr("disabled", true);
       }
        
 };
 
 var cerrarProcesoTasa = function (){
     var validar = validarEstadoCierre();
     if(validar){
        $('#porcentajeRemocionSST').attr("disabled",true);
        $('#porcentajeRemocionDBO').attr("disabled",true);
        $('#valorTasaCobrada').attr("disabled",true);
     }
 };