package modelo.ClientesManagers;


import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.ClientesDelegates.*;
import modelo.ClientesDelegates.*;
import modelo.ParametrizacionDelegates.SeleccionarUnidadesMedida;
import org.json.simple.*;

public class ClientesManager {


    
    /**
     * 
     * Llama al Delegate para insertar un cliente
     * 
     * @param nit
     * @param razonSocial
     * @param ciiu
     * @param ciclo
     * @param sector
     * @param pozo
     * @param direccion
     * @param barrio
     * @param comuna
     * @param telefono
     * @param telefono2
     * @param usoServicio
     * @param correo
     * @param correo2
     * @param web
     * @param representanteLegal
     * @param estadoUltVertimiento
     * @throws Exception 
     */
    public Integer insertar(String nit, String razonSocial, String ciiu, String direccion, String barrio, String comuna, 
            String telefono, String telefono2, String usoServicio, String correo, 
            String correo2, String web, String representanteLegal, String estadoUltVertimiento) throws Exception{
                
        InsertarCliente insert = new InsertarCliente(nit, razonSocial, ciiu, direccion, barrio, comuna, telefono, 
                telefono2, usoServicio, correo, correo2, web, representanteLegal, estadoUltVertimiento);
        
        insert.ejecutar();
        return insert.getResultado();
            
    }
    //-----------------------------------------------------------------------------
    
    
    
    
    
    /**
     * 
     * Llama al Delegate para actualizar un cliente
     * 
     * @param nit
     * @param razonSocial
     * @param ciiu
     * @param ciclo
     * @param sector
     * @param pozo
     * @param direccion
     * @param barrio
     * @param comuna
     * @param telefono
     * @param telefono2
     * @param usoServicio
     * @param correo
     * @param correo2
     * @param web
     * @param representanteLegal
     * @param estadoUltVertimiento
     * @param codigo
     * @throws Exception 
     */
    public Integer actualizar(String nit, String razonSocial, String ciiu,
            String direccion, String barrio, String comuna, String telefono, String telefono2, String usoServicio, String correo, 
            String correo2, String web, String representanteLegal, String estadoUltVertimiento, String codigo) throws Exception{
                
        ActualizarCliente update = new ActualizarCliente(nit, razonSocial, ciiu,  direccion, barrio, comuna, telefono, telefono2, usoServicio, 
                correo, correo2, web, representanteLegal, estadoUltVertimiento, codigo);
        update.ejecutar();
        
         return update.getResultado();
            
    }
    //-----------------------------------------------------------------------------
    
    
    
    /**
     * 
     * Llama al delegate para eliminar un cliente
     * 
     * @param codigo
     * @return
     * @throws Exception 
     */
    public JSONArray Eliminar(int codigo) throws Exception{
        
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject(); 
        
        Integer respError ;
        
        EliminarCliente delete = new EliminarCliente(codigo);
        delete.ejecutar();
        
        respError = delete.getError();
        
        jsonObject.put("error", respError);
        
        jsonArray.add(jsonObject);
        
        return jsonArray;
        
        
    }
    //-----------------------------------------------------------------------------
    
    
    /**
     * 
     * Llama al delegate para obtener los clientes.
     * 
     * @param nit
     * @param ciiu
     * @param razonSocial
     * @param direccion
     * @param comuna
     * @param ciclo
     * @param usoServicio
     * @return
     * @throws Exception 
     */
    public JSONArray getClientes(String nit, String ciiu, String razonSocial, String direccion, 
            String comuna, String usoServicio, String barrio) throws Exception{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarCliente select = new SeleccionarCliente(nit, ciiu, razonSocial, direccion, 
            comuna, usoServicio, barrio);
        ResultSet rset = select.ejecutar();
        
        //Creamos los JSONArray para guardar los objetos JSON
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos
        //en el JSONArray.
        while(rset.next()){
            
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            
            jsonObject.put("codigo", rset.getString("CODIGO"));
            jsonObject.put("nit", rset.getString("NIT"));
            jsonObject.put("actividadEconomica", rset.getString("ACTIVIDAD_ECONOMICA"));
            jsonObject.put("razonSocial",rset.getString("RAZON_SOCIAL"));
            jsonObject.put("descripcion",rset.getString("RAZON_SOCIAL"));
            jsonObject.put("direccion", rset.getString("DIRECCION"));
            jsonObject.put("barrio", rset.getString("BARRIO"));
            jsonObject.put("comuna", rset.getString("COMUNA"));
            jsonObject.put("usoServicio", rset.getString("USOSERVICIO"));
            jsonObject.put("codigoCiiu", rset.getString("CODIGO_CIIU"));
            
            
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
            
        }
        
        jsonArreglo.add(jsonArray);
        
        //Se cierra el ResultSet
        select.desconectar();
        
        return jsonArreglo;
        
    }
    //-----------------------------------------------------------------------------
    
    
    
    /**
     * 
     * Llama al Delegate para obtener un cliente especifico
     * 
     * @param codigo
     * @return
     * @throws Exception 
     */
    public JSONArray getCliente(int codigo) throws Exception{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarCliente select = new SeleccionarCliente();
        ResultSet rset = select.getCliente(codigo);
                
        //Creamos los JSONArray para guardar los objetos JSON
        JSONArray jsonArray = new JSONArray();

        //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos
        //en el JSONArray.
        while(rset.next()){
            
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nit", rset.getString("NIT"));
            jsonObject.put("razonSocial", rset.getString("RAZON_SOCIAL"));
            jsonObject.put("actividadEconomica", rset.getString("FK_ACTIVIDAD_ECONOMICA"));
            jsonObject.put("direccion", rset.getString("DIRECCION"));
            jsonObject.put("barrio", rset.getString("BARRIO"));
            jsonObject.put("comuna", rset.getString("COMUNA"));
            jsonObject.put("telefono", rset.getString("TELEFONO"));
            jsonObject.put("telefono2", rset.getString("TELEFONO2"));
            jsonObject.put("usoServicio", rset.getString("FK_USO_SERVICIO"));
            jsonObject.put("email", rset.getString("EMAIL"));
            jsonObject.put("email2", rset.getString("EMAIL_SECUNDARIO"));
            jsonObject.put("repLegal", rset.getString("NOMBRE_REPRESENTANTE_LEGAL"));
            jsonObject.put("estadoVertimiento", rset.getString("FK_ESTADO"));
            jsonObject.put("codigo", rset.getString("CODIGO"));
            jsonObject.put("web", rset.getString("PAGINA_WEB"));

            
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
            
        }
        
        //Se cierra el ResultSet
        select.desconectar();
        
              
        return jsonArray;
        
    }
    //-----------------------------------------------------------------------------
    
        /**
     * 
     * Llama al Delegate para obtener un cliente especifico
     * 
     * @param codigo
     * @return
     * @throws Exception 
     */
    public JSONArray getClienteporNit(String nit) throws Exception{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarCliente select = new SeleccionarCliente();
        ResultSet rset = select.getClienteporNit(nit);
                
        //Creamos los JSONArray para guardar los objetos JSON
        JSONArray jsonArray = new JSONArray();

        //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos
        //en el JSONArray.
        while(rset.next()){
            
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nit", rset.getString("NIT"));
            jsonObject.put("descripcion", rset.getString("RAZON_SOCIAL"));
            jsonObject.put("direccion", rset.getString("DIRECCION"));
     
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
            
        }
        
        //Se cierra el ResultSet
        select.desconectar();
        
              
        return jsonArray;
        
    }
    //-----------------------------------------------------------------------------
    
    
    
    /**
     * 
     * Llama al delegate para obtener un cliente por el contrato
     * 
     * @param contrato
     * @return
     * @throws Exception 
     */
    public JSONArray getClientePorContrato(int contrato) throws Exception{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarCliente select = new SeleccionarCliente();
        ResultSet rset = select.getClientePorContrato(contrato);
                
        //Creamos los JSONArray para guardar los objetos JSON
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        
        //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos
        //en el JSONArray.
        while(rset.next()){
            
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nit", rset.getString("NIT"));
            jsonObject.put("razonSocial", rset.getString("RAZON_SOCIAL"));
            jsonObject.put("actividadEconomica", rset.getString("ACTIVIDAD_ECONOMICA"));
            jsonObject.put("ciclo", rset.getString("CICLO"));
            jsonObject.put("sector", rset.getString("SECTOR"));
            jsonObject.put("pozo", rset.getString("POZO_AFORO_VERTIMIENTO"));
            jsonObject.put("direccion", rset.getString("DIRECCION"));
            jsonObject.put("barrio", rset.getString("BARRIO"));
            jsonObject.put("comuna", rset.getString("COMUNA"));
            jsonObject.put("telefono", rset.getString("TELEFONO"));
            jsonObject.put("telefono2", rset.getString("TELEFONO2"));
            jsonObject.put("usoServicio", rset.getString("FK_USO_SERVICIO"));
            jsonObject.put("email", rset.getString("EMAIL"));
            jsonObject.put("email2", rset.getString("EMAIL_SECUNDARIO"));
            jsonObject.put("repLegal", rset.getString("NOMBRE_REPRESENTANTE_LEGAL"));
            jsonObject.put("estadoVertimiento", rset.getString("FK_ESTADO"));
            jsonObject.put("codigo", rset.getString("CODIGO"));
            
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
            
        }
        
              
        jsonArreglo.add(jsonArray);
        //Se cierra el ResultSet
        select.desconectar();
        
        return jsonArreglo;
        
    }
    //-----------------------------------------------------------------------------

    
    
    
   
            
    /**
     * 
     * Llama al delegate para obtener los logs por cliente y contrato
     * 
     * @param codigo
     * @param tipo_campo
     * @return
     * @throws Exception 
     */
    public JSONArray  getLogsCliente(int codigo, String tipo_campo) throws Exception{
    
        //Ejecutamos la consulta y obtenemos el ResultSet
        SeleccionarLogsCliente select = new SeleccionarLogsCliente();
        ResultSet rset = select.getLogsCliente(codigo,tipo_campo);
                
        //Creamos los JSONArray para guardar los objetos JSON
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArreglo = new JSONArray();
        
        //Recorremos el ResultSet, armamos el objeto JSON con la info y guardamos
        //en el JSONArray.
        while(rset.next()){
            
            //Armamos el objeto JSON con la informacion del registro
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("campo", rset.getString("CAMPO"));
            jsonObject.put("valorAnterior", rset.getString("VALOR_ANTES"));
            jsonObject.put("valorPosterior", rset.getString("VALOR_DESPUES"));
            jsonObject.put("fechaModificacion", rset.getString("FECHA_MODIFICACION"));
            
            //Guardamos el JSONObject en el JSONArray y lo enviamos a la vista.
            jsonArray.add(jsonObject.clone());
            
        }
        
              
        jsonArreglo.add(jsonArray);
        
        //Se cierra el ResultSet
        select.desconectar();
        
        return jsonArreglo;
        
    }
    //-----------------------------------------------------------------------------

    
    
    
}