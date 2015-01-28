<%-- 
    Document   : CrearCliente
    Created on : 18/12/2013, 11:47:35 AM
    Author     : juanhgm
--%>

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
        <!-- Si el usuario no tiene permisos -->
        <% if(validar == false){ %>
        <div id="div_cliente" class="formularios" style="margin:0px!important;padding:0px!important;">
            <p>Lo sentimos, usted no tiene permisos para acceder a esta pagina.</p>
        </div>

        <!-- Si el usuario tiene permisos -->
        <% } else if(validar == true){ %>

         <div id="div_cliente" class="formularios" style="margin:0px!important;padding:0px!important;">
                 <h2>Cliente</h2>
            <div id="formclientes">
              <div class="columnas">
                 <div class="contenedorCodigo">
                         <div class="etiquetas">
                                 <label for="codigo">
                                         NIT <span class="campoObligatorio">*</span>:
                                 </label>
                         </div>
                         <div class="campos">
                             <input type="text" id="nit" name="nit" value="" class="k-textbox cmpRequerido" required  tabindex="1" maxlength="100"/>
                                 <span class="k-invalid-msg" data-for="nit"></span>
                         </div>
                 </div>
               

                 <div class="contenedorCiuu" style="margin-right: 150px;">
                         <div class="etiquetas">
                                 <label for="ciiu">
                                         Actividad Económica:<span class="campoObligatorio">*</span>:
                                 </label>
                         </div>
                        <div class="campos">
                            <select id="ciiu"  name="ciiu" class="lstRequerido "  required  tabindex="3"> 
                                            <option value=""></option>							  	

                            </select>
                       </div>
                        <span class="k-invalid-msg" data-for="ciiu"></span>
                 </div>
                  <div class="contenedoBarrio">
                         <div class="etiquetas">
                                 <label for="barrio">
                                         Barrio 
                                 </label>
                         </div>
                         <div class="campos"> 
                                 <input class=" k-textbox "  type="text" id="barrio" name="barrio" value=""  tabindex="5"  maxlength="100"/>
                                 <span class="k-invalid-msg" data-for="Pozo"></span>
                         </div>
                 </div>


                   <div class="contenedoComuna">
                         <div class="etiquetas">
                                 <label for="comuna">
                                         Comuna: 
                                 </label>
                         </div>
                         <div class="campos"> 
                                 <input class=""  type="text" id="comuna" name="comuna" value=""  tabindex="10"  maxlength="5" />
                                 <span class="k-invalid-msg" data-for="comuna"></span>
                         </div>
                 </div>

                        <div class="contenedotelefonoDos">
                                <div class="etiquetas">
                                        <label for="telefono">
                                                Teléfono: 
                                        </label>
                                </div>
                                <div class="campos"> 
                                        <input  type="text" id="telefono" name="telefono" value=""  tabindex="12"  maxlength="10" />
                                        <span class="k-invalid-msg" data-for="telefono"></span>
                                </div>
                        </div>
                           <div class="contenedoEmail">
                                <div class="etiquetas">
                                        <label for="email">
                                                E-mail: 
                                        </label>
                                </div>
                                <div class="campos"> 
                                        <input class="k-textbox "  type="text" id="email" name="email" value=""  tabindex="14"  maxlength="200"/>
                                        <span class="k-invalid-msg" data-for="email"></span>
                                </div>
                        </div>
                    <div class="contenedoNombreRepreLegal">
                         <div class="etiquetas">
                                 <label for="representante">
                                         Nombre Represente Legal :
                                 </label>
                         </div>
                         <div class="campos"> 
                                 <input class="k-textbox "  type="text" id="representante" name="representante" value=""  tabindex="17" maxlength="480"/>
                                 <span class="k-invalid-msg" data-for="representante"></span>
                         </div>
                      </div> 
                       
                 </div>
                 <div class="columnas">
                 <div class="contenedorDescripcion">
                         <div class="etiquetas">
                                 <label for="razon">
                                         Razón Social<span class="campoObligatorio">*</span>:
                                 </label>
                         </div>
                         <div class="campos">
                             <input class="cmpRequerido k-textbox"  type="text" id="razon" name="razon" value="" required tabindex="2"  maxlength="500"/>
                                 <span class="k-invalid-msg" data-for="razon"></span>
                         </div>
                 </div>               
                 <div class="contenedoUsoServicio">
                         <div class="etiquetas">
                                 <label for="usoServicio">
                                         Uso del Servicio: 
                                 </label>
                         </div>
                         <div class="campos"> 

                             <select id="usoServicio" name="usoServicio" class="" tabindex="4">
                                     <option value=""></option>
                                     <option value="1">COMERCIAL</option>
                                     <option value="2">INDUSTRIAL</option>
                                     <option value="3">OFICIAL</option>
                                     <option value="4">ESPECIAL</option>
                                 </select>
                                 <span class="k-invalid-msg" data-for="usoServicio"></span>
                         </div>
                 </div>
                  <div class="contenedoDireccion">
                         <div class="etiquetas">
                                 <label for="consumo">
                                         Dirección: 
                                 </label>
                         </div>
                         <div class="campos">
                                 <div id="direccion">
                                            <select id="ubicacion" name="completaDireccion" class="cmpDireccion "  tabindex="6" >
                                                <option value=""></option>
                                                <option value="AVN">AVN</option>
                                                <option value="CRA">CRA</option>							  	
                                                <option value="CLL">CLL</option>
                                                <option value="DGN">DGN</option>
                                                <option value="TVL">TVL</option>
                                            </select>						
                                            <input type="text" id="princial" name="completaDireccion" value="" class="cmpDireccion k-textbox"  tabindex="7"  maxlength="100"/> 
                                            <span> # </span> 
                                            <input type="text" id="numCasaPrincipal" name="completaDireccion" value=""  class="cmpDireccion k-textbox"  tabindex="8"  maxlength="100"/>
                                            <span> - </span> 
                                            <input type="text" id="numCasaSecundario"  name="completaDireccion" value="" class="cmpDireccion k-textbox"  tabindex="9"  maxlength="100"/>
                                            <input type="hidden"  id="direccionCompleta" value=""  />
                                            <span class="k-invalid-msg" data-for="completaDireccion"></span>
                                        </div>
                               <!--  <input class=" k-textbox "  type="text" id="direccion" name="direccion" value="" />-->
                                 <span class="k-invalid-msg" data-for="Pozo"></span>
                         </div>
                 </div>
                 <div class="contenedoPagina">
                         <div class="etiquetas">
                                 <label for="pagina">
                                         Página web:
                                 </label>
                         </div>
                         <div class="campos"> 
                             <input class="k-textbox "  type="text" id="pagina" name="pagina" value=""  tabindex="11" maxlength="150"/>
                                 <span class="k-invalid-msg" data-for="pagina"></span>
                         </div>
                 </div>               
                    <div class="contenedoTelefonoDos">
                         <div class="etiquetas">
                                 <label for="telefonoDos">
                                         Teléfono 2: 
                                 </label>
                         </div>
                         <div class="campos"> 
                                 <input  type="text" id="telefonoDos" name="telefonoDos" value=""  tabindex="13" maxlength="10"/>
                                 <span class="k-invalid-msg" data-for="telefonoDos"></span>
                         </div>
                 </div>
                

                  <div class="contenedoEmailSecundario">
                         <div class="etiquetas">
                                 <label for="emailSecundario">
                                         E-mail Secundario:
                                 </label>
                         </div>
                         <div class="campos"> 
                                 <input class="k-textbox "  type="text" id="emailSecundario" name="emailSecundario" value=""  tabindex="15" maxlength="200"/>
                                 <span class="k-invalid-msg" data-for="emailSecundario"></span>
                         </div>
                 </div>
                 
               
                 

                 <input type="hidden" id="codigo" value=""/>
                 
                 </div>
         </div>
            <div id="contenedorBotonesCliente" class="botones">
                   <input type="button" id="guardar" value="Guardar"  />
                   <input type="button" id="modificar" value="Modificar"  />      
                   <input type="button" id="cancelar" value="Cancelar"  /> 
           </div>
         </div>
        <% } %>

