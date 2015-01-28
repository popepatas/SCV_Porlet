package control.HelperServlets;

import Extensions.ArchivoExtension;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.ProcesoVertimientosManagers.InformeProcesoSeco;
import modelo.ProcesoVertimientosManagers.ManejoLodos;
import modelo.ProcesoVertimientosManagers.ProgramarMonitoreo;
import modelo.ProcesoVertimientosManagers.Visitas;
import modelo.ReportesManagers.ReportesManager;

/**
 *
 * @author Nadesico
 */
@WebServlet(name = "DescargarArchivoServlet", urlPatterns = {"/DescargarArchivoServlet"})
public class DescargarArchivoServlet extends HttpServlet {

    
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
        doPost(request, response);
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
       
        try {
            
            int opcion = Integer.parseInt(request.getParameter("opcion"));
            obtenerArchivo(opcion, request, response);
            
        } catch (SQLException ex) {
            //Logger.getLogger(DescargarArchivoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void obtenerArchivo(int opcion, HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException{
        Integer codigoArchivo = Integer.parseInt(request.getParameter("codigoArchivo"));
        int codigoProceso;
        Integer codigoHistorialDagma, codigoSupMonitoreo;
        
        switch(opcion){
            case 1:{
                        Visitas manager = new Visitas();
                        ArchivoExtension archivo = manager.getAnexoVisita(codigoArchivo);
                        generarArchivo(archivo.getNombreArchivo(), archivo.getDataArchivo(), request, response);
                        
                   }
            break;
            case 2:{
                      
                        codigoProceso = Integer.parseInt(request.getParameter("codigoProceso"));
                        InformeProcesoSeco manager = new InformeProcesoSeco();
                        ArchivoExtension archivo = manager.getArchivosCargado(codigoProceso,codigoArchivo);
                        generarArchivo(archivo.getNombreArchivo(), archivo.getDataArchivo(), request, response);
                        
                   }
            break;
            case 3:{
                        codigoHistorialDagma = Integer.parseInt(request.getParameter("codigoHistorialDagma"));
                        ReportesManager manager = new ReportesManager();
                        ArchivoExtension archivo = manager.obtenerArchivoHistorialDagma(codigoHistorialDagma, codigoArchivo);
                        generarArchivo(archivo.getNombreArchivo(), archivo.getDataArchivo(), request, response);
                        
            }
            break;
            case 4:{
                        codigoSupMonitoreo = Integer.parseInt(request.getParameter("codigoSupMonitoreo"));
                        ProgramarMonitoreo manager = new ProgramarMonitoreo();
                        ArchivoExtension archivo = manager.getArchivosCargado(codigoArchivo, codigoSupMonitoreo );
                        generarArchivo(archivo.getNombreArchivo(), archivo.getDataArchivo(), request, response);
                        
            }
            break;
            case 5:{
                        ManejoLodos manager = new ManejoLodos();
                        ArchivoExtension archivo = manager.getAnexoLodos(codigoArchivo);
                        generarArchivo(archivo.getNombreArchivo(), archivo.getDataArchivo(), request, response); 
            }
              
        }
        
    }
    
    public void generarArchivo(String nombreArchivo, Blob archivo, HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException{
        
        //Obtenemos la informacion del blob.
        int fileLength = (int)archivo.length();
        ServletContext context = getServletContext();
        // Configuramos el tipo de mime para que el archivo sea descargado
        String mimeType = context.getMimeType(nombreArchivo);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        //Eliminamos el Slash del nombre del archivo
        nombreArchivo = nombreArchivo.replace("\\","");
        //Configuramos las propiedades y la cabecera para la respuesta.
        response.setContentType(mimeType);
        response.setContentLength(fileLength);
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", nombreArchivo);
        response.setHeader(headerKey, headerValue);
        
        // Enviamos la informacion hacia el cliente.
        byte[] buffer = archivo.getBytes(1, fileLength);
        ServletOutputStream outs = response.getOutputStream();
        outs.write(buffer);
        outs.flush();
        outs.close();
    }
}