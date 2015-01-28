package modelo.ProcesoVertimientosDelegates;

import configuracion.Queries;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Types;
import modelo.DbManager;

public class ActualizarVisita {

    //Atributos
    private int tecnico;
    private int motivo;
    private String observacion;
    private String fechaVisita;
    private int codigo;
    private DbManager db;

    public ActualizarVisita(int tecnico, int motivo, String observacion, String fechaVisita, int proceso) {
        this.tecnico = tecnico;
        this.motivo = motivo;
        this.observacion = observacion;
        this.fechaVisita = fechaVisita;
        this.codigo = proceso;
        db = new DbManager();
    }


    
    /**
     *
     * Ejecuta el procedimiento almacenado SpActualizarVisita.
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
                    .getString("SpActualizarVisita"));

            //Preparamos los parametros
            callableStatement.setInt(1, codigo);
            callableStatement.setInt(2, tecnico);    
            callableStatement.setInt(3, motivo);
            callableStatement.setString(4, fechaVisita);
            callableStatement.setString(5, observacion);
          
            
            //Ejecutamos el procedimiento
            callableStatement.execute();
            
            //Cerramos la conexion
            db.desconectar(conn);

        }catch(Exception e){

            db.desconectar(conn);
            throw e;

        }

    }
    //-----------------------------------------------------------------------------



}
