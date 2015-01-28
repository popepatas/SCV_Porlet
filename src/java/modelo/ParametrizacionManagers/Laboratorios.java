/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.ParametrizacionManagers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ParametrizacionDelegates.ActualizarLaboratorios;
import modelo.ParametrizacionDelegates.EliminarLaboratorios;
import modelo.ParametrizacionDelegates.InsertarLaboratorios;
import modelo.ParametrizacionDelegates.SeleccionarLaboratorios;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author illustrato
 */
public class Laboratorios {
    
     private int codigoLaboratorio;

     public int getCodigoLaboratorio() {
        return codigoLaboratorio;
     }
    
      
      public void insertar(String nombres, String contactos, String direcciones, 
            String telefono, String telefono2, String correo, String resolucion,String vigencia) throws Exception{
          
          
          InsertarLaboratorios insertar = new InsertarLaboratorios(nombres, contactos, direcciones, telefono, telefono2, correo, resolucion,  vigencia);
          insertar.ejecutar();
          
          codigoLaboratorio = insertar.getCodLaboratorio();
        
      }
      
      
       public void actualizar(String nombres, String contactos, String direcciones, 
            String telefono, String telefono2, String correo, String resolucion,String vigencia, int codigo) throws Exception{
      
           ActualizarLaboratorios actualizar = new ActualizarLaboratorios(nombres, contactos, direcciones, telefono, telefono2, correo, resolucion,  vigencia, codigo);
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
        
        EliminarLaboratorios delete = new EliminarLaboratorios(codigo);
        delete.ejecutar();
        
        respError = delete.getError();
        
        jsonObject.put("error", respError);
        
        jsonArray.add(jsonObject);
        
        return jsonArray;
        
        
    }
      
      
      public JSONArray getLaboratorios(String nombre, String contactos,String direccion,String telefono1,String telefono2,
            String correo,String resolucion,String vigencia) throws SQLException{
          
          JSONArray jsonArray  = new JSONArray();
          JSONArray jsonArreglo  = new JSONArray();
          JSONObject jsonObject = new JSONObject();
                           
         SeleccionarLaboratorios seleccionar = new SeleccionarLaboratorios();
         ResultSet rset = seleccionar.getLaboratorios(nombre, contactos, direccion, telefono1, telefono2, correo, resolucion, vigencia);
         
           try {
              while(rset.next()){
                  
                  jsonObject.put("codigo", rset.getString("CODIGO"));
                  jsonObject.put("nombre",rset.getString("NOMBRES"));
                  jsonObject.put("contacto",rset.getString("CONTACTO"));
                  jsonObject.put("direccion",rset.getString("DIRECCION"));
                  jsonObject.put("telefono1",rset.getString("TELEFONO1"));
                  jsonObject.put("telefono2",rset.getString("TELEFONO2"));
                  jsonObject.put("correo",rset.getString("EMAIL"));
                  jsonObject.put("resolucion",rset.getString("RESOLUCION_ACREDITACION"));
                  jsonObject.put("vigencia",rset.getString("VIGENCIA"));
                  jsonObject.put("descripcion",rset.getString("NOMBRES"));
                  jsonArray.add(jsonObject.clone());
                  
              }
          } catch (SQLException ex) {
             // Logger.getLogger(Laboratorios.class.getName()).log(Level.SEVERE, null, ex);
          }
          
          seleccionar.desconectar();

          jsonArreglo.add(jsonArray);
          
          return jsonArreglo;
      }
    
      
       public JSONObject getLaboratorio(int codigo){

        SeleccionarLaboratorios seleccionar = new SeleccionarLaboratorios();
        ResultSet rset = seleccionar.getLaboratorio(codigo);
        JSONObject jsonObject = new JSONObject();
           try {

                jsonObject = obtenerJSONString(rset);

                seleccionar.desconectar();

          } catch (SQLException ex) {
             // Logger.getLogger(Laboratorios.class.getName()).log(Level.SEVERE, null, ex);
          }
          
          
          return jsonObject;
      }
      
      
     /**
     * 
     * Arma el la cadena JSON que seria enviada a la vista.
     * 
     * @param rset
     * @return String
     * @throws SQLException
     */
    private JSONObject obtenerJSONString(ResultSet rset) throws SQLException{
      
        //Variables necesarias
        boolean flag = false;
        String contrato = null;
        JSONArray jsonArray1 = new JSONArray();
        JSONObject jsonObject1 = new JSONObject();

        
        //Recorremos los registros obtenidos de la consulta
        while(rset.next()){
            
            //Si la bandera es false, armamos la cabecera.
            if(!flag){
                
                  jsonObject1.put("codigo", rset.getString("CODIGO"));
                  jsonObject1.put("nombre",rset.getString("NOMBRES"));
                  jsonObject1.put("contacto",rset.getString("CONTACTO"));
                  jsonObject1.put("direccion",rset.getString("DIRECCION"));
                  jsonObject1.put("telefono1",rset.getString("TELEFONO1"));
                  jsonObject1.put("telefono2",rset.getString("TELEFONO2"));
                  jsonObject1.put("correo",rset.getString("EMAIL"));
                  jsonObject1.put("resolucion",rset.getString("RESOLUCION_ACREDITACION"));
                  jsonObject1.put("vigencia",rset.getString("VIGENCIA"));
                  jsonObject1.put("descripcion",rset.getString("NOMBRES"));
                                  
                   flag = true;
                
            }
            
            JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("codigoParam", rset.getString("COD_PARAM"));
            jsonObject2.put("codigoAcredParam", rset.getString("COD_PARAM_ACRED"));
            jsonObject2.put("parametro", rset.getString("DESP_PARAM"));
            
            jsonArray1.add(jsonObject2);
                   
        }
        
        jsonObject1.put("paramAcreditados",jsonArray1);

        
        return jsonObject1;
        
    } 
    
     
}
