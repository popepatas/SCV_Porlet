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
<div class="contenedorInfoTasa">
    <div class="columnas">
        <div id="contenedorCargaTotalDBO">
        <div class="etiqueta">Carga Total DBO</div>
        <div  class="campo">
                 <input id="CargaTotaDBO" type="text" name="" class="cargaDBO k-textbox cv-disabled" value="" disabled=true />
        </div>
        </div>
    
    <div id="contenedorTarifaDBO">
        <div class="etiqueta">Tarifa DBO</div>
        <div  class="campo">
                <input id="tarifaDbo" type="text" name="" class="k-textbox cv-disabled" value="" disabled=true />
        </div>
    </div>
     
      
    
     <div class="contenedorPorcentajeRemocionDBO">
        <div class="etiqueta">Porcentaje de remoción DBO (PTAR)</div>
        <div  class="campo">
                <input id="porcentajeRemocionDBO" type="text" name="" class="k-textbox campoDecimal" value="" />
        </div>
    </div>  
  <div class="contenedorValorTasaDBO">
        <div class="etiqueta">Valor tasa retributiva por DBO</div>
        <div  class="campo">
            <input id="valorTasaDBO" type="text" name=""  value="" class="k-textbox cv-disabled" disabled=true  />
        </div>
    </div>
         <div class="contenedorValorTasaCobrada">
        <div class="etiqueta">  Tasa Retributiva Cobrada </div>
        <div  class="campo">
            <input id="valorTasaCobrada" type="text" name=""  class="k-textbox campoEntero" value="" />
        </div>
    </div>
 </div>
  <div class="columnas">
     <div id="contenedorCargaTotalSST">
        <div class="etiqueta">Carga Total SST</div>
        <div  class="campo">
                <input id="CargaTotalSST" type="text" name="" class="cargaSST k-textbox cv-disabled" value="" disabled=true />
        </div>
     </div>
        <div id="contenedorTarifaSST">
        <div class="etiqueta">Tarifa SST</div>
            <div  class="campo">
                <input id="tarifaSst" type="text" name="" class="k-textbox cv-disabled"value="" disabled=true />
           </div>
        </div>
        
     <div id="contenedorPorcentajeRemocionSST">
        <div class="etiqueta">Porcentaje de remoción SST (PTAR) </div>
        <div  class="campo">
                <input id="porcentajeRemocionSST" type="text" name="" class="k-textbox campoDecimal" value="" />
        </div>
     </div>
      
     <div id="contenedorValorTasaSST">
        <div class="etiqueta">Valor Tasa retributiva por SST </div>
        <div  class="campo">
            <input id="valorTasaSST" type="text" name=""  class="k-textbox cv-disabled" value="" disabled=true  />
        </div>
    </div>
        
     
     <div class="contendorValorTasaTotalAnio">
        <div class="etiqueta">Tasa Retributiva Total</div>
        <div  class="campo">
            <input id="valorTasaTotalAnio" type="text" name=""  class="k-textbox cv-disabled" value=""  disabled=true/>
        </div>
    </div>
     
  </div> 
    <div class="contendorValorTotalPagar">
        <div class="etiqueta">Tasa Retributiva  al año</div>
        <div  class="campo">
            <input id="valorTotalPagar" type="text" name=""  class="k-textbox cv-disabled" value=""  disabled=true/>
        </div>
    </div>   
 </div> 
 <div class="botones">
        <input type="button" id="guardarTasaRetributiva" class="guardar" value="Guardar">        
 </div>
<% } %>