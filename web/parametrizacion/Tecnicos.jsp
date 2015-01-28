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
        <link href="../js/librerias/Kendo/css/kendo.common.min.css" rel="stylesheet" />
        <link href="../js/librerias/Kendo/css/kendo.default.min.css" rel="stylesheet" />
        
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
        
        
        
        <script type="text/javascript" src="../js/funciones/parametrizacion/tecnicos.js"></script>
        <script src="../js/funciones/masterPage.js" type="text/javascript"></script>
        
        <title>SCV - T&eacute;cnicos</title>
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
                        
                            <div id="div_tecnicos" class="formularios">
                                <h2>Técnicos</h2>
                                <div class="preguntaNivelUno">
                                    <div id="contenedorCodigo">
                                        <div class="etiquetas">
                                            <label for="tipoPersona">
                                                Tipo Persona<span class="campoObligatorio">*</span>:
                                            </label>
                                        </div>
                                        <div class="campos">
                                            <select id="tipoPersona"  name="tipoPersona" class="lstRequerido" required>
                                                <option value=""></option>							  	
                                                <option value="1">FUNCIONARIO</option>
                                                <option value="2">CONTRATISTA PESONA NATURAL</option>
                                                <option value="3">CONTRATISTA PESONA JURIDICA</option>
                                             </select>
                                             <span class="k-invalid-msg" data-for="tipoPersona"></span>
                                        </div>
                                    </div>
                                    <div id="contenedorNombres">
                                        <div class="etiquetas">
                                            <label for="nombres">
                                                Nombre<span class="campoObligatorio">*</span>:
                                            </label>
                                        </div>
                                        <div class="campos">
                                            <input type="text" id="nombres" name="nombres" value="" class="cmpRequerido" required/>
                                            <span class="k-invalid-msg" data-for="nombres"></span>
                                        </div>
                                    </div>
                                    <div id="contenedorApellidos">
                                        <div class="etiquetas">
                                            <label for="apellidos">
                                                Apellidos<span class="campoObligatorio">*</span>:
                                            </label>	
                                        </div>
                                        <div class="campos">
                                            <input type="text"  id="apellidos"  name="apellidos" value="" class="cmpRequerido" required />
                                            <span class="k-invalid-msg" data-for="apellidos"></span>
                                        </div>
                                    </div>
                                    <div id="contenedorTipoDocumento">
                                        <div class="etiquetas">
                                            <label for="tipoDocumento">
                                                Tipo de documento<span class="campoObligatorio">*</span>:
                                            </label>	
                                        </div>
                                        <div class="campos">
                                            <select id="tipoDocumento" name="tipoDocumento" class="lstRequerido" required >
                                                <option value=""></option>
                                                <option value="1">CC</option>	
                                                <option value="2">NIT</option>								  	
                                             </select>
                                             <span class="k-invalid-msg" data-for="tipoDocumento"></span>
                                        </div>
                                    </div>
                                    <div id="contenedorDocumento">
                                        <div class="etiquetas">
                                            <label for="documento">
                                                Documento de identidad<span class="campoObligatorio">*</span>:
                                            </label>
                                        </div>
                                        <div class="campos">
                                            <input type="text" id="documento" name="documento" value="" class="cmpRequerido cmpNit" required />
                                            <span class="k-invalid-msg" data-for="documento"></span>
                                        </div>
                                    </div>
                                    <div id="contenedorEstado">
                                        <div class="etiquetas">
                                            <label for="documento">
                                                Estado<span class="campoObligatorio">*</span>:
                                            </label>
                                        </div>
                                        <div class="campos">
                                            <select id="estadoTec" name="estadoTec" class="lstRequerido"  required>
                                                <option value=""></option>							  	
                                                <option value="1">ACTIVO</option>
                                                <option value="2">INACTIVO</option>								  	
                                            </select>
                                            <span class="k-invalid-msg" data-for="estado"></span>
                                        </div>
                                    </div>
                                </div>

                                <input type="hidden" id="codigo" value=""/>
                                <div class="botones">
                                    <input type="button" id="guardar" value="Guardar" />
                                    <input type="button" id="modificar" value="Modificar" />
                                    <input type="button" id="consultar" value="Consultar" />
                                    <input type="button" id="limpiar" value="limpiar" />
                                    
                                </div>
                            </div>
                        <div id="grillaTecnicos">
                            
                        </div>
			
                    </div>
                    <% } %> 
                </section>
            </section>
        </section>
    </body>
</html>

