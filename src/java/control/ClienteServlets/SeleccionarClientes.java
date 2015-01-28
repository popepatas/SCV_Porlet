/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control.ClienteServlets;

import control.ParametrizacionServlets.*;
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
import modelo.ClientesManagers.ClientesManager;
import modelo.ParametrizacionManagers.*;
import org.json.simple.*;

/**
 *
 * @author Galatea
 */
@WebServlet(name = "SeleccionarClientes", urlPatterns = {"/SeleccionarClientes"})
public class SeleccionarClientes extends HttpServlet {



    
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
    //-----------------------------------------------------------------------------
    
    
    
    
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
                getClientes(request, response);
            }
                break;
                
            //Obtenemos una actividad economica especifica.    
            case 2:{
                getCliente(request, response);
            }
                break;
           
            case 3:{ 
                getClientePorContrato(request, response); 
            }
                break;
            
            case 4:{ 
                getClienteporNit(request, response); 
            }
                break;
        }

        
    }
    //-----------------------------------------------------------------------------


    
    
    /**
     * 
     * Llama al manager y obtiene la informacion de todas las
     * Actividades Economicas
     * 
     * @param request
     * @param response 
     */
    private void getClientes(HttpServletRequest request, HttpServletResponse response){
        
        try {
            
            //Obtenemos los parametros
            String nit = request.getParameter("nit");
            String razonSocial = request.getParameter("razonSocial"); 
            String direccion = request.getParameter("direccion"); 
            String ciiu = request.getParameter("ciiu");
            String comuna = request.getParameter("comuna");
            String usoServicio = request.getParameter("usoServicio");
            String barrio = request.getParameter("barrio");

            
            //Obtenemos La informacion del manager
            ClientesManager manager = new ClientesManager();
            JSONArray jsonArray = manager.getClientes(nit, ciiu, razonSocial, direccion, comuna, usoServicio, barrio);
            String json = null;
            //Armamos la respuesta JSON y la enviamos
            response.setContentType("application/json");
            for(Object jsonObject : jsonArray){
                    json = jsonObject.toString();
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
    private void getCliente(HttpServletRequest request, HttpServletResponse response){
    
        try {

            //Obtenemos los parametros
            int codigo = Integer.parseInt(request.getParameter("codigo"));
            
            //Obtenemos La informacion del manager
            ClientesManager manager = new ClientesManager();
            JSONArray jsonArray = manager.getCliente(codigo);

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
    private void getClientePorContrato(HttpServletRequest request, HttpServletResponse response){
    
        try {

            //Obtenemos los parametros
            int contrato = Integer.parseInt(request.getParameter("contrato"));
            
            //Obtenemos La informacion del manager
            ClientesManager manager = new ClientesManager();
            JSONArray jsonArray = manager.getClientePorContrato(contrato);

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
     * Llama al manager y obtiene la informacion de todas las
     * Actividades Economicas
     * 
     * @param request
     * @param response 
     */
    private void getClienteporNit(HttpServletRequest request, HttpServletResponse response){
        
        try {
            
            //Obtenemos los parametros
            String nit = request.getParameter("nit");
            
            //Obtenemos La informacion del manager
            ClientesManager manager = new ClientesManager();
            JSONArray jsonArray = manager.getClienteporNit(nit);
            String json = null;
            //Armamos la respuesta JSON y la enviamos
            response.setContentType("application/json");
            for(Object jsonObject : jsonArray){
                    json = jsonObject.toString();
                    response.getWriter().write(jsonObject.toString());

            }

            
        } catch (Exception ex) {
            //Logger.getLogger(SeleccionarActEconomica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //-----------------------------------------------------------------------------
    
    
    
}
