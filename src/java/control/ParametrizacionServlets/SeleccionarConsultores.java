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
import modelo.ParametrizacionManagers.Consultores;
import org.json.simple.JSONArray;

/**
 *
 * @author illustrato
 */
@WebServlet(name = "SeleccionarConsultores", urlPatterns = {"/SeleccionarConsultores"})
public class SeleccionarConsultores extends HttpServlet {


    
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
            
                    //Obtenemos la opcion
                int opcion = Integer.parseInt(request.getParameter("opcion"));

                switch(opcion){

                    //Obtenemos todas las actividades economicas
                    case 1:{
                        getConsultores(request, response);
                    }
                        break;

                    //Obtenemos una actividad economica especifica.    
                    case 2:{
                        getConsultor(request, response);
                    }
                        break;
                }
            
            
        } catch (Exception e) {
            
        }
        
        
    }

  
  /**
     * 
     * Llama al manager y obtiene la informacion de todas los Consultores
     *
     * 
     * @param request
     * @param response 
     */
    private void getConsultores(HttpServletRequest request, HttpServletResponse response){
        
        try {
            
            String nombre = request.getParameter("nombre");
            String apellidos = request.getParameter("apellidos");
            String direccion = request.getParameter("direccion");
            String telefono = request.getParameter("telefono1");
            String telefono2 = request.getParameter("telefono2");
            String identificacion = request.getParameter("identificacion");
            String correo = request.getParameter("correo");

            //Obtenemos La informacion del manager
            Consultores manager = new Consultores();
            JSONArray jsonArray = manager.getConsultores(nombre, apellidos, direccion, telefono, telefono2, identificacion, correo);

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
     * Llama al manager y obtiene la informacion de una Consultor especifico.
     * 
     * @param request
     * @param response 
     */
    private void getConsultor(HttpServletRequest request, HttpServletResponse response){
    
        try {

            //Obtenemos los parametros
            int codigo = Integer.parseInt(request.getParameter("codigo"));
            
            //Obtenemos La informacion del manager
            Consultores manager = new Consultores();
            JSONArray jsonArray = manager.getConsultor(codigo);

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
