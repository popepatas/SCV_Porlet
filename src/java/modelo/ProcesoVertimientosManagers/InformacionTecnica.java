/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.ProcesoVertimientosManagers;

import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.ParametrizacionDelegates.SeleccionarPrmfisicoquimicos;
import modelo.ParametrizacionDelegates.SeleccionarPuntosVertimiento;
import modelo.ParametrizacionManagers.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import modelo.ProcesoVertimientosDelegates.*;
import modelo.ParametrizacionManagers.*;

public class InformacionTecnica {
    
    
    /**
     * 
     * Arma el JSON con la informacion tecnica.
     * 
     * @param codigoProceso
     * @return
     * @throws Exception 
     */
    public JSONArray getInformacionTecnica(int codigoProceso) throws Exception{
      
        int cantidadDatos = hayDatos(codigoProceso);
        
        //Mangers necesarios y Delegates Necesarios
        PuntosVertimiento puntoVert = new PuntosVertimiento();
        ParamFisicoquimicos param = new ParamFisicoquimicos();
        
       
        //Creamos el contenedor principal del JSON
        JSONArray contenedorPrincipal = new JSONArray();
        
        //Obtenemos la informacion del punto de vertimiento.
        Object data[] = puntoVert.getPuntosParaInfoTecnica(codigoProceso);
        ResultSet rset1 = (ResultSet)data[0];
        
        //Array para guardar la informacion del punto
        JSONArray puntoArray = new JSONArray();
        int puntoMonitoreo = 0;
        
        //Guardamos la informacion del punto en el JSON
        while(rset1.next()){
            
            JSONObject puntoObject = new JSONObject();          
            
            puntoObject.put("codigo" , rset1.getString("PUNTO_MONITOREO"));
            puntoMonitoreo = Integer.parseInt(rset1.getString("PUNTO_MONITOREO"));
            puntoObject.put("ubicacion" , rset1.getString("UBICACION"));
            puntoObject.put("latitud" , rset1.getString("LATITUD"));
            puntoObject.put("longitud" , rset1.getString("LONGITUD"));
            puntoObject.put("ciiu" , rset1.getString("CIIU"));
            puntoObject.put("fechaMonitoreo" , rset1.getString("FECHA_MONITOREO_PUNTO"));
            puntoObject.put("descripcionCiiu" , rset1.getString("DESCRIPCION_CIIU"));
            puntoObject.put("jordanadaProductivaDia" , rset1.getString("JPRODDIAS"));
            puntoObject.put("jordanadaProductivaHoras" , rset1.getString("JPRODHORAS"));
            puntoObject.put("jordanadaProductivaObsev" , rset1.getString("JPRODOBV"));
            
            
            int ciiu = rset1.getInt("CIIU");
            
            //Guardamos las informacion de la jornada
            JSONArray arrayJornada = new JSONArray();
            SeleccionarInfoTecJornadas infoTecJornadas = new SeleccionarInfoTecJornadas();
            
            //Validamos si ya hay datos registrados en las jornadas y en los detalles de las jornadas.
            if(cantidadDatos > 0){
                
                //Obtenemos La informacion de las jornadas y las guardamos en el JSON
                ResultSet rsetJornadas = infoTecJornadas.getJornadas(puntoMonitoreo);
                while(rsetJornadas.next()){

                    JSONObject objectJornada = new JSONObject();
                    objectJornada.put("nombre", "Jornada " + rsetJornadas.getInt("JORNADA"));
                    objectJornada.put("cargaDBO", rsetJornadas.getDouble("CARGA_DBO"));
                    objectJornada.put("cargaSST", rsetJornadas.getDouble("CARGA_SST"));
                    objectJornada.put("horaInicio", rsetJornadas.getString("HORA_INICIO"));
                    objectJornada.put("horaFin", rsetJornadas.getString("HORA_FIN"));
                    objectJornada.put("caudalJornada", rsetJornadas.getString("CAUDAL_JORNADA"));
                    
                    int jornada = rsetJornadas.getInt("JORNADA");
                    
                    //Obtenemos y guardamos la informacion de cada parametro
                    Object data2[] = param.getParametrosParaInfoTecnica(puntoMonitoreo,jornada, ciiu);
                    ResultSet rset2 = (ResultSet)data2[0];

                    JSONArray arrayParametro = new JSONArray();
                    while(rset2.next()){

                        JSONObject objectParametro = new JSONObject();
                        objectParametro.put("parametro", rset2.getString("PARAMETRO"));
                        objectParametro.put("codigoParametro", rset2.getString("CODIGO_PARAMETRO"));
                        objectParametro.put("rangoInicial", rset2.getString("RANGO_INICIAL"));
                        objectParametro.put("rangoFinal", rset2.getString("RANGO_FINAL"));
                        objectParametro.put("valorInforme", rset2.getDouble("VALOR"));
                        objectParametro.put("cumple", rset2.getString("CUMPLE"));
                        objectParametro.put("observacion", rset2.getString("OBSERVACION"));
                        objectParametro.put("indicardorMenor", rset2.getString("MENOR"));
                        
                        arrayParametro.add(objectParametro);
                    }

                    //Cerramos conexion de parametros
                    SeleccionarPrmfisicoquimicos select2 = (SeleccionarPrmfisicoquimicos)data2[1];
                    select2.desconectar();

                    //Guardamos los parametros en la jornada.
                    objectJornada.put("tabla", arrayParametro);

                    //Guardamos la Jornada
                    arrayJornada.add(objectJornada);
                }
                //Cerramos la conexion de jornadas

            }else if(cantidadDatos == 0){
                
                //Guardamos la informacion de las jornadas
                for(int i = 1; i < 5; i++){
                JSONObject objectJornada = new JSONObject();
                objectJornada.put("nombre", "Jornada " + i);
                objectJornada.put("cargaDBO", "");
                objectJornada.put("cargaSST", "");
                objectJornada.put("horaInicio", "");
                objectJornada.put("horaFin", "");
                objectJornada.put("caudalJornada","");

                //Obtenemos y guardamos la informacion de cada parametro
                Object data2[] = param.getParametrosParaInfoTecnica(ciiu);
                ResultSet rset2 = (ResultSet)data2[0];

                JSONArray arrayParametro = new JSONArray();
                while(rset2.next()){

                    JSONObject objectParametro = new JSONObject();
                    objectParametro.put("parametro", rset2.getString("PARAMETRO"));
                    objectParametro.put("codigoParametro", rset2.getString("CODIGO_PARAMETRO"));
                    objectParametro.put("rangoInicial", rset2.getString("RANGO_INICIAL"));
                    objectParametro.put("rangoFinal", rset2.getString("RANGO_FINAL"));
                    objectParametro.put("valorInforme", "");
                    objectParametro.put("cumple", "");
                    objectParametro.put("observacion", "");
                    objectParametro.put("indicardorMenor", "");
                    
                    arrayParametro.add(objectParametro);
                }

                //Cerramos conexion de parametros
                SeleccionarPrmfisicoquimicos select2 = (SeleccionarPrmfisicoquimicos)data2[1];
                select2.desconectar();

                //Guardamos los parametros en la jornada.
                objectJornada.put("tabla", arrayParametro);

                //Guardamos la Jornada
                arrayJornada.add(objectJornada);
                }
            }
            
            //guardamos las jornadas en el punto
            puntoObject.put("jornadas", arrayJornada);
            puntoArray.add(puntoObject);
            
            infoTecJornadas.desconectar();
        }
        
        
        contenedorPrincipal.add(puntoArray);

        //Cerrar Conexion.
        SeleccionarPuntosVertimiento select = (SeleccionarPuntosVertimiento)data[1];
        select.desconectar();
        
        return contenedorPrincipal;
    }
    
    
    public int registrarInfoTecPuntoMonitoreo(String fechaMonitoreo, String jornadaProductivaDia, String jornadaProductivaHora, 
                                                String jornadaProductivaObservacion, String codigoPunto) throws Exception{
        
        
        InsertarInfoTecPuntoMonitoreo insert = new InsertarInfoTecPuntoMonitoreo(fechaMonitoreo, jornadaProductivaDia, jornadaProductivaHora, 
                                                                                    jornadaProductivaObservacion, codigoPunto);
        insert.ejecutar();
        
        return insert.getCodigoMonitoreo();
    }
    
    
    
    public int registrarInfoTecJornada(String nombreJornada, String cargaDBO, String cargaSST, 
                                        String horaInicio, String horaFin, String codigoPunto, String caudalJornada) throws Exception{
        
        InsertarInfoTecJornada insert = new InsertarInfoTecJornada(nombreJornada, cargaDBO, cargaSST, horaInicio, horaFin, codigoPunto, caudalJornada);
        insert.ejecutar();
        
        return insert.getCodigoPuntoJornada();
    }
    
    
    
    public void registrarInfoTecDetallesJornada(String codigoPunto, String codigoParametro, String valorInforme, String observacion, 
                                                String cumple, String codigoJornadaPuntoMonitoreo, String indicadorMenor) throws Exception{
        
        InsertarInfoTecDetallesJornada insert = new InsertarInfoTecDetallesJornada(codigoPunto, codigoParametro, valorInforme, 
                                                                                    observacion, cumple, codigoJornadaPuntoMonitoreo, indicadorMenor);
        insert.ejecutar();
        
    }

    
    public int hayDatos (int codigoMonitoreo) throws SQLException{
        
        SeleccionarInfoTecJornadas select = new SeleccionarInfoTecJornadas();
        ResultSet rset = select.hayDatos(codigoMonitoreo);
        int cantidad = 0;
        
        while(rset.next()){
            cantidad = rset.getInt("CANTIDAD");
        }
        
        return cantidad;
    }
    
    public int contarJornadas (int puntoMonitoreo) throws SQLException{
        
        SeleccionarInfoTecJornadas select = new SeleccionarInfoTecJornadas();
        ResultSet rset = select.contarJornadas(puntoMonitoreo);
        int cantidad = 0;
        
        while(rset.next()){
            cantidad = rset.getInt("CANTIDAD");
        }
        
        return cantidad;
    }
}
