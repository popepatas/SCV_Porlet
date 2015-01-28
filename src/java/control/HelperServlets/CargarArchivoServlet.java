/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control.HelperServlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import configuracion.*;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ProcesoVertimientosManagers.InformeProcesoSeco;
import modelo.ProcesoVertimientosManagers.ManejoLodos;
import modelo.ProcesoVertimientosManagers.ProgramarMonitoreo;
import modelo.ProcesoVertimientosManagers.Visitas;
import modelo.ReportesManagers.ReportesManager;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Nadesico
 */
@WebServlet(name = "CargarArchivoServlet", urlPatterns = {"/CargarArchivoServlet"})
public class CargarArchivoServlet extends HttpServlet {

    private String ruta;
    private String nombreArchivo;
    private File archivo;
    private int codigo;
    private int opcion;
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        //Obtenemos la opcion que esta
        opcion = Integer.parseInt(request.getParameter("opcion"));
        codigo = Integer.parseInt(request.getParameter("codigo"));
        
        //Subimos el archivo al servidor
        //Para poder ser guardado en la bd.
        subirArchivo(request);
        
        //Creamos una referencia al archivo
        archivo = new File(ruta);
        try {
            //Guardamos el archivo en la BD.
            guardarArchivo();
            
            //Borramos el archivo del servidor
            archivo.delete();
            
        } catch (Exception ex) {
            //Logger.getLogger(CargarArchivoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
    
    
    
    
    //Se selecciona la ruta en la que se cargarán temporalmente los archivos
    private String getRutaCarga(){
    
        String ruta = "";
        
        switch(opcion){
        
            case 1: { ruta = Sources.getString("anexosVisita"); }
                break;
            case 2: { ruta = Sources.getString("anexosInformacionTecnica"); }
                break;
            case 3: { ruta = Sources.getString("anexosInformeProcesoSeco");}
                break;
            case 4: { ruta = Sources.getString("anexosHistorialDagma"); }
                break;
            case 5: { ruta = Sources.getString("anexosSupervisionMonitoreo"); }
                break;    
        }
        
        return ruta;
    }
    
    
    public void guardarArchivo() throws Exception{
    
        switch(opcion){
        
            case 1: {
                        Visitas manager = new Visitas();
                        manager.insertarAnexoVisita(codigo, archivo, nombreArchivo);
                    }
                    break;
            case 2: {
                        ManejoLodos manager = new ManejoLodos();
                        manager.insertarAnexoLodos(codigo, archivo, nombreArchivo);
                    }
                break;
                
             case 3: {
                        InformeProcesoSeco manager = new InformeProcesoSeco();
                        manager.insertarAnexos(codigo, archivo, nombreArchivo);
                    }
                    break;
             case 4:
             {
                 ReportesManager manager = new ReportesManager();
                 manager.insertarAnexoDagma(codigo, archivo, nombreArchivo);
             } break;
              case 5:
             {
                 ProgramarMonitoreo manager = new ProgramarMonitoreo();
                 manager.insertarAnexos(codigo, archivo, nombreArchivo);
             } 
        }
        
    }    

    private void subirArchivo(HttpServletRequest request){
            
        //Armamos la ruta donde se subiran los archivos
        String folder = getRutaCarga();
        String rutaServlet = getServletContext().getRealPath("/");
        String rutaCompleta = rutaServlet + folder;
        String rutaConArchivo = "";
        
        //Se procesa solo si es multipart y sube el archivo al directorio especificado.
        if(ServletFileUpload.isMultipartContent(request)){

            try {
                
                DiskFileItemFactory itemFactory = new DiskFileItemFactory();
                ServletFileUpload serFileUpload = new ServletFileUpload(itemFactory);
                
                
                List multiparts = serFileUpload.parseRequest(request);

                for(Object reg : multiparts){

                    FileItem item = (FileItem)reg;
                    
                    if(!item.isFormField()){
                        
                        String name = new File(item.getName()).getName();

                        item.write( new File(rutaCompleta + File.separator + name));
                        
                        this.ruta = rutaCompleta + File.separator + name;
                        this.nombreArchivo = File.separator + name;
                    }

                }

                
            } catch (Exception ex) {
               
            }         

        }
        
    }
    

}
