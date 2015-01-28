package modelo.ParametrizacionDelegates;

import java.sql.Connection;
import java.sql.ResultSet;
import modelo.DbManager;

/**
 *
 * @author illustrato
 */
public class SeleccionarAsociacionContratos {
    //Atributos
    private DbManager db;
    private Connection conn;
    
    /**
     * 
     * Constructor
     * 
     */
    public SeleccionarAsociacionContratos() {
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
    public ResultSet getAsociaciones(String contrato){

        String cabecera = "SELECT COUNT(CONTRATO_PADRE) AS ASOCIACIONES, CONTRATO_PADRE AS CONTRATO FROM TB_CONTRATOS_ASIGNADOS WHERE CODIGO IS NOT NULL";
        
        
        if(!("").equals(contrato) && contrato != null){

            cabecera += " AND CONTRATO_PADRE = " + contrato;

        }
        
        String footer = " GROUP BY CONTRATO_PADRE";
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
    public ResultSet getAsociacion(int contrato){
    
        String query = "SELECT CONTRATO_PADRE, CONTRATO_HIJO, CODIGO FROM TB_CONTRATOS_ASIGNADOS WHERE CONTRATO_PADRE =  "+contrato;
        
        ResultSet rset = db.ejecutar(conn, query);

        return rset;
        
    }
    //-----------------------------------------------------------------------------
    
    
    public void desconectar()
    {
        db.desconectar(this.conn);
    }
}
