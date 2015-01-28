/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.ParametrizacionDelegates;

import configuracion.Queries;
import java.sql.CallableStatement;
import java.sql.Connection;
import modelo.DbManager;

/**
 *
 * @author illustrato
 */
public class InsertarAcreditacionParametros {
    
    
       //Atributos
    private int laboratorio;
    private int parametro;
    private DbManager db;


/**
 * 
 * Constructor
 * 
 * @param descripcion 
 */
    public InsertarAcreditacionParametros(int parametro, int laboratorio ) {

        this.parametro = parametro;
        this.laboratorio = laboratorio;
        db = new DbManager();
        
    }




    /**
     *
     * Ejecuta el procedimiento almacenado SpInsertarUnidadesMedida.
     *
     * @throws Exception
     */
    public void ejecutar() throws Exception{

        CallableStatement callableStatement = null;
        
        //Conectamos con la bd.
        Connection conn = db.conectar();
        
        try{
            
            //Preparamos y ejecutamos el procedimiento.
            callableStatement = conn.prepareCall(Queries
                    .getString("SpAcreditacionParametros"));

            //Preparamos los parametros
            callableStatement.setInt(1, parametro);
            callableStatement.setInt(2, laboratorio); 

            //Ejecutamos el procedimiento
            callableStatement.execute();

            //Cerramos la conexion
            db.desconectar(conn);

        }catch(Exception e){

            db.desconectar(conn);
            throw e;

        }finally{

             if(callableStatement != null){

                callableStatement.clearParameters();
                callableStatement.close();

            }

        }
    }

    
}
