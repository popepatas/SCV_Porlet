/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control.AutenticacionServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.AutenticacionManager.PermisosAcceso;
import org.json.simple.JSONObject;

/**
 *
 * @author illustrato
 */
@WebServlet(name = "InsertarPermisos", urlPatterns = {"/InsertarPermisos"})
public class InsertarPermisos extends HttpServlet {

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
          
        JSONObject jsonObject = new JSONObject();
        response.setContentType("application/json");
        
        try {
            
           int codigo_rol = Integer.parseInt(request.getParameter("rol"));
           int codigo_pantalla =  Integer.parseInt(request.getParameter("pantalla"));
           String valor = request.getParameter("valor");
          
           PermisosAcceso manager = new PermisosAcceso();
                  
           jsonObject = manager.insertarPermisos(codigo_rol, codigo_pantalla, valor);          
          
           response.getWriter().write(jsonObject.toString());
          
        } catch (Exception ex) {
            
            jsonObject.put("error", 0);
            jsonObject.put("exception",ex.toString());
           
            response.getWriter().write(jsonObject.toString());
        }
          
        
    }

   

}
