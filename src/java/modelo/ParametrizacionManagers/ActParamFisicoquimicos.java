package modelo.ParametrizacionManagers;

import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.ParametrizacionDelegates.*;
import org.json.simple.*;

public class ActParamFisicoquimicos {


    
    /**
     * 
     * Llama al delegate para Insertar una actividad parametro fisicoquimicos
     * 
     * @param actividadEconomica
     * @param paramFisicoquimicos
     * @param rangoInicial
     * @param rangoFinal
     * @param mayorIgualRangoInicial
     * @param mayorIgualRangoFinal
     * @throws Exception 
     */
    public JSONObject insertar(int actividadEconomica, int paramFisicoquimicos, Double rangoInicial, Double rangoFinal,
                String mayorIgualRangoInicial, String mayorIgualRangoFinal,String mostrarRango) throws Exception{
                
        InsertarActParamfisicoquimicos insert = new InsertarActParamfisicoquimicos(actividadEconomica, paramFisicoquimicos, rangoInicial, 
                rangoFinal, mayorIgualRangoInicial, mayorIgualRangoFinal, mostrarRango);
        insert.ejecutar();
        
        JSONObject jsonObject = new JSONObject(); 
        Integer respError ;
        respError = insert.getError();
        
        jsonObject.put("error", respError);       
     
        
        return jsonObject;
            
    }
    //-----------------------------------------------------------------------------
    
    
    
    
    /**
     * 
     * Llama al delegate para actualizar una actividad parametro fisicoquimicos
     * 
     * @param actividadEconomica
     * @param paramFisicoquimicos
     * @param rangoInicial
     * @param rangoFinal
     * @param mayorIgualRangoInicial
     * @param mayorIgualRangoFinal 
     * @throws Exception 
     */
    public JSONObject actualizar(int codigo, int actividadEconomica, int paramFisicoquimicos, Double rangoInicial, 
                Double rangoFinal, String mayorIgualRangoInicial, String mayorIgualRangoFinal,String mostrarRango) throws Exception{
        
        ActualizarActParamfisicoquimicos update = new ActualizarActParamfisicoquimicos(codigo,actividadEconomica, paramFisicoquimicos, rangoInicial, 
                rangoFinal, mayorIgualRangoInicial, mayorIgualRangoFinal, mostrarRango);
        update.ejecutar();
        
         JSONObject jsonObject = new JSONObject(); 
        Integer respError ;
        respError = update.getError();
        
        jsonObject.put("error", respError);       
     
        
        return jsonObject;
        
    }
    //-----------------------------------------------------------------------------
    
    
    /**
     * 
     * Llama al Delegate para eliminar un parametro fisicoquimicos
     * 
     * @param codigo
     * @return
     * @throws Exception 
     */
    public JSONArray Eliminar(int codigo) throws Exception{
        
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject(); 
        
        Integer respError ;
        
        EliminarActEconomica delete = new EliminarActEconomica(codigo);
        delete.ejecutar();
        
        respError = delete.getError();
        
        jsonObject.put("error", respError);
        
        jsonArray.add(jsonObject);
        
        return jsonArray;
        
        
    }
    //-----------------------------------------------------------------------------
    
    
    
    /**
     * 
     * Llama al delegate para obtener las actividades parametros
     * fisicoquimicos
     * 
     * @return
     * @throws SQLException 
     */
    public JSONArray getActParamFisicoquimicos(String actividad, String parametro,String rangoInicial, String rangoFinal,
                                                String mayorRangoInicial, String mayorRangoFinal, String mostrarRango) throws SQLException{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarActParamfisicoquimicos select = new SeleccionarActParamfisicoquimicos();
        ResultSet rset = select.getActParamFisicoquimicos(actividad, parametro, rangoInicial, rangoFinal, mayorRangoInicial, mayorRangoFinal,mostrarRango);
        
        //Creamos los JSONArray para guardar los objetos JSON
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos
        //en el JSONArray.
        
          
        while(rset.next()){
            
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("actividad", rset.getString("ACTDESP"));
            jsonObject.put("parametro", rset.getString("PARAMFIQUI"));
            jsonObject.put("rangoInicial",Double.parseDouble(rset.getString("RANGOINICIAL")));
            jsonObject.put("rangoFinal", Double.parseDouble(rset.getString("RANGOFINAL")));
            jsonObject.put("mayorFinal", rset.getString("MAYORFINAL"));
            jsonObject.put("mayorInicial",rset.getString("MAYORINICIAL"));
            jsonObject.put("codActividad",rset.getString("ACTCOD"));
            jsonObject.put("codParametro",rset.getString("PARMCOD"));
            jsonObject.put("codCiiu",rset.getString("CODCIIU"));
            jsonObject.put("descripcion",rset.getString("ACTDESP") +" ("+rset.getString("CODCIIU")+")");
            jsonObject.put("mostrarRango",rset.getString("MOSTRAR_RANGO"));
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
    * Llama al delegate para obtener una actividad parametro fisicoquimico
    * 
    * @param codigoActividad
    * @param codigoParam
    * @return
    * @throws SQLException 
    */
    public JSONArray getActParamFisicoquimico(Integer codigoActividad, Integer codigoParam, String rangoInicial, String rangoFinal, String mayorInicial, String mayorFinal, String mostrarRango) throws SQLException{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarActParamfisicoquimicos select = new SeleccionarActParamfisicoquimicos();
        ResultSet rset = select.getActParamFisicoquimicos( codigoActividad,  codigoParam, rangoInicial, rangoFinal, mayorInicial, mayorFinal,mostrarRango);
        
        //Creamos los JSONArray para guardar los objetos JSON
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        
        //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos
        //en el JSONArray.
        while(rset.next()){
            
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("codigo", rset.getString("COD_ACTPARM"));
            jsonObject.put("actividad", rset.getString("ACTDESP"));
            jsonObject.put("parametro", rset.getString("PARAMFIQUI"));
            jsonObject.put("rangoInicial", Double.parseDouble(rset.getString("RANGOINICIAL")));
            jsonObject.put("rangoFinal", Double.parseDouble(rset.getString("RANGOFINAL")));
            jsonObject.put("mayorFinal", rset.getString("MAYORFINAL"));
            jsonObject.put("mayorInicial",rset.getString("MAYORINICIAL"));
            jsonObject.put("codActividad",rset.getString("ACTCOD"));
            jsonObject.put("codParametro",rset.getString("PARMCOD"));
            jsonObject.put("codCiiu",rset.getString("CODCIIU"));
            jsonObject.put("descripcion",rset.getString("ACTDESP") +" ("+rset.getString("CODCIIU")+")");
            jsonObject.put("mostrarRango",rset.getString("MOSTRAR_RANGO"));
            
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
            
        }
                
        select.desconectar();
        
        jsonArreglo.add(jsonArray);     
        return jsonArreglo;
        
    }
    //-----------------------------------------------------------------------------

    public JSONObject Eliminar(int codigoAct, int codigoParam) throws Exception {
       
         
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject(); 
        
        Integer respError ;
        
        EliminarActParamfisicoquimicos delete = new EliminarActParamfisicoquimicos(codigoAct,codigoParam);
        delete.ejecutar();
        
        respError = delete.getError();
        
        jsonObject.put("error", respError);
        
        
        
        return jsonObject;
               
        
    }
    
}