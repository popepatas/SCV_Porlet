package modelo.ParametrizacionManagers;

import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.ParametrizacionDelegates.ActualizarTecnicos;
import modelo.ParametrizacionDelegates.InsertarTecnicos;
import modelo.ParametrizacionDelegates.SeleccionarTecnicos;
import modelo.ParametrizacionDelegates.EliminarTecnicos;
import org.json.simple.*;

public class Tecnicos {


    
    /**
     * 
     * Llama al delegate para insertar un Tecnico
     * 
     * @param nombres
     * @param apellidos
     * @param tipoDocumento
     * @param documento
     * @param estado
     * @param tipoPersona
     * @throws Exception 
     */
    public void insertar(String nombres, String apellidos, int tipoDocumento, String documento,
            int estado, int tipoPersona) throws Exception{
                
        InsertarTecnicos insert = new InsertarTecnicos(nombres, apellidos, tipoDocumento, documento, estado, tipoPersona);
        insert.ejecutar();
            
    }
    //-----------------------------------------------------------------------------
    
    
    
    
    /**
     * 
     * Llama al delegate para actualizar un Tecnico
     * 
     * @param codigo
     * @param descripcion
     * @throws Exception 
     */
    public void actualizar(int codigo, String nombres, String apellidos, int tipoDocumento, String documento,
            int estado, int tipoPersona) throws Exception{
        
        ActualizarTecnicos update = new ActualizarTecnicos(codigo, nombres, apellidos, tipoDocumento, documento, estado, tipoPersona);
        update.ejecutar();
        
    }
    //-----------------------------------------------------------------------------
    
    
     /**
     * 
     * Llama al delegate para Eliminar un Tecnico
     * 
     * @param codigo     
     * @throws Exception 
     */
    public JSONObject Eliminar(int codigo) throws Exception{
        
        
        JSONObject jsonObject = new JSONObject(); 
        
        Integer respError ;
        
        EliminarTecnicos delete = new EliminarTecnicos(codigo);
        delete.ejecutar();
        
        respError = delete.getError();
        
        jsonObject.put("error", respError);
        
        
        
        return jsonObject;
        
        
    }
    //-----------------------------------------------------------------------------
    
    
    /**
     * 
     * Obtiene la informacion de los Tecnicos y
     * guarda todo en un JSONArray para entregarselo a la vista.
     * 
     * @return JSONArray
     * @throws SQLException 
     */
    public JSONArray getTecnicos(String tipoPersona, String nombres, String apellidos, 
                                    String tipoDocumento, String documento, String estado) throws SQLException{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarTecnicos select = new SeleccionarTecnicos();
        ResultSet rset = select.getTecnicos(tipoPersona, nombres, apellidos, tipoDocumento, documento, estado);
        
        //Creamos los JSONArray para guardar los objetos JSON
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos
        //en el JSONArray.
        while(rset.next()){
            
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("codigo", rset.getString("CODIGO"));
            jsonObject.put("descripcion", rset.getString("NOMBRE"));
            jsonObject.put("tipoDocumento", rset.getString("TIPODOCUMENTO"));
            jsonObject.put("documento", rset.getString("DOCUMENTO"));
            jsonObject.put("estado", rset.getString("ESTADO"));
            jsonObject.put("tipoPersona", rset.getString("TIPOPERSONA"));
            
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
     * Obtiene la informacion de un tecnico y
     * guarda todo en un JSONArray para entregarselo a la vista.
     * 
     * @return JSONArray
     * @throws SQLException 
     */
    public JSONArray getTecnico(int codigo) throws SQLException{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarTecnicos select = new SeleccionarTecnicos();
        ResultSet rset = select.getTecnico(codigo);
        
        //Creamos los JSONArray para guardar los objetos JSON
        JSONArray jsonArray = new JSONArray();

        //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos
        //en el JSONArray.
        while(rset.next()){
            
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("codigo", rset.getString("CODIGO"));
            jsonObject.put("nombres", rset.getString("NOMBRES"));
            jsonObject.put("apellidos", rset.getString("APELLIDOS"));
            jsonObject.put("tipoDocumento", rset.getString("FK_TIPO_DOCUMENTO"));
            jsonObject.put("documento", rset.getString("DOCUMENTO"));
            jsonObject.put("estado", rset.getString("FK_ESTADO"));
            jsonObject.put("tipoPersona", rset.getString("FK_TIPO_PERSONA"));
            
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
            
        }
        
        select.desconectar();    
        return jsonArray;
        
    }
    //-----------------------------------------------------------------------------
    
}