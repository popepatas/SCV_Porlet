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
public class SeleccionarInfoTecJornadas {
    
    private DbManager db;
    private String filaInicio;
    private String filaFin ;
    private String tipoVisita;
    private String fechaInicial;
    private String fechaFinal;
    private String codigoProceso;
    private String estadoVisita;
    private String contrato;
    private String nit;
    private String razonSocial; 
    private String programacion;
    private String comuna;
    private Connection conn;
    
    
    public SeleccionarInfoTecJornadas(){        
        
        this.db = new DbManager();
        conn = db.conectar();
    }
    
   
     public ResultSet getJornadas(int puntoMonitoreo){
         
        String query = "SELECT *FROM TB_JORNADAS_PUNTOS_MONITOREOS WHERE FK_PUNTO_MONITOREO = " + puntoMonitoreo + "ORDER BY JORNADA";
        
        ResultSet rset = db.ejecutar(conn, query);
                
        return rset;
       
    }
     
    public ResultSet contarJornadas(int puntoMonitoreo){
        String query = "SELECT\n" +
                        "COUNT(pmon.CODIGO) AS CANTIDAD\n" +
                        "FROM TB_JORNADAS_PUNTOS_MONITOREOS pmon\n" +
                        "WHERE FK_PUNTO_MONITOREO = " + puntoMonitoreo;
        
        ResultSet rset = db.ejecutar(conn, query);
                
        return rset;
    }
    
    public ResultSet hayDatos(int codigoMonitoreo){
          String query = "SELECT COUNT(jpmon.CODIGO) AS CANTIDAD\n" +
                         "FROM TB_MONITOREOS mon\n" +
                         "JOIN TB_PUNTOS_MONITOREOS pmon ON mon.CODIGO = pmon.FK_MONITOREO\n" +
                         "JOIN TB_JORNADAS_PUNTOS_MONITOREOS jpmon ON pmon.CODIGO = jpmon.FK_PUNTO_MONITOREO \n" +
                         "WHERE FK_PROCESO_VERTIMIENTO = " + codigoMonitoreo 
                         +" AND mon.FK_ESTADO = 1 ";
          
          ResultSet rset = db.ejecutar(conn, query);
          return rset;
      }
  
      public void desconectar()
      {
          db.desconectar(conn);
      }
  }