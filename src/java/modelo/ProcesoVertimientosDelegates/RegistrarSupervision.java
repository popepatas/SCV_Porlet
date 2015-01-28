/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.ProcesoVertimientosDelegates;

import configuracion.Queries;
import java.sql.CallableStatement;
import java.sql.Connection;
import modelo.DbManager;

/**
 *
 * @author illustrato
 */
public class RegistrarSupervision {
    
    private int codigoProceso;
    private int tecnico;   
    private int error = -1;
    private DbManager db;

    
    public RegistrarSupervision(int codigoProceso, int tecnico) {
        this.tecnico = tecnico;
        this.codigoProceso = codigoProceso;
        db = new DbManager();
    }
    
    
    
    /**
     *
     * Ejecuta el procedimiento almacenado SpRegistraSupervision.
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
                    .getString("SpRegistraSupervision"));

            //Preparamos los parametros
            callableStatement.setInt(1, codigoProceso);
            callableStatement.setInt(2, tecnico); 
            
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

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    
}
