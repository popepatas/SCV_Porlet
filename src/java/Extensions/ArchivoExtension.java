/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Extensions;

import java.sql.Blob;


public class ArchivoExtension {
    
    private Blob dataArchivo;
    private String nombreArchivo;

    public ArchivoExtension(Blob dataArchivo, String nombreArchivo) {
        this.dataArchivo = dataArchivo;
        this.nombreArchivo = nombreArchivo;
    }

    public Blob getDataArchivo() {
        return dataArchivo;
    }

    public void setDataArchivo(Blob dataArchivo) {
        this.dataArchivo = dataArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }
    
    
    
}
