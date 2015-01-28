/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control.ProcesoVertimientosServlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.ParametrizacionManagers.UnidadesMedida;
import modelo.ProcesoVertimientosManagers.InformacionGeneral;
import org.json.simple.JSONArray;

/**
 *
 * @author illustrato
 */
@WebServlet(name = "SeleccionarInformacionGeneral", urlPatterns = {"/SeleccionarInformacionGeneral"})
public class SeleccionarInformacionGeneral extends HttpServlet {

    
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
            doPost( request,  response);
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
           response.setContentType("charset=UTF-8");
            //Obtenemos la opcion
            int opcion = Integer.parseInt(request.getParameter("opcion"));

            switch(opcion){

                //Obtenemos todas las actividades economicas
                case 1:{
                    getInformacionesGenerales(request, response);
                }
                    break;

                //Obtenemos una actividad economica especifica.    
                case 2:{
                    getInformacionGeneral(request, response);
                }
                    break;
            }
    }

        private void  getInformacionesGenerales(HttpServletRequest request, HttpServletResponse response){


        }
        private void getInformacionGeneral(HttpServletRequest request, HttpServletResponse response){

              try {
                    int codigoProceso = Integer.parseInt(request.getParameter("codigoProceso"));
                    //Obtenemos La informacion del manager
                    InformacionGeneral manager = new InformacionGeneral();
                    JSONArray jsonArray = manager.getInformacionGeneral(codigoProceso);

                    //Armamos la respuesta JSON y la enviamos
                    response.setContentType("application/json");
                    for(Object jsonObject : jsonArray){

                            response.getWriter().write(jsonObject.toString());

                    }

                } catch (Exception ex) {
                    //Logger.getLogger(SeleccionarActEconomica.class.getName()).log(Level.SEVERE, null, ex);
                }
        }

}
