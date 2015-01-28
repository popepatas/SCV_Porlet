<%-- 
    Document   : index
    Created on : 16/12/2013, 08:52:23 AM
    Author     : Galatea
--%>

<%@page import="modelo.AutenticacionManager.Autenticacion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Iniciamos la sesion del usuario  -->
<% 
  
    HttpSession sesion = request.getSession(true);
    Object logeado = sesion.getAttribute("login");
    
    if( logeado == null ){    
        response.sendRedirect("/SCV_Portlet/Login.jsp");
        
    }
     
%>

<!DOCTYPE html>
<html>
    <head>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/styles.css" />
        <link rel="stylesheet" type="text/css" href="css/formalize.css">
        <link href='http://fonts.googleapis.com/css?family=Maven+Pro:400,500,700,900' rel='stylesheet' type='text/css'>
        <link href='http://fonts.googleapis.com/css?family=PT+Sans' rel='stylesheet' type='text/css'>                                        
        <script type="text/javascript" src="js/librerias/jquery.min.js"></script>
        <script type="text/javascript" src="js/librerias/jquery.migrate.min.js"></script>
        <script type="text/javascript" src="js/funciones/funciones.js"></script>
        <script type="text/javascript" src="js/dom/manipulacion.js"></script>

        <title>Sistema de Control de Vertimentos</title>
    </head>
    <body>
        <section id="Wrapper">
         <%@include file="/templates/header.html" %>
            <section id="ContenedorContent">
                <section id="Content">
                   
                    
                    <%@include file="templates/main_nav.html" %>
                    <div id="ContentLeft"></div>
                    
                    <div class="contenidoPrincipal home" >
                        <p class="tituloPrincipal">Sistema de Control de Vertimentos</p>
                        <br>
                        <br>
                       
                        <p class="parrafoPrincipal">
                            Sistema de seguimiento y trazabilidad al proceso de recolección y evaluación de los 
                            informes de control de vertimientos (caracterización, proceso seco) que son enviados 
                            por los establecimientos industriales, comerciales, especiales y oficiales. 
                        </p>
                    </div>
                </section>
            </section>
        </section>
    </body>
</html>
