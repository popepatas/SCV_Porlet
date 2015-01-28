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
    <div id="contenedorInfoTecnica">
        <p>Lo sentimos, usted no tiene permisos para acceder a esta pagina.</p>
    </div>

    <!-- Si el usuario tiene permisos -->
    <% } else if(validar == true){ %>
    
<script id="template-Puntos-InfoCaracterizacion" type="text/x-handlebars-template">
  {{#each puntos}}  
  <div class="contenedorPunto">
        
    <div class="contendorinformacionBasica">
      <input type="hidden" name="codigoEvalPuntos" value="{{codigo}}" />
      <div class="pal-line">
        <div class="InfoBasica InfoPal Titulo">          
          <div name="infoActEconomica" class="campoInfo">{{descripcionCiiu}}</div>
          <input type="hidden" name="ciiuEvalPuntos" value="{{ciiu}}">
        </div>
      </div>
      <div class="pal-line">
        <div class="InfoBasica InfoPal">
          <div class="etiqueta">Ubicación</div>
          <div name="infoUbicacion" class="campoInfo">{{ubicacion}}</div>
        </div>
        <div class="InfoBasica InfoPal">
          <div class="etiqueta">Latitud</div>
          <div name="infoLatitud" class="campoInfo">{{latitud}}</div>
        </div>
        <div class="InfoBasica InfoPal">
          <div class="etiqueta">Longitud</div>
          <div name="infoLongitud" class="campoInfo">{{longitud}}</div>
       </div> 
      </div>
      <div class="pal-line">
        <div class="InfoBasica"  style="display:none">
          <div class="etiqueta">Caudal Promedio (l/s)</div>
          <div class="campos">
            <input  name="caudal" type="text" value="{{caudalPromedio}}" class="campoDecimal  k-textbox" />
          </div>
        </div> 
        <div class="InfoBasica">
          <div class="etiqueta">Fecha Monitoreo:</div>
          <div class="campos">
            <input  name="fechaMonitoreo" type="text" value="{{fechaMonitoreo}}" class="fechasMonPunto" />
          </div>
        </div> 
          <div class="InfoBasica">
            <div class="etiqueta">Días al Mes</div>
            <div class="campos">
              <input name="jordanadaProductivaDia" type="text" value="{{jordanadaProductivaDia}}" class="jordanadaProductivaDia campoEntero k-textbox" />
            </div>
          </div>              
          <div class="InfoBasica">
            <div class="etiqueta">Horas al día</div>
            <div  class="campos">
            <input name="jordanadaProductivaHoras" type="text" value="{{jordanadaProductivaHoras}}" class="jordanadaProductivaHoras campoEntero k-textbox" />
            </div>
          </div>
      </div>
      <div  class="pal-line" > 
        <div class="InfoBasica">
          <div class="etiqueta">Observaciones</div>
          <div  class="campos">
            <textarea name="jordanadaProductivaObsev" class="k-textbox jordanadaProductivaObsev">{{jordanadaProductivaObsev}}</textarea>
          </div>
        </div>
      </div>
    </div>
	<div class="contendorJornadas" >
     {{#each jornadas}}  
      <div id="jornada{{nombre}}" class="Jornadas">
        
          <div class="contenedorInfoJornada"> 
            <div class="titulo">{{nombre}}</div>
              <div class="InfoBasica infoJornada">
                  <div class="etiqueta">Caudal Promedio Jornada (l/s):</div>
                  <div  class="campos">
                     <input  type="text" name="caudalJornada" class="caudalJornada  k-textbox campoDecimal" value="{{caudalJornada}}"  />
                  </div>
                </div>
                <div class="InfoBasica infoJornada">
                  <div class="etiqueta">Carga DBO</div>
                  <div  class="campos">
                     <input id="{{nombre}}CargaDBO" type="text" name="cargaDBO" class="cargaDBO  k-textbox cv-disabled" value="{{cargaDBO}}"  disabled="true"/>
                  </div>
                </div>
                <div class="InfoBasica infoJornada">
                  <div class="etiqueta">Carga SST</div>
                  <div  class="campos">
                    <input id="{{nombre}}CargaSST" type="text" name="CargaSST" class="cargaSST k-textbox cv-disabled" value="{{cargaSST}}" disabled="true"/>
                  </div>
                </div> 
                <div class="InfoBasica infoJornada">
                  <div class="etiqueta">Hora de inicio</div>
                  <div class="campos">
                    <input type="text" name="horaInicio" class="tiempoInfoTecnicas" value="{{horaInicio}}" />
                  </div>
                </div>                
                 <div class="InfoBasica infoJornada">
                  <div class="etiqueta">Hora Final</div>
                  <div class="campos">
                    <input type="text" name="horaFin" class="tiempoInfoTecnicas" value="{{horaFin}}" />
                  </div>
                </div> 
            </div>  
                          
          
          <div class="tablaParamJordanda">
            <table>
              <thead>
                <th>Parametro</th>
                <th>Rango</th>
                <th>Menor</th>
                <th>valorInforme</th>
                <th>Observación</th>
                <th>cumple</th>
              </thead>
              <tbody>
               {{#each tabla}} 
                <tr>
                  <td>
                    {{parametro}}
                    <input type="hidden" name="parametro" value="{{parametro}}" />
                    <input type="hidden" name="codigoParametro" value="{{codigoParametro}}" />
                  </td>
                  <td class="rangosParametros"> 
                    {{rangoInicial}} - {{rangoFinal}}
                    <input type="hidden" name="rangoIncialParam" class="rangoIncialParam k-textbox" value="{{rangoInicial}}" />
                    <input type="hidden" name="rangoFinalParam" class="rangoFinalParam k-textbox" value="{{rangoFinal}}" />
                  </td>
                  <td class="indicadorMayorMenor">
                        <select name="indicardorMenor" class="indicardorMenor" >                         
                                {{{selectedList indicardorMenor}}}
                        </select>
                  </td>
                  <td class="valoresParmaInfo">
                    <input type="text" name="valorInforme" class="valorParamPunto {{parametro}} campoDecimal k-textbox" data-jornada="{{nombre}}" value="{{valorInforme}}" />
                  </td>
                  <td class="observacionParmaInfo">
                    <input type="text" name="observacion" class="observParamPunto k-textbox" value="{{observacion}}" />
                  </td>
                  <td class="valoresParmaInfo">
                     <input type="text" name="cumple" class="cumple k-textbox cv-disabled" value="{{cumple}}"  disabled="true"/>
                  </td>
                </tr>
              {{/each}} 
                
              </tbody>
            </table>
          </div>
        </div>        
    {{/each}}   
	</div>
  </div>
 {{/each}}  
</script>

<div id="contenedorInfoTecnica">        
        <div id="contenedorInformacionGeneral">
           <input  type="hidden" id="codigoInfoTecnico" />
            <div id="contenedorCargarDocumentos">
              <div id="contenedorEnlaceCargaInfoTec">
                  <a id="cargarArchivosInfoTec" href="#" class="cv-cargarArchivos" >Cargar Archivos</a>
                  <a id="verArchInfoTec" href="#" class="cv-verArchivos">Ver Archivos Cargados</a>
              </div>
            </div>
           </div>
    <br></br>
           <div id="contenedorCargarDocumentos">
               <div class="etiquetas">
                   <label for="cargarDocumentos">
                           Jornada Productiva General<span class="campoObligatorio">*</span>:
                   </label>
               </div>
               <div class="campos">
                   <div class="campoInterno"> 
                       Días al mes : <input type="text" id="diasAlMes" value=""  class="k-textbox" />  
                   </div>
                   <div class="campoInterno"> 
                       Horas al días: <input type="text" id="horasAlDia" value=""  class="k-textbox" />          
                   </div>
               </div>            
           </div>         
           <div id="contenedorPreTratamiento">
               <div class="etiquetas">
                   <label for="preTratamiento">
                           ¿Tiene Sistema de Pre tratamiento?<span class="campoObligatorio">*</span>:
                   </label>
               </div>
               <div class="campos">

                       <select id="preTratamiento" value=""  class="" > 
                           <option value=""></option>
                           <option value="SI">SI</option>
                           <option value="NO">NO</option>
                       </select>   
               </div>
           </div>
          <div id="contenedorCualPreTratamiento" class="campoOculto">
               <div class="etiquetas">
                   <label for="cualPreTratamiento">
                           ¿Cual?
                   </label>
               </div>
               <div class="campos">
                     <input type="text" id="cualPreTratamiento" value=""  class="k-textbox" />                  
               </div>
           </div>
                <div id="contenedorGeneracionLodos">
               <div class="etiquetas">
                   <label for="generacionLodos">
                          ¿Existe generación de lodos de la actividad productiva?<span class="campoObligatorio">*</span>:
                   </label>
               </div>
               <div class="campos">
                   <select id="generacionLodos" value=""  class="" > 
                       <option value=""></option>
                       <option value="SI">SI</option>
                       <option value="NO">NO</option>
                   </select>   
               </div>
           </div>
           <div id="contenedorCualGeneracionLodos" class="campoOculto">
               <div class="etiquetas">
                   <label for="cualGeneracionLodos">
                           ¿Cual?
                   </label>
                   
               </div>
               <div class="campos">
                    <select id="cualGeneracionLodos" value=""  class="" > 
                       <option value=""></option>
                       <option value="1">GRASAS</option>
                       <option value="2">SUSTANCIAS PELIGROSAS</option>
                       <option value="3">OTROS</option>
                   </select>                  
               </div>
               <div id="contenedorCualGeneracionLodosOtros" class="campoOculto">
                    <div class="etiquetas">
                        <label for="cualGeneracionLodosOtros">
                                ¿Cual?
                        </label>
                    </div>
                    <div class="campos">
                          <input type="text" id="cualGeneracionLodosOtros" value=""  class="k-textbox" />                  
                    </div>
              </div>
               <a id="linkLodos" href="#">Manejar lodos</a>
           </div>
        </div>
        
        <div id="contenedorRegistroInformePuntos">
            <h2>Puntos de vertimientos</h2>
            <div id="contenedorEvalPuntosVertimiento">

            </div>
       </div>
        <div id="contenedorCargaArchivosInfoTec" style="display: none">							
              <div class="etiquetas">
                  <label for="archivosInfoTec">
                     Seleccione sus Archivos
                  </label>
              </div>
              <div class="campos">
                  <input name="filesInfoTec" id="filesInfoTec" type="file" />

              </div>
          </div>
        <div id="contenedorArchivosSubidosInfoTec"  style="display: none;"> 
            <div id="grillaArchivosSubidoInfoTec">

            </div>
        </div>
    <div class="botones">
        <input  type="button" id="guardarInfoTecCaract" value="Guardar" />
        <input  type="button" id="pdfIncumplimiento" value="PDF Incumplimiento" style="display:none;" />
    </div>
        
</div>
<% } %>