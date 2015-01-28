package modelo.ProcesoVertimientosDelegates;

import modelo.ParametrizacionDelegates.*;
import java.sql.Connection;
import java.sql.ResultSet;
import modelo.*;


public class SeleccionarEntidadesLodos {
    
   //Atributos
    private DbManager db;
    private Connection conn;
    
    /**
     * 
     * Constructor
     * 
     */
    public SeleccionarEntidadesLodos() {
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
    public ResultSet getLodos(String codigo) throws Exception{
       
        try{
           
        
            String query = "SELECT\n" +
                            "* " +
                            "FROM TB_ENTIDADES_MANEJOS_LODOS lod\n" +
                            "JOIN TB_MONITOREOS mon ON lod.FK_MONITOREO = mon.CODIGO\n" +
                            "JOIN TB_PROCESOS_VERTIMIENTOS pv ON mon.FK_PROCESO_VERTIMIENTO = pv.CODIGO\n" +
                            "WHERE pv.CODIGO = " + codigo;

            ResultSet rset = db.ejecutar(conn, query);

            return rset;
        
        }
        catch(Exception e)
        {
           throw  e;
        }
    }
    //-----------------------------------------------------------------------------
    
    
    
    
    /**
     * 
     * Obtiene la informacion de una unidad de medida
     * 
     * @param codigo
     * @return rset 
     */
    public ResultSet getLodo(int codigo){
    
            String query = "SELECT *FROM TB_ENTIDADES_MANEJOS_LODOS WHERE CODIGO = " + codigo;
        
        ResultSet rset = db.ejecutar(conn, query);

        return rset;
        
    }
    //-----------------------------------------------------------------------------
 
    public void desconectar()
    {
        db.desconectar(this.conn);
    }
    
}
