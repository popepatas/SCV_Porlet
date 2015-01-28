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
import modelo.ApiManager;
import modelo.ProcesoVertimientosManagers.*;
import org.json.simple.JSONArray;
import modelo.ParametrizacionManagers.*;

/**
 *
 * @author Galatea
 */
@WebServlet(name = "SeleccionarMonitoreos", urlPatterns = {"/SeleccionarMonitoreos"})
public class SeleccionarMonitoreos extends HttpServlet {


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
        
        //Obtenemos la opcion
        int opcion = Integer.parseInt(request.getParameter("opcion"));
        
        switch(opcion){
            
            //Obtenemos todas las actividades economicas
            case 1:{
                getMonitoreos(request, response);
            }
                break;
                
            //Obtenemos una actividad economica especifica.    
            case 2:{
                getMonitoreo(request, response);
            }
                break;
            
             case 3:{
                getMonitoreosAdmon(request, response);
            }
                break;
        }
        
    }
    
    
    
    
    
        /**
     * 
     * Llama al manager y obtiene la informacion de todas las
     * Actividades Economicas
     * 
     * @param request
     * @param response 
     */
    private void getMonitoreos(HttpServletRequest request, HttpServletResponse response){
        
        try {
            String fechaInicial = request.getParameter("fechaInicialMonitoreo");
            String fechaFinal = request.getParameter("fechaFinalMonitoreo");
            int codigoProceso = Integer.parseInt(request.getParameter("codigoProceso"));
            
            //Obtenemos La informacion del manager
            ProgramarMonitoreo manager = new ProgramarMonitoreo();
            JSONArray jsonArray = manager.getMonitoreos(fechaInicial, fechaFinal, codigoProceso);

            //Armamos la respuesta JSON y la enviamos
            response.setContentType("application/json");
            for(Object jsonObject : jsonArray){

                    response.getWriter().write(jsonObject.toString());

            }

            
        } catch (Exception ex) {
            //Logger.getLogger(SeleccionarActEconomica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //-----------------------------------------------------------------------------
    
    
        private void getMonitoreosAdmon(HttpServletRequest request, HttpServletResponse response){
        
        try {
            String fechaInicial = request.getParameter("fechaInicialMonitoreo");
            String fechaFinal = request.getParameter("fechaFinalMonitoreo");
            String contrato = request.getParameter("contrato");
            String nit = request.getParameter("nit");
            String razonSocial = request.getParameter("razonSocial");
            String comuna = request.getParameter("comuna");
            String codigoProceso = request.getParameter("codigoProceso");
            String direccion = request.getParameter("direccion");
            String estado = request.getParameter("estado");
            String codigoMonitoreo = request.getParameter("codigo");
            
              //Este parametro es enviado automaticamente por Kendoui
            int take = ApiManager.numeroNull(request.getParameter("take"));
            //Este parametro es enviado automaticamente por Kendoui
            int skip = ApiManager.numeroNull(request.getParameter("skip"));
            Integer filaInicio = skip + 1;
            Integer filaFin= take + skip;
               
              
            
            
            //Obtenemos La informacion del manager
            ProgramarMonitoreo manager = new ProgramarMonitoreo();
            JSONArray jsonArray = manager.getMonitoreosAdmon(filaInicio.toString(), filaFin.toString(), fechaInicial, fechaFinal, codigoProceso, contrato, nit,razonSocial, comuna, direccion,estado, codigoMonitoreo);

            //Armamos la respuesta JSON y la enviamos
            response.setContentType("application/json");
            for(Object jsonObject : jsonArray){

                    response.getWriter().write(jsonObject.toString());

            }

            
        } catch (Exception ex) {
            //Logger.getLogger(SeleccionarActEconomica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //-----------------------------------------------------------------------------
    
    
    
    /**
     * 
     * Llama al manager y obtiene la informacion de una actividad
     * economica especifica.
     * 
     * @param request
     * @param response 
     */
    private void getMonitoreo(HttpServletRequest request, HttpServletResponse response){
    
        try {

            //Obtenemos los parametros
            int codigo = Integer.parseInt(request.getParameter("codigo"));
            
            //Obtenemos La informacion del manager
            ProgramarMonitoreo manager = new ProgramarMonitoreo();
            JSONArray jsonArray = manager.getMonitoreo(codigo);

            //Armamos la respuesta JSON y la enviamos
            response.setContentType("application/json");
            for(Object jsonObject : jsonArray){

                    response.getWriter().write(jsonObject.toString());

            }

            
        } catch (Exception ex) {
            //Logger.getLogger(SeleccionarActEconomica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //-----------------------------------------------------------------------------


}
