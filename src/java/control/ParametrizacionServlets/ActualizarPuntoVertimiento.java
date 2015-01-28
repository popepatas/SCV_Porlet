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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author Galatea
 */
@WebServlet(name = "ActualizarPuntoVertimiento", urlPatterns = {"/ActualizarPuntoVertimiento"})
public class ActualizarPuntoVertimiento extends HttpServlet {


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
        JSONObject resError = new JSONObject();
        try {
            
            //Obtenemos el numero de contrato
            Double contrato = Double.parseDouble(request.getParameter("contrato"));
            
            //Obtenemos la cadena con la informacion y la convertimos en un
            //JSONArray
            String puntos = request.getParameter("puntos");
            Object obj = JSONValue.parse(puntos);
            JSONArray jsonArray = new JSONArray();
            jsonArray = (JSONArray) obj;
            
            //Recorremos el JSONArray y obtenemos la informacion.
            for(int i = 0; i < jsonArray.size(); i ++){
                
                JSONObject jsonObject = (JSONObject)jsonArray.get(i);
                String ubicacion = (String)jsonObject.get("ubicacion");
                String latitud = (String)jsonObject.get("latitud");
                String longitud = (String)jsonObject.get("longitud");
                String observacion = (String)jsonObject.get("observacion");
                int estado = Integer.parseInt((String)jsonObject.get("estado"));
                String codigo = (String)jsonObject.get("codigo");
                String tipoEstructura = (String)jsonObject.get("tipoEstructura");
                
                //Creamos el manager y guardamos la informacion.
                PuntosVertimiento manager = new PuntosVertimiento();
                manager.actualizar( codigo,  ubicacion,  latitud,  longitud, 
             observacion,  estado,  contrato, tipoEstructura);
                
            }
            
            
            resError.put("error", 1);
        } catch (Exception ex) {
           resError.put("error", 0);
           
           
        }
        
    }


}
