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
public class SeleccionarInformacionGeneral {
      
   //Atributos
    private DbManager db;
    private Connection conn;
    
    /**
     * 
     * Constructor
     * 
     */
    public SeleccionarInformacionGeneral() {
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
    public ResultSet getInformacionGeneral(int codigoProceso){
    
        String query = "SELECT\n" +
                        " PV.CODIGO as COD_PROCESO,\n" +
                        " PV.INFORMA_MONITOREO,\n" +
                        " PV.FK_TIPO_INFORME,\n" +
                        " ASE.REQUIERE_VISITA,\n" +
                        " ASE.CONTACTO,\n" +
                        " ASE.ASUNTO,\n" +
                        " to_char(ASE.FECHA_ASESORIA,'DD/MM/YYYY' ) as FECHA_ASESORIA,\n" +
                        " ASE.FK_TIPO_CONTACTO,\n" +
                        " ASE.CODIGO as COD_ASESORIA,\n" +
                        " CLI.RAZON_SOCIAL,\n" +
                        " CLI.NIT,\n" +
                        " PV.FK_CONTRATO as CONTRATO,\n" +
                        " PV.FK_ESTADO_PROCESO as ESTADO_PROCESO, \n" +
                        " CLI.CODIGO AS COD_CLI \n" +
                        " FROM\n" +
                        "  TB_PROCESOS_VERTIMIENTOS PV\n" +
                        " INNER JOIN  TB_CLIENTES CLI ON CLI.CODIGO = PV.FK_CLIENTE\n" +
                        " LEFT JOIN  TB_ASESORIAS ASE ON PV.CODIGO = ASE.FK_PROCESO_VERTIMIENTO"
                        + " WHERE "
                        + " PV.CODIGO = "+ codigoProceso;
                
            ResultSet rset = db.ejecutar(conn, query);
            
           
            return rset;

    }
    //-----------------------------------------------------------------------------
    
    
    public void desconectar()
    {
        db.desconectar(this.conn);
    }
}
