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
public class TiposInformeVertimientos {
    
    
      
    /**
     * 
     * Llama al delegate para insertar un Tipo de contacto
     * 
     * @param descripcion
     * @throws Exception 
     */
    public void insertar(String descripcion) throws Exception{
                
            
            InsertarTiposInformeVertimientos  insert = new InsertarTiposInformeVertimientos(descripcion);
            
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
                
            
            ActualizarTiposInformesVertimientos  actualizar = new ActualizarTiposInformesVertimientos(codigo, descripcion);
            
            actualizar.ejecutar();
    }
    
    
    
            /**
     * 
     * Llama al delegate para Eliminar un Laboratorio
     * 
     * @param codigo     
     * @throws Exception 
     */
    public JSONArray Eliminar(int codigo) throws Exception{
        
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject(); 
        
        Integer respError ;
        
        EliminarTiposInformesVertimientos delete = new EliminarTiposInformesVertimientos(codigo);
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
    
      public JSONArray getTiposInformes(String descripcion) throws Exception{
      
          //Ejecutamos la consulta y obtenemos el ResultSet
            SeleccionarTiposInformesVertimiento seleccionar = new SeleccionarTiposInformesVertimiento();                     
            ResultSet rst = seleccionar.getTiposInformes(descripcion);
            
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
     * Obtiene la informacion del tipo de Informe mediante un codigo y
     * guarda todo en un JSONArray para entregarselo a la vista.
     * 
     * @return JSONArray
     * @throws SQLException 
     */
    
      public JSONArray getTipoInforme(int codigo) throws Exception{
      
          //Ejecutamos la consulta y obtenemos el ResultSet
            SeleccionarTiposInformesVertimiento seleccionar = new SeleccionarTiposInformesVertimiento();                     
            ResultSet rst = seleccionar.getTipoInforme(codigo);
            
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
    
      
         /**
     * 
     * Obtiene la informacion del tipo de Informe de caracterización  y Proceso Seco
     * guarda todo en un JSONArray para entregarselo a la vista.
     * 
     * @return JSONArray
     * @throws SQLException 
     */
    
      public JSONArray getTipoInformesPrincial() throws Exception{
      
          //Ejecutamos la consulta y obtenemos el ResultSet
            SeleccionarTiposInformesVertimiento seleccionar = new SeleccionarTiposInformesVertimiento();                     
            ResultSet rst = seleccionar.getTipoInformesPrincipal();
            
            
          //Creamos los JSONArray para guardar los objetos JSON
            JSONArray jsonArreglo = new  JSONArray();
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            
            
            while(rst.next()){
                //Creamos el objecto JSON
                jsonObject.put("descripcion", rst.getString("DESCRIPCION"));
                jsonObject.put("codigo",rst.getString("CODIGO"));
                
                //Creamos el Array JSON
                jsonArray.add(jsonObject.clone());                
                
            }
            jsonArreglo.add(jsonArray);
              
            seleccionar.desconectar();
            return jsonArreglo;
      
      }
    
      
      
}
