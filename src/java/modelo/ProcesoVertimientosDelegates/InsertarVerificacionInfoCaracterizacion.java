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
public class InsertarVerificacionInfoCaracterizacion {
      //Atributos
    private String checkeado;
    private Integer codigo;
    private Integer codigoProceso;
    private DbManager db;

    public InsertarVerificacionInfoCaracterizacion(String checkeado, Integer codigo, Integer codigoProceso) {
        this.checkeado = checkeado;
        this.codigo = codigo;
        this.codigoProceso = codigoProceso;       
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
            
            String query =  Queries.getString("SpInsetarVerificacionInfoCaracterizacion");
                    
            //Preparamos y ejecutamos el procedimiento.
            callableStatement = conn.prepareCall(query);

            //Preparamos los parametros
            callableStatement.setInt(1, codigoProceso);
            callableStatement.setString(2, checkeado);
            callableStatement.setInt(3, codigo);
           

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

}
