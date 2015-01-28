<%-- 
    Document   : clienteP
    Created on : 18-Dec-2013, 22:21:43
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
<!DOCTYPE html>
	<html>
	<head>
			<meta charset="utf-8">
			<meta http-equiv="X-UA-Compatible" content="IE=edge">
			<title>Cliente</title>
			<link rel="stylesheet" href="../css/normalize.css" />
			<link rel="stylesheet" href="../css/styles.css" />
			<link href="../js/librerias/Kendo/css/kendo.common.min.css" rel="stylesheet">
                        <link href="../js/librerias/Kendo/css/kendo.default.min.css" rel="stylesheet">
			<script type="text/javascript" src="../js/librerias/jquery.min.js"></script>
			<script type="text/javascript" src="../js/librerias/jquery.migrate.min.js"></script>
			<script type="text/javascript" src="../js/librerias/jquery.formalize.min.js"></script>
			<script type="text/javascript" src="../js/funciones/funciones.js"></script>
			<script type="text/javascript" src="../js/dom/manipulacion.js"></script>
			<script type="text/javascript" src="../js/librerias/Kendo/js/kendo.core.min.js"></script>
			<script type="text/javascript" src="../js/librerias/Kendo/js/kendo.validator.min.js"></script>
			<script type="text/javascript" src="../js/librerias/Kendo/js/kendo.userevents.min.js"></script>
			<script type="text/javascript" src="../js/librerias/Kendo/js/kendo.numerictextbox.min.js"></script>

                        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.data.min.js"></script>
                        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.binder.min.js"></script>
                        
                        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.editable.min.js"></script>
                        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.window.min.js"></script>
                        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.filtermenu.min.js"></script>
                        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.columnmenu.min.js"></script>
                        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.groupable.min.js"></script>
                        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.pager.min.js"></script>
                        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.selectable.min.js"></script>
                        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.sortable.min.js"></script>
                        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.reorderable.min.js"></script>
                        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.resizable.min.js"></script>
                        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.grid.min.js"></script>    
                        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.popup.min.js"></script> 
                        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.list.min.js"></script> 
                        <script type="text/javascript" src="../js/librerias/Kendo/js/kendo.dropdownlist.min.js"></script> 

			<script type="text/javascript" src="../js/funciones/cliente.js"></script>
	</head>
	<body>
            <div id="Wrapper">
		<section class="k-content">
                                <!-- Si el usuario no tiene permisos -->
                                <% if(validar == false){ %>
                                <div id="div_cliente" class="formularios">
                                    <p>Lo sentimos, usted no tiene permisos para acceder a esta pagina.</p>
                                </div>

                                <!-- Si el usuario tiene permisos -->
                                <% } else if(validar == true){ %>          
				<div id="div_cliente" class="formularios">
					<div class="titulo">Cliente</div>
                                        <br>
					<div class="contenedorCodigo">
						<div class="etiquetas">
							<label for="codigo">
								NIT <span class="campoObligatorio">*</span>:
							</label>
						</div>
						<div class="campos">
							<input type="text" id="nit" name="nit" value="" class="k-textbox cmpRequerido cmpNit" required />
							<span class="k-invalid-msg" data-for="nit"></span>
						</div>
					</div>
                                        <div class="contenedorDescripcion">
						<div class="etiquetas">
							<label for="razon">
								Razón Social<span class="campoObligatorio">*</span>:
							</label>
						</div>
						<div class="campos">
							<input class="cmpRequerido k-textbox"  type="text" id="razon" name="razon" value="" required/>
							<span class="k-invalid-msg" data-for="razon"></span>
						</div>
					</div>
                                        
                                        <div class="contenedorCiuu">
						<div class="etiquetas">
							<label for="ciiu">
								Actividad Económica:<span class="campoObligatorio">*</span>:
							</label>
						</div>
						<select id="ciiu"  name="ciiu" class="lstRequerido"  required>
							  	<option value=""></option>							  	
							  		
                                                </select>
                                            <span class="k-invalid-msg" data-for="ciiu"></span>
					</div>
                                        
                                        <div class="contenedorCiclo">
						<div class="etiquetas">
							<label for="ciclo">
								Ciclo:
							</label>
						</div>
						<div class="campos"> 
							<input class=""  type="text" id="ciclo" name="ciclo" value="" />
							<span class="k-invalid-msg" data-for="ciclo"></span>
						</div>
					</div>
                                        
                                        <div class="contenedoSector">
						<div class="etiquetas">
							<label for="sector">
								Sector:
							</label>
						</div>
						<div class="campos"> 
							<input class=""  type="text" id="sector" name="sector" value="" />
							<span class="k-invalid-msg" data-for="ciclo"></span>
						</div>
					</div>
                                        
                                         <div class="contenedoPozo">
						<div class="etiquetas">
							<label for="pozo">
								Pozo o aforo vertimiento: 
							</label>
						</div>
						<div class="campos"> 
							<input class=""  type="text" id="pozo" name="pozo" value="" />
							<span class="k-invalid-msg" data-for="Pozo"></span>
						</div>
					</div>
                                        
                                         <div class="contenedoConsumo">
						<div class="etiquetas">
							<label for="consumo">
								Consumo de acueducto: 
							</label>
						</div>
						<div class="campos"> 
							<input class=""  type="text" id="consumo" name="consumo" value="" />
							<span class="k-invalid-msg" data-for="Pozo"></span>
						</div>
					</div>
                                        
                                         <div class="contenedoDireccion">
						<div class="etiquetas">
							<label for="consumo">
								Dirección: 
							</label>
						</div>
						<div class="campos"> 
							<input class=" k-textbox "  type="text" id="direccion" name="direccion" value="" />
							<span class="k-invalid-msg" data-for="Pozo"></span>
						</div>
					</div>
                                        
                                         <div class="contenedoBarrio">
						<div class="etiquetas">
							<label for="barrio">
								Barrio 
							</label>
						</div>
						<div class="campos"> 
							<input class=" k-textbox "  type="text" id="barrio" name="barrio" value="" />
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
							<input class=""  type="text" id="comuna" name="comuna" value="" />
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
							<input  type="text" id="telefono" name="telefono" value="" />
							<span class="k-invalid-msg" data-for="telefono"></span>
						</div>
					</div>
                                        <div class="contenedoTelefonoDos">
						<div class="etiquetas">
							<label for="telefonoDos">
								Teléfono 2: 
							</label>
						</div>
						<div class="campos"> 
							<input  type="text" id="telefonoDos" name="telefonoDos" value="" />
							<span class="k-invalid-msg" data-for="telefonoDos"></span>
						</div>
					</div>
                                        <div class="contenedoUsoServicio">
						<div class="etiquetas">
							<label for="usoServicio">
								Uso del Servicio: 
							</label>
						</div>
						<div class="campos"> 
							
                                                    <select id="usoServicio" name="usoServicio" class="k-textbox">
                                                            <option value=""></option>
                                                            <option value="1">COMERCIAL</option>
                                                            <option value="2">INDUSTRIAL</option>
                                                            <option value="3">OFICIAL</option>
                                                            <option value="4">ESPECIAL</option>
                                                        </select>
							<span class="k-invalid-msg" data-for="usoServicio"></span>
						</div>
					</div>
                                         <div class="contenedoEmail">
						<div class="etiquetas">
							<label for="email">
								E-mail: 
							</label>
						</div>
						<div class="campos"> 
							<input class="k-textbox "  type="text" id="email" name="email" value="" />
							<span class="k-invalid-msg" data-for="email"></span>
						</div>
					</div>
                                          
                                         <div class="contenedoEmailSecundario">
						<div class="etiquetas">
							<label for="emailSecundario">
								E-mail Secundario:
							</label>
						</div>
						<div class="campos"> 
							<input class="k-textbox "  type="text" id="emailSecundario" name="emailSecundario" value="" />
							<span class="k-invalid-msg" data-for="emailSecundario"></span>
						</div>
					</div>
                                         <div class="contenedoPagina">
						<div class="etiquetas">
							<label for="pagina">
								Página web:
							</label>
						</div>
						<div class="campos"> 
							<input class="k-textbox "  type="text" id="pagina" name="pagina" value="" />
							<span class="k-invalid-msg" data-for="pagina"></span>
						</div>
					</div>
                                           <div class="contenedoNombreRepreLegal">
						<div class="etiquetas">
							<label for="representante">
								Nombre Represente Legal :
							</label>
						</div>
						<div class="campos"> 
							<input class="k-textbox "  type="text" id="representante" name="representante" value="" />
							<span class="k-invalid-msg" data-for="representante"></span>
						</div>
					</div> 
                                        
					<input type="hidden" id="codigo" value=""/>
                                        <br>
					<div class="botones">
						<input type="button" id="guardar" value="Guardar"  />
						<input type="button" id="modificar" value="Modificar"  />
						<input type="button" id="consultar" value="Consultar" />
					</div>
				</div>
                                <% } %>
		</section>
                <br>
		<section class="contenedor-grilla">
			<div id="grillaCliente" class="grilla-small">
				
			</div>
		</section>
</div>
	</body>
</html>


