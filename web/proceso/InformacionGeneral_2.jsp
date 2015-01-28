
   <%@page import="modelo.ApiManager"%>
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
    <div id="div_contenedorInformaciónGeneral">
        <p>Lo sentimos, usted no tiene permisos para acceder a esta pagina.</p>
    </div>

    <!-- Si el usuario tiene permisos -->
    <% } else if(validar == true){ %>
<input type="hidden" id="estadoProceso" value=""/> 
<div id="div_contenedorInformaciónGeneral">
          <div id="contenedorInfoProceso">
                <div class="columnas"> 
                    <div id="contenedortipoInforme">
                   
                       <div class="etiquetas">
                         Tipo de informe
                     </div>
                     <div class="campos">
                         <select id="tipoInforme" name="tipoInforme" class="">                        
                         </select>
                     </div>
                 </div>
                 <div id="contenedorInformo">
                     <div class="etiquetas">
                         ¿Informó monitoreo?
                     </div>
                     <div class="campos">
                         <input type="checkbox" id="informo" value="" class="k-checkbox"/>
                     </div>
                 </div>
             
             <div id="ContendorAsesoria">
                 <input type="hidden" id="codigoAsesoria" name="codigoAsesoria" value="" />
                   <div id="contenedorTipoContacto">
                       <div class="etiquetas">
                           Tipo de Contacto:
                       </div>
                       <div class="campos">
                           <select id="tipoContacto" name="tipoContacto" class="">                        
                           </select>

                       </div>
                   </div>
                    <div id="contenedorContacto">
                        <div class="etiquetas">
                           Persona que Contactó:
                        </div>
                        <div class="campos">
                            <input type="text" id="contacto" name="contacto" class="k-textbox">                                                    
                        </div>
                    </div>
             </div>
                </div>
              <div class="columnas">
                
                
                    <div id="contenedorAsunto">
                        <div class="etiquetas">
                           Asunto:
                        </div>
                        <div class="campos">
                            
                            <textarea id="asunto" name="asunto" class="k-textbox textarea" ></textarea>

                        </div>
                     </div>
                     <div id="contenedorFechaAsesoria">
                        <div class="etiquetas">
                           Fecha de Asesoría:
                        </div>
                        <div class="campos">
                            
                            <input type="text" id="fechaAsesoria" name="fechaAsesoria" >     

                        </div>
                     </div>                     
                     <div id="contenedorRequiereVisita">
                        <div class="etiquetas">
                            ¿Requiere de una vista?:
                        </div>
                        <div class="campos">
                            <input type="checkbox" id="requiereVisita" name="requiereVisita" value="" class="k-checkbox"/>
                        </div>
                     </div>
                 <div id="contenedorEnlaceProgramarVisita" style="display:none">
                        <div class="etiquetas">
                            <a id="progVisita" href="#" >Programar Visita </a>
                        </div>                        
                     </div>
                </div> 
          </div>
         <input type="hidden" id="tipoVisita" name="tipoVisita" value="" />
       
</div>
<div id="contenedorInfoCliente"><br><br>
          <%@include file="../cliente/CrearCliente.jsp" %>
</div>
         <div class="botones">    
             <input type="button" id="guardar" value="Guardar" />
            <!-- <input type="button" id="modificar" value="Modificar" />-->
            <!-- <input type="button" id="consultar" value="Consultar" />-->
 </div>   
 <% } %>  