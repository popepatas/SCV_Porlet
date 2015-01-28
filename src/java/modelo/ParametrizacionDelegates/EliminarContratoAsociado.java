/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.ParametrizacionDelegates;

import configuracion.Queries;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import modelo.DbManager;

/**
 *
 * @author illustrato
 */
public class EliminarContratoAsociado {
    
    
    private int codigo;
    private int error;
    private DbManager db;

    public EliminarContratoAsociado(int codigo) {
        this.codigo = codigo;
        this.db =  new DbManager();
    }

   

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }
    
    public void ejecutar() throws SQLException{
    
        CallableStatement callableStatement = null;

        //Conectamos con la bd.
        Connection conn = db.conectar();

        try{

            //Preparamos y ejecutamos el procedimiento.
            callableStatement = conn.prepareCall(Queries.
                    getString("SpEliminarContratoAsociado"));

            //Preparamos los parametros            
            callableStatement.setDouble(1, codigo);
            
            
            //Obtenemos el paramertro de salida el SP
            callableStatement.registerOutParameter(2, java.sql.Types.INTEGER);

            //Ejecutamos el procedimiento
            callableStatement.execute();
            
            //Almacenamos el paramertro de salida en la variable error          
            error =  callableStatement.getInt(2);

            //Cerramos la conexion
            db.desconectar(conn);


        }catch(SQLException e){

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
