/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.AutenticacionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.AutenticacionDelegate.InsertarPermisos;
import modelo.AutenticacionDelegate.SeleccionarPermisos;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author illustrato
 */
public class PermisosAcceso {
    
    
    public JSONObject insertarPermisos(int codigo_rol, int codigo_pantalla, String valor) throws Exception{
        
        InsertarPermisos insert = new InsertarPermisos(codigo_rol, codigo_pantalla, valor);
        
        insert.ejecutar();
        
        Integer respuesta = insert.getResultado();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("resp", respuesta);
        
        
        return jsonObject;
        
    }
    
    public JSONArray SeleccionarPermisos(String rol) throws SQLException{
        
         
        JSONArray  permisos = new JSONArray();
        JSONArray  ArrayPantallas = new JSONArray();
        JSONObject modulos = new JSONObject();
        JSONObject pantallas = new JSONObject();
        Boolean flag = true;
        String modulo = "";
        String codigo_modulo = "";
        SeleccionarPermisos select = new SeleccionarPermisos();        
         
        ResultSet result = select.getModulos();
         
         while(result.next()){             
             
                modulo = result.getString("VAR_MODULO");
                codigo_modulo = result.getString("PK_CODIGO");
                modulos.put("modulo", modulo);                     

                ResultSet rstPantallas = select.getPantallas(rol, codigo_modulo);

                while(rstPantallas.next()){

                    pantallas.put("codigo", rstPantallas.getString("PK_CODIGO"));
                    pantallas.put("jsp", rstPantallas.getString("VAR_PANTALLA"));
                    pantallas.put("pantalla", rstPantallas.getString("VAR_DESCRIPCION"));                 
                    pantallas.put("existe", rstPantallas.getString("EXISTE"));        

                    ArrayPantallas.add(pantallas.clone());
                    pantallas.clear();
                }

                modulos.put("pantallas",ArrayPantallas.clone());
                ArrayPantallas.clear();
                permisos.add(modulos.clone());
         }
         
        select.desconectar();
    return permisos;     
    }
}

