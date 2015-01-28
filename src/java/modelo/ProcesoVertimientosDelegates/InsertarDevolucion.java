package modelo.ProcesoVertimientosDelegates;

import configuracion.Queries;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;
import modelo.DbManager;

public class InsertarDevolucion {

    //Atributos
    private String tipoDevolucion;
    private String fechaEntrega;
    private String observacion;
    private String codigoProceso;
    private String fechaDevolucion;
    private DbManager db;
    private CallableStatement callableStatement;

    public InsertarDevolucion(String tipoDevolucion, String fechaEntrega, String observacion, String codigoProceso, String fechaDevolucion) {
        this.tipoDevolucion = tipoDevolucion;
        this.fechaEntrega = fechaEntrega;
        this.observacion = observacion;
        this.codigoProceso = codigoProceso;
        this.fechaDevolucion = fechaDevolucion;
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
                    .getString("SpInsertarDevolucion"));

            //Preparamos los parametros
            setParamIntegerNulo(1, tipoDevolucion);
            setParamStringNulo(2, fechaEntrega);
            setParamStringNulo(3, observacion);
            setParamIntegerNulo(4, codigoProceso);
            setParamStringNulo(5, fechaDevolucion);
            
            
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
