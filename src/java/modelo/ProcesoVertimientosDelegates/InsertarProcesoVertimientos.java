/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.ProcesoVertimientosDelegates;

import configuracion.Queries;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;
import modelo.DbManager;

/**
 *
 * @author illustrato
 */
public class InsertarProcesoVertimientos {
    
    
    private Double contrato;
    private String nit;
    private String fechaCreacion;
    private int resultado;
    private int codigoProceso;
    private String ciclo;     
    private String sector;     
    private String pozo;
    private String consumo;
    private String direccion;
    private String usuario_creacion;
    private DbManager db;
    private CallableStatement callableStatement;

    /**
     * 
     * Constructor
     * 
     * @param contrato
     * @param nit
     * @param fechaCreacion
     * @param codigoProceso
     * @param ciclo
     * @param pozo
     * @param consumo
     * @param direccion 
     */
    public InsertarProcesoVertimientos(Double contrato, String nit, String fechaCreacion,
            String ciclo, String pozo, String consumo, String direccion, String sector, String usuario_creacion) {
        this.contrato = contrato;
        this.nit = nit;
        this.fechaCreacion = fechaCreacion;
        this.resultado = resultado;
        this.codigoProceso = codigoProceso;
        this.ciclo = ciclo;
        this.sector = sector;
        this.pozo = pozo;
        this.consumo = consumo;
        this.direccion = direccion;
        this.usuario_creacion = usuario_creacion;
        db = new DbManager();
    }
    
    

    public int getResultado() {
        return resultado;
    }

    public int getCodigoProceso() {
        return codigoProceso;
    }

    /**
     *
     * Ejecuta el procedimiento almacenado SpInsertarAsesoria.
     *
     * @throws Exception
     */
    public void ejecutar() throws Exception{
        
        //Conectamos con la bd.
        Connection conn = db.conectar();

        try{
            
            //Preparamos y ejecutamos el procedimiento.
            callableStatement = conn.prepareCall(Queries
                    .getString("SpInsertarProcesoVertimientos"));


            //Preparamos los parametros
            callableStatement.setDouble(1, contrato);
            callableStatement.setString(2,nit);
            callableStatement.setString(3,fechaCreacion);            
            setParamIntegerNulo(4,ciclo);
            setParamStringNulo(5,sector);
            setParamIntegerNulo(6,pozo);
            callableStatement.setString(7,consumo);
            setParamStringNulo(8,direccion);
            callableStatement.setString(9,usuario_creacion);
            
            
            
            callableStatement.registerOutParameter(10, Types.INTEGER);
            callableStatement.registerOutParameter(11, Types.INTEGER);    
            
            //Ejecutamos el procedimiento.
            callableStatement.execute();

            //Obtenemos la respuesta
            this.resultado = callableStatement.getInt(10);
            this.codigoProceso = callableStatement.getInt(11);
            
            //Cerramos la conexion
            db.desconectar(conn);

        }catch(Exception e){

            db.desconectar(conn);
            throw e;

        }

    }
    //-----------------------------------------------------------------------------

       private void setParamStringNulo(int index, String param) throws SQLException{
    
        if(param.equals("") || param == null){
            
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
    

