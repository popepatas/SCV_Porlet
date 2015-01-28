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
import modelo.ParametrizacionManagers.UnidadesMedida;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author illustrato
 */
@WebServlet(name = "EliminarAsociacionContrato", urlPatterns = {"/EliminarAsociacionContrato"})
public class EliminarAsociacionContrato extends HttpServlet {

    
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
    //-----------------------------------------------------------------------------
    
    
    
    
    
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
        
                   
            //Obtenemos los paramtros enviados
            int opcion =  Integer.parseInt(request.getParameter("opcion"));
            switch(opcion){
                     
           
                    //Obtenemos todas las actividades economicas
                    case 1:{
                        
                        eliminarGrupoContratosAsociados(request,response);
                       
                    }
                        break;

                    //Obtenemos una actividad economica especifica.    
                    case 2:{
                       eliminarContratosAsociados(request,response);
                    }
                        break;
                }
          
        
    }
    //-----------------------------------------------------------------------------


    private void eliminarGrupoContratosAsociados(HttpServletRequest request, HttpServletResponse response){
        
        try{
            //Obtenemos los paramtros enviados
            Double codigo =  Double.parseDouble(request.getParameter("contrato"));
                  
            JSONArray respError = new JSONArray(); // uno significa que no hay error
                      
            //Obtenemos La informacion del manager
            AsociacionContratos manager = new AsociacionContratos();    
            
            //Almacenamos el error que pueda resultar
            respError =  manager.Eliminar(codigo);
            
            //Armamos la respuesta JSON y la enviamos
            response.setContentType("application/json");
            
            for(Object jsonObject : respError){
                
                    response.getWriter().write(respError.toString());
                
            }
            
        }catch(Exception e){
            
        }
    }
    
     private void eliminarContratosAsociados(HttpServletRequest request, HttpServletResponse response) throws IOException{
           JSONArray respError = new JSONArray();
           
        try{
            //Obtenemos los paramtros enviados
            int codigo =  Integer.parseInt(request.getParameter("codigo"));
                  
            // uno significa que no hay error
                      
            //Obtenemos La informacion del manager
            AsociacionContratos manager = new AsociacionContratos();    
            
            //Almacenamos el error que pueda resultar
            respError =  manager.EliminarContratoAsociado(codigo);
            
            //Armamos la respuesta JSON y la enviamos
            response.setContentType("application/json");
            
            for(Object jsonObject : respError){
                
                    response.getWriter().write(respError.toString());
                
            }
            
        }catch(Exception e){
            
            JSONObject error = new JSONObject();
            error.put("error",0);
            respError.add(error);
             //Armamos la respuesta JSON y la enviamos
            response.setContentType("application/json");
            
            for(Object jsonObject : respError){
                
                    response.getWriter().write(respError.toString());
                
            }
        }
     }
    
    
}
