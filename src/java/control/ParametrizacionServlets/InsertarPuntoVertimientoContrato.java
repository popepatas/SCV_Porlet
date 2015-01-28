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
import org.json.simple.*;

/**
 *
 * @author Galatea
 */
@WebServlet(name = "InsertarPuntoVertimientoContrato", urlPatterns = {"/InsertarPuntoVertimientoContrato"})
public class InsertarPuntoVertimientoContrato extends HttpServlet {



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
                String tipoEstructura = (String)jsonObject.get("tipoEstructura");
                int estado = Integer.parseInt((String)jsonObject.get("estado"));
                
                //Creamos el manager y guardamos la informacion.
                PuntosVertimiento manager = new PuntosVertimiento();
                manager.insertar(ubicacion, latitud, longitud, observacion, estado, contrato,tipoEstructura);
                
            }
            
        } catch (Exception ex) {
            //Logger.getLogger(InsertarActEconomica.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }



}
