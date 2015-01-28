package modelo.ProcesoVertimientosDelegates;

import configuracion.Queries;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;
import modelo.DbManager;

public class InsertarMonitoreo {

    //Atributos
    private String consultorMonitoreo;
    private String fechaMonitoreo;
    private String horaInicio;
    private String horaFin;
    private int laboratorio;
    private int codigoProceso;
    private int codigoMonitoreo;
    private String duraccionMonitoreo;
    private String observacion;
    private CallableStatement callableStatement;
    private DbManager db;
    
    
    public InsertarMonitoreo(String consultorMonitoreo, String fechaMonitoreo, String horaInicio, String horaFin, 
            int laboratorio, int codigoProceso, String observacion, String duraccionMonitoreo) {
        this.consultorMonitoreo = consultorMonitoreo;
        this.fechaMonitoreo = fechaMonitoreo;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.laboratorio = laboratorio;
        this.codigoProceso = codigoProceso;
        this.observacion = observacion;
        this.duraccionMonitoreo = duraccionMonitoreo;
        db = new DbManager();
    }

    
    public int getCodigoMonitoreo() {
        return codigoMonitoreo;
    }
    
    
    /**
     *
     * Ejecuta el procedimiento almacenado SpInsertarUnidadesMedida.
     *
     * @throws Exception
     */
    public void ejecutar() throws Exception{

        
        //Conectamos con la bd.
        Connection conn = db.conectar();
        
        try{

            //Preparamos y ejecutamos el procedimiento.
            callableStatement = conn.prepareCall(Queries
                    .getString("SpInsertarMonitoreo"));

            //Preparamos los parametros
            
            setParamIntegerNulo(1,consultorMonitoreo);
            callableStatement.setString(2, fechaMonitoreo);
            callableStatement.setString(3, horaInicio);
            callableStatement.setString(4, horaFin);
            callableStatement.setInt(5, laboratorio);
            callableStatement.setInt(6, codigoProceso);
            setParamStringNulo(7, observacion);
            callableStatement.setString(8, duraccionMonitoreo);
            //Registramos el parametro de Salida
            callableStatement.registerOutParameter(9, java.sql.Types.INTEGER);
            
            //Ejecutamos el procedimiento
            callableStatement.execute();
            
            //Guardamos el parametro de salida
            codigoMonitoreo  = callableStatement.getInt(9);
            
            //Cerramos la conexion
            db.desconectar(conn);

        }catch(Exception e){

            db.desconectar(conn);
            throw e;

        }

    }
    //-----------------------------------------------------------------------------


    private void setParamStringNulo(int index, String param) throws SQLException{
    
        if(param == ""){
            
            callableStatement.setNull(index,Types.NULL); 
            
        }else{
            
            callableStatement.setString(index, param);
        }
        
    }
    
      private void setParamIntegerNulo(int index, String numero) throws SQLException{
    
        if(numero.equals("") || numero == null){
            
            callableStatement.setNull(index,Types.NULL); 
            
        }else{
            
            int number = Integer.parseInt(numero);
            callableStatement.setInt(index, number);
        }
        
    }
}
