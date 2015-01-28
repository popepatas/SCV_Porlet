<%-- 
    Document   : Proceso.jsp
    Created on : 19-Dec-2013, 14:22:57
    Author     : illustrato
--%>

<%@page import="modelo.ApiManager"%>
<%@page import="modelo.UsuariosManagers.Usuarios"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Verificamos si el usuario tiene permisos para ingresar a la pagina -->
<%
        String codigoProceso =  ApiManager.quitaNull(request.getParameter("proceso"));
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
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Proceso de Vertimientos</title>
        <link rel="stylesheet" href="../css/normalize.css" />
        <link rel="stylesheet" href="../css/styles.css" />
        <link rel="stylesheet" href="../js/librerias/select2/select2.css" />
        <link rel="stylesheet" href="../js/librerias/Kendo/css/kendo.common.min.css" />
        <link rel="stylesheet" href="../js/librerias/Kendo/css/kendo.default.min.css" />
        <link rel="stylesheet" href="../js/librerias/fullcalendar/fullcalendar.css" />
        <link rel="stylesheet" href="../js/librerias/fullcalendar/fullcalendar.print.css" />
                        
        <script type="text/javascript" src="../js/librerias/jquery.min.js"></script>
        <script type="text/javascript" src="../js/librerias/jquery.migrate.min.js"></script>
        <script type="text/javascript" src="../js/librerias/jquery.formalize.min.js"></script>
        <script type="text/javascript" src="../js/librerias/jquery.json.min.js"></script>
        <script type="text/javascript" src="../js/librerias/handlebars.min.js"></script>
        <script type="text/javascript" src="../js/funciones/funciones.js"></script>
        <script type="text/javascript" src="../js/dom/manipulacion.js"></script>
        <script type="text/javascript" src="../js/librerias/select2/select2.min.js"></script>
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
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.timepicker.min.js"></script> 
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
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.upload.min.js"></script>
        <script type="text/javascript" src="../js/funciones/proceso/procesoCV.js"></script>
        <script type="text/javascript" src="../js/funciones/proceso/informacionGeneral.js"></script>
        <script type="text/javascript" src="../js/funciones/proceso/crearVisita.js"></script>
        <script type="text/javascript" src="../js/funciones/proceso/resultadoVisita.js"></script>
        <script type="text/javascript" src="../js/funciones/proceso/visitas.js"></script>
        <script type="text/javascript" src="../js/funciones/proceso/actualizarVisita.js"></script>
        <script type="text/javascript" src="../js/funciones/proceso/programarMonitoreo/consultarProgramacionMonitoreo.js"></script>
        <script type="text/javascript" src="../js/funciones/proceso/plantillas/templateProgramarMonitoreo.js"></script>
        <script type="text/javascript" src="../js/funciones/proceso/programarMonitoreo/programarMonitoreo.js"></script>
        <script type="text/javascript" src="../js/funciones/proceso/programarMonitoreo/consultarProgramacionMonitoreo.js"></script>
        <script type="text/javascript" src="../js/funciones/proceso/infoCaracterizacion/verificarInfoCaracterizacion.js"></script>
        <script type="text/javascript" src="../js/funciones/proceso/infoCaracterizacion/informacionTecnica.js"></script>
        <script type="text/javascript" src="../js/funciones/proceso/infoProcesoSeco/InformacionProcesoSeco.js"></script>
        <script type="text/javascript" src="../js/funciones/proceso/infoCaracterizacion/manejoLodos.js"></script>
        <script type="text/javascript" src="../js/funciones/proceso/tasaRetributiva.js"></script>        
        
        <script type="text/javascript" src="../js/funciones/proceso/clienteInfoGeneral.js"></script>
        <script type="text/javascript" src="../js/funciones/proceso/cerrarProceso.js"></script>
        
        
        <script type="text/javascript" src="../js/librerias/fullcalendar/fullcalendar.min.js"></script>

         
  </head>
  <body>
  
            <section id="Wrapper">
            <%@include file="/templates/header.html" %>
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
            <section id="ContenedorContent">
            <section id="headerForm" class="cabezote">                 
                
                <div class="titulo">
                    Código del Proceso <span class="campo-titulo"><%= codigoProceso %></span>
                    <input type="hidden" id="codigoProceso" name="codigoProceso" value="<%=codigoProceso%>"/> 
                    <input type="hidden" id="codigoCliente" name="codigoCliente" value=""/>
                </div>
                <div class="titulo">
                    Razón Social  <span id="razonSocial" class="campo-titulo"></span>
                </div>
                <div class="titulo">
                    NIT  <span id="Infonit" class="campo-titulo"></span>
                </div>
                <div class="titulo">
                    Nro Contrato  <span id="contrato" class="campo-titulo"></span>
                </div>
                <div class="botones">
                    <input type="button" id="finalizarProceso" value="Finalizar Proceso" />           
                </div>
            </section> 
            <section id="bodyForm" class="body-form">
                    <div id="Contenedor" class="k-content">
                            <div id="TabsContainer">
                               <ul class="ul-MenuTabs">
                                   <li id="Tab1" class="k-Tab  k-state-active tabla-header"><!-- Tab1 -->
                                       Información General
                                   </li>
                                   <li id="Tab2" class="k-Tab" ><!-- Tab2 -->
                                       Visitas
                                   </li>
                                   <li id="Tab3" class="k-Tab" style="display:none;"><!-- Tab3 -->
                                      Programar Monitoreo
                                   </li>
                                   <li id="Tab4" class="k-Tab" style="display:none;"><!-- Tab4 -->
                                      Informe de Caracterización
                                   </li>
                                   <li id="Tab5" class="k-Tab" style="display:none;"><!-- Tab5 -->
                                      Informe de Proceso Seco
                                   </li>

                               </ul>                    
                                <!-- Contenedor Tab1 -->
                               <div id="TabInformacionGeneral" class="Tab1 TabContenedor" >

                               </div>
                                <!-- Contenedor Tab2 -->
                               <div id="TabVisitas"  class="Tab2 TabContenedor" >

                               </div>
                                <!-- Contenedor Tab3 -->
                               <div id="TabProgramacionMonitoreo" class="Tab3 TabContenedor">

                               </div>
                                 <!-- Contenedor Tab4 -->
                               <div id="TabInfoCaracterizacion" class="Tab4 TabContenedor">
                                   
                                   <div id="TabContainerCaracterizacion">
                                        <ul class="ul-MenuTabs-infoCaracterizacion">
                                             <li id="subTab1" class="k-subTab  k-Tab k-state-active"><!-- subTab1 -->
                                                 Verificar
                                             </li>
                                             <li id="subTab2" class="k-subTab k-Tab" ><!-- subTab2 -->
                                                 Información Técnica
                                             </li>
                                             <li id="subTab3" class="k-subTab k-Tab" sytle="display:none;"><!-- subTab3 -->
                                                Tasa Retributiva
                                             </li>
                                         </ul>   
                                                <!-- Contenedor subTab1 -->
                                        <div id="TabVerificacion" class="subTab1  SubTabContenedor" >

                                        </div>
                                         <!-- Contenedor subTab2 -->
                                        <div id="TabTecnico"  class="subTab2  SubTabContenedor" >

                                        </div>
                                         <!-- Contenedor subTab3 -->
                                        <div id="TabTasaRetroactiva" class="subTab3 SubTabContenedor">

                                        </div>
                                   </div>
                               </div>
                                 <!-- Contenedor Tab4 -->
                               <div id="TabInfoCaracterizacion" class="Tab5 TabContenedor">
                                     
                               </div>  
                                 
                           </div>
                   </div>
              </section></section>
              <% } %>
              </section>
              <div id="modalBox">
              </div>
              <div id="modalBox2">
              </div>
  </body>
</html>

