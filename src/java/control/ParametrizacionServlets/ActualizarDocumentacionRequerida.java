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
import modelo.ParametrizacionManagers.DocumentacionRequerida;

/**
 *
 * @author illustrato
 */
@WebServlet(name = "ActualizarDocumentacionRequerida", urlPatterns = {"/ActualizarDocumentacionRequerida"})
public class ActualizarDocumentacionRequerida extends HttpServlet {

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
            //Obtenemos los datos enviados.
                try {    
                   String descripcion = request.getParameter("descripcion");
                   Integer tipoInforme = Integer.parseInt(request.getParameter("tipoInforme"));
                   Integer codigo = Integer.parseInt(request.getParameter("codigo"));

                   //Creamos el manager para registrar la informacion
                   DocumentacionRequerida manager = new DocumentacionRequerida();

                   manager.actualizar(codigo,descripcion, tipoInforme);
                   
               } catch (Exception ex) {
                  // Logger.getLogger(ActualizarDocumentacionRequerida.class.getName()).log(Level.SEVERE, null, ex);
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
