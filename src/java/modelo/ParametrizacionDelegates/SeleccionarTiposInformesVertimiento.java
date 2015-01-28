package modelo.ParametrizacionDelegates;

import java.sql.Connection;
import java.sql.ResultSet;
import modelo.*;


public class SeleccionarTiposInformesVertimiento {
    
   //Atributos
    private DbManager db;
    private Connection conn;
    
    /**
     * 
     * Constructor
     * 
     */
    public SeleccionarTiposInformesVertimiento() {
        db = new DbManager();
        conn = db.conectar();
    }
    
    
    
    
    /**
     * 
     * Obtiene la informacion de las un Tipo Informe de vertimientos de la bd
     * y la devuelve en un ResultSet
     * 
     * @return rset 
     */
    public ResultSet getTiposInformes(String descripcion){
    
        String query = "SELECT *FROM TB_TIPOS_INFORMES WHERE CODIGO IS NOT NULL";
        if(!("").equals(descripcion) && descripcion != null){

            query += " AND DESCRIPCION LIKE '%" + descripcion + "%'";

        }       
        
        ResultSet rset = db.ejecutar(conn, query);

        return rset;
        
    }
    //-----------------------------------------------------------------------------
    
    
    
    
    /**
     * 
     * Obtiene la informacion de un Tipo Informe de vertimientos
     * 
     * @param codigo
     * @return rset 
     */
    public ResultSet getTipoInforme(int codigo){
    
        String query = "SELECT *FROM TB_TIPOS_INFORMES WHERE CODIGO = " + codigo;
        
        ResultSet rset = db.ejecutar(conn, query);

        return rset;
        
    }
    
    
       /**
     * 
     * Obtiene la informacion de un Tipo Informe de vertimientos
     * 
     * @param codigo
     * @return rset 
     */
    public ResultSet getTipoInformesPrincipal(){
    
        String query = "SELECT *FROM TB_TIPOS_INFORMES WHERE CODIGO IN (4,3)";
        ResultSet rset = db.ejecutar(conn, query);

        return rset;
        
    }
    //-----------------------------------------------------------------------------
    
    public void desconectar()
    {
        db.desconectar(this.conn);
    }
}
