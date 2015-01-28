/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control.ProcesoVertimientosServlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.ProcesoVertimientosManagers.VerificacionInfoCaracterizacion;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author illustrato
 */
@WebServlet(name = "InsertarVerificacionInfoCaracterizacion", urlPatterns = {"/InsertarVerificacionInfoCaracterizacion"})
public class InsertarVerificacionInfoCaracterizacion extends HttpServlet {

 

    
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
            
         
            //Obtenemos la cadena con la informacion y la convertimos en un
            //JSONArray
            String respuestas = request.getParameter("respuestas");
            Integer codigoProceso = Integer.parseInt(request.getParameter("codigoProceso"));
            Object obj = JSONValue.parse(respuestas);
            JSONArray jsonArray = new JSONArray();
            jsonArray = (JSONArray) obj;
            
            //Recorremos el JSONArray y obtenemos la informacion.
            for(int i = 0; i < jsonArray.size(); i ++){
                
                JSONObject jsonObject = (JSONObject)jsonArray.get(i);
                String checkeado = (String)jsonObject.get("checkeado");
                Integer codigo = Integer.parseInt( (String)jsonObject.get("codigo"));
                
                //Creamos el manager y guardamos la informacion.
                VerificacionInfoCaracterizacion manager = new VerificacionInfoCaracterizacion();
                manager.insertar(checkeado, codigo, codigoProceso);
                
            }
            
        } catch (Exception ex) {
            //Logger.getLogger(InsertarActEconomica.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    
    }


}
