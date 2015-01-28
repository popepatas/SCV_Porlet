package modelo.ProcesoVertimientosDelegates;

import configuracion.Queries;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Types;
import modelo.DbManager;

public class InsertarPuntoMonitoreo {

    private int codigoPunto;
    private int actividadEconomica;
    private int codigoMonitoreo;
    private DbManager db;

    
    public InsertarPuntoMonitoreo(int codigoPunto, int actividadEconomica, int codigoMonitoreo) {
        this.codigoPunto = codigoPunto;
        this.actividadEconomica = actividadEconomica;
        this.codigoMonitoreo = codigoMonitoreo;
        db = new DbManager();
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
                    .getString("SpInsertarPuntoMonitoreo"));

            //Preparamos los parametros
            callableStatement.setInt(1, codigoPunto);
            callableStatement.setInt(2, actividadEconomica);
            callableStatement.setInt(3, codigoMonitoreo);
            
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
