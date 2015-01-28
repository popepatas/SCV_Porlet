package modelo.ProcesoVertimientosDelegates;

import configuracion.Queries;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;
import modelo.DbManager;

public class InsertarInfoTecDetallesJornada {

    private String codigoPunto;
    private String codigoParametro;
    private String valorInforme;
    private String observacion;
    private String cumple;
    private String codigoJornadaPuntoMonitoreo;
    private String indicadorMenor;
    private DbManager db;
    private CallableStatement callableStatement;

    
    public InsertarInfoTecDetallesJornada(String codigoPunto, String codigoParametro, String valorInforme, String observacion, String cumple, String codigoJornadaPuntoMonitoreo,String indicadorMenor) {
        this.codigoPunto = codigoPunto;
        this.codigoParametro = codigoParametro;
        this.valorInforme = valorInforme;
        this.observacion = observacion;
        this.cumple = cumple;
        this.codigoJornadaPuntoMonitoreo = codigoJornadaPuntoMonitoreo;
        this.indicadorMenor = indicadorMenor;
        db = new DbManager();
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
                    .getString("SpInsertarInfoTecDetalleJornada"));

            //Preparamos los parametros
            setParamIntegerNulo(1, codigoPunto);
            setParamIntegerNulo(2, codigoParametro);
            setParamDoubleNulo(3, valorInforme);
            setParamStringNulo(4, observacion);
            setParamStringNulo(5, cumple);
            setParamIntegerNulo(6, codigoJornadaPuntoMonitoreo);
            setParamStringNulo(7, indicadorMenor);
            
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

    private void setParamIntegerNulo(int index, String numero) throws SQLException{
    
        if(numero == "" || numero == null){
            
            callableStatement.setNull(index,Types.NULL); 
            
        }else{
            
            int number = Integer.parseInt(numero);
            callableStatement.setInt(index, number);
        }
        
    }
    
    
    private void setParamDoubleNulo(int index, String numero) throws SQLException{
    
        if(numero == "" || numero == null){
            
            callableStatement.setNull(index,Types.NULL); 
            
        }else{
            
            double number = Double.parseDouble(numero);
            callableStatement.setDouble(index, number);
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
