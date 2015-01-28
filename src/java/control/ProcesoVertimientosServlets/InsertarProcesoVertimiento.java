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

@WebServlet(name = "InsertarProcesoVertimiento", urlPatterns = {"/InsertarProcesoVertimiento"})
public class InsertarProcesoVertimiento extends HttpServlet {



    
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
            Double contrato = Double.parseDouble(request.getParameter("contrato"));
            String nit = request.getParameter("nit");
            String fecha = request.getParameter("fechaCreacion");
            String ciclo = request.getParameter("ciclo");         
            String sector = request.getParameter("sector");      
            String pozo = request.getParameter("pozo");
            String consumo = request.getParameter("consumo");
            String direccion = request.getParameter("direccion");
            String usuario_creacion = "userPrueba";
            
            
            
            JSONArray resp = new JSONArray();
            
            //Creamos el manager para registrar la informacion
            ProcesoVertimientos manager = new ProcesoVertimientos();
            resp = manager.insertar(contrato, nit, fecha, ciclo, sector, pozo, consumo, direccion, usuario_creacion);
            
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
