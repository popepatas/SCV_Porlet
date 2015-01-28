package modelo.ParametrizacionDelegates;

import configuracion.Queries;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import modelo.DbManager;

public class ActualizarActParamfisicoquimicos {

    //Atributos
    private int actividadEconomica;
    private int paramFisicoquimicos;
    private int codigo;
    private int error;
    private Double rangoInicial;
    private Double rangoFinal;
    private String mayorIgualRangoInicial;
    private String mayorIgualRangoFinal;
    private String mostrarRango;
    private DbManager db;
    private CallableStatement callableStatement;

    
    
    
    /**
     * 
     * Constructor
     * 
     * @param codigo
     * @param actividadEconomica
     * @param paramFisicoquimicos
     * @param rangoInicial
     * @param rangoFinal
     * @param mayorIgualRangoInicial
     * @param mayorIgualRangoFinal 
     */
    public ActualizarActParamfisicoquimicos(int codigo,int actividadEconomica, int paramFisicoquimicos, Double rangoInicial, 
                                            Double rangoFinal, String mayorIgualRangoInicial, String mayorIgualRangoFinal, String mostrarRango) {
        this.codigo = codigo;
        this.actividadEconomica = actividadEconomica;
        this.paramFisicoquimicos = paramFisicoquimicos;
        this.rangoInicial = rangoInicial;
        this.rangoFinal = rangoFinal;
        this.mayorIgualRangoInicial = mayorIgualRangoInicial;
        this.mayorIgualRangoFinal = mayorIgualRangoFinal;
        this.mostrarRango =  mostrarRango;
        db = new DbManager();
        
    }
  public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }


    

    /**
     *
     * Ejecuta el procedimiento almacenado SpActualizarActParamfisicoquimicos.
     *
     * @throws Exception
     */
    public void ejecutar() throws Exception{

        
        
        //Conectamos con la bd.
        Connection conn = db.conectar();
        
        try{

            //Preparamos y ejecutamos el procedimiento.
            callableStatement = conn.prepareCall(Queries
                    .getString("SpActualizarActParamfisicoquimicos"));

            //Preparamos los parametros
            callableStatement.setInt(1, actividadEconomica);
            callableStatement.setInt(2, paramFisicoquimicos);
            callableStatement.setDouble(3, rangoInicial);
            callableStatement.setDouble(4, rangoFinal);
            callableStatement.setString(5, mayorIgualRangoInicial);
            callableStatement.setString(6, mayorIgualRangoFinal);
            callableStatement.setInt(7, codigo);
            setParamIntNulo(8, mostrarRango);

           
             //Obtenemos el paramertro de salida el SP
            callableStatement.registerOutParameter(9, java.sql.Types.NUMERIC);

            //Ejecutamos el procedimiento
            callableStatement.execute();
            
            //Almacenamos el paramertro de salida en la variable error          
            error =  callableStatement.getInt(9);

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
    
    
      private void setParamIntNulo(int index, String numero) throws SQLException{
    
        if("".equals(numero)){
            
            callableStatement.setNull(index,Types.NULL); 
            
        }else{
            
            int number = Integer.parseInt(numero);
            callableStatement.setInt(index, number);
        }
        
    }
    //-----------------------------------------------------------------------------



}
