package modelo.ReportesManagers;

import Extensions.ArchivoExtension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Blob;
import java.sql.Date;
import java.sql.ResultSet;import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import modelo.ReportesDelegates.InsertarAnexoDagma;
import modelo.ReportesDelegates.ReportesDelegate;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author jmrincon
 */
public class ReportesManager {
    
    public JSONArray getReporte1(String fechaInicial, String fechaFinal, String estadoProceso, String numeroContrato, 
            String nit, String actividadProductiva, String razonSocial, String comuna, String laboratorio, 
            String usoServicio, String tipoInforme, String filaInicio, String filaFin) throws Exception{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        ReportesDelegate select = new ReportesDelegate(fechaInicial, fechaFinal, estadoProceso, 
                numeroContrato, nit, actividadProductiva, razonSocial, comuna,  laboratorio, 
                usoServicio, tipoInforme);
        ResultSet rset = select.ejecutarReporte1(filaInicio, filaFin);
        
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        
        while(rset.next()){
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("codigoProceso", rset.getString("CODIGOPROCESO"));
            jsonObject.put("numeroContrato", rset.getString("NUMEROCONTRATO"));
            jsonObject.put("nit", rset.getString("NIT"));
            jsonObject.put("codigoCliente", rset.getString("CODIGOCLIENTE"));
            jsonObject.put("razonSocial", rset.getString("RAZONSOCIAL"));
            jsonObject.put("actividadProductiva", rset.getString("ACTIVIDADPRODUCTIVA"));
            jsonObject.put("jornadaLaboral", rset.getString("JORNADALABORAL"));
            jsonObject.put("usoServicio", rset.getString("USOSERVICIO"));
            jsonObject.put("direccion", rset.getString("DIRECCION"));
            jsonObject.put("barrio", rset.getString("BARRIO"));
            jsonObject.put("comuna", rset.getString("COMUNA"));
            jsonObject.put("telefono", rset.getString("TELEFONO"));
            jsonObject.put("nombreContacto", rset.getString("NOMBRECONTACTO"));
            jsonObject.put("email", rset.getString("EMAIL"));
            jsonObject.put("tipoInforme", rset.getString("TIPOINFORME"));
            jsonObject.put("notificado", rset.getString("NOTIFICADO"));
            jsonObject.put("labContacto", rset.getString("LABCONTACTO"));
            jsonObject.put("nombConsultor", rset.getString("NOMBCONSULTOR"));
            jsonObject.put("fechaEntrega", rset.getString("FECHAENTREGA"));
            jsonObject.put("fechaDevolucion", rset.getString("FECHADEVOLUCION"));
            jsonObject.put("fechaEntregaDevolucion", rset.getString("FECHAENTREGADEVOLUCION"));
            jsonObject.put("rnum", rset.getString("RNUM"));
            jsonObject.put("total", rset.getString("TOTAL_ROWS"));
            
            ResultSet rsetEstado = select.obtenerUltimoEstadoXProceso(rset.getString("CODIGOPROCESO"));
            while(rsetEstado.next()){
                jsonObject.put("codigoEstadoActual", rsetEstado.getString("CODIGOESTADOACTUAL"));
                jsonObject.put("ultimoEstado", rsetEstado.getString("ESTADOACTUAL"));
            }
            
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
        }
        jsonArreglo.add(jsonArray);
        //Se cierra el ResultSet
        select.desconectar();
        return jsonArreglo;
    }
    
    public JSONArray getReporte2(String fechaInicial, String fechaFinal, String estadoProceso, String numeroContrato, 
            String nit, String actividadProductiva, String razonSocial, String comuna, String laboratorio, 
            String usoServicio, String tipoInforme, String filaInicio, String filaFin) throws Exception{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        ReportesDelegate select = new ReportesDelegate(fechaInicial, fechaFinal, estadoProceso, 
                numeroContrato, nit, actividadProductiva, razonSocial, comuna, laboratorio, 
                usoServicio, tipoInforme);
        ResultSet rset = select.ejecutarReporte2(filaInicio, filaFin);
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        while(rset.next()){
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("codigoProceso", rset.getString("CODIGOPROCESO"));
            jsonObject.put("numeroContrato", rset.getString("NUMEROCONTRATO"));
            jsonObject.put("nit", rset.getString("NIT"));
            jsonObject.put("codigoCliente", rset.getString("CODIGOCLIENTE"));
            jsonObject.put("razonSocial", rset.getString("RAZONSOCIAL"));
            jsonObject.put("actividadProductiva", rset.getString("ACTIVIDADPRODUCTIVA"));
            jsonObject.put("jornadaLaboral", rset.getString("JORNADALABORAL"));
            jsonObject.put("usoServicio", rset.getString("USOSERVICIO"));
            jsonObject.put("direccion", rset.getString("DIRECCION"));
            jsonObject.put("barrio", rset.getString("BARRIO"));
            jsonObject.put("comuna", rset.getString("COMUNA"));
            jsonObject.put("telefono", rset.getString("TELEFONO"));
            jsonObject.put("nombreContacto", rset.getString("NOMBRECONTACTO"));
            jsonObject.put("email", rset.getString("EMAIL"));
            jsonObject.put("tipoInforme", rset.getString("TIPOINFORME"));
            jsonObject.put("ultimoEstado", rset.getString("ESTADOPROCESO"));
            
            ResultSet rsetVisita = select.obtenerUltimaVisitaXProceso(rset.getString("CODIGOPROCESO"));
            while(rsetVisita.next()){
                jsonObject.put("codigoUltimaVisita", rsetVisita.getString("CODIGOVISITA"));
                jsonObject.put("fechaUltimaVisita", rsetVisita.getString("FECHAULTIMAVISITA"));
            }
            if (jsonObject.get("codigoUltimaVisita") == null) {
                jsonObject.put("codigoUltimaVisita", null);
                jsonObject.put("fechaUltimaVisita", "...");
            }
            
            jsonObject.put("fechaProceso", rset.getString("FECHAPROCESO"));
            jsonObject.put("fechaMonitoreo", rset.getString("FECHAMONITOREO"));
            jsonObject.put("labContacto", rset.getString("LABCONTACTO"));
            jsonObject.put("estadoAnterior", rset.getString("ESTADOANTERIOR"));
            jsonObject.put("estadoActual", rset.getString("ESTADOACTUAL"));
            jsonObject.put("fechaModificacion", rset.getString("FECHAMODIFICACION"));
            jsonObject.put("rnum", rset.getString("RNUM"));
            jsonObject.put("total", rset.getString("TOTAL_ROWS"));
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
        }
        jsonArreglo.add(jsonArray);
        //Se cierra el ResultSet
        select.desconectar();
        return jsonArreglo;
    }
    
    public JSONArray getReporte3(String fechaInicial, String fechaFinal, String estadoProceso, String numeroContrato, 
            String nit, String actividadProductiva, String razonSocial, String comuna, String laboratorio, 
            String usoServicio, String tipoInforme, String filaInicio, String filaFin) throws Exception{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        ReportesDelegate select = new ReportesDelegate(fechaInicial, fechaFinal, estadoProceso, 
                numeroContrato, nit, actividadProductiva, razonSocial, comuna, laboratorio, 
                usoServicio, tipoInforme);
        ResultSet rset = select.ejecutarReporte3(filaInicio, filaFin);
        
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        while(rset.next()){
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("codigoProceso", rset.getString("CODIGOPROCESO"));
            jsonObject.put("numeroContrato", rset.getString("NUMEROCONTRATO"));
            jsonObject.put("nit", rset.getString("NIT"));
            jsonObject.put("codigoCliente", rset.getString("CODIGOCLIENTE"));
            jsonObject.put("razonSocial", rset.getString("RAZONSOCIAL"));
            jsonObject.put("actividadProductiva", rset.getString("ACTIVIDADPRODUCTIVA"));
            jsonObject.put("jornadaLaboral", rset.getString("JORNADALABORAL"));
            jsonObject.put("usoServicio", rset.getString("USOSERVICIO"));
            jsonObject.put("direccion", rset.getString("DIRECCION"));
            jsonObject.put("barrio", rset.getString("BARRIO"));
            jsonObject.put("comuna", rset.getString("COMUNA"));
            jsonObject.put("telefono", rset.getString("TELEFONO"));
            jsonObject.put("nombreContacto", rset.getString("NOMBRECONTACTO"));
            jsonObject.put("email", rset.getString("EMAIL"));
            jsonObject.put("tipoInforme", rset.getString("TIPOINFORME"));
            jsonObject.put("codigoVisita", rset.getString("CODIGOVISITA"));
            jsonObject.put("fechaVisita", rset.getString("FECHAVISITA"));
            jsonObject.put("estadoVisita", rset.getString("ESTADOVISITA"));
            jsonObject.put("resultadoVisita", rset.getString("RESULTADOVISITA"));
            jsonObject.put("nombreTecnico", rset.getString("NOMBRETECNICO"));
            jsonObject.put("rnum", rset.getString("RNUM"));
            jsonObject.put("total", rset.getString("TOTAL_ROWS"));
            
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
        }
        jsonArreglo.add(jsonArray);
        //Se cierra el ResultSet
        select.desconectar();
        return jsonArreglo;
    }
    
    public JSONArray getReporte4(String fechaInicial, String fechaFinal, String estadoProceso, String numeroContrato, 
            String nit, String actividadProductiva, String razonSocial, String comuna, String laboratorio, 
            String usoServicio, String tipoInforme, String filaInicio, String filaFin) throws Exception{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        ReportesDelegate select = new ReportesDelegate(fechaInicial, fechaFinal, estadoProceso, 
                numeroContrato, nit, actividadProductiva, razonSocial, comuna, laboratorio, 
                usoServicio, tipoInforme);
        ResultSet rset = select.ejecutarReporte4(filaInicio, filaFin);
        
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        while(rset.next()){
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("codigoProceso", rset.getString("CODIGOPROCESO"));
            jsonObject.put("numeroContrato", rset.getString("NUMEROCONTRATO"));
            jsonObject.put("nit", rset.getString("NIT"));
            jsonObject.put("codigoCliente", rset.getString("CODIGOCLIENTE"));
            jsonObject.put("razonSocial", rset.getString("RAZONSOCIAL"));
            jsonObject.put("actividadProductiva", rset.getString("ACTIVIDADPRODUCTIVA"));
            jsonObject.put("jornadaLaboral", rset.getString("JORNADALABORAL"));
            jsonObject.put("usoServicio", rset.getString("USOSERVICIO"));
            jsonObject.put("direccion", rset.getString("DIRECCION"));
            jsonObject.put("barrio", rset.getString("BARRIO"));
            jsonObject.put("comuna", rset.getString("COMUNA"));
            jsonObject.put("telefono", rset.getString("TELEFONO"));
            jsonObject.put("nombreContacto", rset.getString("NOMBRECONTACTO"));
            jsonObject.put("email", rset.getString("EMAIL"));
            jsonObject.put("tipoInforme", rset.getString("TIPOINFORME"));
            jsonObject.put("notificado", rset.getString("NOTIFICADO"));
            
            jsonObject.put("codigoEstadoActual", rset.getString("CODIGOESTADOPROCESO"));
            jsonObject.put("ultimoEstado", rset.getString("ESTADOPROCESO"));
            
            ResultSet rsetVisita = select.obtenerUltimaVisitaXProceso(rset.getString("CODIGOPROCESO"));
            while(rsetVisita.next()){
                jsonObject.put("codigoUltimaVisita", rsetVisita.getString("CODIGOVISITA"));
                jsonObject.put("fechaUltimaVisita", rsetVisita.getString("FECHAULTIMAVISITA"));
            }
            if (jsonObject.get("codigoUltimaVisita") == null) {
                jsonObject.put("codigoUltimaVisita", null);
                jsonObject.put("fechaUltimaVisita", "...");
            }
            
            ResultSet rsetHistorial = select.obtenerUltimoHistorialDagmaXProceso(rset.getString("CODIGOPROCESO"));
            while(rsetHistorial.next()){
                jsonObject.put("codigoUltimoDagma", rsetHistorial.getString("CODIGOHISTORIAL"));
                jsonObject.put("fechaUltimoDagma", rsetHistorial.getString("FECHARADICADOHISTORIAL"));
            }
            if (jsonObject.get("codigoUltimoDagma") == null) {
                jsonObject.put("codigoUltimoDagma", null);
                jsonObject.put("fechaUltimoDagma", "...");
            }
            
            jsonObject.put("fechaRadicacion", rset.getString("FECHARADICACION"));
            jsonObject.put("labContacto", rset.getString("LABCONTACTO"));
            jsonObject.put("rnum", rset.getString("RNUM"));
            jsonObject.put("total", rset.getString("TOTAL_ROWS"));
            
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
        }
        jsonArreglo.add(jsonArray);
        //Se cierra el ResultSet
        select.desconectar();
        return jsonArreglo;
    }
    
    public JSONArray getReporte5(String fechaInicial, String fechaFinal, String estadoProceso, String numeroContrato, 
            String nit, String actividadProductiva, String razonSocial, String comuna, String laboratorio, 
            String usoServicio, String tipoInforme, String filaInicio, String filaFin) throws Exception{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        ReportesDelegate select = new ReportesDelegate(fechaInicial, fechaFinal, estadoProceso, 
                numeroContrato, nit, actividadProductiva, razonSocial, comuna, laboratorio, 
                usoServicio, tipoInforme);
        ResultSet rset = select.ejecutarReporte5(filaInicio, filaFin);
        
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        Integer count = 0;
        while(rset.next()){
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("codigoProceso", rset.getString("CODIGOPROCESO"));
            jsonObject.put("fechaProceso", rset.getString("FECHAPROCESO"));
            jsonObject.put("numeroContrato", rset.getString("NUMEROCONTRATO"));
            jsonObject.put("nit", rset.getString("NIT"));
            jsonObject.put("codigoCliente", rset.getString("CODIGOCLIENTE"));
            jsonObject.put("razonSocial", rset.getString("RAZONSOCIAL"));
            jsonObject.put("actividadProductiva", rset.getString("ACTIVIDADPRODUCTIVA"));
            jsonObject.put("jornadaLaboral", rset.getString("JORNADALABORAL"));
            jsonObject.put("usoServicio", rset.getString("USOSERVICIO"));
            jsonObject.put("direccion", rset.getString("DIRECCION"));
            jsonObject.put("barrio", rset.getString("BARRIO"));
            jsonObject.put("comuna", rset.getString("COMUNA"));
            jsonObject.put("telefono", rset.getString("TELEFONO"));
            jsonObject.put("nombreContacto", rset.getString("NOMBRECONTACTO"));
            jsonObject.put("email", rset.getString("EMAIL"));
            jsonObject.put("tipoInforme", rset.getString("TIPOINFORME"));
            jsonObject.put("labContacto", rset.getString("LABCONTACTO"));
            jsonObject.put("tuvoSupervision", rset.getString("TUVOSUPERVISION"));
            jsonObject.put("codigoUltimoMonitoreo", rset.getString("CODIGOMONITOREO"));
            jsonObject.put("fechaUltimoMonitoreo", rset.getString("FECHAULTIMOMONITOREO"));
            
            
            jsonObject.put("cantidadMonitoreo", rset.getString("CANTIDADMONITOREO"));
            jsonObject.put("cantidadVisitas", rset.getString("CANTIDADVISITAS"));
            //jsonObject.put("rnum", rset.getString("RNUM"));
            //jsonObject.put("total", rset.getString("TOTAL_ROWS"));
            
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            count ++;
            jsonArray.add(jsonObject.clone());
        }
        
        if (jsonArray.size() > 0) {
            JSONObject jsonObject1 = (JSONObject)jsonArray.get(0);
            jsonObject1.put("total", count);
        }
        jsonArreglo.add(jsonArray);
        //Se cierra el ResultSet
        select.desconectar();
        return jsonArreglo;
    }
    
    public JSONArray getReporte6(String fechaInicial, String fechaFinal, String numeroContrato, 
            String nit, String actividadProductiva, String usoServicio, String paramFisicoQuimico, 
            String rangoInicial, String rangoFinal, String filaInicio, String filaFin) throws Exception{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        ReportesDelegate select = new ReportesDelegate(fechaInicial, fechaFinal, null, 
                numeroContrato, nit, actividadProductiva, null, null, null, 
                usoServicio, null);
        
        String[] arrParamFisicoQuimico = paramFisicoQuimico.split(",");
        String[] arrRangoInicial = rangoInicial.split(",");
        String[] arrRangoFinal = rangoFinal.split(",");
        
        ResultSet rset = select.ejecutarReporte6(arrParamFisicoQuimico, arrRangoInicial, arrRangoFinal, filaInicio, filaFin);
        
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        while(rset.next()){
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("codigoProceso", rset.getString("CODIGOPROCESO"));
            jsonObject.put("numeroContrato", rset.getString("NUMEROCONTRATO"));
            jsonObject.put("nit", rset.getString("NIT"));
            jsonObject.put("razonSocial", rset.getString("RAZONSOCIAL"));
            jsonObject.put("codigoCliente", rset.getString("CODIGOCLIENTE"));
            jsonObject.put("direccion", rset.getString("DIRECCION"));
            jsonObject.put("barrio", rset.getString("BARRIO"));
            jsonObject.put("comuna", rset.getString("COMUNA"));
            jsonObject.put("usoServicio", rset.getString("USOSERVICIO"));
            jsonObject.put("actividadProductiva", rset.getString("ACTIVIDADPRODUCTIVA"));
            jsonObject.put("puntoVertimiento", rset.getString("PUNTOVERTIMIENTO"));
            jsonObject.put("jornada", rset.getString("JORNADA"));
            jsonObject.put("descParametro", rset.getString("DESCPARAMETRO"));
            jsonObject.put("valorParametro", rset.getString("VALORPARAMETRO"));
            jsonObject.put("rnum", rset.getString("RNUM"));
            jsonObject.put("total", rset.getString("TOTAL_ROWS"));
            
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
        }
        jsonArreglo.add(jsonArray);
        //Se cierra el ResultSet
        select.desconectar();
        return jsonArreglo;
    }
    
    public JSONArray getReporte7(String fechaInicial, String fechaFinal, String numeroContrato, 
            String nit, String actividadProductiva, String usoServicio, String lodoInicial, String lodoFinal, 
            String tasaInicial, String tasaFinal, String filaInicio, String filaFin) throws Exception{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        ReportesDelegate select = new ReportesDelegate(fechaInicial, fechaFinal, null, 
                numeroContrato, nit, actividadProductiva, null, null, null, usoServicio, null);
        ResultSet rset = select.ejecutarReporte7(lodoInicial, lodoFinal, tasaInicial, tasaFinal, filaInicio, filaFin);
        
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        while(rset.next()){
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("codigoProceso", rset.getString("CODIGOPROCESO"));
            jsonObject.put("numeroContrato", rset.getString("NUMEROCONTRATO"));
            jsonObject.put("codigoCliente", rset.getString("CODIGOCLIENTE"));
            jsonObject.put("nit", rset.getString("NIT"));
            jsonObject.put("razonSocial", rset.getString("RAZONSOCIAL"));
            jsonObject.put("direccion", rset.getString("DIRECCION"));
            jsonObject.put("barrio", rset.getString("BARRIO"));
            jsonObject.put("comuna", rset.getString("COMUNA"));
            jsonObject.put("usoServicio", rset.getString("USOSERVICIO"));
            jsonObject.put("actividadProductiva", rset.getString("ACTIVIDADPRODUCTIVA"));
            jsonObject.put("volumenLodo", rset.getString("VOLUMENLODO"));
            jsonObject.put("tasaRetributiva", rset.getString("TASARETRIBUTIVA"));
            jsonObject.put("rnum", rset.getString("RNUM"));
            jsonObject.put("total", rset.getString("TOTAL_ROWS"));
            
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
        }
        jsonArreglo.add(jsonArray);
        //Se cierra el ResultSet
        select.desconectar();
        return jsonArreglo;
    }
    
    public JSONArray getMonitoreosProceso(String codigoProceso) throws Exception{
        //Ejecutamos la consulta y obtenemos el ResultSet
        ReportesDelegate select = new ReportesDelegate(null, null, null, null, null, null, null, null, null, null, null);
        ResultSet rset = select.obtenerMonitoreosXProceso(codigoProceso);
        
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        
        while(rset.next()){
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("codigoMonitoreo", rset.getString("CODIGOMONITOREO"));
            jsonObject.put("codigoProceso", rset.getString("CODIGOPROCESO"));
            jsonObject.put("labContacto", rset.getString("LABCONTACTO"));
            jsonObject.put("tuvoMonitoreo", rset.getString("TUVOMONITOREO"));
            jsonObject.put("nombreTecnico", rset.getString("NOMBRETECNICO"));
            jsonObject.put("obseSupervision", rset.getString("OBSESUPERVISION"));
            jsonObject.put("fechaUltimoMonitoreo", rset.getString("FECHAULTIMOMONITOREO"));
            jsonObject.put("obseMonitoreo", rset.getString("OBSEMONITOREO"));
            
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
        }
        jsonArreglo.add(jsonArray);
        //Se cierra el ResultSet
        select.desconectar();
        return jsonArreglo;
    }
    
    public JSONArray getVisitasProceso(String codigoProceso) throws Exception{
        //Ejecutamos la consulta y obtenemos el ResultSet
        ReportesDelegate select = new ReportesDelegate(null, null, null, null, null, null, null, null, null, null, null);
        ResultSet rset = select.obtenerVisitasXProceso(codigoProceso);
        
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        
        while(rset.next()){
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("codigoVisita", rset.getString("CODIGOVISITA"));
            jsonObject.put("codigoProceso", rset.getString("CODIGOPROCESO"));
            jsonObject.put("nombreTecnico", rset.getString("NOMBRETECNICO"));
            jsonObject.put("tipoVisita", rset.getString("TIPOVISITA"));
            jsonObject.put("motivoVisita", rset.getString("MOTIVOVISITA"));
            jsonObject.put("resultadoVisita", rset.getString("RESULTADOVISITA"));
            jsonObject.put("fechaVisita", rset.getString("FECHAVISITA"));
            jsonObject.put("obseVisita", rset.getString("OBSEVISITA"));
            jsonObject.put("estadoVisita", rset.getString("ESTADOVISITA"));
            
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
        }
        jsonArreglo.add(jsonArray);
        //Se cierra el ResultSet
        select.desconectar();
        return jsonArreglo;
    }
    
    public JSONArray getHistorialDagmaProceso(String codigoProceso) throws Exception{
        //Ejecutamos la consulta y obtenemos el ResultSet
        ReportesDelegate select = new ReportesDelegate(null, null, null, null, null, null, null, null, null, null, null);
        ResultSet rset = select.obtenerHistorialDagmaXProceso(codigoProceso);
        
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        
        while(rset.next()){
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("codigoHistorialDagma", rset.getString("CODIGOHISTORIALDAGMA"));
            jsonObject.put("codigoProceso", rset.getString("CODIGOPROCESO"));
            jsonObject.put("radicadoHistorialDagma", rset.getString("RADICADOHISTORIALDAGMA"));
            jsonObject.put("fechaHistorialDagma", rset.getString("FECHAHISTORIALDAGMA"));
            jsonObject.put("obseHistorialDagma", rset.getString("OBSEHISTORIALDAGMA"));
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
        }
        jsonArreglo.add(jsonArray);
        //Se cierra el ResultSet
        select.desconectar();
        return jsonArreglo;
    }
    
    public JSONArray getAdjuntosProceso(String codigoProceso) throws Exception{
        //Ejecutamos la consulta y obtenemos el ResultSet
        ReportesDelegate select = new ReportesDelegate(null, null, null, null, null, null, null, null, null, null, null);
        ResultSet rset = select.obtenerAdjuntosXProceso(codigoProceso);
        
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        
        while(rset.next()){
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("codigoArchivo", rset.getString("CODIGOADJUNTO"));
            jsonObject.put("codigoProceso", rset.getString("CODIGOPROCESO"));
            jsonObject.put("nombreArchivo", rset.getString("NOMBREARCHIVO"));
            jsonObject.put("fechaArchivo", rset.getString("FECHAARCHIVO"));
            
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
        }
        jsonArreglo.add(jsonArray);
        //Se cierra el ResultSet
        select.desconectar();
        return jsonArreglo;
    }
    
    public JSONArray getAdjuntosVisita(String codigoVisita) throws Exception{
        //Ejecutamos la consulta y obtenemos el ResultSet
        ReportesDelegate select = new ReportesDelegate(null, null, null, null, null, null, null, null, null, null, null);
        ResultSet rset = select.obtenerAdjuntosXVisita(codigoVisita);
        
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        
        while(rset.next()){
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("codigoArchivo", rset.getString("CODIGOADJUNTO"));
            jsonObject.put("codigoVisita", rset.getString("CODIGOVISITA"));
            jsonObject.put("nombreArchivo", rset.getString("NOMBREARCHIVO"));
            
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
        }
        jsonArreglo.add(jsonArray);
        //Se cierra el ResultSet
        select.desconectar();
        return jsonArreglo;
    }
    
    //HistorialDagma
    public JSONArray getAdjuntosHistorialDagma(String codigoHistorialDagma) throws Exception{
        //Ejecutamos la consulta y obtenemos el ResultSet
        ReportesDelegate select = new ReportesDelegate(null, null, null, null, null, null, null, null, null, null, null);
        ResultSet rset = select.obtenerAdjuntosXHistorialDagma(codigoHistorialDagma);
        
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        
        while(rset.next()){
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("codigoArchivo", rset.getString("CODIGOADJUNTO"));
            jsonObject.put("codigoHistorialDagma", rset.getString("CODIGOHISTORIALDAGMA"));
            jsonObject.put("nombreArchivo", rset.getString("NOMBREARCHIVO"));
            
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
        }
        jsonArreglo.add(jsonArray);
        //Se cierra el ResultSet
        select.desconectar();
        return jsonArreglo;
    }
    
    public ArchivoExtension obtenerArchivoHistorialDagma(Integer codigoHistorialDagma, Integer codigoArchivo) throws SQLException{
        ReportesDelegate select = new ReportesDelegate(null, null, null, null, null, null, null, null, null, null, null);
        ResultSet rset = select.obtenerArchivoHistorialDagma(codigoHistorialDagma, codigoArchivo);
        String nombreArchivo = null;
        Blob dataArchivo = null;
        ArchivoExtension  archivo;
        while(rset.next()){
            nombreArchivo = rset.getString("NOMBREARCHIVO");
            dataArchivo = rset.getBlob("ARCHIVO");
        }
        archivo = new ArchivoExtension(dataArchivo, nombreArchivo);
        return archivo;
    }
    
    public int insertarHistorialDagma(String radicado, String fechaRadicado, String observacion, int proceso, int tipoRadicado) throws Exception{
        ReportesDelegate delegate = new ReportesDelegate();
        return  delegate.insertarHistorialDagma(radicado, fechaRadicado, observacion, proceso, tipoRadicado);
    }
    
    /*public JSONObject Eliminar(int codigo) throws Exception{
        JSONObject jsonObject = new JSONObject(); 
        Integer respError ;
        EliminarTecnicos delete = new EliminarTecnicos(codigo);
        delete.ejecutar();
        respError = delete.getError();
        jsonObject.put("error", respError);
        return jsonObject;
    }*/
        
        
        public void insertarAnexoDagma(int codigoHist, File archivo, String nombreArchivo) throws FileNotFoundException, Exception{
            InsertarAnexoDagma insert = new InsertarAnexoDagma(codigoHist, archivo, nombreArchivo);
            insert.ejecutar();
        }
        
        public JSONArray obtenerHistorialDagma(String codigo) throws SQLException{
            //Ejecutamos la consulta y obtenemos el ResultSet
            ReportesDelegate select = new ReportesDelegate();
            ResultSet rset = select.obtenerHistorialDagma(codigo);
            //Creamos los JSONArray para guardar los objetos JSON
            JSONArray jsonArray = new JSONArray();
            JSONArray jsonArreglo = new JSONArray();

            //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos
            //en el JSONArray.
            while(rset.next()){

                //Armamos el objeto JSON con la informacion del registro
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("codigo", rset.getString("CODIGO"));
                jsonObject.put("procesoVertimiento", rset.getString("FK_PROCESO_VERTIMIENTO"));
                jsonObject.put("fechaRadicado", rset.getString("FECHA_RADICADO"));
                jsonObject.put("radicado", rset.getString("RADICADO"));

                //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
                jsonArray.add(jsonObject.clone());

            }

            jsonArreglo.add(jsonArray);
            select.desconectar();

            return jsonArreglo;
        }
        
        public JSONArray obtenerRegistroDagma(String codigoHistorial) throws SQLException{
            
            //Ejecutamos la consulta y obtenemos el ResultSet
            ReportesDelegate select = new ReportesDelegate();
            ResultSet rset = select.obtenerRegistroDagma(codigoHistorial);
            //Creamos los JSONArray para guardar los objetos JSON
            JSONArray jsonArray = new JSONArray();
            JSONArray jsonArreglo = new JSONArray();

            //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos
            //en el JSONArray.
            while(rset.next()){

                //Armamos el objeto JSON con la informacion del registro
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("codigo", rset.getString("CODIGO"));
                jsonObject.put("fechaRadicado", rset.getString("FECHA_RADICADO"));
                jsonObject.put("radicado", rset.getString("RADICADO"));
                jsonObject.put("tipoRadicado", rset.getString("FK_TIPO_RADICADO"));
                jsonObject.put("observacion", rset.getString("OBSERVACION"));

                //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
                jsonArray.add(jsonObject.clone());

            }

            jsonArreglo.add(jsonArray);
            select.desconectar();

            return jsonArreglo;
        }
        
        public void actualizarRegistroDagma(String radicado, String fechaRadicado, int procesoVertimiento, 
                int tipoRadicado, int codigoHistorial, String observacion){
            ReportesDelegate update = new ReportesDelegate();
            update.actualizarRegistroDagma(radicado, fechaRadicado, procesoVertimiento, tipoRadicado, codigoHistorial, observacion);
        }
        
        public JSONArray eliminarHistorialDagma(int codigoHistorial) throws Exception{
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject(); 

            ReportesDelegate delete = new ReportesDelegate();
            int respError = delete.eliminarHistorialDagma(codigoHistorial);

            jsonObject.put("error", respError);

            jsonArray.add(jsonObject);
        
            return jsonArray;
        }
        
        public Map organizarInfoExcelDagma(String codigoProceso) throws SQLException{
            
            ReportesDelegate delegate = new ReportesDelegate();
            ResultSet rset = delegate.obtenerHistExcelDagma(codigoProceso);
            
            //Creamos el mapa y organizamos la cabecera
            Map<String, Object[]> data = new HashMap<String, Object[]>();
            
            int contador = 0;
            String strContador = "";
            
            while(rset.next()){
                strContador = String.valueOf(contador);
                data.put(strContador, new Object[] { rset.getString("RADICADO"), rset.getString("FECHA_RADICADO"), 
                                                        rset.getString("TIPO_RADICADO"), rset.getString("OBSERVACION") });
                contador++;
            }
                                   
            return data;
            
        }
        
        public void escribirExcelDagma(String url, String codigoProceso) throws SQLException{
            
            try{
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("Historial Dagma");

            //Obtenemos el mapa con la informacion
            Map datos = organizarInfoExcelDagma(codigoProceso);

            //Informacion de los titulos
            Map<String, Object[]> data = new HashMap<String, Object[]>();

            //Guardamos la cabecera del excel.
            data.put(String.valueOf(0), new Object[] {"RADICADO","FECHA RADICACION", "TIPO RADICACION", "OBSERVACION"});

            //Escribimos el titulo
            
            Font font = workbook.createFont();
            CellStyle style = workbook.createCellStyle();
            style.setFont(font);
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
            
            Set<String> keyset2 = data.keySet();
            for (String key : keyset2) {
                Row row = sheet.createRow(0);
                Object [] objArr = (Object [])data.get(key);
                int cellnum = 0;
                for (Object obj : objArr) {
                    
                    Cell cell = row.createCell(cellnum++);
                    cell.setCellStyle(style);
                    
                    if(obj instanceof Date) 
                        cell.setCellValue((Date)obj);
                    else if(obj instanceof Boolean)
                        cell.setCellValue((Boolean)obj);
                    else if(obj instanceof String)
                        cell.setCellValue((String)obj);
                    else if(obj instanceof Double)
                        cell.setCellValue((Double)obj);
                }
            }

            //Obtenemos la informacion del mapa e insertamos la informacion en el excel
            Set<String> keyset = datos.keySet();
            int rownum = 1;
            for (String key : keyset) {
                Row row = sheet.createRow(rownum++);
                Object [] objArr = (Object [])datos.get(key);
                int cellnum = 0;
                for (Object obj : objArr) {
                    Cell cell = row.createCell(cellnum++);
                    if(obj instanceof Date) 
                        cell.setCellValue((Date)obj);
                    else if(obj instanceof Boolean)
                        cell.setCellValue((Boolean)obj);
                    else if(obj instanceof String)
                        cell.setCellValue((String)obj);
                    else if(obj instanceof Double)
                        cell.setCellValue((Double)obj);
                }
            }

                //Guardamos el archivo en el disco.
                  FileOutputStream out = 
                    new FileOutputStream(new File(url));
                workbook.write(out);
                out.close();
            
            }catch(Exception e){
            
            }
        }
}
