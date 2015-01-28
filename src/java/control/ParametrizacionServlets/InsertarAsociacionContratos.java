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
@WebServlet(name = "InsertarAsociacionContratos", urlPatterns = {"/InsertarAsociacionContratos"})
public class InsertarAsociacionContratos extends HttpServlet {

private  JSONObject objectErrores; 
private  JSONArray arrayErrores ;

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
            
             objectErrores = new JSONObject();
             arrayErrores = new JSONArray(); 
             
            //Obtenemos el numero de contrato
            Double contratoPadre = Double.parseDouble(request.getParameter("contrato"));
            
            //Obtenemos la cadena con la informacion y la convertimos en un
            //JSONArray
            String puntos = request.getParameter("contratosAsignados");
            Object obj = JSONValue.parse(puntos);
            JSONArray jsonArray = new JSONArray();
            jsonArray = (JSONArray) obj;
                        
            //Recorremos el JSONArray y obtenemos la informacion.
            for(int i = 0; i < jsonArray.size(); i ++){
                
                //Obtenemos el contrato hijo
                JSONObject jsonObject = (JSONObject)jsonArray.get(i);
                Double contratoHijo = Double.parseDouble((String)jsonObject.get("contratoAsignado"));
                
                
                //Creamos el manager y guardamos la informacion.
                AsociacionContratos manager = new AsociacionContratos();
                int error = manager.insertar(contratoPadre, contratoHijo);
                
                
                /*
                    Obtenemos la respuesta del procedimiento y Validamos si el contrato padre 
                    es valido, si no lo es no registramos nada y enviamos el error.
                */
                if(error == 2){
                    
                    guardarErrores(error, contratoPadre);
                    escribirJSON(response);

                    break;
                        
                }else if(error == 4){ //Si el hijo no es valido, registramos el error y 
                                      //enviamos el listado a la vista al finalizar el for.
                
                    guardarErrores(error, contratoHijo);
                    
                }
                
                
            }
            
            escribirJSON(response);
            
        } catch (Exception ex) {
             JSONObject respError = new JSONObject();
            respError.put("error",0);
            arrayErrores.add(respError);
            escribirJSON(response);
        }
        
        
    }

    
    
    
    /**
     * 
     * Arma un JSON con los contratos que no pudieron ser asociados.
     * 
     * @param error
     * @param contrato
     * @return 
     */
    private void guardarErrores(int error, Double contrato){
        
        
        
        objectErrores.put("contrato", contrato);
        objectErrores.put("error",error);

        arrayErrores.add(objectErrores);
            
    }
    
    
    
    
    /**
     * 
     * Escribe el string de JSON para enviarlo a la vista
     * 
     * @param response
     * @param errores
     * @throws IOException 
     */
    private void escribirJSON(HttpServletResponse response) throws IOException{
       
        JSONArray arrayResp = new JSONArray();
        arrayResp.add(arrayErrores);
        //Armamos la respuesta JSON y la enviamos
        response.setContentType("application/json");
        for(Object json : arrayResp){

                response.getWriter().write(json.toString());

        }
        
    }


}
