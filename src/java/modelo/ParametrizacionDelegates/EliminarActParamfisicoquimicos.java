/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.ParametrizacionDelegates;

import configuracion.Queries;
import java.sql.CallableStatement;
import java.sql.Connection;
import modelo.DbManager;

/**
 *
 * @author illustrato
 */
public class EliminarActParamfisicoquimicos {
    
    private int error;
    private int codigoActividad;
    private int codigoParam;
    private DbManager db;
    
    
     /**
     *
     * Constructor
     *
     * @param codigo
     * @param error
     */
    public EliminarActParamfisicoquimicos(int codigoActividad, int codigoParam){
        this.codigoActividad = codigoActividad;
        this.codigoParam = codigoParam;
        db = new DbManager();
    }
    
     /**
     * 
     * Getters y Setters
     * 
     */
    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }
       
         /**
     *
     * Ejecuta el procedimiento almacenado SpEliminarActParamfisicoquimicos.
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
                    getString("SpEliminarActParamfisicoquimicos"));

            //Preparamos los parametros            
            callableStatement.setInt(1, codigoActividad);
            callableStatement.setInt(2, codigoParam);
            
            
            //Obtenemos el paramertro de salida el SP
            callableStatement.registerOutParameter(3, java.sql.Types.INTEGER);

            //Ejecutamos el procedimiento
            callableStatement.execute();
            
            //Almacenamos el paramertro de salida en la variable error          
            error =  callableStatement.getInt(3);

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
