<%@page import="modelo.ApiManager"%>
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
    <div class="contenidoPrincipal crear" >                 
           <div id="div_crear_proceso" class="formularios">
               <h2>Crear Proceso</h2>
               <div id="contenedorContrato">
                   <div class="etiquetas">
                       <label for="contrato">
                               Contrato<span class="campoObligatorio">*</span>:
                       </label>
                   </div>
                   <div class="campos">
                       <input type="text" id="contrato" name="contrato" value="<%= ApiManager.quitaNull(request.getParameter("contrato")) %>"  class="cmpRequerido k-textbox" required />
                       <span class="k-invalid-msg" data-for="contrato"></span>
                   </div>
               </div>	
               <div id="infoContrato">
                    <div class="contenedorCiclo">
                              <div class="etiquetas">
                                      <label for="cicloContrato">
                                              Ciclo
                                      </label>
                              </div>
                              <div class="campos"> 
                                  <input class="k-textbox "  type="text" id="cicloContrato" name="cicloContrato" value=""  />
                                      <span class="k-invalid-msg" data-for="cicloContrato"></span>
                              </div>
                      </div>
                      <div class="contenedoConsumo">
                              <div class="etiquetas">
                                      <label for="consumoContrato">
                                          Consumo de acueducto(m<sup>3</sup>)<span class="campoObligatorio">*</span>:
                                      </label>
                              </div>
                              <div class="campos"> 
                                      <input class="k-textbox cmpRequerido"  type="text" id="consumoContrato" name="consumoContrato" value="" required />
                                      <span class="k-invalid-msg" data-for="consumoContrato"></span>
                              </div>
                      </div>    
                       <div class="contenedoPozo">
                              <div class="etiquetas">
                                      <label for="pozo">
                                              Pozo o aforo vertimiento (m<sup>3</sup>):
                                      </label>
                              </div>
                              <div class="campos"> 
                                      <input class="k-textbox"  type="text" id="pozoContrato" name="pozoContrato" value=""  />
                                      <span class="k-invalid-msg" data-for="Pozo"></span>
                              </div>
                       </div>
                         <div class="contenedoSector">
                              <div class="etiquetas">
                                      <label for="sectorContrato">
                                              Sector:
                                      </label>
                              </div>
                              <div class="campos"> 
                                      <input class="k-textbox"  type="text" id="sectorContrato" name="sectorContrato" value=""  />
                                      <span class="k-invalid-msg" data-for="sectorContrato"></span>
                              </div>
                      </div>

                      
                </div>
               <div id="contenedorNitProceso">
                   <div class="etiquetas">
                       <label for="nitProceso">
                           Nit<span class="campoObligatorio">*</span>:
                       </label>
                   </div>
                   <div class="campos">
                       <input type="text" id="nitProceso" name="nitProceso" value="<%= ApiManager.quitaNull(request.getParameter("nit")) %>"  class="cmpRequerido cmpNit k-textbox" required />
                       <span class="k-invalid-msg" data-for="nitProceso"></span>
                   </div>
               </div>	
               <div class="contenedorFechaCreacion">							
                   <div class="etiquetas">
                       <label for="fechaCreacion">
                           Fecha Creación<span class="campoObligatorio">*</span>:
                       </label>
                   </div>
                   <div class="campos">
                       <input type="text" data-role='datepicker' id="fechaCreacion" name="fechaCreacion" data-type="date"  class="cmpRequerido" required  />
                       <span class="k-invalid-msg" data-for="fechaCreacion"></span>
                   </div>
               </div>	
           </div>

           <input type="hidden" id="codigo" value=""/>
           <div class="botones">
               <input type="button" id="crearProcesoVertimiento" value="Crear" />                                
           </div>                        
   </div>
   <% } %>                    
