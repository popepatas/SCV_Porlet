<%-- 
    Document   : AdmonCalendarioMonitoreo
    Created on : 28-Feb-2014, 14:31:41
    Author     : illustrato
--%>


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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="../css/formalize.css">
        <link href='http://fonts.googleapis.com/css?family=PT+Sans' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" href="../css/styles.css" />
        <link rel="stylesheet" href="../js/librerias/Kendo/css/kendo.common.min.css" />
        <link rel="stylesheet" href="../js/librerias/Kendo/css/kendo.default.min.css" />
        <link rel="stylesheet" href="../js/librerias/fullcalendar/fullcalendar.css" />
        <link rel="stylesheet" href="../js/librerias/fullcalendar/fullcalendar.print.css" />
                        
        <script type="text/javascript" src="../js/librerias/jquery.min.js"></script>
        <script type="text/javascript" src="../js/librerias/jquery.migrate.min.js"></script>
        <script type="text/javascript" src="../js/librerias/jquery.formalize.min.js"></script>
        <script type="text/javascript" src="../js/funciones/funciones.js"></script>
        <script type="text/javascript" src="../js/dom/manipulacion.js"></script>
        <script type="text/javascript" src="../js/librerias/masterField/js/jquery.masterField.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.core.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.fx.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.validator.min.js"></script>	
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.userevents.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.numerictextbox.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.data.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.calendar.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.popup.min.js"></script>			
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.datepicker.min.js"></script>	
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.tabstrip.min.js"></script>        
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.draganddrop.min.js"></script>              
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
              
        <script type="text/javascript" src="../js/funciones/proceso/crearVisita.js"></script>
        
        <script type="text/javascript" src="../js/funciones/admonVisitas/AdmonCalendarioVisitas.js"></script>
        <script type="text/javascript" src="../js/librerias/fullcalendar/fullcalendar.min.js"></script>

	

        <title>Sistema de Control de Vertimentos - Proceso</title>
    </head>
    <body>
        
        <section id="Wrapper">
             <%@include file="/templates/header.html" %>
        
            <section id="ContenedorContent">
               
                   <%@include file="../templates/main_nav.html" %>
                   <%@include file="../templates/left_nav_adminVisitas.html" %>     
                <% if(validar == false){ %>
                <div id="contenedorCalendario">
                    <p>Lo sentimos, usted no tiene permisos para acceder a esta pagina.</p>
                </div>

                <!-- Si el usuario tiene permisos -->
                <% } else if(validar == true){ %>
                <div class="overlay" style="display:none"> 
                    <img  class="loader" src="../css/loader.gif" />
                </div>
                <div id="contenedorCalendario">
                    <div id="calendar" style="width:800px">                        
                    </div>                        
                </div>
                <% } %>
                
            </section>
        </section>
              <div id="modalBox">
              </div>
              <div id="modalBox2">
              </div>
    </body>
</html>
