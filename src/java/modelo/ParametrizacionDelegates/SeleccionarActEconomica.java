package modelo.ParametrizacionDelegates;

import java.sql.Connection;
import java.sql.ResultSet;
import modelo.*;


public class SeleccionarActEconomica {
    
   //Atributos
    private DbManager db;
    private Connection conn;
    
    /**
     * 
     * Constructor
     * 
     */
    public SeleccionarActEconomica() {
        db = new DbManager();
        conn = db.conectar();
    }
    
    
    
    
    /**
     * 
     * Obtiene la informacion de actividades economicas de la bd
     * y la devuelve en un ResultSet
     * 
     * @return rset 
     */
    public ResultSet getActEconomicas(String ciiu, String descripcion){
            
        String query = "SELECT * FROM TB_ACTIVIDADES_ECONOMICAS WHERE codigo IS NOT NULL";
        
        if(!("").equals(ciiu) && ciiu != null){
            
            query += " AND CODIGO_CIIU = "+ciiu;
            
        }
        
        if(!("").equals(descripcion) && descripcion != null){
            
            query += " AND UPPER(DESCRIPCION) LIKE UPPER('"+ descripcion + "%')";
            
        }
        query += " ORDER BY CODIGO_CIIU, DESCRIPCION ASC";
        ResultSet rset = db.ejecutar(conn, query);
        
        return rset;
        
    }
    //-----------------------------------------------------------------------------
    
    
    
    
    /**
     * 
     * Obtiene la informacion de una actividad economica especifica.
     * 
     * @param codigo
     * @return rset 
     */
    public ResultSet getActEconomica(int codigo){
    
        //Conectamos con la BD y ejecutamos la sentencia.
        String query = "SELECT * FROM TB_ACTIVIDADES_ECONOMICAS WHERE CODIGO = " + codigo;
        query += " ORDER BY CODIGO_CIIU, DESCRIPCION ASC";
        ResultSet rset = db.ejecutar(conn, query);
        
        return rset;
        
    }
    //-----------------------------------------------------------------------------
 
    public void desconectar( )
    {
        db.desconectar(this.conn);
    }
}
