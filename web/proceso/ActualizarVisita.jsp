<%-- 
    Document   : ConsultarProceso
    Created on : 18/12/2013, 11:49:15 AM
    Author     : Luis Cardozo
--%>
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

<!-- Si el usuario no tiene permisos -->
<% if(validar == false){ %>
<div class="contenidoPrincipal" >
    <p>Lo sentimos, usted no tiene permisos para acceder a esta pagina.</p>
</div>

<!-- Si el usuario tiene permisos -->
<% } else if(validar == true){ %>
<div class="contenidoPrincipal" >
        <div id="div_actualizar_visita" class="formularios">
             <div id="contenedorTecnicosAct">
                <div class="etiquetas">
                    <label for="tecnicosAct">
                        Técnicos<span class="campoObligatorio">*</span>:
                    </label>	
                </div>
                <div class="campos">
                    <select id="tecnicosAct" name="tecnicosAct" class="lstRequerido"  required >
                        <option value=""></option>							  	
                     </select><span class="k-invalid-msg" data-for="tecnicosAct"></span>
                </div>
            </div>

            <div id="contenedorMotivosAct">
                <div class="etiquetas">
                    <label for="motivosAct">
                        Motivo<span class="campoObligatorio">*</span>:
                    </label>	
                </div>
                <div class="campos">
                    <select id="motivosAct" name="motivosAct" class="lstRequerido k-textbox"  required >
                        <option value=""></option>							  	
                     </select><span class="k-invalid-msg" data-for="motivosAct"></span>
                </div>
            </div>

            <div class="contenedorObservacionAct">							
                <div class="etiquetas">
                    <label for="observacionesAct">
                        Observaciones<span class="campoObligatorio">*</span>:
                    </label>
                </div>
                <div class="campos">
                    <textarea id="observacionesAct" name="observacionesAct"  value=""  class="k-textbox" required ></textarea>
                    <span class="k-invalid-msg" data-for="observacionesAct"></span>
                </div>
            </div>

            <div id="contenedorFechaVisitaAct">
                 <div class="etiquetas">
                     <label for="fechaVisitaAct">
                        Fecha Visita: <span class="campoObligatorio">*</span>:
                     </label>	
                 </div>
                 <div class="campos">
                       <input type="date" data-role='datepicker' id="fechaVisitaAct" name="fechaVisitaAct" data-type="date"   class="cmpRequerido cmpFecha" required  />
                         <span data-for='fechaVisita' class='k-invalid-msg'></span>
                 </div>
             </div>	

        </div>
        <input type="hidden" id="codigo" value=""/>
        <div class="botones">                            
            <input type="button" id="actualizarVisitaAct" value="Actualizar" />                                
            <input type="button" id="limpiarActVisitasAct" value="Limpiar" />
            <!--<input type="button" id="cancelarActVisitasAct" value="Cancelar" />-->
        </div>
</div>
<% } %>