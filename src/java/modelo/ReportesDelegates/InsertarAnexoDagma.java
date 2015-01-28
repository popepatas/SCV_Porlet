package modelo.ReportesDelegates;

import modelo.ProcesoVertimientosDelegates.*;
import configuracion.Queries;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Types;
import modelo.DbManager;

public class InsertarAnexoDagma {

    //Atributos
    private int codigoHist;
    private FileInputStream archivoBinario;
    private String nombreArchivo; 
    private File archivo;
    private DbManager db;

    
    
    
    public InsertarAnexoDagma(int codigoHist, File archivo, String nombreArchivo) throws FileNotFoundException {
        
        this.codigoHist = codigoHist;
        this.nombreArchivo = nombreArchivo;
        this.archivo = archivo;
        
        //Recibimos el file y convertimos el archivo a binario.
        this.archivoBinario = new FileInputStream(archivo);
        
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
                    .getString("SpInsertarAnexoDagma"));

            //Preparamos los parametros
            callableStatement.setInt(1, codigoHist);
            callableStatement.setString(2, nombreArchivo);  
            callableStatement.setBinaryStream(3, archivoBinario, (int)archivo.length());
              
            
            
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
