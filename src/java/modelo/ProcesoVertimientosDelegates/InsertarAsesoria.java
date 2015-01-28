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
public class InsertarAsesoria {
    
    
    private String requiereVisita;
    private String contacto;
    private String asunto;
    private String fecha_asesoria;
    private Integer codigoProceso;
    private String tipoContacto;
    private DbManager db;
    private CallableStatement callableStatement;

 
    public InsertarAsesoria(String requiereVisita, String contacto, String asunto, String fecha_asesoria, Integer codigoProceso,String tipoContacto) {
        this.requiereVisita = requiereVisita;
        this.contacto = contacto;
        this.asunto = asunto;
        this.fecha_asesoria = fecha_asesoria;
        this.codigoProceso = codigoProceso;
        this.tipoContacto = tipoContacto;
        db = new DbManager();
        callableStatement = null;
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
                    .getString("SpInsertarAsesoria"));


            //Preparamos los parametros
            callableStatement.setInt(1, codigoProceso);
            setParamStringNulo(2,requiereVisita);
            setParamStringNulo(3,contacto);
            setParamStringNulo(4,asunto);
            setParamStringNulo(5,fecha_asesoria);            
            setParamIntegerNulo(6,tipoContacto);

            //Ejecutamos el procedimiento.
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
    
        if( numero == null || "".equals(numero) ){
            
            callableStatement.setNull(index,Types.NULL); 
            
        }else{
            
            int number = Integer.parseInt(numero);
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
    

