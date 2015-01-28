/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.UsuariosDelegates;

import configuracion.Queries;
import java.sql.CallableStatement;
import java.sql.Connection;
import modelo.DbManager;

/**
 *
 * @author illustrato
 */
public class InsertarUsuarios {
    
    private String usuario;
    private Integer rol;
    private Integer respuesta;  
    private DbManager db;

    public Integer getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(Integer respuesta) {
        this.respuesta = respuesta;
    }

    public InsertarUsuarios(String usuario, Integer rol) {
        this.usuario = usuario;
        this.rol = rol;
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
                    .getString("SpAdicionarUsuario"));

            //Preparamos los parametros
            callableStatement.setString(1, usuario);
            callableStatement.setInt(2, rol);
            
            //Obtenemos el paramertro de salida el SP
            callableStatement.registerOutParameter(3, java.sql.Types.NUMERIC);

            //Ejecutamos el procedimiento
            callableStatement.execute();
            
            //Almacenamos el paramertro de salida en la variable error          
            respuesta =  callableStatement.getInt(3);

            //Cerramos la conexion
            db.desconectar(conn);

        }catch(Exception e){

            db.desconectar(conn);
            respuesta = 0;
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
