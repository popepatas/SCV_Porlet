$(document).on("ready",tabsProceso);

function tabsProceso(){
   
   
    var contenido = $('div#contenido');
    var url_anterior = '';
    var extension = '.html';
    var original = window.location;
    
    
    $('ul#nav a').each(function(){ //Cambiamos los href por el contenido del atributo data-hash
        $(this).attr('href', $(this).data('hash'));
    });
    
    $('ul#nav a').on('click', function(e){          
    
        var hash = $(this).attr('href'); 
        var revisar = revisarURL(hash);
        
        e.preventDefault();  
        
        revisar.done(function(){
            window.location.href = hash; // Buen hash, cambiemoslo en la URL            
        });
        
        revisar.fail(function(){
            window.location.href = '#error';
        });
        
        revisar.always(function(datos){
            contenido.html(datos);
        });
         
    });
     
    revisarURL().always(function(datos){
    // Si hay un hash en la URL (ej, copiamos y pegamos en una conversación) cargará la URL correcta.
        contenido.html(datos);
    }); 
    
    setInterval(function(){
        
        
        revisarURL().fail(function(){
            window.location.href = '#error';
        }).always(function(datos){
            contenido.html(datos);
        });
        
        
    },250); // Revisamos cualquier cambio en el Hash cada 250 milisegundos
 
    function revisarURL (hash){
        
        var deferred = $.Deferred();
        var cargarPagina;
        
        if (!hash) { // Esto ocurre cuando se pulsa el botón de atrás o adelante en el navegador o al pasar una URL con hash
            hash = window.location.hash;
        }
        
        if (!hash) { // Esto puede pasar si es la primera URL - index.html en nuestro caso
            var url = window.location.pathname; // Obtenemos la URL completa
            var archivo = url.substring(url.lastIndexOf('/')+1); // Nos quedamos con el nombre del archivo (index.html)
            hash = archivo.replace(extension,''); // Le quitamos la extensión para convertirlo en "hash"
        }
        
        if (hash !== url_anterior){
            
            url_anterior = hash; 
            cargarPagina = cargarPagina(hash);
            
            cargarPagina.done(function(data){ 
                    var html = $(data);
                    var filtrado = html.find('#contenido');
                    deferred.resolve(filtrado.html());
                });
                
            cargarPagina.fail(function(){ // La URL no existe                  
                deferred.reject('<p>La página no existe.</p>'); // Rechazamos nuestro deferred      
            });
            
        }
        return deferred.promise(); // Devolvemos una promesa, no un deferred
    }
    
    function cargarPagina(hash){
        
        url = hash.replace('#','');  //Quitamos la almohadilla
        return $.ajax({
            url: url + extension,
            async: true,
            dataType: "html"});         
    }
    
    
}
