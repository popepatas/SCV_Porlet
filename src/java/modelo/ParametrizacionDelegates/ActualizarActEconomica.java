package modelo.ParametrizacionDelegates;


import configuracion.Queries;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import modelo.DbManager;

public class ActualizarActEconomica {


    //Atributos
    private String descripcion;
    private int ciiu;
    private int codigo;
    private int error;  
    private DbManager db;



    /**
     * 
     * Constructor
     * 
     * @param descripcion
     * @param codigo
     * @param ciiu 
     */
    public ActualizarActEconomica(String descripcion, int codigo, int ciiu){

        this.descripcion = descripcion;
        this.ciiu = ciiu;
        this.codigo = codigo;
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
     * Ejecuta el procedimiento almacenado SpActualizarActEconom.
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
                    .getString("SpActualizarActEconom"));

            //Preparamos los parametros
            callableStatement.setString(1, descripcion);
            callableStatement.setInt(2, codigo);
            callableStatement.setInt(3, ciiu);

        
            //Obtenemos el paramertro de salida el SP
            callableStatement.registerOutParameter(4, java.sql.Types.NUMERIC);

            //Ejecutamos el procedimiento
            callableStatement.execute();
            
            //Almacenamos el paramertro de salida en la variable error          
            error =  callableStatement.getInt(4);

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
