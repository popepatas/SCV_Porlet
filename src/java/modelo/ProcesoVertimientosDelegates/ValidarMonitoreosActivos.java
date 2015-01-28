package modelo.ProcesoVertimientosDelegates;

import configuracion.Queries;
import java.sql.CallableStatement;
import modelo.ParametrizacionDelegates.*;
import java.sql.Connection;
import java.sql.ResultSet;
import modelo.*;


public class ValidarMonitoreosActivos {
    
   //Atributos
    private DbManager db;
    private Connection conn;
    private int codigoProceso;
    private int resultado;
    
    /**
     * 
     * Constructor
     * 
     */
    public ValidarMonitoreosActivos(int codigoProceso) {
        this.codigoProceso = codigoProceso;
        db = new DbManager();
        conn = db.conectar();
    }

    public int getResultado() {
        return resultado;
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
                    .getString("SpValidarMonitoreoActivo"));

            //Preparamos los parametros
            callableStatement.setInt(1, codigoProceso);

            //Registramos el parametro de Salida
            callableStatement.registerOutParameter(2, java.sql.Types.INTEGER);
            
            //Ejecutamos el procedimiento
            callableStatement.execute();
            
            //Guardamos el parametro de salida
            resultado  = callableStatement.getInt(2);
            
            //Cerramos la conexion
            db.desconectar(conn);

        }catch(Exception e){

            db.desconectar(conn);
            throw e;

        }

    }
    //-----------------------------------------------------------------------------
    
}
