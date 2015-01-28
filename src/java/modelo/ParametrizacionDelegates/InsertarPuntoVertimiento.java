package modelo.ParametrizacionDelegates;

import configuracion.Queries;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import modelo.DbManager;

public class InsertarPuntoVertimiento {

    //Atributos
    private String ubicacion;
    private String latitud;
    private String longitud;
    private String observacion;
    private int estado;
    private Double contrato;
    private DbManager db;
    private String tipoEstructura;

    public InsertarPuntoVertimiento(String ubicacion, String latitud, String longitud, 
            String observacion, int estado, Double contrato, String tipoEstructura) {
        
        this.ubicacion = ubicacion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.observacion = observacion;
        this.estado = estado;
        this.contrato = contrato;
        this.tipoEstructura = tipoEstructura;
        
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
            
            String query = Queries
                    .getString("SpInsertarPuntoVert");
                    
            //Preparamos y ejecutamos el procedimiento.
            callableStatement = conn.prepareCall(query);

            //Preparamos los parametros
            callableStatement.setString(1, ubicacion);
            callableStatement.setString(2, latitud);
            callableStatement.setString(3,longitud);
            callableStatement.setString(4, observacion);
            callableStatement.setInt(5, estado);
            callableStatement.setDouble(6, contrato);
            callableStatement.setString(7, tipoEstructura);

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
