/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.ParametrizacionManagers;

import modelo.ParametrizacionDelegates.EliminarParametrosAcreditacion;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.ParametrizacionDelegates.InsertarAcreditacionParametros;
import modelo.ParametrizacionDelegates.SeleccionarAcreditacionParametros;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author illustrato
 */
public class AcreditacionParametros {
    
    
     
    /**
     * 
     * Llama al delegate para insertar la Acreditacion
     * 
     * @param descripcion
     * @param ciiu
     * @throws Exception 
     */
    public void insertar(int parametro, int laboratorio) throws Exception{
                
        InsertarAcreditacionParametros insert = new InsertarAcreditacionParametros(parametro,laboratorio);
        insert.ejecutar();
            
    }
    
    
    
      /**
     * 
     * Obtiene la informacion de una unidad de medida y
     * guarda todo en un JSONArray para entregarselo a la vista.
     * 
     * @param codigo
     * @return JSONArray
     * @throws SQLException 
     */
    public JSONObject getPuntoVertimiento(int codigo) throws SQLException{
        return null;
    
        /*//Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarPuntosVertimiento select = new SeleccionarPuntosVertimiento();
        ResultSet rset = select.getPuntoVertimiento(codigo);
        
        //Obtenemos el JSONString
        JSONObject jsonArray = obtenerJSONString(rset);
        select.desconectar();
              
        return jsonArray;*/
        
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
                
                contrato = rset.getString("FK_CONTRATO");
                jsonObject1.put("contrato",contrato);
                flag = true;
                
            }
                
            String ubicacion = rset.getString("DESCRIPCION");
            String latitud = rset.getString("LATITUD");
            String longitud = rset.getString("LONGITUD");
            String estado = rset.getString("FK_ESTADO");
            String observacion = rset.getString("OBSERVACION");
            String codigo = rset.getString("CODIGO");
            
            JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("ubicacion", ubicacion);
            jsonObject2.put("latitud", latitud);
            jsonObject2.put("longitud", longitud);
            jsonObject2.put("observacion", observacion);
            jsonObject2.put("estado", estado);
            jsonObject2.put("codigo", codigo);
            
            jsonArray1.add(jsonObject2);
                   
        }
        
        jsonObject1.put("puntos",jsonArray1);

        
        return jsonObject1;
        
    }    
   
    
    public int eliminar (int codigo) throws Exception{
    
            EliminarParametrosAcreditacion eliminar = new EliminarParametrosAcreditacion(codigo);
            
            eliminar.ejecutar();
            
            return eliminar.getError();
    
    }
    
    
    /**
     * 
     * Llama al Delegate para obtener un cliente especifico
     * 
     * @param codigo
     * @return
     * @throws Exception 
     */
    public JSONArray getAcreditacionPorLab(String lab) throws Exception{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarAcreditacionParametros select = new SeleccionarAcreditacionParametros();
        ResultSet rset = select.getAcreditacionPorLab(lab);
                
        //Creamos los JSONArray para guardar los objetos JSON
        JSONArray jsonArray = new JSONArray();
        JSONArray salida = new JSONArray();

        //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos
        //en el JSONArray.
        while(rset.next()){
            
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("codigoParam", rset.getString("CODIGO_PARAM"));
            /*jsonObject.put("parametro", rset.getString("PARAMETRO"));*/
     
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
            
        }
        //Se cierra el ResultSet
        select.desconectar();
        
              salida.add(jsonArray);
        return salida;
        
    }
    //-----------------------------------------------------------------------------
    
    
    
}
