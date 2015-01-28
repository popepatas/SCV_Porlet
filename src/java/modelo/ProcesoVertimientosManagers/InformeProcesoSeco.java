/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.ProcesoVertimientosManagers;

import Extensions.ArchivoExtension;
import java.io.File;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

import modelo.ProcesoVertimientosDelegates.EliminarAnexoInformes;
import modelo.ProcesoVertimientosDelegates.InsertarAnexoInformeProcesoSeco;
import modelo.ProcesoVertimientosDelegates.RegistrarProcesoSeco;;
import modelo.ProcesoVertimientosDelegates.SeleccionarArchivosInformesCargados;
import modelo.ProcesoVertimientosDelegates.SeleccionarInformacionProcesoSeco;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author illustrato
 */
public class InformeProcesoSeco {
    
    
    /*         
     * @param codigoProceso
     * @param laboratorioProcesoSeco
     * @param consultorProcesoSeco
     * @param fechaEntregaProcesoSeco
     * @param fechaRadicacionProcesoSeco
     * @param fechaEntDevolProcesoSeco
     * @param fechaDevolProcesoSeco
     * @param observacionDevolProsesoSeco
     * @param tipoDevolProcesoSeco
     */
    public JSONObject registrar(int codigoProceso, Integer laboratorioProcesoSeco, Integer consultorProcesoSeco, String fechaEntregaProcesoSeco, String fechaRadicacionProcesoSeco, String fechaEntDevolProcesoSeco, String fechaDevolProcesoSeco, String observacionDevolProsesoSeco, Integer tipoDevolProcesoSeco, String observacionesProcesoSeco ){
        
        JSONObject jsonObject = new JSONObject();
        
        try { 
                RegistrarProcesoSeco registrar = new RegistrarProcesoSeco(codigoProceso, laboratorioProcesoSeco, consultorProcesoSeco, fechaEntregaProcesoSeco, fechaRadicacionProcesoSeco, fechaEntDevolProcesoSeco, fechaDevolProcesoSeco, observacionDevolProsesoSeco, tipoDevolProcesoSeco, observacionesProcesoSeco);        
                registrar.ejecutar();
                
                jsonObject.put("resultado", registrar.getResultado());
                
            
        } catch (Exception ex) {
            
        }
        
            return jsonObject;
    
    }
    
    
    public void insertarAnexos(int codigoProceso, File archivo, String nombreArchivo) throws Exception{
        
        InsertarAnexoInformeProcesoSeco insert = new InsertarAnexoInformeProcesoSeco(codigoProceso, archivo, nombreArchivo);
        insert.ejecutar();
        
    }
    
    public JSONObject eliminarAnexos(int codigo, int codigoProceso) throws Exception{
     
        JSONObject jsonObject = new JSONObject();
        int error ;
        
        EliminarAnexoInformes eliminar = new EliminarAnexoInformes(codigo,codigoProceso);
        eliminar.ejecutar();
        
        error = eliminar.getError();
        
        jsonObject.put("error", error);
        
        return jsonObject;
    
    }
    
    public JSONObject getInformacionProcesoSeco(int codigoProceso) throws SQLException{
    
       
        JSONObject jsonObject = new JSONObject();
        ResultSet rset ;
        
          
        SeleccionarInformacionProcesoSeco select = new SeleccionarInformacionProcesoSeco(codigoProceso);
                
        rset = select.getInformacionProcesoSeco();
        
            while(rset.next()){
                
                jsonObject.put("fechaRadicacionProcesoSeco", rset.getString("FECHA_RADICACION"));
                jsonObject.put("fechaEntregaProcesoSeco", rset.getString("FECHA"));
                jsonObject.put("consultorProcesoSeco", rset.getString("FK_CONSULTOR"));
                jsonObject.put("laboratorioProcesoSeco", rset.getString("FK_LABORATORIO"));
                jsonObject.put("observacionDevolProsesoSeco", rset.getString("OBSERVACION"));
                jsonObject.put("fechaEntDevolProcesoSeco", rset.getString("FECHA_ENTREGA_DEVOLUCION"));
                jsonObject.put("fechaDevolProcesoSeco", rset.getString("FECHA_DEVOLUCION"));
                jsonObject.put("tipoDevolProcesoSeco", rset.getString("FK_TIPO_DEVOLUCION"));
                jsonObject.put("codigoDevolProcesoSeco", rset.getString("COD_DEVOL"));
                jsonObject.put("observacionesProcesoSeco", rset.getString("OBSERVACION_PROCESO"));
                
            }
       
        
        
        //Se cierra el ResultSet
        select.desconectar();
        
        return  jsonObject;
    
    }
    
   public JSONArray getArchivosCargados(int codigoProceso, Integer codigo) throws SQLException{
       
       ResultSet rset;
       
       JSONObject jsonObject = new JSONObject();
       JSONArray  jsonArray = new JSONArray();
       JSONArray  jsonArreglo = new JSONArray();
       SeleccionarArchivosInformesCargados select = new SeleccionarArchivosInformesCargados();
       
       rset = select.getArchivosCargados(codigoProceso, codigo);
       
                                            
        while(rset.next()){
            
            jsonObject.put("codigo", rset.getString("CODIGO"));
            jsonObject.put("nombreArchivo",  rset.getString("NOMBRE_ARCHIVO"));
          
            jsonArray.add(jsonObject.clone());
            
        }
        
        jsonArreglo.add(jsonArray);
        
        //Se cierra el ResultSet
        select.desconectar();
        
        return  jsonArreglo;
   
   }
   
    public ArchivoExtension getArchivosCargado(int codigoProceso,Integer codigoArchivo) throws SQLException{
        
        SeleccionarArchivosInformesCargados select = new SeleccionarArchivosInformesCargados();
        ResultSet rset = select.getArchivosCargados(codigoProceso,codigoArchivo);
        
        String nombreArchivo = null;
        Blob dataArchivo = null;
        ArchivoExtension  archivo;
        
        while(rset.next()){
            nombreArchivo = rset.getString("NOMBRE_ARCHIVO");
            dataArchivo = rset.getBlob("ARCHIVO");
        }
        
         archivo = new ArchivoExtension(dataArchivo, nombreArchivo);
        
        return archivo;
    }
}
