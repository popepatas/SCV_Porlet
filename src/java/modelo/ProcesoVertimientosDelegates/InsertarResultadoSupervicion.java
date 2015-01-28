/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.ProcesoVertimientosDelegates;

import configuracion.Queries;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import modelo.DbManager;

/**
 *
 * @author illustrato
 */
public class InsertarResultadoSupervicion {
    
   

    private int codigo;
    private int tecnico;
    private String observacion;
    private String estuvo;
    private int error = 0;
    private DbManager db;

    
    public InsertarResultadoSupervicion(int codigo, int tecnico, String observacion, String estuvo) {
        this.tecnico = tecnico;
        this.codigo = codigo;
        this.observacion = observacion;
        this.estuvo = estuvo;
        
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
                    .getString("SpInsertarResultadoSupervision"));

            //Preparamos los parametros
            callableStatement.setInt(1, codigo);
            callableStatement.setInt(2, tecnico);             
            callableStatement.setString(3, observacion);
            callableStatement.setString(4, estuvo);
            callableStatement.registerOutParameter(5, Types.INTEGER);
            
             
            //Ejecutamos el procedimiento
            callableStatement.execute();
            
            error = callableStatement.getInt(5);
            
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
