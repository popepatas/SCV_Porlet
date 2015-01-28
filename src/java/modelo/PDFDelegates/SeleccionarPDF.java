/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.PDFDelegates;

import modelo.ParametrizacionDelegates.*;
import java.sql.Connection;
import java.sql.ResultSet;
import modelo.DbManager;

/**
 *
 * @author illustrato
 */
public class SeleccionarPDF {
      private DbManager db;
      private Connection conn;
      
    /**
     * 
     * Constructor
     * 
     */
    public SeleccionarPDF() {
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
    public ResultSet getCliente(int codigo){
    
        String query = "SELECT COALESCE(cl.NOMBRE_REPRESENTANTE_LEGAL,' ') AS NOMBRE_REPRESENTANTE_LEGAL,\n" +
                        "COALESCE(cl.RAZON_SOCIAL,' ') AS RAZON_SOCIAL,\n" +
                        "COALESCE(cl.NOMBRE_REPRESENTANTE_LEGAL,' ') AS DIRECCION\n" +
                        "FROM TB_CLIENTES cl\n" +
                        "JOIN TB_PROCESOS_VERTIMIENTOS pvert ON cl.CODIGO = pvert.FK_CLIENTE\n" +
                        "WHERE pvert.CODIGO = " + codigo;
                
        ResultSet rset = db.ejecutar(conn, query);

        return rset;
        
    }
    
    public ResultSet getClienteByCodigo(String codigo){
    
        String query = "SELECT* \n" +
                        "FROM TB_CLIENTES cl\n" +
                        "WHERE cl.CODIGO = " + codigo;
                
        ResultSet rset = db.ejecutar(conn, query);

        return rset;
        
    }
    
    public ResultSet getDescPuntoVertimiento(int codigo){
    
        String query = "SELECT\n" +
                            "PVERT.DESCRIPCION,\n" +
                            "PVERT.CODIGO,\n" +                           
                            "COALESCE(LAB.NOMBRES,'') AS LAB,\n" +
                            "COALESCE(CON.NOMBRES || ' ' ||CON.APELLIDOS,'') AS CNSTR \n"+
                        "FROM\n" +
                        "SCV.TB_MONITOREOS MON\n" +
                        "INNER JOIN SCV.TB_PUNTOS_MONITOREOS PTM ON MON.CODIGO = PTM.FK_MONITOREO\n" +
                        "INNER JOIN SCV.TB_JORNADAS_PUNTOS_MONITOREOS JM ON PTM.CODIGO = JM.FK_PUNTO_MONITOREO\n" +
                        "INNER JOIN SCV.TB_DETALLES_JORNADAS DTJ ON JM.CODIGO = DTJ.FK_JORNADA_PUTOS_MONITOREO\n" +
                        "INNER JOIN SCV.TB_PUNTOS_VERTIMIENTOS PVERT ON PVERT.CODIGO = PTM.FK_PUNTO_VERTIMIENTO\n" +
                        "LEFT JOIN SCV.TB_LABORATORIOS_CONSULTOR LAB ON LAB.CODIGO = MON.FK_LABORATORIO\n"+
                        "LEFT JOIN SCV.TB_LABORATORIOS_CONSULTOR CON ON CON.CODIGO = MON.FK_CONSULTOR\n"+
                        "WHERE\n" +
                            "MON.FK_PROCESO_VERTIMIENTO = "+codigo+" AND\n" +
                            "DTJ.CUMPLE IS NOT NULL AND\n" +
                            "DTJ.CUMPLE = 'NO'\n" +
                        "GROUP BY\n" +
                            "PVERT.DESCRIPCION,\n" +
                            "PVERT.CODIGO\n" +
                            ",CON.NOMBRES,CON.APELLIDOS, LAB.NOMBRES\n"+
                        "ORDER BY\n" +
                         "PVERT.CODIGO ASC";
                
        ResultSet rset = db.ejecutar(conn, query);

        return rset;
        
    }
    
    public ResultSet getParametrosNoCumplidos(String codigoPuntoVert){
        String query = "SELECT\n" +
                        "'Jornada ' || JPM.JORNADA || ' - ' || PRMTRO.DESCRIPCION AS PARAMETRO,\n" +
                        "ACTPARM.RANGO_INICIAL AS RANGO_INICIAL,\n" +
                        "ACTPARM.RANGO_FINAL AS RANGO_FINAL,\n" +
                        "DTJ.VALOR AS VALOR,\n" +
                        "UND.DESCRIPCION AS UNIDAD,\n"+
                        "DTJ.CUMPLE\n" +
                        "FROM\n" +
                        " TB_PROCESOS_VERTIMIENTOS PV\n" +
                        "INNER JOIN  TB_MONITOREOS MON ON PV.CODIGO = MON.FK_PROCESO_VERTIMIENTO\n" +
                        "INNER JOIN  TB_PUNTOS_MONITOREOS PM ON MON.CODIGO = PM.FK_MONITOREO\n" +
                        "INNER JOIN  TB_JORNADAS_PUNTOS_MONITOREOS JPM ON JPM.FK_PUNTO_MONITOREO = PM.CODIGO\n" +
                        "INNER JOIN  TB_DETALLES_JORNADAS DTJ ON DTJ.FK_JORNADA_PUTOS_MONITOREO = JPM.CODIGO\n" +
                        "INNER JOIN  TB_PARAM_FISICOQUIMICOS PRMTRO ON DTJ.FK_PARAM_FISICOQUIMICO = PRMTRO.CODIGO\n" +
                        "INNER JOIN  TB_UNIDADES_MEDIDAS UND ON UND.CODIGO = PRMTRO.FK_UNIDAD_MEDIDA\n" +
                        "INNER JOIN  TB_ACT_PARAMFISICOQUIMICOS ACTPARM ON PM.FK_ACTIVIDAD_ECONOMICA = ACTPARM.FK_ACTIVIDAD_ECONOMICA AND PRMTRO.CODIGO = ACTPARM.FK_PARAM_FISICOQUIMICO\n" +
                        "WHERE\n" +
                        "DTJ.CUMPLE LIKE 'NO' AND PM.FK_PUNTO_VERTIMIENTO = " + codigoPuntoVert + "\n" +
                        "ORDER BY\n" +
                        "PV.CODIGO ASC,\n" +
                        "PM.FK_PUNTO_VERTIMIENTO ASC,\n" +
                        "JPM.JORNADA ASC";

        
        ResultSet rset = db.ejecutar(conn, query);
        return rset;
    }
    
    public ResultSet contarParamIncumplidos(String codigoProceso){
        String query = "SELECT \n" +
                            "COUNT(DTJ.CODIGO) AS CANTIDAD\n" +
                            "FROM \n" +
                            " TB_MONITOREOS MON\n" +
                            "INNER JOIN  TB_PUNTOS_MONITOREOS PM ON MON.CODIGO = PM.FK_MONITOREO \n" +
                            "INNER JOIN  TB_DETALLES_JORNADAS DTJ ON DTJ.FK_PUNTO_MONITOREO = PM.CODIGO \n" +
                            "WHERE MON.FK_PROCESO_VERTIMIENTO = " +codigoProceso+ " AND DTJ.CUMPLE LIKE 'NO'";
        ResultSet rset = db.ejecutar(conn, query);
        return rset;
    }
    
        public ResultSet contarParam(String codigoProceso){
        String query = "SELECT \n" +
                            "COUNT(DTJ.CODIGO) AS CANTIDAD\n" +
                            "FROM \n" +
                            " TB_MONITOREOS MON\n" +
                            "INNER JOIN  TB_PUNTOS_MONITOREOS PM ON MON.CODIGO = PM.FK_MONITOREO \n" +
                            "INNER JOIN  TB_DETALLES_JORNADAS DTJ ON DTJ.FK_PUNTO_MONITOREO = PM.CODIGO \n" +
                            "WHERE MON.FK_PROCESO_VERTIMIENTO = " +codigoProceso+ " AND DTJ.CUMPLE IS NOT NULL";
        ResultSet rset = db.ejecutar(conn, query);
        return rset;
    }
        
    public ResultSet obtenerAñoProceso(String codigoProceso){
        String query = "SELECT to_char(FECHA_CREACION,'yyyy') AS AÑO FROM TB_PROCESOS_VERTIMIENTOS\n" +
                       "WHERE CODIGO = " + codigoProceso;
        
        ResultSet rset = db.ejecutar(conn, query);
        return rset;        
    }
    
    public ResultSet obtenerLabCons(String codigoProceso){
        String query = "SELECT (CON.NOMBRES || ' ' || CON.APELLIDOS) CONSULTOR, LAB.NOMBRES AS LABORATORIO\n" +
                        "FROM TB_MONITOREOS MON\n" +
                        "LEFT JOIN TB_LABORATORIOS_CONSULTOR LAB ON MON.FK_LABORATORIO = LAB.CODIGO\n" +
                        "LEFT JOIN TB_LABORATORIOS_CONSULTOR CON ON MON.FK_CONSULTOR = CON.CODIGO\n" +
                        "WHERE MON.FK_PROCESO_VERTIMIENTO = "+codigoProceso+" AND MON.FK_ESTADO = 1";

        ResultSet rset = db.ejecutar(conn, query);
        return rset;            
    }
    
    public void desconectar()
    {
        db.desconectar(this.conn);
    }
}
