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
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

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
        <script type="text/javascript" src="../js/funciones/parametrizacion/tiempoBackup.js"></script>
        <script src="../js/funciones/masterPage.js" type="text/javascript"></script>
                        
        <title>SCV - Tiempo de Backup</title>
    </head>
    <body>
        <section id="Wrapper">
            <%@include file="../templates/header.html" %>
                  
        
            <section id="ContenedorContent">
                <section id="Content">
               
                    <%@include file="../templates/main_nav.html" %>
                    <%@include file="../templates/left_nav_parametrizacion.html" %>                  
                    
               
                    <!-- Si el usuario no tiene permisos -->
                    <% if(validar == false){ %>
                    <div class="contenidoPrincipal" >
                        <p>Lo sentimos, usted no tiene permisos para acceder a esta pagina.</p>
                    </div>

                    <!-- Si el usuario tiene permisos -->
                    <% } else if(validar == true){ %>
                    <div class="overlay" style="display:none"> 
                        <img  class="loader" src="../css/loader.gif" />
                    </div>
                    <div class="contenidoPrincipal" >
                        <div id="div_tiempo_backup" class="formularios">
                            <h2>Tiempo de Backup</h2>

                            <div class="contenedorTiempo">
                                <div class="etiquetas">
                                    <label for="dbo">
                                        Tiempo (días)<span class="campoObligatorio">*</span>:
                                    </label>
                                </div>
                                <div class="campos">
                                    <input class="cmpRequerido k-textbox"  type="text" id="time" name="time" value="" required/>
                                    <span class="k-invalid-msg" data-for="time"></span>
                                </div>
                               
                            </div>


                            <input type="hidden" id="codigo" value=""/>
                            <div class="botones">
                                <input type="button" id="modificar" value="modificar" />                                
                            </div>
                        </div>
                    </div>
                    <% } %> 
                </section>
            </section>
        </section>
    </body>
</html>

