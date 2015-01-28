<%-- 
    Document   : ClientesConContratosAsignado.jsp
    Created on : 09-Jan-2014, 12:18:48
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
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="../css/styles.css" />
        <link rel="stylesheet" type="text/css" href="../css/formalize.css" />

        <link href='http://fonts.googleapis.com/css?family=PT+Sans' rel='stylesheet' type='text/css' />
        

        <link rel="stylesheet" href="../css/styles.css" />
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

			
                        <script type="text/javascript" src="../js/funciones/clientes/clientesConContrato.js"></script>
        
        <title>Sistema de Control de Vertimentos - Cliente</title>
    </head>
    <body>
        <section id="Wrapper">
            <%@include file="/templates/header.html" %>
        
            <section id="ContenedorContent">
                <section id="Content">
                     
                    <%@include file="/templates/main_nav.html" %>
                    <%@include file="/templates/left_nav_clientes.html" %>
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

                                                <div id="div_clienteConContratos" class="formularios">
                                                        <h2>Consultar Cliente</h2>
                                                        <br>
                                                        <div class="contenedorNit">
                                                                <div class="etiquetas">
                                                                        <label for="nitBqa">
                                                                                NIT: 
                                                                        </label>
                                                                </div>
                                                                <div class="campos">
                                                                        <input type="text" id="nitBqa" name="nitBqa" value="" class="k-textbox  cmpNit"  />
                                                                        <span class="k-invalid-msg" data-for="nitBqa"></span>
                                                                </div>
                                                        </div>
                                                        <div class="contenedorContrato">
                                                                <div class="etiquetas">
                                                                        <label for="contratoBqa">
                                                                               Contrato:
                                                                        </label>
                                                                </div>
                                                                <div class="campos">
                                                                        <input class="k-textbox"  type="text" id="contratoBqa" name="contratoBqa" value="" />
                                                                        <span class="k-invalid-msg" data-for="contratoBqa"></span>
                                                                </div>
                                                        </div>

                                                        <input type="hidden" id="codigo" value=""/>
                                                        <br>
                                                        <div class="botones">               
                                                                
                                                                <input type="button" id="consultar" value="Consultar" />
                                                                <input type="button" id="limpiar" value="Limpiar" />

                                                                <!--<input type="button" id="guardar" value="Limpiar"  />-->
                                                        </div>
                                                </div>

                                </section>
                                <br>
                                <section class="contenedor-grilla">
                                        <div id="grillaClienteConContratos" class="">

                                        </div>
                                </section>
                </div>
                            
                        </p>
                    </div>
                </section>
            </section>
                      <%@include file="../templates/footer.html" %>
        </section>
        <% } %>
    </body>
</html>
