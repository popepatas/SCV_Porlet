/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import modelo.ParametrizacionManagers.*;
import modelo.ProcesoVertimientosManagers.InformacionTecnica;
/**
 *
 * @author Nadesico
 */
@WebServlet(name = "InsertarInformacionTecnica", urlPatterns = {"/InsertarInformacionTecnica"})
public class InsertarInformacionTecnica extends HttpServlet {


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        doPost(request, response);
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
        try {  

            //Obtenemos JSON
            String puntos = request.getParameter("puntos");
            InformacionTecnica infoTec = new InformacionTecnica();
        

            Object obj = JSONValue.parse(puntos);
            JSONArray jsonArray = new JSONArray();
            jsonArray = (JSONArray) obj;
            int tamaño= jsonArray.size();     
            
            //Recorremos el JSONArray y obtenemos la informacion.
            for(int i = 0; i < jsonArray.size(); i ++){
                
                //Obtenemos la info de los puntos
                JSONObject jsonObject = (JSONObject)jsonArray.get(i);
                String fechaMonitoreo = (String)jsonObject.get("fechaMonitoreo");
                String jornadaProductivaDia = (String)jsonObject.get("jordanadaProductivaDia");
                String jornadaProductivaHora = (String)jsonObject.get("jordanadaProductivaHoras");
                String jornadaProductivaObservacion = (String)jsonObject.get("jordanadaProductivaObsev");
                String codigoPunto = (String)jsonObject.get("codigoEvalPuntos");
                          
                //Guardamos los datos del punto.
                int codigoMonitoreo = infoTec.registrarInfoTecPuntoMonitoreo(fechaMonitoreo, jornadaProductivaDia, jornadaProductivaHora, jornadaProductivaObservacion, codigoPunto);
                String codigoMonitoreo2 = String.valueOf(codigoMonitoreo);
                
                //Obtenemos las jornadas
                JSONArray jsonArray2 = (JSONArray)jsonObject.get("jornadas");
                int tamaño2= jsonArray2.size();
                //Recorremos las jornadas.
                for (int j = 0; j < jsonArray2.size(); j++) {
                    
                    JSONObject jsonObject2 = (JSONObject)jsonArray2.get(j);
                    String nombreJornada = String.valueOf(j+1);
                    String cargaDBO = (String)jsonObject2.get("cargaDBO");
                    String cargaSST = (String)jsonObject2.get("CargaSST");
                    String horaInicio = (String)jsonObject2.get("horaInicio");
                    String horaFin = (String)jsonObject2.get("horaFin");
                    String caudalJornada = (String)jsonObject2.get("caudalJornada");
                    
                    //Guardamos los datos de la jornada
                    int codigoJornadaPunto = infoTec.registrarInfoTecJornada(nombreJornada, cargaDBO, cargaSST, horaInicio, horaFin, codigoMonitoreo2, caudalJornada);
                    String codigoJornadaPunto2 = String.valueOf(codigoJornadaPunto);
                            
                    //Guardamos los parametros
                    JSONArray jsonArray3 = (JSONArray)jsonObject2.get("tabla");
                    int tamaño3= jsonArray3.size();
                    //Recorremos los parametros
                    for (int k = 0; k < jsonArray3.size(); k++) {
                        
                        JSONObject jsonObject3 = (JSONObject)jsonArray3.get(k);
                        String codigoParametro = (String)jsonObject3.get("codigoParametro");
                        String valorInforme = (String)jsonObject3.get("valorInforme");
                        String observacion = (String)jsonObject3.get("observacion"); 
                        String cumple = (String)jsonObject3.get("cumple"); 
                        String indicadorMenor = (String)jsonObject3.get("indicardorMenor"); 
                        
                        //Guardamos los parametros
                        infoTec.registrarInfoTecDetallesJornada(codigoPunto, codigoParametro, valorInforme, observacion, cumple, codigoJornadaPunto2,indicadorMenor);
                        
                    }
                }
            }
            
            } catch (Exception ex) {
                Logger.getLogger(InsertarInformacionTecnica.class.getName()).log(Level.SEVERE, null, ex);
            }


        
    }


}
