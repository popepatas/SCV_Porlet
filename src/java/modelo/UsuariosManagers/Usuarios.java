/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.UsuariosManagers;


import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.UsuariosDelegates.InsertarUsuarios;
import modelo.UsuariosDelegates.ActualizarUsuarios;
import modelo.UsuariosDelegates.SeleccionarUsuarios;
import modelo.UsuariosDelegates.ValidarPermisos;
import modelo.UsuariosDelegates.EliminarUsuario;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Nadesico
 */
public class Usuarios {
    
    
    public boolean verificarPermisos(String usuario, String pagina) throws Exception{
        
        ValidarPermisos validar = new ValidarPermisos(pagina, usuario);
        validar.ejecutar();
        int respuesta = validar.getRespuesta();
        if(respuesta > 0){
            return true;
        }
        
        return false;
    }
    
    public Integer insertarUsuarios(String usuario, Integer rol) throws Exception{
        
        InsertarUsuarios insert = new  InsertarUsuarios(usuario,rol);
        
         insert.ejecutar();
         
         return insert.getRespuesta();
        
        
    
    }
    
    public Integer actualizarUsuarios(String usuario, Integer rol, Integer codigo) throws Exception{
        
        
        ActualizarUsuarios update = new ActualizarUsuarios(usuario,rol,codigo);        
        update.ejecutar();
        
        return update.getRespuesta();
    
    }
     
    /**
     * 
     * Llama al delegate para Eliminar una actividad economica.
     * 
     * @param codigo     
     * @throws Exception 
     */
    public JSONObject Eliminar(int codigo) throws Exception{
        
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject(); 
        
        Integer respError ;
        
        EliminarUsuario delete = new EliminarUsuario(codigo);
        delete.ejecutar();
        
        respError = delete.getError();
        
        jsonObject.put("error", respError);
        
        
        
        return jsonObject;
        
        
    }
    public  JSONArray seleccionarUsuarios(String usuario, String rol, String codigo) throws SQLException{
        
        SeleccionarUsuarios select = new SeleccionarUsuarios();
        
        ResultSet rset =  select.getUsuarios(usuario, rol, codigo);
        
        //Creamos los JSONArray para guardar los objetos JSON
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos en el JSONArray.
        while(rset.next()){
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("codigo", rset.getInt("PK_CODIGO"));
            jsonObject.put("descripcion", rset.getString("VAR_USUARIO"));
            jsonObject.put("rol", rset.getString("VAR_ROL"));
            jsonObject.put("idRol", rset.getInt("FK_ROL"));
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
        }
        
          
        
        select.desconectar();
        jsonArreglo.add(jsonArray);
        return jsonArreglo;
        
     
    }
}
