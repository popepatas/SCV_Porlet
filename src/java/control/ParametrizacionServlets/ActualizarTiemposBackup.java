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
import modelo.ParametrizacionManagers.*;

/**
 *
 * @author illustrato
 */
@WebServlet(name = "ActualizarTiemposBackup", urlPatterns = {"/ActualizarTiemposBackup"})
public class ActualizarTiemposBackup extends HttpServlet {

  

    
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
            
             int valor = Integer.parseInt(request.getParameter("tiempo"));
             int codigo = Integer.parseInt(request.getParameter("codigo"));
             
             TiemposBackup manager = new TiemposBackup();
             
             manager.actualizar(valor, codigo);
            
            
        } catch (Exception e) {
        }
       
    }

}
