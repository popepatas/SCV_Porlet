<%@page import="modelo.ApiManager"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        
        <link rel="stylesheet" type="text/css" href="../css/formalize.css">
        <link href='http://fonts.googleapis.com/css?family=PT+Sans' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" href="../css/normalize.css" />
        <link rel="stylesheet" href="../css/styles.css" />
        <link href="../js/librerias/masterField/css/jquery.masterField.css" rel="stylesheet" />
        <link href="../js/librerias/Kendo/css/kendo.common.min.css" rel="stylesheet" />
        <link href="../js/librerias/Kendo/css/kendo.default.min.css" rel="stylesheet" />
        
        <script type="text/javascript" src="../js/librerias/jquery.min.js"></script>
        <script type="text/javascript" src="../js/librerias/jquery.migrate.min.js"></script>
        <script type="text/javascript" src="../js/librerias/jquery.formalize.min.js"></script>
        <script type="text/javascript" src="../js/funciones/funciones.js"></script>
        <script type="text/javascript" src="../js/dom/manipulacion.js"></script>
        <script type="text/javascript" src="../js/librerias/masterField/js/jquery.masterField.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.core.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.fx.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.validator.min.js"></script>	
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.userevents.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.numerictextbox.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.data.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.calendar.min.js"></script>
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.popup.min.js"></script>			
        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.datepicker.min.js"></script>        
        <script type="text/javascript" src="../js/funciones/proceso/inicio/crearProceso.js"></script>
        <script type="text/javascript" src="../js/funciones/proceso/inicio/crearProcesoGeneral.js"></script>
        
        
        <title>Crear Proceso</title>
    </head>
    <body>
         <div class="overlay" style="display:none"> 
            <img  class="loader" src="../css/loader.gif" />
        </div>
        <section id="Wrapper">
           
              <%@include file="/templates/header.html" %>
        
            <section id="ContenedorContent">
                <section id="Content">
                 
                    
                    <%@include file="/templates/main_nav.html" %>
                    <%@include file="/templates/left_nav_proceso.html" %>
                    
                    
                    <%@include file="/proceso/CrearProceso.jsp" %>
                  
                </section>
            </section>
                                          <%@include file="../templates/footer.html" %>
        </section>
    </body>
</html>

