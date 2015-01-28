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
import java.sql.Types;
import modelo.DbManager;

public class ActualizarConsultores {

    //Atributos
    private String nombres;    
    private String apellidos;
    private String direcciones;
    private String telefono;
    private String telefono2;
    private String correo;
    private String identificacion;        
    private int codigo;
    private DbManager db;
    private CallableStatement callableStatement;

    
    
    /**
     * 
     * Constructor
     * 
     * @param nombres
     * @param apellidos
     * @param direcciones
     * @param telefono
     * @param telefono2
     * @param correo     
     * @param identificacion     
     * @param vigencia     
     * @param codigo 
     */
    public ActualizarConsultores(String nombres, String apellidos, String direcciones, 
            String telefono, String telefono2, String correo, String identificacion, 
            int codigo) {
        
        this.nombres = nombres;        
        this.apellidos = apellidos;
        this.direcciones = direcciones;
        this.telefono = telefono;
        this.telefono2 = telefono2;
        this.correo = correo;
        this.identificacion = identificacion;                
        this.codigo = codigo;
        callableStatement = null;
        db = new DbManager();
    }

   




    /**
     *
     * Ejecuta el procedimiento almacenado SpActualizarLabConsultores.
     *
     * @throws Exception
     */
    public void ejecutar() throws Exception{

     
        
        //Conectamos con la bd.
        Connection conn = db.conectar();
        
        try{

            //Preparamos y ejecutamos el procedimiento.
            callableStatement = conn.prepareCall(Queries
                    .getString("SpActualizarConsultores"));

            //Preparamos los parametros
            callableStatement.setString(1, nombres);            
            setParamStringNulo(2, apellidos);
            setParamStringNulo(3, direcciones);
            setParamLongNulo(4, telefono);
            setParamLongNulo(5, telefono2);
            setParamStringNulo(6, correo);
            callableStatement.setString(7, identificacion);                         
            callableStatement.setInt(8, codigo);
            
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
private void setParamLongNulo(int index, String numero) throws SQLException{
    
        if(numero == ""){
            
            callableStatement.setNull(index,Types.NULL); 
            
        }else{
            
            long number = Long.parseLong(numero);
            callableStatement.setLong(index, number);
        }
        
    }

   private void setParamStringNulo(int index, String param) throws SQLException{
    
        if(param.equals("") || param == null){
            
            callableStatement.setNull(index,Types.NULL); 
            
        }else{
            
            callableStatement.setString(index, param);
        }
        
    }
    


}
