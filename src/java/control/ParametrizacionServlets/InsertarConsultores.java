/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control.ParametrizacionServlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.ParametrizacionManagers.Consultores;

/**
 *
 * @author illustrato
 */
@WebServlet(name = "InsertarConsultores", urlPatterns = {"/InsertarConsultores"})
public class InsertarConsultores extends HttpServlet {

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
          
        try{
                   
                   String nombre = request.getParameter("nombre");
                   String apellidos = request.getParameter("apellidos");
                   String direccion = request.getParameter("direccion");
                   String telefono =  request.getParameter("telefono1");
                   String telefono2 = request.getParameter("telefono2");
                   String identificacion = request.getParameter("identificacion");
                   String correo = request.getParameter("correo");
                  
                   
                   
                   
                   Consultores manager = new Consultores();
                   manager.insertar(nombre, apellidos, direccion, telefono, telefono2, correo, identificacion );
                   
                   
        }
        catch(Exception e){
            
        }
        
    }

}

  


