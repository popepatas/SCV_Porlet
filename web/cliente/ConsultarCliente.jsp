<%-- 
    Document   : ConsultarCliente
    Created on : 18/12/2013, 11:47:53 AM
    Author     : juanhgm
--%>

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
        <link rel="stylesheet" type="text/css" href="../css/formalize.css" />

        <link href='http://fonts.googleapis.com/css?family=PT+Sans' rel='stylesheet' type='text/css' />
        <link href='http://fonts.googleapis.com/css?family=Maven+Pro:400,500,700,900' rel='stylesheet' type='text/css'>

    <link rel="stylesheet" href="../css/styles.css" />
    <link rel="stylesheet" href="../js/librerias/select2/select2.css" />
    <link href="../js/librerias/Kendo/css/kendo.common.min.css" rel="stylesheet">
    <link href="../js/librerias/Kendo/css/kendo.default.min.css" rel="stylesheet">
    <script type="text/javascript" src="../js/librerias/jquery.min.js"></script>
    <script type="text/javascript" src="../js/librerias/jquery.migrate.min.js"></script>
    <script type="text/javascript" src="../js/librerias/jquery.formalize.min.js"></script>
    <script type="text/javascript" src="../js/funciones/funciones.js"></script>
    <script type="text/javascript" src="../js/dom/manipulacion.js"></script>
    <script type="text/javascript" src="../js/librerias/select2/select2.min.js"></script>
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
    <script  type="text/javascript" src="../js/librerias/Kendo/js/kendo.calendar.min.js"></script>
    <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.datepicker.min.js"></script>	

    <script type="text/javascript" src="../js/funciones/clientes/cliente.js"></script>
    <script type="text/javascript" src="../js/funciones/clientes/consultarCliente.js"></script>
    <script type="text/javascript" src="../js/funciones/clientes/logsCliente.js"></script>
    <script type="text/javascript" src="../js/funciones/proceso/inicio/crearProceso.js"></script>
        
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
                         
                                <section class="k-content">

                                                <div id="div_cliente" class="formularios">
                                                        <h2>Consultar Cliente</h2>
                                                        <br>
                                                        <div class="contenedorCodigo">
                                                                <div class="etiquetas">
                                                                        <label for="nitConsultar">
                                                                                NIT:
                                                                        </label>
                                                                </div>
                                                                <div class="campos">
                                                                        <input type="text" id="nitConsultar" name="nitConsultar" value="" class="k-textbox cmpNit" />
                                                                        <span class="k-invalid-msg" data-for="nit"></span>
                                                                </div>
                                                        </div>
                                                        <div class="contenedorDescripcion">
                                                                <div class="etiquetas">
                                                                        <label for="razonConsultar">
                                                                                Razón Social:
                                                                        </label>
                                                                </div>
                                                                <div class="campos">
                                                                        <input class="k-textbox"  type="text" id="razonConsultar" name="razonConsultar" value="" />
                                                                        <span class="k-invalid-msg" data-for="razon"></span>
                                                                </div>
                                                        </div>

                                                        <div class="contenedorCiuu" style="margin-right: 150px;">
                                                                <div class="etiquetas">
                                                                        <label for="ciiu">
                                                                                Actividad Económica:
                                                                        </label>
                                                                </div>
                                                                <select id="ciiuConsultar"  name="ciiuConsultar" class=""  >
                                                                    <option value=""></option>							  	

                                                                </select>
                                                            <span class="k-invalid-msg" data-for="ciiu"></span>
                                                        </div>

                                     

                                                       
                                                         <div class="contenedoDireccion">
                                                                <div class="etiquetas">
                                                                        <label for="direccionConsultar">
                                                                                Dirección: 
                                                                        </label>
                                                                </div>
                                                                <div class="campos"> 
                                                                        <input class=" k-textbox "  type="text" id="direccionConsultar" name="direccionConsultar" value="" />
                                                                        <span class="k-invalid-msg" data-for="Pozo"></span>
                                                                </div>
                                                        </div>

                                                         <div class="contenedoBarrio">
                                                                <div class="etiquetas">
                                                                        <label for="barrioConsultar">
                                                                                Barrio 
                                                                        </label>
                                                                </div>
                                                                <div class="campos"> 
                                                                        <input class=" k-textbox "  type="text" id="barrioConsultar" name="barrioConsultar" value="" />
                                                                        <span class="k-invalid-msg" data-for="Pozo"></span>
                                                                </div>
                                                        </div>


                                                          <div class="contenedoComuna">
                                                                <div class="etiquetas">
                                                                        <label for="comunaConsultar">
                                                                                Comuna: 
                                                                        </label>
                                                                </div>
                                                                <div class="campos"> 
                                                                        <input class=""  type="text" id="comunaConsultar" name="comunaConsultar" value="" />
                                                                        <span class="k-invalid-msg" data-for="comuna"></span>
                                                                </div>
                                                        </div>
                                                       
                                                        <div class="contenedoUsoServicio">
                                                                <div class="etiquetas">
                                                                        <label for="usoServicioConsultar">
                                                                                Uso del Servicio: 
                                                                        </label>
                                                                </div>
                                                                <div class="campos"> 

                                                                    <select id="usoServicioConsultar" name="usoServicioConsultar" class="">
                                                                            <option value=""></option>
                                                                            <option value="1">COMERCIAL</option>
                                                                            <option value="2">INDUSTRIAL</option>
                                                                            <option value="3">OFICIAL</option>
                                                                            <option value="4">ESPECIAL</option>
                                                                        </select>
                                                                        <span class="k-invalid-msg" data-for="usoServicioConsultar"></span>
                                                                </div>
                                                        </div>
                                                      
                                                        
                                                        <br>
                                                        <div class="botones">               

                                                            <input type="button" id="consultar" value="Consultar" />
                                                            <input type="button" id="limpiar" value="Limpiar"  />  
                                                            <input type="button" id="nuevo" value="Nuevo"  />  
                                                            <input type="button" id="generarPDF" value="Notificar PDF"  />  
                                                                <!--<input type="button" id="guardar" value="Limpiar"  />-->
                                                        </div>
                                                </div>

                                </section>
                                <section class="contenedor-grilla">
                                        <div id="grillaCliente">

                                        </div>
                                </section>
                                                 
                      
                    </div>
                </section>
            </section>
                      <%@include file="../templates/footer.html" %>
            <div id="modalBox">            
            </div>
            <div id="modalBox2">            
            </div>
             </section>
     
       <% } %>
    
    </body>
    
</html>
