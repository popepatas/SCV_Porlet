package modelo.ParametrizacionDelegates;

import configuracion.Queries;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import modelo.DbManager;

/**
 *
 * @author illustrato
 */
public class SeleccionarPuntosVertimiento {
    //Atributos
    private DbManager db;
    private Connection conn;
    
    /**
     * 
     * Constructor
     * 
     */
    public SeleccionarPuntosVertimiento() {
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
    public ResultSet getPuntosVertimiento(String contrato){
    
        String cabecera = "SELECT pv.fk_contrato AS CONTRATO, COUNT(pv.fk_contrato) AS PUNTOS_ASOCIADOS FROM TB_PUNTOS_VERTIMIENTOS pv WHERE pv.CODIGO IS NOT NULL";
        
        if(!("").equals(contrato) && contrato != null){
            cabecera += " AND pv.fk_contrato = " + contrato;
        }

        String footer = " GROUP BY pv.fk_contrato";
        
        String query = cabecera + footer;
        
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
    public ResultSet getPuntoVertimiento(String codigo){
    
        String query = "SELECT * FROM TB_PUNTOS_VERTIMIENTOS WHERE FK_CONTRATO = "+codigo;
        
        ResultSet rset = db.ejecutar(conn, query);

        return rset;
        
    }
    //-----------------------------------------------------------------------------
    
    
    /**
     * 
     * Obtiene la informacion de los puntos de vertimiento
     * para alimentar la pantalla de Informacion Tecnica.
     * 
     * @param proceso
     * @return
     * @throws Exception 
     */
    public ResultSet getPuntosParaInfoTecnica(int proceso) throws Exception{
        
                
        CallableStatement callableStatement = null;
        
        try{

            //Preparamos y ejecutamos el procedimiento.
            callableStatement = conn.prepareCall(Queries
                    .getString("SelPuntoVertInfoTecnica"));

            //Preparamos los parametros
            callableStatement.setInt(1, proceso);
 

            //Ejecutamos el procedimiento
            ResultSet rset = callableStatement.executeQuery();

            return rset;
            
        }catch(Exception e){

            db.desconectar(conn);
            throw e;

        }
        
    }
    
    public void desconectar()
    {
        db.desconectar(this.conn);
    }
}
