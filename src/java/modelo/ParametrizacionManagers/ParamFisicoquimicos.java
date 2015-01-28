package modelo.ParametrizacionManagers;

import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.ParametrizacionDelegates.*;
import org.json.simple.*;

public class ParamFisicoquimicos {


    
    /**
     * 
     * Llama al delegate para insertar un parametro fisicoquimicos
     * 
     * @param descripcion
     * @param tipoParametro
     * @param unidadMedida
     * @throws Exception 
     */
    public int insertar(String descripcion, int tipoParametro, int unidadMedida) throws Exception{
               
        InsertarPrmfisicoquimico insert = new InsertarPrmfisicoquimico(descripcion, tipoParametro, unidadMedida);
        insert.ejecutar();
        int salida = insert.getSalida();
        
        return salida;
        
            
    }
    //-----------------------------------------------------------------------------
    
    
    
    
    /**
     * 
     * Llama al delegate para actualizar un parametro fisicoquimico
     * 
     * @param codigo
     * @param descripcion
     * @param tipoParametro
     * @param unidadMedida
     * @throws Exception 
     */
    public void actualizar(int codigo, String descripcion, int tipoParametro, int unidadMedida) throws Exception{
        
        ActualizarPrmfisicoquimico update = new ActualizarPrmfisicoquimico(descripcion, tipoParametro, unidadMedida, codigo);
        update.ejecutar();
        
    }
    //-----------------------------------------------------------------------------
    
    
    /**
     * 
     * Llama al delegate para eliminar un parametro fisicoquimico
     * 
     * @param codigo
     * @return
     * @throws Exception 
     */
    public JSONArray Eliminar(int codigo) throws Exception{
        
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject(); 
        
        Integer respError ;
        
        EliminarPrmfisicoquimicos delete = new EliminarPrmfisicoquimicos(codigo);
        delete.ejecutar();
        
        respError = delete.getError();
        
        jsonObject.put("error", respError);
        
        jsonArray.add(jsonObject);
        
        return jsonArray;
        
        
    }
    //-----------------------------------------------------------------------------
    
    
    /**
     * 
     * Llama al Delegate para obtener los parametros fisicoquimicos
     * 
     * @return
     * @throws SQLException 
     */
    public JSONArray getPrmfisicoquimicos(String descripcion, String unidades, String tipoParametro) throws SQLException{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarPrmfisicoquimicos select = new SeleccionarPrmfisicoquimicos();
        ResultSet rset = select.getPrmfisicoquimicos(descripcion, unidades, tipoParametro);
        
        //Creamos los JSONArray para guardar los objetos JSON
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos
        //en el JSONArray.
        while(rset.next()){
            
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("codigo", rset.getString("Codigo"));
            jsonObject.put("descripcion", rset.getString("Descripcion"));
            jsonObject.put("tipoParamFisicoquimico", rset.getString("TiposParamFisicoquimico"));
            jsonObject.put("unidadMedida", rset.getString("UnidadMedida"));
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
            
        }
        select.desconectar();
        jsonArreglo.add(jsonArray);
        
        return jsonArreglo;
        
    }
    //-----------------------------------------------------------------------------
    
    
    
    
     
   /**
    * 
    * Llama al delegeate para obtener un parametro fisicoquimico
    * 
    * @param codigo
    * @return
    * @throws SQLException 
    */
    public JSONArray getPrmfisicoquimico(int codigo) throws SQLException{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarPrmfisicoquimicos select = new SeleccionarPrmfisicoquimicos();
        ResultSet rset = select.getPrmfisicoquimico(codigo);
        
        //Creamos los JSONArray para guardar los objetos JSON
        JSONArray jsonArray = new JSONArray();

        //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos
        //en el JSONArray.
        while(rset.next()){
            
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("codigo", rset.getString("Codigo"));
            jsonObject.put("descripcion", rset.getString("Descripcion"));
            jsonObject.put("tipoParamFisicoquimico", rset.getString("CodigoTipoParm"));
            jsonObject.put("unidadMedida", rset.getString("CodigoUnidad"));
            
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
            
        }
        
        select.desconectar();
        return jsonArray;
        
    }
    //-----------------------------------------------------------------------------
     /**
     * 
     * Llama al Delegate para obtener los parametros fisicoquimicos dependiendo de un Tipo Parametro
     * 
     * @return
     * @throws SQLException 
     */
    public JSONArray getParamFisicoQuimicosXTipoParametro(String tipoParametro) throws SQLException{
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarPrmfisicoquimicos select = new SeleccionarPrmfisicoquimicos();
        ResultSet rset = select.getParamFisicoQuimicosXTipoParametro(tipoParametro);
        
        //Creamos los JSONArray para guardar los objetos JSON
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos
        //en el JSONArray.
        while(rset.next()){
            
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("codigo", rset.getString("Codigo"));
            jsonObject.put("descripcion", rset.getString("Descripcion"));
            jsonObject.put("tipoParamFisicoquimico", rset.getString("TiposParamFisicoquimico"));
            jsonObject.put("unidadMedida", rset.getString("UnidadMedida"));
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
            
        }
        select.desconectar();
        jsonArreglo.add(jsonArray);
        
        return jsonArreglo;
        
    }
    
    /**
     * 
     * Obtiene el ResultSet con la informacion de los parametros fisicoquimicos
     * para mostrar en Informacion Tecnica.
     * 
     * @param codigoCiiu
     * @return
     * @throws Exception 
     */
    public Object[] getParametrosParaInfoTecnica(int puntoMonitoreo, int jornada, int codigoCiiu) throws Exception{
       
        //Obtenemos la informacion de los parametros
        SeleccionarPrmfisicoquimicos select = new SeleccionarPrmfisicoquimicos();
        ResultSet rst = select.getParametrosParaInfoTecnica(puntoMonitoreo, jornada, codigoCiiu);
        
        Object data[] = new Object[2];
        data[0] = rst;
        data[1] = select;
        
        return data;
    }

    
     public String obtenerParamFisicoQuimicosXTipoParametro() throws SQLException{
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarPrmfisicoquimicos select = new SeleccionarPrmfisicoquimicos();
        ResultSet rset = select.getPrmfisicoquimicos(null, null, null);
        //Creamos los JSONArray para guardar los objetos JSON
        String parametros = "";
        //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos
        //en el JSONArray.
        while(rset.next()){
            parametros += "," +  rset.getString("DESCRIPCION");
            
        }
        select.desconectar();
        return parametros;
     }
    
        public Object[] getParametrosParaInfoTecnica(int codigoCiiu) throws Exception{
       
        //Obtenemos la informacion de los parametros
        SeleccionarPrmfisicoquimicos select = new SeleccionarPrmfisicoquimicos();
        ResultSet rst = select.getParametrosParaInfoTecnica(codigoCiiu);
        
        Object data[] = new Object[2];
        data[0] = rst;
        data[1] = select;
        
        return data;
    }
    
}
