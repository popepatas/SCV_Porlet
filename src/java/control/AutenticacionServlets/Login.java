/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control.AutenticacionServlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.AutenticacionManager.Autenticacion;


/**
 *
 * @author illustrato
 */
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

    
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
        request.setCharacterEncoding("UTF-8");
        doCheckCredentials(request, response);
    }
    
      public  void doCheckCredentials(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        boolean autentica = false;
        
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        /*
         * login = new String( login.getBytes("ISO8859-1"), "ISO8859-1");
         * password = new String( password.getBytes("ISO8859-1"), "ISO8859-1");
         */
        if (login != null && password != null) {
            autentica = autenticaUsuario(login, password, request, response);
            if (autentica) {
                response.sendRedirect("/SCV_Portlet/index.jsp");
            }else {// autentica es false
                response.sendRedirect("/SCV_Portlet/errorLogin.jsp");
            }
        } else { //login o password son nulos
                response.sendRedirect("/SCV_Portlet/errorLogin.jsp");
        }
    }

     public  boolean autenticaUsuario(String login, String password, HttpServletRequest request, HttpServletResponse response) {
       
         boolean autentica = false;
       Autenticacion autenticacion = new Autenticacion(); 
       
        if (login != null && password != null) {
            try {
                
                autentica = autenticacion.validar(login,password);
                
                if (autentica) {
                    autenticacion.doLogIn(login, request, response);
                }
                
            } catch (Exception sqlex) {
                System.out.println("ERROR en login.autenticaUsuario() " + sqlex);
            }
        }
        return autentica;
    }


}
