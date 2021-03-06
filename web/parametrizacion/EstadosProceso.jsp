<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="modelo.UsuariosManagers.Usuarios"%>
<!-- Verificamos si el usuario tiene permisos para ingresar a la pagina -->
<%
     HttpSession sesion = request.getSession(); 
     boolean validar =   false ;
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
        <script type="text/javascript" src="../js/funciones/funciones.js"></script>
        <script type="text/javascript" src="../js/dom/manipulacion.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.core.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.validator.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.data.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.binder.min.js"></script>                        
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.editable.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.window.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.filtermenu.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.columnmenu.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.groupable.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.pager.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.selectable.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.sortable.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.reorderable.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.resizable.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.grid.min.js"></script> 
        <script type="text/javascript" src="../js/funciones/parametrizacion/estadosProcesos.js"></script>
        <script type="text/javascript" src="../js/funciones/masterPage.js" ></script>
                        
        <title>SCV - Estados del Proceso</title>
    </head>
    <body>
        
        <section id="Wrapper">
           <%@include file="../templates/header.html" %>
        
            <section id="ContenedorContent">
                
                   <%@include file="../templates/main_nav.html" %>
                    
                     <%@include file="../templates/left_nav_parametrizacion.html" %>                  
                    
                        <!-- Si el usuario no tiene permisos -->
                        <% if(validar == false){ %>
                        <div id="div_estados_proceso" class="formularios">
                            <p>Lo sentimos, usted no tiene permisos para acceder a esta pagina.</p>
                        </div>

                        <!-- Si el usuario tiene permisos -->
                        <% } else if(validar == true){ %>
                        <div class="overlay" style="display:none"> 
                            <img  class="loader" src="../css/loader.gif" />
                        </div>
                        <div id="div_estados_proceso" class="formularios">
                            <h2>Estados del Proceso</h2>
					
                           
                                <div class="etiquetas">
                                    <label for="descripcion">
                                        Descripción<span class="campoObligatorio">*</span>:
                                    </label>
                                </div>
                                <div class="campos">
                                    <input class="cmpRequerido k-textbox"  type="text" id="descripcion" name="descripcion" value="" required/>
                                    <span class="k-invalid-msg" data-for="descripcion"></span>
                                </div>
                            </div>
                            
                            <input type="hidden" id="codigo" value=""/>
                                <div class="botones">						
                                    <input type="button" id="modificar" value="Modificar"  />
                                    <input type="button" id="consultar" value="Consultar" />
                                </div>
                            <% } %>
                    <div id="grillaEstadosProceso" class="grilla-small"></div>
              
            </section>
        </section>
    </body>
</html>

