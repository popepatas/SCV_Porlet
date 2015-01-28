/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control.ProcesoVertimientosServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.ProcesoVertimientosManagers.Visitas;
import org.json.simple.JSONArray;

/**
 *
 * @author illustrato
 */
@WebServlet(name = "SeleccionarFechasVisita", urlPatterns = {"/SeleccionarFechasVisita"})
public class SeleccionarFechasVisita extends HttpServlet {

       
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
      doPost(request,response);
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
        try{
        //Parametros que envia el plugin jQuery FullCalendar, estos son enviados en formato TimeStamp
            String start = request.getParameter("start");
            String end    = request.getParameter("end");   
            String clase    = request.getParameter("clase");   
            int codigoProceso  = Integer.parseInt(request.getParameter("codigoProceso")); 
            JSONArray jsonArray = new JSONArray();
            
            Calendar fechaInicial =  GregorianCalendar.getInstance();
            Calendar fechaFinal   =  GregorianCalendar.getInstance();
            
            /*Se transforaman las fechas de TimeStamp a Calendar*/   
            fechaInicial.setTimeInMillis( (Long.parseLong(start)*1000));                
            fechaFinal.setTimeInMillis( (Long.parseLong(end)*1000));
            
            start = fechaInicial.get(GregorianCalendar.DAY_OF_MONTH) +"/"+(fechaInicial.get(GregorianCalendar.MONTH)+1)+"/"+fechaInicial.get(GregorianCalendar.YEAR);
            end   = fechaFinal.get(GregorianCalendar.DAY_OF_MONTH) +"/"+(fechaFinal.get(GregorianCalendar.MONTH)+1)+"/"+fechaFinal.get(GregorianCalendar.YEAR);
            
            Visitas manager = new Visitas();
                  
            jsonArray = manager.getFechasVisitasPorProceso(codigoProceso, start, end,clase);
            
            //Armamos la respuesta JSON y la enviamos
            response.setContentType("application/json");
            for(Object jsonObject : jsonArray){

                    response.getWriter().write(jsonObject.toString());

            }
            
            
        } catch (SQLException ex) {
        //    Logger.getLogger(SeleccionarFechasVisitas.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            
            
            
    }

 

}
