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
import modelo.ProcesoVertimientosManagers.*;
import org.json.simple.JSONArray;

@WebServlet(name = "InsertarVisita", urlPatterns = {"/InsertarVisita"})
public class InsertarVisita extends HttpServlet {



    
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
       
        try {
            
            //Obtenemos los datos enviados.
            int tecnico = Integer.parseInt(request.getParameter("tecnicos"));
            int motivo = Integer.parseInt(request.getParameter("motivos"));
            int proceso =  Integer.parseInt(request.getParameter("proceso"));
            int tipoVisita =  Integer.parseInt(request.getParameter("tipoVisita"));
            String observaciones = request.getParameter("observaciones");
            String fechaVisita = request.getParameter("fechaVisita");
           
            
            JSONArray resp = new JSONArray();
            
            //Creamos el manager para registrar la informacion
            Visitas manager = new Visitas();
            resp = manager.insertar(tecnico, motivo, observaciones, fechaVisita, proceso, tipoVisita);
            
            response.setContentType("application/json");
            for(Object jsonObject : resp){
                
                    response.getWriter().write(resp.toString());
                
            }
            
            
            
        } catch (Exception ex) {
            //Logger.getLogger(InsertarActEconomica.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    //-----------------------------------------------------------------------------


}
