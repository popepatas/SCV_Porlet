package modelo.ParametrizacionDelegates;

import configuracion.Queries;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import modelo.DbManager;

public class InsertarUnidadesMedida {

    //Atributos
    private String descripcion;
    private int error;
    private DbManager db;


/**
 * 
 * Constructor
 * 
 * @param descripcion 
 */
    public InsertarUnidadesMedida(String descripcion) {

        this.descripcion = descripcion;
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
     * Ejecuta el procedimiento almacenado SpInsertarUnidadesMedida.
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
                    .getString("SpInsertarUnidadesMedida"));

            //Preparamos los parametros
            callableStatement.setString(1, descripcion);
 
            //Obtenemos el paramertro de salida el SP
            callableStatement.registerOutParameter(2, java.sql.Types.NUMERIC);

            //Ejecutamos el procedimiento
            callableStatement.execute();
            
            //Almacenamos el paramertro de salida en la variable error          
            error =  callableStatement.getInt(2);

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
