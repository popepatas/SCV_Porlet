/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.ParametrizacionManagers;

import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.ParametrizacionDelegates.SeleccionarTipoVisita;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author illustrato
 */
public class TipoVisita {
    
    public JSONArray getTiposVisitas() throws SQLException{
    //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarTipoVisita select = new SeleccionarTipoVisita();
        ResultSet rset = select.getTiposVisitas();
        
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
    
}
