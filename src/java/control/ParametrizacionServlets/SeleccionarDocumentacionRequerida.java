/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control.ParametrizacionServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.ParametrizacionManagers.DocumentacionRequerida;
import org.json.simple.JSONArray;

/**
 *
 * @author illustrato
 */
@WebServlet(name = "SeleccionarDocumentacionRequerida", urlPatterns = {"/SeleccionarDocumentacionRequerida"})
public class SeleccionarDocumentacionRequerida extends HttpServlet {

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
                getDocumentacionRequeridas(request, response);
            }
                break;
                
            //Obtenemos una actividad economica especifica.    
            case 2:{
                getDocumentacionRequerida(request, response);
            }
                break;
        }
        
    }

  private void getDocumentacionRequeridas(HttpServletRequest request, HttpServletResponse response){
  
        try {
            
            String descripcion = request.getParameter("descripcion");
            String tipoInforme = request.getParameter("tipoInforme");
            
            //Obtenemos La informacion del manager
            DocumentacionRequerida manager = new DocumentacionRequerida();
            JSONArray jsonArray = manager.getDocumentacionRequerida(descripcion, tipoInforme);

            //Armamos la respuesta JSON y la enviamos
            response.setContentType("application/json");
            for(Object jsonObject : jsonArray){

                    response.getWriter().write(jsonObject.toString());

            }

            
        } catch (Exception ex) {
            //Logger.getLogger(SeleccionarActEconomica.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
  
   private void getDocumentacionRequerida(HttpServletRequest request, HttpServletResponse response) {
         //Obtenemos La informacion del manager
         try {  
           Integer codigo = Integer.parseInt(request.getParameter("codigo"));
           Integer tipoInforme = null;
            
            DocumentacionRequerida manager = new DocumentacionRequerida();
            JSONArray jsonArray = manager.getDocumentacionRequerida(codigo, tipoInforme);

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
