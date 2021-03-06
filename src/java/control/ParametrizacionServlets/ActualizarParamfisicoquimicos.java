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
import org.json.simple.*;
import modelo.ParametrizacionManagers.*;

/**
 *
 * @author Galatea
 */
@WebServlet(name = "ActualizarParamfisicoquimicos", urlPatterns = {"/ActualizarParamfisicoquimicos"})
public class ActualizarParamfisicoquimicos extends HttpServlet {



    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
    //-----------------------------------------------------------------------------
    
    
    
    
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
            
            //Obtenemos la informacion del form.
            String descripcion = request.getParameter("descripcion");
            int unidades = Integer.parseInt(request.getParameter("unidades"));
            int tipoParametro = Integer.parseInt(request.getParameter("tipoParametro"));
            int codigo = Integer.parseInt(request.getParameter("codigo"));

            ParamFisicoquimicos manager = new ParamFisicoquimicos();
            manager.actualizar(codigo, descripcion, tipoParametro, unidades);
            
        } catch (Exception ex) {
            //Logger.getLogger(ActualizarActEconomica.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    //-----------------------------------------------------------------------------
    
}
