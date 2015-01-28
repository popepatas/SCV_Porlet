package modelo.ProcesoVertimientosDelegates;

import configuracion.Queries;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Types;
import modelo.DbManager;
import modelo.ParametrizacionManagers.TipoVisita;

public class InsertarVisita {

    //Atributos
    private int tecnico;
    private int motivo;
    private String observacion;
    private String fechaVisita;
    private int proceso;
    private int resultado;
    private int tipoVisita;
    private DbManager db;
    
    /**
     * 
     *@param tecnico
     * @param motivo
     * @param observacion
     * @param fechaVisita
     * @param proceso
     * @param tipoVisita
     * @return 
     * @throws java.lang.Exception 
     * 
     */

    public InsertarVisita(int tecnico, int motivo, String observacion, String fechaVisita, int proceso, int tipoVisita) {
        this.tecnico = tecnico;
        this.motivo = motivo;
        this.observacion = observacion;
        this.fechaVisita = fechaVisita;
        this.proceso = proceso;
        this.tipoVisita = tipoVisita;
        db = new DbManager();
    }

    public int getResultado() {
        return resultado;
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
                    .getString("SpInsertarVisita"));

            //Preparamos los parametros
            callableStatement.setInt(1, tecnico);
            callableStatement.setInt(2, motivo);    
            callableStatement.setString(3, observacion);
            callableStatement.setString(4, fechaVisita);
            callableStatement.setInt(5, proceso);
            callableStatement.setInt(6, tipoVisita);    
            callableStatement.registerOutParameter(7, Types.INTEGER);
            
            //Ejecutamos el procedimiento
            callableStatement.execute();

            //Obtenemos el resultado
            this.resultado = callableStatement.getInt(7);
            
            //Cerramos la conexion
            db.desconectar(conn);

        }catch(Exception e){

            db.desconectar(conn);
            throw e;

        }

    }
    //-----------------------------------------------------------------------------



}
