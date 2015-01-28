/*
 * @version 0.5
 *
 * Created By Luis Eduardo Cardozo (https://github.com/luiscdz/MasterField.git)
 *
 */
hola = 0;
elm = 0;
 ;(function( $, window, document, undefined ) {


    // se crean opciones por defecto.
    var pluginName = "masterField";

        defaults = {
            		 	repeatElements: "", //No Implementado en esta versión
            		 	IdBoxNumberAdding : "",
                                classContenedor:"",
            		 	buttomAdd: true,
            		 	buttomDelete: true,
                                nameIgualId: false,                                
                                onDelete:function(){
                                    
                                },
                                onAfterDelete:function(){
                                    
                                }
       			   };
  
    // Metodo construcctor del Plugin
    var Plugin = function (element, options) {
        
			        this.element  = element; // Se matiene el HTML plano.
			        this.jElement = $(element); // Se convierte en un selector de jQuery y se le aniade un nuevo atributo.
			        this.jHijos   = this.jElement.children();
			        this.jHijos.each(function(index,obj){

                                        $(obj).attr('name','master-field-'+$(obj).attr('id'));
                                });

			      	this.jHijos.addClass('simple-master-field'); 
			        this.jClon    = this.jElement.clone();
 
			        this.options = $.extend( {}, defaults, options ); // Clase para el manejo de las opciones.
					
			        this._defaults = defaults; // Opciones por defecto
			        this._name = pluginName; // Nombre del plugin
			        this.init();// Metodo Main

                               




			    };
                            
                            
              
                  
    
    /*-----------------------------------*/
    
    
    /*Metodos publicos*/
    Plugin.prototype = {

        init: function() {

         			_private.formatear(this.jElement,this.jHijos, this.options); //Establecemos el formato
         			_private.eventAddElement(this.jElement,this.jClon,this.options);
         			_private.eventDeleteElement(this.options, this.jElement);
            
        },

        addElement: function(event) {
                var data;
                
                if(event === undefined){
                   data ={jClon:this.jClon,jElement:this.jElement,options:this.options}; 
                }else{
                   data =  event.data;
                }
        	
                var args = {data:data};
        		 
                 _private.crearObj(args,1);
        },
        
        getValues: function(){
            
              var array = new Array;
              var json = {};
              
              
              //Se toma el elemento padre y se busca en sus hijos
              this.jElement.children().each(function(index,obj){
                        var    $obj = $(obj);
                        
                        
                        //Dentro de cada hijo se busca los elementos que contienen un datatype mfd-name
                        $obj.children().each(function(index,obj2){
                             var    $obj2 = $(obj2);
                             var elem = $obj2.find('input[data-mfd-name], select[data-mfd-name], textarea[data-mfd-name]');
                             var valor = "";
                              if(elem.attr("data-mfd-name") != undefined){
                                  //console.log(elem.val())
                                    if( elem.val() != ""){
                                      valor = elem.val(); 
                                  }
                                   JsonDinamico(json, elem.attr("data-mfd-name"), valor );
                              }

                          });
                          
                          array.push(json);
                          json = {};

              });
              
              
              
             /*  this.jElement.find('[data-mfd-name]').each(function(index,obj2){
                            
                             var $obj2 = $(obj2);
                             var elem = $obj2
                             var valor = "";
                              if(elem.data("mfd-name") != undefined){
                                  //console.log(elem.val())
                                    if( elem.val() != ""){
                                      valor = elem.val(); 
                                  }
                                   JsonDinamico(json, elem.data("mfd-name"), valor );
                              }
                          
                          
                          array.push(json);
                          json = {};

                       });*/
              return array;
              
              
         },
         destroy:function(){
             
             this.jElement.html(this.jClon.html());
             
         },
         reset: function(){
             
             $(".mf-virtual").remove();
         },
         setValues:function(array){
                
                 var cont = 0; 
                 var tam = array.length;                 
                 var obj = {};
                 var cmp;
                 
                 for(var i=0; i<tam ; i++){ //Toma el array                       
                        if(i > 0){ /*Si es mayor al primer elemento se ira creando un nuevo elemento por cada item mas dentro del array*/
                            this.addElement();
                        }
                        obj = array[i];
                       
                       for(key in obj){
                         
                            cmp =    this.jElement.find('[data-mfd-name='+key+']');
                            if(cmp.length > 0){//Si hay un parametro que no se encuentra en el DOM lo ignora
                                cmp[cont].value = obj[key];
                            }
                          
                        }
                     cont++;
             }
             
         },
         cancelDelete:function(){
             this.jElement.last().append(_private.cache.objCancel);
         }



                   };

   /*Metodos privados*/       	
	var _private = {   
	 				num: 1,	
                                        cache: {
                                            objCancel: null,
                                            position: 0
                                        },
                                        debug : function(obj) {
								if (window.console && window.console.log){
									//window.console.log('Contador de selecciones: ' + $obj.size());
									window.console.log(obj);									
							    }
							},

					eventAddElement: function(jElement,jClon,options){

        					 	
					           var botonAdd    = '.add-master-field';
					           _private.debug("/---EvenrAdd clone ----");  
                                                   _private.debug(Plugin);
					           	hola = Plugin;
					           var data = {jClon:jClon,jElement: jElement,options:options};
   
						   	   jElement.on('click',botonAdd,data,Plugin.prototype.addElement) ;

				       	},

				       	eventDeleteElement: function(options, jElement) {
                                           var parm = { 
                                                        botonCancel : '.cancel-master-field',
                                                        options : options,
                                                        jElement : jElement
                                                      };
                                                      
                                                     jElement.on( "click", ".cancel-master-field", parm,_private.eliminaObj );                                           	               	
						     

       					},

					formatear : function(jElement,jHijos,options) {
							 	  							
                                                        jHijos.addClass('first-master-field');	   		 		   	
                                                        var htmlInicial = jElement.html();
                                                        var btnAdd =  '<span class="add-master-field"></span></div>';

                                                        if ( typeof options.buttomAdd == "boolean"){

                                                                if (options.buttomAdd == false){
                                                                        btnAdd = "";
                                                                }

                                                        }else{
                                                                 _private.debug('buttomAdd: Esta propiedad debe ser un boolean');
                                                        }

                                                        var formato =   '<div class="container-master-field first-container-master-field '+options.classContenedor+'">' + htmlInicial + btnAdd;
                                                        jElement.html(formato);
					},

                                        crearObj : function(event,uso) {


                                                var plugin = event.data;                                                
                                                var jPadreElement;
                                                var jAddElement ;                                                
                                                var jHijos;

                                                //Hace referencia al contenedor actual
                                                jPadreElement =  plugin.jElement.children().eq(0); 

                                                
                                                jAddElement =  plugin.jClon.clone();
                                                jHijos = jAddElement.children();
                                                hola = jHijos;
                                                elm = jAddElement;
                                                //jHijo.find('input[id], select[id], textarea[id]').each(function(){
                                                jHijos.find('[data-mfd-name]').each(function(){
                                                    var id = $(this).attr('id');
                                                    $(this).attr('id',id+_private.num);

                                                    if(typeof  plugin.options.nameIgualId === "boolean"){

                                                        if(plugin.options.nameIgualId  === true){
                                                            $(this).attr('name',id+_private.num);
                                                        }
                                                    }
                                                    _private.num ++;

                                                });


                                                var btnAdd    = '<span class="add-master-field"></span>';
                                                var btnDelete = '<span class="cancel-master-field"></span>';


                                                if ( typeof  plugin.options.buttomAdd === "boolean" ){

                                                                if(plugin.options.buttomAdd === false){
                                                                        btnAdd = "";
                                                                }

                                                }else{
                                                         _private.debug('buttomAdd: Esta propiedad debe ser un boolean');
                                                }


                                                if ( typeof  plugin.options.buttomDelete === "boolean"){

                                                                if(plugin.options.buttomDelete === false){
                                                                        btnDelete = "";
                                                                }

                                                }else{
                                                         _private.debug('btnDelete: Esta propiedad debe ser un boolean');
                                                }


                                                var htmlInicial = jAddElement.html();
                                                var formato =   '<div  class="container-master-field mf-virtual ' +plugin.options.classContenedor+' ">' + htmlInicial+btnDelete+ '</div>';
                                                i = jPadreElement.parent().append(formato);

                                       },
                                        eliminaObj : function(event) {

                                                    var options = event.data.options;
                                                    var campoCancel = $(this);
                                                    var padre = campoCancel.parent();
                                                    var jElement = options.jElement;
                                                    var AfterElementDelete;
                                                    
                                                    AfterElementDelete = _private.callbacks.AfterElementDelete.call(this,options);
                                                   
                                                   if(AfterElementDelete !== false){
                                                        _private.callbacks.elementDelete.call(this,options);
                                                        _private.cache.objCancel = padre;
                                                         padre.remove();
                                                    }

                                        },
                                        callbacks:{
                                               elementDelete:  function (options) {
                                                     $this = $(this).parent();
                                                     options.onDelete.call( $this, event );
                                                     

                                               },
                                               AfterElementDelete :  function (options) {
                                                     $this = $(this).parent();
                                                     return  options.onAfterDelete.call( $this);  
                                                }

                                            
                                        }

        };

    
    $.fn[pluginName] = function ( options ) {

        return this.each(function () {

            if (!$.data(this, "plugin_" + pluginName)) {
                $.data(this, "plugin_" + pluginName, new Plugin( this, options ));
            }

        });

    };

})( jQuery, window, document );


