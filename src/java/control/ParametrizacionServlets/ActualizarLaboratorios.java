/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control.ParametrizacionServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.ParametrizacionManagers.AcreditacionParametros;
import modelo.ParametrizacionManagers.Laboratorios;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author illustrato
 */
@WebServlet(name = "ActualizarLaboratorios", urlPatterns = {"/ActualizarLaboratorios"})
public class ActualizarLaboratorios extends HttpServlet {
    private Reader paramAcreditados;

    
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
        try {
                   String nombre = request.getParameter("nombre");                   
                   String direccion = request.getParameter("direccion");
                   String telefono = request.getParameter("telefono1");
                   String telefono2 = request.getParameter("telefono2");                   
                   String correo = request.getParameter("correo");
                   String resolucion = request.getParameter("resolucion");
                   String vigencia = request.getParameter("vigencia");
                   String contactos = request.getParameter("contactos");
                   Integer codigo = Integer.parseInt(request.getParameter("codigo"));  
                   String paramAcreditados = request.getParameter("paramAcreditados");
                                      
            Laboratorios manager = new Laboratorios();            
            manager.actualizar(nombre, contactos, direccion, telefono, telefono2, correo, resolucion, vigencia, codigo);
            
            int resp = 0;
            //Obtenemos La informacion del manager
            AcreditacionParametros managerAcreditacion = new AcreditacionParametros();
             
            //Eliminamos lo parametros
            resp =  managerAcreditacion.eliminar(codigo);            

            Object obj = JSONValue.parse(paramAcreditados);
            JSONArray jsonArray = new JSONArray();
            jsonArray = (JSONArray) obj;

            //Recorremos el JSONArray y obtenemos la informacion.
            for(int i = 0; i < jsonArray.size(); i ++){

                JSONObject jsonObject = (JSONObject)jsonArray.get(i);                
                int codParametro = Integer.parseInt((String)jsonObject.get("codigoParam"));                

                managerAcreditacion.insertar(codParametro, codigo);

            }

            
        } catch (Exception e) {
            
        }
        
        
    }

 

}
