package modelo.ParametrizacionManagers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import modelo.ParametrizacionDelegates.*;
import org.json.simple.*;

public class PuntosVertimiento {


    
    /**
     * 
     * Llama al delegate para insertar una unidad de medida
     * 
     * @param descripcion
     * @throws Exception 
     */
    public void insertar(String ubicacion, String latitud, String longitud, 
            String observacion, int estado, Double contrato, String tipoEstructura) throws Exception{
                
        InsertarPuntoVertimiento insert = new InsertarPuntoVertimiento(ubicacion, latitud, longitud, 
                                                                                observacion, estado, contrato, tipoEstructura);
        
        insert.ejecutar();
            
    }
    //-----------------------------------------------------------------------------
    
    
    
    
    /**
     * 
     * Llama al delegate para actualizar una unidad de medida
     * 
     * @param codigo
     * @param descripcion
     * @throws Exception 
     */
    public void actualizar(String codigo, String descripcion, String latitud, String longitud, 
            String observacion, int estado, Double contrato, String tipoEstructura) throws Exception{
        
        ActualizarPuntoVertimiento update = new ActualizarPuntoVertimiento( codigo,  descripcion,  latitud,  longitud, 
             observacion,  estado,  contrato, tipoEstructura);
        
        update.ejecutar();
        
    }
    //-----------------------------------------------------------------------------
    
    
     /**
     * 
     * Llama al delegate para Eliminar una unidad de medida.
     * 
     * @param codigo     
     * @throws Exception 
     */
    public JSONArray Eliminar(int codigo) throws Exception{
        
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject(); 
        
        Integer respError ;
        
        EliminarPuntoVertimiento delete = new EliminarPuntoVertimiento(codigo);
        delete.ejecutar();
        
        respError = delete.getError();
        
        jsonObject.put("error", respError);
        
        jsonArray.add(jsonObject);
        
        return jsonArray;
        
        
    }
    //-----------------------------------------------------------------------------
    
    
    /**
     * 
     * Obtiene la informacion de las unidades de medida y
     * guarda todo en un JSONArray para entregarselo a la vista.
     * 
     * @return JSONArray
     * @throws SQLException 
     */
    public JSONArray getPuntosVertimiento(String contrato) throws SQLException{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarPuntosVertimiento select = new SeleccionarPuntosVertimiento();
        ResultSet rset = select.getPuntosVertimiento(contrato);
        
        //Creamos los JSONArray para guardar los objetos JSON
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos
        //en el JSONArray.
        while(rset.next()){
            
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("contrato", rset.getString("CONTRATO"));
            jsonObject.put("puntos", rset.getString("PUNTOS_ASOCIADOS"));
            
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
            
        }
        select.desconectar();
        jsonArreglo.add(jsonArray);
        
        return jsonArreglo;
        
    }
    //-----------------------------------------------------------------------------
    
    
    
    
     
   /**
     * 
     * Obtiene la informacion de una unidad de medida y
     * guarda todo en un JSONArray para entregarselo a la vista.
     * 
     * @param codigo
     * @return JSONArray
     * @throws SQLException 
     */
    public JSONObject getPuntoVertimiento(String codigo) throws SQLException{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarPuntosVertimiento select = new SeleccionarPuntosVertimiento();
        ResultSet rset = select.getPuntoVertimiento(codigo);
        
        //Obtenemos el JSONString
        JSONObject jsonArray = obtenerJSONString(rset);
        select.desconectar();
              
        return jsonArray;
        
    }
    //-----------------------------------------------------------------------------
    
    
    /**
     * 
     * Arma el la cadena JSON que seria enviada a la vista.
     * 
     * @param rset
     * @return String
     * @throws SQLException
     */
    private JSONObject obtenerJSONString(ResultSet rset) throws SQLException{
      
        //Variables necesarias
        boolean flag = false;
        String contrato = null;
        JSONArray jsonArray1 = new JSONArray();
        JSONObject jsonObject1 = new JSONObject();
        int cont = 0;

        
        //Recorremos los registros obtenidos de la consulta
        while(rset.next()){
            
            //Si la bandera es false, armamos la cabecera.
            if(!flag){
                
                contrato = rset.getString("FK_CONTRATO");
                jsonObject1.put("contrato",contrato);
                flag = true;
                
            }
                
            String ubicacion = rset.getString("DESCRIPCION");
            String latitud = rset.getString("LATITUD");
            String longitud = rset.getString("LONGITUD");
            String estado = rset.getString("FK_ESTADO");
            String observacion = rset.getString("OBSERVACION");
            String codigo = rset.getString("CODIGO");
            String tipoEstructura = rset.getString("TIPO_ESTRUCTURA");
            
            JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("ubicacion", ubicacion);
            jsonObject2.put("latitud", latitud);
            jsonObject2.put("longitud", longitud);
            jsonObject2.put("observacion", observacion);
            jsonObject2.put("estado", estado);
            jsonObject2.put("codigo", codigo);
            jsonObject2.put("tipoEstructura", tipoEstructura);
            
            jsonArray1.add(jsonObject2);
            
            cont++;
        }
        if(cont > 0){
            jsonObject1.put("puntos",jsonArray1);
            
        }else{
            jsonObject1.put("error",2);
        }

        
        return jsonObject1;
        
    }    
    
    
    /**
     * 
     * Obtiene el ResultSet con los puntos de vertimiento
     * para mostrar en la pantalla de Informacion Tecnica.
     * 
     * @param proceso
     * @return
     * @throws Exception 
     */
    public Object[] getPuntosParaInfoTecnica(int proceso) throws Exception{
    
        SeleccionarPuntosVertimiento select = new SeleccionarPuntosVertimiento();
        ResultSet rset = select.getPuntosParaInfoTecnica(proceso);
        
        Object data[] = new Object[2];
        data[0] = rset;
        data[1] = select;
        
        return data;
        
    }
}