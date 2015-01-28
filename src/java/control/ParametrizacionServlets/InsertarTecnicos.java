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
import modelo.ApiManager;
import modelo.ParametrizacionManagers.Tecnicos;

/**
 *
 * @author illustrato
 */
@WebServlet(name = "InsertarTecnicos", urlPatterns = {"/InsertarTecnicos"})
public class InsertarTecnicos extends HttpServlet {

   

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
            
         try {  
              int     tipoPersona = Integer.parseInt(request.getParameter("tipoPersona"));
              String  nombres = request.getParameter("nombres");
              String  apellidos = ApiManager.quitaNull(request.getParameter("apellidos"));
              int     tipoDocumento = Integer.parseInt(request.getParameter("tipoDocumento"));
              String  documento = request.getParameter("documento");
              int  estado = Integer.parseInt(request.getParameter("estado"));
       
                Tecnicos manager = new Tecnicos();
       
              manager.insertar(nombres, apellidos, tipoDocumento, documento, estado, tipoPersona);
            
        } catch (Exception ex) {
           
        }
        
    }



}
