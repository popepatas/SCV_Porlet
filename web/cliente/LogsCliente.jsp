<%-- 
    Document   : LogsCliente
    Created on : 07-Jan-2014, 12:09:32
    Author     : illustrato
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <% if(validar == false){ %>
        <div class="contenedor-grilla">
            <p>Lo sentimos, usted no tiene permisos para acceder a esta pagina.</p>
        </div>

        <!-- Si el usuario tiene permisos -->
        <% } else if(validar == true){ %>          
       <div class="contenedor-grilla">
            <div id="grillaLogsCliente" class="grilla-medium">

            </div>
       </div>
      <% } %>
   