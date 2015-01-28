/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control.ParametrizacionServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.ParametrizacionManagers.ActividadEconomica;
import org.json.simple.JSONArray;
import modelo.ParametrizacionManagers.*;
import org.json.simple.JSONObject;

/**
 *
 * @author Galatea
 */
@WebServlet(name = "SeleccionarPuntoVertimientoContrato", urlPatterns = {"/SeleccionarPuntoVertimientoContrato"})
public class SeleccionarPuntoVertimientoContrato extends HttpServlet {


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
            
            //Obtenemos todas las actividades economicas
            case 1:{
                getPuntosVertimiento(request, response);
            }
                break;
                
            //Obtenemos una actividad economica especifica.    
            case 2:{
                getPuntoVertimiento(request, response);
            }
                break;
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
    private void getPuntosVertimiento(HttpServletRequest request, HttpServletResponse response){
        
        try {

            String contrato = request.getParameter("contrato");
            
            //Obtenemos La informacion del manager
            PuntosVertimiento manager = new PuntosVertimiento();
            JSONArray jsonArray = manager.getPuntosVertimiento(contrato);

            //Armamos la respuesta JSON y la enviamos
            response.setContentType("application/json");
            for(Object jsonObject : jsonArray){

                    response.getWriter().write(jsonObject.toString());

            }

            
        } catch (Exception ex) {
            
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
    private void getPuntoVertimiento(HttpServletRequest request, HttpServletResponse response){
    
        try {

            //Obtenemos los parametros
            String codigo = request.getParameter("codigo");
            
            //Obtenemos La informacion del manager
            PuntosVertimiento manager = new PuntosVertimiento();
            JSONObject jsonArray = manager.getPuntoVertimiento(codigo);
            String jsonString = jsonArray.toString();
            
            //Armamos la respuesta JSON y la enviamos
            response.setContentType("application/json");
            response.getWriter().write(jsonArray.toString());

            
        } catch (Exception ex) {
            //Logger.getLogger(SeleccionarActEconomica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //-----------------------------------------------------------------------------


}
