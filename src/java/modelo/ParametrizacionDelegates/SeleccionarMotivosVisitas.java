package modelo.ParametrizacionDelegates;

import java.sql.Connection;
import java.sql.ResultSet;
import modelo.*;


public class SeleccionarMotivosVisitas {
    
   //Atributos
    private DbManager db;
    private Connection conn;
    
    
    /**
     * 
     * Constructor
     * 
     */
    public SeleccionarMotivosVisitas() {
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
    public ResultSet getMotivosVisitas(String descripcion){
    
        String query = "SELECT *FROM TB_MOTIVOS_VISITAS WHERE CODIGO IS NOT NULL";
        
        if(!("").equals(descripcion) && descripcion != null){

            query += " AND DESCRIPCION LIKE '%" + descripcion + "%'";

        }
        
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
    public ResultSet getMotivoVisita(int codigo){
    
        String query = "SELECT *FROM TB_MOTIVOS_VISITAS WHERE CODIGO = " + codigo;
        
        ResultSet rset = db.ejecutar(conn, query);

        return rset;
        
    }
    //-----------------------------------------------------------------------------
    
    
    public void desconectar()
    {
        db.desconectar(this.conn);
    }
}
