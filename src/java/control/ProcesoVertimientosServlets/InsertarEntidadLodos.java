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

@WebServlet(name = "InsertarEntidadLodos", urlPatterns = {"/InsertarEntidadLodos"})
public class InsertarEntidadLodos extends HttpServlet {



    
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
            String nombreEmpresa = request.getParameter("nombreEmpresaLodos");
            
            boolean bolrecolecta = Boolean.valueOf(request.getParameter("recolectaLodos"));
            String recolecta = "NO";
            if(bolrecolecta == true) recolecta = "SI";
            
            String volumen = request.getParameter("volumenLodos");
            String fechaRecoleccion = request.getParameter("recoleccionLodos");
            String frecuencia = request.getParameter("frecuenciaLodos");
            
            boolean bolTransporte = Boolean.valueOf(request.getParameter("transporteLodos"));
            String transporte = "NO";
            if(bolTransporte == true) transporte = "SI";
            
            boolean bolDispone = Boolean.valueOf(request.getParameter("disponeLodos"));
            String dispone = "NO";
            if(bolDispone == true) dispone = "SI";
            
            String sitioDispone = request.getParameter("sitioDispoLodos");
            String codigo = request.getParameter("codigoProceso");
            
            ManejoLodos manager = new ManejoLodos();
            manager.insertarEntidadLodos(nombreEmpresa, recolecta, volumen, fechaRecoleccion, frecuencia, transporte, dispone, sitioDispone, codigo);
            
        } catch (Exception ex) {
            //Logger.getLogger(InsertarActEconomica.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    //-----------------------------------------------------------------------------


}
