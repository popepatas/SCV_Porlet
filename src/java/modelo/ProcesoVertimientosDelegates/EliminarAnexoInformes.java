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
public class EliminarAnexoInformes {
       
    private int error;
    private int codigo;
    private int codigoProceso;
    private DbManager db;
    
    
    
    /**
     *
     * Constructor
     *
     * @param codigo
     */
    public EliminarAnexoInformes(int codigo, int codigoProceso){
        this.codigo = codigo;
        this.codigoProceso = codigoProceso;
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
     * Ejecuta el procedimiento almacenado SpElmAdjuntoInformes.
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
                    getString("SpElmAdjuntoInformes"));

            //Preparamos los parametros            
            callableStatement.setInt(1, codigo);
            callableStatement.setInt(2, codigoProceso);
            
            
            //Obtenemos el paramertro de salida el SP
            callableStatement.registerOutParameter(3, java.sql.Types.VARCHAR);

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
