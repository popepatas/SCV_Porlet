/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control.ProcesoVertimientosServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.ProcesoVertimientosManagers.ProgramarMonitoreo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author illustrato
 */
@WebServlet(name = "RegistrarSuperviciones", urlPatterns = {"/RegistrarSuperviciones"})
public class RegistrarSuperviciones extends HttpServlet {

  
  
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
        
        JSONArray resp = new JSONArray();
        
        try { 
            //Obtenemos la cadena con la informacion y la convertimos en un
            //JSONArray
            String monitoreos = request.getParameter("monitoreos");
            int tecnico  = Integer.parseInt(request.getParameter("tecnico"));
            int resul;
            int codProceso;
            JSONObject jsonObject = new JSONObject();
            JSONArray  jsonArray = new JSONArray();
            
            
            Object obj = JSONValue.parse(monitoreos);
            JSONArray monitoreosArray = new JSONArray();
            monitoreosArray = (JSONArray) obj;
          
                    //Recorremos el JSONArray y obtenemos la informacion.
                  for(int i = 0; i < monitoreosArray.size(); i ++){
                      codProceso =  Integer.parseInt(monitoreosArray.get(i).toString());

                      ProgramarMonitoreo manager = new ProgramarMonitoreo();                
                      resul = manager.registrarSupervision(codProceso,tecnico);


                       jsonObject.put("monitoreo",codProceso);
                       jsonObject.put("resultado",resul);

                       jsonArray.add(jsonObject.clone());


                  }
                  resp.add(jsonArray);
                    //Armamos la respuesta JSON y la enviamos
                response.setContentType("application/json");
                
                for(Object jsonObjectResp : resp){

                        response.getWriter().write(jsonObjectResp.toString());

                }
          } catch (Exception ex) {
                
          }
    }

    

}
