/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.ClientesDelegates;

import java.sql.Connection;
import java.sql.ResultSet;
import modelo.DbManager;

/**
 *
 * @author illustrato
 */
public class SeleccionarLogsCliente {
     //Atributos
    private DbManager db;
    private Connection conn;
    
    /**
     * 
     * Constructor
     * 
     */
    public SeleccionarLogsCliente() {
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
    public ResultSet getLogsCliente(int codigo, String tipo_campo){
    
        String query = " SELECT \n" +
                        "  CODIGO,\n" +
                        "  CAMPO,\n" +
                        "  VALOR_ANTES,\n" +
                        "  VALOR_DESPUES,\n" +
                        "  CODIGO_CLIENTE,\n" +
                        "  to_char(FECHA_MODIFICACION,'DD/MM/YYYY') AS FECHA_MODIFICACION\n" +
                        " FROM TB_LOGS_CLIENTES \n" +
                        " WHERE\n" +
                        "  CODIGO_CLIENTE = " + codigo+
                        "   AND trim(upper(CAMPO)) = trim(upper('"+ tipo_campo+ "'))"+
                        " ORDER BY CAMPO, FECHA_MODIFICACION DESC";
                           
        
        ResultSet rset = db.ejecutar(conn, query);
        
        return rset;
        
    }
    
    public void desconectar()
    {
        db.desconectar(this.conn);
    }
}
