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
public class InsertarDocumentacionRequerida {
       
    //Atributos
    private String descripcion;
    private int tipoInforme;
    private DbManager db;

/**
 * 
 * Constructor
 * 
 * @param descripcion 
 */
    public InsertarDocumentacionRequerida(String descripcion, int tipoInforme) {

        this.descripcion = descripcion;
        this.tipoInforme = tipoInforme;
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
                    .getString("SpInsertarDocumentacionRequerida"));

            //Preparamos los parametros
            callableStatement.setString(1, descripcion);
            callableStatement.setInt(2, tipoInforme);
 

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
    //-----------------------------------------------------------------------------

    
}
