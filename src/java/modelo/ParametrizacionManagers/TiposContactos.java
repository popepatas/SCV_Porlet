/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.ParametrizacionManagers;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.ParametrizacionDelegates.*;
import org.json.simple.*;
/**
 *
 * @author illustrato
 */
public class TiposContactos {
    
    
      
    /**
     * 
     * Llama al delegate para insertar un Tipo de contacto
     * 
     * @param descripcion
     * @throws Exception 
     */
    public void insertar(String descripcion) throws Exception{
                
            
            InsertarTiposContactos  insert = new InsertarTiposContactos(descripcion);
            
            insert.ejecutar();
    }
    
    
        
    /**
     * 
     * Llama al delegate para insertar un Tipo de contacto
     * 
     * @param descripcion
     * @throws Exception 
     */
    public void actualizar(String descripcion, int codigo) throws Exception{
                
            
            ActualizarTiposContactos  update = new ActualizarTiposContactos(codigo, descripcion);
            
            update.ejecutar();
    }
     
     /**
     * 
     * Llama al delegate para Eliminar una tipo de contacto
     * @param codigo     
     * @throws Exception 
     */
    
    public  JSONArray eliminar(int codigo) throws Exception{
    
    
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject(); 
        
        Integer respError ;
        
        
        EliminarTiposContactos delete = new EliminarTiposContactos(codigo);
        
        delete.ejecutar();
        
        respError = delete.getError();
        
        jsonObject.put("error", respError);
        
        jsonArray.add(jsonObject);
    
        return jsonArray;
    }
    
     /**
     * SELECT
     * Obtiene la informacion de una unidad de medida y
     * guarda todo en un JSONArray para entregarselo a la vista.
     * 
     * @return JSONArray
     * @throws SQLException 
     */
    
      public JSONArray getTiposContactos(String descripcion) throws Exception{
      
          //Ejecutamos la consulta y obtenemos el ResultSet
            SeleccionarTiposContactos seleccionar = new SeleccionarTiposContactos();                     
            ResultSet rst = seleccionar.getTiposContactos(descripcion);
            
          //Creamos los JSONArray para guardar los objetos JSON
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArreglo = new JSONArray();
            
            while(rst.next()){
                //Creamos el objecto JSON
                jsonObject.put("descripcion", rst.getString("DESCRIPCION"));
                jsonObject.put("codigo", rst.getString("CODIGO"));
                
                //Creamos el Array JSON
                jsonArray.add(jsonObject.clone());                
                
            }
            
              jsonArreglo.add(jsonArray);
            seleccionar.desconectar();
            return jsonArreglo;
      
      }

       /**
     * 
     * Obtiene la informacion del tipo de contacto mediante un codigo y
     * guarda todo en un JSONArray para entregarselo a la vista.
     * 
     * @return JSONArray
     * @throws SQLException 
     */
    
      public JSONArray getTipoContacto(int codigo) throws Exception{
      
          //Ejecutamos la consulta y obtenemos el ResultSet
            SeleccionarTiposContactos seleccionar = new SeleccionarTiposContactos();                     
            ResultSet rst = seleccionar.getTipoContacto(codigo);
            
          //Creamos los JSONArray para guardar los objetos JSON
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            
            
            while(rst.next()){
                //Creamos el objecto JSON
                jsonObject.put("descripcion", rst.getString("DESCRIPCION"));
                jsonObject.put("codigo",rst.getString("CODIGO"));
                
                //Creamos el Array JSON
                jsonArray.add(jsonObject.clone());                
                
            }
            seleccionar.desconectar();
            return jsonArray;
      
      }
    
}
