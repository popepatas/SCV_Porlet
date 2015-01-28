%<-- 
    Document   : excelDagma
    Created on : 20/03/2014, 04:12:39 PM
    Author     : Nadesico
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page import="modelo.DbManager"%>
<%@page import="java.util.Calendar"%>
<%@page import="modelo.ApiManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reporte Dagma</title>
    </head>
    <body>
        <!-- Codigo para convertir el html a excel -->
        <%
        //Obtenemos los parametros iniciales y le damos formato al excel
        String exportToExcel = request.getParameter("xls");
        String fechaInicial = request.getParameter("fi");
        String fechaFinal = request.getParameter("ff");
        response.setCharacterEncoding("UTF-8");
        if (exportToExcel != null && exportToExcel.toString().equalsIgnoreCase("SI")) {
            response.setContentType("application/vnd.ms-excel");            
            response.setHeader("Content-Disposition", "inline; filename="
                    + "reporteDagma.xls");
 
        }
        
            //Obtenemos los procesos
            String queryProceso = "SELECT PV.CODIGO,PV.FK_CONTRATO\n" +
                                    "FROM TB_PROCESOS_VERTIMIENTOS PV\n" +
                                    "JOIN TB_MONITOREOS MON ON MON.FK_PROCESO_VERTIMIENTO = PV.CODIGO\n" +
                                    "JOIN TB_PUNTOS_MONITOREOS PMON ON PMON.FK_MONITOREO = MON.CODIGO\n" +
                                    "JOIN TB_DETALLES_JORNADAS DET ON DET.FK_PUNTO_MONITOREO = PMON.CODIGO\n" +
                                    "WHERE FECHA_PROCESO BETWEEN to_date('"+fechaInicial+"','dd/mm/yyyy') " +
                                    " AND to_date('"+fechaFinal+"','dd/mm/yyyy')\n" +
                                    " AND MON.FK_ESTADO = 1 " +
                                    "GROUP BY PV.CODIGO,PV.FK_CONTRATO ORDER BY PV.CODIGO ASC";

        
        DbManager db = new DbManager();
        Connection conn = db.conectar();
        ResultSet rsetProceso = db.ejecutar(conn, queryProceso);
        
        //Armamos la fecha actual
        int año = Calendar.getInstance().get(Calendar.YEAR);
        int mes = Calendar.getInstance().get(Calendar.MONTH);
        int dia = Calendar.getInstance().get(Calendar.DATE);
        String fecha = dia + "/" + (mes+1) + "/" + año;
        
        
    %>
   
        <table width="30%" border="1" bordercolor="#999999">
          <tr>
            <th bgcolor="#EFEFEF" scope="col" width="50"><strong>1. AÑO REPORTE</strong></th>
            <td bgcolor="#FFFFFF" scope="col" width="50"><%= año %></td>
            <th bgcolor="#EFEFEF" scope="col" width="50"><strong>2. FECHA REPORTE</strong></th>
            <td bgcolor="#FFFFFF" scope="col" width="50"><%= fecha %></td>
          </tr>
        </table><br></br>
        <table width="100%" height="139" border="1" bordercolor="#999999">
          <tr>
            <th width="10%" bgcolor="#EFEFEF" scope="col"><strong>3. DEPARTAMENTO</strong></th>
            <td width="4%" scope="col">VALLE</td>
            <th width="15%" rowspan="2" bgcolor="#EFEFEF" scope="col">7. PERSONA PRESTADORA DEL SERVICIO PÚBLICO DOMICILIARIO DE ALCANTARILLADO</th>
            <td width="22%" scope="col" rowspan="2" align="center">EMPRESAS MUNICIPALES DE CALI - EMCALI EICE ESP</td>
            <th width="6%" bgcolor="#EFEFEF" scope="col"><strong>8. NIT</strong></th>
            <th colspan="6" bgcolor="#EFEFEF" scope="col">10. PERMISO DE VERTIMIENTO - PLAN DE SANEAMIENTO Y MANEJO DE VERTIMIENTOS</th>
          </tr>
          <tr>
            <td bgcolor="#EFEFEF"><strong>4. MUNICIPIO</strong></td>
            <td>CALI</td>
            <td>890,399,003-4</td>
            <td bgcolor="#EFEFEF"><strong>11. RESOLUCIÓN</strong></td>
            <td width="5%">4133.0.21.234</td>
            <th width="9%" bgcolor="#EFEFEF"><strong>14. No. de DESCARGAS DEL SISTEMA DE ALCANTARILLADO</strong></th>
            <th colspan="4" bgcolor="#EFEFEF"><strong>15. CUERPOS RECEPTORES</strong></th>
          </tr>
          <tr>
            <td bgcolor="#EFEFEF"><strong>5. VEREDA</strong></td>
            <td>NA</td>
            <td rowspan="2" bgcolor="#EFEFEF" align="center"><strong>9. AUTORIDAD AMBIENTAL COMPETENTE</strong></td>
            <td colspan="2" rowspan="2" align="center">DEPARTAMENTO ADMINISTRATIVO DE GESTION DE MEDIO AMBIENTE - DAGMA</td>
            <td bgcolor="#EFEFEF"><strong>12. FECHA</strong></td>
            <td>15- Julio -2010</td>
            <td rowspan="2"></td>
            <td width="20">1</td>
            <td width="8%"></td>
          </tr>
          <tr>
            <td bgcolor="#EFEFEF"><strong>6. CORREGIMIENTO</strong></td>
            <td>NA</td>
            <td bgcolor="#EFEFEF"><strong>13. VIGENCIA</strong></td>
            <td>Periodo 2007-2016</td>
            <td width="20">2</td>
            <td colspan="3">&nbsp;</td>
          </tr>

        </table>
        <p>&nbsp;</p>
        <% 
        while(rsetProceso.next()){ 
        %>
        <table width="100%" height="258" border="1" bordercolor="#CCCCCC" bgcolor="#F7F7F7" >
          <tr>
            <th height="23" scope="col"><strong>16</strong></th>
            <th scope="col"><strong>17</strong></th>
            <th scope="col"><strong>18</strong></th>
            <th scope="col"><strong>19</strong></th>
            <th scope="col"><strong>20</strong></th>
            <th scope="col"><strong>21</strong></th>
            <th scope="col"><strong>22</strong></th>
            <th scope="col"><strong>23</strong></th>
            <th scope="col"><strong>24</strong></th>
            <th scope="col"><strong>25</strong></th>
            <th scope="col"><strong>26</strong></th>
            <th scope="col"><strong>27</strong></th>
            <th scope="col"><strong>28</strong></th>
            <th scope="col"><strong>29</strong></th>
            <th scope="col"><strong>30</strong></th>
            <th scope="col"><strong>31</strong></th>
          </tr>
          <tr>
            <td rowspan="2" style="vertical-align:middle;text-align:center;" width="50"><strong>No.</strong></td>
            <td rowspan="2" style="vertical-align:middle;text-align:center;" width="100"><strong>NOMBRE USUARIO O SUSCRIPTOR</strong></td>
            <td rowspan="2" style="vertical-align:middle;text-align:center;" width="100"><strong>TIPO DE USUARIO</strong></td>
            <td rowspan="2" style="vertical-align:middle;text-align:center;" width="100"><strong>CODIGO DE CUENTA / CONTRATO</strong></td>
            <td rowspan="2" style="vertical-align:middle;text-align:center;" width="100"><strong>TIPO DE ESTRUCTURA DE DESCARGA AL SISTEMA DE ALCANTARILLADO DE LOS VERTIMIENTOS ASOCIADO AL VERTIMIENTO DEL USUARIO Y/O SUSCRIPTOR</strong></td>
            <td rowspan="2" style="vertical-align:middle;text-align:center;" width="100"><strong>DESCRIPCIÓN ACTIVIDAD PRODUCTIVA</strong></td>
            <td rowspan="2" style="vertical-align:middle;text-align:center;" width="50"><strong>CIIU</strong></td>
            <td height="63" colspan="3" style="vertical-align:middle;text-align:center;"  width="100"><strong>NORMA DE  VERTIMIENTO A ALCANTARILLADO Decreto No 1594 de 1984</strong></td>
            <td colspan="2" style="vertical-align:middle;text-align:center;" width="100"><strong>CARACTERÍSTICAS DEL VERTIMIENTO</strong></td>
            <td rowspan="2" style="vertical-align:middle;text-align:center;" width="100"><strong>FECHA DE LA CARACTERIZACIÓN DEL VERTIMIENTO</strong></td>
            <td rowspan="2" style="vertical-align:middle;text-align:center;" width="100"><strong>CUMPLIMIENTO NORMATIVO(SI / NO// ND / NR)</strong></td>
            <td colspan="2" style="vertical-align:middle;text-align:center;" align="center" width="100"><strong>INFORME A AUTORIDAD AMBIENTAL POR INCUMPLIMIENTO</strong></td>
          </tr>
          <tr>
            <td style="vertical-align:middle;text-align:center;" height="62"><strong>PARÁMETROS</strong></td>
            <td style="vertical-align:middle;text-align:center;"><strong>VALOR</strong></td>
            <td style="vertical-align:middle;text-align:center;"><strong>UNIDAD</strong></td>
            <td style="vertical-align:middle;text-align:center;"><strong>VALOR</strong></td>
            <td style="vertical-align:middle;text-align:center;"><strong>UNIDAD</strong></td>
            <td style="vertical-align:middle;text-align:center;"><strong>FECHA</strong></td>
            <td style="vertical-align:middle;text-align:center;"><strong>RADICADO</strong></td>
          </tr>
        </table>
        <table width="30%" border="1" bordercolor="#999999">
        <% 
            //Obtenemos el codigo del proceso y armamos los queries.
            String codigoProceso = rsetProceso.getString("CODIGO");
            String queryContentenido = "SELECT\n" +
                                        " PPV.CODIGO AS COD_PUNTO,\n"+
                                        "JPM.JORNADA,\n"+
                                        "PRMTRO.DESCRIPCION AS PARAMETRO,\n" +
                                        "(CASE WHEN ACTPARM.MOSTRAR_RANGO = 1 THEN ((CASE WHEN ACTPARM.RANGO_INICIAL > 0 AND ACTPARM.RANGO_INICIAL < 1 THEN to_char(ACTPARM.RANGO_INICIAL,'0.99999') ELSE to_char(ACTPARM.RANGO_INICIAL) END) || ' <> ' || (CASE WHEN ACTPARM.RANGO_FINAL > 0 AND ACTPARM.RANGO_FINAL < 1 THEN to_char(ACTPARM.RANGO_FINAL,'0.99999') ELSE to_char(ACTPARM.RANGO_FINAL) END))\n" +
                                        "   WHEN ACTPARM.MOSTRAR_RANGO = 2 THEN ((CASE WHEN ACTPARM.RANGO_FINAL > 0 AND ACTPARM.RANGO_FINAL < 1 THEN to_char(ACTPARM.RANGO_FINAL,'0.99999') ELSE to_char(ACTPARM.RANGO_FINAL) END))\n" +
                                        "   WHEN ACTPARM.MOSTRAR_RANGO = 3 THEN '-'\n" +
                                        "  END) AS RANGO,\n" +
                                        "UND.DESCRIPCION AS UNIDAD,\n" +
                                        "(CASE WHEN DTJ.VALOR > 1 THEN ((COALESCE(DTJ.MENOR,' ')) || DTJ.VALOR) ELSE ((COALESCE(DTJ.MENOR,' ')) || to_char(DTJ.VALOR,'0.99999')) END) AS VALOR,\n" +
                                        "to_char(MON.FECHA_MONITOREO,'dd/mm/yyyy') AS FECHA_CARACTERIZACION,\n" +
                                        "(CASE WHEN ACTPARM.MOSTRAR_RANGO = 3 THEN 'NO APLICA' ELSE DTJ.CUMPLE END) AS CUMPLE, \n" +
                                        "(CASE WHEN PV.FECHA_RADICACION IS NULL THEN ' ' ELSE to_char(PV.FECHA_RADICACION,'dd/mm/yyyy') END) AS FECHA_RADICACION\n" +
                                        "FROM\n" +
                                        " TB_PROCESOS_VERTIMIENTOS PV\n" +
                                        "INNER JOIN  TB_MONITOREOS MON ON PV.CODIGO = MON.FK_PROCESO_VERTIMIENTO\n" +
                                        "INNER JOIN  TB_PUNTOS_MONITOREOS PM ON MON.CODIGO = PM.FK_MONITOREO\n" +
                                        "INNER JOIN  TB_PUNTOS_VERTIMIENTOS PPV ON PM.FK_PUNTO_VERTIMIENTO = PPV.CODIGO\n" +
                                        "INNER JOIN  TB_JORNADAS_PUNTOS_MONITOREOS JPM ON JPM.FK_PUNTO_MONITOREO = PM.CODIGO\n" +
                                        "INNER JOIN  TB_DETALLES_JORNADAS DTJ ON DTJ.FK_JORNADA_PUTOS_MONITOREO = JPM.CODIGO\n" +
                                        "INNER JOIN  TB_PARAM_FISICOQUIMICOS PRMTRO ON DTJ.FK_PARAM_FISICOQUIMICO = PRMTRO.CODIGO\n" +
                                        "INNER JOIN  TB_UNIDADES_MEDIDAS UND ON UND.CODIGO = PRMTRO.FK_UNIDAD_MEDIDA\n" +
                                        "INNER JOIN  TB_ACT_PARAMFISICOQUIMICOS ACTPARM ON PM.FK_ACTIVIDAD_ECONOMICA = ACTPARM.FK_ACTIVIDAD_ECONOMICA AND PRMTRO.CODIGO = ACTPARM.FK_PARAM_FISICOQUIMICO\n" +
                                        "INNER JOIN  TB_CLIENTES CLNT ON PV.FK_CLIENTE = CLNT.CODIGO\n" +
                                        "LEFT JOIN  TB_TIPOS_USOS_SERVICIOS TUSO ON CLNT.FK_USO_SERVICIO = TUSO.CODIGO\n" +
                                        "INNER JOIN  TB_ACTIVIDADES_ECONOMICAS AECON ON CLNT.FK_ACTIVIDAD_ECONOMICA = AECON.CODIGO\n" +
                                        "WHERE" + 
                                        " DTJ.CUMPLE IS NOT NULL AND MON.FK_ESTADO = 1 AND PV.CODIGO = "+codigoProceso+"\n" +
                                        "ORDER BY   PM.FK_PUNTO_VERTIMIENTO, JPM.JORNADA,PPV.DESCRIPCION ASC";
            
                        String queryContenido1 = "SELECT\n" +
                                        "CLNT.RAZON_SOCIAL AS NOMBRE_EMPRESA,\n" +
                                        "COALESCE(TUSO.DESCRIPCION,' ') AS TIPO,\n" +
                                        "AECON.DESCRIPCION AS DESCCIIU,\n" +
                                        "AECON.CODIGO_CIIU AS CIIU\n" +
                                        "FROM\n" +
                                        " TB_PROCESOS_VERTIMIENTOS PV\n" +
                                        "INNER JOIN  TB_CLIENTES CLNT ON PV.FK_CLIENTE = CLNT.CODIGO\n" +
                                        "LEFT JOIN  TB_TIPOS_USOS_SERVICIOS TUSO ON CLNT.FK_USO_SERVICIO = TUSO.CODIGO\n" +
                                        "INNER JOIN  TB_ACTIVIDADES_ECONOMICAS AECON ON CLNT.FK_ACTIVIDAD_ECONOMICA = AECON.CODIGO\n" +
                                        "WHERE PV.CODIGO = "+codigoProceso;
            
                        String queryCantidad = "SELECT\n" +
                                                "COUNT(PV.CODIGO) AS CANTIDAD\n" +
                                                "FROM\n" +
                                                " TB_PROCESOS_VERTIMIENTOS PV\n" +
                                                "INNER JOIN  TB_MONITOREOS MON ON PV.CODIGO = MON.FK_PROCESO_VERTIMIENTO\n" +
                                                "INNER JOIN  TB_PUNTOS_MONITOREOS PM ON MON.CODIGO = PM.FK_MONITOREO\n" +
                                                "INNER JOIN  TB_PUNTOS_VERTIMIENTOS PPV ON PM.FK_PUNTO_VERTIMIENTO = PPV.CODIGO\n" +
                                                "INNER JOIN  TB_JORNADAS_PUNTOS_MONITOREOS JPM ON JPM.FK_PUNTO_MONITOREO = PM.CODIGO\n" +
                                                "INNER JOIN  TB_DETALLES_JORNADAS DTJ ON DTJ.FK_JORNADA_PUTOS_MONITOREO = JPM.CODIGO\n" +
                                                "WHERE DTJ.CUMPLE IS NOT NULL AND MON.FK_ESTADO = 1 AND PV.CODIGO = " + codigoProceso;
                    
                     String queryObservaciones = "SELECT\n" +
                                                "PM.JORNADA_PRODUCTIVA_OBSERVACION\n" +
                                                "FROM\n" +
                                                " TB_PROCESOS_VERTIMIENTOS PV\n" +
                                                "INNER JOIN  TB_MONITOREOS MON ON PV.CODIGO = MON.FK_PROCESO_VERTIMIENTO\n" +
                                                "INNER JOIN  TB_PUNTOS_MONITOREOS PM ON MON.CODIGO = PM.FK_MONITOREO\n" +
                                                "AND PV.CODIGO = "+codigoProceso+" AND PM.JORNADA_PRODUCTIVA_OBSERVACION IS NOT NULL AND MON.FK_ESTADO = 1";
                    
                    String queryLab = "SELECT\n" +
                                        "('Nombre: '|| LAB.NOMBRES ||' - Direccion: ' || LAB.DIRECCION || ' - Telefono: ' || LAB.TELEFONO1 || ' - Contacto: ' || LAB.CONTACTO) AS LABORATORIO,\n" +
                                        "LAB.RESOLUCION_ACREDITACION AS RESOLUCION \n" +
                                        "FROM\n" +
                                        " TB_PROCESOS_VERTIMIENTOS PV\n" +
                                        "INNER JOIN  TB_MONITOREOS MON ON PV.CODIGO = MON.FK_PROCESO_VERTIMIENTO\n" +
                                        "INNER JOIN TB_LABORATORIOS_CONSULTOR LAB ON MON.FK_LABORATORIO = LAB.CODIGO\n" +
                                        "WHERE PV.CODIGO = "+codigoProceso+" AND MON.FK_ESTADO = 1";
                    
                

                            
                    //Obtenemos las observaciones por proceso
                    ResultSet rsetObservaciones = db.ejecutar(conn, queryObservaciones);
                    
                    String observaciones = "";
                    while(rsetObservaciones.next()){
                        observaciones += " - " + rsetObservaciones.getString("JORNADA_PRODUCTIVA_OBSERVACION");
                    }
                    
                    //Obtenemos el laboratorio
                    ResultSet rsetLab = db.ejecutar(conn, queryLab);
                    
                    String lab = "";
                    String resolucion = "";
                
                    while(rsetLab.next()){
                        lab += rsetLab.getString("LABORATORIO");
                        resolucion += rsetLab.getString("RESOLUCION");
                    }
        
                    //Obtenemos la cantidad de registros por informe
                    ResultSet rsetCantidad = db.ejecutar(conn, queryCantidad);
                    String cantidad = "";
                    while(rsetCantidad.next()){
                        cantidad = rsetCantidad.getString("CANTIDAD");
                    }
                
        %>
        <%  
            //Obtenemos la informacion de la primera parte de la tabla
            ResultSet rsetContenido1 = db.ejecutar(conn, queryContenido1);
         %>   
           
     
        
        <%
          
         int flagEncabezado = 0;
         int flagPunto = 0;
         int contParam = 0;
         int cantParam = 0;
         
        //Obtenemos la informacion de la segunda parte de la tabla.
        ResultSet rsetContenido = db.ejecutar(conn, queryContentenido);
        while(rsetContenido.next()){
        %>
        
            <%             
                   if(flagPunto == 0){                        
                       
                       String queryPuntoVertimiento =  "SELECT\n" +
                                                                "PV.CODIGO,\n" +
                                                                "PTV.DESCRIPCION,\n" +
                                                                "PTM.FK_PUNTO_VERTIMIENTO,\n"+
                                                                "JM.JORNADA, " +
                                                                "Count(JM.CODIGO) as CANT_PUNTO,\n" +
                                                                "CLI.RAZON_SOCIAL\n"+
                                                             "FROM\n" +
                                                                "SCV.TB_PROCESOS_VERTIMIENTOS PV\n" +
                                                                "INNER JOIN SCV.TB_MONITOREOS MON ON PV.CODIGO = MON.FK_PROCESO_VERTIMIENTO\n" +
                                                                "INNER JOIN SCV.TB_PUNTOS_MONITOREOS PTM ON MON.CODIGO = PTM.FK_MONITOREO\n" +
                                                                "INNER JOIN SCV.TB_PUNTOS_VERTIMIENTOS PTV ON PTV.CODIGO = PTM.FK_PUNTO_VERTIMIENTO\n" +
                                                                "INNER JOIN SCV.TB_JORNADAS_PUNTOS_MONITOREOS JM ON PTM.CODIGO = JM.FK_PUNTO_MONITOREO\n" +
                                                                "INNER JOIN SCV.TB_DETALLES_JORNADAS DTJ ON JM.CODIGO = DTJ.FK_JORNADA_PUTOS_MONITOREO\n" +
                                                                "INNER JOIN SCV.TB_CLIENTES CLI ON CLI.CODIGO = PV.FK_CLIENTE\n"+
                                                             "WHERE\n" +
                                                                "DTJ.CUMPLE IS NOT NULL AND\n" +
                                                                "MON.FK_ESTADO = 1 AND\n" +
                                                                "PTV.CODIGO = "+rsetContenido.getString("COD_PUNTO")+" AND\n" +
                                                                "PV.CODIGO = "+codigoProceso+" AND\n" +
                                                                "JM.JORNADA = "+rsetContenido.getString("JORNADA") +" \n" +
                                                             "GROUP BY PV.CODIGO, PTM.FK_PUNTO_VERTIMIENTO,JM.JORNADA, PTV.DESCRIPCION, CLI.RAZON_SOCIAL ORDER BY PTV.DESCRIPCION, PTM.FK_PUNTO_VERTIMIENTO,JM.JORNADA ASC"; 

                           ResultSet rsetEncabezado = db.ejecutar(conn, queryPuntoVertimiento);
                                                    
                           while(rsetEncabezado.next()){
                              cantParam = rsetEncabezado.getInt("CANT_PUNTO");
                              if(cantParam > 0 && contParam == 0 || cantParam == contParam ){
                                  
                                    if(flagEncabezado == 0){%> 
                                       <tr>
                                        <td scope="col" rowspan="<%= cantidad %>" style="vertical-align:middle;text-align:center;"><%= codigoProceso %></td>                        
                                    <%}else{%>
                                         <tr style="border-top-color: black;">
                                    <%}%>
                                    <td scope="col" rowspan="<%= cantParam %>" style="vertical-align:middle;text-align:center; "><%=rsetEncabezado.getString("RAZON_SOCIAL")%> - <%= rsetEncabezado.getString("DESCRIPCION")%> : Jornada <%=rsetEncabezado.getString("JORNADA")%></td>
                                
                        <%   }else{%>
                                  <tr>
                             <%}
                               if( contParam  == (cantParam-1)){
                                  contParam = 0;
                               }else{
                                      //Esta bandera indica la cantidad de parametros que se van recoriendo
                                        contParam++;                               
                               }
                           }
                           
                   }
         
            
             if(flagEncabezado == 0){
                while(rsetContenido1.next()){ 
                    flagEncabezado = 1;
            %>    

                <td scope="col" rowspan="<%= cantidad %>" style="vertical-align:middle;text-align:center;"><%=  rsetContenido1.getString("TIPO") %></td>

                <td scope="col" rowspan="<%= cantidad %>" style="vertical-align:middle;text-align:center;"><%= rsetProceso.getString("FK_CONTRATO") %></td>
                <td scope="col" rowspan="<%= cantidad %>" style="vertical-align:middle;text-align:center;"></td>
                <td scope="col" rowspan="<%= cantidad %>" style="vertical-align:middle;text-align:center;"><%= rsetContenido1.getString("DESCCIIU") %></td>
                <td scope="col" rowspan="<%= cantidad %>" style="vertical-align:middle;text-align:center;"><%= rsetContenido1.getString("CIIU") %></td>
            <% } }
           
           %>
        
            
            
        
            <td scope="col"><%= rsetContenido.getString("PARAMETRO") %></td>
            <td  scope="col" style="vertical-align:middle;text-align:center;"><%= rsetContenido.getString("RANGO") %></td>
            <td scope="col" style="vertical-align:middle;text-align:center;"><%= ApiManager.convCharUnids(rsetContenido.getString("UNIDAD")) %></td>
            <td scope="col" style="vertical-align:middle;text-align:center;"><%= rsetContenido.getString("VALOR") %></td>
            <td scope="col" style="vertical-align:middle;text-align:center;"><%= ApiManager.convCharUnids(rsetContenido.getString("UNIDAD")) %></td>
            <td scope="col" style="vertical-align:middle;text-align:center;"><%= rsetContenido.getString("FECHA_CARACTERIZACION") %></td>
            <td scope="col" style="vertical-align:middle;text-align:center;"><%= rsetContenido.getString("CUMPLE") %></td>
            <td scope="col" style="vertical-align:middle;text-align:center;"><%= rsetContenido.getString("FECHA_RADICACION") %></td>
            <td scope="col" style="vertical-align:middle;text-align:center;"></td>
            </tr>
        <% }
        %>
            <tr><td colspan="16"><strong>32. OBSERVACIONES:</strong> <%= observaciones %></td></tr>
            <tr><td colspan="16"><strong>33. NOMBRE (S) DEL (LOS) LABORATORIO(S) ACREDITADO (S):</strong> <%= lab %></td></tr>
            <tr><td colspan="16"><strong>34. RESOLUCIÓN IDEAM DE ACREDITACIÓN No</strong> <%= resolucion %></td></tr>
        </table>
        <p>&nbsp;</p>
        <% } db.desconectar(conn); %>
    </body>
</html>
