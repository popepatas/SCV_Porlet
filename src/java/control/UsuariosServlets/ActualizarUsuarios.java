/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control.UsuariosServlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.UsuariosManagers.Usuarios;
import org.json.simple.JSONObject;

/**
 *
 * @author illustrato
 */
@WebServlet(name = "ActualizarUsuarios", urlPatterns = {"/ActualizarUsuarios"})
public class ActualizarUsuarios extends HttpServlet {

    

  
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
         
            Integer respuesta = 0;

            try {        
                String usuario = request.getParameter("descripcion");
                Integer rol =  Integer.parseInt(request.getParameter("rol"));
                Integer codigo = Integer.parseInt(request.getParameter("codigo"));

                Usuarios manager = new Usuarios();            
                respuesta = manager.actualizarUsuarios(usuario, rol,codigo);

                //Armamos la respuesta JSON y la enviamos
                response.setContentType("application/json");

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("error", respuesta);
                response.getWriter().write(jsonObject.toString());

            } catch (Exception ex) {

                //Armamos la respuesta JSON y la enviamos
                response.setContentType("application/json");

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("error", respuesta);
                response.getWriter().write(jsonObject.toString());
            }
    }


}
