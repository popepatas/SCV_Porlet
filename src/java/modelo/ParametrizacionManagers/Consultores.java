/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.ParametrizacionManagers;

import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.ParametrizacionDelegates.ActualizarConsultores;
import modelo.ParametrizacionDelegates.EliminarConsultores;
import modelo.ParametrizacionDelegates.InsertarConsultores;
import modelo.ParametrizacionDelegates.SeleccionarConsultores;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author illustrato
 */
public class Consultores {
    
      public void insertar(String nombres, String apellidos, String direcciones, 
            String telefono, String telefono2, String correo, String identificacion) throws Exception{
      
          InsertarConsultores insertar = new InsertarConsultores(nombres, apellidos, direcciones, telefono, telefono2, correo, identificacion);
          insertar.ejecutar();
      }
      
      
       public void actualizar(String nombres, String apellidos, String direcciones, 
            String telefono, String telefono2, String correo, String identificacion, int codigo) throws Exception{
      
          ActualizarConsultores actualizar = new ActualizarConsultores(nombres, apellidos, direcciones, telefono, telefono2, correo, identificacion, codigo);
          actualizar.ejecutar();
      }
      
      
        /**
     * 
     * Llama al delegate para Eliminar un Consultorio
     * 
     * @param codigo     
     * @throws Exception 
     */
    public JSONArray Eliminar(int codigo) throws Exception{
        
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject(); 
        
        Integer respError ;
        
        EliminarConsultores delete = new EliminarConsultores(codigo);
        delete.ejecutar();
        
        respError = delete.getError();
        
        jsonObject.put("error", respError);
        
        jsonArray.add(jsonObject);
        
        return jsonArray;
        
        
    }
      
       public JSONArray getConsultores(String nombre ,String apellidos, String direccion, String telefono, String telefono2, String identificacion, String correo) throws SQLException{
          
          JSONArray jsonArray  = new JSONArray();
          JSONArray jsonArreglo  = new JSONArray();
          JSONObject jsonObject = new JSONObject();
                           
         SeleccionarConsultores seleccionar = new SeleccionarConsultores();
         ResultSet rset = seleccionar.getConsultores(nombre, apellidos, direccion, telefono, telefono2, identificacion, correo);
         
           try {
              while(rset.next()){
                  
                  jsonObject.put("codigo",rset.getString("CODIGO"));
                  jsonObject.put("nombre",rset.getString("NOMBRES"));
                  jsonObject.put("apellidos",rset.getString("APELLIDOS"));
                  jsonObject.put("direccion",rset.getString("DIRECCION"));
                  jsonObject.put("telefono1",rset.getString("TELEFONO1"));
                  jsonObject.put("telefono2",rset.getString("TELEFONO2"));
                  jsonObject.put("correo",rset.getString("EMAIL"));
                  jsonObject.put("identificacion",rset.getString("IDENTIFICACION"));
                 jsonObject.put("descripcion", rset.getString("NOMBRES") +' ' + rset.getString("APELLIDOS") );
                  
                  jsonArray.add(jsonObject.clone());
                  
              }
              
              

              
          } catch (SQLException ex) {
             // Logger.getLogger(Laboratorios.class.getName()).log(Level.SEVERE, null, ex);
          }
          
            seleccionar.desconectar();     
            jsonArreglo.add(jsonArray);
          
          return jsonArreglo;
      }
    
      
       public JSONArray getConsultor(int codigo){
                     
         SeleccionarConsultores seleccionar = new SeleccionarConsultores();
         ResultSet rset = seleccionar.getConsultor(codigo);
           
          JSONArray jsonArray  = new JSONArray();
          JSONObject jsonObject = new JSONObject();
          
          try {
              while(rset.next()){
                  
                  jsonObject.put("codigo",rset.getString("CODIGO"));
                  jsonObject.put("nombre",rset.getString("NOMBRES"));
                  jsonObject.put("apellidos",rset.getString("APELLIDOS"));
                  jsonObject.put("direccion",rset.getString("DIRECCION"));
                  jsonObject.put("telefono1",rset.getString("TELEFONO1"));
                  jsonObject.put("telefono2",rset.getString("TELEFONO2"));
                  jsonObject.put("correo",rset.getString("EMAIL"));
                  jsonObject.put("identificacion",rset.getString("IDENTIFICACION"));
                  jsonObject.put("descripcion", rset.getString("NOMBRES") +' ' + rset.getString("APELLIDOS") );
                  
                  
                  jsonArray.add(jsonObject.clone());
                  
              }
            
              seleccionar.desconectar();

          } catch (SQLException ex) {
             // Logger.getLogger(Laboratorios.class.getName()).log(Level.SEVERE, null, ex);
          }
          
          
          
          
          return jsonArray;
      }

         
    
}
