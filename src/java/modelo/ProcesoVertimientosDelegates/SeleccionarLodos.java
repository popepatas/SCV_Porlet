package modelo.ProcesoVertimientosDelegates;

import modelo.ParametrizacionDelegates.*;
import java.sql.Connection;
import java.sql.ResultSet;
import modelo.*;


public class SeleccionarLodos {
    
   //Atributos
    private DbManager db;
    private Connection conn;
    
    /**
     * 
     * Constructor
     * 
     */
    public SeleccionarLodos() {
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
    public ResultSet getLodo(int codigo){
    
            String query = "SELECT\n" +
                            "JORNADA_PRODUCTIVA_DIAS,\n" +
                            "JORNADA_PRODUCTIVA_HORAS,\n" +
                            "PRE_TRATAMIENTO,\n" +
                            "LODOS_ACTIVIDAD_PRODUCTIVA,\n" +
                            "OTROS_PRETRATAMIENTO,\n" +
                            "OTROS_ACTIVIDAD_PROD,\n" +
                            "OTROS_CUAL_ACT_PROD\n" + 
                            "FROM TB_MONITOREOS \n" +
                            "WHERE FK_PROCESO_VERTIMIENTO = "+codigo+" AND FK_ESTADO = 1";
        
        ResultSet rset = db.ejecutar(conn, query);

        return rset;
        
    }
    //-----------------------------------------------------------------------------
    
    public ResultSet getArchivosLodos(int codigo){
        String query = "SELECT CODIGO,\n" +
                        "NOMBRE_ARCHIVO\n" +
                        "FROM TB_ARCHIVOS_LODOS\n" +
                        "WHERE FK_PROCESO_VERTIMIENTO = " + codigo;
        
        ResultSet rset = db.ejecutar(conn, query);

        return rset;
    }
 
        public ResultSet getAnexoLodos(int codigoArchivo){
    
        String query = "SELECT ARCHIVO,\n" +
                        "NOMBRE_ARCHIVO\n" +
                        "FROM TB_ARCHIVOS_LODOS\n" +
                        "WHERE CODIGO = " + codigoArchivo;
        
        ResultSet rset = db.ejecutar(conn, query);

        return rset;
        
    }
    public void desconectar()
    {
        db.desconectar(this.conn);
    }
    
}
