/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.UsuariosDelegates;

import configuracion.Queries;
import java.sql.Connection;
import java.sql.ResultSet;
import modelo.DbManager;

/**
 *
 * @author illustrato
 */
public class SeleccionarUsuarios {


        //Atributos
        private DbManager db;
        private Connection conn;

        /**
         * 
         * Constructor
         * 
         */
        public SeleccionarUsuarios() {
            db = new DbManager();
            conn = db.conectar();
        }


           public ResultSet getUsuarios(String usuario, String rol, String codigo){

            String query = Queries.getString("SelUsuarios"); 
            
            if(!"".equals(usuario) && usuario != null ){
                query +=  " AND USR.VAR_USUARIO = " + usuario;
            }
            
            if(!"".equals(rol) && rol != null ){
                query +=  " AND USR.FK_ROL = " + rol;
            }
            
            if(!"".equals(codigo) && codigo != null ){
                query +=  " AND USR.PK_CODIGO = " + codigo;
            }

            query += " ORDER BY USR.VAR_USUARIO  ASC";
            ResultSet rset = db.ejecutar(conn, query);

            return rset;

        }


  public void desconectar( )
  {
        db.desconectar(this.conn);
  }
        

}
