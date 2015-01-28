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
public class SeleccionarLaboratorios {
    //Atributos
    private DbManager db;
    private Connection conn;
    
    /**
     * 
     * Constructor
     * 
     */
    public SeleccionarLaboratorios() {
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
    public ResultSet getLaboratorios(String nombre, String contactos,String direccion,String telefono1,String telefono2,
            String correo,String resolucion,String vigencia){

        String query = "SELECT\n " +
                        " LABORATORIO.CODIGO,\n" +
                        " LABORATORIO.NOMBRES,\n" +
                        " LABORATORIO.CONTACTO,\n" +
                        " LABORATORIO.DIRECCION,\n" +
                        " LABORATORIO.TELEFONO1,\n" +
                        " LABORATORIO.TELEFONO2,\n" +
                        " LABORATORIO.EMAIL,\n" +
                        " LABORATORIO.RESOLUCION_ACREDITACION,\n" +
                        " TO_CHAR(LABORATORIO.VIGENCIA, 'DD/MM/YYYY') VIGENCIA\n" +
                        " FROM\n" +
                        " TB_LABORATORIOS_CONSULTOR LABORATORIO\n" +
                        " WHERE\n" +
                        " LABORATORIO.FK_TIPO_ENTIDAD = 1";
        
        if(!("").equals(nombre) && nombre != null){

            query += " AND upper(LABORATORIO.NOMBRES) LIKE upper('%" + nombre + "%')";

        }
        if(!("").equals(contactos) && contactos != null){

            query += " AND LABORATORIO.CONTACTO LIKE '%" + contactos + "%'";

        }
        if(!("  #  - ").equals(direccion) && direccion != null){

            query += " AND LABORATORIO.DIRECCION LIKE '%" + direccion + "%'";

        }
        if(!("").equals(telefono1) && telefono1 != null){

            query += " AND LABORATORIO.TELEFONO1 = " + telefono1;

        }
        if(!("").equals(telefono2) && telefono2 != null){

            query += " AND LABORATORIO.TELEFONO2 = " + telefono2;

        }
        if(!("").equals(resolucion) && resolucion != null){

            query += " AND LABORATORIO.RESOLUCION_ACREDITACION LIKE '%" + resolucion + "%'";
        }
        if(!("").equals(vigencia) && vigencia != null){

            query += " AND LABORATORIO.VIGENCIA = '" + vigencia + "'";
        }
        if(!("").equals(correo) && correo != null){

            query += " AND upper(LABORATORIO.EMAIL) LIKE upper('%" + correo + "%')";
        }
        ResultSet rset = db.ejecutar(conn, query);

        return rset;
        
    }
    //-----------------------------------------------------------------------------
    
    
    
    
    /**
     * 
     * Obtiene la informacion de una unidad de medida
     * 
     * @param codigo
     * @return rset 
     */
    public ResultSet getLaboratorio(int codigo){
    
        String query = "SELECT\n " +
                        "LABORATORIO.CODIGO,\n" +
                        "LABORATORIO.NOMBRES,\n" +
                        "LABORATORIO.CONTACTO,\n" +
                        "LABORATORIO.DIRECCION,\n" +
                        "LABORATORIO.TELEFONO1,\n" +
                        "LABORATORIO.TELEFONO2,\n" +
                        "LABORATORIO.EMAIL,\n" +
                        "LABORATORIO.RESOLUCION_ACREDITACION,\n" +
                        "TO_CHAR(LABORATORIO.VIGENCIA, 'DD/MM/YYYY') VIGENCIA,\n" +
                        "ACRED.FK_PARAMETRO_FISQUIM AS COD_PARAM,\n" +
                        "ACRED.CODIGO AS COD_PARAM_ACRED, \n"+
                        "PARAM.DESCRIPCION AS DESP_PARAM \n"+
                        " FROM\n" +
                        " TB_LABORATORIOS_CONSULTOR LABORATORIO \n" +
                        "LEFT JOIN  TB_PARAMETROS_ACREDITACION ACRED ON ACRED.FK_LABORATORIO = LABORATORIO.CODIGO\n" +
                        "LEFT JOIN  TB_PARAM_FISICOQUIMICOS PARAM ON PARAM.CODIGO = ACRED.FK_PARAMETRO_FISQUIM \n" +
                        " WHERE\n" +
                        " LABORATORIO.FK_TIPO_ENTIDAD = 1 AND"
                        +" LABORATORIO.CODIGO = " + codigo;
        
        ResultSet rset = db.ejecutar(conn, query);

        return rset;
        
    }
    
    public void desconectar()
    {
        db.desconectar(this.conn);
    }
}
