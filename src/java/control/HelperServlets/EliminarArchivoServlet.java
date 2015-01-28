/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control.HelperServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.ProcesoVertimientosManagers.Visitas;

/**
 *
 * @author Nadesico
 */
@WebServlet(name = "EliminarArchivoServlet", urlPatterns = {"/EliminarArchivoServlet"})
public class EliminarArchivoServlet extends HttpServlet {

    
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


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        try {
            
            int opcion = Integer.parseInt(request.getParameter("opcion"));
            eliminarArchivo(opcion, request, response);
            
        } catch (Exception ex) {
            Logger.getLogger(EliminarArchivoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    public void eliminarArchivo(int opcion, HttpServletRequest request, HttpServletResponse response) throws Exception{
        
        int codigoArchivo = Integer.parseInt(request.getParameter("codigoArchivo"));
        
        switch(opcion){
            case 1:{
                        Visitas manager = new Visitas();
                        manager.eliminarAnexoVisita(codigoArchivo);
                   }
                   break;
            }
    }
        
}
