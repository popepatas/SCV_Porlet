/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control.HelperServlets;




import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.BaseFont;
import com.lowagie.text.DocumentException;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.PDFManagers.PDFManager;
import static oracle.net.aso.C01.i;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;




/**
 *
 * @author Nadesico
 */
@WebServlet(name = "GenerarPdfServlet", urlPatterns = {"/GenerarPdfServlet"})
public class GenerarPdfServlet extends HttpServlet {



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
 
                generarPDF(request, response);
            
            
        } catch (Exception ex) {
       
        }
        
    }
    
    /**
     * 
     * Toma la opcion y determina que reporte PDF se debe generar.
     * 
     * @param request
     * @param response
     * @throws IOException
     * @throws DocumentException 
     */
    public void generarPDF(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException{
        try{
            int opcion = Integer.parseInt(request.getParameter("opcion"));

            switch(opcion){

                case 1:{    /*Generamos y enviamos el reporte PDF de notificacion*/
                    
                            //Obtenemos los clientes
                            String cliente = request.getParameter("clientes");
                            cliente = cliente.replace("checkAll,","");
                            cliente = cliente.replace("{","");
                            cliente = cliente.replace("}","");
                            String[] clients = cliente.split(",");
                            
                            //Configuramos los nombres y las rutas de los archivos de entrada y salida
                            String archivoPDF = "notificacion.pdf";
                            String archivoHTML = "anexo4.html";
                            // MODIFICAR PDF SGC
                            String rutaPDF = getServletContext().getRealPath("/") + "\\pdf\\" + archivoPDF;
                            String rutaHTML = getServletContext().getRealPath("/") + "\\pdf\\" + archivoHTML;
                            
                            System.out.println("ruta PDF  = "+rutaPDF);
                            System.out.println("ruta HTML  = "+rutaHTML);
                                    

                            //Convertimos el HTML en String y lo manipulamos
                            String html = "";
                            for(int i = 0; i < clients.length; i++){
                                String htmlToPdf = fileToString(rutaHTML);
                                PDFManager manager = new PDFManager();        
                                html += manager.pdfNotificacion(htmlToPdf,clients[i]);  
                            }
                            
                            String cabecera = "<div style=\"text-align:justify; font-family:Arial, Helvetica, sans-serif; font-size:14px;\">";
                            String footer = "</div>";
              
                            String htmlFinal = cabecera+html+footer;
                            
                            //Escribimos el pdf
                            escribirPDF(htmlFinal, rutaPDF);
                            
                       }
                break;
                case 2:{
                            /*Generamos y enviamos el reporte PDF de incumplimiento*/
                    
                            //Obtenemos el codigo del proceso
                            int codigo = Integer.parseInt(request.getParameter("codigoProceso"));
                            
                            String archivoPDF = "incumplimiento.pdf";
                            String archivoHTML = "anexo5.html";
                            String rutaPDF = getServletContext().getRealPath("/") + "sources\\pdf\\" + archivoPDF;
                            String rutaHTML = getServletContext().getRealPath("/") + "pdf\\" + archivoHTML;
                            
                            String htmlToPdf = fileToString(rutaHTML);
                            PDFManager manager = new PDFManager();
                            String html = manager.getPdfIncumplimiento(codigo, htmlToPdf);
                            
                            escribirPDF(html, rutaPDF);
                            enviarPDF(rutaPDF, archivoPDF, request, response);
                       }
                break;
                case 3:{    
                            /*Generamos y enviamos el reporte PDF de devolucion*/
                    
                            //Obtenemos el codigo del proceso
                            int codigo = Integer.parseInt(request.getParameter("codigoProceso"));

                            //Configuramos los nombres y las rutas de los archivos de entrada y salida
                            String archivoPDF = "devolucion.pdf";
                            String archivoHTML = "anexo3.html";
                            String rutaPDF = getServletContext().getRealPath("/") + "sources\\pdf\\" + archivoPDF;
                            String rutaHTML = getServletContext().getRealPath("/") + "pdf\\" + archivoHTML;

                            //Convertimos el HTML en String y lo manipulamos
                            String htmlToPdf = fileToString(rutaHTML);
                            PDFManager manager = new PDFManager();        
                            String html = manager.pdfDevolucion(codigo, htmlToPdf);

                            //Generamos el pdf
                            escribirPDF(html, rutaPDF);
                            enviarPDF(rutaPDF, archivoPDF, request, response);
                       }
                break;
                case 4:{    
                            /*Envía el pdf generado de notificacion*/
                            String archivoPDF = "notificacion.pdf";
                            String rutaPDF = getServletContext().getRealPath("/") + "\\pdf\\" + archivoPDF;
                            enviarPDF(rutaPDF, archivoPDF, request, response);
                       }
            }
        }catch(Exception e){
        }
    }
    
    /**
     * 
     * Convierte codigo html en archivo pdf
     * 
     * @param html
     * @param ruta
     * @throws IOException 
     */
    public void escribirPDF(String archivoHTML, String archivoPDF) throws IOException, DocumentException{
        try{
            //Tomamos el html del JSP y lo convertimos a pdf.
            String inputFile = archivoHTML;
            //String url = new File(inputFile).toURI().toURL().toString();
            String outputFile = archivoPDF;
            OutputStream os = new FileOutputStream(outputFile);
            
            ITextRenderer renderer = new ITextRenderer();
            //renderer.getFontResolver().addFont("C:\\windows\\fonts\ARIALNI.TTF", "UTF-8", BaseFont.EMBEDDED);
            renderer.setDocumentFromString(inputFile);
            renderer.layout();
            renderer.createPDF(os);

            os.close();
        }catch(Exception ex){
            String mensaje = ex.getMessage();
            System.out.println(mensaje);
        }
        
        
        
    }
    
    
    /**
     * 
     * Envia el archivo pdf a la vista para que sea descargado.
     * 
     * @param ruta
     * @param nombreArchivo
     * @param request
     * @param response
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void enviarPDF(String ruta, String nombreArchivo, HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException, IOException{
        
        //Obtenemos el archivo
        File pdfFile = new File(ruta);

        //Enviamos el pdf a la vista para que sea descargado.
        response.setContentType("application/pdf");
        response.addHeader("Content-Disposition", "attachment; filename=" + nombreArchivo);
        response.setContentLength((int) pdfFile.length());

        FileInputStream fileInputStream = new FileInputStream(pdfFile);
        OutputStream responseOutputStream = response.getOutputStream();
        int bytes;
        while ((bytes = fileInputStream.read()) != -1) {
                responseOutputStream.write(bytes);
        }
    }
    
    public static String fileToString(String filePath) {
      return fileToString(new File(filePath));
    }

    public static String fileToString(File file) {

      byte [] fileBytes = new byte[0];

        try {
            byte [] buffer = new byte[4096];
            ByteArrayOutputStream outs = new ByteArrayOutputStream();
            InputStream ins = new FileInputStream(file);

            int read = 0;
            while ((read = ins.read(buffer)) != -1 ) {
              outs.write(buffer, 0, read);
            }

            ins.close();
            outs.close();
            fileBytes = outs.toByteArray();

        } catch (Exception e) { 
          e.printStackTrace();
        }

        return new String(fileBytes);
    }
    
}
