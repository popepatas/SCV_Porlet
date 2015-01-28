package modelo.ParametrizacionManagers;

import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.ParametrizacionDelegates.SeleccionarEstados;
import org.json.simple.*;

public class Estados {
    public JSONArray getEstados(String descripcion) throws SQLException{
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarEstados select = new SeleccionarEstados();
        ResultSet rset = select.getEstados(descripcion);
        //Creamos los JSONArray para guardar los objetos JSON
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos en el JSONArray.
        while(rset.next()){
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("codigo", rset.getString("CODIGO"));
            jsonObject.put("descripcion", rset.getString("DESCRIPCION"));
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
        }
        select.desconectar();
        jsonArreglo.add(jsonArray);
        return jsonArreglo;
    }
    
    public JSONArray getEstado(int codigo) throws SQLException{
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarEstados select = new SeleccionarEstados();
        ResultSet rset = select.getEstado(codigo);
        //Creamos los JSONArray para guardar los objetos JSON
        JSONArray jsonArray = new JSONArray();
        //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos en el JSONArray.
        while(rset.next()){
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("codigo", rset.getString("CODIGO"));
            jsonObject.put("descripcion", rset.getString("DESCRIPCION"));
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
        }
        select.desconectar();
        return jsonArray;
    }
    
    public JSONArray getEstadoXFiltro(String filtro) throws SQLException{
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarEstados select = new SeleccionarEstados();
        ResultSet rset = select.getEstadosXFiltro(filtro);
        //Creamos los JSONArray para guardar los objetos JSON
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos
        //en el JSONArray.
        while(rset.next()){
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("codigo", rset.getString("CODIGO"));
            jsonObject.put("descripcion", rset.getString("DESCRIPCION"));
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
            
        }
        select.desconectar();
        jsonArreglo.add(jsonArray);
        return jsonArreglo;
    }
}