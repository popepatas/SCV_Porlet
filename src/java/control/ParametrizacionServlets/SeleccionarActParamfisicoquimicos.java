/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control.ParametrizacionServlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.ApiManager;
import modelo.ParametrizacionManagers.*;
import org.json.simple.*;

/**
 *
 * @author illustrato
 */
@WebServlet(name = "SeleccionarActParamfisicoquimicos", urlPatterns = {"/SeleccionarActParamfisicoquimicos"})
public class SeleccionarActParamfisicoquimicos extends HttpServlet {

   
   
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
            
         //Obtenemos la opcion
        int opcion = Integer.parseInt(request.getParameter("opcion"));
        
        switch(opcion){
            
            //Obtenemos todas las actividades economicas
            case 1:{
                getActParamFisicoquimicos( request,  response);
            }
                break;
                
            //Obtenemos una actividad economica especifica.    
            case 2:{
                getActParamFisicoquimico( request,  response);
            }
                break;
        }

        
        
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

    /**
     * 
     * Llama al manager y obtiene la informacion de todas las
     * Actividades Economicas
     * 
     * @param request
     * @param response 
     */
    private void getActParamFisicoquimicos(HttpServletRequest request, HttpServletResponse response){
        
        try {

            //Obtenemos parametros
            String actividad = request.getParameter("actividad");
            String parametro = request.getParameter("parametro");
            String rangoInicial = request.getParameter("rangoInicial");
            String rangoFinal = request.getParameter("rangoFinal");
            String mayorRangoInicial = request.getParameter("mayorRangoInicial");
            String mayorRangoFinal = request.getParameter("mayorRangoFinal");
            String mostrarRango = request.getParameter("mostrarRango");
            
            
            //Obtenemos La informacion del manager
            ActParamFisicoquimicos manager = new ActParamFisicoquimicos();
            JSONArray jsonArray = manager.getActParamFisicoquimicos(actividad, parametro, rangoInicial, rangoFinal, mayorRangoInicial, mayorRangoFinal,mostrarRango);

            //Armamos la respuesta JSON y la enviamos
            response.setContentType("application/json");
            for(Object jsonObject : jsonArray){

                    response.getWriter().write(jsonObject.toString());

            }

            
        } catch (Exception ex) {
            //Logger.getLogger(SeleccionarActEconomica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //-----------------------------------------------------------------------------
    
    
    /**
     * 
     * Llama al manager y obtiene la informacion de una actividad
     * economica especifica.
     * 
     * @param request
     * @param response 
     */
    private void getActParamFisicoquimico(HttpServletRequest request, HttpServletResponse response){
    
        try {

            //Obtenemos los parametros
            Integer codigoAct = ApiManager.ponerNull(request.getParameter("actividad"));
            Integer codigoParm = ApiManager.ponerNull(request.getParameter("parametro"));
            String rangoFinal = request.getParameter("rangoFinal");
            String rangoInicial = request.getParameter("rangoInicial");
            String mayorFinal = request.getParameter("mayorFinal");
            String mayorInicial = request.getParameter("mayorInicial");
            String mostrarRango = request.getParameter("mostrarRango");
            
            //Obtenemos La informacion del manager
            ActParamFisicoquimicos manager = new ActParamFisicoquimicos();
            JSONArray jsonArray = manager.getActParamFisicoquimico(codigoAct, codigoParm, rangoInicial,rangoFinal,mayorInicial,mayorFinal,mostrarRango);

            //Armamos la respuesta JSON y la enviamos
            response.setContentType("application/json");
            for(Object jsonObject : jsonArray){

                    response.getWriter().write(jsonObject.toString());

            }

            
        } catch (Exception ex) {
            //Logger.getLogger(SeleccionarActEconomica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //-----------------------------------------------------------------------------

    

}
