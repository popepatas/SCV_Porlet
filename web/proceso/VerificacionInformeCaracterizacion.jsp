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

<div id="ContenedorListaCheckeoInformes">
    <div class="contenedorPreguntasNivel1">
        
    </div>
    
</div>
<h2> Devolución </h2>
<div id="contenedorDevolucionCaracterizacion">
  <div class="contenedorTipoDevolCaracterizacion">							
     <div class="etiquetas">
         <label for="tipoDevolCaracterizacon">
            Tipo de Devolución:
         </label>
     </div>
     <div class="campos">
         <select id="tipoDevolCaracterizacion" name="tipoDevolCaracterizacion" >
             <option value=""></option>
             <option value="1">PARCIAL</option>
             <option value="2">TOTAL</option>                       
         </select>

     </div>
  </div> 
    <div id="contenedorInfoDevolucionCaracterizacion" style="display: none;"> 
         <div class="contenedorFechaDevolCaracterizacion">							
            <div class="etiquetas">
                <label for="FechaDevolCaracterizacion">
                    Fecha de Devolución <span class="campoObligatorio">*</span>:
                </label>
            </div>
            <div class="campos">
                <input type="text" data-devol="devol" data-role='datepicker' id="fechaDevolCaracterizacion" name="fechaDevolCaracterizacion" data-type="date"  class=""  />
                <span class="k-invalid-msg" data-for="fechaDevolCaracterizacion"></span>
            </div>
         </div>
          <div class="contenedorObservacionDevolCaracterizacion">							
            <div class="etiquetas">
                <label for="observacionDevolCaracterizacion">
                   Observación:
                </label>
            </div>
            <div class="campos">
                <textarea id="observacionDevolCaracterizacion" name="observacionDevolCaracterizacion"  class="" ></textarea>
                <span class="k-invalid-msg" data-for="observacionDevolCaracterizacion"></span>
            </div>
         </div>
          <div class="contenedorFechaEntDevolCaracterizacion">							
            <div class="etiquetas">
                <label for="fechaEntDevolCaracterizacion">
                    Fecha Entrega de Complementos :
                </label>
            </div>
            <div class="campos">
                <input type="text" data-role='datepicker' id="fechaEntDevolCaracterizacion" name="fechaEntDevolCaracterizacion" data-type="date"  class=""  />
                <span class="k-invalid-msg" data-for="fechaEntDevolCaracterizacion"></span>
            </div>
         </div>
        <a id="pdfDevolucionCaract" name="pdfDevolucionCaract" href="#">Devolución PDF</a>
        <br>
        <br>
  </div>
</div>
<input type="button" id="guardarVerfCaracterizacion" value="Guardar">
<% } %>