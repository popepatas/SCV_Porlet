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
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        
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
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.fx.min.js"></script>
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
        
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.calendar.min.js"></script> 
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.popup.min.js"></script> 
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.datepicker.min.js"></script> 
        
        <script type="text/javascript" src="../js/funciones/parametrizacion/consultores.js"></script>
        
        <script type="text/javascript" src="../js/funciones/masterPage.js" ></script>
                        
        <title>Consultores</title>
    </head>
    <body>
        
        <section id="Wrapper">
            <%@include file="../templates/header.html" %>
        
            <section id="ContenedorContent">
                
                    <%@include file="../templates/main_nav.html" %>                    
                    <%@include file="../templates/left_nav_parametrizacion.html" %>                  
                            <!-- Si el usuario no tiene permisos -->
                            <% if(validar == false){ %>
                            <div id="div_laboratorios_consultores" class="formularios">
                                <p>Lo sentimos, usted no tiene permisos para acceder a esta pagina.</p>
                            </div>

                            <!-- Si el usuario tiene permisos -->
                            <% } else if(validar == true){ %>
                            <div class="overlay" style="display:none"> 
                                <img  class="loader" src="../css/loader.gif" />
                            </div>
                            <div id="div_laboratorios_consultores" class="formularios">
                                <h2>Consultores</h2>
                                
                                <div id="contenedorNombre">
                                    <div class="etiquetas">
                                        <label for="nombre">
                                            Nombre<span class="campoObligatorio">*</span>:
                                        </label>	
                                    </div>
                                    <div class="campos">
                                        <input type="text" id="nombre" name="nombre"  value=""  class="cmpRequerido k-textbox"required/>
                                        <span class="k-invalid-msg" data-for="nombre"></span>
                                    </div>
                                </div>
                                <div id="contenedorConsulCampos">
                                    <div id="contenedorApellidos">
                                        <div class="etiquetas">
                                            <label for="apellidos">
                                                Apellidos:
                                            </label>	
                                        </div>
                                        <div class="campos">
                                            <input type="text" id="apellidos" name="apellidos"  value=""  class="k-textbox"/>
                                            <span class="k-invalid-msg" data-for="apellidos"></span>
                                        </div>
                                    </div>
                               
                                    <div id="contenedorIdentificacion">
                                        <div class="etiquetas">
                                            <label for="resolucion">
                                                Identificación <span class="campoObligatorio">*</span>:
                                            </label>	
                                        </div>
                                        <div class="campos">
                                            <input type="text" id="identificacion" name="identificacion" value="" class="cmpRequerido " required/>
                                            <span class="k-invalid-msg" data-for="identificacion"></span>
                                        </div>
                                    </div>
                                </div>                                
                                <div id="contenedorDireccion">
                                    <div class="etiquetas">
                                        Direcci&oacute;n :
                                    </div>
                                    <div class="campos">
                                        <div id="direccion">
                                            <select id="ubicacion" name="completaDireccion" class="cmpDireccion" >
                                                <option value=""></option>
                                                <option value="AVN">AVN</option>
                                                <option value="CRA">CRA</option>							  	
                                                <option value="CLL">CLL</option>
                                                <option value="DGN">DGN</option>
                                                <option value="TVL">TVL</option>
                                            </select>						
                                            <input type="text" id="princial" name="completaDireccion" value="" class="cmpDireccion k-textbox"/> 
                                            <span> # </span> 
                                            <input type="text" id="numCasaPrincipal" name="completaDireccion" value=""  class="cmpDireccion k-textbox"/>
                                            <span> - </span> 
                                            <input type="text" id="numCasaSecundario"  name="completaDireccion" value="" class="cmpDireccion k-textbox"/>
                                            <input type="hidden"  id="direccionCompleta" value=""  />
                                            <span class="k-invalid-msg" data-for="completaDireccion"></span>
                                        </div>
                                    </div>
                                </div>
                                <div id="contenedorTelefono1">
                                    <div class="etiquetas">
                                        <label for="telefono1">
                                            Tel&eacute;fono 1:
                                        </label>	
                                    </div>
                                    <div class="campos">
                                        <input type="text" id="telefono1" name="telefono1" value="" class="cmpRequerido"  maxlength="10"/>
                                        <span class="k-invalid-msg" data-for="telefono1"></span>
                                    </div>
                                </div>
                                <div id="contenedorTelefono2">
                                    <div class="etiquetas">
                                        <label for="telefono2">
                                            Tel&eacute;fono 2:
                                        </label>	
                                    </div>
                                    <div class="campos">
                                        <input type="text" id="telefono2"  name="telefono2" value="" class="" maxlength="10" />
                                        <span class="k-invalid-msg" data-for="telefono2"></span>
                                    </div>
                                </div>
                                <div id="contenedorEmail">
                                    <div class="etiquetas">
                                        <label for="email">
                                            E-mail:
                                        </label>	
                                    </div>
                                    <div class="campos">
                                        <input type="email" id="email" name="email" value="" class="cmpRequerido cmpEmail k-textbox" maxlength="100" />
                                        <span class="k-invalid-msg" data-for="email"></span>
                                    </div>
                                </div>
                               
												
                                <input type="hidden" id="codigo" value=""/>
                                <div class="botones">
                                    <input type="button" id="guardar" value="Guardar" />
                                    <input type="button" id="modificar" value="Modificar" />
                                    <input type="button" id="consultar" value="Consultar" />
                                    <input type="button" id="limpiar" value="Limpiar">
                                </div>
                            </div>
                            <% } %>
                        <div id="grillaConsultores">
                            
                        </div>                    
                
            </section>
        </section>
    </body>
</html>
