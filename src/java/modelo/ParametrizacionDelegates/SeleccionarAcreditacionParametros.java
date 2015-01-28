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
public class SeleccionarAcreditacionParametros {
     //Atributos
    private DbManager db;
    private Connection conn;
    
    /**
     * 
     * Constructor
     * 
     */
    public SeleccionarAcreditacionParametros() {
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
    public ResultSet getAcreditacionParametros(int codigo){
    
        String query = "SELECT *FROM TB_PUNTOS_VERTIMIENTOS WHERE FK_CONTRATO = "+codigo;
        
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
    public ResultSet getAcreditacionPorLab(String lab){
    
        String query = "SELECT pac.CODIGO AS CODIGO_ACREDITACION, pfis.CODIGO AS CODIGO_PARAM, pfis.DESCRIPCION AS PARAMETRO\n" +
                        "FROM TB_PARAMETROS_ACREDITACION pac\n" +
                        "JOIN TB_PARAM_FISICOQUIMICOS pfis ON pac.FK_PARAMETRO_FISQUIM = pfis.CODIGO\n" +
                        "WHERE pac.FK_LABORATORIO =  " + lab;
        
        ResultSet rset = db.ejecutar(conn, query);

        return rset;
        
    }
    //-----------------------------------------------------------------------------
    
    
    
    public void desconectar()
    {
        db.desconectar(this.conn);
    }
}
