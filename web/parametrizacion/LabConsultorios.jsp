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
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        
        <link rel="stylesheet" type="text/css" href="../css/styles.css" />
        <link rel="stylesheet" type="text/css" href="../css/formalize.css">
        <link href='http://fonts.googleapis.com/css?family=PT+Sans' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" href="../css/normalize.css" />
        <link href="../js/librerias/Kendo/css/kendo.common.min.css" rel="stylesheet" />
        <link href="../js/librerias/Kendo/css/kendo.default.min.css" rel="stylesheet" />
        <link href="../js/librerias/masterField/css/jquery.masterField.css" rel="stylesheet" />
        
        <script type="text/javascript" src="../js/librerias/jquery.min.js"></script>
        <script type="text/javascript" src="../js/librerias/jquery.migrate.min.js"></script>
        <script type="text/javascript" src="../js/librerias/jquery.formalize.min.js"></script>
        <script type="text/javascript" src="../js/funciones/funciones.js"></script>
        <script type="text/javascript" src="../js/dom/manipulacion.js"></script>
        <script type="text/javascript" src="../js/librerias/jquery.json.min.js"></script>
        <script type="text/javascript" src="../js/librerias/masterField/js/jquery.masterField.js"></script>
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
        
        <script type="text/javascript" src="../js/funciones/parametrizacion/laboratorios.js"></script>
        
        <script type="text/javascript" src="../js/funciones/masterPage.js" ></script>
                        
        <title>SCV - Laboratorios </title>
    </head>
    <body>
        
        <section id="Wrapper">
            <%@include file="../templates/header.html" %>
        
            <section id="ContenedorContent">
                
                   <%@include file="../templates/main_nav.html" %>
                    
                    <%@include file="../templates/left_nav_parametrizacion.html" %>                  
                    
                            <!-- Si el usuario no tiene permisos -->
                            <% if(validar == false){ %>
                            <div id="div_laboratorios" class="formularios">
                                <p>Lo sentimos, usted no tiene permisos para acceder a esta pagina.</p>
                            </div>

                            <!-- Si el usuario tiene permisos -->
                            <% } else if(validar == true){ %>
                            <div class="overlay" style="display:none"> 
                                <img  class="loader" src="../css/loader.gif" />
                            </div>
                            <div id="div_laboratorios" class="formularios">
                                <h2>Laboratorios </h2>
                               
                                <div id="contenedorNombre">
                                    <div class="etiquetas">
                                        <label for="nombre">
                                            Nombre <span class="campoObligatorio">*</span>:
                                        </label>	
                                    </div>
                                    <div class="campos">
                                        <input type="text" id="nombre" name="nombre"  value=""  class="cmpRequerido k-textbox"required/>
                                        <span class="k-invalid-msg" data-for="nombre"></span>
                                    </div>
                                </div>
                                                               
                                <div id="contenedorPersonaContacto">
                                     <div class="etiquetas">
                                         <label for="personaContacto">
                                            Persona de Contacto <span class="campoObligatorio">*</span>:
                                         </label>	
                                     </div>
                                     <div class="campos">
                                         <input type="text" id="personaContacto" name="personaContacto" value="" class="k-textbox cmpRequerido" required/>
                                         <span class="k-invalid-msg" data-for="personaContacto"></span>
                                     </div>
                                </div>
                                 <div id="contenedorResolucion">
                                     <div class="etiquetas">
                                         <label for="resolucion">
                                             Resoluci&oacute;n de Acreditaci&oacute;n <span class="campoObligatorio">*</span>:
                                         </label>	
                                     </div>
                                     <div class="campos">
                                         <input type="text" id="resolucion" name="resolucion" value="" class="k-textbox cmpRequerido" required/>
                                         <span class="k-invalid-msg" data-for="resolucion"></span>
                                     </div>
                                 </div>
                                 <div id="contenedorVigencia">
                                     <div class="etiquetas">
                                         <label for="vigencia">
                                            Vigencia <span class="campoObligatorio">*</span>:
                                         </label>	
                                     </div>
                                     <div class="campos">
                                           <input type="text" data-role='datepicker' id="vigencia" name="vigencia" data-type="date"   class="cmpRequerido" required  />
                                             <span data-for='vigencia' class='k-invalid-msg'></span>
                                     </div>
                                 </div>
                                
                                <div id="contenedorDireccion">
                                    <div class="etiquetas">
                                        Direcci&oacute;n <span class="campoObligatorio">*</span>:
                                    </div>
                                    <div class="campos">
                                        <div id="direccion">
                                            <select id="ubicacion" name="completaDireccion" class="cmpDireccion " >
                                                <option value=""></option>
                                                <option value="AV">AV</option>
                                                <option value="CRA">CRA</option>							  	
                                                <option value="CLL">CLL</option>
                                                <option value="DG">DG</option>
                                                <option value="TV">TV</option>
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
                                            Tel&eacute;fono 1<span class="campoObligatorio">*</span>:
                                        </label>	
                                    </div>
                                    <div class="campos">
                                        <input type="text" id="telefono1" name="telefono1" value="" class="cmpRequerido" required />
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
                                        <input type="text" id="telefono2"  name="telefono2" value="" class="" />
                                        <span class="k-invalid-msg" data-for="telefono2"></span>
                                    </div>
                                </div>
                                <div id="contenedorEmail">
                                    <div class="etiquetas">
                                        <label for="email">
                                            E-mail<span class="campoObligatorio">*</span>:
                                        </label>	
                                    </div>
                                    <div class="campos">
                                        <input type="email" id="email" name="email" value="" class="cmpRequerido cmpEmail k-textbox" required />
                                        <span class="k-invalid-msg" data-for="email"></span>
                                    </div>
                                </div>
                                <div id="acreditacion">
                                    <h3>
                                            Parámetros Acreditados:
                                    </h3>
                                    <div id="contenedorParametrosAcreditados">                                   
                                        <div id="contenedorParametroFisQuim">
                                            <div class="etiquetas">
                                                       Parámetro Fisicoquímico:                                             
                                            </div>
                                            <div class="campos">
                                                <select id="ParamFisQuim" name="ParamFisQuim" data-mfd-name="codigoParam" >                                                
                                                </select>

                                            </div>
                                        </div>
                                        <div id="contenedorCampoOculto">
                                             <input type="hidden" id="codigoAcredParam" name="codigoAcredParam" data-mfd-name="codigoAcredParam" value=""/>
                                       </div>
                                     </div>
                                    <span class="k-invalid-msg" data-for="ParamFisQuim"></span>
                               </div>
												
                                <input type="hidden" id="codigo" value=""/>
                                <div class="botones">
                                    <input type="button" id="guardar" value="Guardar" />
                                    <input type="button" id="modificar" value="Modificar" />
                                    <input type="button" id="consultar" value="Consultar" />                                    
                                   <input type="button" id="limpiar" value="Limpiar" />      
                                    
                                </div>
                            </div>
                            <% } %>
                        <div id="grillaLaboratorios">
                            
                        </div>
                   
               
            </section>
        </section>
    </body>
</html>
