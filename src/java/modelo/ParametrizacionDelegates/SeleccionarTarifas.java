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
public class SeleccionarTarifas {
    
    //Atributos
    private DbManager db;
    private Connection conn;
    
    
    /**
     * 
     * Constructor
     * 
     */
    public SeleccionarTarifas() {
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
    public ResultSet getTarifas(){
    
        String query = "SELECT\n" +
                            " TAR.CODIGO,\n" +
                            " TAR.VALOR,\n" +
                            " TAR.FK_PARAMFISICOQUIMICO,\n" +
                            " PARM.DESCRIPCION AS DESPARM\n" +
                            " FROM\n" +
                            "  TB_TARIFAS TAR\n" +
                            " INNER JOIN  TB_PARAM_FISICOQUIMICOS PARM ON TAR.FK_PARAMFISICOQUIMICO = PARM.CODIGO";
        
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
    public ResultSet getTarifa(int codigo){
    
        String query = "SELECT\n" +
                            " TAR.CODIGO,\n" +
                            " TAR.VALOR,\n" +
                            " TAR.FK_PARAMFISICOQUIMICO,\n" +
                            " PARM.DESCRIPCION AS DESPARM\n" +
                            " FROM\n" +
                            "  TB_TARIFAS TAR\n" +
                            " INNER JOIN  TB_PARAM_FISICOQUIMICOS PARM ON TAR.FK_PARAMFISICOQUIMICO = PARM.CODIGO"
                            +" WHERE TAR.CODIGO = "+ codigo;
        
        ResultSet rset = db.ejecutar(conn, query);

        return rset;
        
    }
    //-----------------------------------------------------------------------------
    
    
    public void desconectar()
    {
        db.desconectar(this.conn);
    }
}
