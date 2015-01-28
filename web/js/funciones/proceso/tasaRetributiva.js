
var incioTasaRetributiva = function(){
    
  $(document).on("blur","#porcentajeRemocionSST, #porcentajeRemocionDBO",calcularValoresTasaRetibutiva);
  $(document).on("blur","#valorTasaCobrada",calcularTotalTasaCobrar);
  $("#guardarTasaRetributiva").on("click",guardarTasaRetributiva);
  
};

var  mostrarInfoTasaRetibutiva= function(){
    
   var url = servlet.consultar.tarifas;     
    
   var urlTasa = servlet.consultar.tasaRetributiva;
    
    var xhrSst =  $.ajax({   
                  type:"POST",
                  data:{ opcion : 2, codigo : 2},
                  url: url,
                  cache :false
        });
    
    var xhrDbo =  $.ajax({   
                  type:"POST",
                  data:{ opcion : 2, codigo : 1},
                  url: url,
                  cache :false
        });    
        
        var xhrTasa =  $.ajax({   
                            type:"POST",
                            data:{ codigoProceso : campo.obtener("codigoProceso")},
                            url: urlTasa,
                            cache :false
                        });    
                    
        $.when(xhrSst,xhrDbo,xhrTasa).done(function(data1, data2, data3){                  
            incioTasaRetributiva();
            var dataTasa = data3[0].cargasParam;
              campo.asignar("valorTasaCobrada",data3[0].valorTasaCobrada); 
              campo.asignar("valorTotalPagar",data3[0].valorTotalTasaPagar);
            
            if(dataTasa.length > 0){
                
               
                if(dataTasa[0].codigoParametro == "16"){//DBO
                    
                    campo.asignar("porcentajeRemocionDBO",dataTasa[0].procentajeRemocion);
                    campo.asignar("CargaTotaDBO",dataTasa[0].valorCarga);
                    campo.asignar("tarifaDbo",dataTasa[0].valorTarifa);
                    campo.asignar("valorTasaDBO",dataTasa[0].valorTasa);
                }
                
                if(dataTasa[1].codigoParametro == "17"){//SST                    
                    campo.asignar("porcentajeRemocionSST",dataTasa[1].procentajeRemocion);
                    campo.asignar("CargaTotalSST",dataTasa[1].valorCarga);
                    campo.asignar("tarifaSst",dataTasa[1].valorTarifa);
                    campo.asignar("valorTasaSST",dataTasa[1].valorTasa);
                }
               
                
            }else if(dataTasa.length == 0){
                
                campo.asignar('tarifaSst',data1[0].valor);
                campo.asignar('tarifaDbo',data2[0].valor);
                
            }
            
            calcularValoresTasaRetibutiva(); calcularTotalTasaCobrar();            
            cerrarProcesoTasa();

        });
    
    
};

var calcularValoresTasaRetibutiva = function(){
    
    var valorTasaPorSst =  ( parseFloat(campo.obtener("CargaTotalSST")) *( 1 - (parseFloat(campo.obtener("porcentajeRemocionSST")/100)))) * parseFloat(campo.obtener('tarifaSst'));
    var valorTasaPorDbo =  ( parseFloat(campo.obtener("CargaTotaDBO"))  * ( 1 - (parseFloat(campo.obtener("porcentajeRemocionDBO")/100)))) * parseFloat(campo.obtener('tarifaDbo'));
    
            
       if(!isNaN(valorTasaPorSst)){
             campo.asignar("valorTasaSST",valorTasaPorSst.toFixed(5));
             
        }else{ 
             campo.asignar("valorTasaSST","");  
        }
        
       if(!isNaN(valorTasaPorDbo)){
           campo.asignar("valorTasaDBO",valorTasaPorDbo.toFixed(5));
           
        }else{ 
            campo.asignar("valorTasaDBO","");  
        }
              
       if( campo.obtener("valorTasaDBO") != '' &&   campo.obtener("valorTasaDBO") != '' ){ 
           var totalAnio = (valorTasaPorSst+valorTasaPorDbo)*12;
           $("#valorTasaTotalAnio").val(totalAnio.toFixed(5));
       }
};


var calcularTotalTasaCobrar = function(){
    
  var valorTasaTotalAnio = campo.obtener("valorTasaTotalAnio");
  var valorTasaCobrada = campo.obtener("valorTasaCobrada");
  var total = 0;
    if(valorTasaTotalAnio != '' && valorTasaCobrada != ''){
        
        total = valorTasaTotalAnio - valorTasaCobrada;
        campo.asignar("valorTotalPagar",total.toFixed(5));
    };  
       
        
};


var guardarTasaRetributiva = function(){
 
     var cargasParam = [
                                            {
                                             codigoParametro: "16",
                                             valorTarifa:campo.obtener("tarifaDbo"),
                                             procentajeRemocion:campo.obtener("porcentajeRemocionDBO"),
                                             valorTasa:campo.obtener("valorTasaDBO"),
                                             valorCarga:campo.obtener("CargaTotaDBO")
                                            },
                                            {
                                             codigoParametro: "17",
                                             valorTarifa:campo.obtener("tarifaSst"),
                                             procentajeRemocion:campo.obtener("porcentajeRemocionSST"),
                                             valorTasa:campo.obtener("valorTasaSST"),
                                             valorCarga:campo.obtener("CargaTotalSST")
                                            }
                                          ];
                                          
     var parametros = {
                           codigoProceso : campo.obtener("codigoProceso"),
                           valorTasaCobrada : campo.obtener("valorTasaCobrada"),
                           valorTotalTasaPagar :  campo.obtener("valorTotalPagar"),
                           cargasParam :  $.toJSON(cargasParam)
                   };
     
     var url =  servlet.insertar.tasaRetributiva;
     var xhrTasa = $.ajax(
                            {
                            type:"POST",
                            data: parametros,
                            url: url,
                            cache :false
                           }        
                    );
    
    return xhrTasa;
    
};