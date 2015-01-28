/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.ProcesoVertimientosDelegates;

import java.sql.Connection;
import java.sql.ResultSet;
import modelo.DbManager;

/**
 *
 * @author illustrato
 */
public class SeleccionarFechasVisita {
    
    
    private DbManager db;
    private Connection conn;
    
    
    public SeleccionarFechasVisita(){
        
        this.db = new DbManager();
        conn = db.conectar();
        
    }
    
     

    public ResultSet getFechasVisitasPorProceso(int codigoProceso, String fechaInicio, String fechaFin) {

        String query = "SELECT\n" +
                        "VSTA.FECHA_VISITA\n" +
                        "FROM\n" +
                        " TB_PROCESOS_VERTIMIENTOS PV\n" +
                        "INNER JOIN  TB_VISITAS VSTA ON PV.CODIGO = VSTA.FK_PROCESO_VERTIMIENTO\n" +
                        "WHERE\n"+
                        "VSTA.FECHA_VISITA  BETWEEN to_date('"+fechaInicio +"','DD/MM/YYYY') AND to_date('"+ fechaFin +"','DD/MM/YYYY')\n"+
                        " AND VSTA.FK_PROCESO_VERTIMIENTO = " + codigoProceso+"\n"+
                        " GROUP BY\n" + 
                        "VSTA.FECHA_VISITA\n" +
                        "ORDER BY VSTA.FECHA_VISITA ASC";
                
            ResultSet rset = db.ejecutar(conn, query);
                                  
            return rset;

    }
    
    public void desconectar()
    {
        db.desconectar(this.conn);
    }
}

