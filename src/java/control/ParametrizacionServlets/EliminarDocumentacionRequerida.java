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
import modelo.ParametrizacionManagers.DocumentacionRequerida;
import org.json.simple.JSONArray;

/**
 *
 * @author illustrato
 */
@WebServlet(name = "EliminarDocumentacionRequerida", urlPatterns = {"/EliminarDocumentacionRequerida"})
public class EliminarDocumentacionRequerida extends HttpServlet {

   

    
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
            
            Integer codigo = Integer.parseInt(request.getParameter("codigo"));
            
            JSONArray respError = new JSONArray(); // uno significa que no hay error
            

            //Creamos el manager para registrar la informacion
            DocumentacionRequerida manager = new DocumentacionRequerida();
        
            respError = manager.eliminar(codigo);
            
            //Armamos la respuesta JSON y la enviamos
            response.setContentType("application/json");
            
            for(Object jsonObject : respError){
                
                    response.getWriter().write(respError.toString());
                
            }
            
        } catch (Exception ex) {
            //Logger.getLogger(InsertarDocumentacionRequerida.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

 
}
