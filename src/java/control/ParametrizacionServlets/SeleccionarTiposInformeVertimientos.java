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
import modelo.ParametrizacionManagers.EstadosProceso;
import modelo.ParametrizacionManagers.TiposInformeVertimientos;
import org.json.simple.JSONArray;

/**
 *
 * @author Galatea
 */
@WebServlet(name = "SeleccionarTiposInformeVertimientos", urlPatterns = {"/SeleccionarTiposInformeVertimientos"})
public class SeleccionarTiposInformeVertimientos extends HttpServlet {


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
            
            //Obtenemos todas los Tipos de informes
            case 1:{
                getTiposInformes(request, response);
            }
                break;
                
            //Obtenemos un tipo de informe especifico.    
            case 2:{
                getTipoInforme(request, response);
            }
                break;
                
            // Obtenemos solo caracterización y proceso seco
            
            case 3:{
            
                getTipoInformesPrincipal(request, response);
            }    
                
        }
    }

 
    
            /**
     * 
     * Llama al manager y obtiene la informacion de todas las
     * Actividades Economicas
     * 
     * @param request
     * @param response 
     */
    private void getTiposInformes(HttpServletRequest request, HttpServletResponse response){
        
        try {
            String descripcion = request.getParameter("descripcion");
            
            //Obtenemos La informacion del manager
            TiposInformeVertimientos manager = new TiposInformeVertimientos();
            JSONArray jsonArray = manager.getTiposInformes(descripcion);

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
    private void getTipoInforme(HttpServletRequest request, HttpServletResponse response){
    
        try {

            //Obtenemos los parametros
            int codigo = Integer.parseInt(request.getParameter("codigo"));
            
            //Obtenemos La informacion del manager
            TiposInformeVertimientos manager = new TiposInformeVertimientos();
            JSONArray jsonArray = manager.getTipoInforme(codigo);

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
    private void getTipoInformesPrincipal(HttpServletRequest request, HttpServletResponse response){
    
        try {
                    
            //Obtenemos La informacion del manager
            TiposInformeVertimientos manager = new TiposInformeVertimientos();
            JSONArray jsonArray = manager.getTipoInformesPrincial();

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
