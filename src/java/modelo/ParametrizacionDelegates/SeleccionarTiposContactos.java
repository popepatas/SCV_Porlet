/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.ParametrizacionDelegates;

import java.sql.Connection;
import java.sql.ResultSet;
import modelo.DbManager;

/**
 *
 * @author illustrato
 */
public class SeleccionarTiposContactos {
    
    /*Atributos*/
    private DbManager db;
    private Connection conn;
    
       /**
     * 
     * Constructor
     * 
     */
    public SeleccionarTiposContactos() {
        db = new DbManager();
        conn = db.conectar();
    }
    
    
      /**
     * 
     * Obtiene la informacion de los tipos de contactos de la bd
     * y la devuelve en un ResultSet
     * 
     * @return rset 
     */
    public ResultSet getTiposContactos(String descripcion){
        
        String query = "SELECT\n " +
                        "CODIGO,\n " +
                        "DESCRIPCION\n " +
                        "FROM\n " +
                        "TB_TIPOS_CONTACTOS\n" +
                        "WHERE CODIGO IS NOT NULL";
        
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
    
      public ResultSet getTipoContacto(int codigo){
        
        String query = "SELECT\n " +
                        "CODIGO,\n " +
                        "DESCRIPCION\n " +
                        "FROM\n " +
                        " TB_TIPOS_CONTACTOS"+
                        " WHERE "
                        + " CODIGO = " + codigo ;
        
        ResultSet rset = db.ejecutar(conn, query);

        return rset;
        
    }
      
    public void desconectar()
    {
        db.desconectar(this.conn);
    }
}
