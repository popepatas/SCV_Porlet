<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="modelo.UsuariosManagers.Usuarios"%>
<!-- Verificamos si el usuario tiene permisos para ingresar a la pagina -->
<%
     HttpSession sesion = request.getSession(); 
     boolean validar = false ;
    String username = (String)sesion.getAttribute("login");     
    if(username == null){         
        response.sendRedirect("/SCV_Portlet/Login.jsp");     
    }else{         
        String pagina = this.getClass().getSimpleName().replaceAll("_", ".");         
        Usuarios manager = new Usuarios();             
        validar = manager.verificarPermisos(username, pagina);     
    }
   
   
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        
        <link rel="stylesheet" type="text/css" href="../css/styles.css" />
        <link rel="stylesheet" type="text/css" href="../css/formalize.css">
        <link href='http://fonts.googleapis.com/css?family=PT+Sans' rel='stylesheet' type='text/css'>        
        <link rel="stylesheet" href="../css/normalize.css" />
        <link href="../js/librerias/Kendo/css/kendo.common.min.css" rel="stylesheet">
        <link href="../js/librerias/Kendo/css/kendo.default.min.css" rel="stylesheet">
        
        <script type="text/javascript" src="../js/librerias/jquery.min.js"></script>
        <script type="text/javascript" src="../js/librerias/jquery.migrate.min.js"></script>
        <script type="text/javascript" src="../js/librerias/jquery.formalize.min.js"></script>
        <script type="text/javascript" src="../js/librerias/handlebars.min.js"></script>
        <script type="text/javascript" src="../js/funciones/funciones.js"></script>
        <script type="text/javascript" src="../js/dom/manipulacion.js"></script>
        <script type="text/javascript" src="../js/funciones/permisos/permisos.js"></script>
        

        <title>SCV - Asignar Permisos</title>
    </head>
    <body>
        <script id="template-permisos-pantallas" type="text/x-handlebars-template">
                {{#each permisos}}
                    <ul class="per-contentModulos">
                        
                        <li class="per-modulos">
                                <h3>
                                    <span class="icon-dashboard"></span> {{modulo}}
                                </h3>
                                 <ul class="per-contentPantalla">
                                         {{#each pantallas}}
                                            <li class="per-pantallas">
                                                    {{checkbox existe 'permisos' codigo}}
                                                    <label class="lbl-accordian"> {{jsp}}</label>
                                            </li>
                                            {{/each}}
                                 </ul>
                                
                        </li>
                        
                        </ul>
                {{/each}}
        </script>
        <section id="Wrapper">
           <%@include file="../templates/header.html" %>
                
            <section id="ContenedorContent">
              
                   
                    <%@include file="../templates/main_nav.html" %>
                    <%@include file="../templates/left_nav_permisos.html" %>   
                    
                  
                      
                            <!-- Si el usuario no tiene permisos -->
                            <% if(validar == false){ %>
                            <div id="div_motivos_visita" class="formularios">
                                <p>Lo sentimos, usted no tiene permisos para acceder a esta pagina.</p>
                            </div>

                            <!-- Si el usuario tiene permisos -->
                            <% } else if(validar == true){ %>
                            <div class="overlay" style="display:none"> 
                                <img  class="loader" src="../css/loader.gif" />
                            </div>
                            <div id="div_permisos_acceso" class="formularios">
                                 <div class="contenedorRoles">
                                    <div class="etiquetas">
                                        <label for="rol">
                                            Roles <span class="campoObligatorio">*</span>:
                                        </label>
                                    </div>
                                    <div class="campos">
                                        <select id="rol" class="cmpRequerido"  required>
                                           
                                        </select>
                                            
                                    </div>
                                </div>
                                <div id="accordian">
                                    <!--<ul class="per-contentModulos">
                                        <li class="per-modulos">
                                            <h3>
                                                  <span class="icon-dashboard"></span> Clientes
                                            </h3>
                                            <ul class="per-contentPantalla">
                                                <li class="per-pantallas">    
                                                    <input type="checkbox" name="prueba" value="" class="chk-accordian"/>
                                                    <label class="lbl-accordian">prueba</label>
                                                </li>                                                       
                                            </ul>
                                        </li>
                                    </ul>-->
                                </div>
                            </div>
                            <% } %>
                           
			
                  
                </section>
       
        </section>    
    </body>
</html>

