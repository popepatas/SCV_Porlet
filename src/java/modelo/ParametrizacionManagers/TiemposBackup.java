/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.ParametrizacionManagers;

import control.ParametrizacionServlets.ActualizarTiemposBackup;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.ParametrizacionDelegates.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author illustrato
 */
public class TiemposBackup {
    
    public void actualizar(int valor, int codigo) throws Exception{
    
          ActualizarTiemposbackup update = new ActualizarTiemposbackup(valor, codigo);
          update.ejecutar();
    }
    
    
    
    
    /**
     * 
     * Obtiene la informacion de las unidades de medida y
     * guarda todo en un JSONArray para entregarselo a la vista.
     * 
     * @return JSONArray
     * @throws SQLException 
     */
    public JSONArray getTiemposBackup() throws SQLException{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarTiemposBackup select = new SeleccionarTiemposBackup();
        ResultSet rset = select.getTiemposBackup();
        
        //Creamos los JSONArray para guardar los objetos JSON
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos
        //en el JSONArray.
        while(rset.next()){
            
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("codigo", rset.getString("CODIGO"));
            jsonObject.put("tiempo", rset.getString("DIAS_BACKUP"));

            
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
    public JSONArray getTiempoBackup(int codigo) throws SQLException{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarTiemposBackup select = new SeleccionarTiemposBackup();
        ResultSet rset = select.getTiempoBackup(codigo);
        
        //Creamos los JSONArray para guardar los objetos JSON
        JSONArray jsonArray = new JSONArray();

        //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos
        //en el JSONArray.
        while(rset.next()){
            JSONObject jsonObject = new JSONObject();
            //Armamos el objeto JSON con la informacion del registro
            jsonObject.put("codigo", rset.getString("CODIGO"));
            jsonObject.put("tiempo", rset.getString("DIAS_BACKUP"));
            
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
            
        }
        
        select.desconectar();      
        return jsonArray;
        
    }
    //-----------------------------------------------------------------------------
    
}
