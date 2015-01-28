package modelo.ParametrizacionManagers;

import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.ParametrizacionDelegates.*;
import org.json.simple.*;

public class TiposPrmfisicoquimicos {


    
    /**
     * 
     * Llama al delegate para insertar un tipo de parametro fisicoquimico
     * 
     * @param descripcion
     * @throws Exception 
     */
    public void insertar(String descripcion) throws Exception{
                
        InsertarTiposPrmfisicoquimicos insert = new InsertarTiposPrmfisicoquimicos(descripcion);
        insert.ejecutar();
            
    }
    //-----------------------------------------------------------------------------
    
    
    
    
    /**
     * 
     * Llama al delegate para actualizar un tipo de parametro fisicoquimico
     * 
     * @param codigo
     * @param descripcion
     * @throws Exception 
     */
    public void actualizar(int codigo, String descripcion) throws Exception{
        
        ActualizarTiposPrmfisicoquimicos update = new ActualizarTiposPrmfisicoquimicos(codigo, descripcion);
        update.ejecutar();
        
    }
    //-----------------------------------------------------------------------------
    
    
     /**
     * 
     * Llama al delegate para Eliminar un tipo de parametro fisicoquimico.
     * 
     * @param codigo     
     * @throws Exception 
     */
    public JSONArray Eliminar(int codigo) throws Exception{
        
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject(); 
        
        Integer respError ;
        
        EliminarTiposPrmfisicoquimicos delete = new EliminarTiposPrmfisicoquimicos(codigo);
        delete.ejecutar();
        
        respError = delete.getError();
        
        jsonObject.put("error", respError);
        
        jsonArray.add(jsonObject);
        
        return jsonArray;
        
        
    }
    //-----------------------------------------------------------------------------
    
    
    /**
     * 
     * Llama al delegate para obtener los tipos parametros fisicoquimicos
     * 
     * @return
     * @throws SQLException 
     */
    public JSONArray getTiposPrmfisicoquimicos(String descripcion) throws SQLException{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarTiposPrmfisicoquimicos select = new SeleccionarTiposPrmfisicoquimicos();
        ResultSet rset = select.getTiposPrmfisicoquimicos(descripcion);
        
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
    * Llama al delegate para obtener un tipo de parametro fisicoquimico
    * 
    * @param codigo
    * @return
    * @throws SQLException 
    */
    public JSONArray getTipoPrmfisicoquimico(int codigo) throws SQLException{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarTiposPrmfisicoquimicos select = new SeleccionarTiposPrmfisicoquimicos();
        ResultSet rset = select.getTipoPrmfisicoquimico(codigo);
        
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