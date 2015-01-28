<%@page import="modelo.UsuariosManagers.Usuarios"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
			
        <link rel="stylesheet" type="text/css" href="../css/styles.css" />
        <link rel="stylesheet" type="text/css" href="../css/formalize.css">
        <link href='http://fonts.googleapis.com/css?family=PT+Sans' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" href="../css/normalize.css" />
        <link href="../js/librerias/masterField/css/jquery.masterField.css" rel="stylesheet" />
        <link href="../js/librerias/Kendo/css/kendo.common.min.css" rel="stylesheet" />
        <link href="../js/librerias/Kendo/css/kendo.default.min.css" rel="stylesheet" />
        
        <script type="text/javascript" src="../js/librerias/jquery.min.js"></script>
        <script type="text/javascript" src="../js/librerias/jquery.migrate.min.js"></script>
        <script type="text/javascript" src="../js/librerias/jquery.formalize.min.js"></script>
        <script type="text/javascript" src="../js/librerias/jquery.json.min.js"></script>
        <script type="text/javascript" src="../js/funciones/funciones.js"></script>
        <script type="text/javascript" src="../js/dom/manipulacion.js"></script>
        <script type="text/javascript" src="../js/librerias/masterField/js/jquery.masterField.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.core.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.validator.min.js"></script>	
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.userevents.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.numerictextbox.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.data.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.popup.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.list.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.autocomplete.min.js"></script>
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
        <script type="text/javascript" src="../js/funciones/parametrizacion/puntosVertimientos.js"></script>
        
        <script src="../js/funciones/masterPage.js" type="text/javascript"></script>
        
        <title>SCV - Puntos de Vertimientos por Contrato</title>
        
    </head>
    <body>
        <div class="overlay" style="display:none"> 
                <img  class="loader" src="../css/loader.gif" />
        </div>
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
                    <div class="contenidoPrincipal" >
                        <form>
                            <div id="div_puntos_vertimientos" class="formularios">
                                <h2>Puntos de Vertimientos por Contrato</h2>
                                    <div id="contenedorContrato">
                                        <div class="etiquetas">
                                            <label for="contrato">
                                                Contrato<span class="campoObligatorio">*</span>:
                                            </label>
                                        </div>
                                        <div class="campos">
                                            <input type="text" id="contrato" value=""  class="cmpRequerido k-textbox" required />
                                            <span class="k-invalid-msg" data-for="contrato"></span>
                                        </div>
                                    </div>	
                                    <div id="contenedorFormPuntos" >
                                        <div class="contenedorDescripcion">							
                                            <div class="etiquetas">
                                                <label for="ubicacion">
                                                    Ubicaci&oacute;n<span class="campoObligatorio">*</span>:
                                                </label>
                                            </div>
                                            <div class="campos">
                                                <input type="text" id="ubicacion" name="ubicacion" data-mfd-name="ubicacion" value=""  class="cmpRequerido k-textbox" required maxlength="800"/>
                                                <span class="k-invalid-msg" data-for="ubicaciones"></span>
                                            </div>
                                        </div>	
                                        <div class="contenedorTipoEstructura">							
                                            <div class="etiquetas">
                                                <label for="tipoEstructura">
                                                    Tipo de Estructura:
                                                </label>
                                            </div>
                                            <div class="campos">
                                                <input type="text" id="tipoEstructura" name="tipoEstructura" data-mfd-name="tipoEstructura" value=""  class="k-textbox"  maxlength="300"x/>
                                                <span class="k-invalid-msg" data-for="tipoEstructura"></span>
                                            </div>
                                        </div>	

                                        <div class="contenedorLatitud">							
                                            <div class="etiquetas">
                                                <label for="latitud">Latitud: </label>
                                            </div>
                                            <div class="campos">
                                                <input type="text" id="latitud" name="latitud" data-mfd-name="latitud" value="" class="k-textbox"/>
                                            </div>
                                        </div>
                                        
                                        <div class="contenedorLongitud">							
                                            <div class="etiquetas">
                                                <label for="longitud">Longitud: </label>
                                            </div>
                                            <div class="campos">
                                                <input type="text" id="longitud" name="longitud" data-mfd-name="longitud" value="" class="k-textbox"/>
                                            </div>
                                        </div>

                                        <div class="contenedorObservacion">
                                            <div class="etiquetas">
                                                <label for="observacion">
                                                    Observaci&oacute;n
                                                </label>
                                            </div>
                                            <div class="campos">
                                                <textarea id="observacion" name="observacion" data-mfd-name="observacion" class="k-textbox"></textarea>
                                            </div>
                                        </div>

                                        <div class="contenedorEstado">
                                            <div class="etiquetas">
                                                <label for="estado">
                                                    Estado<span class="campoObligatorio">*</span>:
                                                </label>
                                            </div>
                                            <div class="campos">
                                                <select id="estado" name="estado" class="lstRequerido" data-mfd-name="estado" required >
                                                    <option value=""></option>
                                                    <option value="1">ACTIVO</option>
                                                    <option value="2">INACTIVO</option>
                                                </select>
                                                <span class="k-invalid-msg" data-for="estados"></span>
                                            </div>
                                        </div>
                                        <div id="contenedorCampoOculto">
                                            <input type="hidden" id="codigo" name="codigo" data-mfd-name="codigo" value=""/>
                                         </div>
                                    </div>
                                      <input type="hidden" id="act" name="act"  value=""/>
                                    
                                    <div class="botones">
                                        <input type="button" id="guardar" value="Guardar" />
                                        <input type="button" id="modificar" value="Modificar" />
                                        <input type="button" id="consultar" value="Consultar" />                                        
                                        <input type="button" id="limpiar" value="Limpiar" />                                        
                                    </div>
				</div>
                            <div id="grillaPuntoVertimiento"></div>
                    </div>
                    <% } %>
                </section>
            </section>
        </section>
	</body>
</html>

