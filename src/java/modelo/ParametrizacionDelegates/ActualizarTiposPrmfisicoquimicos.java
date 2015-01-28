package modelo.ParametrizacionDelegates;

import configuracion.Queries;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import modelo.DbManager;

public class ActualizarTiposPrmfisicoquimicos {

    //Atributos
    private int codigo;
    private String descripcion;
    private DbManager db;



    /**
     * 
     * Constructor
     * 
     * @param codigo
     * @param nombres 
     */
    public ActualizarTiposPrmfisicoquimicos(int codigo, String nombres) {

        this.codigo = codigo;
        this.descripcion = nombres;
        db = new DbManager();

    }




    /**
     *
     * Ejecuta el procedimiento almacenado SpActualizarTiposPrmfisicoquimicos.
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
                    .getString("SpActualizarTiposPrmfisicoquimicos"));

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
