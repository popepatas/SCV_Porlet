
$(document).on("ready", inicio);
var template; 


function inicio(){ 
 
  campo.lista.crear("rol",servlet.consultar.roles ,"");
  $("#rol").on("change",cargarTemplatePermisos);
  $("#div_permisos_acceso").on("change",".chkPantalla",guardarPermisos);
    
  var htmlTemplate   = $("#template-permisos-pantallas").html();
        template       = Handlebars.compile(htmlTemplate);
        
  $("#div_permisos_acceso").on("click","#accordian h3",function(){
		//slide up all the link lists
		$("#accordian ul ul").slideUp();
		//slide down the link list below the h3 clicked - only if its closed
		if(!$(this).next().is(":visible"))
		{
			$(this).next().slideDown();
		}
    });
     
}

var guardarPermisos = function(){
    
    var chk = this.value;
    var chkValue = campo.check($(this).attr("id"));
    var parametros = {
                        rol:campo.obtener("rol"),
                        pantalla:chk, 
                        valor:chkValue
                    };
                    
    var xhr  = $.ajax({
                    type:"POST",
                    dataType:"json",
                    url: servlet.insertar.permisos,
                    cache:false,
                    data:parametros
                  });
                  
    xhr.done(function(response,status,xhr){
        if(response.error == undefined){
            
            if(response.resp == 0){
                alert("Error inseperado a guadar.");
            }
        }else{
             alert("Error inseperado a guadar.");
        }
        
    });
    
};

var cargarTemplatePermisos = function(){
        
     
     var html = "";
     var xhrPermisoPantallas;
     
     //Obtenemos los puntos
     xhrPermisoPantallas = $.ajax({
                           type:"POST",
                           dataType:"json",
                           url: servlet.consultar.permisos,
                           cache:false,
                           data:{rol:campo.obtener("rol")}
                         });
                        
     
     xhrPermisoPantallas.done(function(response,status,xhr){
         
          var cntx = {};
          var htmlBody = "";
          
          if(response.error == undefined){
              
                cntx.permisos = response;               
                htmlBody = template(cntx);
                $("#accordian").html(htmlBody);
                
          }else if(response.error == 0){
              
               alert("Se ha presentado un error inesperado");
          }
           
     });
     
    
};


//Helper para generar Checkbox en base a voler de SI y NO
Handlebars.registerHelper('checkbox', function(chekeado, nombre,valor){
  
 var check   = campo.convertirBool(chekeado) == true ? "checked" : "" ;
    
  var html = '<input type="checkbox" id="chkPer'+valor+'" name="'+nombre+'" '+ check +' value="'+ valor +'" class="chkPantalla"/>';
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
