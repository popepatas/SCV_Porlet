<%-- 
    Document   : Visitas
    Created on : 16-Jan-2014, 10:20:05
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
        <div id="div_consultar_visitas" class="formularios">
            <div class="titulo">Consultar Visitas</div>
            <div id="contenedorCodigoProcesoVisita" style="display: none;">
                <div class="etiquetas">
                    <label for="estadoVisita">
                            Código Proceso:
                    </label>
                </div>
                <div class="campos">
                    <input type="text" id="codigoProcesoVisita" name="codigoProcesoVisita" value=""  class="k-textbox" />    
                    <span class="k-invalid-msg" data-for="estadoVisita"></span>
                </div>
            </div>
             <div id="contenedorContratoVisita" style="display: none;">
                <div class="etiquetas">
                    <label for="contratoVisita">
                            Contrato:
                    </label>
                </div>
                <div class="campos">
                    <input type="text" id="contratoVisita" name="contratoVisita" value=""  class="k-textbox" />    
                    <span class="k-invalid-msg" data-for="contratoVisita"></span>
                </div>
            </div>
            <div id="contenedorNitVisita" style="display: none;">
                <div class="etiquetas">
                    <label for="nitVisita">
                            NIT:
                    </label>
                </div>
                <div class="campos">
                    <input type="text" id="nitVisita" name="nitVisita" value=""  class="k-textbox" />    
                    <span class="k-invalid-msg" data-for="nitVisita"></span>
                </div>
            </div>
            <div id="contenedorRazonSocialVisita" style="display: none;">
                <div class="etiquetas">
                    <label for="razonSocialVisita">
                            Razón Social:
                    </label>
                </div>
                <div class="campos">
                    <input type="text" id="razonSocialVisita" name="razonSocialVisita" value=""  class="k-textbox" />    
                    <span class="k-invalid-msg" data-for="razonSocialVisita"></span>
                </div>
            </div>
            
            <div id="contenedorDireccionVisita" style="display: none;">
                <div class="etiquetas">
                    <label for="direccionVisita">
                            Dirección :
                    </label>
                </div>
                <div class="campos">
                  <input type="text" id="direccionVisita" name="direccionVisita"  class="k-textbox"/>   
                </div>
            </div>
            <div id="contenedorComunaVisita" style="display: none;">
                <div class="etiquetas">
                    <label for="comuna">
                            Comuna :
                    </label>
                </div>
                <div class="campos">
                  <input type="text" id="comuna" name="comuna"  class="k-textbox"/>   
                </div>
            </div>
            <div id="contenedorTipoVisitaConsultar">
                <div class="etiquetas">
                    <label for="tipoVisitaConsultar">
                            Tipo de Visita:
                    </label>
                </div>
                <div class="campos">
                    <select id="tipoVisitaConsultar" name="tipoVisitaConsultar" value=""  class="">
                    </select>    
                    <span class="k-invalid-msg" data-for="tipoVisitaConsultar"></span>
                </div>
            </div>
            <div id="contenedorMotivoVisita2">
                <div class="etiquetas">
                    <label for="motivoVisita2">
                            Motivo Visita:
                    </label>
                </div>
                <div class="campos">
                    <select id="motivoVisita2" name="motivoVisita2" value=""  class="">
                        <option value=""> </option>
                    </select>    
                    <span class="k-invalid-msg" data-for="motivoVisita2"></span>
                </div>
            </div>	
             <div id="contenedorTipoVisita">
                <div class="etiquetas">
                    <label for="estadoVisita">
                            Estado:
                    </label>
                </div>
                <div class="campos">
                    <select id="estadoVisita" name="estadoVisita" value=""  class="">
                        <option value="">  </option>
                        <option value="1">ACTIVO</option>
                        <option value="3">CANCELADO</option>
                        <option value="4">REPROGRAMADO</option>
                        <option value="5">PENDIENTE DE RESULTADO</option>
                        <option value="6">REALIZADO(A)</option>
                    </select>    
                    <span class="k-invalid-msg" data-for="estadoVisita"></span>
                </div>
            </div>	
            <div class="texto"> Seleccioné un rango de fechas:</div>
            <div class="contenedorFechaInicio">							
                <div class="etiquetas">
                    <label for="fechaInicio">
                        Fecha Inicio:
                    </label>
                </div>
                <div class="campos">
                    <input type="text" data-role='datepicker' id="fechaInicio" name="fechaInicio" data-type="date"  class=""   />
                    <span class="k-invalid-msg" data-for="fechaInicio"></span>
                </div>
            </div>	

            <div class="contenedorFechaFin">							
                <div class="etiquetas">
                    <label for="fechaFin">
                        Fecha Final:
                    </label>
                </div>
                <div class="campos">
                    <input type="text" data-role='datepicker' id="fechaFin" name="fechaFin" data-type="date"  class="cmpRequerido"   />
                    <span class="k-invalid-msg" data-for="fechaFin"></span>
                </div>
            </div>	
        </div>
        <div class="botones">                            
            <input type="button" id="consultar" value="Consultar" />                                
            <input type="button" id="limpiar" value="Limpiar" />
            <input type="button" id="promVisita" value="Programar visita" />
        </div>
      <input type="button" id="generar" value="Generar Excel" />
     <div id="Contenedor-Grilla">
        <div id="grillaVisitas">

        </div>
    </div>
      <div id="contenedorArchivosSubidoVisita">
          <div id="grillaArchivosSubidoVisita"></div>
      </div>    
</div>
<% } %>