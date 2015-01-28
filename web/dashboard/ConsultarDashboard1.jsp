<%-- 
    Document   : ConsultarDashboard1
    Created on : 10/02/2014, 11:47:53 AM
    Author     : jmrincon
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="modelo.UsuariosManagers.Usuarios"%>
<!-- Verificamos si el usuario tiene permisos para ingresar a la pagina -->
<%
    HttpSession sesion = request.getSession();
    String username = (String)sesion.getAttribute("usr");
    String pagina = this.getClass().getSimpleName().replaceAll("_", ".");
    Usuarios manager = new Usuarios();
    boolean validar = manager.verificarPermisos(username, pagina);
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="../css/styles.css" rel="stylesheet" type="text/css"/>
        <link href="../css/formalize.css" rel="stylesheet" type="text/css"/>
        <link href='http://fonts.googleapis.com/css?family=PT+Sans' rel='stylesheet' type='text/css' />
        <link href="../css/styles.css" rel="stylesheet"/>
        <link href="../js/librerias/Kendo/css/kendo.common.min.css" rel="stylesheet">
        <link href="../js/librerias/Kendo/css/kendo.default.min.css" rel="stylesheet">
        
        <!-- CDN-based stylesheet reference for Kendo UI DataViz -->
        <link href="http://cdn.kendostatic.com/2012.2.710/styles/kendo.dataviz.min.css" rel="stylesheet">
        
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
        
        <!-- CDN-based script reference for Kendo UI DataViz; utilizing a local reference if offline -->
        <script src="http://cdn.kendostatic.com/2012.2.710/js/kendo.dataviz.min.js"></script>
        
	<script type="text/javascript" src="../js/funciones/dashboard/consultarDashboard1.js"></script>
        
        <title>Sistema de Control de Vertimentos - Cliente</title>
    </head>
    <body>
        <div class="overlay" style="display:none">
            <img  class="loader" src="../css/loader.gif" />
        </div>
        <section id="Wrapper">
            <%@include file="/templates/header.html" %>
            <section id="ContenedorContent">
                <section id="Content">
                    <%@include file="/templates/main_nav.html" %>
                    <%@include file="/templates/left_nav_dashboard.html" %>
                    <!-- Si el usuario no tiene permisos -->
                    <% if(validar == false){ %>
                    <div class="contenidoPrincipal" >
                        <p>Lo sentimos, usted no tiene permisos para acceder a esta pagina.</p>
                    </div>
                    
                    <!-- Si el usuario tiene permisos -->
                    <% } else if(validar == true){ %>
                    <div class="contenidoPrincipal" >
                        <div id="Wrapper">
                            <section class="k-content">
                                <div id="div_Dashboard1" class="formularios">
                                    <div class="titulo">Producci&oacute;n Lodo por Cliente y Rango de A&ntilde;os</div><br>
                                    
                                    <div class="contenedorEstado">
                                        <div class="etiquetas"><label for="clientes">Cliente:</label></div>
                                        <div class="campos">
                                            <select id="clientes" name="clientes" class="k-textbox">
                                                <option value=""></option>
                                            </select>
                                            <span class="k-invalid-msg" data-for="clientes"></span>
                                        </div>
                                    </div>
                                    <div class="contenedoUsoServicio">
                                        <div class="etiquetas"><label for="rangoInicial">Inicial:</label></div>
                                        <div class="campos">
                                            <input type="text" id="rangoInicial" name="rangoInicial" required/>
                                            <span class="k-invalid-msg" data-for="rangoInicial"></span>
                                        </div>
                                    </div>
                                    <div class="contenedoUsoServicio">
                                        <div class="etiquetas"><label for="rangoFinal">Final:</label></div>
                                        <div class="campos">
                                            <input type="text" id="rangoFinal" name="rangoFinal" required/>
                                            <span class="k-invalid-msg" data-for="rangoFinal"></span>
                                        </div>
                                    </div>
                                    <br>
                                    <div class="botones">
                                        <input type="button" id="consultar" value="Consultar" />
                                        <input type="button" id="limpiar" value="Limpiar"  />
                                    </div>
                                </div>
                            </section>
                            
                        </div>
                        <div>
                            <section class="contenedor-chart">
                                <div id="chartDashboard1" style="background: center no-repeat url('../images/mundo.jpg');"></div>
                            </section>
                        </div>
                    </div>
                </section>
            </section>
            <%@include file="../templates/footer.html" %>
            <div id="modalBox"></div>
        </section>
        <% } %>
    </body>
</html>