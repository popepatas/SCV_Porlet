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
@WebServlet(name = "InsertarResultadoSupervision", urlPatterns = {"/InsertarResultadoSupervision"})
public class InsertarResultadoSupervision extends HttpServlet {
    
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
          JSONObject jsonObjectResp = new JSONObject();
        try {
            
            int tecnico  = Integer.parseInt(request.getParameter("tecnico"));
            int codigo   = Integer.parseInt(request.getParameter("codigo"));
            String observacion  = request.getParameter("observacion");
            String estuvo  = request.getParameter("estuvo");
            int resul;
            
            ProgramarMonitoreo manager = new ProgramarMonitoreo();
            
            resul = manager.insertarResultadoSupervision(codigo, tecnico, observacion, estuvo);
            
                        
            //Armamos la respuesta JSON y la enviamos
            response.setContentType("application/json");
            jsonObjectResp.put("error",resul);
            response.getWriter().write(jsonObjectResp.toString());
            
          
            
        } catch (Exception e) {
              //Armamos la respuesta JSON y la enviamos
                response.setContentType("application/json");
                jsonObjectResp.put("error",0);
                response.getWriter().write(jsonObjectResp.toString());
            
        }
  
            
       
    }

  

}
