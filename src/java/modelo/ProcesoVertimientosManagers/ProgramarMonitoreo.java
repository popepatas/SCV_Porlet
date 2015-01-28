/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.ProcesoVertimientosManagers;

import Extensions.ArchivoExtension;
import java.io.File;
import java.sql.Blob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.text.DefaultEditorKit;
import modelo.DbManager;
import modelo.ParametrizacionDelegates.EliminarMotivosVisitas;
import modelo.ParametrizacionDelegates.SeleccionarAsociacionContratos;
import modelo.ProcesoVertimientosDelegates.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author illustrato
 */
public class ProgramarMonitoreo {
    
    
    public int insertar(String consultorMonitoreo, String fechaMonitoreo, String horaInicio, String horaFin, 
            int laboratorio, int codigoProceso, String observacion, String duraccionMonitoreo) throws Exception{      
        
        //Ejecutamos el procedimiento almacenado
        InsertarMonitoreo insert = new InsertarMonitoreo(consultorMonitoreo, fechaMonitoreo, horaInicio, horaFin, laboratorio, codigoProceso,observacion, duraccionMonitoreo);
        insert.ejecutar();
        
        return insert.getCodigoMonitoreo();
    }
    
    
    public void insertarPuntoMonitoreo(int codigoPunto, int actividadEconomica, int codigoMonitoreo) throws Exception{
    
        InsertarPuntoMonitoreo insert = new InsertarPuntoMonitoreo(codigoPunto, actividadEconomica, codigoMonitoreo);
        insert.ejecutar();
        
    }
    
    public Integer registrarSupervision(int codigoProceso, int tecnico) throws Exception{
    
            RegistrarSupervision registrar = new RegistrarSupervision(codigoProceso,tecnico);
            registrar.ejecutar();
            
             return registrar.getError();
    }
    
    public Integer registrarTasaRetributiva(int codigoProceso, int codigoParametro, String valorTarifa, String procentajeRemocion, String valorTasa, String valorCarga, String valorTasaCobrada,String valorTotalTasaPagar) throws Exception {
    
        RegistrarTasaRetributiva registrar = new RegistrarTasaRetributiva(codigoProceso, codigoParametro, valorTarifa, procentajeRemocion, valorTasa, valorCarga, valorTasaCobrada, valorTotalTasaPagar );
        
         registrar.ejecutar(); 
        
         return registrar.getError();
    }
    
    public JSONObject getTasaRetributiva(int codigoProceso) throws SQLException{
        
                //Ejecutamos la consulta y obtenemos el ResultSet
               SeleccionarTasaRetributiva select = new SeleccionarTasaRetributiva();
               ResultSet rset = select.getTasaRetributiva(codigoProceso);

               
               //Variables necesarias
               boolean flag = false;
               String valorAnio = null;
               String valorCobrado = null;
               JSONArray jsonArray1 = new JSONArray();
               JSONObject jsonObject1 = new JSONObject();


               //Recorremos los registros obtenidos de la consulta
               while(rset.next()){

                   //Si la bandera es false, armamos la cabecera.
                   if(!flag){

                      
                        valorAnio = rset.getString("VALOR_TASA_RETRIBUTIVA");
                        valorCobrado = rset.getString("VALOR_COBRADO_TASA_RETRIBUTIVA");
                       
                       
                       jsonObject1.put("valorTotalTasaPagar",valorAnio);
                       jsonObject1.put("valorTasaCobrada",valorCobrado);
                       flag = true;

                   }

                 
                   String codigoParametro = rset.getString("PK_PARAM_FISCOQUIM");
                   String valorTarifa = rset.getString("VALOR_TARIFA");
                   String procentajeRemocion = rset.getString("PORCENTAJE_REMOCION");
                   String valorTasa = rset.getString("VALOR_TASA_RETIBUTIVA");
                   String valorCarga = rset.getString("TOTAL_CARGA");
                   

                   JSONObject jsonObject2 = new JSONObject();
                   jsonObject2.put("codigoParametro", codigoParametro);
                   jsonObject2.put("valorTarifa", valorTarifa);
                   jsonObject2.put("procentajeRemocion", procentajeRemocion);
                   jsonObject2.put("valorTasa", valorTasa);
                   jsonObject2.put("valorCarga", valorCarga);
                   

                   jsonArray1.add(jsonObject2);

               }


               jsonObject1.put("cargasParam",jsonArray1);

              select.desconectar();
               return  jsonObject1;
        
    
    }
    
    public Integer insertarResultadoSupervision(int codigoProceso, int tecnico, String observacion, String estuvo) throws Exception{
    
            InsertarResultadoSupervicion registrar = new InsertarResultadoSupervicion(codigoProceso,tecnico,observacion,estuvo);
            registrar.ejecutar();
            
            return registrar.getError();
           
    }
    
    
    public JSONArray getMonitoreos(String fechaInicial, String fechaFinal, int codigoProceso) throws Exception{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarMonitoreos select = new SeleccionarMonitoreos();
        ResultSet rset = select.getMonitoreos(fechaInicial, fechaFinal, codigoProceso);
        
        //Creamos los JSONArray para guardar los objetos JSON
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        
        //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos
        //en el JSONArray.
        while(rset.next()){
            
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("codigo", rset.getString("CODIGO"));
            jsonObject.put("procesoVertimiento", rset.getString("FK_PROCESO_VERTIMIENTO"));
            jsonObject.put("fechaMonitoreo", rset.getString("FECHA_MONITOREO"));
            jsonObject.put("horaInicial", rset.getString("HORA_INICIAL"));
            jsonObject.put("horaFinal", rset.getString("HORA_FINAL"));
            jsonObject.put("consultor", rset.getString("CONSULTOR"));
            jsonObject.put("laboratorio", rset.getString("LABORATORIO"));
            jsonObject.put("estado", rset.getString("ESTADO"));
            jsonObject.put("duracion", rset.getString("DURACION_MONITOREO"));
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
            
        }
        
        jsonArreglo.add(jsonArray);
        select.desconectar();
        
        return jsonArreglo;
        
    }
    /**
     *
     * @param filaInicio
     * @param filaFin
     * @param fechaInicial
     * @param fechaFinal
     * @param codigoProceso
     * @param contrato
     * @param nit
     * @param comuna
     * @param razonSocial
     * @param direccion
     * @param estado
     * @param codigoMonitoreo
     * @return 
     * @throws java.lang.Exception
     */
     public JSONArray getMonitoreosAdmon(String filaInicio, String filaFin, String fechaInicial, String fechaFinal, String codigoProceso, String contrato,String nit,String razonSocial, String comuna, String direccion, String estado, String codigoMonitoreo   ) throws Exception{
    
         
           //Creamos los JSONArray para guardar los objetos JSON
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarMonitoreos select = new SeleccionarMonitoreos();
       ResultSet rset = select.getMonitoreosAdmon(filaInicio, filaFin, fechaInicial, fechaFinal, codigoProceso, contrato, nit ,razonSocial, comuna, direccion, estado, codigoMonitoreo);
        
      
        
        //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos
        //en el JSONArray.
        while(rset.next()){
            
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("codigo", rset.getString("CODIGO"));
            jsonObject.put("procesoVertimiento", rset.getString("FK_PROCESO_VERTIMIENTO"));
            jsonObject.put("fechaMonitoreo", rset.getString("FECHA_MONITOREO"));
            jsonObject.put("horaInicial", rset.getString("HORA_INICIAL"));
            jsonObject.put("horaFinal", rset.getString("HORA_FINAL"));
            jsonObject.put("consultor", rset.getString("CONSULTOR"));
            jsonObject.put("laboratorio", rset.getString("LABORATORIO"));
            jsonObject.put("tecnicoSup", rset.getString("TECNICO"));
            jsonObject.put("observSup", rset.getString("OBSERVACION_SUPERVISION"));
            jsonObject.put("razonSocial", rset.getString("RAZON_SOCIAL"));
            jsonObject.put("nit", rset.getString("NIT"));
            jsonObject.put("estado", rset.getString("ESTADO"));
            jsonObject.put("estadoSup", rset.getString("ESTADOBQ"));
            jsonObject.put("contrato", rset.getString("FK_CONTRATO"));
            jsonObject.put("estuvo", rset.getString("ESTUVO_MONITOREO"));
            
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
            
        }
        
        jsonArreglo.add(jsonArray);
        select.desconectar();
        
        return jsonArreglo;
        
    }
    
    
    public JSONArray getMonitoreo(int codigo) throws SQLException{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarMonitoreos select = new SeleccionarMonitoreos();
        ResultSet rset = select.getMonitoreo(codigo);
        
        //Creamos los JSONArray para guardar los objetos JSON
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        
        //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos
        //en el JSONArray.
        while(rset.next()){
            
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("codigo", rset.getString("CODIGO"));
            jsonObject.put("fechaMonitoreo", rset.getString("FECHA_MONITOREO"));
            jsonObject.put("horaInicial", rset.getString("HORA_INICIAL"));
            jsonObject.put("horaFinal", rset.getString("HORA_FINAL"));
            jsonObject.put("consultor", rset.getString("FK_CONSULTOR"));
            jsonObject.put("laboratorio", rset.getString("FK_LABORATORIO"));
            jsonObject.put("observacion", rset.getString("MOTIVO_REPROGRAMACION"));
            jsonObject.put("tecnicoSup", rset.getString("FK_TECNICO_SUPERVISION"));
            jsonObject.put("observacionSup", rset.getString("OBSERVACION_SUPERVISION"));
            jsonObject.put("estuvoSup", rset.getString("ESTUVO_MONITOREO"));
            jsonObject.put("duracion", rset.getString("DURACION_MONITOREO"));
            
                    
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
            
        }
        
        jsonArreglo.add(jsonArray);
        select.desconectar();
        
        return jsonArreglo;
        
    }
    
        
            /**
     * 
     * Llama al delegate que valida si existe un monitoreo activo para un proceso
     * 
     * @param codigo     
     * @throws Exception 
     */
    public JSONArray validarMonitoreoAct(int codigo) throws Exception{
        
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject(); 
        
        Integer resultado ;
        
        ValidarMonitoreosActivos validate = new ValidarMonitoreosActivos(codigo);
        validate.ejecutar();
        
        resultado = validate.getResultado();
        
        jsonObject.put("resultado", resultado);
        
        jsonArray.add(jsonObject);
        
        return jsonArray;
        
        
    }
    
    
     public void insertarAnexos(int codigoMonitoreo, File archivo, String nombreArchivo) throws Exception{
        
        InsertarAnexosSupervision insert = new InsertarAnexosSupervision(codigoMonitoreo, archivo, nombreArchivo);
        insert.ejecutar();
        
    }
    
    public JSONObject eliminarAnexos(Integer codigoArchivo, Integer codigoMonitoreo) throws Exception{
     
        JSONObject jsonObject = new JSONObject();
        int error ;
        
        EliminarAnexosMonitoreo eliminar = new EliminarAnexosMonitoreo(codigoArchivo,codigoMonitoreo);
        eliminar.ejecutar();
        
        error = eliminar.getError();
        
        jsonObject.put("error", error);
        
        return jsonObject;
    
    }
    
    public ArchivoExtension getArchivosCargado(Integer codigoArchivo, int codigoMonitoreo) throws SQLException{
        
        SeleccionarArchivosCargadosSupervision select = new SeleccionarArchivosCargadosSupervision();
        ResultSet rset = select.getArchivosCargados(codigoArchivo,codigoMonitoreo);
        
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
    
    public JSONArray obtenerPuntosMonitoreo(String puntoMonitoreo) throws Exception{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarMonitoreos select = new SeleccionarMonitoreos();
        ResultSet rset = select.obtenerPuntosMonitoreo(puntoMonitoreo);
        
        //Creamos los JSONArray para guardar los objetos JSON
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        
        //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos
        //en el JSONArray.
        while(rset.next()){
            
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("actividadEconomica", rset.getString("FK_ACTIVIDAD_ECONOMICA"));
            
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
            
        }
        
        jsonArreglo.add(jsonArray);
        select.desconectar();
        
        return jsonArreglo;
        
    }
    
    
}