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
    <div id="div_contendorInformacionProceso" class="formularios">
        <p>Lo sentimos, usted no tiene permisos para acceder a esta pagina.</p>
    </div>

    <!-- Si el usuario tiene permisos -->
    <% } else if(validar == true){ %>
<script id="template-Preguntas-ver-caracterizacion" type="text/x-handlebars-template">    
          {{#each requisitos}}     
             <div class="containerRequisito">
              <input type="hidden" name="codigoRequisito" value="{{codigo}}" />
              <div class="campoCheck">
                  {{checkbox checkeado 'cheackeado'}}
              </div>
              <div class="textoPregunta">
                  {{reguisito}}
              </div>
          </div> 
        {{/each}}
             
</script>

<div id="div_contendorInformacionProceso" class="formularios">
    
        <div id="contenedorLaboratorioProcesoSeco">
                <div class="etiquetas">
                    <label for="laboratorioProcesoSeco">
                        Laboratorio:
                    </label>	
                </div>
                <div class="campos">
                    <select id="laboratorioProcesoSeco" name="laboratorioProcesoSeco">
                        <option value=""></option>							  	
                     </select>
                    <span class="k-invalid-msg" data-for="laboratorioProcesoSeco"></span>
                </div>
            <div id="contenedorInfoBasicaLab" style="display:none">
                <div>
                    <span>Teléfono: </span><span id="infoLabTel"> </span>
                </div>           
                <div>
                     <span>Dirección: </span><span id="infoLabDir"> </span>
                </div>
             </div>
        </div>
      <div id="contenedorConsultorProcesoSeco">
                <div class="etiquetas">
                    <label for="consultorProcesoSeco">
                        Consultor:
                    </label>	
                </div>
                <div class="campos">
                    <select id="consultorProcesoSeco" name="consultorProcesoSeco" class=" "  >
                        <option value=""></option>							  	
                     </select>
                    <span class="k-invalid-msg" data-for="consultorProcesoSeco"></span>
                </div>
                <div id="contenedorInfoBasicaCon" style="display:none">
                    <div>
                        <span>Teléfono: </span><span id="infoConTel"> </span>
                    </div>           
                    <div>
                         <span>Dirección: </span><span id="infoConDir"> </span>
                    </div>
                </div>
        </div> 
          <div class="contenedorFechaRadicacionProcesoSeco">							
                  <div class="etiquetas">
                      <label for="fechaRadicacionProcesoSeco">
                          Fecha Radicación <span class="campoObligatorio">*</span>:
                      </label>
                  </div>
                  <div class="campos">
                      <input type="text" data-role='datepicker' id="fechaRadicacionProcesoSeco" name="fechaRadicacionProcesoSeco" data-type="date"  data-lesserdate-field="fechaEntregaProcesoSeco" required  />
                      <span class="k-invalid-msg" data-for="fechaRadicacionProcesoSeco"></span>
                  </div>
          </div>
            <div class="contenedorFechaEntregaProcesoSeco">							
                    <div class="etiquetas">
                        <label for="fechaEntregaProcesoSeco">
                            Fecha Entrega <span class="campoObligatorio">*</span>:
                        </label>
                    </div>
                    <div class="campos">
                        <input type="text" data-role='datepicker' id="fechaEntregaProcesoSeco" name="fechaEntregaProcesoSeco" data-type="date" data-greaterdate-field="fechaRadicacionProcesoSeco" required  />
                        <span class="k-invalid-msg" data-for="fechaEntregaProcesoSeco"></span>
                    </div>
            </div>
           <div id="ObservacionProcesoSeco" >
                 <div class="etiquetas">
                        Observaciones:
                  </div>
                  <div class="campos">
                      <textarea id="observacionesProcesoSeco"  name="observacionesProcesoSeco" maxlength="1000" ></textarea>
                        
                  </div>
            </div>
      
            <div id="contenedorAchivosProcesoSeco" >
              <div id="contenedorEnlaceCarga">
                  <a id="cargarArchivosProceso" href="#" class="cv-cargarArchivos" >Cargar Archivos</a>
                  <a id="verArchProceso" href="#" class="cv-verArchivos">Ver Archivos Cargados</a>
              </div>
              <div id="contenedorCargaArchivosProcesoSeco" style="display: none">							
                    <div class="etiquetas">
                        <label for="archivosProcesoSeco">
                           Seleccione sus Archivos
                        </label>
                    </div>
                    <div class="campos">
                        <input name="filesProcesoSeco" id="filesProcesoSeco" type="file" />
                        
                    </div>
                </div>
              <div id="contenedorArchivosSubidos"  style="display: none"> 
                  <div id="grillaArchivosSubido">
                      
                  </div>
              </div>
           </div> 
          
           <h2> Verificar Requisitos </h2>
           <div id="ContenedorListaCheckeoInformes">
            
           </div>
            <h2> Devolución </h2>
           <div id="contenedorDevolucion">
             <div class="contenedorTipoDevolProcesoSeco">							
                <div class="etiquetas">
                    <label for="tipoDevolProcesoSeco">
                       Tipo de Devolución:
                    </label>
                </div>
                <div class="campos">
                    <select id="tipoDevolProcesoSeco" name="tipoDevolProcesoSeco" >
                        <option value=""></option>
                        <option value="1">PARCIAL</option>
                        <option value="2">TOTAL</option>                       
                    </select>
                        
                </div>
             </div> 
               <div id="contenedorInfoDevolucionProcesoSeco" style="display: none;"> 
                    <div class="contenedorFechaDevolProcesoSeco">							
                       <div class="etiquetas">
                           <label for="FechaDevolProcesoSeco">
                               Fecha de Devolución <span class="campoObligatorio">*</span>:
                           </label>
                       </div>
                       <div class="campos">
                           <input type="text" data-devol="devol" data-role='datepicker' id="fechaDevolProcesoSeco" name="fechaDevolProcesoSeco" data-type="date"  class=""  />
                           <span class="k-invalid-msg" data-for="fechaDevolProcesoSeco"></span>
                       </div>
                    </div>
                     <div class="contenedorObservacionDevolProsesoSeco">							
                       <div class="etiquetas">
                           <label for="observacionDevolProsesoSeco">
                              Observación:
                           </label>
                       </div>
                       <div class="campos">
                           <textarea id="observacionDevolProsesoSeco" name="observacionDevolProsesoSeco"  class="" ></textarea>
                           <span class="k-invalid-msg" data-for="observacionDevolProsesoSeco"></span>
                       </div>
                    </div>
                     <div class="contenedorFechaEntDevolProcesoSeco">							
                       <div class="etiquetas">
                           <label for="fechaEntDevolProcesoSeco">
                               Fecha Entrega de Complementos :
                           </label>
                       </div>
                       <div class="campos">
                           <input type="text" data-role='datepicker' id="fechaEntDevolProcesoSeco" name="fechaEntDevolProcesoSeco" data-type="date"  class=""  />
                           <span class="k-invalid-msg" data-for="fechaEntDevolProcesoSeco"></span>
                       </div>
                    </div>
                   <a id="linkDevolucionPdf" href="#">Devolución PDF</a>
                   <br>
                   <br>
             </div>
           </div>
           <div class="botones">
               <input type="button" id="guardarInfoProcesoSeco" class="Guardar"  value="Guardar" />
           </div>
</div>
<% } %> 