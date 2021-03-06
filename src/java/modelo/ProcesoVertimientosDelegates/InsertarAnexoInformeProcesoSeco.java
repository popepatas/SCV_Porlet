/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.ProcesoVertimientosDelegates;
import configuracion.Queries;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Types;
import modelo.DbManager;


/**
 *
 * @author illustrato
 */
public class InsertarAnexoInformeProcesoSeco {
   
    //Atributos
    private int codigoProceso;
    private FileInputStream archivoBinario;
    private String nombreArchivo; 
    private File archivo;
    private DbManager db;    
    
    
    public InsertarAnexoInformeProcesoSeco(int codigoProceso, File archivo, String nombreArchivo) throws FileNotFoundException {
        
        this.codigoProceso = codigoProceso;
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
                    .getString("InsertarArchivoInformes"));

            //Preparamos los parametros
            callableStatement.setInt(1, codigoProceso);            
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


