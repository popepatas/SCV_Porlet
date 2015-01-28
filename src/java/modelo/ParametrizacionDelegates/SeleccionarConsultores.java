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
public class SeleccionarConsultores {
      private DbManager db;
      private Connection conn;
      
    /**
     * 
     * Constructor
     * 
     */
    public SeleccionarConsultores() {
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
    public ResultSet getConsultores(String nombre ,String apellidos, String direccion, String telefono, String telefono2, String identificacion, String correo){

        String query = "SELECT\n" +
                        " CONSULTORES.CODIGO,\n" +
                        " CONSULTORES.NOMBRES,\n" +
                        " CONSULTORES.APELLIDOS,\n" +
                        " CONSULTORES.IDENTIFICACION,\n" +
                        " CONSULTORES.DIRECCION,\n" +
                        " CONSULTORES.TELEFONO1,\n" +
                        " CONSULTORES.TELEFONO2,\n" +
                        " CONSULTORES.EMAIL\n" +
                        " FROM\n" +
                        " TB_LABORATORIOS_CONSULTOR CONSULTORES\n" +
                        " WHERE\n" +
                        " CONSULTORES.FK_TIPO_ENTIDAD = 2";
        
        if(!("").equals(nombre) && nombre != null){

            query += " AND CONSULTORES.NOMBRES LIKE UPPER('%" + nombre + "%')";

        }
        if(!("").equals(apellidos) && apellidos != null){

            query += " AND CONSULTORES.APELLIDOS LIKE UPPER('%" + apellidos + "%')";

        }
        if(!("  #  - ").equals(direccion) && direccion != null){

            query += " AND CONSULTORES.DIRECCION LIKE UPPER('%" + direccion + "%')";

        }
        if(!("").equals(telefono) && telefono != null){

            query += " AND CONSULTORES.TELEFONO1 = " + telefono;

        }
        if(!("").equals(telefono2) && telefono2 != null){

            query += " AND CONSULTORES.TELEFONO2 = " + telefono2;

        }
        if(!("").equals(identificacion) && identificacion != null){

            query += " AND CONSULTORES.IDENTIFICACION LIKE '%" + identificacion + "%'";
        }
        if(!("").equals(correo) && correo != null){

            query += " AND UPPER(CONSULTORES.EMAIL) LIKE UPPER('%" + correo + "%')";
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
    public ResultSet getConsultor(int codigo){
    
        String query = "SELECT\n" +
                        " CONSULTORES.CODIGO,\n" +
                        " CONSULTORES.NOMBRES,\n" +
                        " CONSULTORES.APELLIDOS,\n" +
                        " CONSULTORES.IDENTIFICACION,\n" +
                        " CONSULTORES.DIRECCION,\n" +
                        " CONSULTORES.TELEFONO1,\n" +
                        " CONSULTORES.TELEFONO2,\n" +
                        " CONSULTORES.EMAIL\n" +
                        " FROM\n" +
                        " TB_LABORATORIOS_CONSULTOR CONSULTORES\n" +
                        " WHERE\n" +
                        " CONSULTORES.FK_TIPO_ENTIDAD = 2 AND "
                         + " CODIGO = " + codigo;
                
        ResultSet rset = db.ejecutar(conn, query);

        return rset;
        
    }
    
    public void desconectar()
    {
        db.desconectar(this.conn);
    }
}
