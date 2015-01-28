/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control.ClienteServlets;

import control.ParametrizacionServlets.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.*;
import modelo.ClientesManagers.*;

/**
 *
 * @author Galatea
 */
@WebServlet(name = "ActualizarCliente", urlPatterns = {"/ActualizarCliente"})
public class ActualizarCliente extends HttpServlet {



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
        
            JSONObject jsonObject = new JSONObject();

        try {

            //Obtenemos los parametros
            String nit = request.getParameter("nit");
            String razonSocial = request.getParameter("razonSocial");
            String ciiu = request.getParameter("ciiu");
            String direccion = request.getParameter("direccion");
            String barrio = request.getParameter("barrio");
            String comuna = request.getParameter("comuna");
            String telefono = request.getParameter("telefono");
            String telefono2 = request.getParameter("telefono2");
            String usoServicio = request.getParameter("usoServicio");
            String correo = request.getParameter("correo");
            String correo2 = request.getParameter("correo2");
            String web = request.getParameter("web");
            String representanteLegal = request.getParameter("representanteLegal");
            String estadoUltVertimiento = request.getParameter("estadoUltVertimiento");
            String codigo = request.getParameter("codigo");
            Integer respuesta;
            
            //Actualizamos el cliente.
            ClientesManager manager = new ClientesManager();
            
            respuesta =  manager.actualizar(nit, razonSocial, ciiu,  direccion, barrio, comuna, telefono, telefono2, usoServicio, correo, 
                    correo2, web, representanteLegal, estadoUltVertimiento, codigo);
           
            response.setContentType("application/json");
            
            jsonObject.put("error",respuesta);
            response.getWriter().write(jsonObject.toString());
            
        } catch (Exception ex) {
           response.setContentType("application/json");
           jsonObject.put("error","0");
           response.getWriter().write(jsonObject.toString());
        }
        
    }
    //-----------------------------------------------------------------------------
    
}
