package modelo.ProcesoVertimientosDelegates;

import configuracion.Queries;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Types;
import modelo.DbManager;

public class InsertarResultadoVisita {

    //Atributos
    private int tecnico;
    private int codigo;
    private String resultado;
    private String chkResultado;
    private DbManager db;

    
    
    /**
     * 
     * Constructor
     * 
     * @param tecnico
     * @param codigo
     * @param resultado 
     */
    public InsertarResultadoVisita(int tecnico, int codigo, String resultado, String chkResultado) {
        this.tecnico = tecnico;
        this.codigo = codigo;
        this.resultado = resultado;
        this.chkResultado = chkResultado;
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
                    .getString("SpInsertarResultadoVisita"));

            //Preparamos los parametros
            callableStatement.setInt(1, tecnico);
            callableStatement.setString(2, resultado);
            callableStatement.setInt(3, codigo);    
            callableStatement.setString(4,chkResultado);
            
            //Ejecutamos el procedimiento
            callableStatement.execute();
            
            //Cerramos la conexion
            db.desconectar(conn);

        }catch(Exception e){

            db.desconectar(conn);
            throw e;

        }

    }
    //-----------------------------------------------------------------------------



}
