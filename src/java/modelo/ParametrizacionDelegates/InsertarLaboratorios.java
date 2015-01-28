package modelo.ParametrizacionDelegates;

import configuracion.Queries;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import modelo.DbManager;

public class InsertarLaboratorios {

    //Atributos
    private String nombres;    
    private String contactos;
    private String direcciones;
    private String telefono;
    private String telefono2;
    private String correo;
    private String resolucion;    
    private String vigencia;    
    private int codLaboratorio;
    private DbManager db;
    private CallableStatement callableStatement;

    
    
    
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
     
     */
    public InsertarLaboratorios(String nombres, String contactos, String direcciones, 
            String telefono, String telefono2, String correo, String resolucion, String vigencia) {
        
        this.nombres = nombres;        
        this.contactos = contactos;
        this.direcciones = direcciones;
        this.telefono = telefono;
        this.telefono2 = telefono2;
        this.correo = correo;
        this.resolucion = resolucion;        
        this.vigencia = vigencia;
        callableStatement = null;
        db = new DbManager();
    }
    

    public int getCodLaboratorio() {
        return codLaboratorio;
    }

    public void setCodLaboratorio(int codLaboratorio) {
        this.codLaboratorio = codLaboratorio;
    }



    /**
     *
     * Ejecuta el procedimiento almacenado SpInsertarLabConsultores.
     *
     * @throws Exception
     */
    public void ejecutar() throws Exception{

        
        
        //Conectamos con la bd.
        Connection conn = db.conectar();
        
        try{

            //Preparamos y ejecutamos el procedimiento.
            callableStatement = conn.prepareCall(Queries
                    .getString("SpInsertarLaboratorios"));

            //Preparamos los parametros
            callableStatement.setString(1, nombres);            
            callableStatement.setString(2, contactos);
            callableStatement.setString(3, direcciones);
            setParamLongNulo(4, telefono);
            setParamLongNulo(5, telefono2);
            callableStatement.setString(6, correo);
            callableStatement.setString(7, resolucion);
            callableStatement.setString(8, vigencia);    
            
            //Obtenemos el paramertro de salida el SP
            callableStatement.registerOutParameter(9, java.sql.Types.INTEGER);
            
            //Ejecutamos el procedimiento
            callableStatement.execute();
            
            codLaboratorio = callableStatement.getInt(9);
            
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


}
