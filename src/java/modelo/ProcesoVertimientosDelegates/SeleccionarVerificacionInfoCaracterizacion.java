/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.ProcesoVertimientosDelegates;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ApiManager;
import modelo.DbManager;

/**
 *
 * @author illustrato
 */
public class SeleccionarVerificacionInfoCaracterizacion {
    
    private DbManager db;
    private int existe;
    private Integer codigoProceso;
    private Integer tipoInforme;
    private Connection conn;
    
    
    public SeleccionarVerificacionInfoCaracterizacion( Integer codigoProceso, Integer tipoInforme) throws SQLException{
           this.codigoProceso =  codigoProceso;
           this.tipoInforme = tipoInforme;
           this.db = new DbManager();
           this.conn = db.conectar();
           exitenVerificaciones(); 
    }
    
    public SeleccionarVerificacionInfoCaracterizacion(){
           this.db = new DbManager();
           this.conn = db.conectar();
    }
     
    public void desconectar(){
        db.desconectar(conn);
    }
    
    /* Obtiene toda la gerarquia de cada padre */
    public ResultSet getVerificaciones() throws SQLException{
        
      
         ResultSet rset  = null;
            
            String query = "SELECT\n" +
                                "RQ.CODIGO,\n" +
                                "RQ.FK_TIPO_INFORME,\n" +
                                "RQ.DESCRIPCION,\n" +
                                "'NO' AS TIENE\n" +
                             "FROM\n" +
                                " TB_REQUISITOS RQ\n" +
                              " WHERE\n" +
                                " RQ.FK_TIPO_INFORME = " + tipoInforme;
            
                if(existe != 0){
                    query = "SELECT\n" +
                            "RQ.CODIGO,\n" +
                            "RQ.FK_TIPO_INFORME,\n" +
                            "RQ.DESCRIPCION,\n" +                           
                            "COALESCE(VDOC.TIENE,'NO') AS TIENE\n" +
                            "FROM\n" +
                                " TB_REQUISITOS RQ\n" +
                                "INNER JOIN  TB_VERIFICACION_DOCUMENTOS VDOC ON VDOC.FK_REQUISITO = RQ.CODIGO\n"+
                            " WHERE\n" +
                            " RQ.FK_TIPO_INFORME  = " + tipoInforme;
                    
                    query += " AND VDOC.FK_PROCESO_VERTIMIENTO = " + codigoProceso;
                }
                    query += " ORDER BY RQ.CODIGO";
        
               rset = db.ejecutar(conn, query);
                      
        return rset;
      
    }
    

    
     /*Se valida que existan verificaciones para definir que mostrar*/
     private void exitenVerificaciones() throws SQLException{
        
      
        String query = "SELECT\n" +
                            "COUNT(RQ.CODIGO) AS existe\n" +
                            "FROM\n" +
                            " TB_REQUISITOS RQ\n" +
                            "INNER JOIN   TB_VERIFICACION_DOCUMENTOS VDOC ON VDOC.FK_REQUISITO = RQ.CODIGO\n"+
                            " WHERE\n"+
                            " VDOC.FK_PROCESO_VERTIMIENTO = " + codigoProceso;
        
        ResultSet rset = db.ejecutar(conn, query);
        
         while(rset.next()){             
            existe =  rset.getInt(1);             
         }
         
         rset.close();
        
         
    }
     
    public ResultSet getDevolucionCaracterizacion(String codigoProceso){
        
        String query = "SELECT\n" +
                        "  FK_TIPO_DEVOLUCION TIPO_DEVOLUCION,\n" +
                        "  to_char(FECHA_ENTREGA_DEVOLUCION,'dd/mm/yyyy') FECHA_ENTREGA,\n" +
                        "  OBSERVACION,\n" +
                        "  to_char(FECHA_DEVOLUCION, 'dd/mm/yyyy') FECHA_DEVOLUCION\n" +
                        "FROM TB_DEVOLUCIONES\n" +
                        "WHERE FK_PROCESO_VERTIMIENTO = " + codigoProceso;
        
        ResultSet rset = db.ejecutar(conn, query);
        
        return rset;
    }
    
}
