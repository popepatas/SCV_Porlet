/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.AutenticacionManager;

import configuracion.ActiveDirectory;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.AutenticacionDelegate.Ldap;
import org.apache.commons.codec.binary.Base64;

 



/**
 *
 * @author illustrato
 */
public class Autenticacion {
    
    
      public boolean validar(String usuario, String password){

          //#DESCOMENTAR_SGC boolean val =  Ldap.autenticar(usuario,password);
           boolean val = true;
           return val;           
      }
      
       public  void doLogIn(String login,  HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
           
        String rol = "";      
        String ip = request.getRemoteAddr();      
        HttpSession sesion = request.getSession();
        
        variablesSession(sesion, response, login, rol, ip);
        //Tiempo en que expira la sesion
        sesion.setMaxInactiveInterval(Integer.parseInt(ActiveDirectory.getString("sesionTimeOut")));
    }
       
       
       private static void variablesSession(HttpSession sesion, HttpServletResponse response, String login, String rol, 
            String ip) {

        //public static void variablesSession(HttpSession sesion, String login, String ip, String rol, String persona, String codigoPersona) {
        sesion.setAttribute("login", login);
        sesion.setAttribute("ip", ip);   
        
        Cookie ssocookie = new Cookie("login", encode(login));
        ssocookie.setPath("/");
        response.addCookie(ssocookie);

        ssocookie = new Cookie("ip", encode(ip));
        ssocookie.setPath("/");
        response.addCookie(ssocookie);

        /*ssocookie = new Cookie("rol", encode(rol));
        ssocookie.setPath("/");
        response.addCookie(ssocookie);*/

    }
       
    private static String encode(String sb) {
        return Base64.encodeBase64String(sb.getBytes());
    }
   
}
