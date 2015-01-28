<%-- 
    Document   : ConsultarMonitoreo
    Created on : 13-Feb-2014, 19:39:31
    Author     : illustrato
--%>

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
<!-- Si el usuario no tiene permisos -->
<% if(validar == false){ %>
<div class="contenidoPrincipal" >
    <p>Lo sentimos, usted no tiene permisos para acceder a esta pagina.</p>
</div>

<!-- Si el usuario tiene permisos -->
<% } else if(validar == true){ %>
<div class="contenidoPrincipal" >
    <div id="div_consultar_monitoreo" class="formularios">
           <div id="contenedorCodigoProcesoMonitoreo" style="display: none;">
                <div class="etiquetas">
                    <label for="estadoVisita">
                            Código Proceso:
                    </label>
                </div>
                <div class="campos">
                    <input type="text" id="codigoProcesoMonitoreo" name="codigoProcesoMonitoreo" value=""  class="k-textbox" />    
                    <span class="k-invalid-msg" data-for="estadoVisita"></span>
                </div>
            </div>
             <div id="contenedorContratoMonitoreo" style="display: none;">
                <div class="etiquetas">
                    <label for="contratoMonitoreo">
                            Contrato:
                    </label>
                </div>
                <div class="campos">
                    <input type="text" id="contratoMonitoreo" name="contratoMonitoreo" value=""  class="k-textbox" />    
                    <span class="k-invalid-msg" data-for="contratoMonitoreo"></span>
                </div>
            </div>
            <div id="contenedorNitMonitoreo" style="display: none;">
                <div class="etiquetas">
                    <label for="nitVisita">
                            NIT:
                    </label>
                </div>
                <div class="campos">
                    <input type="text" id="nitMonitoreo" name="nitMonitoreo" value=""  class="k-textbox" />    
                    <span class="k-invalid-msg" data-for="nitMonitoreo"></span>
                </div>
            </div>
            <div id="contenedorRazonSocialMonitoreo" style="display: none;">
                <div class="etiquetas">
                    <label for="razonSocialMonitoreo">
                            Razón Social:
                    </label>
                </div>
                <div class="campos">
                    <input type="text" id="razonSocialMonitoreo" name="razonSocialMonitoreo" value=""  class="k-textbox" />    
                    <span class="k-invalid-msg" data-for="razonSocialMonitoreo"></span>
                </div>
            </div>
            <div id="contenedorDireccionMonitoreo" style="display: none;">
                <div class="etiquetas">
                    <label for="direccionMonitoreo">
                            Dirección :
                    </label>
                </div>
                <div class="campos">
                  <input type="text" id="direccionMonitoreo" name="direccionMonitoreo"  class="k-textbox"/>   
                </div>
            </div>
            <div id="contenedorComunaMonitoreo" style="display: none;">
                <div class="etiquetas">
                    <label for="comunaMonitoreo">
                            Comuna :
                    </label>
                </div>
                <div class="campos">
                  <input type="text" id="comunaMonitoreo" name="comunaMonitoreo"  class="k-textbox"/>   
                </div>
            </div>
             <div id="contenedorEstadoMonitoreoAdmon" style="display: none;">
                <div class="etiquetas">
                    <label for="estadoMonitoreoAdmon">
                            Estado Monitoreo:
                    </label>
                </div>
                <div class="campos">
                  <select type="text" id="estadoMonitoreoAdmon" name="estadoMonitoreoAdmon"   >   
                      <option value=""></option>
                      <option value="1">ACTIVO</option>
                      <option value="7">EN SUPERVISIÓN</option>                     
                  </select>
                </div>
            </div>
            <div id="contenedorFechaInicial" >
                 <div class="etiquetas">
                     <label for="fechaInicialMonitoreo">
                        Fecha Inicial :
                     </label>	
                 </div>
                 <div class="campos">
                       <input type="date" data-role='datepicker' id="fechaInicialMonitoreo" name="fechaInicialMonitoreo" data-type="date" class="cmpRequerido cmpFecha" required  />
                        <span data-for='fechaInicialMonitoreo' class='k-invalid-msg'></span>
                 </div>
             </div>
            <div id="contenedorFechaFinal" >
                 <div class="etiquetas">
                     <label for="fechaFinalMonitoreo">
                        Fecha Final:
                     </label>	
                 </div>
                 <div class="campos">
                       <input type="date" data-role='datepicker' id="fechaFinalMonitoreo" name="fechaFinalMonitoreo" data-type="date" class="cmpRequerido cmpFecha" required  />
                        <span data-for='fechaFinalMonitoreo' class='k-invalid-msg'></span>
                 </div>
             </div>       
        
    </div>
    <div class="botones">                            
        <input type="button" id="consultarMonitoreo" value="Consultar" /> 
        <input type="button" id="limpiarMonitoreo" value="Limpiar" />
        <input type="button" id="bntProgramarMonitoreo" value="Programar" />
    </div>
    <div id="contenedorTecnicoSupervision" style="display:none">
        <div class="etiquetas">
            Seleccionar Técnico que supervisa:
        </div>
        <div class="campos">
            <select id="tecnicosSupervision" >        
            </select>
        </div>
     </div>
    <div id="grillaMonitoreo" class=""></div>
</div>
<% } %>