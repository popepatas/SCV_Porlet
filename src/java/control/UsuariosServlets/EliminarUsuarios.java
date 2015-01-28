/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control.UsuariosServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.UsuariosManagers.Usuarios;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author illustrato
 */
@WebServlet(name = "EliminarUsuarios", urlPatterns = {"/EliminarUsuarios"})
public class EliminarUsuarios extends HttpServlet {

    
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
      
           
            //Obtenemos los paramtros enviados
            int codigo =  Integer.parseInt(request.getParameter("codigo"));
           
            JSONObject respError = new JSONObject(); // uno significa que no hay error
            
            //Obtenemos La informacion del manager
            Usuarios manager = new Usuarios();            
        try {
            //Almacenamos el error que pueda resultar
            respError =  manager.Eliminar(codigo);
            //Armamos la respuesta JSON y la enviamos
            response.setContentType("application/json");
            response.getWriter().write(respError.toString());
            
        } catch (Exception ex) {
            respError.put("error",0);
             //Armamos la respuesta JSON y la enviamos
            response.setContentType("application/json");
            response.getWriter().write(respError.toString());
        }
            
          
                
            
    }

   
}
