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
import modelo.ParametrizacionManagers.Laboratorios;
import modelo.ApiManager;
import modelo.ParametrizacionManagers.AcreditacionParametros;
import modelo.ParametrizacionManagers.PuntosVertimiento;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author illustrato
 */
@WebServlet(name = "InsertarLaboratorios", urlPatterns = {"/InsertarLaboratorios"})
public class InsertarLaboratorios extends HttpServlet {

  
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
        JSONObject salida = new JSONObject();
        try{
                   
                   String nombre = request.getParameter("nombre");                   
                   String direccion = request.getParameter("direccion");
                   String telefono = request.getParameter("telefono1");
                   String telefono2 = request.getParameter("telefono2");                   
                   String correo = request.getParameter("correo");
                   String resolucion = request.getParameter("resolucion");
                   String vigencia = request.getParameter("vigencia");
                   String contactos = request.getParameter("contactos");
                   String paramAcreditados = request.getParameter("paramAcreditados");
                   
                   
                   Laboratorios manager = new Laboratorios();
                   manager.insertar(nombre, contactos, direccion, telefono, telefono2, correo, resolucion, vigencia);
                  
                     
                    AcreditacionParametros managerAcreditacion = new AcreditacionParametros();

                  int codigoLaboratorio =  manager.getCodigoLaboratorio();
                   // la informacion se converte en unJSONArray

                    Object obj = JSONValue.parse(paramAcreditados);
                    JSONArray jsonArray = new JSONArray();
                    jsonArray = (JSONArray) obj;
            
            //Recorremos el JSONArray y obtenemos la informacion.
            for(int i = 0; i < jsonArray.size(); i ++){
                
                JSONObject jsonObject = (JSONObject)jsonArray.get(i);                
                int codParametro = Integer.parseInt((String)jsonObject.get("codigoParam"));                
                
                managerAcreditacion.insertar(codParametro, codigoLaboratorio);
                
            }

                   
        }
        catch(Exception e){
             //Armamos la respuesta JSON y la enviamos
            response.setContentType("application/json");
            salida.put("error",0);
            response.getWriter().write(salida.toString());
        }
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
