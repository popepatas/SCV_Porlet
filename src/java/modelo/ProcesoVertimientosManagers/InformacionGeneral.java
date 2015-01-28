/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.ProcesoVertimientosManagers;

import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.ProcesoVertimientosDelegates.ActualizarAsesoria;
import modelo.ProcesoVertimientosDelegates.ActualizarProcesoVertimientos;
import modelo.ProcesoVertimientosDelegates.InsertarAsesoria;
import modelo.ProcesoVertimientosDelegates.SeleccionarInformacionGeneral;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


/**
 *
 * @author illustrato
 */
public class InformacionGeneral {
    
    
      /**
     * 
     * Llama al delegate para insertar la Información General
     * 
     * @param requiereVisita
     * @param contacto
     * @param asunto
     * @param fecha_asesoria
     * @param codigoProceso
     * @param informaMonitoreo
     * @param tipoInforme
     * @throws Exception 
     */
    public void registrar(String requiereVisita, String contacto, String asunto, String fecha_asesoria, Integer codigoProceso,String tipoContacto, String tipoInforme, String informaMonitoreo) throws Exception{
                
       
        InsertarAsesoria insertAsesoria = new InsertarAsesoria(requiereVisita, contacto, asunto, fecha_asesoria, codigoProceso, tipoContacto);
        ActualizarProcesoVertimientos updateProcesoVertimiento = new ActualizarProcesoVertimientos(codigoProceso, tipoInforme, informaMonitoreo);
        /*Se actualiza en la tabla de puntos vertimiento*/
        updateProcesoVertimiento.ejecutar();
         /*Se inserta en la tabla de asesoria*/
        insertAsesoria.ejecutar();
            
    }
    //-----------------------------------------------------------------------------
    
    public void actualizar(String requiereVisita, String contacto, String asunto, String fecha_asesoria, Integer codigoProceso,String tipoContacto, String tipoInforme, String informaMonitoreo) throws Exception{
                
        
        ActualizarAsesoria updateAsesoria = new ActualizarAsesoria(requiereVisita, contacto, asunto, fecha_asesoria, codigoProceso, tipoContacto);
        ActualizarProcesoVertimientos updateProcesoVertimiento = new ActualizarProcesoVertimientos(codigoProceso, tipoInforme, informaMonitoreo);
        /*Se actualiza en la tabla de puntos vertimiento*/
        updateProcesoVertimiento.ejecutar();
        /*Se inserta en la tabla de asesoria*/
        updateAsesoria.ejecutar();
            
    }
    //-----------------------------------------------------------------------------
    
    public JSONArray getInformacionGeneral(int codigoProceso) throws SQLException{
        
        JSONArray jsonArreglo = new JSONArray();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        
        ResultSet rset ;
        
        SeleccionarInformacionGeneral select = new SeleccionarInformacionGeneral();
        
        rset = select.getInformacionGeneral(codigoProceso);
        
        
        while(rset.next()){
        
            
            jsonObject.put("codigoProceso",rset.getString("COD_PROCESO"));
            jsonObject.put("informo",rset.getString("INFORMA_MONITOREO"));
            jsonObject.put("tipoInforme",rset.getString("FK_TIPO_INFORME"));
            jsonObject.put("requiereVisita",rset.getString("REQUIERE_VISITA"));
            jsonObject.put("personaContacto",rset.getString("CONTACTO"));
            jsonObject.put("asunto",rset.getString("ASUNTO"));
            jsonObject.put("tipoContacto",rset.getString("FK_TIPO_CONTACTO"));
            jsonObject.put("razonSocial",rset.getString("RAZON_SOCIAL"));
            jsonObject.put("nit",rset.getString("NIT"));
            jsonObject.put("contrato",rset.getString("CONTRATO"));
            jsonObject.put("codigoAsesoria",rset.getString("COD_ASESORIA"));
            jsonObject.put("fechaAsesoria",rset.getString("FECHA_ASESORIA"));
            jsonObject.put("estadoProceso",rset.getString("ESTADO_PROCESO"));
            jsonObject.put("codigoCliente",rset.getString("COD_CLI"));
            jsonArray.add(jsonObject.clone());
        }
        
        jsonArreglo.add(jsonArray);
        
        //Se cierra el ResultSet
        select.desconectar();
    
        return jsonArreglo;
    }
    //-----------------------------------------------------------------------------
    
}
