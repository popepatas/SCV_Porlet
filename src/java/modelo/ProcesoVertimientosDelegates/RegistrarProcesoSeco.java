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
public class RegistrarProcesoSeco {
    
    private int codigoProceso;
    private Integer laboratorioProcesoSeco;
    private Integer consultorProcesoSeco;
    private String fechaEntregaProcesoSeco; 
    private String fechaRadicacionProcesoSeco;
    private String fechaEntDevolProcesoSeco;
    private String fechaDevolProcesoSeco;
    private String observacionDevolProsesoSeco;
    private Integer tipoDevolProcesoSeco; 
    private String observacionesProcesoSeco;
    private int resultado;
    private DbManager db;
    private CallableStatement callableStatement;
            
    /*     
     * Llama al delegate para registrar proceso seco
     * 
     * @param codigoProceso
     * @param laboratorioProcesoSeco
     * @param consultorProcesoSeco
     * @param fechaEntregaProcesoSeco
     * @param fechaRadicacionProcesoSeco
     * @param fechaEntDevolProcesoSeco
     * @param fechaDevolProcesoSeco
     * @param observacionDevolProsesoSeco
     * @param tipoDevolProcesoSeco
     */
    public RegistrarProcesoSeco(int codigoProceso, int laboratorioProcesoSeco, Integer consultorProcesoSeco, String fechaEntregaProcesoSeco, String fechaRadicacionProcesoSeco,  String fechaEntDevolProcesoSeco, String fechaDevolProcesoSeco, String observacionDevolProsesoSeco, Integer tipoDevolProcesoSec, String observacionesProcesoSeco ) {
        this.codigoProceso = codigoProceso;
        this.laboratorioProcesoSeco = laboratorioProcesoSeco;
        this.consultorProcesoSeco = consultorProcesoSeco;
        this.fechaEntregaProcesoSeco = fechaEntregaProcesoSeco;
        this.fechaRadicacionProcesoSeco = fechaRadicacionProcesoSeco;
        
        this.fechaEntDevolProcesoSeco = fechaEntDevolProcesoSeco;
        this.fechaDevolProcesoSeco = fechaDevolProcesoSeco;
        this.observacionDevolProsesoSeco = observacionDevolProsesoSeco;
        this.tipoDevolProcesoSeco = tipoDevolProcesoSeco;
        this.observacionesProcesoSeco = observacionesProcesoSeco;
        db = new DbManager();
        callableStatement = null;
    }

    public int getResultado() {
        return resultado;
    }

    public void setResultado(int resultado) {
        this.resultado = resultado;
    }
    
      
    
     /**
     *
     * Ejecuta el procedimiento almacenado SpRegistrarProcesoSeco.
     *
     * @throws Exception
     */
    public void ejecutar() throws Exception{

      
        
        //Conectamos con la bd.
        Connection conn = db.conectar();
        
        try{

            //Preparamos y ejecutamos el procedimiento.
            callableStatement = conn.prepareCall(Queries
                    .getString("SpRegistrarProcesoSeco"));

            //Preparamos los parametros
            callableStatement.setInt(1, codigoProceso);
            setParamIntegerNulo(2, laboratorioProcesoSeco);               
            setParamIntegerNulo(3, consultorProcesoSeco);
            callableStatement.setString(4, fechaRadicacionProcesoSeco);
            callableStatement.setString(5, fechaEntregaProcesoSeco);
            
            setParamIntegerNulo(6,tipoDevolProcesoSeco);            
            setParamStringNulo(7,fechaDevolProcesoSeco);                        
            setParamStringNulo(8,fechaEntDevolProcesoSeco);
            setParamStringNulo(9,observacionDevolProsesoSeco);
            
            setParamStringNulo(10, observacionesProcesoSeco);
         
            callableStatement.registerOutParameter(11, Types.INTEGER);
            
            //Ejecutamos el procedimiento
            callableStatement.execute();

            //Obtenemos el resultado
             this.resultado = callableStatement.getInt(11);
            
            //Cerramos la conexion
            db.desconectar(conn);

        }catch(Exception e){

            db.desconectar(conn);
            throw e;

        }

    }
    
      private void setParamIntegerNulo(int index, Integer numero) throws SQLException{
    
        if( numero == null){
            
            callableStatement.setNull(index,Types.NULL); 
            
        }else{
            
            int number = numero;
            callableStatement.setInt(index, number);
        }
        
    }
      
     private void setParamStringNulo(int index, String param) throws SQLException{
    
        if(param.equals("") || param == null){
            
            callableStatement.setNull(index,Types.NULL); 
            
        }else{
            
            callableStatement.setString(index, param);
        }
        
    }
    
        
}
