<html>
    <head>
        <title>SCV | Login</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <link rel="stylesheet" type="text/css" href="css/styles.css" />
        <script type="text/javascript" src="js/librerias/jquery.min.js"></script>
        <script type="text/javascript" src="js/librerias/jquery.migrate.min.js"></script>
        <script type="text/javascript" src="js/funciones/funciones.js"></script>
        <script type="text/javascript" src="js/funciones/login.js"></script>
    </head>
<body>
   <div class="contenedor-form">
     <img src="images/header.png" width="900" >
     
     <div class="testbox">
       <h1>Ingresar</h1>

         <form method="POST" action="Login"> 
            <label id="icon" for="name"><img src="images/user.png" width="15" ></label>
            <input type="text" name="login" id="login" placeholder="Nombre" required/>
            <label id="icon" for="name"><img src="images/pass.png" width="15" ></label>
            <input type="password" name="password" id="password" placeholder="Contraseña" required/>             
            <input id="enviar" type="submit" class="button-form" value="Entrar" />
         </form>
       <div class="invalid">Nombre o contraseña incorrecta</div>
     </div>
   </div>
</body>
</html>
</html>