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
import modelo.ApiManager;
import modelo.ProcesoVertimientosManagers.InformeProcesoSeco;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author illustrato
 */
@WebServlet(name = "SeleccionarInformacionProcesoSeco", urlPatterns = {"/SeleccionarInformacionProcesoSeco"})
public class SeleccionarInformacionProcesoSeco extends HttpServlet {

   
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
         
        int opcion = Integer.parseInt(request.getParameter("opcion"));
         
        switch(opcion){

                //Obtenemos todas las actividades economicas
                case 1:{
                    getInformacionProcesoSeco(request, response);
                }
                    break;

                
            }
        
        
    }
    
    private void getInformacionProcesoSeco (HttpServletRequest request, HttpServletResponse response) throws IOException{
        
        try {
            
            int codigoProceso  = Integer.parseInt(request.getParameter("codigoProceso")); 
            JSONObject jsonObject = new JSONObject();
         

            InformeProcesoSeco manager = new InformeProcesoSeco();
        
            jsonObject = manager.getInformacionProcesoSeco(codigoProceso);
     

            //Armamos la respuesta JSON y la enviamos
            response.setContentType("application/json");
           
            response.getWriter().write(jsonObject.toString());

                        
           }catch(SQLException ex){
            
           }


    }
    
}

