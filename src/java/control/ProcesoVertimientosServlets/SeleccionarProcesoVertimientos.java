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
import modelo.ProcesoVertimientosManagers.ProcesoVertimientos;
import org.json.simple.JSONArray;

/**
 *
 * @author illustrato
 */
@WebServlet(name = "SeleccionarProcesoVertimientos", urlPatterns = {"/SeleccionarProcesoVertimientos"})
public class SeleccionarProcesoVertimientos extends HttpServlet {

   

    
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
            
               //Obtenemos los parametros
            String nit = request.getParameter("nitBqa");
            String razonSocial = request.getParameter("razonSocial"); 
            String codigoProceso = request.getParameter("nroProceso"); 
            String ciiu = request.getParameter("ciiuBqa");
            String contrato = request.getParameter("contratoBqa");
            String anio = request.getParameter("anio");
            String fechaInicial = request.getParameter("fechaInicio");
            String fechaFinal = request.getParameter("fechaFin");
            //Este parametro es enviado automaticamente por Kendoui
            int take = ApiManager.numeroNull(request.getParameter("take"));
            //Este parametro es enviado automaticamente por Kendoui
            int skip = ApiManager.numeroNull(request.getParameter("skip"));
            Integer filaInicio = skip + 1;
            Integer filaFin= take + skip;

            //Obtenemos La informacion del manager
            ProcesoVertimientos manager = new ProcesoVertimientos();
            JSONArray jsonArray = manager.getProcesoVertimientos(codigoProceso, nit, contrato, ciiu, razonSocial, anio, fechaInicial, fechaFinal, filaInicio.toString(),filaFin.toString());

            //Armamos la respuesta JSON y la enviamos
            response.setContentType("application/json");
            for(Object jsonObject : jsonArray){

                    response.getWriter().write(jsonObject.toString());

            }

            
        } catch (Exception ex) {
            //Logger.getLogger(SeleccionarActEconomica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    

}
