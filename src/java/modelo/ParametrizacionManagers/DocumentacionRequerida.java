/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.ParametrizacionManagers;

import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.ParametrizacionDelegates.ActualizarDocumentacionRequerida;
import modelo.ParametrizacionDelegates.EliminarDocumentacionRequerida;
import modelo.ParametrizacionDelegates.InsertarDocumentacionRequerida;
import modelo.ParametrizacionDelegates.SeleccionarDocumentacionRequerida;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author illustrato
 */
public class DocumentacionRequerida {
      
    /**
     * 
     * Llama al delegate para insertar un documentacion requerida
     * 
     * @param descripcion
     * @param tipoInforme
     * @throws Exception 
     */
    public void insertar(String descripcion, int tipoInforme) throws Exception{
                
        InsertarDocumentacionRequerida insert = new InsertarDocumentacionRequerida(descripcion,tipoInforme);
        insert.ejecutar();
            
    }
    //-----------------------------------------------------------------------------
    
    
    
    
    /**
     * 
     * Llama al delegate para actualizar un Documento requerido
     * 
     * @param codigo
     * @param descripcion
     * @param tipoInforme
     * @throws Exception 
     */
    public void actualizar(int codigo, String descripcion, int tipoInforme) throws Exception{
        
        ActualizarDocumentacionRequerida update = new ActualizarDocumentacionRequerida(codigo, descripcion, tipoInforme);
        update.ejecutar();
        
    }
    //-----------------------------------------------------------------------------
    
    
     /**
     * 
     * Llama al delegate para Eliminar una unidad de medida.
     * 
     * @param codigo     
     * @throws Exception 
     */
    public JSONArray eliminar(int codigo) throws Exception{
        
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject(); 
        
        Integer respError ;
        
        EliminarDocumentacionRequerida delete = new EliminarDocumentacionRequerida(codigo);
        delete.ejecutar();
        
        respError = delete.getError();
        
        jsonObject.put("error", respError);
        
        jsonArray.add(jsonObject);
        
        return jsonArray;
        
        
    }
    //-----------------------------------------------------------------------------
    
    
    /**
     * 
     * Obtiene la informacion de la documentacion requerida y
     * guarda todo en un JSONArray para entregarselo a la vista.
     * 
     * @return JSONArray
     * @throws SQLException 
     */
    public JSONArray getDocumentacionRequerida(String descripcion, String tipoInforme) throws SQLException{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarDocumentacionRequerida select = new SeleccionarDocumentacionRequerida();
        ResultSet rset = select.getDocumentacionRequeridas(descripcion, tipoInforme);
        
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
            jsonObject.put("tipoInforme", rset.getString("TIPO_INFORME"));
            jsonObject.put("codTipoInforme", rset.getString("COD_TIPO_INFORME"));
            
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
     * Obtiene la informacion de un documento requerido y
     * guarda todo en un JSONArray para entregarselo a la vista.
     * 
     * @param codigo
     * @param tipoInforme
     * @return JSONArray
     * @throws SQLException 
     */
    public JSONArray getDocumentacionRequerida(Integer codigo, Integer tipoInforme) throws SQLException{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarDocumentacionRequerida select = new SeleccionarDocumentacionRequerida();
        ResultSet rset = select.getDocumentacionRequerida(codigo,tipoInforme);
        
        //Creamos los JSONArray para guardar los objetos JSON
        JSONArray jsonArray = new JSONArray();

        //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos
        //en el JSONArray.
        while(rset.next()){
            
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("codigo", rset.getString("CODIGO"));
            jsonObject.put("descripcion", rset.getString("DESCRIPCION"));
            jsonObject.put("tipoInforme", rset.getString("TIPO_INFORME"));
            jsonObject.put("codTipoInforme", rset.getString("COD_TIPO_INFORME"));
            
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
            
        }
        
        select.desconectar();      
        return jsonArray;
        
    }
    //-----------------------------------------------------------------------------
}
