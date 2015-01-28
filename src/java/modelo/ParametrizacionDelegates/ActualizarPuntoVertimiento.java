package modelo.ParametrizacionDelegates;

import configuracion.Queries;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import modelo.DbManager;

public class ActualizarPuntoVertimiento {

    //Atributos
    private String codigo;
    private String descripcion;
    private String latitud;
    private String longitud;
    private String observacion;
    private String tipoEstructura;
    private int estado;
    private Double contrato;
    private DbManager db;
    private CallableStatement callableStatement;

    
    /**
     * 
     * Constructor
     * 
     * @param codigo
     * @param descripcion
     * @param latitud
     * @param longitud
     * @param observacion
     * @param estado
     * @param contrato 
     */
    public ActualizarPuntoVertimiento(String codigo, String descripcion, String latitud, String longitud, 
            String observacion, int estado, Double contrato, String tipoEstructura) {
        this.codigo = codigo;
        this.descripcion = descripcion;
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
     * Ejecuta el procedimiento almacenado SpActualizarUnidadesMedida.
     *
     * @throws Exception
     */
    public void ejecutar() throws Exception{

        //Conectamos con la bd.
        Connection conn = db.conectar();

        try{

            //Preparamos y ejecutamos el procedimiento.
            callableStatement = conn.prepareCall(Queries
                    .getString("SpActualizarPuntoVert"));

            //Preparamos los parametros
            callableStatement.setString(1, descripcion);
            callableStatement.setString(2, latitud);
            callableStatement.setString(3, longitud);
            callableStatement.setString(4, observacion);
            callableStatement.setInt(5, estado);
            callableStatement.setDouble(6, contrato);
                     
            setParamIntegerNulo(7, codigo);
            callableStatement.setString(8, tipoEstructura);
            
            //Ejecutamos el procedimiento
            callableStatement.execute();

            //Cerramos la conexion
            db.desconectar(conn);

        }catch(Exception e){

            db.desconectar(conn);
            throw e;

        }

    }


    private void setParamIntegerNulo(int index, String numero) throws SQLException{
    
        if(numero == null ){
            
            callableStatement.setNull(index,Types.NULL); 
            
        }else{
            
            int number = Integer.parseInt(numero);
            callableStatement.setInt(index, number);
        }
        
    }



}
