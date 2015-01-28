<%-- 
    Document   : ConsultarReporte4
    Created on : 10/02/2014, 11:47:53 AM
    Author     : jmrincon
--%>
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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
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
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.dropdownlist.min.js"></script> 
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.draganddrop.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.window.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.calendar.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.datepicker.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.upload.min.js"></script>
	<script type="text/javascript" src="../js/funciones/reportes/consultarReporte4.js"></script>
        <script type="text/javascript" src="../js/funciones/reportes/historialDagma.js"></script>
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
                            <section class="k-content">
                                <div id="div_Reporte4" class="formularios">
                                    <div class="titulo">Criterios de Consulta: Reporte de Procesos</div><br>
                                    
                                    <div class="contenedorFechaInicio">
                                        <div class="etiquetas"><label for="fechaInicial">Fecha Inicio:</label></div>
                                        <div class="campos">
                                            <input type="text" data-role='datepicker' id="fechaInicial" name="fechaInicial" data-type="date" class="cmpFecha"/>
                                            <span class="k-invalid-msg" data-for="fechaInicial"></span>
                                        </div>
                                    </div>
                                    <div class="contenedorFechaFin">
                                        <div class="etiquetas"><label for="fechaFinal">Fecha Final:</label></div>
                                        <div class="campos">
                                            <input type="text" data-role='datepicker' id="fechaFinal" name="fechaFinal" data-type="date" class="cmpFecha" />
                                            <span class="k-invalid-msg" data-for="fechaFinal"></span>
                                        </div>
                                    </div>
                                    <div class="contenedorEstado">
                                        <div class="etiquetas"><label for="estadoProceso">Estado Proceso:</label></div>
                                        <div class="campos">
                                            <select id="estadoProceso" name="estadoProceso" class="k-textbox">
                                                <option value=""></option>
                                            </select>
                                            <span class="k-invalid-msg" data-for="estadoProceso"></span>
                                        </div>
                                    </div>
                                    <div class="contenedorContrato">
                                        <div class="etiquetas"><label for="numeroContrato">Contrato:</label></div>
                                        <div class="campos">
                                            <input type="text" id="numeroContrato" name="numeroContrato" value="" class="k-textbox cmpRequerido" />
                                            <span class="k-invalid-msg" data-for="numeroContrato"></span>
                                        </div>
                                    </div>
                                    <div class="contenedorCodigo">
                                        <div class="etiquetas"><label for="nitConsultar">NIT:</label></div>
                                        <div class="campos">
                                            <input type="text" id="nitConsultar" name="nitConsultar" value="" class="k-textbox cmpNit" />
                                            <span class="k-invalid-msg" data-for="nitConsultar"></span>
                                        </div>
                                    </div>
                                    <div class="contenedorDescripcion">
                                        <div class="etiquetas"><label for="razonSocial">Razón Social:</label></div>
                                        <div class="campos">
                                            <input class="k-textbox" type="text" id="razonSocial" name="razonSocial" value="" />
                                            <span class="k-invalid-msg" data-for="razonSocial"></span>
                                        </div>
                                    </div>
                                    <div class="contenedoUsoServicio">
                                        <div class="etiquetas"><label for="comuna">Comuna:</label></div>
                                        <div class="campos">
                                            <input type="text" id="comuna" name="comuna" />
                                            <span class="k-invalid-msg" data-for="comuna"></span>
                                        </div>
                                    </div>
                                    <div class="contenedorEstado">
                                        <div class="etiquetas"><label for="laboratorio">Laboratorio:</label></div>
                                        <div class="campos">
                                            <select id="laboratorio" name="laboratorio" class="k-textbox">
                                                <option value=""></option>
                                            </select>
                                            <span class="k-invalid-msg" data-for="laboratorio"></span>
                                        </div>
                                    </div>
                                    <div class="contenedorCiuu">
                                        <div class="etiquetas"><label for="actividadProductiva">Actividad Productiva:</label></div>
                                        <div class="campos">
                                            <select id="actividadProductiva" name="actividadProductiva" class="k-textbox">
                                                <option value=""></option>
                                            </select>
                                            <span class="k-invalid-msg" data-for="actividadProductiva"></span>
                                        </div>
                                    </div>
                                    <div class="contenedoUsoServicio">
                                        <div class="etiquetas"><label for="usoServicio">Uso Servicio:</label></div>
                                        <div class="campos">
                                            <select id="usoServicio" name="usoServicio" class="k-textbox">
                                                <option value=""></option>
                                                <option value="1">COMERCIAL</option>
                                                <option value="2">INDUSTRIAL</option>
                                                <option value="3">OFICIAL</option>
                                                <option value="4">ESPECIAL</option>
                                            </select>
                                            <span class="k-invalid-msg" data-for="usoServicio"></span>
                                        </div>
                                    </div>
                                    <div class="contenedorTipoInforme">
                                        <div class="etiquetas"><label for="tipoInforme">Tipo Informe:</label></div>
                                        <div class="campos">
                                            <select id="tipoInforme" name="tipoInforme" class="k-textbox">
                                                <option value=""></option>
                                            </select>
                                            <span class="k-invalid-msg" data-for="tipoInforme"></span>
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
                            <section class="contenedor-grilla">
                                <div id="grillaReporte4" style="width:1500px;"></div>
                            </section>
                        </div>
                        <div>
                            <form id="exportToExcelForm" action="/SCV_Portlet/exportarExcel" method="POST" target="exportToExcelHiddenFrame">
                                <input type="hidden" id="excelExportGridData" name="griddata" />
                                <input type="hidden" id="nombreReporte" name="nombreReporte" value="Reporte4" />
                            </form>
                            <iframe name="exportToExcelHiddenFrame" style="display: none;"></iframe>
                        </div>
                    </div>
                    <% } %>
                </section>
            </section>
            <%@include file="../templates/footer.html" %>
            
            <div id="modalBoxCliente"></div>
            
            <div id="modalBoxVisitas"></div>
            
            <div id="modalBoxHistorialDagma"></div>
            
            <div id="modalBoxAdjuntosProceso"></div>
            
            <div id="modalBoxHistorial"></div>
            
            
        </section>
    </body>
</html>