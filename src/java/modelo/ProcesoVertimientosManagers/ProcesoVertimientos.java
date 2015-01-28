/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.ProcesoVertimientosManagers;

import java.sql.Date;
import java.sql.ResultSet;
import modelo.ProcesoVertimientosDelegates.SeleccionarProcesoVertimientos;
import modelo.DbManager;
import modelo.ProcesoVertimientosDelegates.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author illustrato
 */
public class ProcesoVertimientos {
    
  
    
    
   /**
     * 
     * Llama al delegate para obtener los clientes.
     * 
     * @param codigoProceso
     * @param nit
     * @param ciiu
     * @param contrato
     * @param razonSocial
     * @param anio
     * @param fechaInicial
     * @param fechaFinal
     * @param filaInicio
     * @param filaFin
     * @return
     * @throws Exception 
     */
    public JSONArray getProcesoVertimientos(String codigoProceso, String nit, String contrato ,String ciiu, String razonSocial, 
                                String anio, String fechaInicial, String fechaFinal,String filaInicio, String filaFin) throws Exception{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarProcesoVertimientos select = new SeleccionarProcesoVertimientos(codigoProceso, nit, contrato, ciiu, razonSocial, anio, fechaInicial, fechaFinal, filaInicio, filaFin);
        ResultSet rset = select.ejecutar();
        
        //Creamos los JSONArray para guardar los objetos JSON
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos
        
        //en el JSONArray.
        while(rset.next()){
            
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            
            jsonObject.put("codigoProceso", rset.getString("CODIGO"));
            jsonObject.put("nit", rset.getString("NIT"));
            jsonObject.put("razonSocial",rset.getString("RAZON_SOCIAL"));
            jsonObject.put("actividadEconomica", rset.getString("CIIU"));
            jsonObject.put("contrato", rset.getString("FK_CONTRATO"));            
            jsonObject.put("tipoInforme", rset.getString("TIPOINFORME"));
            jsonObject.put("fechaProceso", rset.getString("FECHA_PROCESO"));
            jsonObject.put("estado", rset.getString("ESTADOP"));
            jsonObject.put("rnum", rset.getString("RNUM"));
            jsonObject.put("valRQ", rset.getString("VALIDAR_RQ"));
          
            
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
            
        }
       
        jsonArreglo.add(jsonArray);
        
        //Se cierra el ResultSet
        select.desconectar();
        
        return jsonArreglo;
        
    }  
     
      public JSONObject finalizarProceso(int codigoProceso) throws Exception{
      
            JSONObject jsonObject = new JSONObject();
            int respuesta;
            
            FinalizarProcesoVertimientos finish = new FinalizarProcesoVertimientos(codigoProceso);
            finish.ejecutar();
            
            respuesta = finish.getRespuesta();
            jsonObject.put("respuesta", respuesta);
            
            return jsonObject;
      }
 
      public JSONArray getProcesoVertimientos() throws Exception{
            
             SeleccionarProcesoVertimientos select = new SeleccionarProcesoVertimientos();
             ResultSet rset = select.ejecutar();
        

            //Creamos los JSONArray para guardar los objetos JSON
            JSONArray jsonArray = new JSONArray();
            JSONArray jsonArreglo = new JSONArray();
            //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos
            //en el JSONArray.
            while(rset.next()){

                //Armamos el objeto JSON con la informacion del registro
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("codigoProceso", rset.getString("CODIGO"));
                jsonObject.put("nit", rset.getString("NIT"));
                jsonObject.put("razonSocial",rset.getString("RAZON_SOCIAL"));
                jsonObject.put("actividadEconomica", rset.getString("CIIU"));
                jsonObject.put("contrato", rset.getString("FK_CONTRATO"));            
                jsonObject.put("tipoInforme", rset.getString("TIPOINFORME"));
                jsonObject.put("fechaProceso", rset.getString("FECHA_PROCESO"));
                jsonObject.put("estado", rset.getString("ESTADOP"));


                //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
                jsonArray.add(jsonObject.clone());

            }
            
            jsonArreglo.add(jsonArray);
            
            //Se cierra el ResultSet
            select.desconectar();

            return jsonArreglo;

    }
    
    public JSONArray insertar(Double contrato, String nit, String fechaCreacion, String ciclo, String sector, String pozo, String consumo, String direccion, String usuario_creacion ) throws Exception{
        
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject(); 
        
        Integer respError ;
        Integer codigoProceso;
               
        
        InsertarProcesoVertimientos insert = new InsertarProcesoVertimientos(contrato, nit, fechaCreacion, ciclo, pozo, consumo, direccion, sector, usuario_creacion);
        insert.ejecutar();
                
        respError = insert.getResultado();
        codigoProceso = insert.getCodigoProceso();
        
        jsonObject.put("error", respError);
        jsonObject.put("codigoProceso", codigoProceso);
        
        jsonArray.add(jsonObject);
        
        return jsonArray;
        
    }
    
    public JSONObject eliminarProcesoVertimiento(int codigo) throws Exception{
        
        JSONObject  salida = new JSONObject();
        
        EliminarProcesoVertimientos eliminar =  new EliminarProcesoVertimientos(codigo);
        
        eliminar.ejecutar();
        
        salida.put("error",eliminar.getError());        
        
        return  salida;
        
    }
    
}