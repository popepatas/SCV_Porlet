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
import modelo.ParametrizacionManagers.Tarifas;

/**
 *
 * @author illustrato
 */
@WebServlet(name = "ActualizarTarifas", urlPatterns = {"/ActualizarTarifas"})
public class ActualizarTarifas extends HttpServlet {

  

    
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
            
             int valorDbo = Integer.parseInt(request.getParameter("dbo"));
             int valorSst = Integer.parseInt(request.getParameter("sst"));
             
             Tarifas manager = new Tarifas();
             
             manager.actualizar(valorDbo, 1);
             manager.actualizar(valorSst, 2);
            
            
            
        } catch (Exception e) {
        }
       
    }

}
