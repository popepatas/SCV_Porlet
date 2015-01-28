<%-- 
    Document   : ConsultarProceso
    Created on : 18/12/2013, 11:49:15 AM
    Author     : Luis Cardozo
--%>
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
        <div id="div_crear_visita" class="formularios">
             <div id="contenedorTecnicos">
                <div class="etiquetas">
                    <label for="tecnicos">
                        Técnicos<span class="campoObligatorio">*</span>:
                    </label>	
                </div>
                <div class="campos">
                    <select id="tecnicos" name="tecnicos" class="lstRequerido"  required >
                        <option value=""></option>							  	
                     </select><span class="k-invalid-msg" data-for="tecnicos"></span>
                </div>
            </div>

            <div id="contenedorMotivos">
                <div class="etiquetas">
                    <label for="motivos">
                        Motivo<span class="campoObligatorio">*</span>:
                    </label>	
                </div>
                <div class="campos">
                    <select id="motivos" name="motivos" class="lstRequerido"  required >
                        <option value=""></option>							  	
                     </select><span class="k-invalid-msg" data-for="motivos"></span>
                </div>
            </div>

            <div class="contenedorObservacion">							
                <div class="etiquetas">
                    <label for="observaciones">
                        Observaciones<span class="campoObligatorio">*</span>:
                    </label>
                </div>
                <div class="campos">
                    <textarea id="observaciones" name="observaciones"  value=""  class="k-textbox" ></textarea>
                    <span class="k-invalid-msg" data-for="observaciones"></span>
                </div>
            </div>

            <div id="contenedorFechaVisita" style="display:none">
                 <div class="etiquetas">
                     <label for="fechaVisita">
                        Fecha Visita <span class="campoObligatorio">*</span>:
                     </label>	
                 </div>
                 <div class="campos">
                       <input type="date" data-role='datepicker' id="fechaVisita" name="fechaVisita" data-type="date"   class="cmpRequerido cmpFecha" required  />
                         <span data-for='fechaVisita' class='k-invalid-msg'></span>
                 </div>
             </div>	
            
            

        </div>
        <div class="botones">                            
            <input type="button" id="crearVisita" value="Crear" />                                
            <input type="button" id="limpiarCrearVisitas" value="Limpiar" />
            <input type="button" id="cancelarCrearVisitas" value="Cancelar" />
        </div>
</div>
<% } %>