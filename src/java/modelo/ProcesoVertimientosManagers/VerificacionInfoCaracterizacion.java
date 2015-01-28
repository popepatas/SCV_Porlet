/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.ProcesoVertimientosManagers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import modelo.ApiManager;
import modelo.DbManager;
import modelo.ProcesoVertimientosDelegates.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author illustrato
 */
public class VerificacionInfoCaracterizacion {
    
    
    public void insertar(String checkeado, Integer codigo, Integer codigoProceso) throws Exception{
        
        InsertarVerificacionInfoCaracterizacion insertar = new InsertarVerificacionInfoCaracterizacion(checkeado, codigo, codigoProceso);
        insertar.ejecutar();
    
    }
    /*
     * Este metodo construye el arbol gerarquico  de los requisitos de ley que debe cumplir un tipo de informe
     * para ser enviado al DAGMA
     */
    public JSONArray getVerificacionInfoCaracterizacion(Integer codigoProceso, Integer tipoInforme) throws SQLException{
      
        ResultSet rset  = null;
        JSONArray  jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        JSONArray  jsonArrayPreguntas = new JSONArray();
                
     
        //Se crea el objeto
        SeleccionarVerificacionInfoCaracterizacion select = new SeleccionarVerificacionInfoCaracterizacion(codigoProceso, tipoInforme);
        
         
           //Seleccionamos los "Hijos" y  "Nietos" del requisito "Padre"
           rset = select.getVerificaciones();
           
                                                  
            while(rset.next()){

                jsonObject.put("codigo", rset.getString("CODIGO"));
                jsonObject.put("reguisito",  rset.getString("DESCRIPCION"));
                jsonObject.put("fk_informe", rset.getString("FK_TIPO_INFORME"));
                jsonObject.put("checkeado", rset.getString("TIENE"));

                jsonArray.add(jsonObject.clone());

            }       
             
        jsonArrayPreguntas.add(jsonArray.clone());
               
        //Se cierra el ResultSet
        rset.close();
        select.desconectar();
        
        return jsonArrayPreguntas;
    }
    
    public JSONArray getDevolucionCaracterizacion(String codigoPoceso) throws SQLException{
        
        JSONArray  jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        
        SeleccionarVerificacionInfoCaracterizacion select = new SeleccionarVerificacionInfoCaracterizacion();
        ResultSet rset = select.getDevolucionCaracterizacion(codigoPoceso);
        
        while(rset.next()){
            jsonObject.put("tipoDevolCaracterizacion",rset.getString(("TIPO_DEVOLUCION")));
            jsonObject.put("fechaEntDevolCaracterizacion",rset.getString(("FECHA_ENTREGA")));
            jsonObject.put("observacionDevolCaracterizacion",rset.getString(("OBSERVACION")));
            jsonObject.put("fechaDevolCaracterizacion",rset.getString(("FECHA_DEVOLUCION")));
        }
        
        jsonArray.add(jsonObject.clone());
        return jsonArray;
    }
    
    public void insertarDevlucion(String tipoDevolucion, String fechaEntrega, String observacion, String codigoProceso, String fechaDevolucion) throws Exception
    {
        InsertarDevolucion insert = new InsertarDevolucion(tipoDevolucion, fechaEntrega, observacion, codigoProceso, fechaDevolucion);
        insert.ejecutar();
    }
}
