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
public class SeleccionarActParamfisicoquimicos {
    
    
      //Atributos
    private DbManager db;
    private Connection conn;
    
    /**
     * 
     * Constructor
     * 
     */
    public SeleccionarActParamfisicoquimicos() {
        db = new DbManager();
                conn = db.conectar();
    }
    
    
    
    
    /**
     * 
     * Obtiene la informacion de las actividades parametros fisicoquimicos
     * y retorna un ResultSet
     * 
     * @param actividad
     * @param parametro
     * @param rangoFinal
     * @param rangoInicial
     * @param mayorRangoInicial
     * @param mayorRangoFinal
     * @param mostrarRango
     * @return rset 
     */
    public ResultSet getActParamFisicoquimicos(String actividad, String parametro,String rangoInicial, String rangoFinal,
                                                String mayorRangoInicial, String mayorRangoFinal, String mostrarRango){
    try{

        String query = "SELECT "  +
                    "act.DESCRIPCION as  ACTDESP, " +
                    "act.CODIGO_CIIU as CODCIIU, " +
                    "parm.DESCRIPCION as  PARAMFIQUI, " +
                    "actparm.RANGO_INICIAL AS RANGOINICIAL, " +
                    "actparm.RANGO_FINAL AS RANGOFINAL, " +
                    "actparm.MAYOR_QUE_RANGO_INICIAL as MAYORINICIAL , " +
                    "actparm.MAYOR_QUE_RANGO_FINAL as MAYORFINAL, " +
                    "act.CODIGO as  ACTCOD, " +
                    "parm.CODIGO as  PARMCOD, " +                    
                    "CASE actparm.MOSTRAR_RANGO  WHEN 1 THEN 'Mostrar'  WHEN 2 THEN 'Muestra el rango mayor' WHEN 3 THEN 'No mostrar' END  as MOSTRAR_RANGO " +
                    " FROM " +
                    " TB_ACT_PARAMFISICOQUIMICOS actparm " +
                    " INNER JOIN  TB_ACTIVIDADES_ECONOMICAS act ON act.CODIGO = actparm.FK_ACTIVIDAD_ECONOMICA " +
                    " INNER JOIN  TB_PARAM_FISICOQUIMICOS parm ON parm.CODIGO = actparm.FK_PARAM_FISICOQUIMICO " +
                    " WHERE parm.CODIGO IS NOT NULL";
        
        if(!("").equals(actividad) && actividad != null){

            query += " AND act.CODIGO = " + actividad;

        }
        
        if(!("").equals(parametro) && parametro != null){

            query += " AND parm.CODIGO = " + parametro;

        }
        
        if(!("").equals(rangoInicial) && rangoInicial != null){

            query += " AND actparm.RANGO_INICIAL = " + rangoInicial;

        }
        
        if(!("").equals(rangoFinal) && rangoFinal != null){

            query += " AND actparm.RANGO_FINAL = " + rangoFinal;

        }
        
        if(!("").equals(mayorRangoFinal) && mayorRangoFinal != null){

            query += " AND actparm.MAYOR_QUE_RANGO_FINAL LIKE " + mayorRangoFinal;

        }
        
        if(!("").equals(mayorRangoInicial) && mayorRangoInicial != null){

            query += " AND actparm.MAYOR_QUE_RANGO_INICIAL LIKE " + mayorRangoInicial;

        }
        
        if(!("").equals(mayorRangoInicial) && mayorRangoInicial != null){

            query += " AND actparm.MAYOR_QUE_RANGO_INICIAL LIKE " + mayorRangoInicial;

        }
        
        if(!("").equals(mayorRangoInicial) && mayorRangoInicial != null){

            query += " AND actparm.MAYOR_QUE_RANGO_INICIAL LIKE " + mayorRangoInicial;

        }
        
         if(!("").equals(mostrarRango) && mostrarRango != null){

            query += " AND actparm.MOSTRAR_RANGO LIKE " + mostrarRango;

        }
        
        
        System.out.println(query);
        
        ResultSet rset = db.ejecutar(conn, query);

        return rset;
    }catch(Exception e){ 
        return null; 
    }
    }
    //-----------------------------------------------------------------------------
    
    
    
    
    /**
     * 
     * Obtiene la informacion de un registro especifico.
     * 
     * @param codigoActividad
     * @param codigoParam
     * @param rangoInicial
     * @param rangoFinal
     * @param mayorInicial
     * @param mayorFinal
     * @return rset 
     */
    public ResultSet getActParamFisicoquimicos(Integer codigoActividad, 
            Integer codigoParam, String rangoInicial, String rangoFinal, 
            String mayorInicial, String mayorFinal, String mostrarRango){
    
        
        String query = "SELECT " +
                    "actparm.CODIGO as COD_ACTPARM,"+ 
                    "act.DESCRIPCION as ACTDESP,"+ 
                    "act.CODIGO_CIIU as CODCIIU, " +
                    "parm.DESCRIPCION as PARAMFIQUI, " +
                    "actparm.RANGO_INICIAL as RANGOINICIAL, " +
                    "actparm.RANGO_FINAL as RANGOFINAL, " +
                    "actparm.MAYOR_QUE_RANGO_INICIAL as MAYORINICIAL , " +
                    "actparm.MAYOR_QUE_RANGO_FINAL as MAYORFINAL, " +
                    "act.CODIGO as ACTCOD, " +
                    "parm.CODIGO as PARMCOD, " +
                    "actparm.MOSTRAR_RANGO as MOSTRAR_RANGO " +
                    "FROM" +
                    "  TB_ACT_PARAMFISICOQUIMICOS actparm " +
                    " INNER JOIN  TB_ACTIVIDADES_ECONOMICAS act ON act.CODIGO = actparm.FK_ACTIVIDAD_ECONOMICA " +
                    " INNER JOIN  TB_PARAM_FISICOQUIMICOS parm ON parm.CODIGO = actparm.FK_PARAM_FISICOQUIMICO "
                    + " WHERE "
                    + " act.CODIGO IS NOT NULL";
       
        if(codigoActividad != null ){
          query +=  " AND act.CODIGO = "+ codigoActividad;
        }
        
        if(codigoParam != null){
        
            query += " AND parm.CODIGO = "+codigoParam;
        }
         
          if(!("").equals(rangoInicial) && rangoInicial != null){

            query += " AND actparm.RANGO_INICIAL = " + rangoInicial;

        }
        
        if(!("").equals(rangoFinal) && rangoFinal != null){

            query += " AND actparm.RANGO_FINAL = " + rangoInicial;

        }
        
        if(!("").equals(mayorFinal) && mayorFinal != null){

            query += " AND actparm.MAYOR_QUE_RANGO_FINAL LIKE '" + mayorFinal+"'";

        }
         
        if(!("").equals(mayorInicial) && mayorInicial != null){

            query += " AND actparm.MAYOR_QUE_RANGO_INICIAL LIKE '" + mayorInicial+"'";

        }
        
        if(!("").equals(mostrarRango) && mostrarRango != null){

            query += " AND actparm.MOSTRAR_RANGO LIKE " + mostrarRango;

        }
        
        query += " ORDER BY act.DESCRIPCION, parm.DESCRIPCION ASC";
        
        ResultSet rset = db.ejecutar(conn, query);

        return rset;
        
    }
    //-----------------------------------------------------------------------------
    
    public void desconectar()
    {
        db.desconectar(this.conn);
    }
    
}
