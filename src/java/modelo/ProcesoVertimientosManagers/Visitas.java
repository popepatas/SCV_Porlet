/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.ProcesoVertimientosManagers;

import Extensions.ArchivoExtension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import modelo.ProcesoVertimientosDelegates.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author illustrato
 */
public class Visitas {
  
    
    
    
    
    public JSONArray getVisitasPorProceso(String filaInicio, String filaFin, String tipoVisita, String fechaInicial, String fechaFinal, String codigoProceso, String estadoVisita, String contrato, String nit, String razonSocial, String motivoVisita) throws SQLException{
        
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        ResultSet rset ;
        
          
        SeleccionarVisitas select = new SeleccionarVisitas(filaInicio, filaFin, tipoVisita, fechaInicial, fechaFinal, codigoProceso, estadoVisita,contrato,nit,razonSocial,motivoVisita);
                
        rset = select.getVisitas();
        
                                            
        while(rset.next()){
            
            jsonObject.put("codigo", rset.getString("CODIGO"));
            jsonObject.put("nombre",  rset.getString("NOMBRES"));
            jsonObject.put("apellidos", rset.getString("APELLIDOS"));
            jsonObject.put("fecha_visita", rset.getString("FECHA_VISITA"));
            jsonObject.put("motivo", rset.getString("MOVITO"));
            jsonObject.put("tipovisita", rset.getString("TIPOVISITA"));
            jsonObject.put("estado", rset.getString("ESTADO"));
            jsonObject.put("total", rset.getString("total_rows"));
            jsonObject.put("resultado", rset.getString("Resultado"));
            jsonObject.put("contrato", rset.getString("CONTRATO"));
            jsonObject.put("razon_social", rset.getString("RAZON_SOCIAL"));
            jsonObject.put("codigoProceso", rset.getString("CODPROCESO"));
            jsonObject.put("tecnicoVisito", rset.getString("TECNICO_VISITO"));
        
            jsonArray.add(jsonObject.clone());
            
        }
        
        jsonArreglo.add(jsonArray);
        
        //Se cierra el ResultSet
        select.desconectar();
        
        return  jsonArreglo;
        
    }
    
     /* 
     * @param filaInicio
     * @param filaFin
     * @param tipoVisita
     * @param fechaInicial
     * @param fechaFinal
     * @param codigoProceso
     * @param estadoVisita
     * @param contrato
     * @param nit
     * @param razonSocial
     * @param programacion
     * @param comuna
     *
     * @return JSONArray
     */
    public JSONArray getVisitasPendientes(String filaInicio, String filaFin, String codigoProceso, String contrato, String nit, String razonSocial, String comuna, String direccion) throws SQLException{
        
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        ResultSet rset ;
        
          
        SeleccionarVisitas select = new SeleccionarVisitas(filaInicio, filaFin, codigoProceso, contrato, nit, razonSocial, comuna, direccion);
                
        rset = select.getVisitasPendientes();
        
                                            
        while(rset.next()){
            
            jsonObject.put("codigoProceso", rset.getString("CODPROCESO"));
            jsonObject.put("contrato",  rset.getString("CONTRATO"));
            jsonObject.put("nit", rset.getString("NIT"));
            jsonObject.put("razon_social", rset.getString("RAZON_SOCIAL"));
            jsonObject.put("comuna", rset.getString("COMUNA"));
            jsonObject.put("direccion", rset.getString("DIRECCION"));
        
            jsonArray.add(jsonObject.clone());
            
        }
        
        jsonArreglo.add(jsonArray);
        
        //Se cierra el ResultSet
        select.desconectar();
        
        return  jsonArreglo;
        
    }
    
    public JSONObject getContVisitasPendientes(String codigoProceso) throws SQLException{
       
         ResultSet rset;
         JSONObject jsonObject = new JSONObject();
               
               
       SeleccionarVisitas select = new SeleccionarVisitas();     
       
       rset = select.getContadorVisitasPendientes(codigoProceso);
       
                                            
        while(rset.next()){
            
            jsonObject.put("count", rset.getString("COUNT"));
        
            
        }
       
       select.desconectar();
       
       return jsonObject;
    
    }
    
    public JSONArray getVisita(String codigoProceso) throws Exception{
        
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        ResultSet rset ;
        
          
        SeleccionarVisitas select = new SeleccionarVisitas();
                
        rset = select.getVisita(codigoProceso);
        
                                            
        while(rset.next()){
            
            jsonObject.put("tecnico", rset.getString("FK_TECNICO"));
            jsonObject.put("motivo",  rset.getString("FK_MOTIVO_VISITA"));
            jsonObject.put("observaciones", rset.getString("OBSERVACIONES"));      
            jsonObject.put("fechaCita", rset.getString("FECHA_VISITA"));
            jsonObject.put("codigo",rset.getString("CODIGO"));
            jsonObject.put("estado", rset.getString("FK_ESTADO"));
            jsonArray.add(jsonObject.clone());
            
        }
        
        jsonArreglo.add(jsonArray);
        
        //Se cierra el ResultSet
        select.desconectar();
        
        return  jsonArreglo;
        
    }
    
    public JSONArray getFechasVisitasPorProceso(int codigoProceso, String fechaInicio, String fechaFin, String clase) throws SQLException{
    
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        ResultSet rset ;
        
        SeleccionarFechasVisita select = new SeleccionarFechasVisita();
                
        rset = select.getFechasVisitasPorProceso(codigoProceso, fechaInicio, fechaFin);
        
        while(rset.next()){
            jsonObject.put("title", "Visitas");
            jsonObject.put("id",  rset.getString("FECHA_VISITA"));
            jsonObject.put("start", rset.getString("FECHA_VISITA"));
            jsonObject.put("end", rset.getString("FECHA_VISITA"));
            jsonObject.put("className", clase);
        
            jsonArray.add(jsonObject.clone());
            
        }
        
        jsonArreglo.add(jsonArray);
        
        //Se cierra el ResultSet
        select.desconectar();
        
        return  jsonArreglo;
    }
    
    public JSONArray getVisitasPorProceso(String codigoProceso) throws SQLException{
        
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        ResultSet rset ;
        
          
        SeleccionarVisitas select = new SeleccionarVisitas();
                
        rset = select.getVisita(codigoProceso);
        
                                            
        while(rset.next()){
            
            jsonObject.put("codigo", rset.getString("CODIGO"));
            jsonObject.put("nombre",  rset.getString("NOMBRES"));
            jsonObject.put("apellidos", rset.getString("APELLIDOS"));
            jsonObject.put("fecha_visita", rset.getString("FECHA_VISITA"));
            jsonObject.put("motivo", rset.getString("MOVITO"));
            jsonObject.put("tipovisita", rset.getString("TIPOVISITA"));
            jsonObject.put("estado", rset.getString("ESTADO"));
            jsonObject.put("total", rset.getString("total_rows"));
            jsonObject.put("resultado", rset.getString("Resultado"));
        
            jsonArray.add(jsonObject.clone());
            
        }
        
        jsonArreglo.add(jsonArray);
        
        //Se cierra el ResultSet
        select.desconectar();
        
        return  jsonArreglo;
        
    }
    /**
     * 
     *@param tecnico
     * @param motivo
     * @param observacion
     * @param fechaVisita
     * @param proceso
     * @param tipoVisita
     * @return 
     * @throws java.lang.Exception 
     * 
     */
    public JSONArray insertar(int tecnico, int motivo, String observacion, String fechaVisita, int proceso, int tipoVisita) throws Exception{
        
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject(); 

        Integer resultado ;
        
        InsertarVisita insert = new InsertarVisita(tecnico, motivo, observacion, fechaVisita, proceso,tipoVisita);
        insert.ejecutar();

        resultado = insert.getResultado();

        jsonObject.put("resultado", resultado);

        jsonArray.add(jsonObject);
              
        return jsonArray;
        
        
    }
    
    public void actualizar(int tecnico, int motivo, String observacion, String fechaVisita, int codigo) throws Exception{
        
        ActualizarVisita update = new ActualizarVisita(tecnico, motivo, observacion, fechaVisita, codigo);
        update.ejecutar();              
        
    }
    
    
    /**
     * 
     * Organiza la informacion tomada de la BD en un mapa, para despues
     * ser insertado en el archivo de excel.
     * 
     * @param filaInicio
     * @param filaFin
     * @param tipoVisita
     * @param fechaInicial
     * @param fechaFinal
     * @param codigoProceso
     * @return
     * @throws SQLException 
     */
    private Map organizarInfoExcel(String filaInicio, String filaFin, String tipoVisita, String fechaInicial, 
            String fechaFinal, String codigoProceso, String estadoVisita, String contrato, String nit, String razonSocial, String motivoVisita) throws SQLException{
       
        //Obtenemos el ResultSet con la informacion
        SeleccionarVisitas select = new SeleccionarVisitas(filaInicio, filaFin, tipoVisita, fechaInicial, fechaFinal, codigoProceso,estadoVisita, contrato,nit,razonSocial, motivoVisita);
        ResultSet rset = select.getVisitas();
        
        //Creamos el mapa y organizamos la cabecera
        Map<String, Object[]> data = new HashMap<String, Object[]>();
                        
        
        //Obtenemos la informacion del rset y la guardamos en el mapa.
        int contador = 0;

        String strContador="";
        
        while(rset.next()){
            strContador = String.valueOf(contador);
           
            data.put(strContador, new Object[] {rset.getString("CODIGO"),rset.getString("NOMBRES"),rset.getString("APELLIDOS"),
                rset.getString("FECHA_VISITA"),rset.getString("MOVITO"),rset.getString("TIPOVISITA"),rset.getString("ESTADO")});
            
            contador++;
        }
        

        
        select.desconectar();
        
        return data;
    
    }
    
       /**
     * 
     * Organiza la informacion tomada de la BD en un mapa, para despues
     * ser insertado en el archivo de excel.
     * 
     * @param filaInicio
     * @param filaFin
     * @param tipoVisita
     * @param fechaInicial
     * @param fechaFinal
     * @param codigoProceso
     * @return
     * @throws SQLException 
     */
    private Map organizarInfoExcel(String filaInicio, String filaFin, 
             String codigoProceso, String contrato, String nit, String razonSocial, String comuna, String direccion) throws SQLException{
       
        //Obtenemos el ResultSet con la informacion
        SeleccionarVisitas select = new SeleccionarVisitas(filaInicio, filaFin, codigoProceso, contrato, nit, razonSocial, comuna, direccion);
        ResultSet rset = select.getVisitasPendientes();
        
        //Creamos el mapa y organizamos la cabecera
        Map<String, Object[]> data = new HashMap<String, Object[]>();
                        
        
        //Obtenemos la informacion del rset y la guardamos en el mapa.
        int contador = 0;

        String strContador="";
        
        while(rset.next()){
            strContador = String.valueOf(contador);
           
            data.put(strContador, new Object[] {rset.getString("CODPROCESO"),rset.getString("CONTRATO"),rset.getString("NIT"),rset.getString("RAZON_SOCIAL"),rset.getString("COMUNA"), rset.getString("DIRECCION")});
            
            contador++;
        }
        

        
        select.desconectar();
        
        return data;
    
    }
    
    
    /**
     * 
     * Escribe la informacion de la visita en el archivo de Excel.
     * 
     * @param filaInicio
     * @param filaFin
     * @param tipoVisita
     * @param fechaInicial
     * @param fechaFinal
     * @param codigoProceso
     * @param url
     * @throws SQLException
     * @throws IOException 
     */
    public void escribirExcel(String filaInicio, String filaFin, String tipoVisita, String fechaInicial, 
            String fechaFinal, String codigoProceso, String url, String estadoVisita,String contrato, String nit, String razonSocial, String motivoVisita) throws SQLException, IOException{
    
 
        //Instanciamos los objetos para el excel
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Visitas");
        
        //Obtenemos el mapa con la informacion
        Map datos = organizarInfoExcel(filaInicio, filaFin, tipoVisita, 
                fechaInicial, fechaFinal, codigoProceso, estadoVisita, contrato, nit, razonSocial, motivoVisita);
        
        //Informacion de los titulos
        Map<String, Object[]> data = new HashMap<String, Object[]>();
        
        //Guardamos la cabecera del excel.
        data.put(String.valueOf(0), new Object[] {"CODIGO", "NOMBRES", "APELLIDOS",
                            "FECHA VISITA","MOTIVO","TIPO VISITA","ESTADO"});
        
        //Escribimos el titulo
        
        Font font = workbook.createFont();
        CellStyle style = workbook.createCellStyle();
        style.setFont(font);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);

        Set<String> keyset2 = data.keySet();
        for (String key : keyset2) {
            Row row = sheet.createRow(0);
            Object [] objArr = (Object [])data.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {

                Cell cell = row.createCell(cellnum++);
                cell.setCellStyle(style);

                if(obj instanceof Date) 
                    cell.setCellValue((Date)obj);
                else if(obj instanceof Boolean)
                    cell.setCellValue((Boolean)obj);
                else if(obj instanceof String)
                    cell.setCellValue((String)obj);
                else if(obj instanceof Double)
                    cell.setCellValue((Double)obj);
            }
        }
        
        //Obtenemos la informacion del mapa e insertamos la informacion en el excel
        Set<String> keyset = datos.keySet();
        int rownum = 1;
        for (String key : keyset) {
            Row row = sheet.createRow(rownum++);
            Object [] objArr = (Object [])datos.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);
                if(obj instanceof Date) 
                    cell.setCellValue((Date)obj);
                else if(obj instanceof Boolean)
                    cell.setCellValue((Boolean)obj);
                else if(obj instanceof String)
                    cell.setCellValue((String)obj);
                else if(obj instanceof Double)
                    cell.setCellValue((Double)obj);
            }
        }


                
        //Guardamos el archivo en el disco.
          FileOutputStream out = 
            new FileOutputStream(new File(url));
        workbook.write(out);
        out.close();
        
    }
    
      public void escribirExcelPendientes(String filaInicio, String filaFin, 
            String codigoProceso, String url, String contrato, String nit, String razonSocial, String comuna, String direccion) throws SQLException, IOException{
    
 
        //Instanciamos los objetos para el excel
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("VisitasPendientes");
        
        //Obtenemos el mapa con la informacion
        Map datos = organizarInfoExcel(filaInicio, filaFin,  
                 codigoProceso,  contrato, nit, razonSocial, comuna, direccion);
        
        //Informacion de los titulos
        Map<String, Object[]> data = new HashMap<String, Object[]>();
        
        //Guardamos la cabecera del excel.
        data.put(String.valueOf(0), new Object[] {"CODIGO PROCESO", "CONTRATO", "NIT","RAZÓN SOCIAL", "COMUNA", "DIRECCION"});
        
        //Escribimos el titulo
        Set<String> keyset2 = data.keySet();
        for (String key : keyset2) {
            Row row = sheet.createRow(0);
            Object [] objArr = (Object [])data.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);
                if(obj instanceof Date) 
                    cell.setCellValue((Date)obj);
                else if(obj instanceof Boolean)
                    cell.setCellValue((Boolean)obj);
                else if(obj instanceof String)
                    cell.setCellValue((String)obj);
                else if(obj instanceof Double)
                    cell.setCellValue((Double)obj);
            }
        }
        
        //Obtenemos la informacion del mapa e insertamos la informacion en el excel
        Set<String> keyset = datos.keySet();
        int rownum = 1;
        for (String key : keyset) {
            Row row = sheet.createRow(rownum++);
            Object [] objArr = (Object [])datos.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);
                if(obj instanceof Date) 
                    cell.setCellValue((Date)obj);
                else if(obj instanceof Boolean)
                    cell.setCellValue((Boolean)obj);
                else if(obj instanceof String)
                    cell.setCellValue((String)obj);
                else if(obj instanceof Double)
                    cell.setCellValue((Double)obj);
            }
        }


                
        //Guardamos el archivo en el disco.
          FileOutputStream out = 
            new FileOutputStream(new File(url));
        workbook.write(out);
        out.close();
        
    }
  
    public void insertarResultadoVisita(int tecnico, int codigo, String resultado, String chkResultado) throws Exception
    {
        
        InsertarResultadoVisita insert = new InsertarResultadoVisita(tecnico, codigo, resultado, chkResultado);
        insert.ejecutar();
        
    
    }
    
        public JSONArray obtenerResultadoVisita(String codigoVisita) throws Exception{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarResultadoVisita select = new SeleccionarResultadoVisita();
        ResultSet rset = select.obtenerResultadoVisita(codigoVisita);
        //Creamos los JSONArray para guardar los objetos JSON
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        
        //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos
        //en el JSONArray.
        while(rset.next()){
            
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tecnicoVisito", rset.getString("FK_TECNICO_VISITO"));
            jsonObject.put("resultado", rset.getString("RESULTADO"));
            jsonObject.put("reprogramar", rset.getString("REPROGRAMAR"));

            
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
            
        }
        
        jsonArreglo.add(jsonArray);
        select.desconectar();
        
        return jsonArreglo;
        
    }
    
    public void insertarAnexoVisita(int codigoVisita, File archivo, String nombreArchivo) throws Exception{
        
        InsertarAnexoVisita insert = new InsertarAnexoVisita(codigoVisita, archivo, nombreArchivo);
        insert.ejecutar();
        
    }
    
    public JSONArray eliminarAnexoVisita(int codigoAnexo) throws Exception{

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject(); 

        Integer respError ;

        EliminarAnexoVisita delete = new EliminarAnexoVisita(codigoAnexo);
        delete.ejecutar();

        respError = delete.getError();

        jsonObject.put("error", respError);

        jsonArray.add(jsonObject);

        return jsonArray;
    }
    
     public JSONArray getArchivosCargados(int codigoVisita, Integer codigo) throws SQLException{
       
       ResultSet rset;
       
       JSONObject jsonObject = new JSONObject();
       JSONArray  jsonArray = new JSONArray();
       JSONArray  jsonArreglo = new JSONArray();
       SeleccionarAnexoVisita select = new SeleccionarAnexoVisita();
       
       rset = select.getArchivosCargados(codigoVisita, codigo);
       
                                            
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
    
    public ArchivoExtension getAnexoVisita(int codigoArchivo) throws SQLException{
        
        SeleccionarAnexoVisita select = new SeleccionarAnexoVisita();
        ResultSet rset = select.getAnexoVisita(codigoArchivo);
        
        String nombreArchivo = null;
        Blob dataArchivo = null;
        
        while(rset.next()){
            nombreArchivo = rset.getString("NOMBRE_ARCHIVO");
            dataArchivo = rset.getBlob("ARCHIVO");
        }
        
        return new ArchivoExtension(dataArchivo, nombreArchivo);
    }
}







