/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control.ProcesoVertimientosServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.ProcesoVertimientosManagers.InformacionGeneral;

/**
 *
 * @author illustrato
 */
@WebServlet(name = "ActualizarInformacionGeneral", urlPatterns = {"/ActualizarInformacionGeneral"})
public class ActualizarInformacionGeneral extends HttpServlet {

    

    
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
        try {   
            String requiereVisita = request.getParameter("requiereVisita");
            String contacto = request.getParameter("contacto");
            String asunto = request.getParameter("asunto");
            String fecha_asesoria = request.getParameter("fechaAsesoria");
            Integer codigoProceso = Integer.parseInt(request.getParameter("codigoProceso"));
            String tipoContacto =request.getParameter("tipoContacto");
            String tipoInforme = request.getParameter("tipoInforme");
            String informaMonitoreo = request.getParameter("informo");
            
            InformacionGeneral manager = new InformacionGeneral();
           
            manager.actualizar(requiereVisita, contacto, asunto, fecha_asesoria, codigoProceso, tipoContacto, tipoInforme, informaMonitoreo);
            
        } catch (Exception ex) {
            Logger.getLogger(ActualizarInformacionGeneral.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }


}
