package control.ReportesServlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.ApiManager;
import modelo.ReportesManagers.ReportesManager;
import org.json.simple.JSONArray;

/**
 *
 * @author Marcos
 */
@WebServlet(name = "ConsultarReporte7", urlPatterns = {"/ConsultarReporte7"})
public class ConsultarReporte7 extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //Obtenemos los parametros
            String fechaInicial = request.getParameter("fechaInicial");
            String fechaFinal = request.getParameter("fechaFinal");
            String estadoProceso = null;
            String numeroContrato = request.getParameter("numeroContrato");
            String nit = request.getParameter("nit");
            String actividadProductiva = request.getParameter("actividadProductiva");
            String razonSocial = null;
            String comuna = null;
            String laboratorio = null;
            String usoServicio = request.getParameter("usoServicio");
            String tipoInforme = null;
            
            String lodoInicial = request.getParameter("lodoInicial");
            String lodoFinal = request.getParameter("lodoFinal");
            String tasaInicial = request.getParameter("tasaInicial");
            String tasaFinal = request.getParameter("tasaFinal");
            
            int take = ApiManager.numeroNull(request.getParameter("take"));
            int skip = ApiManager.numeroNull(request.getParameter("skip"));
            Integer filaInicio = skip + 1;
            Integer filaFin= take + skip;
            
            //Obtenemos La informacion del manager
            ReportesManager manager = new ReportesManager();
            JSONArray jsonArray = manager.getReporte7(fechaInicial, fechaFinal, numeroContrato, 
                nit, actividadProductiva, usoServicio, lodoInicial, lodoFinal, 
                tasaInicial, tasaFinal, filaInicio.toString(), filaFin.toString());
            String json = null;
            //Armamos la respuesta JSON y la enviamos
            response.setContentType("application/json");
            for(Object jsonObject : jsonArray){
                json = jsonObject.toString();
                response.getWriter().write(jsonObject.toString());
            }
        } catch (Exception ex) {
            //Logger.getLogger(Reporte5Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}