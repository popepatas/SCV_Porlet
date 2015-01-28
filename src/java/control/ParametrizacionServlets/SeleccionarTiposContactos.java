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
import modelo.ParametrizacionManagers.TiposContactos;
import modelo.ParametrizacionManagers.UnidadesMedida;
import org.json.simple.JSONArray;

/**
 *
 * @author illustrato
 */
@WebServlet(name = "SeleccionarTiposContactos", urlPatterns = {"/SeleccionarTiposContactos"})
public class SeleccionarTiposContactos extends HttpServlet {

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
            
            int opcion = Integer.parseInt(request.getParameter("opcion"));
            
            switch(opcion){
            
                case 1: getTiposContactos(request, response);
                    break;
                case 2: getTipoContacto(request, response);
                    break;
            
            }
            
            
            
        } catch (Exception e) {
            
        }
        
        
    }

    
     /**
     * 
     * Llama al manager y obtiene la informacion de un tipo de contacto especifico
     * 
     * @param request
     * @param response 
     */
    
    private void getTipoContacto(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
         try{  
           int codigo = Integer.parseInt(request.getParameter("codigo"));
        
            //Obtenemos La informacion del manager
            TiposContactos manager  = new TiposContactos();
            JSONArray jsonArray = manager.getTipoContacto(codigo);

            //Armamos la respuesta JSON y la enviamos
            response.setContentType("application/json");
            for(Object jsonObject : jsonArray){

                    response.getWriter().write(jsonObject.toString());

            }
         }
         catch(Exception e){
         }

        
    }
/**
     * 
     * Llama al manager y obtiene la informacion de los  tipos de contactos
     * 
     * @param request
     * @param response 
     */
    private void getTiposContactos(HttpServletRequest request, HttpServletResponse response) {
          try{  
              
            String descripcion = request.getParameter("descripcion");
        
            //Obtenemos La informacion del manager
            TiposContactos manager  = new TiposContactos();
            JSONArray jsonArray = manager.getTiposContactos(descripcion);

            //Armamos la respuesta JSON y la enviamos
            response.setContentType("application/json");
            for(Object jsonObject : jsonArray){

                    response.getWriter().write(jsonObject.toString());

            }
         }
         catch(Exception e){
         }
    }

}
