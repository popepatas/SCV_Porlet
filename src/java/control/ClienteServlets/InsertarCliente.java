/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control.ClienteServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.ClientesManagers.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Galatea
 */
@WebServlet(name = "InsertarCliente", urlPatterns = {"/InsertarCliente"})
public class InsertarCliente extends HttpServlet {

   

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
         JSONObject respError = new JSONObject();
        try {
            
            //Obtenemos los parametros
            String nit = request.getParameter("nit");
            String razonSocial = request.getParameter("razonSocial");
            String direccion = request.getParameter("direccion");
            String barrio = request.getParameter("barrio");
            String correo = request.getParameter("correo");
            String correo2 = request.getParameter("correo2");
            String web = request.getParameter("web");
            String representanteLegal = request.getParameter("representanteLegal");
            String estadoUltVertimiento = request.getParameter("estadoUltVertimiento");
            String ciiu = request.getParameter("ciiu");       
            String comuna = request.getParameter("comuna");          
            String telefono = request.getParameter("telefono");
            String telefono2 = request.getParameter("telefono2");            
            String usoServicio = request.getParameter("usoServicio");
            Integer respuesta;
            
            //Creamos el manager para registrar la informacion
            ClientesManager manager = new ClientesManager();
            
            respuesta = manager.insertar(nit, razonSocial, ciiu,  direccion, barrio, comuna, telefono, 
                    telefono2, usoServicio, correo, correo2, web, representanteLegal, estadoUltVertimiento);
              
              response.setContentType("application/json");
            
              respError.put("error",respuesta);
              response.getWriter().write(respError.toString());
        
        } catch (Exception ex) {
            
              response.setContentType("application/json");
              respError.put("error",0);
              response.getWriter().write(respError.toString());
        }
            
    }

    /**
     * 
     * Convierte en Integer un valor String solo si es numero
     * o asigna NULL en caso de ser un String vacio.
     * 
     * @param numero
     * @return 
     */
    private Integer getIntegerNulo(String numero){
    
        Integer number = null;
        
            if(numero != ""){
                
                number = Integer.parseInt(numero);
                
            }
        
        
        return number;
    }
    
    

}
