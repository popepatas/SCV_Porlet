package control.ParametrizacionServlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.ParametrizacionManagers.Estados;
import org.json.simple.JSONArray;

/**
 *
 * @author jmrincon
 */
@WebServlet(name = "SeleccionarEstado", urlPatterns = {"/SeleccionarEstado"})
public class SeleccionarEstado extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int opcion = Integer.parseInt(request.getParameter("opcion"));
        switch(opcion){
            case 1:{
                getEstados(request, response);
                break;
            }
            case 2:{
                getEstado(request, response);
                break;
            }
            case 3:{
                String filtro = String.valueOf(request.getParameter("filtro"));
                getEstadosXFiltro(filtro, request, response);
            }
        }
    }
    
    private void getEstados(HttpServletRequest request, HttpServletResponse response){    
        try {
            String descripcion = request.getParameter("descripcion");
            //Obtenemos La informacion del manager
            Estados manager = new Estados();
            JSONArray jsonArray = manager.getEstados(descripcion);
            //Armamos la respuesta JSON y la enviamos
            response.setContentType("application/json");
            for(Object jsonObject : jsonArray){
                response.getWriter().write(jsonObject.toString());
            }
        } catch (Exception ex) {
            //Logger.getLogger(SeleccionarActEconomica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void getEstado(HttpServletRequest request, HttpServletResponse response){
        try {
            //Obtenemos los parametros
            int codigo = Integer.parseInt(request.getParameter("codigo"));
            //Obtenemos La informacion del manager
            Estados manager = new Estados();
            JSONArray jsonArray = manager.getEstado(codigo);
            //Armamos la respuesta JSON y la enviamos
            response.setContentType("application/json");
            for(Object jsonObject : jsonArray){
                response.getWriter().write(jsonObject.toString());
            }
        } catch (Exception ex) {
            //Logger.getLogger(SeleccionarActEconomica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void getEstadosXFiltro(String filtro, HttpServletRequest request, HttpServletResponse response){
        try {
            //Obtenemos La informacion del manager
            Estados manager = new Estados();
            JSONArray jsonArray = manager.getEstadoXFiltro(filtro);
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