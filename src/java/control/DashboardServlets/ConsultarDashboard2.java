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
import modelo.ReportesManagers.ReportesManager;
import org.json.simple.JSONArray;

/**
 *
 * @author jmrincon
 */
@WebServlet(name = "ConsultarDashboard2", urlPatterns = {"/ConsultarDashboard2"})
public class ConsultarDashboard2 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            //Obtenemos los parametros
            String codigoCliente = request.getParameter("codigoCliente");
            String paramFisicoQuimico = request.getParameter("paramFisicoQuimico");
            String fechaInicio = "01/01/" + request.getParameter("rangoInicial");
            String fechaFin = "01/01/" + request.getParameter("rangoFinal");
            //Obtenemos La informacion del manager
            DashboardManager manager = new DashboardManager();
            JSONArray jsonArray = manager.getDashboard2(codigoCliente, fechaInicio, fechaFin, paramFisicoQuimico);
            String json = null;
            //Armamos la respuesta JSON y la enviamos
            response.setContentType("application/json");
            for(Object jsonObject : jsonArray){
                json = jsonObject.toString();
                response.getWriter().write(jsonObject.toString());
            }
        } catch (Exception ex) {
            Logger.getLogger(ConsultarDashboard2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}