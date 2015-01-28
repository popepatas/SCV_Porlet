package modelo.ProcesoVertimientosDelegates;

import modelo.ParametrizacionDelegates.*;
import java.sql.Connection;
import java.sql.ResultSet;
import modelo.*;


public class SeleccionarResultadoVisita {
    
   //Atributos
    private DbManager db;
    private Connection conn;
    
    /**
     * 
     * Constructor
     * 
     */
    public SeleccionarResultadoVisita() {
        db = new DbManager();
        conn = db.conectar();
    }
    
 
    public ResultSet obtenerResultadoVisita(String codioVisita)
    {
        String query = "  SELECT  FK_TECNICO_VISITO,\n" +
                        "          RESULTADO,\n" +
                        "          REPROGRAMAR\n" +
                        "  FROM TB_VISITAS\n" +
                        "  WHERE CODIGO = " + codioVisita;
        
        ResultSet rset = db.ejecutar(conn, query);
        
        return rset;
    }
    
    public void desconectar()
    {
        db.desconectar(this.conn);
    }
    
}
