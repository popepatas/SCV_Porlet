/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.ParametrizacionManagers;

import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.ParametrizacionDelegates.ActualizarTarifas;
import modelo.ParametrizacionDelegates.SeleccionarTarifas;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author illustrato
 */
public class Tarifas {
    
    public void actualizar(int valor, int codigo) throws Exception{
    
          ActualizarTarifas update = new ActualizarTarifas(valor, codigo);
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
    public JSONArray getTarifas() throws SQLException{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarTarifas select = new SeleccionarTarifas();
        ResultSet rset = select.getTarifas();
        
        //Creamos los JSONArray para guardar los objetos JSON
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos
        //en el JSONArray.
        while(rset.next()){
            
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("codigo", rset.getString("CODIGO"));
            jsonObject.put("valor", rset.getString("VALOR"));
             jsonObject.put("codParm", rset.getString("FK_PARAMFISICOQUIMICO"));
            jsonObject.put("descpParm", rset.getString("DESPARM"));
            
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
    public JSONArray getTarifa(int codigo) throws SQLException{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarTarifas select = new SeleccionarTarifas();
        ResultSet rset = select.getTarifa(codigo);
        
        //Creamos los JSONArray para guardar los objetos JSON
        JSONArray jsonArray = new JSONArray();

        //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos
        //en el JSONArray.
        while(rset.next()){
            JSONObject jsonObject = new JSONObject();
            //Armamos el objeto JSON con la informacion del registro
            jsonObject.put("codigo", rset.getString("CODIGO"));
            jsonObject.put("valor", rset.getString("VALOR"));
            jsonObject.put("codParm", rset.getString("FK_PARAMFISICOQUIMICO"));
            jsonObject.put("descpParm", rset.getString("DESPARM"));
            
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
            
        }
        
        select.desconectar();
        return jsonArray;
        
    }
    //-----------------------------------------------------------------------------
    
}
