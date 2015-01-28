package modelo.UsuariosDelegates;

import modelo.ParametrizacionDelegates.*;
import configuracion.Queries;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import modelo.DbManager;

public class ValidarPermisos {

    //Atributos
    private String pagina;
    private String usuario;
    private int respuesta;
    private DbManager db;

    
    public ValidarPermisos(String pagina, String usuario) {
        this.pagina = pagina;
        this.usuario = usuario;
        db = new DbManager();
    }

    public int getRespuesta() {
        return respuesta;
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
                    .getString("SpTienePermiso"));

            //Preparamos los parametros
            callableStatement.setString(1, usuario);
            callableStatement.setString(2, pagina);
            
            //Obtenemos el paramertro de salida el SP
            callableStatement.registerOutParameter(3, java.sql.Types.NUMERIC);

            //Ejecutamos el procedimiento
            callableStatement.execute();
            
            //Almacenamos el paramertro de salida en la variable error          
            respuesta =  callableStatement.getInt(3);

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
