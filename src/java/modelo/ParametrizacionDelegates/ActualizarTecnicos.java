package modelo.ParametrizacionDelegates;

import configuracion.Queries;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import modelo.DbManager;

public class ActualizarTecnicos {

    //Atributos
    private int codigo;
    private String nombres;
    private String apellidos;
    private int tipoDocumento;
    private String documento;
    private int estado;
    private int tipoPersona;
    private DbManager db;


    /**
     * 
     * Constructor
     * 
     * @param codigo
     * @param nombres
     * @param apellidos
     * @param tipoDocumento
     * @param documento
     * @param estado
     * @param tipoPersona 
     */
    public ActualizarTecnicos(int codigo, String nombres, String apellidos, int tipoDocumento, String documento,
            int estado, int tipoPersona) {

        this.codigo = codigo;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.tipoDocumento = tipoDocumento;
        this.documento = documento;
        this.estado = estado;
        this.tipoPersona = tipoPersona;
        db = new DbManager();

    }




    /**
     *
     * Ejecuta el procedimiento almacenado SpActualizarTecnicos.
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
                    .getString("SpActualizarTecnicos"));

            //Preparamos los parametros
            callableStatement.setInt(1, codigo);
            callableStatement.setString(2, nombres);
            callableStatement.setString(3, apellidos);
            callableStatement.setInt(4, tipoDocumento);
            callableStatement.setString(5, documento);
            callableStatement.setInt(6,estado);
            callableStatement.setInt(7, tipoPersona);

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
