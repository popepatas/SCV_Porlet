/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control.ParametrizacionServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.ParametrizacionManagers.ActParamFisicoquimicos;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author illustrato
 */
@WebServlet(name = "EliminarActParamfisicoquimicos", urlPatterns = {"/EliminarActParamfisicoquimicos"})
public class EliminarActParamfisicoquimicos extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
  
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
         JSONObject respError = new JSONObject(); // uno significa que no hay error
        try {
            int codigoAct =  Integer.parseInt(request.getParameter("actividad"));
            int codigoParam =  Integer.parseInt(request.getParameter("parametro"));
        
            //Obtenemos La informacion del manager
            ActParamFisicoquimicos manager = new ActParamFisicoquimicos();            
            //Almacenamos el error que pueda resultar
            respError =  manager.Eliminar(codigoAct, codigoParam);
            
            //Armamos la respuesta JSON y la enviamos
            response.setContentType("application/json");
            response.getWriter().write(respError.toString());
                
            
        } catch (Exception ex) {
            respError.put("error",0);
            response.setContentType("application/json");
            response.getWriter().write(respError.toString());
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
