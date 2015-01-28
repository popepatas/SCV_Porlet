package control.DashboardServlets;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.DashboardManagers.DashboardManager;
import org.json.simple.JSONArray;

/**
 *
 * @author jmrincon
 */
@WebServlet(name = "ConsultarDashboard1", urlPatterns = {"/ConsultarDashboard1"})
public class ConsultarDashboard1 extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //Obtenemos los parametros
            String codigoCliente = request.getParameter("codigoCliente");
            String rangoMetrosInicial = request.getParameter("rangoInicial");
            String rangoMetrosFinal = request.getParameter("rangoFinal");
            //Obtenemos La informacion del manager
            DashboardManager manager = new DashboardManager();
            JSONArray jsonArray = manager.getDashboard1(codigoCliente, rangoMetrosInicial, rangoMetrosFinal);
            String json = null;
            //Armamos la respuesta JSON y la enviamos
            response.setContentType("application/json");
            for(Object jsonObject : jsonArray){
                json = jsonObject.toString();
                response.getWriter().write(jsonObject.toString());
            }
        } catch (Exception ex) {
            Logger.getLogger(ConsultarDashboard1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}