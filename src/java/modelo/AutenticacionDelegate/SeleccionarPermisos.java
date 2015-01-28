/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.AutenticacionDelegate;

import java.sql.Connection;
import java.sql.ResultSet;
import modelo.DbManager;

/**
 *
 * @author illustrato
 */
public class SeleccionarPermisos {
    
    private DbManager db;
    private Connection conn;
    

    public SeleccionarPermisos() {
        db = new DbManager();        
        conn = db.conectar();
    }
    
    
    public ResultSet getModulos(){
        
        String query = "SELECT PK_CODIGO, UPPER(VAR_MODULO) AS  VAR_MODULO FROM TB_MODULOS";
        
        ResultSet rset = db.ejecutar(conn, query);
                
        return rset;
    }
    
    public ResultSet getPantallas(String rol, String modulo){
        String query = "       SELECT\n" +
                        "	PAN.PK_CODIGO,\n" +
                        "	PAN.VAR_PANTALLA,\n" +
                        "	VAR_DESCRIPCION,\n" +
                        "	PAN.FK_MODULO,\n" +
                        "	'NO' AS EXISTE\n" +
                        "	FROM\n" +
                        "	 SCV.TB_PANTALLAS PAN \n" +
                        "	WHERE\n" +
                        "		PAN.PK_CODIGO NOT IN (SELECT PROL.FK_PANTALLA FROM SCV.TB_PANTALLAS_ROLES PROL WHERE PROL.FK_ROL = " + rol+ ")   \n" +
                        "        AND PAN.FK_MODULO = "+ modulo+                        
                        " UNION\n" +
                        "	SELECT\n" +
                        "	PAN.PK_CODIGO,\n" +
                        "	PAN.VAR_PANTALLA,\n" +
                        "	VAR_DESCRIPCION,\n" +
                        "	PAN.FK_MODULO,\n" +
                        "	'SI' AS EXISTE\n" +                        
                        "	FROM\n" +
                        "	 SCV.TB_PANTALLAS PAN \n" +
                        "	WHERE\n" +
                        "	 PAN.PK_CODIGO IN (SELECT PROL.FK_PANTALLA FROM SCV.TB_PANTALLAS_ROLES PROL WHERE PROL.FK_ROL =  " + rol+ ") \n" +
                        "        AND PAN.FK_MODULO = "+ modulo+       
                        " ORDER BY FK_MODULO ASC";
        
        ResultSet rset = db.ejecutar(conn, query);
                
        return rset;
    }
    
    public void desconectar()
    {
        db.desconectar(this.conn);
    }
    
}
