package modelo.ParametrizacionDelegates;

import configuracion.Queries;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import modelo.DbManager;

public class InsertarPrmfisicoquimico {

    //Atributos
    private String descripcion;
    private int tipoParametro;
    private int unidadMedida;
    private int salida;
    private DbManager db;
    
    

    public int getSalida() {
        return salida;
    }
    

    public void setSalida(int salida) {
        this.salida = salida;
    }
    
    /**
     * 
     * Constructor
     * 
     * @param descripcion
     * @param tipoParametro
     * @param unidadMedida 
     */
    public InsertarPrmfisicoquimico(String descripcion, int tipoParametro, int unidadMedida) {
        this.descripcion = descripcion;
        this.tipoParametro = tipoParametro;
        this.unidadMedida = unidadMedida;
        db = new DbManager();
    }

    /**
     *
     * Ejecuta el procedimiento almacenado SpInsertarPrmfisicoquimicos.
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
                    .getString("SpInsertarPrmfisicoquimicos"));

            //Preparamos los parametros
            callableStatement.setString(1, descripcion);
            callableStatement.setInt(2, tipoParametro);
            callableStatement.setInt(3, unidadMedida);
             //Obtenemos el paramertro de salida el SP
            callableStatement.registerOutParameter(4, java.sql.Types.INTEGER);
            //Ejecutamos el procedimiento
            callableStatement.execute();
            
            salida = callableStatement.getInt(4);

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
