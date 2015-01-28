package control.ReportesServlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.ReportesManagers.ReportesManager;
import org.json.simple.JSONArray;

/**
 *
 * @author Marcos
 */
@WebServlet(name = "ConsultarHistorialDagma", urlPatterns = {"/ConsultarHistorialDagma"})
public class ConsultarHistorialDagma extends HttpServlet {

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
       
        int opcion = Integer.parseInt(request.getParameter("opcion"));
        switch(opcion){
            case 1: obtenerHistorial(request, response);
                break;
            case 2: obtenerRegistroDagma(request, response);
                break;
        }
    }
    
    public void obtenerHistorial(HttpServletRequest request, HttpServletResponse response){
        try {
            String codigo = request.getParameter("codigoProceso");
            
            //Obtenemos La informacion del manager
            ReportesManager manager = new ReportesManager();
            JSONArray jsonArray = manager.obtenerHistorialDagma(codigo);

            //Armamos la respuesta JSON y la enviamos
            response.setContentType("application/json");
            for(Object jsonObject : jsonArray){
                response.getWriter().write(jsonObject.toString());
            }
        } catch (Exception ex) {
            //Logger.getLogger(Reporte1Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void obtenerRegistroDagma(HttpServletRequest request, HttpServletResponse response){
        try {
            String codigo = request.getParameter("codigo");

            //Obtenemos La informacion del manager
            ReportesManager manager = new ReportesManager();
            JSONArray jsonArray = manager.obtenerRegistroDagma(codigo);

            //Armamos la respuesta JSON y la enviamos
            response.setContentType("application/json");
            for(Object jsonObject : jsonArray){
             response.getWriter().write(jsonObject.toString());
            }
        } catch (Exception ex) {
        //Logger.getLogger(Reporte1Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
}