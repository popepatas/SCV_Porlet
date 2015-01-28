package modelo.ParametrizacionDelegates;

import java.sql.Connection;
import java.sql.ResultSet;
import modelo.*;


public class SeleccionarEstados {
    private DbManager db;
    private Connection conn;
    
    public SeleccionarEstados() {
        db = new DbManager();
        conn = db.conectar();
    }
    
    public ResultSet getEstados(String descripcion){
        String query = "SELECT * FROM TB_ESTADOS WHERE CODIGO IS NOT NULL";
        if(!("").equals(descripcion) && descripcion != null){
            query += " AND DESCRIPCION LIKE '%" + descripcion + "%'";
        }
        ResultSet rset = db.ejecutar(conn, query);
        return rset;
    }
    
    public ResultSet getEstado(int codigo){
        String query = "SELECT * FROM TB_ESTADOS WHERE CODIGO = " + codigo;
        ResultSet rset = db.ejecutar(conn, query);
        return rset;
    }
    
    public ResultSet getEstadosXFiltro(String filtro){
        String query = "SELECT * FROM TB_ESTADOS WHERE CODIGO IN (" + filtro + ")";
        ResultSet rset = db.ejecutar(conn, query);
        return rset;
    }
    
    public void desconectar(){
        db.desconectar(this.conn);
    }
}