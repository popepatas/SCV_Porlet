/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.ClientesDelegates;

import configuracion.Queries;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import modelo.DbManager;

/**
 *
 * @author Galatea
 */
public class InsertarCliente {
    
    
    //Atributos
    private String nit;
    private String razonSocial;
    private String ciiu;
    private String direccion;
    private String barrio;
    private String comuna;
    private String telefono;
    private String telefono2;
    private String usoServicio;
    private String correo;
    private String correo2;
    private String web;
    private String representanteLegal;
    private String estadoUltVertimiento;
    private DbManager db;
    private CallableStatement callableStatement;
    private int resultado;
    
    
    
    /**
     * 
     * Constructor
     * 
     * @param nit
     * @param razonSocial
     * @param ciiu
     * @param ciclo
     * @param sector
     * @param pozo
     * @param direccion
     * @param barrio
     * @param comuna
     * @param telefono
     * @param telefono2
     * @param usoServicio
     * @param correo
     * @param correo2
     * @param web
     * @param representanteLegal
     * @param estadoUltVertimiento 
     */
    public InsertarCliente(String nit, String razonSocial, String ciiu,
            String direccion, String barrio, String comuna, String telefono, String telefono2, String usoServicio, String correo, 
            String correo2, String web, String representanteLegal, String estadoUltVertimiento) {
        
        this.nit = nit;
        this.razonSocial = razonSocial;
        this.ciiu = ciiu;
        this.direccion = direccion;
        this.barrio = barrio;
        this.comuna = comuna;
        this.telefono = telefono;
        this.telefono2 = telefono2;
        this.usoServicio = usoServicio;
        this.correo = correo;
        this.correo2 = correo2;
        this.web = web;
        this.representanteLegal = representanteLegal;
        this.estadoUltVertimiento = estadoUltVertimiento;
        db = new DbManager();
        
    }

    public int getResultado() {
        return resultado;
    }
    
    
    
    
        /**
     *
     * Ejecuta el procedimiento almacenado SpInsertarCliente.
     *
     * @throws Exception
     */
    public void ejecutar() throws Exception{
        
        //Conectamos con la bd.
        Connection conn = db.conectar();
        
        try{

            //Preparamos y ejecutamos el procedimiento.
            callableStatement = conn.prepareCall(Queries
                    .getString("SpInsertarCliente"));

            //Preparamos los parametros
            setParamStringNulo(1, nit);
            setParamStringNulo(2, razonSocial);
            setParamIntegerNulo(3, ciiu);
            setParamStringNulo(4, direccion);
            setParamStringNulo(5, barrio);
            setParamIntegerNulo(6, comuna);
            setParamLongNulo(7, telefono);
            setParamLongNulo(8, telefono2);
            setParamIntegerNulo(9, usoServicio);
            setParamStringNulo(10, correo);
            setParamStringNulo(11, correo2);
            setParamStringNulo(12, web);
            setParamStringNulo(13, representanteLegal);
            setParamStringNulo(14, estadoUltVertimiento);          
            //Obtenemos el paramertro de salida el SP
            callableStatement.registerOutParameter(15, java.sql.Types.INTEGER);
            
            //Ejecutamos el procedimiento
            callableStatement.execute();

            resultado = callableStatement.getInt(15);
            
            //Cerramos la conexion
            db.desconectar(conn);

        }catch(Exception e){

            db.desconectar(conn);
            throw e;

        }

}
    //-----------------------------------------------------------------------------
    
    
    private void setParamIntegerNulo(int index, String numero) throws SQLException{
    
        if(numero.equals("") || numero == null){
            
            callableStatement.setNull(index,Types.NULL); 
            
        }else{
            
            int number = Integer.parseInt(numero);
            callableStatement.setInt(index, number);
        }
        
    }
    
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
