package modelo.ProcesoVertimientosDelegates;

import configuracion.Queries;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;
import modelo.DbManager;

public class ActualizarEntidadLodos {

    //Atributos
    private String nombreEmpresa;
    private String recolecta;
    private String volumen;
    private String fechaRecoleccion;
    private String frecuencia;
    private String transporte;
    private String dispone;
    private String sitioDispone;
    private String codigo;
    private String codigoLodos;
    private CallableStatement callableStatement;
    private DbManager db;

    public ActualizarEntidadLodos(String nombreEmpresa, String recolecta, String volumen, String fechaRecoleccion, String frecuencia, 
            String transporte, String dispone, String sitioDispone, String codigo, String codigoLodos) {
        this.nombreEmpresa = nombreEmpresa;
        this.recolecta = recolecta;
        this.volumen = volumen;
        this.fechaRecoleccion = fechaRecoleccion;
        this.frecuencia = frecuencia;
        this.transporte = transporte;
        this.dispone = dispone;
        this.sitioDispone = sitioDispone;
        this.codigo = codigo;
        this.codigoLodos = codigoLodos;
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
                    .getString("SpActualizarEntidadLodos"));

            //Preparamos los parametros
            setParamStringNulo(1, nombreEmpresa);
            setParamStringNulo(2,recolecta);
            setParamIntegerNulo(3, volumen);
            setParamStringNulo(4, fechaRecoleccion);
            setParamStringNulo(5,frecuencia);
            setParamStringNulo(6, transporte);
            setParamStringNulo(7,sitioDispone);
            setParamIntegerNulo(8, codigo);
            setParamStringNulo(9,dispone);
            setParamIntegerNulo(10,codigoLodos);
        
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
        
    
    private void setParamStringNulo(int index, String param) throws SQLException{
    
        if(param == "" || param == null){
            
            callableStatement.setNull(index,Types.NULL); 
            
        }else{
            
            callableStatement.setString(index, param);
        }
        
    }
}
