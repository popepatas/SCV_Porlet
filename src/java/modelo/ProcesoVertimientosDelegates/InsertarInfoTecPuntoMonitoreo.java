package modelo.ProcesoVertimientosDelegates;

import configuracion.Queries;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;
import modelo.DbManager;

public class InsertarInfoTecPuntoMonitoreo {

    private String fechaMonitoreo;
    private String jornadaProductivaDia;
    private String jornadaProductivaHora;
    private String jornadaProductivaObservacion;
    private String codigoPunto;
    private DbManager db;
    private CallableStatement callableStatement;
    private int codigoMonitoreo;

    
    
    public InsertarInfoTecPuntoMonitoreo(String fechaMonitoreo, String jornadaProductivaDia, String jornadaProductivaHora, String jornadaProductivaObservacion, String codigoPunto) {
        this.fechaMonitoreo = fechaMonitoreo;
        this.jornadaProductivaDia = jornadaProductivaDia;
        this.jornadaProductivaHora = jornadaProductivaHora;
        this.jornadaProductivaObservacion = jornadaProductivaObservacion;
        this.codigoPunto = codigoPunto;
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
                    .getString("SpInsertarInfoTecPuntoMonitoreo"));

            //Preparamos los parametros
            setParamStringNulo(1, fechaMonitoreo);
            setParamIntegerNulo(2, jornadaProductivaDia);
            setParamIntegerNulo(3, jornadaProductivaHora);
            setParamStringNulo(4, jornadaProductivaObservacion);
            setParamIntegerNulo(5, codigoPunto);
            callableStatement.registerOutParameter(6, java.sql.Types.INTEGER);
            
            //Ejecutamos el procedimiento
            callableStatement.execute();
            
            codigoMonitoreo = callableStatement.getInt(6);
            
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
        
    
    private void setParamStringNulo(int index, String param) throws SQLException{
    
        if(param == "" || param == null){
            
            callableStatement.setNull(index,Types.NULL); 
            
        }else{
            
            callableStatement.setString(index, param);
        }
        
    }

}
