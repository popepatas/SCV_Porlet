package modelo.ParametrizacionDelegates;

import java.sql.Connection;
import java.sql.ResultSet;
import modelo.*;


public class SeleccionarTecnicos {
    
   //Atributos
    private DbManager db;
    private Connection conn;
    
   
    /**
     * 
     * Constructor
     * 
     */
    public SeleccionarTecnicos() {
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
    public ResultSet getTecnicos(String tipoPersona, String nombres, String apellidos, 
                                    String tipoDocumento, String documento, String estado){
    
        String query =  "SELECT tc.CODIGO, \n" +
                        "       tc.NOMBRES || ' ' || tc.Apellidos AS NOMBRE,\n" +
                        "       td.DESCRIPCION AS TipoDocumento,\n" +
                        "       tc.DOCUMENTO,\n" +
                        "       te.DESCRIPCION AS Estado,\n" +
                        "       tp.DESCRIPCION AS TipoPersona\n" +
                        "FROM TB_TECNICOS tc\n" +
                        "JOIN TB_TIPOS_DOCUMENTO td ON tc.FK_TIPO_DOCUMENTO = td.CODIGO\n" +
                        "JOIN TB_ESTADOS te ON tc.FK_ESTADO = te.CODIGO\n" +
                        "JOIN TB_TIPOS_PERSONAS tp ON tc.FK_TIPO_PERSONA = tp.CODIGO\n" +
                        "WHERE tc.CODIGO IS NOT NULL";
        
        if(!("").equals(tipoPersona) && tipoPersona != null){

            query += " AND tc.FK_TIPO_PERSONA = " + tipoPersona;

        }
        if(!("").equals(nombres) && nombres != null){

            query += " AND tc.NOMBRES LIKE UPPER('%" + nombres +"%')"; 

        }
        if(!("").equals(apellidos) && apellidos != null){

            query += " AND tc.APELLIDOS LIKE UPPER('%" + apellidos + "%')";

        }
        if(!("").equals(tipoDocumento) && tipoDocumento != null){

            query += " AND tc.FK_TIPO_DOCUMENTO = " + tipoDocumento;

        }
        if(!("").equals(documento) && documento != null){

            query += " AND tc.DOCUMENTO LIKE '%" + documento + "%'";

        }
        if(!("").equals(estado) && estado != null){

            query += " AND tc.FK_ESTADO = " + estado;

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
    public ResultSet getTecnico(int codigo){
    
        String query = "SELECT *FROM TB_TECNICOS WHERE CODIGO = " + codigo;
        
        ResultSet rset = db.ejecutar(conn, query);

        return rset;
        
    }
    //-----------------------------------------------------------------------------
    
    public void desconectar()
    {
        db.desconectar(this.conn);
    }
    
}
