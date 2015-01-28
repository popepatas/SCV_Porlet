package modelo.ParametrizacionManagers;

import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.ParametrizacionDelegates.*;
import org.json.simple.*;

public class EstadosProceso {



    
    
    
    
    /**
     * 
     * Llama al delegate para actualizar una unidad de medida
     * 
     * @param codigo
     * @param descripcion
     * @throws Exception 
     */
    public void actualizar(int codigo, String descripcion) throws Exception{
        
        ActualizarEstadosProceso update = new ActualizarEstadosProceso(codigo, descripcion);
        update.ejecutar();
        
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
    public JSONArray getEstados(String descripcion) throws SQLException{
    
        
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarEstadosProceso select = new SeleccionarEstadosProceso();
        ResultSet rset = select.getEstados(descripcion);
        
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
            jsonObject.put("abreviatura", rset.getString("ABREVIATURA"));
            
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
    public JSONArray getEstado(int codigo) throws SQLException{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarEstadosProceso select = new SeleccionarEstadosProceso();
        ResultSet rset = select.getEstado(codigo);
        
        //Creamos los JSONArray para guardar los objetos JSON
        JSONArray jsonArray = new JSONArray();

        //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos
        //en el JSONArray.
        while(rset.next()){
            
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("codigo", rset.getString("CODIGO"));
            jsonObject.put("descripcion", rset.getString("DESCRIPCION"));
            jsonObject.put("abreviatura", rset.getString("ABREVIATURA"));
            
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
            
        }
        
        select.desconectar();
      
        return jsonArray;
        
    }
    //-----------------------------------------------------------------------------
    
}