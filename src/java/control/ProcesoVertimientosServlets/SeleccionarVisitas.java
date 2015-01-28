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
import modelo.ApiManager;
import modelo.ProcesoVertimientosManagers.Visitas;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author illustrato
 */
@WebServlet(name = "SeleccionarVisitas", urlPatterns = {"/SeleccionarVisitas"})
public class SeleccionarVisitas extends HttpServlet {

  

   
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
           
            int opcion = Integer.parseInt(request.getParameter("opcion"));

            switch(opcion){

                //Obtenemos todas las visitas economicas
                case 1:{
                    getVisitas(request, response);
                }
                    break;

                //Obtenemos una visita  especifica.    
                case 2:{
                    getVisita(request, response);
                }
                    break;
                 //Obtenemos una todas las visitas de los proceso
                case 3:{
                    
                    getVisitasPendientes(request, response);
                }
                    break;
                
                case 4:{
                    
                    getContVisitasPendientes(request, response);
                }
                    break;
            }
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
        doGet(request, response);
    }

    private void getVisitas(HttpServletRequest request, HttpServletResponse response){
        try{
            
            //Parametros que envia el plugin jQuery FullCalendar, estos son enviados en formato TimeStamp
            String fechaInicio = request.getParameter("fechaInicio");
            String fechaFin    = request.getParameter("fechaFin");   
            String tipoVisita    = request.getParameter("tipoVisita");   
            String codigoProceso  = request.getParameter("codigoProceso"); 
            String estadoVisita  = request.getParameter("estadoVisita");             
            String contrato  = request.getParameter("contrato"); 
            String nit  = request.getParameter("nit"); 
            String razonSocial  = request.getParameter("razonSocial");
            String motivoVisita = request.getParameter("motivoVisita");
            
            JSONArray jsonArray = new JSONArray();
            //Este parametro es enviado automaticamente por Kendoui
            int take = ApiManager.numeroNull(request.getParameter("take"));
            //Este parametro es enviado automaticamente por Kendoui
            int skip = ApiManager.numeroNull(request.getParameter("skip"));
            Integer filaInicio = skip + 1;
            Integer filaFin= take + skip;



            Visitas manager = new Visitas();

            jsonArray = manager.getVisitasPorProceso(filaInicio.toString(), filaFin.toString(), tipoVisita, fechaInicio, fechaFin, codigoProceso, estadoVisita,contrato,nit,razonSocial,motivoVisita);


            //Armamos la respuesta JSON y la enviamos
            response.setContentType("application/json");
            for(Object jsonObject : jsonArray){

                response.getWriter().write(jsonObject.toString());

            }
        }catch(Exception e){

        }
    }
    
       private void getVisitasPendientes(HttpServletRequest request, HttpServletResponse response){
        try{
            
            //Parametros que envia el plugin jQuery FullCalendar, estos son enviados en formato TimeStamp
            
            
            String codigoProceso  = request.getParameter("codigoProceso");             
            String contrato  = request.getParameter("contrato"); 
            String nit  = request.getParameter("nit"); 
            String razonSocial  = request.getParameter("razonSocial");             
            String comuna  = request.getParameter("comuna"); 
            String direccion = request.getParameter("direccion");
            
            JSONArray jsonArray = new JSONArray();
            //Este parametro es enviado automaticamente por Kendoui
            int take = ApiManager.numeroNull(request.getParameter("take"));
            //Este parametro es enviado automaticamente por Kendoui
            int skip = ApiManager.numeroNull(request.getParameter("skip"));
            Integer filaInicio = skip + 1;
            Integer filaFin= take + skip;



            Visitas manager = new Visitas();

            jsonArray = manager.getVisitasPendientes(filaInicio.toString(), filaFin.toString(), codigoProceso, contrato, nit, razonSocial, comuna, direccion);


            //Armamos la respuesta JSON y la enviamos
            response.setContentType("application/json");
            for(Object jsonObject : jsonArray){

                response.getWriter().write(jsonObject.toString());

            }
        }catch(Exception e){

        }
    }
    
    
    private void getVisita(HttpServletRequest request, HttpServletResponse response){
     
        try{
            
            String codigo  = request.getParameter("codigo"); 
            JSONArray jsonArray = new JSONArray();
        
            Visitas manager = new Visitas();
            jsonArray = manager.getVisita(codigo);


            //Armamos la respuesta JSON y la enviamos
            response.setContentType("application/json");
            for(Object jsonObject : jsonArray){

                response.getWriter().write(jsonObject.toString());

            }
        }catch(Exception e){
            
        }
    }
    
    
    private void getContVisitasPendientes(HttpServletRequest request, HttpServletResponse response){
            
            JSONObject jsonObject = new JSONObject();
               
           try{ 
            String codigoProceso  = request.getParameter("codigoProceso"); 
            Visitas manager = new Visitas();
            jsonObject =  manager.getContVisitasPendientes(codigoProceso);
            
            
            //Armamos la respuesta JSON y la enviamos
            response.setContentType("application/json");
            response.getWriter().write(jsonObject.toString());
            
           }catch(Exception e){
           }
            
    }
    
    
}

