/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author illustrato
 */
public class ApiManager {
    
        public static String quitaNull(String s) {
          if (s == null) {
              return "";
          }
          return s.trim();
        } 
        
        public static Integer ponerNull(String s) {
            if (s != null && !s.equals("")) {
                return Integer.parseInt(s);
            } 

            return null;

         }
        
       public static Integer numeroNull(String s) {
           
         String valor = quitaNull(s);
         Integer valorNumerico = 0;

            if (valor.equals("")) {
                
                return valorNumerico ;
                
            }else{
              valorNumerico = Integer.parseInt(s);
              return  valorNumerico;
            }
             
       } 
    
        public static  List resultSetToArrayList(ResultSet rs) throws SQLException{
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();
            ArrayList list = new ArrayList();

            while (rs.next()){
               HashMap row = new HashMap(columns);
               for(int i=1; i<=columns; ++i){           
                row.put(md.getColumnName(i),rs.getObject(i));
               }
                list.add(row);
            }

           return list;
        }
        
       public static void limpiarJSONObject(JSONObject jsonObject){
    
           if(!jsonObject.isEmpty()){
               jsonObject.clear();
           }
           
       }
       
       
        public static void limpiarJSONArray(JSONArray jsonArray){
    
           if(!jsonArray.isEmpty()){
               jsonArray.clear();
           }
           
       }
        
     public static String convCharUnids(String cadena){
            
            if(cadena.indexOf("{U}") != -1){
                cadena = cadena.replace("{U}", "&#956;");
            }else{
                
                if(cadena.indexOf("{SP:") != -1){
                    cadena = cadena.replace("{SP:", "<sup>");
                    cadena = cadena.replace("}", "</sup>");
                }else if(cadena.indexOf("{SB:") != -1 ){
                    cadena = cadena.replace("{SB:", "<sub>");
                    cadena = cadena.replace("}", "</sub>"); 
                }
            }
            
            return cadena;
        }
}