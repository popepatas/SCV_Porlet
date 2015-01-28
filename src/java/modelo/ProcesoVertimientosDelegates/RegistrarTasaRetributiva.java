/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.ProcesoVertimientosDelegates;

import configuracion.Queries;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import modelo.DbManager;

/**
 *
 * @author illustrato
 */
public class RegistrarTasaRetributiva {
    private int codigoProceso;
    private int codigoParametro;
    private String valorTarifa;
    private String procentajeRemocion;
    private String valorTasa;
    private String valorCarga;    
    private String valorTasaCobrada;
    private String valorTotalTasaPagar;
    private int error = -1;
    private DbManager db;
    private CallableStatement callableStatement;


    public RegistrarTasaRetributiva(int codigoProceso, int codigoParametro, String valorTarifa, String procentajeRemocion, String valorTasa, String valorCarga, String valorTasaCobrada, String valorTotalTasaPagar) {
        this.codigoProceso = codigoProceso;
        this.codigoParametro = codigoParametro;
        this.valorTarifa = valorTarifa;
        this.procentajeRemocion = procentajeRemocion;
        this.valorTasa = valorTasa;
        this.valorCarga = valorCarga;
        this.valorTasaCobrada = valorTasaCobrada;
        this.valorTotalTasaPagar  = valorTotalTasaPagar;
         db = new DbManager();
        callableStatement = null;
    }
    
    
    
    /**
     *
     * Ejecuta el procedimiento almacenado SpRegistraSupervision.
     *
     * @throws Exception
     */
    public void ejecutar() throws Exception{

        
        
        //Conectamos con la bd.
        Connection conn = db.conectar();
        
        try{

            //Preparamos y ejecutamos el procedimiento.
            callableStatement = conn.prepareCall(Queries
                    .getString("SpRegistrarTasaRetributiva"));

            //Preparamos los parametros
            callableStatement.setInt(1, codigoProceso);
            callableStatement.setInt(2, codigoParametro);
            setParamDoubleNulo(3, valorTarifa); 
            setParamDoubleNulo(4,procentajeRemocion ); 
            setParamDoubleNulo(5, valorTasa );              
            setParamDoubleNulo(6, valorCarga);            
            setParamDoubleNulo(7, valorTotalTasaPagar);
            setParamDoubleNulo(8, valorTasaCobrada); 
            
            
           //Registramos el parametro de Salida
            callableStatement.registerOutParameter(9, java.sql.Types.INTEGER);
            
            //Ejecutamos el procedimiento
            callableStatement.execute();
            
             //Guardamos el parametro de salida
            error  = callableStatement.getInt(9);
            
            //Cerramos la conexion
            db.desconectar(conn);

        }catch(Exception e){

            db.desconectar(conn);
            throw e;

        }

    }
    //-----------------------------------------------------------------------------

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }
    
    private void setParamDoubleNulo(int index, String numero) throws SQLException{
    
        if( numero == null || "".equals(numero) ){
            
            callableStatement.setNull(index,Types.NULL); 
            
        }else{
            
            Double number = Double.parseDouble(numero);
            callableStatement.setDouble(index, number);
        }
        
    }

}
