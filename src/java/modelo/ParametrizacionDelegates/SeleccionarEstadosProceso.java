package modelo.ParametrizacionDelegates;

import java.sql.Connection;
import java.sql.ResultSet;
import modelo.*;


public class SeleccionarEstadosProceso {
    
   //Atributos
    private DbManager db;
    private Connection conn;
    
    /**
     * 
     * Constructor
     * 
     */
    public SeleccionarEstadosProceso() {
        db = new DbManager();
        conn = db.conectar();
    }
    
    
    
    
    /**
     * 
     * Obtiene la informacion de las unidades de medida de la bd
     * y la devuelve en un ResultSet
     * 
     * @return rset 
     */
    public ResultSet getEstados(String descripcion){
    
        String query = "SELECT *FROM TB_ESTADOS_PROCESOS WHERE CODIGO IS NOT NULL ";
        
        if(!("").equals(descripcion) && descripcion != null){
            query += " AND DESCRIPCION LIKE upper('%" + descripcion + "%')";
        }
           query += " ORDER BY DESCRIPCION ASC";
        ResultSet rset = db.ejecutar(conn, query);

        return rset;
        
    }
    //-----------------------------------------------------------------------------
    
    
    
    
    /**
     * 
     * Obtiene la informacion de una unidad de medida
     * 
     * @param codigo
     * @return rset 
     */
    public ResultSet getEstado(int codigo){
    
        String query = "SELECT *FROM TB_ESTADOS_PROCESOS WHERE CODIGO = " + codigo;
        
        ResultSet rset = db.ejecutar(conn, query);

        return rset;
        
    }
    //-----------------------------------------------------------------------------
    
    public void desconectar()
    {
        db.desconectar(this.conn);
    }
    
}
