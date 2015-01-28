package modelo.ParametrizacionDelegates;

import java.sql.Connection;
import java.sql.ResultSet;
import modelo.*;


public class SeleccionarUnidadesMedida {
    
   //Atributos
    private DbManager db;
    private Connection conn;
    
    /**
     * 
     * Constructor
     * 
     */
    public SeleccionarUnidadesMedida() {
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
    public ResultSet getUnidadesMedida(String descripcion){
    
        String query = "SELECT *FROM TB_UNIDADES_MEDIDAS WHERE CODIGO IS NOT NULL";
        if(!("").equals(descripcion) && descripcion != null){

            query += " AND upper(DESCRIPCION) LIKE upper('%" + descripcion + "%')";

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
    public ResultSet getUnidadMedida(int codigo){
    
        String query = "SELECT *FROM TB_UNIDADES_MEDIDAS WHERE CODIGO = " + codigo;
        
        ResultSet rset = db.ejecutar(conn, query);

        return rset;
        
    }
    //-----------------------------------------------------------------------------
    
    
    public void desconectar()
    {
        db.desconectar(this.conn);
    }
    
}
