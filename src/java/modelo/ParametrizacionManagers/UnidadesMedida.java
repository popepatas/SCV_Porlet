package modelo.ParametrizacionManagers;

import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.ParametrizacionDelegates.*;
import org.json.simple.*;

public class UnidadesMedida {


    
    /**
     * 
     * Llama al delegate para insertar una unidad de medida
     * 
     * @param descripcion
     * @throws Exception 
     */
    public JSONObject insertar(String descripcion) throws Exception{
                
        InsertarUnidadesMedida insert = new InsertarUnidadesMedida(descripcion);
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
     * Llama al delegate para actualizar una unidad de medida
     * 
     * @param codigo
     * @param descripcion
     * @throws Exception 
     */
    public JSONObject actualizar(int codigo, String descripcion) throws Exception{
        
        ActualizarUnidadesMedida update = new ActualizarUnidadesMedida(codigo, descripcion);
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
     * Llama al delegate para Eliminar una unidad de medida.
     * 
     * @param codigo     
     * @throws Exception 
     */
    public JSONArray Eliminar(int codigo) throws Exception{
        
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject(); 
        
        Integer respError ;
        
        EliminarUnidadesMedida delete = new EliminarUnidadesMedida(codigo);
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
    public JSONArray getUnidadesMedida(String descripcion) throws SQLException{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarUnidadesMedida select = new SeleccionarUnidadesMedida();
        ResultSet rset = select.getUnidadesMedida(descripcion);
        
        //Creamos los JSONArray para guardar los objetos JSON
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos
        //en el JSONArray.
        while(rset.next()){
            
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("codigo", rset.getString("CODIGO"));
            jsonObject.put("descripcion", rset.getString("DESCRIPCION"));
            
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
            
        }
        
        jsonArreglo.add(jsonArray);
        select.desconectar();
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
    public JSONArray getUnidadMedida(int codigo) throws SQLException{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarUnidadesMedida select = new SeleccionarUnidadesMedida();
        ResultSet rset = select.getUnidadMedida(codigo);
        
        //Creamos los JSONArray para guardar los objetos JSON
        JSONArray jsonArray = new JSONArray();

        //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos
        //en el JSONArray.
        while(rset.next()){
            
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("codigo", rset.getString("CODIGO"));
            jsonObject.put("descripcion", rset.getString("DESCRIPCION"));
            
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
            
        }
        
        select.desconectar();      
        return jsonArray;
        
    }
    //-----------------------------------------------------------------------------
    
}