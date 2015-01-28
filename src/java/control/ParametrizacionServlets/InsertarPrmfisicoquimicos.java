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
import modelo.ParametrizacionManagers.*;
import org.json.simple.JSONObject;
/**
 *
 * @author Galatea
 */
@WebServlet(name = "InsertarPrmfisicoquimicos", urlPatterns = {"/InsertarPrmfisicoquimicos"})
public class InsertarPrmfisicoquimicos extends HttpServlet {



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
       
        try {
            
            //Obtenemos los datos enviados.
            String descripcion = request.getParameter("descripcion");
            int unidades = Integer.parseInt(request.getParameter("unidades"));
            int tipoParametro = Integer.parseInt(request.getParameter("tipoParametro"));
            int salida;
            JSONObject jsonObject = new JSONObject() ;

            //Creamos el manager para registrar la informacion
            ParamFisicoquimicos manager = new ParamFisicoquimicos();
            
            salida = manager.insertar(descripcion, tipoParametro, unidades);
            
            jsonObject.put("error",salida);
            
            response.setContentType("application/json");
            response.getWriter().write(jsonObject.toString());

            
            
        } catch (Exception ex) {
            //Logger.getLogger(InsertarActEconomica.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }



}
