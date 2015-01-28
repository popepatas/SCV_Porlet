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
import modelo.ProcesoVertimientosDelegates.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ManejoLodos {
    
    /**
     * 
     * Inserta una entidad de manejo de Lodos
     * 
     * @param nombreEmpresa
     * @param recolecta
     * @param volumen
     * @param fechaRecoleccion
     * @param frecuencia
     * @param transporte
     * @param dispone
     * @param sitioDispone
     * @param codigo
     * @throws Exception 
     */
    public void insertarEntidadLodos(String nombreEmpresa, String recolecta, String volumen, String fechaRecoleccion, 
                                                                    String frecuencia, String transporte, String dispone, String sitioDispone, String codigo) throws Exception{
        
        InsertarEntidadLodos insert = new InsertarEntidadLodos( nombreEmpresa,  recolecta,  volumen,  fechaRecoleccion, 
                                                                     frecuencia,  transporte,  dispone,  sitioDispone,  codigo);
        
        insert.ejecutar();

    }
    
    public void actualizarEntidadLodos(String nombreEmpresa, String recolecta, String volumen, String fechaRecoleccion, String frecuencia, 
            String transporte, String dispone, String sitioDispone, String codigo, String codigoLodos) throws Exception{
        
            ActualizarEntidadLodos update = new ActualizarEntidadLodos(nombreEmpresa, recolecta, volumen, fechaRecoleccion, frecuencia, 
                    transporte, dispone, sitioDispone, codigo, codigoLodos);
            
            update.ejecutar();
    }
    
    
    public JSONArray Eliminar(int codigo) throws Exception{
        
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject(); 
        
        Integer respError ;
        
        EliminarEntidadLodos delete = new EliminarEntidadLodos(codigo);
        delete.ejecutar();
        
        respError = delete.getError();
        
        jsonObject.put("error", respError);
        
        jsonArray.add(jsonObject);
        
        return jsonArray;
        
        
    }
    
    public JSONArray getLodos(String codigo) throws Exception{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarEntidadesLodos select = new SeleccionarEntidadesLodos();
        ResultSet rset = select.getLodos(codigo);
        //Creamos los JSONArray para guardar los objetos JSON
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        
        //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos
        //en el JSONArray.
        while(rset.next()){
            
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("codigo", rset.getString("CODIGO"));
            jsonObject.put("nombreEmpresa", rset.getString("NOMBRE_EMPRESA"));
            jsonObject.put("recolecta", rset.getString("RECOLECTA"));
            jsonObject.put("transporte", rset.getString("TRANSPORTE"));
            jsonObject.put("dispone", rset.getString("DISPONE"));
            
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
            
        }
        
        jsonArreglo.add(jsonArray);
        select.desconectar();
        
        return jsonArreglo;
        
    }
    
    public JSONArray getLodo(int codigo) throws Exception{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarEntidadesLodos select = new SeleccionarEntidadesLodos();
        ResultSet rset = select.getLodo(codigo);
        //Creamos los JSONArray para guardar los objetos JSON
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        
        //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos
        //en el JSONArray.
        while(rset.next()){
            
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("codigo", rset.getString("CODIGO"));
            jsonObject.put("nombreEmpresa", rset.getString("NOMBRE_EMPRESA"));
            jsonObject.put("recolecta", rset.getString("RECOLECTA"));
            jsonObject.put("transporte", rset.getString("TRANSPORTE"));
            jsonObject.put("dispone", rset.getString("DISPONE"));
            jsonObject.put("volumen", rset.getString("VOLUMEN"));
            jsonObject.put("fecha", rset.getString("FECHA_RECOLECCION"));
            jsonObject.put("frecuencia", rset.getString("FRECUENCIA"));
            jsonObject.put("sitio", rset.getString("SITIO_DISPOSICION"));
            
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
            
        }
        
        jsonArreglo.add(jsonArray);
        select.desconectar();
        
        return jsonArreglo;
        
    }
    
    public void insertarLodos(String diasAlMes, String horasAlDia, String preTratamiento, String cualPreTratamiento, String generacionLodos, 
            String cualGeneracionLodos, String codigoProceso, String cualOtroGeneracionLodos) throws Exception{
        
            InsertarLodos insert = new InsertarLodos(diasAlMes, horasAlDia, preTratamiento, cualPreTratamiento, 
                                                        generacionLodos, cualGeneracionLodos, codigoProceso, cualOtroGeneracionLodos);
            
            insert.ejecutar();
    }
    
        public JSONArray getLodosInfoTec(int codigo) throws Exception{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarLodos select = new SeleccionarLodos();
        ResultSet rset = select.getLodo(codigo);
        //Creamos los JSONArray para guardar los objetos JSON
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        
        //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos
        //en el JSONArray.
        while(rset.next()){
            
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("diasAlMes", rset.getString("JORNADA_PRODUCTIVA_DIAS"));
            jsonObject.put("horasAlDia", rset.getString("JORNADA_PRODUCTIVA_HORAS"));
            jsonObject.put("preTratamiento", (rset.getString("PRE_TRATAMIENTO")).trim());
            jsonObject.put("cualPreTratamiento", rset.getString("OTROS_PRETRATAMIENTO"));
            jsonObject.put("generacionLodos", (rset.getString("LODOS_ACTIVIDAD_PRODUCTIVA")).trim());
            jsonObject.put("cualGeneracionLodos", rset.getString("OTROS_ACTIVIDAD_PROD"));
            jsonObject.put("cualGeneracionLodosOtros", rset.getString("OTROS_CUAL_ACT_PROD"));
            
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
            
        }
        
        jsonArreglo.add(jsonArray);
        select.desconectar();
        
        return jsonArreglo;
        
    }
        
    public void insertarAnexoLodos(int codigoVisita, File archivo, String nombreArchivo) throws Exception{
        
        InsertarAnexoLodos insert = new InsertarAnexoLodos(codigoVisita, archivo, nombreArchivo);
        insert.ejecutar();
        
    }
    
    public JSONObject eliminarAnexos(int codigo, int codigoProceso) throws Exception{
     
        JSONObject jsonObject = new JSONObject();
        int error ;
        
        EliminarAnexoLodos eliminar = new EliminarAnexoLodos(codigo, codigoProceso);
        eliminar.ejecutar();
        
        error = eliminar.getError();
        
        jsonObject.put("error", error);
        
        return jsonObject;
    
    }
    
        public JSONArray getArchivosLodos(int codigo) throws Exception{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarLodos select = new SeleccionarLodos();
        ResultSet rset = select.getArchivosLodos(codigo);
        
        //Creamos los JSONArray para guardar los objetos JSON
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        
        //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos
        //en el JSONArray.
        while(rset.next()){
            
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("codigo", rset.getString("CODIGO"));
            String nombreArchivo = (rset.getString("NOMBRE_ARCHIVO")).replace("\\", "");
            jsonObject.put("nombreArchivo", nombreArchivo);
            
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
            
        }
        
        jsonArreglo.add(jsonArray);
        select.desconectar();
        
        return jsonArreglo;
        
    }
        
    public ArchivoExtension getAnexoLodos(int codigoArchivo) throws SQLException{
        
        SeleccionarLodos select = new SeleccionarLodos();
        ResultSet rset = select.getAnexoLodos(codigoArchivo);
        
        String nombreArchivo = null;
        Blob dataArchivo = null;
        
        while(rset.next()){
            nombreArchivo = rset.getString("NOMBRE_ARCHIVO");
            dataArchivo = rset.getBlob("ARCHIVO");
        }
        
        return new ArchivoExtension(dataArchivo, nombreArchivo);
    }
    
}
