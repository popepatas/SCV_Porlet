//Proceso cv js
$(document).on("ready", inicioProceso);


function inicioProceso(){
     
       //eventos       
       $(document).on("click", ".k-Tab",cargarContenido);
       
      
      
       //Tabs
        $("#TabsContainer").kendoTabStrip({
            animation:  {
                open: {
                    effects: "fadeIn"
                }
            }
        });
        
         $("#TabContainerCaracterizacion").kendoTabStrip({
            animation:  {
                open: {
                    effects: "fadeIn"
                }
            }
        });
    //información primer Tab     
    cargarContenido.call(document.getElementById("Tab1"));
    
   
}


function cargarContenido(event){
    
     var $this = $(this);
     var id = $this.attr("id");
     var tabContainer = $('.'+id);     
     var xhr = null;
     var url = "";
     var funcion;
     
     switch(id){//Buscamos el tab en el cual se hizo click y obtenemo url y funcion de inicio del tab
        case "Tab1" :  url="InformacionGeneral.jsp";
                       funcion = cargarInformacionInfoGeneral;
            break; 
            
        case "Tab2" :  url="Visitas.jsp";
                       funcion = cargarInformacionVisitas;
            break;
        
        case "Tab3" :  url="ConsultarMonitoreo.jsp";
                       funcion = cargarProgramacionMonitoreo ;
            break;
            
        case "Tab4" :  url="VerificacionInformeCaracterizacion.jsp";
                       funcion = cargarVerificarCaracterizacion;
            break;
        
        case "Tab5" :  url="InformacionProcesoSeco.jsp";
                       funcion = cargarInformacionProcesoSeco;
            break;
            
        case "subTab2": url="InformacionTecnicaCaracterizacion.jsp";
                        funcion = cargarInformacionTecnicaCarecterizacion;
            break;
        case "subTab3": url="TasaRetributiva.jsp";
                        funcion = cargarInformacionTasaRetributiva;
     }
   //El tab ha sido cargado por primera ves?
   if(tabContainer.children().length === 0){
      
        tabContainer.load(url,funcion); 
   }else{
       
        if(id === "Tab4"){            
                  $(".subTab1").load(url,funcion);
        }
        
   }        
   
}

function cargarInformacionInfoGeneral(response, status, xhr){
   
    xhr.done(function(){    
        informacionGeneral();
    });
    
}

function cargarInformacionVisitas(response,status,xhr){
    xhr.done(function(){    
        inicioVisitas();
    });
    
}


function cargarVerificarCaracterizacion(response,status,xhr){
    xhr.done(function(){
        inicioVerificarCaracterizacion();
    });
}

function cargarProgramacionMonitoreo(response, status, xhr){
   
    xhr.done(function(){   
       
      
        inicioConsultarMonitoreo();
        
    });
    
}


function cargarInformacionTecnicaCarecterizacion(response,status,xhr){
    
    xhr.done(function(){
          inicioInformacionTecnicaCarat();
          //Cargamos el subTab numero 3, le enviamos como this el subtab3
          cargarContenido.call(document.getElementById("subTab3"));
    });
    
}

function cargarInformacionProcesoSeco(respon, status, xhr){
    
    xhr.done(function(){
         inicioInformeProcesoSeco();//InformeProcesoSeco.js
    });
    
}

function cargarInformacionTasaRetributiva (respon, status, xhr){
    xhr.done(function(){
       
        mostrarInfoTasaRetibutiva();
    });
    
}

 function obtenerTab(target) {
        var itemIndex = target[0].value;
        return tabStrip.tabGroup.children("li").eq(itemIndex);
}