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
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <link rel="stylesheet" type="text/css" href="../css/styles.css" />
        <link rel="stylesheet" type="text/css" href="../css/formalize.css">
        <link href='http://fonts.googleapis.com/css?family=PT+Sans' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" href="../css/normalize.css" />
        <link href="../js/librerias/select2/select2.css" rel="stylesheet" />
        <link href="../js/librerias/Kendo/css/kendo.common.min.css" rel="stylesheet" />
        <link href="../js/librerias/Kendo/css/kendo.default.min.css" rel="stylesheet" />
        
        <script type="text/javascript" src="../js/librerias/jquery.min.js"></script>
        <script type="text/javascript" src="../js/librerias/jquery.migrate.min.js"></script>
        <script type="text/javascript" src="../js/librerias/jquery.formalize.min.js"></script>			
        <script type="text/javascript" src="../js/librerias/select2/select2.min.js"></script>
        <script type="text/javascript" src="../js/funciones/funciones.js"></script>
        <script type="text/javascript" src="../js/dom/manipulacion.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.core.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.validator.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.data.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.popup.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.list.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.autocomplete.min.js"></script>
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
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.dropdownlist.min.js"></script> 
        <script type="text/javascript" src="../js/funciones/parametrizacion/actividadesParametros.js"></script>
        <script src="../js/funciones/masterPage.js" type="text/javascript"></script>
        
        <title>SCV - Actividades Económicas_Parámetros Fisicoquímicos</title>
    </head>
	<body>
        <div class="overlay" style="display:none"> 
                <img  class="loader" src="../css/loader.gif" />
        </div>
        <section id="Wrapper">
                <%@include file="../templates/header.html" %>

        
            <section id="ContenedorContent">
                
                   
                <%@include file="../templates/main_nav.html" %>
                <%@include file="../templates/left_nav_parametrizacion.html" %>                  
                <% if(validar == false){ %>
                    <div id="div_actividades_parametros" class="formularios">
                        <p>Lo sentimos, usted no tiene permisos para acceder a esta pagina.</p>
                    </div>
                    
                    <!-- Si el usuario tiene permisos -->
                    <% } else if(validar == true){ %>
                    
                        <div id="div_actividades_parametros" class="formularios">
                            <h2>Actividades Económicas - Parámetros Fisicoquímicos</h2>

                            <div id="contenedorActividadEconomica">
                                <div class="etiquetas">
                                    <label for="actividad">
                                        Actividad económica<span class="campoObligatorio">*</span>:
                                    </label>	
                                </div>
                            <div class="campos">
                                    <select id="actividad"  name="actividad" class="lstRequerido"  required>
                                        <option value=""></option>
                                    </select>
                                    <!--<input type="text" id="actividad" name="actividad" value="" class="cmpRequerido" required />-->
                                    </select>
                                    <span class="k-invalid-msg" data-for="actividad"></span>
                                </div>
                            </div>
                            
                            <div id="contenedorParamFisicoquimicos">
                                <div class="etiquetas">
                                    <label for="parametro">
                                        Parámetros fisicoquímicos<span class="campoObligatorio">*</span>:
                                    </label>	
                                </div>
                                <div class="campos">
                                    <select id="parametro"  name="parametro" class="lstRequerido"  required>
                                        <option value=""></option>							  	
                                        	
                                    </select>
                                    <span class="k-invalid-msg" data-for="parametro"></span>
                                </div>
                            </div>
                            
                            <div id="contenedorRangoInicial">
                                <div class="etiquetas">
                                    <label for="rangoInicial">
                                        Rango inicial<span class="campoObligatorio">*</span>:
                                    </label>
                                </div>
                                <div class="campos">
                                    <input type="text" id="rangoInicial" name="rangoInicial" class="cmpRequerido" value="" required/>
                                    <span class="k-invalid-msg" data-for="rangoInicial"></span>
                                </div>
                            </div>
                            
                            <div id="contenedorRangoFinal">
                                <div class="etiquetas">
                                    <label for="rangoFinal">
                                        Rango final<span class="campoObligatorio">*</span>:
                                    </label>	
                                </div>
                                <div class="campos">
                                    <input type="text" id="rangoFinal" name="rangoFinal" class="cmpRequerido" value="" required/>
                                    <span class="k-invalid-msg" data-for="rangoFinal"></span>
                                </div>
                            </div>
                            
                            <div id="contenedorMayorRangoInicial">
                                <div class="etiquetas">
                                    <label for="mayorRangoInicial">
                                        Mayor o igual rango inicial 
                                    </label>	
                                </div>
                                <div class="campos">
                                    <input type="checkbox" id="mayorRangoInicial"  name="mayorRangoInicial"  value="" />
                                    <span class="k-invalid-msg" data-for="mayorRangoInicial"></span>
                                </div>
                            </div>
                            
                            <div id="contenedorMayorRangoFinal">
                                <div class="etiquetas">
                                    <label for="mayorRangoFinal">
                                        Mayor o igual rango final 
                                    </label>	
                                </div>
                                <div class="campos">
                                    <input type="checkbox" id="mayorRangoFinal"  name="mayorRangoFinal"  value="" />
                                    <span class="k-invalid-msg" data-for="mayorRangoFinal"></span>
                                </div>
                            </div>                                
                            <div id="contenedorMostrarRango">
                                <div class="etiquetas">
                                    <label for="mostrarRango">
                                        Visualización del Rango <span class="campoObligatorio">*</span>:
                                    </label>	
                                </div>
                                <div class="campos">
                                    <select id="mostrarRango"  name="mostrarRango" class="lstRequerido"  required>
                                        <option value=""></option>							  	
                                        <option value="1">Mostrar</option>							  	
                                        <option value="2">Muestra el rango mayor </option>							  	
                                        <option value="3">No Mostrar</option>
                                    </select>
                                    <span class="k-invalid-msg" data-for="parametro"></span>
                                </div>
                            </div>
											
                            <input type="hidden" id="codigo" value=""/>
                            <div class="botones">
                                <input type="button" id="guardar" value="Guardar" />
                                <input type="button" id="modificar" value="Modificar" />
                                <input type="button" id="consultar" value="Consultar" />
                                <input type="button" id="limpiar" value="Limpiar" />
                            </div>
                        </div>	
                    
                    <div id="grillaActParamFiscoQuim" class="grilla-Big"></div>
                    <% } %>
                </section>
           
        </section>
    </body>
</html>

