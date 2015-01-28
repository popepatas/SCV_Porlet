/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control.ProcesoVertimientosServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.ApiManager;
import modelo.ProcesoVertimientosManagers.ManejoLodos;
import org.json.simple.JSONArray;

/**
 *
 * @author illustrato
 */
@WebServlet(name = "SeleccionarEntidadesLodos", urlPatterns = {"/SeleccionarEntidadesLodos"})
public class SeleccionarEntidadesLodos extends HttpServlet {

  

   
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
           
            int opcion = Integer.parseInt(request.getParameter("opcion"));

            switch(opcion){

                //Obtenemos todas las actividades economicas
                case 1:{
                    getLodos(request, response);
                }
                    break;

                //Obtenemos una actividad economica especifica.    
                case 2:{
                    getLodo(request, response);
                }
                    break;
            }
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
        doGet(request, response);
    }

    private void getLodos(HttpServletRequest request, HttpServletResponse response){
        try{
            
            String codigo = request.getParameter("codigo");
            
            JSONArray jsonArray = new JSONArray();

            ManejoLodos manager = new ManejoLodos();

            jsonArray = manager.getLodos(codigo);


            //Armamos la respuesta JSON y la enviamos
            response.setContentType("application/json");
            for(Object jsonObject : jsonArray){

                response.getWriter().write(jsonObject.toString());

            }
        }catch(Exception e){

        }
    }
    
    private void getLodo(HttpServletRequest request, HttpServletResponse response){
     
       try{
            
            int codigo  = Integer.parseInt(request.getParameter("codigo")); 
            JSONArray jsonArray = new JSONArray();
        
            ManejoLodos manager = new ManejoLodos();
            jsonArray = manager.getLodo(codigo);


            //Armamos la respuesta JSON y la enviamos
            response.setContentType("application/json");
            for(Object jsonObject : jsonArray){

                response.getWriter().write(jsonObject.toString());

            }
        }catch(Exception e){
            
        }
    }

}
