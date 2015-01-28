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
public class ActualizarUsuarios {
    
    private String usuario;
    private Integer codigo;
    private Integer rol;
    private Integer respuesta;  
    private DbManager db;

    public Integer getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(Integer respuesta) {
        this.respuesta = respuesta;
    }

    public ActualizarUsuarios(String usuario, Integer rol, Integer codigo) {
        this.usuario = usuario;
        this.rol = rol;
        this.codigo = codigo;
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
                    .getString("SpActualizarUsuario"));

            //Preparamos los parametros
            callableStatement.setString(1, usuario);
            callableStatement.setInt(2, rol);
            callableStatement.setInt(3, codigo);
            
            //Obtenemos el paramertro de salida el SP
            callableStatement.registerOutParameter(4, java.sql.Types.NUMERIC);

            //Ejecutamos el procedimiento
            callableStatement.execute();
            
            //Almacenamos el paramertro de salida en la variable error          
            respuesta =  callableStatement.getInt(4);

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
}
