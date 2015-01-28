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
public class ActualizarDocumentacionRequerida {
    
    
    //Atributos
    private int codigo;
    private String descripcion;
    private int tipoInforme;
    private DbManager db;


    /**
     * 
     * Constructor
     * 
     * @param codigo
     * @param descripcion 
     * @param tipoInforme 
     */
    public ActualizarDocumentacionRequerida(int codigo, String descripcion, int tipoInforme) {

        this.codigo = codigo;
        this.descripcion = descripcion;
        this.tipoInforme = tipoInforme;
        db = new DbManager();

    }




    /**
     *
     * Ejecuta el procedimiento almacenado SpActualizarDocumentacionRequerida.
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
                    .getString("SpActualizarDocumentacionRequerida"));

            //Preparamos los parametros
            callableStatement.setInt(1, codigo);
            callableStatement.setString(2, descripcion);
            callableStatement.setInt(3, tipoInforme);
            

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
