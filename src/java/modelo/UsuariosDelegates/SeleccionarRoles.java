/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.UsuariosDelegates;

import java.sql.Connection;
import java.sql.ResultSet;
import modelo.DbManager;

/**
 *
 * @author illustrato
 */
public class SeleccionarRoles {
 
     //Atributos
    private DbManager db;
    private Connection conn;
    
    /**
     * 
     * Constructor
     * 
     */
    public SeleccionarRoles() {
        db = new DbManager();
        conn = db.conectar();
    }
    
    
       public ResultSet getRoles(String ciiu, String descripcion){
            
        String query =     "SELECT\n" +
                            "  PK_CODIGO,\n" +
                            "  VAR_ROL\n" +
                            "FROM\n" +
                            "  TB_ROLES WHERE PK_CODIGO IS NOT NULL ";
        
       
        query += " ORDER BY VAR_ROL  ASC";
        ResultSet rset = db.ejecutar(conn, query);
        
        return rset;
        
    }
    
      public void desconectar( )
  {
        db.desconectar(this.conn);
  }
    
}
