package modelo.ParametrizacionDelegates;

import configuracion.Queries;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import modelo.DbManager;

public class ActualizarTiposContactos {

    //Atributos
    private int codigo;
    private String descripcion;
    private DbManager db;


    /**
     * 
     * Constructor
     * 
     * @param codigo
     * @param descripcion 
     */
    public ActualizarTiposContactos(int codigo, String descripcion) {

        this.codigo = codigo;
        this.descripcion = descripcion;
        db = new DbManager();

    }




    /**
     *
     * Ejecuta el procedimiento almacenado SpActualizaTiposContactos.
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
                    .getString("SpActualizaTiposContactos"));

            //Preparamos los parametros
            callableStatement.setString(1, descripcion);
            callableStatement.setInt(2, codigo);


            //Ejecutamos el procedimiento
            callableStatement.execute();

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
