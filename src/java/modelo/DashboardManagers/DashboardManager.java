package modelo.DashboardManagers;

import java.sql.ResultSet;
import java.util.Iterator;
import modelo.DashboardDelegates.DashboardDelegate;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author jmrincon
 */
public class DashboardManager {
    
    public JSONArray getDashboard1(String codigoCliente, String rangoMetrosInicial, String rangoMetrosFinal) throws Exception{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        DashboardDelegate select = new DashboardDelegate(codigoCliente, rangoMetrosInicial, rangoMetrosFinal, null, null, null, null, null, null);
        ResultSet rset = select.ejecutarDashboard1();
        
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        
        while(rset.next()){
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            boolean agregar = true;
            if (jsonArray.size() == 0) {
                jsonObject.put("mesProceso", rset.getString("MESPROCESO"));
                jsonObject.put("nombLodo", rset.getString("TIPOLODO"));
                jsonObject.put("cantidadProcesos", rset.getInt("CANTIDAD"));
            }else{
                for (Iterator<JSONObject> it = jsonArray.iterator(); it.hasNext();) {
                    JSONObject jsonObject1 = (JSONObject)it.next();
                    if (jsonObject1.get("mesProceso").equals(rset.getString("MESPROCESO")) 
                            && jsonObject1.get(rset.getString("TIPOLODO")) != null 
                            && jsonObject1.get(rset.getString("TIPOLODO")).equals(rset.getString("TIPOLODO"))) {
                        jsonObject1.put("cantidadProcesos", rset.getInt("CANTIDAD"));
                        agregar = false;
                    }else{
                        jsonObject.put("mesProceso", rset.getString("MESPROCESO"));
                        jsonObject.put("nombLodo", rset.getString("TIPOLODO"));
                        jsonObject.put("cantidadProcesos", rset.getInt("CANTIDAD"));
                    }
                }
            }
            
            if (agregar) {
                //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
                jsonArray.add(jsonObject.clone());
            }
        }
        jsonArreglo.add(jsonArray);
        //Se cierra el ResultSet
        select.desconectar();
        return jsonArreglo;
    }
    
    public JSONArray getDashboard2(String codigoCliente, String paramFisicoQuimico, String rangoDesde, String rangoHasta) throws Exception{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        DashboardDelegate select = new DashboardDelegate(codigoCliente, null, null, rangoDesde, rangoHasta, paramFisicoQuimico, null, null, null);
        ResultSet rset = select.ejecutarDashboard2();
        
        Integer cantProcesos = 0;
        ResultSet rsetCant = select.ejecutarCantProcesosXCliente();
        while(rsetCant.next()){
            cantProcesos = rsetCant.getInt("cantidadProcesos");
        }
        
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        while(rset.next()){
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            boolean agregar = true;
            if (jsonArray.size() == 0) {
                jsonObject.put("mesProceso", rset.getString("MESPROCESO"));
                jsonObject.put("nombParametro", rset.getString("NOMBPARAMETRO"));
                jsonObject.put("cantidadProcesos", 1);
            }else{
                for (Iterator<JSONObject> it = jsonArray.iterator(); it.hasNext();) {
                    JSONObject jsonObject1 = (JSONObject)it.next();
                    if (jsonObject1.get("mesProceso").equals(rset.getString("MESPROCESO"))) {
                        jsonObject.put("cantidadProcesos", (Integer)jsonObject1.get("cantidadProcesos") + 1);
                        agregar = false;
                    }else{
                        jsonObject.put("mesProceso", rset.getString("MESPROCESO"));
                        jsonObject.put("nombParametro", rset.getString("NOMBPARAMETRO"));
                        jsonObject.put("cantidadProcesos", 1);
                    }
                }
            }
            
            if (agregar) {
                //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
                jsonArray.add(jsonObject.clone());
            }
        }
        
        for (Iterator<JSONObject> it = jsonArray.iterator(); it.hasNext();) {
            JSONObject jsonObject2 = (JSONObject)it.next();
            Integer porcProceso = ((Integer)jsonObject2.get("cantidadProcesos") * 100) / cantProcesos;
            jsonObject2.put("porcProceso", porcProceso);
        }
        
        jsonArreglo.add(jsonArray);
        //Se cierra el ResultSet
        select.desconectar();
        return jsonArreglo;
    }
    
    public JSONArray getDashboard3(String codigoCliente, String paramFisicoQuimico, String rangoDesde, String rangoHasta) throws Exception{
        //Ejecutamos la consulta y obtenemos el ResultSet
        DashboardDelegate select = new DashboardDelegate(codigoCliente, null, null, rangoDesde, rangoHasta, paramFisicoQuimico, null, null, null);
        ResultSet rset = select.ejecutarDashboard3();
        
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        while(rset.next()){
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            boolean agregar = true;
            if (jsonArray.size() == 0) {
                jsonObject.put("mesProceso", rset.getString("MESPROCESO"));
                jsonObject.put("nombParametro", rset.getString("NOMBPARAMETRO"));
                jsonObject.put("cantidadProcesos", 1);
            }else{
                for (Iterator<JSONObject> it = jsonArray.iterator(); it.hasNext();) {
                    JSONObject jsonObject1 = (JSONObject)it.next();
                    if (jsonObject1.get("mesProceso").equals(rset.getString("MESPROCESO"))) {
                        jsonObject.put("cantidadProcesos", (Integer)jsonObject1.get("cantidadProcesos") + 1);
                        agregar = false;
                    }else{
                        jsonObject.put("mesProceso", rset.getString("MESPROCESO"));
                        jsonObject.put("nombParametro", rset.getString("NOMBPARAMETRO"));
                        jsonObject.put("cantidadProcesos", 1);
                    }
                }
            }
            
            if (agregar) {
                //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
                jsonArray.add(jsonObject.clone());
            }
        }
        jsonArreglo.add(jsonArray);
        //Se cierra el ResultSet
        select.desconectar();
        return jsonArreglo;
    }
    
    public JSONArray getDashboard4(String numeroContrato, String codigoCliente, String paramFisicoQuimico, String fechaInicio, String fechaFin) throws Exception{
        //Ejecutamos la consulta y obtenemos el ResultSet
        DashboardDelegate select = new DashboardDelegate(codigoCliente, null, null, fechaInicio, fechaFin, paramFisicoQuimico, null, null, numeroContrato);
        ResultSet rset = select.ejecutarDashboard4();
        
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        while(rset.next()){
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            boolean agregar = true;
            if (jsonArray.size() == 0) {
                jsonObject.put("mesJornada", rset.getString("MESJORNADA"));
                jsonObject.put("nombParametro", rset.getString("NOMBPARAMETRO"));
                jsonObject.put("valorJornada", rset.getString("VALOR"));
            }else{
                for (Iterator<JSONObject> it = jsonArray.iterator(); it.hasNext();) {
                    JSONObject jsonObject1 = (JSONObject)it.next();
                    if (jsonObject1.get("mesJornada").equals(rset.getString("MESJORNADA"))) {
                        jsonObject.put("valorJornada", rset.getString("VALOR"));
                        agregar = false;
                    }else{
                        jsonObject.put("mesJornada", rset.getString("MESJORNADA"));
                        jsonObject.put("nombParametro", rset.getString("NOMBPARAMETRO"));
                        jsonObject.put("valorJornada", rset.getString("VALOR"));
                    }
                }
            }
            
            if (agregar) {
                //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
                jsonArray.add(jsonObject.clone());
            }
        }
        jsonArreglo.add(jsonArray);
        //Se cierra el ResultSet
        select.desconectar();
        return jsonArreglo;
    }
    
    public JSONArray getDashboard5(String codigoCliente, String rangoDBOInicial, String rangoDBOFinal, String fechaInicio, String fechafinal) throws Exception{
        //Ejecutamos la consulta y obtenemos el ResultSet
        DashboardDelegate select = new DashboardDelegate(codigoCliente, null, null, fechaInicio, fechafinal, null, rangoDBOInicial, rangoDBOFinal, null);
        ResultSet rset = select.ejecutarDashboard5();
        
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        while(rset.next()){
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            boolean agregar = true;
            if (jsonArray.size() == 0) {
                jsonObject.put("mesProceso", rset.getString("MESPROCESO"));
                jsonObject.put("nombParametro", rset.getString("NOMBPARAMETRO"));
                jsonObject.put("cantidadProcesos", 1);
            }else{
                for (Iterator<JSONObject> it = jsonArray.iterator(); it.hasNext();) {
                    JSONObject jsonObject1 = (JSONObject)it.next();
                    if (jsonObject1.get("mesProceso").equals(rset.getString("MESPROCESO"))) {
                        jsonObject.put("cantidadProcesos", (Integer)jsonObject1.get("cantidadProcesos") + 1);
                        agregar = false;
                    }else{
                        jsonObject.put("mesProceso", rset.getString("MESPROCESO"));
                        jsonObject.put("nombParametro", rset.getString("NOMBPARAMETRO"));
                        jsonObject.put("cantidadProcesos", 1);
                    }
                }
            }
            
            if (agregar) {
                //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
                jsonArray.add(jsonObject.clone());
            }
        }
        jsonArreglo.add(jsonArray);
        //Se cierra el ResultSet
        select.desconectar();
        return jsonArreglo;
    }
}
