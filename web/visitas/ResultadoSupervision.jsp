
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
<% if(validar == false){ %>
<div id="div_registrar_resultado_monitoreo" class="formularios" style="display: none;">
    <p>Lo sentimos, usted no tiene permisos para acceder a esta pagina.</p>
</div>

<!-- Si el usuario tiene permisos -->
<% } else if(validar == true){ %>
<div id="div_registrar_resultado_monitoreo" class="formularios" style="display: none;">
            <div id="contenedorTecnicosSupMonitoreo">
                <div class="etiquetas">
                    <label for="tecnicos">
                        Técnicos<span class="campoObligatorio">*</span>:
                    </label>	
                </div>
                <div class="campos">
                    <select id="tecnicosSupMonitoreo" name="tecnicosSupMonitoreo" class="lstRequerido"  required >
                        <option value=""></option>							  	
                     </select>
                </div>
            </div>
            <div class="contenedorEstuvoSupMonitoreo">							
                <div class="etiquetas">
                    <label for="estuvoSupMonitoreo">
                       ¿Estuvo en el Monitoreo? 
                    </label>
                </div>
                <div class="campos">
                     <select id="estuvoSupMonitoreo" name="estuvoSupMonitoreo" class="">
                        <option value=""></option>							  	
                        <option value="SI">SI</option>
                        <option value="SI">NO</option>
                     </select>
                </div>
            </div>
            <div id="contenedorDatoSupMonitoreo" display="none">
                <div class="contenedorResultado">							
                    <div class="etiquetas">
                        <label for="resultado">
                            Observaciones:
                        </label>
                    </div>
                    <div class="campos">
                        <textarea id="ObservacionesSupMonitoreo" name="ObservacionesSupMonitoreo"  value=""  class="k-textbox" maxlength=500""></textarea>                        
                    </div>
                </div>
                <div class="contenedorArchivos">							
                    <div class="etiquetas">
                        <label for="archivos">
                            Archivos:
                        </label>
                    </div>
                    <div class="campos">
                        <input name="archivosSupMonitoreo" id="archivosSupMonitoreo" type="file" />
                        <span class="k-invalid-msg" data-for="archivos"></span>
                    </div>
                </div>
         </div>

        <input type="hidden" id="codigoSupMonitoreo" value=""/>
        <input type="hidden" id="codigoProceso" value=""/>
        <div class="botones">
            <input type="button" id="guadarResultadoSup" name="guadarResultadoSup" value="Registrar" />                                
        </div>
</div>
<% } %>

