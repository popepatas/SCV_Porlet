/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.ProcesoVertimientosDelegates;

import configuracion.Queries;
import java.sql.CallableStatement;
import java.sql.Connection;
import modelo.DbManager;

/**
 *
 * @author illustrato
 */
public class FinalizarProcesoVertimientos {
    
    private int respuesta;
    private int codigoProceso;
    private DbManager db;
    
    
    
    /**
     *
     * Constructor
     *
     * @param codigo
     * @param error
     */
    public FinalizarProcesoVertimientos(Integer codigoProceso){
        this.codigoProceso = codigoProceso;
        db = new DbManager();
    }

    
    
    /**
     * 
     * Getters y Setters
     * 
     */
    public int getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(int error) {
        this.respuesta = error;
    }
       
    
    
     /**
     *
     * Ejecuta el procedimiento almacenado SpEliminarUnidadesMedida.
     *
     * @throws Exception
     */
    public void ejecutar() throws Exception{

        CallableStatement callableStatement = null;

        //Conectamos con la bd.
        Connection conn = db.conectar();

        try{

            //Preparamos y ejecutamos el procedimiento.
            callableStatement = conn.prepareCall(Queries.
                    getString("SpFinalizarProceso"));

            //Preparamos los parametros            
            callableStatement.setInt(1, codigoProceso);
            
            
            //Obtenemos el paramertro de salida el SP
            callableStatement.registerOutParameter(2, java.sql.Types.NUMERIC);

            //Ejecutamos el procedimiento
            callableStatement.execute();
            
            //Almacenamos el paramertro de salida en la variable error          
            respuesta =  callableStatement.getInt(2);

            //Cerramos la conexion
            db.desconectar(conn);


        }catch(Exception e){

            db.desconectar(conn);
            throw e;

        }finally{

            if(callableStatement != null){

                callableStatement.clearParameters();
                callableStatement.close();

            }
        }

    }
    
    
    
}
