/***************** FUNCIONES ********************/
$(document).on("ready", main);

function main() {
    placeHolderBrowsers();
    stickyNavigation();
    others();
    jQuery(".eliminarEstado").on("click", confirmarEliminacion);
    jQuery("#ConetenedorPublicaciones").on("mouseenter",".PublicacionMuroInformacion",hoverInEliminarEstado);
    jQuery("#ConetenedorPublicaciones").on("mouseleave",".PublicacionMuroInformacion", hoverOutEliminarEstado);
    jQuery("#divform172,#divform166").on("submit", validarEnvioEstado);
    jQuery(".CuerpoEstado").crearLinks(); 
}

function validarEnvioEstado(){
    if(tuga_get('texto') == '' || tuga_get('texto') == null){

         event.preventDefault();
    }
  }

function placeHolderBrowsers() {

    /************************ ESTA FUNCION PERMITEN EL USO DE PLACEHOLDERS DE HTML5 EN TODOS LOS NAVEGADORES ******************/

    $('[placeholder]').parents('form').submit(function() {
      $(this).find('[placeholder]').each(function() {
        var input = $(this);
        if (input.val() == input.attr('placeholder')) {
          input.val('');
        }
      })
    });

    $('[placeholder]').focus(function() {
      var input = $(this);
      if (input.val() == input.attr('placeholder')) {
        input.val('');
        input.removeClass('placeholder');
      }
    }).blur(function() {
      var input = $(this);
      if (input.val() == '' || input.val() == input.attr('placeholder')) {
        input.addClass('placeholder');
        input.val(input.attr('placeholder'));
      }
    }).blur();
}


function stickyNavigation () {
    /**************************************************** Sticky navigation ***********************************************/

    // grab the initial top offset of the navigation

    if($('#ContenedorMenuPrincipal').offset()){ 
      var sticky_navigation_offset_top = $('#ContenedorMenuPrincipal').offset().top;
    }

    // our function that decides weather the navigation bar should have "fixed" css position or not.
    var sticky_navigation = function(){
        var scroll_top = $(window).scrollTop(); // our current vertical position from the top
         
        // if we've scrolled more than the navigation, change its position to fixed to stick to top,
        // otherwise change it back to relative
        if (scroll_top > sticky_navigation_offset_top) { 
            $('#ContenedorMenuPrincipal').css({ 'position': 'fixed', 'top':0, 'left':0 });
        } else {
            $('#ContenedorMenuPrincipal').css({ 'position': 'relative' }); 
        }   
    };
     
    // run our function on load
    sticky_navigation();
     
    // and run it again every time you scroll
    $(window).scroll(function() {
         sticky_navigation();
    });
}


function others (argument) {
    /**************************************************** TARGET BLANK ************************************************************/

    $('A[rel="_blank"]').each(function(){
               $(this).attr('target', '_blank');
    });

    
}
/*****************************************************************
 * DescripciÃ³n: Trae por Ajax los estados mas antiguos de la persona.
 * Usado por : Perfil y por Inicio
 *****************************************************************/

function cargarMasContenido(event) {

        var jump = tuga_get("jump"); //Cantidad de registro que se deben cargar por cada llamada
        var now  = tuga_get('actual'); //El numerÃ³ del registro desde el cual deberÃ¡ traer los estados
        var futuro = parseInt(now) + parseInt(jump); // El nÃºmero futuro que deberÃ¡ traer en el proximo llamado
        var jMasEstados = jQuery("#mas_estados"); // Div que contiene el link de cargar mas..
        var nombrefun = event.data.fun; // nombre de la funciÃ³n getData de tuga
        var param = {limite:jump, desde:futuro}; //parametros que recibe la funciÃ³n getData
        
        jQuery("#loader-mini").show();
        jQuery("#cargar-ajax").hide();

            jQuery.ajax({//FunciÃ³n que trae y ubica los estados en la pantalla.
                type: 'POST',
                url:  '/Tuga/getData?_func=' + nombrefun,
                data: param,
                dataType: "json",
                success: function (data){
                    var estado = data["_tuga_getdata_error_"];
                    var cadena = data["data"]+"";

                    if(!estado){

                         jQuery("#ConetenedorPublicaciones").append(cadena);  

                         if(cadena != '' && cadena != null){
                            jQuery("#loader-mini").hide();
                            jQuery("#cargar-ajax").show();
                            tuga_set('actual',futuro);  

                         }else{
                            jQuery("#cargar-ajax").html("No se encontr&oacute; mas informaci&oacute;n para mostrar.").show();
                            jQuery("#loader-mini").hide();
                         }
                      
                    }else{
                        
                        jQuery("#ConetenedorPublicaciones").html(estado);
                        
                    }
                           
            }
    });     

   }


/*******************************************************************
 * DescripciÃ³n: Trae por Ajax los mensajes mas antiguos del usuario.
 * Usado por : Mensajes
 *******************************************************************/

   function cargarMasContenidoMensajes(event) {
       
        var jump = tuga_get("jump");//Cantidad de registro que se deben cargar por cada llamada
        var now  = tuga_get('actual');//El numerÃ³ del registro desde el cual deberÃ¡ traer los estados
        var futuro = parseInt(now) + parseInt(jump);// El nÃºmero futuro que deberÃ¡ traer en el proximo llamado
        var jMasEstados = jQuery("#mas_estados");// Div que contiene el link de cargar mas..
        var nombrefun = event.data.fun;// nombre de la funciÃ³n getData de tuga
        var tipo = tuga_get('tipo'); // tipo de mensaje que se desea buscar
          if(tipo == 'recibidos'){ tipo = 2}else if(tipo == 'enviados'){tipo = 1};
        var param = {limite:jump, desde:futuro,tipom:tipo};//parametros que recibe la funciÃ³n getData
        
        jQuery("#loader-mini").show();
        jQuery("#cargar-ajax").hide();

            jQuery.ajax({
                type: 'POST',
                url:  '/Tuga/getData?_func=' + nombrefun,
                data: param,
                dataType: "json",
                success: function (data){
                    var estado = data["_tuga_getdata_error_"];
                    var cadena = data["data"]+"";

                    if(!estado){

                         jQuery("#ConetenedorPublicaciones").append(cadena);  

                         if(cadena != '' && cadena != null){
                            jQuery("#loader-mini").hide();
                            jQuery("#cargar-ajax").show();
                            tuga_set('actual',futuro);  

                         }else{
                            jQuery("#cargar-ajax").html("No se encontrÃ³ mas informaciÃ³n para mostrar.").show();
                            jQuery("#loader-mini").hide();
                         }
                      
                    }else{
                        
                        jQuery("#ConetenedorPublicaciones").html(estado);
                        
                    }                           
            }
    });     

   }


   function confirmarEliminacion(){
            elem = this;
            jConfirm("Esta seguro que desea eliminar este estado?", "Elimnar",
                 function(r){
                  
                        if(r){
                            borrarEstado(elem);
                        }
                }
            );            
   }

/*******************************************************************
 * DescripciÃ³n: Elimina el estado seleccionado por el usuario.
 * Usado por : Mensajes, Inicio, Perfil
 *******************************************************************/
   function borrarEstado (elem) {
        console.log(elem);
        var jElem =  jQuery(elem);
        var msg   =  jElem.find('input').val();
        var fun   = "borrarestadosusuario";
        var param = {msg:msg};

         $.ajax({
                type: 'POST',
                url:  '/Tuga/putData?_func=' + fun,
                data: param,
                dataType: "json",
                success: function (data){
                            var estado = data["_tuga_getdata_error_"];
                    
                            if(!estado){
                               jElem.parent().parent().fadeOut(500,function() { 
                                                                    jQuery(this).remove();
                                                               });    
                                                          
                            }else{
                               jAlert("El estado no puedo ser eliminado. Por favor intente nuevamente","Advertencia");
                            }
                                       
                       }
            });
        
   }

 

    function hoverInEliminarEstado(){ 

        jQuery(this).find(".eliminarEstado").show()
    }
     function hoverOutEliminarEstado(){ 

        jQuery(this).find(".eliminarEstado").hide()
    }


$.fn.crearLinks = function() {
        var regexp = /((ftp|http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?)/gi;
        this.each(function() {
            $(this).html(
                $(this).html().replace(regexp,'<a href="$1">$1</a>')
            );
        });
        return $(this);
    }
