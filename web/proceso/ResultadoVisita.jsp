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
        <div id="div_registrar_resultado_visita" class="formularios">
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


            <div class="contenedorResultado">							
                <div class="etiquetas">
                    <label for="resultado">
                        Resultado<span class="campoObligatorio">*</span>:
                    </label>
                </div>
                <div class="campos">
                    <textarea id="resultado" name="resultado"  value=""  class="k-textbox" required ></textarea>
                    <span class="k-invalid-msg" data-for="resultado"></span>
                </div>
            </div>
            
            
            <div class="contenedorArchivos">							
                <div class="etiquetas">
                    <label for="archivos">
                        Archivos:
                    </label>
                </div>
                <div class="campos">
                    <input name="files" id="files" type="file" />
                    <span class="k-invalid-msg" data-for="archivos"></span>
                </div>
            </div>
            <div class="contenedorReprogramar">							
                <div class="etiquetas">
                    <label for="chkResultado">
                       ¿Reprogramar? 
                    </label>
                </div>
                <div class="campos">
                    <input type="checkbox" id="chkResultado" name="chkResultado"  value=""  class="" />
                    <span class="k-invalid-msg" data-for="chkResultado"></span>
                </div>
            </div>

        <input type="hidden" id="codigoResultado" value=""/>
        <div class="botones">
            <input type="button" id="guadarResultado" name="guadarResultados" value="Registrar" />                                
        </div>
</div>
<% } %>

