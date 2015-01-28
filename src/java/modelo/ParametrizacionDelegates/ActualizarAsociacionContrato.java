package modelo.ParametrizacionDelegates;

import configuracion.Queries;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import modelo.DbManager;

public class ActualizarAsociacionContrato {

    //Atributos
    private Double contratoPadre;
    private Double contratoHijo;
    private String codigo;
    private int error;
    private DbManager db;
    private CallableStatement callableStatement;

    
    /**
     * 
     * Constructor
     * 
     * @param contratoPadre
     * @param contratoHijo
     * @param codigo
     * @param error 
     */
    public ActualizarAsociacionContrato(Double contratoPadre, Double contratoHijo, String codigo) {
        this.contratoPadre = contratoPadre;
        this.contratoHijo = contratoHijo;
        this.codigo = codigo;
        db = new DbManager();
    }


    public int getError() {
        return error;
    }
    

    /**
     *
     * Ejecuta el procedimiento almacenado SpActualizarUnidadesMedida.
     *
     * @throws Exception
     */
    public void ejecutar() throws Exception{

        
        //Conectamos con la bd.
        Connection conn = db.conectar();

        try{

            //Preparamos y ejecutamos el procedimiento.
            callableStatement = conn.prepareCall(Queries
                    .getString("SpActualizarAsoc"));

            //Preparamos los parametros

            callableStatement.setDouble(1, contratoPadre);
            callableStatement.setDouble(2, contratoHijo);
            setParamIntegerNulo(3, codigo);
            
            callableStatement.registerOutParameter(4, java.sql.Types.INTEGER);
            
            //Ejecutamos el procedimiento
            callableStatement.execute();

            error = callableStatement.getInt(4);
                
            //Cerramos la conexion
            db.desconectar(conn);

        }catch(Exception e){

            db.desconectar(conn);
            throw e;

        }

    }


    private void setParamIntegerNulo(int index, String numero) throws SQLException{
    
        if(numero == null){
            
            callableStatement.setNull(index,Types.NULL); 
            
        }else{
            
            int number = Integer.parseInt(numero);
            callableStatement.setInt(index, number);
        }
        
    }



}
