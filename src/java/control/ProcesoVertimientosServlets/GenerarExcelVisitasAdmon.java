/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control.ProcesoVertimientosServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.ProcesoVertimientosManagers.Visitas;
import org.json.simple.JSONArray;

/**
 *
 * @author illustrato
 */
@WebServlet(name = "GenerarExcelVisitasAdmon", urlPatterns = {"/GenerarExcelVisitasAdmon"})
public class GenerarExcelVisitasAdmon extends HttpServlet {
    

   
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
            String estadoVisita  = request.getParameter("estadoVisita");             
            String contrato  = request.getParameter("contrato"); 
            String nit  = request.getParameter("nit"); 
            String razonSocial  = request.getParameter("razonSocial"); 
            String programacion  = request.getParameter("programacion"); 
            String comuna  = request.getParameter("comuna"); 
            String direccion =  request.getParameter("direccion"); 
            JSONArray jsonArray = new JSONArray();

            String filaInicio = "";
            String filaFin= "";
            
            Visitas manager = new Visitas();
            
           String appDir = getServletContext().getRealPath("/");
           appDir += "sources/VisitasPendientes.xls";
           
      
            //Llamamos al manager para escribir el excel
            manager.escribirExcelPendientes(filaInicio, filaFin, codigoProceso, appDir, contrato,nit,razonSocial,comuna,direccion);
        
        } catch (SQLException ex) {
            //Logger.getLogger(GenerarExcelVisitas.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        
    }

}
