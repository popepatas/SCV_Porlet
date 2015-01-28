package modelo.ProcesoVertimientosDelegates;

import modelo.ParametrizacionDelegates.*;
import java.sql.Connection;
import java.sql.ResultSet;
import modelo.*;


public class SeleccionarAnexoVisita {
    
   //Atributos
    private DbManager db;
    private Connection conn;
    
    /**
     * 
     * Constructor
     * 
     */
    public SeleccionarAnexoVisita() {
        db = new DbManager();
        conn = db.conectar();
    }
    
    
    

    /**
     * 
     * Obtiene la informacion de una unidad de medida
     * 
     * @param codigo
     * @return rset 
     */
    public ResultSet getAnexoVisita(int codigoArchivo){
    
        String query = "SELECT *FROM TB_ARCHIVOS_ADJUNTOS_VISITAS WHERE CODIGO = " + codigoArchivo;
        
        ResultSet rset = db.ejecutar(conn, query);

        return rset;
        
    }
    
     public ResultSet getArchivosCargados(int codigoVisita, Integer codigoArchivo){
     
        String query = "SELECT CODIGO,\n" +
                        "  FK_VISITA as COD_VISITA,\n" +                        
                        "  SUBSTR(NOMBRE_ARCHIVO,2 ,length(NOMBRE_ARCHIVO))  AS NOMBRE_ARCHIVO,\n" +
                        "  FECHA_CREACION,\n" +
                        "  ARCHIVO\n"+
                        "FROM TB_ARCHIVOS_ADJUNTOS_VISITAS\n"+
                        "WHERE\n"+
                        "FK_VISITA = " + codigoVisita;
       
        if(codigoArchivo != null  ){
        
                    query += " AND CODIGO = " + codigoArchivo;
        }
          
        
        ResultSet rset = db.ejecutar(conn, query);
                
        return rset;
       
    }
    
    //-----------------------------------------------------------------------------
    
    
    public void desconectar()
    {
        db.desconectar(this.conn);
    }
    
}
