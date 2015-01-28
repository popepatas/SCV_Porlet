/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control.ProcesoVertimientosServlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.ApiManager;
import modelo.ProcesoVertimientosManagers.InformeProcesoSeco;
import org.json.simple.JSONObject;


/**
 *
 * @author illustrato
 */
@WebServlet(name = "RegistrarProcesoSeco", urlPatterns = {"/RegistrarProcesoSeco"})
public class RegistrarProcesoSeco extends HttpServlet {
    
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
       
        
              int codigoProceso =  Integer.parseInt(request.getParameter("codigoProceso"));
              Integer laboratorioProcesoSeco = Integer.parseInt(request.getParameter("laboratorioProcesoSeco"));
              Integer consultorProcesoSeco =  ApiManager.ponerNull(request.getParameter("consultorProcesoSeco"));
              String fechaEntregaProcesoSeco = request.getParameter("fechaEntregaProcesoSeco"); 
              String fechaRadicacionProcesoSeco = request.getParameter("fechaRadicacionProcesoSeco");
              String observacionesProcesoSeco = request.getParameter("observacionesProcesoSeco");
              
              /*Campo de devolucion*/
              String fechaEntDevolProcesoSeco = request.getParameter("fechaEntDevolProcesoSeco");
              String fechaDevolProcesoSeco = request.getParameter("fechaDevolProcesoSeco");
              String observacionDevolProsesoSeco = request.getParameter("observacionDevolProsesoSeco");
              Integer tipoDevolProcesoSeco =  ApiManager.ponerNull(request.getParameter("tipoDevolProcesoSeco"));
              JSONObject resultado = new JSONObject();

        
        InformeProcesoSeco manager = new InformeProcesoSeco();
        resultado =  manager.registrar(codigoProceso, laboratorioProcesoSeco, consultorProcesoSeco, fechaEntregaProcesoSeco, fechaRadicacionProcesoSeco, fechaEntDevolProcesoSeco, fechaDevolProcesoSeco, observacionDevolProsesoSeco, tipoDevolProcesoSeco, observacionesProcesoSeco);
        
         //Armamos la respuesta JSON y la enviamos
            response.setContentType("application/json");
            response.getWriter().write(resultado.toString());
        
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
