/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control.ProcesoVertimientosServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "SeleccionarTasaRetributiva", urlPatterns = {"/SeleccionarTasaRetributiva"})
public class SeleccionarTasaRetributiva extends HttpServlet {

    
  
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
        
            
        JSONObject jsonObject1 = new JSONObject();
        try {
            int  codigoProceso = Integer.parseInt(request.getParameter("codigoProceso"));        
            ProgramarMonitoreo manager = new ProgramarMonitoreo();      
            jsonObject1 = manager.getTasaRetributiva(codigoProceso);
            
              //Armamos la respuesta JSON y la enviamos
            response.setContentType("application/json");
            response.getWriter().write(jsonObject1.toString());
            
        } catch (SQLException ex) {
            
        }
        
    }

}
