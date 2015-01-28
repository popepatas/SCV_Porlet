package modelo.ParametrizacionManagers;

import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.ParametrizacionDelegates.*;
import org.json.simple.*;

public class ActividadEconomica {


    
    /**
     * 
     * Llama al delegate para insertar la actividad economica.
     * 
     * @param descripcion
     * @param ciiu
     * @throws Exception 
     */
    public JSONObject insertar(String descripcion, int ciiu) throws Exception{
           
        JSONObject jsonObject = new JSONObject(); 
        Integer respError ;
        
        InsertarActEconomica insert = new InsertarActEconomica(descripcion,ciiu);
        insert.ejecutar();
        
        respError = insert.getError();
        
        jsonObject.put("error", respError);       
     
        
        return jsonObject;
            
    }
    //-----------------------------------------------------------------------------
    
    
    
    
    /**
     * 
     * Llama al delegate para actualizar la actividad economica.
     * 
     * @param codigo
     * @param descripcion
     * @param ciiu
     * @throws Exception 
     */
    public JSONObject actualizar(int codigo, String descripcion, int ciiu) throws Exception{
        
        
        JSONObject jsonObject = new JSONObject(); 
        Integer respError ;
        
        ActualizarActEconomica update = new ActualizarActEconomica(descripcion, codigo, ciiu);
        update.ejecutar();
        
        
        respError = update.getError();
        
        jsonObject.put("error", respError);       
     
        
        return jsonObject;
        
    }
    //-----------------------------------------------------------------------------
    
    
     /**
     * 
     * Llama al delegate para Eliminar una actividad economica.
     * 
     * @param codigo     
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
     * Obtiene la informacion de las actividades economicas y
     * guarda todo en un JSONArray para entregarselo a la vista.
     * 
     * @return JSONArray
     * @throws SQLException 
     */
    public JSONArray getActEconomicas(String ciiu, String descripcion) throws SQLException{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarActEconomica select = new SeleccionarActEconomica();
        ResultSet rset = select.getActEconomicas(ciiu, descripcion);
        
        //Creamos los JSONArray para guardar los objetos JSON
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos
        //en el JSONArray.
        while(rset.next()){
            
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("codigo", rset.getString("CODIGO"));
            jsonObject.put("descripcion2", rset.getString("DESCRIPCION"));
            jsonObject.put("codigo_ciiu",rset.getString("CODIGO_CIIU"));
            jsonObject.put("descripcion",rset.getString("CODIGO_CIIU") +" / "+rset.getString("DESCRIPCION") );
            
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
     * Obtiene la informacion de las actividades economicas y
     * guarda todo en un JSONArray para entregarselo a la vista.
     * 
     * @return JSONArray
     * @throws SQLException 
     */
    public JSONArray getActEconomica(int codigo) throws SQLException{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarActEconomica select = new SeleccionarActEconomica();
        ResultSet rset = select.getActEconomica(codigo);
        
        //Creamos los JSONArray para guardar los objetos JSON
        JSONArray jsonArray = new JSONArray();

        //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos
        //en el JSONArray.
        while(rset.next()){
            
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("codigo", rset.getString("CODIGO"));
            jsonObject.put("descripcion", rset.getString("DESCRIPCION"));
            jsonObject.put("codigo_ciiu",rset.getString("CODIGO_CIIU"));
            
            
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
            
        }
                
        select.desconectar();
              
        return jsonArray;
        
    }
    //-----------------------------------------------------------------------------
    
}