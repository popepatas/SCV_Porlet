<%-- 
    Document   : HistorialDagma
    Created on : 6/03/2014, 11:03:18 PM
    Author     : Nadesico
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
        <div id="contenedorTipoRadicado">
            <div class="etiquetas">
                <label for="tipoRadicado">
                    Tipo Radicado<span class="campoObligatorio">*</span>:
                </label>	
            </div>
            <div class="campos">
                <select id="tipoRadicado"  name="tipoRadicado" class="lstRequerido"  required>
                    <option value=""></option>							  	
                    <option value="1">Radicado de solicitud plan de mejoramiento</option>
                    <option value="2">Radicado respuesta solicitud prorroga</option>	
                    <option value="3">Radicado aprobado plan de mejoramiento</option>	
                    <option value="4">Radicado no aprobado plan de mejoramiento</option>	
                    <option value="5">Radicado solicitud presentación</option>	
                </select>
                <span class="k-invalid-msg" data-for="parametro"></span>
            </div>
        </div>

        <div id="contenedorRadicado">
            <div class="etiquetas">
                <label for="radicado">
                    Radicado <span class="campoObligatorio">*</span>:
                </label>	
            </div>
            <div class="campos">
                <input type="text" id="radicado" name="radicado" value="" class="cmpRequerido" required />
            </div>
        </div>
    
        <div id="contenedorFechaRadicado">
            <div class="etiquetas">
                <label for="fechaRadicado">
                   Fecha Radicado <span class="campoObligatorio">*</span>:
                </label>	
            </div>
            <div class="campos">
                  <input type="text" data-role='datepicker' id="fechaRadicado" name="fechaRadicado" data-type="date"   class="cmpRequerido" required  />
                    <span data-for='fechaRadicado' class='k-invalid-msg'></span>
            </div>
        </div>
        <div id="contenedorObservacionReprogramacion">							
            <div class="etiquetas">
                <label for="observacionDagma">
                    Observaciones<span class="campoObligatorio">*</span>:
                </label>
            </div>
            <div class="campos">
                <textarea id="observacionDagma" name="observacionDagma"  value=""  class="k-textbox" ></textarea>
                <span class="k-invalid-msg" data-for="observacionDagma"></span>
            </div>
        </div>
        <div class="contenedorArchivos">							
            <div class="etiquetas">
                <label for="archivos">
                    Archivos<span class="campoObligatorio">*</span>:
                </label>
            </div>
            <div class="campos">
                <input name="files" id="files" type="file" />
                <span class="k-invalid-msg" data-for="archivos"></span>
            </div>
        </div>
        <input type="hidden" id="codigoHistorial" value=""/>
        <div class="botones">                            
            <input type="button" id="actualizarHistorialDagma" value="Actualizar" />
            <input type="button" id="guardarHistorialDagma" value="Añadir" />
            <input type="button" id="consultarHistorial" value="Consultar Todo" />
            <input type="button" id="limpiarHistorial" value="Limpiar" />
            <input type="button" id="generarExcelHistorial" value="Generar Excel" />
        </div>
        <div id="grillaHistorialDagma"></div>
        <div id="contenedorArchivosSubidos"></div>
</div>
<% } %>
