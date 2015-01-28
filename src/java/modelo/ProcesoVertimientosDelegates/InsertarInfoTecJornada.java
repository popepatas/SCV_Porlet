package modelo.ProcesoVertimientosDelegates;

import configuracion.Queries;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;
import modelo.DbManager;

public class InsertarInfoTecJornada {

    private String nombreJornada;
    private String cargaDBO;
    private String cargaSST;
    private String horaInicio;
    private String horaFin;
    private String codigoPunto;
    private String caudalJornada;
    private int codigoPuntoJornada;
    private DbManager db;
    private CallableStatement callableStatement;

    
    public InsertarInfoTecJornada(String nombreJornada, String cargaDBO, String cargaSST, String horaInicio, String horaFin, String codigoPunto, String caudalJornada) {
        this.nombreJornada = nombreJornada;
        this.cargaDBO = cargaDBO;
        this.cargaSST = cargaSST;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.codigoPunto = codigoPunto;
        this.caudalJornada = caudalJornada;
        
        db = new DbManager();
    }

    public int getCodigoPuntoJornada() {
        return codigoPuntoJornada;
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
                    .getString("SpInsertarInfoTecJornada"));

            //Preparamos los parametros
            setParamIntegerNulo(1, nombreJornada);
            setParamDoubleNulo(2, cargaDBO);
            setParamDoubleNulo(3, cargaSST);
            setParamStringNulo(4, horaInicio);
            setParamStringNulo(5, horaFin);
            setParamIntegerNulo(6, codigoPunto);
            setParamDoubleNulo(7, caudalJornada);
            callableStatement.registerOutParameter(8, java.sql.Types.INTEGER);
            
            //Ejecutamos el procedimiento
            callableStatement.execute();
            codigoPuntoJornada = callableStatement.getInt(8);
            
            //Cerramos la conexion
            db.desconectar(conn);

        }catch(Exception e){

            db.desconectar(conn);
            throw e;

        }

    }
    //-----------------------------------------------------------------------------

    private void setParamDoubleNulo(int index, String numero) throws SQLException{
    
        if(numero == "" || numero == null){
            
            callableStatement.setNull(index,Types.NULL); 
            
        }else{
            
            double number = Double.parseDouble(numero);
            callableStatement.setDouble(index, number);
        }
        
    }
    
    private void setParamIntegerNulo(int index, String numero) throws SQLException{
    
        if(numero == "" || numero == null){
            
            callableStatement.setNull(index,Types.NULL); 
            
        }else{
            
            int number = Integer.parseInt(numero);
            callableStatement.setInt(index, number);
        }
        
    }  
        
    
    private void setParamStringNulo(int index, String param) throws SQLException{
    
        if(param == "" || param == null){
            
            callableStatement.setNull(index,Types.NULL); 
            
        }else{
            
            callableStatement.setString(index, param);
        }
        
    }

}
