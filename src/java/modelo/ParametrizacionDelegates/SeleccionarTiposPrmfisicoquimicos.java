package modelo.ParametrizacionDelegates;

import java.sql.Connection;
import java.sql.ResultSet;
import modelo.*;


public class SeleccionarTiposPrmfisicoquimicos {
    
    //Atributos
    private DbManager db;
    private Connection conn;
    
    /**
     * 
     * Constructor
     * 
     */
    public SeleccionarTiposPrmfisicoquimicos() {
        db = new DbManager();
        conn = db.conectar();
    }
    
    
    
    
    /**
     * 
     * Obtiene la informacion de los tipos de parametro fisicoquimicos de la bd
     * y la devuelve en un ResultSet
     * 
     * @return rset 
     */
    public ResultSet getTiposPrmfisicoquimicos(String descripcion){
    
        String query = "SELECT *FROM TB_TIPOS_PARM_FISICOQUIMICOS WHERE CODIGO IS NOT NULL";
        if(!("").equals(descripcion) && descripcion != null){

            query += " AND DESCRIPCION LIKE '%" + descripcion.toUpperCase() + "%'";

        }
        ResultSet rset = db.ejecutar(conn, query);

        return rset;
        
    }
    //-----------------------------------------------------------------------------
    
    
    
    
    /**
     * 
     * Obtiene la informacion de un tipo de parametro fisicoquimico
     * 
     * @param codigo
     * @return rset 
     */
    public ResultSet getTipoPrmfisicoquimico(int codigo){
    
        String query = "SELECT *FROM TB_TIPOS_PARM_FISICOQUIMICOS WHERE CODIGO = " + codigo;
        
        ResultSet rset = db.ejecutar(conn, query);

        return rset;
        
    }
    //-----------------------------------------------------------------------------
    
    
    public void desconectar()
    {
        db.desconectar(this.conn);
    }
}
