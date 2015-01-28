/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.AutenticacionDelegate;

import configuracion.Queries;
import java.sql.CallableStatement;
import java.sql.Connection;
import modelo.DbManager;

/**
 *
 * @author illustrato
 */
public class InsertarPermisos {
    
    private CallableStatement callableStatement;
    private int codigo_rol;
    private int codigo_pantalla;
    private String valor;
    private Integer resultado;
    private DbManager db;

 
    public Integer getResultado() {
        return resultado;
    }

    public void setResultado(Integer resultado) {
        this.resultado = resultado;
    }
   
    
    
    public InsertarPermisos(int codigo_rol, int codigo_pantalla, String valor) {
        
        this.codigo_rol = codigo_rol;
        this.codigo_pantalla = codigo_pantalla;
        this.valor = valor;
        db = new DbManager();
        callableStatement = null;
        
    }

    
    
     /**
     *
     * Ejecuta el procedimiento almacenado SpInsertarPermisos.
     *
     * @throws Exception
     */
    public void ejecutar() throws Exception{
        
        //Conectamos con la bd.
        Connection conn = db.conectar();
        
        try{

            //Preparamos y ejecutamos el procedimiento.
            callableStatement = conn.prepareCall(Queries
                    .getString("SpInsertarPermisos"));

            //Preparamos los p/*arametros
            callableStatement.setInt(1, codigo_rol);
            callableStatement.setInt(2, codigo_pantalla);
            callableStatement.setString(3, valor);
           
            //Obtenemos el paramertro de salida el SP
            callableStatement.registerOutParameter(4, java.sql.Types.INTEGER);
            
            //Ejecutamos el procedimiento3
            callableStatement.execute();

            resultado = callableStatement.getInt(4);
            
            //Cerramos la conexion
            db.desconectar(conn);

        }catch(Exception e){

            db.desconectar(conn);
            throw e;

        }

 }
    
}
