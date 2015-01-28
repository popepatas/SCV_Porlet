/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.ProcesoVertimientosDelegates;

import java.sql.Connection;
import java.sql.ResultSet;
import modelo.DbManager;

/**
 *
 * @author illustrato
 */
public class SeleccionarArchivosCargadosSupervision {
    
   
    private DbManager db;
    private Connection conn;
    
    public SeleccionarArchivosCargadosSupervision(){
       
        db = new DbManager();
        conn = db.conectar();
    }
    
      
     public ResultSet getArchivosCargados(int codigoArchivo, int codigoMonitoreo){
     
        String query = "SELECT CODIGO,\n" +
                        "  FK_MONITOREO as COD_MONITOREO,\n" +                        
                        "  SUBSTR(NOMBRE_ARCHIVO,2 ,length(NOMBRE_ARCHIVO))  AS NOMBRE_ARCHIVO,\n" +
                        "  FECHA_CREACION,\n" +
                        "  ARCHIVO\n"+
                        "FROM TB_ARCHIVOS_SUPERVISIONES\n"+
                        "WHERE\n"+
                        "FK_MONITOREO = "+codigoMonitoreo;
       
        /*if(codigoArchivo != null  ){
        
                    query += " AND CODIGO = " + codigoArchivo;
        }*/
          
        
        ResultSet rset = db.ejecutar(conn, query);
                
        return rset;
       
    }
     
      public void desconectar()
      {
          db.desconectar(conn);
      }
     
}
