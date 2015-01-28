package modelo.ParametrizacionManagers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import modelo.ParametrizacionDelegates.*;
import org.json.simple.*;

public class AsociacionContratos {


    
    /**
     * 
     * Llama al delegate para insertar una unidad de medida
     * 
     * @param descripcion
     * @throws Exception 
     */
    public int insertar(Double contratoPadre, Double contratoHijo) throws Exception{
                
        InsertarAsociacionContratos insert = new InsertarAsociacionContratos(contratoPadre, contratoHijo);
        insert.ejecutar();
        
        int error = insert.getError();
        
        return error;
            
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
    public int actualizar(Double contratoPadre, Double contratoHijo, String codigo) throws Exception{
        
        ActualizarAsociacionContrato update = new ActualizarAsociacionContrato(contratoPadre, contratoHijo, codigo);
        
        update.ejecutar();
        
        int error = update.getError();
        
        return error;
        
    }
    //-----------------------------------------------------------------------------
    
    
     /**
     * 
     * Llama al delegate para Eliminar Asociación contrato
     * 
     * @param codigo     
     * @throws Exception 
     */
    public JSONArray Eliminar(Double codigo) throws Exception{
        
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject(); 
        
        Integer respError ;
        
        EliminarAsociacionContrato delete = new EliminarAsociacionContrato(codigo);
        delete.ejecutar();
        
        respError = delete.getError();
        
        jsonObject.put("error", respError);
        
        jsonArray.add(jsonObject);
        
        return jsonArray;
        
        
    }
    //-----------------------------------------------------------------------------
    
    
     /**
     * 
     * Llama al delegate para Eliminar Asociación contrato
     * 
     * @param codigo     
     * @throws Exception 
     */
    public JSONArray EliminarContratoAsociado(int codigo) throws Exception{
        
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject(); 
        
        Integer respError ;
        
        EliminarContratoAsociado delete = new EliminarContratoAsociado(codigo);
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
    public JSONArray getAsociaciones(String contrato) throws SQLException{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarAsociacionContratos select = new SeleccionarAsociacionContratos();
        ResultSet rset = select.getAsociaciones(contrato);
        
        //Creamos los JSONArray para guardar los objetos JSON
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos
        //en el JSONArray.
        while(rset.next()){
            
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("contrato", rset.getString("CONTRATO"));
            jsonObject.put("asociaciones", rset.getString("ASOCIACIONES"));
            
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
     * @return JSONArray
     * @throws SQLException 
     */
    public JSONObject getAsociacion(int contrato) throws SQLException{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarAsociacionContratos select = new SeleccionarAsociacionContratos();
        ResultSet rset = select.getAsociacion(contrato);
        
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

        
        //Recorremos los registros obtenidos de la consulta
        while(rset.next()){
            
            //Si la bandera es false, armamos la cabecera.
            if(!flag){
                
                contrato = rset.getString("CONTRATO_PADRE");
                jsonObject1.put("contrato",contrato);
                flag = true;
                
            }
                
            String contratoHijo = rset.getString("CONTRATO_HIJO");
            String codigo = rset.getString("CODIGO");
            
            JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("contratoAsignado", contratoHijo);
            jsonObject2.put("codigo", codigo);
            
            jsonArray1.add(jsonObject2);
                   
        }
                
        
        jsonObject1.put("contratos",jsonArray1);

        
        return jsonObject1;
        
    }    
    
}