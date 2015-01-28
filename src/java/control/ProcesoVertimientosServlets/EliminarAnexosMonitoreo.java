/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control.ProcesoVertimientosServlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.ProcesoVertimientosManagers.ProgramarMonitoreo;
import org.json.simple.JSONObject;

/**
 *
 * @author illustrato
 */
@WebServlet(name = "EliminarAnexosMonitoreo", urlPatterns = {"/EliminarAnexosMonitoreo"})
public class EliminarAnexosMonitoreo extends HttpServlet {

  
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
        JSONObject resp = new JSONObject();
       try {
           Integer codigoArchivo = Integer.parseInt(request.getParameter("codigoArchivo"));
           Integer codigoMonitoreo = Integer.parseInt(request.getParameter("codigoMonitoreo"));
           
           
           ProgramarMonitoreo manager = new ProgramarMonitoreo();
       
           resp =  manager.eliminarAnexos(codigoArchivo, codigoMonitoreo);
           
           response.setContentType("application/json");
           response.getWriter().write(resp.toString());
                
           
            
            
        } catch (Exception ex) {
            resp.put("error", 0);
            response.getWriter().write(resp.toString());
        }
        
        
    }

   
  
}
