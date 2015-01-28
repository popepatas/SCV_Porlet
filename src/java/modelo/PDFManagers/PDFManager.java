/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.PDFManagers;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import modelo.ApiManager;
import modelo.ClientesDelegates.SeleccionarCliente;
import modelo.PDFDelegates.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
/**
 *
 * @author Nadesico
 */
public class PDFManager {
    
    
    public String pdfDevolucion(int codigo, String html) throws SQLException{
        SeleccionarPDF select = new SeleccionarPDF();
        ResultSet rset = select.getCliente(codigo);
        while(rset.next()){
            html = html.replace("::NombreResponsable::",rset.getString("NOMBRE_REPRESENTANTE_LEGAL"));
            html = html.replace("::Cliente::", rset.getString("RAZON_SOCIAL"));
            html = html.replace("::Direccion::", rset.getString("DIRECCION"));
            html = html.replace("::Year::", obtenerAño());
        }
        select.desconectar();
        return html;
    }
    
    public String pdfNotificacion(String html, String codigo) throws SQLException{
        
        html = html.replace("::Year::", obtenerAño());
        html = html.replace("::Month::", obtenerMes());
        
        SeleccionarPDF select = new SeleccionarPDF();
        ResultSet rset = select.getClienteByCodigo(codigo);
        
        while(rset.next()){
            html = html.replace("::Cliente::", rset.getString("RAZON_SOCIAL"));
        }
        
        html += "          <br></br>\n" +
                    "          <br></br>\n" +
                    "          <br></br>\n" +
                    "          <br></br>\n" +
                    "          <br></br>\n" +
                    "          <br></br>\n" +
                    "          <br></br>\n" +
                    "          <br></br>\n" +
                    "          <br></br>\n" +
                    "          <br></br>\n" +
                    "          <br></br>\n" +
                    "          <br></br>\n" +
                    "          <br></br>\n" +
                    "          <br></br>";
        select.desconectar();
        return html;
    }
    
    
    public String getPdfIncumplimiento(int codigo, String html) throws SQLException{
        
        SeleccionarPDF select = new SeleccionarPDF();
        ResultSet rset = select.getDescPuntoVertimiento(codigo);
        String cabecera1 = "<table class=\"parametros\">"
                            + "<thead><tr>\n" +
                    "          <th width=\"153\">PUNTO</th>\n" +
                    "          <th width=\"144\" >PARAMETRO</th>\n" +
                    "          <th width=\"181\" >VALOR REPORTADO</th>\n" +
                    "          <th width=\"206\">VALOR LIMITE NORMA</th>\n" +
                    "        </tr></thead><tbody>";
        String footer = "</tbody></table><br></br>";
        
        String laboratorio = "";
        String consultor   = "";
        String labcon ="";
        
        String descripcionPunto = "";
        String tablaFinal = "";
        String tabla = "";
        String cabecera2 = ""; 
        

         
        
        while(rset.next()){
            
            String codigoPuntoVert = rset.getString("CODIGO");
            laboratorio = rset.getString("LAB");
            consultor   = rset.getString("CNSTR") ;
            ResultSet rset2 = select.getParametrosNoCumplidos(codigoPuntoVert);
            ResultSet rset3 = select.getParametrosNoCumplidos(codigoPuntoVert);
            String cuerpo = "";
            int contador = 0;
            int totalParam = 0;
            descripcionPunto = rset.getString("DESCRIPCION");
            cabecera2 = "<tr>"; 
            
            while(rset3.next()){                
                             
                totalParam++;
            }              
            
             while(rset2.next()){ 
                 
                cuerpo += "<td width=\"144\">"+rset2.getString("PARAMETRO") +"</td>";
                cuerpo += "<td width=\"181\">"+rset2.getDouble("VALOR") +" "+ApiManager.convCharUnids(rset2.getString("UNIDAD")) +"</td>";
                cuerpo += "<td width=\"206\" >"+rset2.getDouble("RANGO_INICIAL")+" - "+rset2.getDouble("RANGO_FINAL")+" "+ApiManager.convCharUnids(rset2.getString("UNIDAD")) +"</td>";                
                cuerpo += "</tr>";
                
                if(contador==0){
                    cabecera2 = "<tr><td width=\"153\" rowspan=\""+totalParam+"\">"+descripcionPunto+"</td>";
                }else{
                    cabecera2 = "<tr>";
                }                
                contador++;
                
                tabla+= cabecera2 + cuerpo ;
                cuerpo = "";
            }    
                        
        }
        
         tablaFinal = cabecera1+ tabla + footer;
         
            if(consultor.length() == 1){
                
                 labcon = "el laboratorio " + laboratorio;
                 

            }else{
                 labcon = "el Consultor(a) "+consultor+" en conjunto con el laboratorio "+ laboratorio;
            }
         
            
         String html2 = html.replace("::Parametros::",tablaFinal);
         html2 = html2.replace("::Laboratorio::",labcon);
         html2 = html2.replace("::Mes::",obtenerMes());
         html2 = html2.replace("::Dia::",obtenerDia());
         html2 = html2.replace("::Year::",obtenerAño());
         html2 = html2.replace("::YearProceso::",obtenerAñoProceso(String.valueOf(codigo)));
                         
        ResultSet rset2 = select.getCliente(codigo);
        while(rset2.next()){
            html2 = html2.replace("::Cliente::", rset2.getString("RAZON_SOCIAL"));
        }
                
        select.desconectar();
        return html2;
    }
    
    private String obtenerAño(){
        return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    }
    
    private String obtenerDia(){
        return String.valueOf(Calendar.getInstance().get(Calendar.DATE));
    }
    private String obtenerMes(){
        int month = Calendar.getInstance().get(Calendar.MONTH);
        String mes = "";
        
        switch(month){
            case 0: mes = "Enero";
                break;
            case 1: mes = "Febrero";
                break;
            case 2: mes = "Marzo";
                break;
            case 3: mes = "Abril";
                break;
            case 4: mes = "Mayo";
                break;
            case 5: mes = "Junio";
                break;
            case 6: mes = "Julio";
                break;
            case 7: mes = "Agosto";
                break;
            case 8: mes = "Septiembre";
                break;
            case 9: mes = "Octubre";
                break;
            case 10: mes = "Noviembre";
                break;
            case 11: mes = "Diciembre";
                break;
            
            
        }
        return mes; 
    }
    
    private String obtenerAñoProceso(String codigoProceso) throws SQLException{
        SeleccionarPDF select = new SeleccionarPDF();
        ResultSet rset = select.obtenerAñoProceso(codigoProceso);
        
        String año = "";
        
        while(rset.next()){
            año = rset.getString("AÑO");
        }
        
        return año;
    }
    
    public JSONArray contarParamIncumplidos(String codigoProceso) throws Exception{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarPDF select = new SeleccionarPDF();
        ResultSet rset = select.contarParamIncumplidos(codigoProceso);
                
        //Creamos los JSONArray para guardar los objetos JSON
        JSONArray jsonArray = new JSONArray();

        //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos
        //en el JSONArray.
        JSONObject jsonObject = new JSONObject();
        while(rset.next()){
            
            //Armamos el objeto JSON con la informacion del registro
            
            jsonObject.put("cantidad", rset.getString("CANTIDAD"));
            
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            
            
        }
        
        jsonArray.add(jsonObject.clone());
        
        //Se cierra el ResultSet
        select.desconectar();
        
              
        return jsonArray;
        
    }
    
        public JSONArray contarParam(String codigoProceso) throws Exception{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarPDF select = new SeleccionarPDF();
        ResultSet rset = select.contarParam(codigoProceso);
                
        //Creamos los JSONArray para guardar los objetos JSON
        JSONArray jsonArray = new JSONArray();

        //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos
        //en el JSONArray.
        JSONObject jsonObject = new JSONObject();
        while(rset.next()){
            
            //Armamos el objeto JSON con la informacion del registro
            
            jsonObject.put("cantidad", rset.getString("CANTIDAD"));
            
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            
            
        }
        
        jsonArray.add(jsonObject.clone());
        
        //Se cierra el ResultSet
        select.desconectar();
        
              
        return jsonArray;
        
    }          
}
