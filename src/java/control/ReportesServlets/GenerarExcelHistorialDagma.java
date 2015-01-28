/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control.ReportesServlets;

import control.ProcesoVertimientosServlets.*;
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
import modelo.ReportesManagers.ReportesManager;
import org.json.simple.JSONArray;

/**
 *
 * @author illustrato
 */
@WebServlet(name = "GenerarExcelHistorialDagma", urlPatterns = {"/GenerarExcelHistorialDagma"})
public class GenerarExcelHistorialDagma extends HttpServlet {

  

   
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

           String proceso = request.getParameter("codigoProceso");
            
           ReportesManager manager = new ReportesManager();
            
           String appDir = getServletContext().getRealPath("/");
           String nombre = "HistorialDagma.xls";
           appDir += "sources/" + nombre;
           
      
            //Llamamos al manager para escribir el excel
            manager.escribirExcelDagma(appDir,proceso);
        
        } catch (SQLException ex) {
            //Logger.getLogger(GenerarExcelVisitas.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        
    }

}
