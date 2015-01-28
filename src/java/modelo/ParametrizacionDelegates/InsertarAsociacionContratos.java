package modelo.ParametrizacionDelegates;

import configuracion.Queries;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import modelo.DbManager;

public class InsertarAsociacionContratos {

    //Atributos
    private Double contratoPadre;
    private Double contratoHijo;
    private int error;
    private DbManager db;

    
    /**
     * 
     * Constructor
     * 
     * @param contratoPadre
     * @param contratoHijo 
     */
    public InsertarAsociacionContratos(Double contratoPadre, Double contratoHijo) {
        this.contratoPadre = contratoPadre;
        this.contratoHijo = contratoHijo;
        db = new DbManager();
    }

    public int getError() {
        return error;
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
            
            String query = Queries
                    .getString("SpInsertarAsoc");
                    
            //Preparamos y ejecutamos el procedimiento.
            callableStatement = conn.prepareCall(query);

            //Preparamos los parametros
            callableStatement.setDouble(1, contratoPadre);
            callableStatement.setDouble(2, contratoHijo);

            
            callableStatement.registerOutParameter(3, java.sql.Types.INTEGER);
       

            //Ejecutamos el procedimiento
            callableStatement.execute();
            
            //Obtenemos el mensaje de error
            error = callableStatement.getInt(3);

            //Cerramos la conexion
            db.desconectar(conn);

        }catch(Exception e){

            db.desconectar(conn);
            throw e;

        }

    }
    //-----------------------------------------------------------------------------



}
