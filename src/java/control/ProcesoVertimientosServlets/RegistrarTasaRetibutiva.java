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
@WebServlet(name = "RegistrarTasaRetibutiva", urlPatterns = {"/RegistrarTasaRetibutiva"})
public class RegistrarTasaRetibutiva extends HttpServlet {

 
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
       
        
                   int codigoProceso = Integer.parseInt(request.getParameter("codigoProceso"));
                   String cargasParam = request.getParameter("cargasParam");
                   String valorTasaCobrada = request.getParameter("valorTasaCobrada");
                   String valorTotalTasaPagar =  request.getParameter("valorTotalTasaPagar");
                   
                   
                    Object obj = JSONValue.parse(cargasParam);
                    JSONArray jsonArray = new JSONArray();
                    jsonArray = (JSONArray) obj;
                    
                     for(int i = 0; i < jsonArray.size(); i ++){

                            //Obtenemos los valores por parametro
                            JSONObject jsonObject = (JSONObject)jsonArray.get(i);
                            
                            
                            
                            String valorTarifa = (String)jsonObject.get("valorTarifa");
                            String procentajeRemocion = (String)jsonObject.get("procentajeRemocion");
                            String valorTasa = (String)jsonObject.get("valorTasa");
                            String valorCarga = (String)jsonObject.get("valorCarga");
                            
                            String  codigoParametro = (String)jsonObject.get("codigoParametro");
                       try {     
                            

                            //Creamos el manager y guardamos la informacion.
                            ProgramarMonitoreo manager = new ProgramarMonitoreo();
                       
                            manager.registrarTasaRetributiva(codigoProceso, Integer.parseInt(codigoParametro), valorTarifa, procentajeRemocion, valorTasa, valorCarga,valorTasaCobrada,valorTotalTasaPagar);
                           
                           
                           
                       } catch (Exception ex) {

                       }

                            
                
                     }
                   
                   String codigoParametro ="";
                   String valorTarifa = request.getParameter("codigoProceso");
                   String procentajeRemocion = request.getParameter("codigoProceso");
                   String valorTasa = request.getParameter("codigoProceso");
                   String valorCarga = request.getParameter("codigoProceso");
     
        
    }

  

}
