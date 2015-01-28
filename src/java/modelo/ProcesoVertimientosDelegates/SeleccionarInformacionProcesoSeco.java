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
public class SeleccionarInformacionProcesoSeco {
    
    
      
    private DbManager db;
    private Connection conn;
    private int codigoProceso;
    
    public SeleccionarInformacionProcesoSeco( int codigoProceso){
        
        this.db = new DbManager();
        conn = db.conectar();
        this.codigoProceso = codigoProceso;
    }

    
      
   
     public ResultSet getInformacionProcesoSeco(){
         
        String query = getQueryBusqueda();
        
        ResultSet rset = db.ejecutar(conn, query);
                
        return rset;
       
    }
     
     
      private String getQueryBusqueda(){
         
                String query = "SELECT\n" +
                                    "to_char(DEV.FECHA_DEVOLUCION,'dd/MM/YYYY') AS FECHA_DEVOLUCION,\n" +
                                    "to_char(DEV.FECHA_ENTREGA_DEVOLUCION,'dd/MM/YYYY') AS FECHA_ENTREGA_DEVOLUCION,\n" +
                                    "DEV.OBSERVACION,\n" +
                                    "DEV.FK_TIPO_DEVOLUCION,\n" +
                                    "MON.FK_LABORATORIO,\n" +
                                    "MON.FK_CONSULTOR,\n" +
                                    "to_char(PV.FECHA,'dd/MM/YYYY') AS FECHA,\n" +
                                    "to_char(PV.FECHA_RADICACION,'dd/MM/YYYY') AS FECHA_RADICACION,\n" +
                                    "DEV.CODIGO AS COD_DEVOL, \n" +
                                    "PV.OBSERVACION_PROCESO \n" +
                                "FROM\n" +
                                    " TB_PROCESOS_VERTIMIENTOS PV\n" +
                                    "INNER JOIN  TB_MONITOREOS MON ON PV.CODIGO = MON.FK_PROCESO_VERTIMIENTO\n" +
                                    "LEFT JOIN  TB_DEVOLUCIONES DEV ON DEV.FK_PROCESO_VERTIMIENTO = PV.CODIGO\n"+
                                "WHERE\n"+
                                "PV.CODIGO = " + codigoProceso;


               return query;
        }
      
      
      
      public void desconectar()
      {
          db.desconectar(conn);
      }
    
    
}
