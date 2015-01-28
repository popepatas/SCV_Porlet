/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.ProcesoVertimientosDelegates;

import configuracion.Queries;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.DbManager;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleResultSet;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author illustrato
 */
public class SeleccionarProcesoVertimientos {
    
     //Atributos
    private String codigoProceso;
    private String nit;
    private String ciiu;
    private String razonSocial;
    private String contrato;
    private String anio;
    private String fechaInicial;
    private String fechaFinal;
    private String filaInicio; 
    private String filaFin;
    private DbManager db;
    private Connection conn;
    
    //Construcctor Sobregardado
    public SeleccionarProcesoVertimientos()
    {
        db = new DbManager();
        conn = db.conectar();
    }
    
    //Construcctor Sobregardado
    public SeleccionarProcesoVertimientos(String codigoProceso, String nit, String contrato ,String ciiu, String razonSocial, 
                                String anio, String fechaInicial, String fechaFinal, String filaInicio, String filaFin) {
        this.codigoProceso = codigoProceso;
        this.contrato = contrato;
        this.nit = nit;
        this.ciiu = ciiu;
        this.razonSocial = razonSocial;
        this.anio = anio;
        this.fechaInicial = fechaInicial;
        this.fechaFinal = fechaFinal;
        this.filaInicio = filaInicio;
        this.filaFin = filaFin;
        db = new DbManager();
        conn = db.conectar();
    }
    
     /**
     *
     * Ejecuta la funcion FnObtenerProcesosVertimientos y retorna un resultset con la busqueda.
     *
     * @return 
     * @throws Exception
     */
     /**
     *
     * Ejecuta la funcion FnObtenerClientes y retorna un resultset con la busqueda.
     *
     * @throws Exception
     */
    public ResultSet ejecutar() throws Exception{

        String query = getQueryBusqueda();
        
        ResultSet rset = db.ejecutar(conn, query);
        
         
        return rset;
    }
    //-----------------------------------------------------------------------------
    
    
    private String getQueryBusqueda(){
        
        String footer = " ORDER BY codigo DESC) WHERE rnum >= "+filaInicio+" AND rnum <= "+filaFin;
        
        String query = "SELECT * FROM (SELECT \n" +
"                    pv.fk_contrato,\n" +
"                    pv.fk_cliente,\n" +
"                    pv.fk_tipo_informe,\n" +
"                    to_char(pv.fecha_proceso,'DD/MM/YYYY') as fecha_proceso,\n" +
"                    to_char(pv.fecha_creacion,'DD/MM/YYYY') as fecha_creacion,\n" +
"                    pv.codigo,\n" +
"                    pv.informa_monitoreo,\n" +
"                    pv.fk_contrato_cliente,\n" +
"                    pv.fk_estado_proceso,\n" +
"                    cl.nit, \n" +
"                    cl.razon_social, \n" +
"                    act.descripcion || ''|| codigo_ciiu || ''  as ciiu,\n" +
"                    tinf.descripcion as tipoinforme,\n" +
"                    estado.descripcion as estadop,\n" +
"                    ROW_NUMBER() OVER (ORDER BY fecha_proceso DESC) AS rnum,\n" +
"                    FN_VERIFICAR_REQUISITOS(pv.codigo) AS VALIDAR_RQ  \n" +
"              FROM TB_PROCESOS_VERTIMIENTOS pv\n" +
"              JOIN TB_CLIENTES cl ON pv.fk_cliente = cl.codigo\n" +
"              JOIN TB_ACTIVIDADES_ECONOMICAS act ON act.codigo = cl.FK_ACTIVIDAD_ECONOMICA\n" +
"              JOIN TB_ESTADOS_PROCESOS estado ON estado.codigo = pv.fk_estado_proceso\n" +
"              LEFT JOIN TB_TIPOS_INFORMES tinf ON tinf.codigo = pv.FK_TIPO_INFORME\n" +
"              WHERE pv.CODIGO IS NOT NULL";
        
        if(codigoProceso != "" && codigoProceso != null){
            
            query += " AND pv.CODIGO = "+codigoProceso;
            
        }
        
        if(nit != "" && nit != null){
            
            query += " AND cl.NIT  LIKE '"+nit+"'";
            
        }
        
        if(contrato != "" && contrato != null){
            
            query += " AND pv.FK_CONTRATO = "+contrato;
            
        }
        
        if(ciiu != "" && ciiu != null){
            
            query += " AND cl.FK_ACTIVIDAD_ECONOMICA = "+ciiu;
            
        }
        
        if(razonSocial != "" && razonSocial != null){
            
            query += " AND cl.RAZON_SOCIAL LIKE '"+razonSocial+"'";
            
        }
        
        if(anio != "" && anio != null){
            
            query += " AND pv.FECHA_PROCESO BETWEEN   to_date('01/01/"+anio+"','DD/MM/YYYY') AND to_date('31/12/"+anio+"','DD/MM/YYYY')";
            
        }
        
        if(fechaInicial != "" && fechaInicial != null){
            
            query += " AND pv.FECHA_PROCESO BETWEEN to_date('"+fechaInicial+"','DD/MM/YYYY') AND to_date('"+fechaFinal+"','DD/MM/YYYY')";
            
        }
        
        return query += footer;
    }
    
    public void desconectar()
    {
        db.desconectar(conn);
    }
    
}
