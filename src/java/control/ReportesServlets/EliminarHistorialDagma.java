/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control.ReportesServlets;

import control.ProcesoVertimientosServlets.*;
import control.ParametrizacionServlets.*;
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
import modelo.ReportesManagers.ReportesManager;
import org.json.simple.JSONArray;

/**
 *
 * @author illustrato
 */
@WebServlet(name = "EliminarHistorialDagma", urlPatterns = {"/EliminarHistorialDagma"})
public class EliminarHistorialDagma extends HttpServlet {

   
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
            
              int codigo = Integer.parseInt(request.getParameter("codigoHistorial"));
              JSONArray respError = new JSONArray();
              
              ReportesManager manager = new ReportesManager();
        
              respError = manager.eliminarHistorialDagma(codigo);
            
               for(Object jsonObject : respError){
                
                    response.getWriter().write(respError.toString());
               }
               
        } catch (Exception ex) {
           // Logger.getLogger(EliminarLaboratorios.class.getName()).log(Level.SEVERE, null, ex);
        }
               
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
