<%-- 
    Document   : ConsultarProceso
    Created on : 18/12/2013, 11:49:15 AM
    Author     : Luis Cardozo
--%>

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
    <!-- Si el usuario no tiene permisos -->
    <% if(validar == false){ %>
    <div class="contenidoPrincipal" >
        <p>Lo sentimos, usted no tiene permisos para acceder a esta pagina.</p>
    </div>

    <!-- Si el usuario tiene permisos -->
    <% } else if(validar == true){ %>
<div class="contenidoPrincipal" >
        <div id="div_manejar_lodos" class="formularios">
             <div id="contenedorNombreEmpresa">
                <div class="etiquetas">
                    <label for="nombreEmpresa">
                        Nombre empresa<span class="campoObligatorio">*</span>:
                    </label>	
                </div>
                <div class="campos">
                    <input type="text" id="nombreEmpresaLodos" name="nombreEmpresaLodos" value="" class="cmpRequerido" required />
                </div>
            </div>

                 <div id="contenedorRecolecta">
                     <div class="etiquetas">
                         Recolecta
                     </div>
                     <div class="campos">
                         <input type="checkbox" id="recolectaLodos" value="NO" class="k-checkbox"/>
                     </div>
                 </div>
            
            <div id="contenedorExtraRecolecta" class="campoOculto">
                
                <div id="contenedorVolumen">
                    <div class="etiquetas">
                        <label for="volumenLodos">
                            Volumen<span class="campoObligatorio">*</span>:
                        </label>	
                    </div>
                    <div class="campos">
                        <input type="text" id="volumenLodos" name="volumenLodos" value="" class="cmpRequerido" required />
                        <span class="k-invalid-msg" data-for="volumenLodos"></span>
                    </div>
                </div>
                
                <div id="contenedorFechaRecoleccionLodos">
                    <div class="etiquetas">
                        <label for="recoleccionLodos">
                           Fecha Recoleccion <span class="campoObligatorio">*</span>:
                        </label>	
                    </div>
                    <div class="campos">
                          <input type="text" data-role='datepicker' id="recoleccionLodos" name="recoleccionLodos" data-type="date"   class="cmpRequerido" required  />
                            <span data-for='recoleccionLodos' class='k-invalid-msg'></span>
                    </div>
                </div>
                
                <div id="contenedorFrecuencia">
                    <div class="etiquetas">
                        <label for="frecuenciaLodos">
                            Frecuencia/año <span class="campoObligatorio">*</span>:
                        </label>	
                    </div>
                    <div class="campos">
                        <input type="text" id="frecuenciaLodos" name="frecuenciaLodos" value="" class="cmpRequerido" required />
                    </div>
                </div>
                
            </div>
            
                 <div id="contenedorTransporte">
                     <div class="etiquetas">
                         Transporte
                     </div>
                     <div class="campos">
                         <input type="checkbox" id="transporteLodos" value="NO" class="k-checkbox"/>
                     </div>
                 </div>

                 <div id="contenedorDispone">
                     <div class="etiquetas">
                         Dispone
                     </div>
                     <div class="campos">
                         <input type="checkbox" id="disponeLodos" value="NO" class="k-checkbox"/>
                     </div>
                 </div>
            
            <div id="contenedorExtraDispone" class="campoOculto">
                <div id="contenedorSitioDispoLodos">
                    <div class="etiquetas">
                        <label for="sitioDispoLodos">
                            Sitio de disposicion <span class="campoObligatorio">*</span>:
                        </label>	
                    </div>
                    <div class="campos">
                        <input type="text" id="sitioDispoLodos" name="sitioDispoLodos" value="" class="cmpRequerido" required />
                    </div>
                </div>     
            </div>

        </div>
        <input type="hidden" id="codigoLodo" value=""/>
        <div class="botones">                            
            <input type="button" id="guardarLodos" value="Añadir" />
            <input type="button" id="modificarLodos" value="Actualizar" />
            <input type="button" id="consultarLodos" value="Consultar" />
            <input type="button" id="limpiarLodos" value="Limpiar" />
            <input type="button" id="cancelarLodos" value="Cancelar" /> 
        </div>
        <div id="grillaLodos"></div>
</div>
<% } %>