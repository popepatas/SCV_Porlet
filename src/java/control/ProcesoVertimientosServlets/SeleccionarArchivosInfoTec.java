/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control.ProcesoVertimientosServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.ProcesoVertimientosManagers.InformeProcesoSeco;
import modelo.ProcesoVertimientosManagers.ManejoLodos;
import org.json.simple.JSONArray;

/**
 *
 * @author illustrato
 */
@WebServlet(name = "SeleccionarArchivosInfoTec", urlPatterns = {"/SeleccionarArchivosInfoTec"})
public class SeleccionarArchivosInfoTec extends HttpServlet {


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
            throws  ServletException, IOException {
        
      try{
        
            JSONArray jsonArray = new JSONArray();
            int codigoProceso = Integer.parseInt(request.getParameter("codigoProceso"));

            ManejoLodos manager = new ManejoLodos();
            jsonArray = manager.getArchivosLodos(codigoProceso);


                //Armamos la respuesta JSON y la enviamos
                response.setContentType("application/json");
                for(Object jsonObject : jsonArray){

                       response.getWriter().write(jsonObject.toString());

                }
            
        } catch(Exception e){

        }
    }
}
