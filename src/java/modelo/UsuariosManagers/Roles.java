/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.UsuariosManagers;

import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.UsuariosDelegates.SeleccionarRoles;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author illustrato
 */
public class Roles {
    
     public  JSONArray getRoles(String usuario, String rol, String codigo) throws SQLException{
        
        SeleccionarRoles select = new SeleccionarRoles();
        
        ResultSet rset =  select.getRoles(codigo, usuario);
        
        //Creamos los JSONArray para guardar los objetos JSON
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos en el JSONArray.
        while(rset.next()){
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("codigo", rset.getString("PK_CODIGO"));
            jsonObject.put("descripcion", rset.getString("VAR_ROL"));
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
        }
        
        select.desconectar();
        jsonArreglo.add(jsonArray);
        return jsonArreglo;
        
     
    }

   
}
