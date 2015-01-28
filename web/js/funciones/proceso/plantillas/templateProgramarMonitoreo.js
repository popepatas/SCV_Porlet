
//Generar el template de la programación de monitoreo
var cargarTemplateMonitoreo = function(setActividadesPuntos, codMonitoreo){
     var htmlTemplate   = $("#template-puntos-vertimiento").html();
     var template       = Handlebars.compile(htmlTemplate);
     var html;
     var xhrPuntos, xhrActEconomicas;
     
     //Obtenemos los puntos
     xhrPuntos = $.ajax({
                           type:"POST",
                           dataType:"json",
                           url: servlet.consultar.puntosVertimiento,
                           cache:false,
                           data:{opcion:2,codigo:$("#contrato").html()}
                        });
                        
   //Obtenemos las actividades         
    xhrActEconomicas = $.ajax({
                            type:"POST",
                            dataType:"json",
                            url: servlet.consultar.actEconomica,
                            cache:false
                        });
      
      
    var xhrTempletePuntos = $.when( xhrPuntos, xhrActEconomicas ).done(function(respPuntos,respAct){
                                    var cntx = {};
                                    var data1 = respPuntos[0];
                                     if(data1.error === undefined){
                                              cntx.puntos = respPuntos[0].puntos;
                                          var actEconomicar = campo.lista.construir(respAct[0]);

                                          htmlBody = template(cntx);

                                          $("#contenedorPuntosVertimiento").html(htmlBody);
                                          $("[name='actividadesMonitoreo']").html(actEconomicar);
                                          
                                     if(typeof setActividadesPuntos == 'function'){ 
                                          setActividadesPuntos(codMonitoreo);
                                     }
                                          
                                          $("[name='actividadesMonitoreo']").on("change", cargarTemplateInfoActividadesParametros);
                                     }else if(data1.error == 0){
                                         alert("Se ha presentado un error inesperado");
                                     }else if(data1.error == 2){
                                           alert("Este contrato no tiene ningún punto de vertimiento asociado. Dirijase a la pantalla de configuración de puntos de vertmientos.");
                                     }
                                });
     
};

var mostrarLodosInfoTec = function(){
    var url = servlet.consultar.lodosInfoTec;
    $.ajax(
              {
                  type:"POST",
                  data: { codigoProceso : campo.obtener("codigoProceso") },
                  url: url,
                  cache :false,
                  success: function(data){                
                    if(data !== "" && typeof data !== "undefined" && data !== null && data.length > 0){
                        if(data[0].diasAlMes !== null && data[0].diasAlMes !== "" && typeof data[0].diasAlMes !== "undefined"){ 
                            campo.asignar("diasAlMes",data[0].diasAlMes); 
                        }
                        if(data[0].horasAlDia !== null && data[0].horasAlDia !== "" && typeof data[0].horasAlDia !== "undefined"){ 
                            campo.asignar("horasAlDia",data[0].horasAlDia); 
                        }
                        if(data[0].preTratamiento !== null && data[0].preTratamiento !== "" && typeof data[0].preTratamiento !== "undefined"){ 
                            campo.asignar("preTratamiento",data[0].preTratamiento); 
                            if(data[0].preTratamiento == "SI"){
                                $("#contenedorCualPreTratamiento").slideDown(500);
                            }
                        }
                        if(data[0].cualPreTratamiento !== null && data[0].cualPreTratamiento !== "" && typeof data[0].cualPreTratamiento !== "undefined"){ 
                            campo.asignar("cualPreTratamiento",data[0].cualPreTratamiento); 
                        }
                        if(data[0].generacionLodos !== null && data[0].generacionLodos !== "" && typeof data[0].generacionLodos !== "undefined"){ 
                            campo.asignar("generacionLodos",data[0].generacionLodos); 
                            if(data[0].generacionLodos == "SI"){
                                $("#contenedorCualGeneracionLodos").slideDown(500);
                            }
                        }
                        if(data[0].cualGeneracionLodos !== null && data[0].cualGeneracionLodos !== "" && typeof data[0].cualGeneracionLodos !== "undefined"){ 
                            campo.asignar("cualGeneracionLodos",data[0].cualGeneracionLodos); 
                            if(data[0].cualGeneracionLodos === "3"){
                                $("#contenedorCualGeneracionLodosOtros").slideDown(500);
                            }
                        }
                        if(data[0].cualGeneracionLodosOtros !== null && data[0].cualGeneracionLodosOtros !== "" && typeof data[0].cualGeneracionLodosOtros !== "undefined"){
                            campo.asignar("cualGeneracionLodosOtros",data[0].cualGeneracionLodosOtros);
                        }
                    }
                  }
              }
           );
    
};

var cargarTemplateInfoTecnicaCaracterizacion = function(){
     mostrarLodosInfoTec();
     mostrarBotonGenerarPdf();
     var htmlTemplate   = $("#template-Puntos-InfoCaracterizacion").html();
     var template       = Handlebars.compile(htmlTemplate);
     var html;
     var xhrInfoTecnica;
     
       template  =  Handlebars.compile(htmlTemplate);
    /*   html = template(datosTemp());       
      $("#contenedorEvalPuntosVertimiento").html(html);
            
      $(".fechasMonPunto").kendoDatePicker()       */
     var xhrCaracterizacion = $.ajax({
                                    type:"POST",
                                    dataType:"json",
                                    url: servlet.consultar.informacionTecnica,
                                    cache:false,
                                    data:{codigoProceso:campo.obtener("codigoProceso")}
                                 });
                                 
     xhrCaracterizacion.done(function(response,status,xhr){
             var cntxt = {};
             cntxt.puntos = response;    
             html = template(cntxt);
             
         $("#contenedorEvalPuntosVertimiento").html(html);
         $(".fechasMonPunto").kendoDatePicker({
                format:"dd/MM/yyyy"
          });
         $(".tiempoInfoTecnicas").kendoTimePicker();
         cerrarProcesoInfoTecnica();
     });
        
     
    
}; 

var cargarTemplateInfoActividadesParametros = function(){
    
    var $this = $(this);
    var parm = {
                opcion:2,
                actividad:this.value                
               };
               
     var htmlTemplate   = $("#template-actividades-parametros").html();
     var template       = Handlebars.compile(htmlTemplate);
     var htmlInsert;
        
     var xhrActParm =   $.ajax({
                            url: servlet.consultar.actvParam,
                            type:'POST',
                            data: parm,
                            dataType:'json',
                            cache:false

                        });
                        
        xhrActParm.done(function(response,status,xhr){
            var cntxt ={};
            cntxt.parametros = response;
            htmlInsert = template(cntxt);
            //Se revisa si ya ha sido creado
            $this.next(".contenedorInfoActParam").remove();
            $this.after(htmlInsert);
        });  
        
        
        xhrActParm.fail(function(){
            alert("Problema al iniciar por favor recarge la pagina.");
        });
  
    
};

//Genera el template de las verificaciones
var cargarTemplateVerificacionInfoCaracterizacion = function(){
         
     var htmlTemplate   = $("#template-Preguntas-ver-caracterizacion").html();
     var template       = Handlebars.compile(htmlTemplate);
     var html;
     var xhrRequisitos;
     mostrarDevolucionCaracterizacion();
     xhrRequisitos = $.ajax({
                            type:"POST",
                            url: servlet.consultar.verificaInfoCaracterizacion,
                            dataType:"json",
                            cache :false,
                            data:{
                                    tipoInforme:campo.obtener("tipoInforme"),
                                    codigoProceso:campo.obtener("codigoProceso")
                                 }
                       });
     xhrRequisitos.done(function(response,status,xhr){
         
         var cntxt = {};
             cntxt.requisitos = response;    
             html = template(cntxt);
     
        $("#ContenedorListaCheckeoInformes").html(html);
        cerrarProcesoVerificar();
         
         
     });
     
      xhrRequisitos.fail(function(response,status,xhr){
        
         alert(msg.consultar.error);
         
         
     });
     
    
    
};
/*----------------------------------HELPERS------------------------------------*/
//Helper de Bloque Para Cambiar el contexto y generar una subPlantilla NO array
Handlebars.registerHelper('with', function(contexto,options){

    return options.fn(contexto);
    
    
});

Handlebars.registerHelper('list', function(contexto, bloque){
    
    var text ="";
    
    for(var i=0; i < contexto.length; i++){
        text += bloque.fn(contexto[i]);
    }
    
    return text;
    
});

//Helper para generar Checkbox en base a voler de SI y NO
Handlebars.registerHelper('checkbox', function(valor, nombre){
  
 var check   = campo.convertirBool(valor) == true ? "checked" : "" ;
    
  var html = '<input type="checkbox"  name="'+nombre+'" '+ check +' />';
  return new Handlebars.SafeString(html);
    
    
});

//Helper para generar un icono de alerta
Handlebars.registerHelper('alert', function(valor, nombre){
    
     var html ='';
       if(valor === 'NO'){

           html = '<img src="../images/cancel.png" class="advertencia" />';
       } 

     return new Handlebars.SafeString(html);    
    
});


Handlebars.registerHelper('selectedList', function(valor){
  
    var select = "";
    var html = "";

    if(valor != "" && valor != undefined && valor != null){
         select = "selected";
    }
    
    html  =  '<option id="" ></option>'+'<option id="<" '+select+' > < </option>';
  
    return new Handlebars.SafeString(html);

    
});



//Helper que emula el funcionamiento del IF
Handlebars.registerHelper('if', function(valor1, condicional, valor2,options){
    var resultado = false;
    switch(condicional.toUpperCase()){
        
        case "EQUAL" :  valor1 == valor2 ? resultado = true : resultado = false;
            break;
        case  "GREATER":  valor1 > valor2 ? resultado = true : resultado = false;
            break;
        case "LESS" :  valor1 < valor2 ? resultado = true : resultado = false;
            break;
        case  "QGREATER":  valor1 >= valor2 ? resultado = true : resultado = false;
            break;
        case "QLESS" :  valor1 <= valor2 ? resultado = true : resultado = false;
            break;
        
    }
    
    if(resultado = true){
         return options.fn(this);
    }else{
        return "";
    }
   
    
});

var datosTemp = function(){
    
    var cntxt={}; 
    cntxt.puntos=[{
                codigo: 1,
                ubicacion: "Cra 56 # 78 - 27",
                latitud: null,
                longitud:null,
                ciiu:344,
                descripcionCiiu:"METALES", 
                caudalPromedio: 456,
                jordanadaProductivaDia:4,
                jordanadaProductivaHoras:3,
                jordanadaProductivaObsev:"OK",    
                jornadas:[
                                       {
                                            nombre:"1",
                                            cargaDBO : 34,
                                            cargaSST: 45,
                                            horaInicio:"2:00 AM",
                                            horaFin:"8:00 AM",
                                                   tabla:[
                                                                 {
                                                                           parametro:"PH",
                                                                           codigoParametro: 23,
                                                                           rangoInicial:1,
                                                                           rangoFinal:2,
                                                                           valorInforme:1.5,
                                                                           cumple:"SI",
                                                                           observacion: null

                                                                   },
                                                                   {
                                                                           parametro:"DBO",
                                                                           codigoParametro: 2,
                                                                           rangoInicial:1,
                                                                           rangoFinal:5,
                                                                           valorInforme:4,
                                                                           cumple:"SI",
                                                                           observacion: null

                                                                   },	     			
                                                                   {
                                                                           parametro:"SST",
                                                                           codigoParametro: 3,
                                                                           rangoInicial:1,
                                                                           rangoFinal:8,
                                                                           valorInforme:4,
                                                                           cumple:"SI",
                                                                           observacion: null

                                                                   }
                                                           ]
                                                   },
                                                   {
                                                           nombre:"2",
                                                           cargaDBO : 34,
                                                           cargaSST: 45,
                                                           horaInicio:"8:00 AM",
                                                                   horaFin:"12:00 AM",
                                                           tabla:[
                                                                           {
                                                                                   parametro:"PH",
                                                                                   codigoParametro: 21,
                                                                                   rangoInicial:1,
                                                                                   rangoFinal:2,
                                                                                   valorInforme:3,
                                                                                   cumple:"NO",
                                                                                   observacion: null

                                                                           },
                                                                           {
                                                                                   parametro:"DBO",
                                                                                   codigoParametro: 2,
                                                                                   rangoInicial:1,
                                                                                   rangoFinal:5,
                                                                                   valorInforme:4,
                                                                                   cumple:"SI",
                                                                                   observacion: null

                                                                           },	     			
                                                                           {
                                                                                   parametro:"SST",
                                                                                   codigoParametro: 3,
                                                                                   rangoInicial:1,
                                                                                   rangoFinal:5,
                                                                                   valorInforme:7,
                                                                                   cumple:"NO",
                                                                                   observacion: null

                                                                           }
                                                           ]
                                                   }
                           ]
                   }];

            return cntxt;

             
    
};