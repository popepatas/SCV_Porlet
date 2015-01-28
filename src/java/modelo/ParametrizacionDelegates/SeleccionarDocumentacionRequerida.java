/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.ParametrizacionDelegates;

import java.sql.Connection;
import java.sql.ResultSet;
import modelo.DbManager;

/**
 *
 * @author illustrato
 */
public class SeleccionarDocumentacionRequerida {
      //Atributos
    private DbManager db;
    private Connection conn;
    
    /**
     * 
     * Constructor
     * 
     */
    public SeleccionarDocumentacionRequerida() {
        db = new DbManager();
        conn = db.conectar();
    }
    
    
    
    
    /**
     * 
     * Obtiene la informacion de las Documentación Requerida de la bd
     * y la devuelve en un ResultSet
     * 
     * @return rset 
     */
    public ResultSet getDocumentacionRequeridas(String descripcion, String tipoInforme){
    
        String query = "SELECT\n" +
                            "RQ.CODIGO,\n" +
                            "RQ.FK_TIPO_INFORME,\n" +
                            "RQ.DESCRIPCION,\n" +
                            "TI.DESCRIPCION AS TIPO_INFORME,\n" +
                            "RQ.FK_TIPO_INFORME AS COD_TIPO_INFORME \n"+
                        "FROM\n" +
                        " TB_REQUISITOS RQ\n" +
                        "INNER JOIN  TB_TIPOS_INFORMES TI ON TI.CODIGO = RQ.FK_TIPO_INFORME \n" + 
                        "WHERE RQ.CODIGO IS NOT NULL";
        
        if(!("").equals(descripcion) && descripcion != null){

            query += " AND UPPER(RQ.DESCRIPCION) LIKE UPPER('%" + descripcion + "%')";

        }
        if(!("").equals(tipoInforme) && tipoInforme != null){

            query += " AND RQ.FK_TIPO_INFORME = " + tipoInforme;

        }
        
        ResultSet rset = db.ejecutar(conn, query);

        return rset;
        
    }
    //-----------------------------------------------------------------------------
    
    
    
    
    /**
     * 
     * Obtiene la informacion de un Documentación Requerida
     * 
     * @param codigo
     * @param tipoInforme
     * @return rset 
     */
    public ResultSet getDocumentacionRequerida(Integer codigo, Integer tipoInforme){
    
        String query = "SELECT\n" +
                        "RQ.CODIGO,\n" +
                        "RQ.FK_TIPO_INFORME,\n" +
                        "RQ.DESCRIPCION,\n" +
                        "TI.DESCRIPCION  AS TIPO_INFORME, \n" +
                        "RQ.FK_TIPO_INFORME AS COD_TIPO_INFORME \n"+
                        "FROM\n" +
                        " TB_REQUISITOS RQ\n" +
                        "INNER JOIN  TB_TIPOS_INFORMES TI ON TI.CODIGO = RQ.FK_TIPO_INFORME\n"+
                        "WHERE \n"+
                        "RQ.CODIGO IS NOT NULL  \n";
       
         if(codigo != null){
         
             query += " AND RQ.CODIGO = " + codigo;
         }
         
         if(tipoInforme != null){
             
             query += " AND RQ.FK_TIPO_INFORME = " + tipoInforme;
         }
         
        
        ResultSet rset = db.ejecutar(conn, query);

        return rset;
        
    }
    //-----------------------------------------------------------------------------
    
    
    public void desconectar()
    {
        db.desconectar(this.conn);
    }

    private static class integer {

        public integer() {
        }
    }
    

}
