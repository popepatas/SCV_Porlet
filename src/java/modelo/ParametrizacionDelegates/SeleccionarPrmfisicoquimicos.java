package modelo.ParametrizacionDelegates;

import configuracion.Queries;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import modelo.*;


public class SeleccionarPrmfisicoquimicos {
    
   //Atributos
    private DbManager db;
    private Connection conn;
    
    /**
     * 
     * Constructor
     * 
     */
    public SeleccionarPrmfisicoquimicos() {
        db = new DbManager();
        conn = db.conectar();
    }
    
    
    /**
     * 
     * Obtiene la informacion de los parametros fisicoquimicos de la bd
     * y la devuelve en un ResultSet
     * 
     * @return rset 
     */
    public ResultSet getPrmfisicoquimicos(String descripcion, String unidades, String tipoParametro){
    
        String query = "SELECT pf.CODIGO AS Codigo, pf.DESCRIPCION AS Descripcion, \n" +
                        "tpf.DESCRIPCION AS TiposParamFisicoquimico, um.DESCRIPCION AS UnidadMedida\n" +
                        "FROM TB_PARAM_FISICOQUIMICOS pf\n" +
                        "JOIN TB_TIPOS_PARM_FISICOQUIMICOS tpf ON pf.FK_TIPO_PARAM_FISICOQUIMICO = tpf.CODIGO\n" +
                        "JOIN TB_UNIDADES_MEDIDAS um ON pf.FK_UNIDAD_MEDIDA = um.CODIGO WHERE pf.CODIGO IS NOT NULL";
        
        if(!("").equals(descripcion) && descripcion != null){

            query += " AND upper(pf.DESCRIPCION) LIKE upper('%" + descripcion + "%')";

        }
        if(!("").equals(unidades) && unidades != null){

            query += " AND pf.FK_UNIDAD_MEDIDA = " + unidades;

        }
        if(!("").equals(tipoParametro) && tipoParametro != null){

            query += " AND pf.FK_TIPO_PARAM_FISICOQUIMICO = " + tipoParametro;

        }
        
        query += " ORDER BY pf.DESCRIPCION ASC";
        
        ResultSet rset = db.ejecutar(conn, query);

        return rset;
        
    }
    //-----------------------------------------------------------------------------
    
    
    
    
    /**
     * 
     * Obtiene la informacion de un parametro fisicoquimico
     * 
     * @param codigo
     * @return rset 
     */
    public ResultSet getPrmfisicoquimico(int codigo){
    
        String query = "SELECT pf.CODIGO AS Codigo, pf.DESCRIPCION AS Descripcion, \n" +
                        "tpf.DESCRIPCION AS TiposParamFisicoquimico, um.DESCRIPCION AS UnidadMedida, "
                        + "tpf.CODIGO CodigoTipoParm, um.CODIGO as CodigoUnidad \n" +
                        "FROM TB_PARAM_FISICOQUIMICOS pf\n" +
                        "JOIN TB_TIPOS_PARM_FISICOQUIMICOS tpf ON pf.FK_TIPO_PARAM_FISICOQUIMICO = tpf.CODIGO\n" +
                        "JOIN TB_UNIDADES_MEDIDAS um ON pf.FK_UNIDAD_MEDIDA = um.CODIGO WHERE pf.CODIGO = " + codigo;
        
        
        query += " ORDER BY pf.DESCRIPCION ASC";
        
        ResultSet rset = db.ejecutar(conn, query);

        return rset;
        
    }
    //-----------------------------------------------------------------------------
    
    
     /**
     * 
     * Obtiene la informacion de los parametros fisicoquimicos dependiendo de un Tipo Parametro de la bd
     * y la devuelve en un ResultSet
     * 
     * @param tipoParametro
     * @return rset 
     */
    public ResultSet getParamFisicoQuimicosXTipoParametro(String tipoParametro){
        String query = "SELECT pf.CODIGO AS Codigo"
                + ", pf.DESCRIPCION AS Descripcion"
                + ", tpf.DESCRIPCION AS TiposParamFisicoquimico"
                + ", um.DESCRIPCION AS UnidadMedida " 
                + "FROM TB_PARAM_FISICOQUIMICOS pf " 
                + "JOIN TB_TIPOS_PARM_FISICOQUIMICOS tpf ON pf.FK_TIPO_PARAM_FISICOQUIMICO = tpf.CODIGO " 
                + "JOIN TB_UNIDADES_MEDIDAS um ON pf.FK_UNIDAD_MEDIDA = um.CODIGO " 
                + "WHERE pf.FK_TIPO_PARAM_FISICOQUIMICO IN (" + tipoParametro + ")";
        
        ResultSet rset = db.ejecutar(conn, query);
        return rset;
    }
    
    
    /**
     * 
     * Obtiene la informacion de los parametros fisicoquimicos
     * necesaria para alimentar la pantalla de informacion Tecnica.
     * 
     * @param codigoCiiu
     * @return 
     */
    public ResultSet getParametrosParaInfoTecnica(int puntoMonitoreo, int jornada, int codigoCiiu) throws Exception{
        
        CallableStatement callableStatement = null;
        
        try{

            //Preparamos y ejecutamos el procedimiento.
            callableStatement = conn.prepareCall(Queries
                    .getString("SelParamFisicoquimicosInfoTec"));

            //Preparamos los parametros
            callableStatement.setInt(1, puntoMonitoreo);
            callableStatement.setInt(2, jornada);
            callableStatement.setInt(3, codigoCiiu);

            //Ejecutamos el procedimiento
            ResultSet rset = callableStatement.executeQuery();

            return rset;
            
        }catch(Exception e){

            db.desconectar(conn);
            throw e;

        }
        
    }
    
    public ResultSet getParametrosParaInfoTecnica(int codigoCiiu) throws Exception{
        
        CallableStatement callableStatement = null;
        
        try{

            //Preparamos y ejecutamos el procedimiento.
            callableStatement = conn.prepareCall(Queries
                    .getString("SelParamFisicoquimicosInfoTecEmpty"));

            //Preparamos los parametros
            callableStatement.setInt(1, codigoCiiu);


            //Ejecutamos el procedimiento
            ResultSet rset = callableStatement.executeQuery();

            return rset;
            
        }catch(Exception e){

            db.desconectar(conn);
            throw e;

        }
        
    }
    
    /**
     * 
     * Cierra la conexion con la bd.
     * 
     */
    public void desconectar()
    {
        db.desconectar(this.conn);
    }
}
