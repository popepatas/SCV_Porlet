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


<script id="template-puntos-vertimiento" type="text/x-handlebars-template">
    
       <div>Puntos de Vertimientos</div>     
        {{#each puntos}}
                {{#if estado 'equal' 1}}
                        <div class="contenedorPunto">
                                <div class="etiqueta ini-ubicacion">Ubicación</div>
                                <div class="campo">{{ubicacion}} <input type="hidden" id="codigoPuntosMonitoreo" name="codigoPuntosMonitoreo" value="{{codigo}}"> </div>
                                <div class="etiqueta">CIIU</div> 
                                <div class="campo">
                                    <select name="actividadesMonitoreo">					
                                    </select>
                                </div>
                                
                        </div>
                {{/if}}
        {{/each}}
         <div>       
            <span class="k-invalid-msg val-puntos" data-for="actividades" role="alert">
                <span class="k-icon k-warning"></span>
                   
            </span>
         </div>        
</script>  
<script id="template-actividades-parametros" type="text/x-handlebars-template">
        <div class="contenedorInfoActParam">
            <div class="tituloActParam">
                    Parámetros:
            </div>
             {{#each parametros}}
             <div class="labelParam">
                 {{parametro}}
             </div>
             {{/each}}
        </div>
</script>

<!-- Si el usuario no tiene permisos -->
<% if(validar == false){ %>
<div class="contenidoPrincipal" >
    <p>Lo sentimos, usted no tiene permisos para acceder a esta pagina.</p>
</div>

<!-- Si el usuario tiene permisos -->
<% } else if(validar == true){ %>       
<div class="contenidoPrincipal" >
    <input type="hidden" id="codigoMonitoreo" value="" />
        <div id="div_registrar_monitoreo" class="formularios">
            <div id="contenedorFechaMonitoreo" >
                 <div class="etiquetas">
                     <label for="fechaMonitoreo">
                        Fecha del Monitoreo <span class="campoObligatorio">*</span>:
                     </label>	
                 </div>
                 <div class="campos">
                       <input type="date" data-role='datepicker' id="fechaMonitoreo" name="fechaMonitoreo" data-type="date" class="cmpRequerido cmpFecha" required  />
                        <span data-for='fechaMonitoreo' class='k-invalid-msg'></span>
                 </div>
             </div>	
             <div id="contenedorLaboratorioMonitoreo">
                <div class="etiquetas">
                    <label for="laboratorioMonitoreo">
                        Laboratorio <span class="campoObligatorio">*</span>:
                    </label>	
                </div>
                <div class="campos">
                    <select id="laboratorioMonitoreo" name="laboratorioMonitoreo" class="lstRequerido"  required >
                        <option value=""></option>							  	
                     </select>
                    <span class="k-invalid-msg" data-for="laboratorioMonitoreo"></span>
                </div>
            </div>
             <div id="contenedorConsultorMonitoreo">
                <div class="etiquetas">
                    <label for="consultorMonitoreo">
                        Consultor:
                    </label>	
                </div>
                <div class="campos">
                    <select id="consultorMonitoreo" name="consultorMonitoreo" class="lstRequerido"  >
                        <option value=""></option>							  	
                     </select>
                    <span class="k-invalid-msg" data-for="consultorMonitoreo"></span>
                </div>
            </div>
            <div id="contenedorHoraInicio">
                <div class="etiquetas">
                    <label for="horaInicioMonitoreo">
                        Hora de Inicio <span class="campoObligatorio">*</span>:
                    </label>	
                </div>
                <div class="campos">
                    <input type="text" id="horaInicioMonitoreo" name="horaInicioMonitoreo" class="cmpRequerido cmpTiempo" data-campo="hora" required  maxlength="10"/>
                    <span class="k-invalid-msg" data-for="horaInicioMonitoreo"></span>
                </div>
            </div>

            <div id="contenedorHoraFin">
                <div class="etiquetas">
                    <label for="horaFinMonitoreo">
                        Hora de finalización<span class="campoObligatorio">*</span>:
                    </label>	
                </div>
                <div class="campos">
                    <input type="text" id="horaFinMonitoreo" name="horaFinMonitoreo" class="cmpRequerido cmpTiempo cmpRangoTiempo"  data-campo-horaMayor="horaInicioMonitoreo" data-campo="hora" required maxlength="10" />                        	  	
                     <span class="k-invalid-msg" data-for="horaFinMonitoreo"></span>
                </div>
            </div>
             <div id="contenedorDuracionMonitoreo">
                <div class="etiquetas">
                    <label for="DuracionMonitoreo">
                        Duración (Horas)<span class="campoObligatorio">*</span>:
                    </label>	
                </div>
                <div class="campos">
                    <input type="text" id="duraccionMonitoreo" name="duraccionMonitoreo" class="cmpRequerido"  required  maxlength="3"/>                        	  	
                     <span class="k-invalid-msg" data-for="duraccionMonitoreo"></span>
                </div>
            </div>
            <div class="contenedorObservacionReprogramacion" id="contenedorObservacionReprogramacion" style="display:none;">							
                <div class="etiquetas">
                    <label for="observacionesReprogramacion">
                        Observaciones<span class="campoObligatorio">*</span>:
                    </label>
                </div>
                <div class="campos">
                    <textarea id="observacionesReprogramacion" name="observacionesReprogramacion"  value=""  class="k-textbox" ></textarea>
                    <span class="k-invalid-msg" data-for="observacionesReprogramacion"></span>
                </div>
            </div>
            <div id="contenedorPuntosVertimiento">
            </div>          
        </div>
        <div class="botones btn-monitoreo">                            
            <input type="button" id="GuardaMonitoreo" value="Guardar" /> 
            <input type="button" id="cancelarProgMonitoreo" value="Cancelar" />
        </div>
</div>
<% } %>

