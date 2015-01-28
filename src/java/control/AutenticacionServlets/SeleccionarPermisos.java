/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control.AutenticacionServlets;

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
import modelo.AutenticacionManager.PermisosAcceso;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author illustrato
 */
@WebServlet(name = "SeleccionarPermisos", urlPatterns = {"/SeleccionarPermisos"})
public class SeleccionarPermisos extends HttpServlet {

   
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
          
        JSONArray resp = new JSONArray();
         JSONArray resp1 = new JSONArray();
                
       try {
           
            String rol = request.getParameter("rol");
         
        
            PermisosAcceso manager = new PermisosAcceso();    
            resp = manager.SeleccionarPermisos(rol);
            resp1.add(resp);     
            
            //Armamos la respuesta JSON y la enviamos
            response.setContentType("application/json");
            for(Object jsonObject : resp1){

               response.getWriter().write(jsonObject.toString());

            }

        } catch (SQLException ex) {
           JSONObject jsonObject = new JSONObject();
            jsonObject.put("error", 0);
        }
        
        
    }


}
