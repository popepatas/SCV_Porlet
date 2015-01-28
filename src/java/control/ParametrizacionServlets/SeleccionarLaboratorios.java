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
import modelo.ParametrizacionManagers.Laboratorios;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author illustrato
 */
@WebServlet(name = "SeleccionarLaboratorios", urlPatterns = {"/SeleccionarLaboratorios"})
public class SeleccionarLaboratorios extends HttpServlet {

  

   
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
         doPost( request,  response);
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
                            getLaboratorios(request, response);
                        }
                            break;

                        //Obtenemos una actividad economica especifica.    
                        case 2:{
                            getLaboratorio(request, response);
                        }
                            break;
                    }


            } catch (Exception e) {

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
    private void getLaboratorios(HttpServletRequest request, HttpServletResponse response){
        
        try {
            String nombre  = request.getParameter("nombre");                            
            String contactos  = request.getParameter("contactos");
            String direccion  = request.getParameter("direccion");
            String telefono1  = request.getParameter("telefono1");
            String telefono2  = request.getParameter("telefono2");                            
            String correo = request.getParameter("correo");
            String resolucion = request.getParameter("resolucion");
            String vigencia = request.getParameter("vigencia");
            
            //Obtenemos La informacion del manager
            Laboratorios manager = new Laboratorios();
            JSONArray jsonArray = manager.getLaboratorios(nombre, contactos, direccion, telefono1, telefono2, correo, resolucion, vigencia);

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
    private void getLaboratorio(HttpServletRequest request, HttpServletResponse response){
    
        try {

            //Obtenemos los parametros
            int codigo = Integer.parseInt(request.getParameter("codigo"));
            
            //Obtenemos La informacion del manager
            Laboratorios manager = new Laboratorios();
            JSONObject jsonArray = manager.getLaboratorio(codigo);

            //Armamos la respuesta JSON y la enviamos
            response.setContentType("application/json");
            response.getWriter().write(jsonArray.toString());

            
        } catch (Exception ex) {
            //Logger.getLogger(SeleccionarActEconomica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //-----------------------------------------------------------------------------

}
