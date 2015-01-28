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
@WebServlet(name = "ConsultarReporte6", urlPatterns = {"/ConsultarReporte6"})
public class ConsultarReporte6 extends HttpServlet {

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
            
            String paramFisicoQuimico = request.getParameter("paramFisicoQuimico");
            
            System.err.println("paramFisicoQuimico: " + paramFisicoQuimico);
            
            Integer totalParametros = Integer.valueOf(request.getParameter("totalParametros"));
            String rangoInicial = "";
            String rangoFinal = "";
            for (int i = 1; i <= totalParametros; i++) {
                rangoInicial += request.getParameter("rangoInicial" + i) + ",";
                rangoFinal += request.getParameter("rangoFinal" + i) + ",";
            }
            
            int take = ApiManager.numeroNull(request.getParameter("take"));
            int skip = ApiManager.numeroNull(request.getParameter("skip"));
            Integer filaInicio = skip + 1;
            Integer filaFin= take + skip;
            
            //Obtenemos La informacion del manager
            ReportesManager manager = new ReportesManager();
            JSONArray jsonArray = manager.getReporte6(fechaInicial, fechaFinal, numeroContrato, 
            nit, actividadProductiva, usoServicio, paramFisicoQuimico, rangoInicial, rangoFinal, filaInicio.toString(), filaFin.toString());
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