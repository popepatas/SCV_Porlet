/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.ClientesDelegates;

import configuracion.Queries;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.DbManager;
import modelo.ParametrizacionDelegates.SeleccionarUnidadesMedida;
import oracle.jdbc.OracleTypes;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Galatea
 */
public class SeleccionarCliente {
    
    
    //Atributos
    private String nit;
    private String ciiu;
    private String razonSocial;
    private String direccion;
    private String comuna;
    private String usoServicio;
    private String barrio;
    private DbManager db;
    private Connection conn;
    
    
    /**
     * 
     * Constructor
     * 
     * @param nit
     * @param ciiu
     * @param razonSocial
     * @param direccion
     * @param comuna
     * @param ciclo
     * @param usoServicio 
     */
    public SeleccionarCliente(String nit, String ciiu, String razonSocial, String direccion, 
            String comuna, String usoServicio, String barrio) {
        
        this.nit = nit;
        this.ciiu = ciiu;
        this.razonSocial = razonSocial;
        this.direccion = direccion;
        this.comuna = comuna;
        this.usoServicio = usoServicio;
        this.barrio = barrio;
        db = new DbManager();
        conn = db.conectar();
        
    }

    
    public SeleccionarCliente()
    {
        db = new DbManager();
        conn = db.conectar();
    }
    
    
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
    
    
    /**
     * 
     * Obtiene la informacion de un Cliente.
     * 
     * @param nit
     * @return 
     */
    public ResultSet getCliente(int codigo){
    
        String query = "SELECT * FROM TB_CLIENTES WHERE CODIGO = " + codigo;
        
        ResultSet rset = db.ejecutar(conn, query);
      
        return rset;
        
    }
    //-----------------------------------------------------------------------------
    
        /**
     * 
     * Obtiene la informacion de un Cliente.
     * 
     * @param nit
     * @return 
     */
    public ResultSet getClienteporNit(String nit){
    
        String query = "SELECT cl.NIT , cl.RAZON_SOCIAL, cl.DIRECCION AS DIRECCION\n" +
                        "FROM TB_CLIENTES cl\n" +
                        "JOIN TB_ACTIVIDADES_ECONOMICAS act ON cl.FK_ACTIVIDAD_ECONOMICA = act.CODIGO\n" +
                        "WHERE cl.NIT = '" + nit+"'";
        
        ResultSet rset = db.ejecutar(conn, query);
      
        return rset;
        
    }
    //-----------------------------------------------------------------------------
    
    
    
    
    /**
     * 
     * Obtiene la informacion de un Cliente.
     * 
     * @param contrato
     * @return 
     */
    public ResultSet getClientePorContrato(int contrato){
    
        String query = "SELECT cl.*\n" +
                        "FROM tb_clientes cl\n" +
                        "JOIN tb_procesos_vertimientos pv ON cl.codigo = pv.fk_cliente\n" +
                        "JOIN tb_contratos con ON pv.fk_contrato = con.contrato "
                        + " WHERE con.contrato = " + contrato
                        + " AND rownum = 1"
                        + " ORDER BY fecha_proceso DESC";
        
        ResultSet rset = db.ejecutar(conn, query);
        
        return rset;
        
    }
    //-----------------------------------------------------------------------------
    
    
        private String getQueryBusqueda(){
        
        String query = "SELECT cl.CODIGO, cl.NIT, ac.DESCRIPCION AS ACTIVIDAD_ECONOMICA, cl.RAZON_SOCIAL, cl.DIRECCION," +
"                           cl.BARRIO, cl.COMUNA, tus.DESCRIPCION AS USOSERVICIO,"
                            + " ac.CODIGO_CIIU" +
"                           FROM TB_CLIENTES cl " +
"                           LEFT JOIN TB_TIPOS_USOS_SERVICIOS tus ON cl.FK_USO_SERVICIO = tus.CODIGO " +
"                           JOIN TB_ACTIVIDADES_ECONOMICAS ac ON cl.FK_ACTIVIDAD_ECONOMICA = ac.CODIGO " +
"                           WHERE cl.NIT IS NOT NULL";
        
        if(nit != "" && nit != null){            
            query += " AND cl.NIT LIKE '%"+nit.toUpperCase()+"%'";
            
        }
        
        if(razonSocial != "" && razonSocial != null){
            
            query += " AND upper(cl.RAZON_SOCIAL) LIKE '%"+razonSocial.toUpperCase()+"%'";
            
        }
        
        if(ciiu != "" && ciiu != null){
            
            query += " AND cl.FK_ACTIVIDAD_ECONOMICA = "+ciiu;
            
        }
        
        if(direccion != "" && direccion != null){
            
            query += " AND upper(cl.DIRECCION) LIKE '%"+direccion.toUpperCase()+"%'";
            
        }
        
        if(comuna != "" && comuna != null){
            
            query += " AND cl.COMUNA = "+comuna;
            
        }
        
        if(usoServicio != "" && usoServicio != null){
            
            query += " AND cl.FK_USO_SERVICIO = "+usoServicio;
            
        }
        
        if(barrio != "" && barrio != null){
            
            query += " AND upper(cl.BARRIO) LIKE '%"+barrio.toUpperCase()+"%'";
            
        }
        query += " ORDER BY cl.CODIGO DESC ";
        return query;
    }
   
        
    public void desconectar()
    {
        db.desconectar(this.conn);
    }
    
}
