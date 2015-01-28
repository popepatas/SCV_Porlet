/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.ProcesoVertimientosDelegates;

import configuracion.Queries;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import modelo.DbManager;

/**
 *
 * @author illustrato
 */
public class ActualizarProcesoVertimientos {
    
    private Integer codigoProceso;
    private String tipoInforme;
    private String informaMonitoreo;
    private DbManager db;
    private CallableStatement callableStatement;

    public ActualizarProcesoVertimientos(Integer codigoProceso, String tipoInforme, String informaMonitoreo) {
        this.codigoProceso = codigoProceso;
        this.tipoInforme = tipoInforme;
        this.informaMonitoreo = informaMonitoreo;
        db = new DbManager();
    }
    
    /**
     *
     * Ejecuta el procedimiento almacenado SpActualizarProcesosVertimientos.
     *
     * @throws Exception
     */
    public void ejecutar() throws Exception{

       
        
        //Conectamos con la bd.
        Connection conn = db.conectar();

        try{
            
            //Preparamos y ejecutamos el procedimiento.
            callableStatement = conn.prepareCall(Queries
                    .getString("SpActualizarProcesosVertimientos"));


            //Preparamos los parametros
            callableStatement.setInt(1, codigoProceso);
            setParamIntegerNulo(2,tipoInforme);
            callableStatement.setString(3,informaMonitoreo);

            //Ejecutamos el procedimiento.
            callableStatement.execute();

            //Cerramos la conexion
            db.desconectar(conn);

        }catch(Exception e){

            db.desconectar(conn);
            throw e;

        }

    }
    //-----------------------------------------------------------------------------
    
     private void setParamIntegerNulo(int index, String numero) throws SQLException{
    
        if( numero == null || "".equals(numero)){
            
            callableStatement.setNull(index,Types.NULL); 
            
        }else{
            
            int number = Integer.parseInt(numero);
            callableStatement.setInt(index, number);
        }
        
    }
    
    
}
