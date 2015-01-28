/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control.ReportesServlets;

import configuracion.Sources;
import control.ParametrizacionServlets.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.ParametrizacionManagers.ActParamFisicoquimicos;
import modelo.ReportesManagers.ReportesManager;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author illustrato
 */
@WebServlet(name = "InsertarHistorialDagma", urlPatterns = {"/InsertarHistorialDagma"})
public class InsertarHistorialDagma extends HttpServlet {
  
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
            String radicado = request.getParameter("radicado");
            String fechaRadicado = request.getParameter("fechaRadicado");
            String observacion = request.getParameter("observacion");
            int proceso = Integer.parseInt(request.getParameter("codigoProceso"));
            int tipoRadicado = Integer.parseInt(request.getParameter("tipoRadicado"));
            
            //Obtenemos La informacion del manager
            ReportesManager manager = new ReportesManager();
                        
            int codigoHistorial =  manager.insertarHistorialDagma(radicado, fechaRadicado, observacion, proceso, tipoRadicado);
            

        } catch (Exception ex) {
            //Logger.getLogger(EliminarActParamfisicoquimicos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

   
}
