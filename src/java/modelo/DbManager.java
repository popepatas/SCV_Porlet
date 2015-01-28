package modelo;

import configuracion.Conexion;
import java.sql.*;


/**
 *
 * Administra todas las transacciones con la bd.
 *
 */
public class DbManager {

    
    /**
     *
     * Obtiene la informacion de conexion, y se conecta a la BD.
     *
     * @return conn
     */
    public Connection conectar(){

        try{
            
            
            //Obtenemos la informacion de conexion
            String DB_SERVER = Conexion.getString("DB_SERVER");
            String DB_USER = Conexion.getString("DB_USER");
            String DB_PASSWORD = Conexion.getString("DB_PASSWORD");
            String DB_SID = Conexion.getString("DB_SID");
            String DB_PORT = Conexion.getString("DB_PORT");

            //Creamos cadena de conexion
            String cadenaConexion = "jdbc:oracle:thin:@"+ DB_SERVER +":"+ DB_PORT +":"+ DB_SID;

            //Creamos la conexion
            DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
            Connection conn = DriverManager.getConnection(cadenaConexion, DB_USER, DB_PASSWORD);

            return conn;

        }catch(Exception e){

            return null;
        }
        
    }
    //-----------------------------------------------------------------------------





    /**
     *
     * Recibe la conexion y ejecuta el query, retornando el ResultSet
     * o null en caso de error.
     *
     * @param conn
     * @param query
     * @return rset
     */
    public ResultSet ejecutar(Connection conn, String query){

        try{

            //Creamos el Statement
            Statement stmt = conn.createStatement();


            //Validamos que el Statement no este vacio
            if(stmt != null){

               
                //ejecutamos la sentencia
                ResultSet rset = stmt.executeQuery(query);
                return rset;
            }

            return null;

        }catch(Exception e){
            return null;
        }
    }
    //-----------------------------------------------------------------------------





    /**
     *
     * Cierra la conexion con la bd.
     *
     * @param conn
     * @return
     */
    public boolean desconectar(Connection conn){

        try{

            //Cerramos la conexion con la bd.
            conn.close();
            return true;
            
        }catch(Exception e){

            return false;

        }

    }
    //-----------------------------------------------------------------------------
    
}
