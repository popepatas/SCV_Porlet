package control.ProcesoVertimientosServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.ProcesoVertimientosManagers.*;
import org.json.simple.JSONArray;

@WebServlet(name = "InsertarLodos", urlPatterns = {"/InsertarLodos"})
public class InsertarLodos extends HttpServlet {



    
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
        
        doPost(request,response);
        
    }
    //-----------------------------------------------------------------------------
    
    
    

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
            
           String diasAlMes = request.getParameter("diasAlMes");
           String horasAlDia = request.getParameter("horasAlDia");
           String preTratamiento = request.getParameter("preTratamiento");
           String cualPreTratamiento = request.getParameter("cualPreTratamiento");
           String generacionLodos = request.getParameter("generacionLodos");
           String cualGeneracionLodos = request.getParameter("cualGeneracionLodos");
           String codigoProceso = request.getParameter("codigoProceso");
           String cualOtroGeneracionLodos = request.getParameter("cualOtroGeneracionLodos");
           
           ManejoLodos manager = new ManejoLodos();
           manager.insertarLodos(diasAlMes, horasAlDia, preTratamiento, cualPreTratamiento, generacionLodos, 
                   cualGeneracionLodos, codigoProceso, cualOtroGeneracionLodos);
            
            
        } catch (Exception ex) {
            //Logger.getLogger(InsertarActEconomica.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    //-----------------------------------------------------------------------------


}
