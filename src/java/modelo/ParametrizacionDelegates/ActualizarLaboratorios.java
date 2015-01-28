package modelo.ParametrizacionDelegates;

import configuracion.Queries;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import modelo.DbManager;

public class ActualizarLaboratorios {

    //Atributos
    private String nombres;    
    private String contactos;
    private String direcciones;
    private String telefono;
    private String telefono2;
    private String correo;
    private String resolucion;    
    private String vigencia;    
    private int codigo;
    private CallableStatement callableStatement;
    private DbManager db;

    
    
    
    /**
     * 
     * Constructor
     * 
     * @param nombres     
     * @param contactos
     * @param direcciones
     * @param telefono
     * @param telefono2
     * @param correo
     * @param resolucion     
     * @param vigencia     
     * @param codigo 
     */
    public ActualizarLaboratorios(String nombres, String contactos, String direcciones, 
            String telefono, String telefono2, String correo, String resolucion, 
            String vigencia, int codigo) {
        
        this.nombres = nombres;        
        this.contactos = contactos;
        this.direcciones = direcciones;
        this.telefono = telefono;
        this.telefono2 = telefono2;
        this.correo = correo;
        this.resolucion = resolucion;        
        this.vigencia = vigencia;
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
                    .getString("SpActualizarLaboratorios"));

            //Preparamos los parametros
            callableStatement.setString(1, nombres);            
            callableStatement.setString(2, contactos);
            callableStatement.setString(3, direcciones);
            setParamLongNulo(4, telefono);
            setParamLongNulo(5, telefono2);
            setParamStringNulo(6, correo);
            callableStatement.setString(7, resolucion);            
            callableStatement.setString(8, vigencia);     
            callableStatement.setInt(9, codigo);
            
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
