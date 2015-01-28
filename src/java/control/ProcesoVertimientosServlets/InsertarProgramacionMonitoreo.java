/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control.ProcesoVertimientosServlets;

import control.ParametrizacionServlets.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.ProcesoVertimientosManagers.*;
import org.json.simple.*;

/**
 *
 * @author Galatea
 */
@WebServlet(name = "InsertarProgramacionMonitoreo", urlPatterns = {"/InsertarProgramacionMonitoreo"})
public class InsertarProgramacionMonitoreo extends HttpServlet {


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
            
            //Obtenemos el numero de contrato
            String consultorMonitoreo = request.getParameter("consultorMonitoreo");
            String fechaMonitoreo = request.getParameter("fechaMonitoreo");
            String horaInicioMonitoreo = request.getParameter("horaInicioMonitoreo");
            String horaFinMonitoreo = request.getParameter("horaFinMonitoreo");
            int laboratorioMonitoreo = Integer.parseInt(request.getParameter("laboratorioMonitoreo"));
            int codigoProceso = Integer.parseInt(request.getParameter("codigoProceso"));
            String observacion = request.getParameter("observacionesReprogramacion");
            String duracionMonitoreo = request.getParameter("duracionMonitoreo");
            
            //Insertamos el programacion del monitoreo y obtenemos el codigo
            ProgramarMonitoreo manager = new ProgramarMonitoreo();
            int codigoMonitoreo = manager.insertar(consultorMonitoreo, fechaMonitoreo, horaInicioMonitoreo, 
                                                    horaFinMonitoreo, laboratorioMonitoreo, codigoProceso, observacion, duracionMonitoreo);
            
            
            //Obtenemos la cadena con la informacion y la convertimos en un
            //JSONArray
            String puntos = request.getParameter("puntosVertimiento");
            Object obj = JSONValue.parse(puntos);
            JSONArray jsonArray = new JSONArray();
            jsonArray = (JSONArray) obj;
                        
            //Recorremos el JSONArray y obtenemos la informacion.
            for(int i = 0; i < jsonArray.size(); i ++){
                
                //Obtenemos la info de los puntos
                JSONObject jsonObject = (JSONObject)jsonArray.get(i);
                int codigoPunto = Integer.parseInt((String)jsonObject.get("codigo"));
                int codigoActividad = Integer.parseInt((String)jsonObject.get("actividad"));
                
                //Creamos el manager y guardamos la informacion.
                manager.insertarPuntoMonitoreo(codigoPunto, codigoActividad, codigoMonitoreo);
                
                
            }
            
            
             respError.put("error", "1");
             response.getWriter().write(respError.toString());
            
            
        } catch (Exception ex) {
                                 
              respError.put("error", "0");
              response.getWriter().write(respError.toString());
               
        }
        
        
    }



}
