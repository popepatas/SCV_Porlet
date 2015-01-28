package modelo.DashboardDelegates;

import java.sql.Connection;
import java.sql.ResultSet;
import modelo.DbManager;

/**
 *
 * @author jmrincon
 */
public class DashboardDelegate {
    //Atributos
    private String codigoCliente;
    private String rangoMetrosInicial;
    private String rangoMetrosFinal;
    
    private String anioDesde;
    private String anioHasta;
    private String paramFisicoQuimico;
    
    private String rangoDBOInicial;
    private String rangoDBOFinal;
    
    private String numeroContrato;
    
    private DbManager db;
    private Connection conn;

    public DashboardDelegate(String codigoCliente, String rangoMetrosInicial, String rangoMetrosFinal, 
            String anioDesde, String anioHasta, String paramFisicoQuimico, 
            String rangoDBOInicial, String rangoDBOFinal, String numeroContrato) {
        this.codigoCliente = codigoCliente;
        this.rangoMetrosInicial = rangoMetrosInicial;
        this.rangoMetrosFinal = rangoMetrosFinal;
        this.anioDesde = anioDesde;
        this.anioHasta = anioHasta;
        this.paramFisicoQuimico = paramFisicoQuimico;
        this.rangoDBOInicial = rangoDBOInicial;
        this.rangoDBOFinal = rangoDBOFinal;
        this.numeroContrato = numeroContrato;
        db = new DbManager();
        conn = db.conectar();
    }
    
    public DashboardDelegate(){
        db = new DbManager();
        conn = db.conectar();
    }
    
    public ResultSet ejecutarDashboard1() throws Exception{
        String query = getDashboard1();
        ResultSet rset = db.ejecutar(conn, query);
        return rset;
    }
    
    public ResultSet ejecutarDashboard2() throws Exception{
        String query = getDashboard2();
        ResultSet rset = db.ejecutar(conn, query);
        return rset;
    }
    
    public ResultSet ejecutarDashboard3() throws Exception{
        String query = getDashboard3();
        ResultSet rset = db.ejecutar(conn, query);
        return rset;
    }
    
    public ResultSet ejecutarCantProcesosXCliente() throws Exception{
        String query = "SELECT count(*) cantidadProcesos FROM TB_PROCESOS_VERTIMIENTOS x WHERE x.FK_CLIENTE = " + codigoCliente;
        //System.err.println("dashboar0: " + query);
        ResultSet rset = db.ejecutar(conn, query);
        return rset;
    }
    
    public ResultSet ejecutarDashboard4() throws Exception{
        String query = getDashboard4();
        ResultSet rset = db.ejecutar(conn, query);
        return rset;
    }
    
    public ResultSet ejecutarDashboard5() throws Exception{
        String query = getDashboard5();
        ResultSet rset = db.ejecutar(conn, query);
        return rset;
    }
    
    private String getDashboard1(){
        String query = "SELECT TO_CHAR(pv.FECHA_PROCESO, 'MM-YYYY') mesProceso, tl.DESCRIPCION tipoLodo, count(*) cantidad " 
                + "FROM TB_PROCESOS_VERTIMIENTOS pv " 
                + "JOIN TB_CLIENTES cl ON cl.CODIGO = pv.FK_CLIENTE " 
                + "JOIN TB_MONITOREOS mo ON mo.FK_PROCESO_VERTIMIENTO = pv.CODIGO " 
                + "JOIN TB_TIPOS_LODOS tl ON tl.CODIGO = mo.FK_TIPO_LODO " 
                + "JOIN TB_ENTIDADES_MANEJOS_LODOS eml ON eml.FK_MONITOREO = mo.CODIGO " 
                + "WHERE pv.FK_TIPO_INFORME IN (4) "
                + "AND eml.VOLUMEN BETWEEN " + rangoMetrosInicial +  " AND " + rangoMetrosFinal;
        
        if(codigoCliente != null && !codigoCliente.equals("")){
             query += " AND pv.FK_CLIENTE = " + codigoCliente;
        }
        
        query += " GROUP BY TO_CHAR(pv.FECHA_PROCESO, 'MM-YYYY'), tl.DESCRIPCION " 
                + "ORDER BY mesProceso, tipoLodo";
        //System.err.println("dashboar1: " + query);
        return query;
    }
    
    private String getDashboard2(){
        String query = "SELECT pv.CODIGO, TO_CHAR(pv.FECHA_PROCESO, 'MM/YYYY') mesProceso, param.DESCRIPCION nombParametro " 
                + ", count(*) cantidadNo " 
                + "FROM TB_PROCESOS_VERTIMIENTOS pv " 
                + "JOIN TB_CLIENTES cl ON cl.CODIGO = pv.FK_CLIENTE " 
                + "JOIN TB_MONITOREOS mo ON mo.FK_PROCESO_VERTIMIENTO = pv.CODIGO " 
                + "JOIN TB_PUNTOS_MONITOREOS pmo ON pmo.FK_MONITOREO = mo.CODIGO " 
                + "JOIN TB_JORNADAS_PUNTOS_MONITOREOS jor ON jor.FK_PUNTO_MONITOREO = pmo.CODIGO " 
                + "JOIN TB_DETALLES_JORNADAS detjor ON detjor.FK_JORNADA_PUTOS_MONITOREO = jor.CODIGO AND UPPER(detjor.CUMPLE) = UPPER('No') " 
                + "LEFT OUTER JOIN TB_PARAM_FISICOQUIMICOS param ON param.CODIGO = detjor.FK_PARAM_FISICOQUIMICO AND param.FK_TIPO_PARAM_FISICOQUIMICO IN (6,7) " 
                + "WHERE pv.FK_TIPO_INFORME IN (4) " 
                + "AND pv.FECHA_PROCESO BETWEEN '" + anioDesde + "' AND '" + anioHasta + "' ";
        
        if(codigoCliente != null && !codigoCliente.equals("")){
             query += " AND pv.FK_CLIENTE = " + codigoCliente;
        }
        
        query += "AND detjor.FK_PARAM_FISICOQUIMICO IN (" + paramFisicoQuimico + ") " 
                + "GROUP BY pv.CODIGO, TO_CHAR(pv.FECHA_PROCESO, 'MM/YYYY'), param.DESCRIPCION " 
                + "ORDER BY mesProceso";
        //System.err.println("dashboar2: " + query);
        return query;
    }
    
    private String getDashboard3(){
        String query = "SELECT pv.CODIGO, TO_CHAR(pv.FECHA_PROCESO, 'MM-YYYY') mesProceso, param.DESCRIPCION nombParametro " 
                + ", count(*) cantidadNo " 
                + "FROM TB_PROCESOS_VERTIMIENTOS pv " 
                + "JOIN TB_CLIENTES cl ON cl.CODIGO = pv.FK_CLIENTE " 
                + "JOIN TB_MONITOREOS mo ON mo.FK_PROCESO_VERTIMIENTO = pv.CODIGO " 
                + "JOIN TB_PUNTOS_MONITOREOS pmo ON pmo.FK_MONITOREO = mo.CODIGO " 
                + "JOIN TB_JORNADAS_PUNTOS_MONITOREOS jor ON jor.FK_PUNTO_MONITOREO = pmo.CODIGO " 
                + "JOIN TB_DETALLES_JORNADAS detjor ON detjor.FK_JORNADA_PUTOS_MONITOREO = jor.CODIGO AND UPPER(detjor.CUMPLE) = UPPER('No') " 
                + "LEFT OUTER JOIN TB_PARAM_FISICOQUIMICOS param ON param.CODIGO = detjor.FK_PARAM_FISICOQUIMICO AND param.FK_TIPO_PARAM_FISICOQUIMICO IN (6,7) " 
                + "WHERE pv.FK_TIPO_INFORME IN (4) " 
                + "AND pv.FECHA_PROCESO BETWEEN '" + anioDesde + "' AND '" + anioHasta + "' ";
        
        if(codigoCliente != null && !codigoCliente.equals("")){
             query += " AND pv.FK_CLIENTE = " + codigoCliente;
        }
        
        query += " AND detjor.FK_PARAM_FISICOQUIMICO IN (" + paramFisicoQuimico + ") " 
                + "GROUP BY pv.CODIGO, TO_CHAR(pv.FECHA_PROCESO, 'MM-YYYY'), param.DESCRIPCION " 
                + "ORDER BY mesProceso";
        //System.err.println("dashboar3: " + query);
        return query;
    }
    
    private String getDashboard4(){
        String query = "SELECT TO_CHAR(jor.HORA_INICIO, 'MM/YYYY') mesJornada, param.DESCRIPCION nombParametro, detjor.VALOR " 
                + "FROM TB_DETALLES_JORNADAS detjor " 
                + "LEFT OUTER JOIN TB_PARAM_FISICOQUIMICOS param ON param.CODIGO = detjor.FK_PARAM_FISICOQUIMICO " 
                + "JOIN TB_JORNADAS_PUNTOS_MONITOREOS jor ON jor.CODIGO = detjor.FK_JORNADA_PUTOS_MONITOREO " 
                + "JOIN TB_PUNTOS_MONITOREOS pmo ON pmo.CODIGO = jor.FK_PUNTO_MONITOREO " 
                + "JOIN TB_MONITOREOS mo ON mo.CODIGO = pmo.FK_MONITOREO " 
                + "JOIN TB_PROCESOS_VERTIMIENTOS pv ON pv.CODIGO = mo.FK_PROCESO_VERTIMIENTO " 
                + "JOIN TB_CLIENTES cl ON cl.CODIGO = pv.FK_CLIENTE " 
                + "WHERE pv.FK_CLIENTE = " + codigoCliente 
                + " AND pv.FECHA_PROCESO BETWEEN '" + anioDesde + "' AND '" + anioHasta + "' " 
                + "AND detjor.FK_PARAM_FISICOQUIMICO IN (" + paramFisicoQuimico + ") ";
        
        if(numeroContrato != null && !numeroContrato.equals("")){
             query += " AND pv.FK_CONTRATO = " + numeroContrato;
        }
        
        query += " ORDER BY mesJornada";
        System.err.println("dashboar4: " + query);
        return query;
    }
    
    private String getDashboard5(){
        String query = "SELECT pv.CODIGO, TO_CHAR(pv.FECHA_PROCESO, 'MM-YYYY') mesProceso, param.DESCRIPCION nombParametro"
                + ", count(*) cantidadNo " 
                +"FROM TB_PROCESOS_VERTIMIENTOS pv " 
                + "JOIN TB_CLIENTES cl ON cl.CODIGO = pv.FK_CLIENTE " 
                + "JOIN TB_MONITOREOS mo ON mo.FK_PROCESO_VERTIMIENTO = pv.CODIGO " 
                + "JOIN TB_PUNTOS_MONITOREOS pmo ON pmo.FK_MONITOREO = mo.CODIGO " 
                + "JOIN TB_JORNADAS_PUNTOS_MONITOREOS jor ON jor.FK_PUNTO_MONITOREO = pmo.CODIGO " 
                + "JOIN TB_DETALLES_JORNADAS detjor ON detjor.FK_JORNADA_PUTOS_MONITOREO = jor.CODIGO AND detjor.FK_PARAM_FISICOQUIMICO IN (16, 17) AND UPPER(detjor.CUMPLE) = UPPER('No') " 
                + "LEFT OUTER JOIN TB_PARAM_FISICOQUIMICOS param ON param.CODIGO = detjor.FK_PARAM_FISICOQUIMICO AND param.FK_TIPO_PARAM_FISICOQUIMICO IN (6,7) " 
                + "WHERE pv.FK_TIPO_INFORME IN (4) " 
                + "AND pv.FECHA_PROCESO BETWEEN '" + anioDesde + "' AND '" + anioHasta + "' ";
        
        if(codigoCliente != null && !codigoCliente.equals("")){
             query += "AND pv.FK_CLIENTE = " + codigoCliente;
        }
        
        query += "AND detjor.VALOR BETWEEN " + rangoDBOInicial + " AND " + rangoDBOFinal 
                + " GROUP BY pv.CODIGO, TO_CHAR(pv.FECHA_PROCESO, 'MM-YYYY'), param.DESCRIPCION"
                + " ORDER BY mesProceso";
        //System.err.println("dashboar5: " + query);
        return query;
    }
    
    public void desconectar(){
        db.desconectar(this.conn);
    }
}