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
import modelo.ParametrizacionManagers.ActividadEconomica;
import org.json.simple.JSONArray;
import modelo.ParametrizacionManagers.*;

/**
 *
 * @author Galatea
 */
@WebServlet(name = "SeleccionarParamfisicoquimicos", urlPatterns = {"/SeleccionarParamfisicoquimicos"})
public class SeleccionarParamfisicoquimicos extends HttpServlet {


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
                getPrmfisicoquimicos(request, response);
            }
                break;
                
            //Obtenemos una actividad economica especifica.    
            case 2:{
                getPrmfisicoquimico(request, response);
            }
                break;
                  //Obtenemos todas los parametros dependiendo de un tipo de parametro
            case 3:{
                 
                String tipoParam = String.valueOf(request.getParameter("tipoParam"));
                
                getParamFisicoQuimicosXTipoParametro(tipoParam, request, response);
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
    private void getPrmfisicoquimicos(HttpServletRequest request, HttpServletResponse response){
        
        try {
            String descripcion = request.getParameter("descripcion");
            String unidades = request.getParameter("unidades");
            String tipoParametro = request.getParameter("tipoParametro");
            //Obtenemos La informacion del manager
            ParamFisicoquimicos manager = new ParamFisicoquimicos();
            JSONArray jsonArray = manager.getPrmfisicoquimicos(descripcion, unidades, tipoParametro);

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
    private void getPrmfisicoquimico(HttpServletRequest request, HttpServletResponse response){
    
        try {

            //Obtenemos los parametros
            int codigo = Integer.parseInt(request.getParameter("codigo"));
            
            //Obtenemos La informacion del manager
            ParamFisicoquimicos manager = new ParamFisicoquimicos();
            JSONArray jsonArray = manager.getPrmfisicoquimico(codigo);

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

    
        private void getParamFisicoQuimicosXTipoParametro(String tipoParametro, HttpServletRequest request, HttpServletResponse response){
        try {
            //Obtenemos La informacion del manager
            ParamFisicoquimicos manager = new ParamFisicoquimicos();
            JSONArray jsonArray = manager.getParamFisicoQuimicosXTipoParametro(tipoParametro);
            
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
