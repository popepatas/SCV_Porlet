<%-- 
    Document   : ConsultarReporteDagma
    Created on : 25-Feb-2014, 01:08:44
    Author     : illustrato
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="modelo.UsuariosManagers.Usuarios"%>
<!-- Verificamos si el usuario tiene permisos para ingresar a la pagina -->
<%
    HttpSession sesion = request.getSession();
    boolean validar =  false ;          
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
        <link href="../css/styles.css" rel="stylesheet" type="text/css"/>
        <link href="../css/normalize.css" rel="stylesheet" type="text/css"/>
        <link href='http://fonts.googleapis.com/css?family=PT+Sans' rel='stylesheet' type='text/css' />
        <link href="../css/styles.css" rel="stylesheet"/>
        <link href="../js/librerias/Kendo/css/kendo.common.min.css" rel="stylesheet">
        <link href="../js/librerias/Kendo/css/kendo.default.min.css" rel="stylesheet">
        
	<script type="text/javascript" src="../js/librerias/jquery.min.js"></script>
	<script type="text/javascript" src="../js/librerias/jquery.migrate.min.js"></script>
	<script type="text/javascript" src="../js/librerias/jquery.formalize.min.js"></script>
	<script type="text/javascript" src="../js/funciones/funciones.js"></script>
	<script type="text/javascript" src="../js/dom/manipulacion.js"></script>
        
	<script type="text/javascript" src="../js/librerias/Kendo/js/kendo.core.min.js"></script>
	<script type="text/javascript" src="../js/librerias/Kendo/js/kendo.validator.min.js"></script>
	<script type="text/javascript" src="../js/librerias/Kendo/js/kendo.userevents.min.js"></script>
	<script type="text/javascript" src="../js/librerias/Kendo/js/kendo.numerictextbox.min.js"></script>
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
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.popup.min.js"></script> 
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.list.min.js"></script> 
         
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.draganddrop.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.window.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.calendar.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.datepicker.min.js"></script>
        

        
        <title>Sistema de Control de Vertimentos - Cliente</title>
    </head>
    <body>
        
        <section id="Wrapper">
            <%@include file="/templates/header.html" %>
            <section id="ContenedorContent">
                <section id="Content">
                    <%@include file="/templates/main_nav.html" %>
                    <%@include file="/templates/left_nav_reportes.html" %>
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
                        <div id="Wrapper">
                          
                                <div id="div_Reporte1" class="formularios">


                                </div>  

                            
                        </div>
                       
                    </div>
                    <% } %>
                </section>
            </section>
            <%@include file="../templates/footer.html" %>
            <div id="modalBox"></div>
        </section>
    </body>
</html>
