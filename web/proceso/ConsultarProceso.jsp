<%-- 
    Document   : ConsultarProceso
    Created on : 18/12/2013, 11:49:15 AM
    Author     : juanhgm
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="modelo.UsuariosManagers.Usuarios"%>
<!-- Verificamos si el usuario tiene permisos para ingresar a la pagina -->
<%
    HttpSession sesion =  request.getSession();      
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
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <link rel="stylesheet" type="text/css" href="../css/formalize.css">
        <link href='http://fonts.googleapis.com/css?family=PT+Sans' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" href="../css/normalize.css" />
        <link rel="stylesheet" href="../css/styles.css" />
        <link href="../js/librerias/masterField/css/jquery.masterField.css" rel="stylesheet" />
        <link href="../js/librerias/Kendo/css/kendo.common.min.css" rel="stylesheet" />
        <link href="../js/librerias/Kendo/css/kendo.default.min.css" rel="stylesheet" />        
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
         <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.binder.min.js"></script>                        
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.editable.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.window.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.filtermenu.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.columnmenu.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.groupable.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.pager.min.js"></script>
       <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.selectable.min.js"></script>
       <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.sortable.min.js"></script>       <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.reorderable.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.resizable.min.js"></script>
      <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.grid.min.js"></script> 
       

	<script type="text/javascript" src="../js/funciones/proceso/consultarProceso.js"></script>

        <title>Sistema de Control de Vertimentos - Proceso</title>
    </head>
    <body>
        
        <section id="Wrapper">
            
            <%@include file="/templates/header.html" %>
        
            <section id="ContenedorContent">
                <section id="Content">
                    <%@include file="/templates/main_nav.html" %>
                    <%@include file="/templates/left_nav_proceso.html" %>
                    
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
                            <div id="div_crear_proceso" class="formularios">
                                <h2>Consultar Proceso</h2>
                               
                                <div id="contenedorNroProceso">
                                    <div class="etiquetas">
                                        <label for="nroProceso">
                                                Nro Proceso:
                                        </label>
                                    </div>
                                    <div class="campos">
                                        <input type="text" id="nroProceso" name="nroProceso" value=""  class="cmpRequerido k-textbox"  />
                                        <span class="k-invalid-msg" data-for="nroProceso"></span>
                                    </div>
                                </div>	
 <div id="contenedorContrato">
                                    <div class="etiquetas">
                                        <label for="contrato">
                                                Contrato:
                                        </label>
                                    </div>
                                    <div class="campos">
                                        <input type="text" id="contratoBqa" name="contratoBqa" value=""  class="cmpRequerido k-textbox"  />
                                        <span class="k-invalid-msg" data-for="contrato"></span>
                                    </div>
                                </div>	
                                <div id="contenedorNit">
                                    <div class="etiquetas">
                                        <label for="nit">
                                            Nit:
                                        </label>
                                    </div>
                                    <div class="campos">
                                        <input type="text" id="nitBqa" name="nitBqa" value=""  class="cmpRequerido cmpNit k-textbox"  />
                                        <span class="k-invalid-msg" data-for="nitBqa"></span>
                                    </div>
                                </div>	
                                <div id="contenedorCiuu">
                                    <div class="etiquetas">
                                        <label for="ciiu">
                                            Ciiu:
                                        </label>
                                    </div>
                                    <div class="campos">
                                        <select id="ciiu" name="ciiu" value=""  class="">
                                        </select>
                                        <span class="k-invalid-msg" data-for="ciiu"></span>
                                    </div>
                                </div>	
                                  <div id="contenedorAnio">
                                    <div class="etiquetas">
                                        <label for="anio">
                                            Año:
                                        </label>
                                    </div>
                                    <div class="campos">
                                        <input type="text" id="anioBqa" name="anioBqa" value=""  class="k-textbox"  />
                                        <span class="k-invalid-msg" data-for="anioBqa"></span>
                                    </div>
                                </div>	
                                <h3> Seleccioné un rango de fechas:</h3>
                                <div class="contenedorFechaInicio">							
                                    <div class="etiquetas">
                                        <label for="fechaInicio">
                                            Fecha Inicio:
                                        </label>
                                    </div>
                                    <div class="campos">
                                        <input type="text" data-role='datepicker' id="fechaInicio" name="fechaInicio" data-type="date"  class="cmpRequerido"   />
                                        <span class="k-invalid-msg" data-for="fechaInicio"></span>
                                    </div>
                                </div>	
                                
                                <div class="contenedorFechaFin">							
                                    <div class="etiquetas">
                                        <label for="fechaFin">
                                            Fecha Final:
                                        </label>
                                    </div>
                                    <div class="campos">
                                        <input type="text" data-role='datepicker' id="fechaFin" name="fechaFin" data-type="date"  class="cmpRequerido"   />
                                        <span class="k-invalid-msg" data-for="fechaFin"></span>
                                    </div>
                                </div>	
                            </div>
                            <div class="botones">                            
                                <input type="button" id="consultar" value="Consultar" />                                
                                <input type="button" id="limpiar" value="Limpiar" />
                            </div>
                    </div>
                    <% } %>
                </section>
                    <section class="contenedor-grilla">
                        <div id="grillaProceso" class=""></div>
                    </section>
            </section>
        </section>
    </body>
</html>
