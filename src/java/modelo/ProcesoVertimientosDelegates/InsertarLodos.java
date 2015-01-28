package modelo.ProcesoVertimientosDelegates;

import configuracion.Queries;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;
import modelo.DbManager;

public class InsertarLodos {

    //Atributos
    private String diasAlMes;
    private String horasAlDia;
    private String preTratamiento;
    private String cualPreTratamiento;
    private String generacionLodos;
    private String cualGeneracionLodos;
    private String codigoProceso;
    private String cualOtroGeneracionLodos;
    private CallableStatement callableStatement;
    private DbManager db;

    public InsertarLodos(String diasAlMes, String horasAlDia, String preTratamiento, String cualPreTratamiento, String generacionLodos, String cualGeneracionLodos, String codigoProceso, String cualOtroGeneracionLodos) {
        this.diasAlMes = diasAlMes;
        this.horasAlDia = horasAlDia;
        this.preTratamiento = preTratamiento;
        this.cualPreTratamiento = cualPreTratamiento;
        this.generacionLodos = generacionLodos;
        this.cualGeneracionLodos = cualGeneracionLodos;
        this.codigoProceso = codigoProceso;
        this.cualOtroGeneracionLodos = cualOtroGeneracionLodos;
        db = new DbManager();
    }
    
    
    /**
     *
     * Ejecuta el procedimiento almacenado SpInsertarUnidadesMedida.
     *
     * @throws Exception
     */
    public void ejecutar() throws Exception{

        
        //Conectamos con la bd.
        Connection conn = db.conectar();
        
        try{

            //Preparamos y ejecutamos el procedimiento.
            callableStatement = conn.prepareCall(Queries
                    .getString("SpInsertarLodos"));

            //Preparamos los parametros
            
            setParamIntegerNulo(1, diasAlMes);
            setParamIntegerNulo(2, horasAlDia);
            setParamStringNulo(3, preTratamiento);
            setParamStringNulo(4, cualPreTratamiento);
            setParamStringNulo(5, generacionLodos);
            setParamStringNulo(6, cualGeneracionLodos);
            setParamIntegerNulo(7, codigoProceso);
            setParamStringNulo(8, cualOtroGeneracionLodos);
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


    private void setParamStringNulo(int index, String param) throws SQLException{
    
        if(param == ""){
            
            callableStatement.setNull(index,Types.NULL); 
            
        }else{
            
            callableStatement.setString(index, param);
        }
        
    }
    
      private void setParamIntegerNulo(int index, String numero) throws SQLException{
    
        if(numero.equals("") || numero == null){
            
            callableStatement.setNull(index,Types.NULL); 
            
        }else{
            
            int number = Integer.parseInt(numero);
            callableStatement.setInt(index, number);
        }
        
    }
}
