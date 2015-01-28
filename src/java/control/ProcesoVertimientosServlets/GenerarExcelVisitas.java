/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control.ProcesoVertimientosServlets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.ApiManager;
import modelo.ProcesoVertimientosManagers.Visitas;
import org.json.simple.JSONArray;

/**
 *
 * @author illustrato
 */
@WebServlet(name = "GenerarExcelVisitas", urlPatterns = {"/GenerarExcelVisitas"})
public class GenerarExcelVisitas extends HttpServlet {

  

   
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

            String fechaInicio = request.getParameter("fechaInicio");
            String fechaFin    = request.getParameter("fechaFin");
            String tipoVisita    = request.getParameter("tipoVisita");
            String codigoProceso  = request.getParameter("codigoProceso");
            String estadoVisita = request.getParameter("estadoVisita");
            String contrato  = request.getParameter("contrato"); 
            String nit  = request.getParameter("nit"); 
            String razonSocial  = request.getParameter("razonSocial");
            String motivoVisita = request.getParameter("motivoVisita");
            
            JSONArray jsonArray = new JSONArray();

            String filaInicio = "";
            String filaFin= "";
            
            Visitas manager = new Visitas();
            
           String appDir = getServletContext().getRealPath("/");
           appDir += "sources/Visitas.xls";
           
      
            //Llamamos al manager para escribir el excel
            manager.escribirExcel(filaInicio, filaFin, tipoVisita, fechaInicio, fechaFin, codigoProceso, appDir, estadoVisita,contrato,nit,razonSocial,motivoVisita);
        
        } catch (SQLException ex) {
            //Logger.getLogger(GenerarExcelVisitas.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        
    }

}
