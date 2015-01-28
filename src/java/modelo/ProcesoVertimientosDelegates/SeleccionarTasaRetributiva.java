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
public class SeleccionarTasaRetributiva {
     //Atributos
    private DbManager db;
    private Connection conn;
    
    /**
     * 
     * Constructor
     * 
     */
    public SeleccionarTasaRetributiva() {
        db = new DbManager();
        conn = db.conectar();
    }
    
    
    
    
    /**
     * 
     * Obtiene la informacion de las cargas y tasas de la bd
     * y la devuelve en un ResultSet
     * 
     * @return rset 
     */
    public ResultSet getTasaRetributiva(int codigoProceso){
        
        String query = "SELECT\n" +
                        "TR.CODIGO,\n" +
                        "TR.PK_PARAM_FISCOQUIM,\n" +
                        "TR.VALOR_TARIFA,\n" +
                        "TR.PORCENTAJE_REMOCION,\n" +
                        "TR.PK_PROCESO_VERTIMIENTOS,\n" +
                        "TR.VALOR_TASA_RETIBUTIVA,\n" +
                        "TR.TOTAL_CARGA,\n" +
                        "MON.VALOR_TASA_RETRIBUTIVA,\n" +
                        "MON.VALOR_COBRADO_TASA_RETRIBUTIVA\n" +
                        "FROM\n" +
                        " TB_PROCESOS_VERTIMIENTOS PV\n" +
                        "INNER JOIN  TB_MONITOREOS MON ON PV.CODIGO = MON.FK_PROCESO_VERTIMIENTO\n" +
                        "INNER JOIN  TB_TASA_RETRIBUTIVA TR ON PV.CODIGO = TR.PK_PROCESO_VERTIMIENTOS\n"+
                        "WHERE\n"+
                        "PV.CODIGO = " + codigoProceso + " AND  MON.FK_ESTADO = 1";
                    
        
        ResultSet rset = db.ejecutar(conn, query);

        return rset;
        
    }
      
    //-----------------------------------------------------------------------------
    
    
    public void desconectar()
    {
        db.desconectar(this.conn);
    }
}
