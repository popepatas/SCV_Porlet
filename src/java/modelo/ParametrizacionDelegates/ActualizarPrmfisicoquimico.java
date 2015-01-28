package modelo.ParametrizacionDelegates;

import configuracion.Queries;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import modelo.DbManager;

public class ActualizarPrmfisicoquimico {

    //Atributos
    private String descripcion;
    private int tipoParametro;
    private int unidadMedida;
    private int codigo;
    private DbManager db;

    
    
    /**
     * 
     * Constructor
     * 
     * @param descripcion
     * @param tipoParametro
     * @param unidadMedida 
     * @param codigo
     */
    public ActualizarPrmfisicoquimico(String descripcion, int tipoParametro, int unidadMedida, int codigo) {
        this.descripcion = descripcion;
        this.tipoParametro = tipoParametro;
        this.unidadMedida = unidadMedida;
        this.codigo = codigo;
        db = new DbManager();
    }



    /**
     *
     * Ejecuta el procedimiento almacenado SpActualizarPrmfisicoquimicos.
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
                    .getString("SpActualizarPrmfisicoquimicos"));

            //Preparamos los parametros
            callableStatement.setString(1, descripcion);
            callableStatement.setInt(2, tipoParametro);
            callableStatement.setInt(3, unidadMedida);
            callableStatement.setInt(4,codigo);

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
    //-----------------------------------------------------------------------------



}
