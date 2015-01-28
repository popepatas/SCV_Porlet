/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.ParametrizacionDelegates;

import configuracion.Queries;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import modelo.DbManager;

/**
 *
 * @author Galatea
 */
public class InsertarActEconomica {


    //Atributos
    private String descripcion;
    private int ciiu;
    private DbManager db;
    private int error;

    
    /**
     * 
     * Constructor
     * 
     * @param descripcion
     * @param ciiu 
     */
    public InsertarActEconomica(String descripcion, int ciiu){

        this.descripcion = descripcion;
        this.ciiu = ciiu;
        db = new DbManager();

    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }




    /**
     *
     * Ejecuta el procedimiento almacenado SpInsertarActEconomica.
     *
     * @throws Exception
     */
    public void ejecutar() throws Exception{

        CallableStatement callableStatement = null;
        
        //Conectamos con la bd.
        Connection conn = db.conectar();

        try{
            
            //Preparamos y ejecutamos el procedimiento.
            callableStatement = conn.prepareCall(Queries
                    .getString("SpInsertarActEconomica"));


            //Preparamos los parametros
            callableStatement.setString(1, descripcion);
            callableStatement.setInt(2, ciiu);

            //Obtenemos el paramertro de salida el SP
            callableStatement.registerOutParameter(3, java.sql.Types.NUMERIC);

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
    //-----------------------------------------------------------------------------

}
