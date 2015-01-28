package modelo.ProcesoVertimientosDelegates;

import modelo.ParametrizacionDelegates.*;
import java.sql.Connection;
import java.sql.ResultSet;
import modelo.*;


public class SeleccionarMonitoreos {
    
   //Atributos
    private DbManager db;
    private Connection conn;
    
    /**
     * 
     * Constructor
     * 
     */
    public SeleccionarMonitoreos() {
        db = new DbManager();
        conn = db.conectar();
    }
    
    
    
    
  /**
     * 
     * Obtiene la informacion de visitas administracion
     * 
     * @param codigo
     * @return rset 
     */
    public ResultSet getMonitoreos(String fechaInicial, String fechaFinal, int codigoProceso) throws Exception{
       
        try{
           
        
            String footer = "";
            String body =  "SELECT\n" +
                            "mon.CODIGO,\n" +
                            "  mon.FK_PROCESO_VERTIMIENTO,\n" +
                            "  to_char(mon.FECHA_MONITOREO,'dd/MM/yyyy')  AS FECHA_MONITOREO,\n" +
                            "  mon.HORA_INICIAL,\n" +
                            "  mon.HORA_FINAL,\n" +
                            "  cons.NOMBRES ||' ' || cons.APELLIDOS AS CONSULTOR,\n" +
                            "  lab.NOMBRES AS LABORATORIO,\n" +
                            "  est.DESCRIPCION AS ESTADO,"
                            + "mon.DURACION_MONITOREO\n" + 
                            "FROM TB_MONITOREOS mon\n" +                            
                            "JOIN TB_LABORATORIOS_CONSULTOR lab ON mon.FK_LABORATORIO = lab.CODIGO\n" +
                            "JOIN TB_ESTADOS est ON mon.FK_ESTADO = est.CODIGO\n" +
                            "LEFT JOIN TB_LABORATORIOS_CONSULTOR cons ON mon.FK_CONSULTOR = cons.CODIGO\n" +
                            "WHERE mon.FK_PROCESO_VERTIMIENTO =  " + codigoProceso ;

            if((fechaInicial != "" && fechaInicial != null) && (fechaFinal != "" && fechaFinal != null)){

                footer = " AND mon.FECHA_MONITOREO BETWEEN '"+fechaInicial+"' AND '"+fechaFinal+"'"; 

            }

            String query = body + footer + " ORDER BY mon.FK_ESTADO, FECHA_MONITOREO DESC";

            ResultSet rset = db.ejecutar(conn, query);

            return rset;
        
        }
        catch(Exception e)
        {
           throw  e;
        }
    }
    //-----------------------------------------------------------------------------
    
    
     /**
     *
     * @param filaInicio
     * @param filaFin
     * @param fechaInicial
     * @param fechaFinal
     * @param codigoProceso
     * @param contrato
     * @param nit
     * @param comuna
     * @param razonSocial
     * @param direccion
     * @param estado
     * @return 
     * @throws java.lang.Exception
     */
      public ResultSet getMonitoreosAdmon(String filaInicio, String filaFin,String fechaInicial, String fechaFinal, String codigoProceso, String contrato,String nit,String razonSocial, String comuna, String direccion, String estado, String codigoMonitoreo) throws Exception{
       
        try{
           
         String footer = "  ORDER BY FECHA_MONITOREO DESC \n";
         String header = "";
         String body = "";
         String query = "";
         
                if(!filaInicio.equals("") && !filaFin.equals("") && filaInicio != null && filaFin != null ) { 
                     
                         footer += ") WHERE rnum >= "+filaInicio+" AND rnum <= "+filaFin;

                         header = "SELECT * FROM (";
                 }

                       body =   "SELECT\n" +
                                    "MON.CODIGO,\n" +
                                    "MON.FK_PROCESO_VERTIMIENTO,\n" +
                                    "to_char(mon.FECHA_MONITOREO,'dd/MM/yyyy') AS FECHA_MONITOREO,\n" +
                                    "MON.HORA_INICIAL,\n" +
                                    "MON.HORA_FINAL,\n" +
                                    "cons.NOMBRES ||' ' || cons.APELLIDOS AS CONSULTOR,\n" +
                                    "LAB.NOMBRES AS LABORATORIO,\n" +
                                    "EST.DESCRIPCION AS ESTADO,\n" +
                                    "TECNSUP.NOMBRES ||' '||TECNSUP.APELLIDOS AS TECNICO,\n" +
                                    "MON.OBSERVACION_SUPERVISION,\n"+
                                    "MON.ESTUVO_MONITOREO, \n" +
                                    "CLI.NIT,\n" +
                                    "CLI.RAZON_SOCIAL,\n" +
                                    "PV.FK_CONTRATO, \n" +
                                    "CASE  \n" +
                                    "WHEN MON.FK_TECNICO_SUPERVISION IS NOT NULL THEN 'EN SUPERVISIÓN' \n" +
                                    "WHEN MON.OBSERVACION_SUPERVISION IS NOT NULL THEN 'SUPERVISADO' \n" +
                                    "ELSE 'SIN SUPERVICIÓN'\n" +
                                    "END ESTADOBQ,\n" +
                                    "ROW_NUMBER() OVER (ORDER BY MON.FECHA_MONITOREO DESC) AS rnum \n"+
                                    "FROM\n" +
                                    " TB_PROCESOS_VERTIMIENTOS PV \n" +
                                    "INNER JOIN  TB_MONITOREOS MON ON PV.CODIGO = MON.FK_PROCESO_VERTIMIENTO\n" +                                    
                                    "INNER JOIN  TB_LABORATORIOS_CONSULTOR LAB ON MON.FK_LABORATORIO = LAB.CODIGO\n" +
                                    "INNER JOIN  TB_ESTADOS EST ON MON.FK_ESTADO = EST.CODIGO\n" +
                                    "INNER JOIN  TB_CLIENTES CLI ON CLI.CODIGO = PV.FK_CLIENTE\n" +
                                    "LEFT JOIN  TB_LABORATORIOS_CONSULTOR CONS ON MON.FK_CONSULTOR = CONS.CODIGO\n" +
                                    "LEFT JOIN  TB_TECNICOS TECNSUP ON TECNSUP.CODIGO = MON.FK_TECNICO_SUPERVISION\n" +
                                    "WHERE\n" +
                                    "PV.CODIGO IS NOT NULL \n" ;

                    
                    if(!fechaInicial.equals("") && fechaInicial != null){

                        body += " AND MON.FECHA_MONITOREO BETWEEN to_date('"+fechaInicial+"','DD/MM/YYYY') AND to_date('"+fechaFinal+"','DD/MM/YYYY')";

                    }
                    
                     if(!codigoMonitoreo.equals("") && codigoMonitoreo != null){
                    
                              body += " AND MON.CODIGO = " + codigoMonitoreo;
                    }
                    
                    if(!codigoProceso.equals("") && codigoProceso != null){
                    
                              body += " AND PV.CODIGO = " + codigoProceso;
                    }
                       
                        
                    if(!contrato.equals("") && contrato != null){     
                        body += " AND PV.FK_CONTRATO  = " + contrato;
                    }
                    
                    if(!razonSocial.equals("") && razonSocial != null){     
                        body += " AND UPPER(CLI.RAZON_SOCIAL)  LIKE  UPPER('" + razonSocial+"%')";
                    }
                    
                    if(!nit.equals("") && nit != null){     
                        body += " AND CLI.NIT  LIKE '" + nit+"%'";
                    }
                    
                    if(!comuna.equals("") && comuna != null){     
                        body += " AND CLI.COMUNA  = " + comuna;
                    }
                    
                    if(!direccion.equals("") && direccion != null){     
                        body += " AND CLI.DIRECCION  LIKE UPPER('" + direccion+"%')";
                    }
                    
                    if(!estado.equals("") && estado != null && ( estado.equals("7") || estado.equals("1"))){     
                        
                        body += " AND MON.FK_ESTADO  =  " + estado;
                        
                    }else{
                    
                        body += " AND MON.FK_ESTADO IN (1,7)";
                    }
                      
                    
                 
                    
                  
                     
                    query = header + body + footer;
                    
               
                 

            ResultSet rset = db.ejecutar(conn, query);

            return rset;
        
        }
        catch(Exception e)
        {
           throw  e;
        }
    }
    
  
     
    
    //-----------------------------------------------------------------------------
    
    
    /**
     * 
     * Obtiene la informacion de una unidad de medida
     * 
     * @param codigo
     * @return rset 
     */
    public ResultSet getMonitoreo(int codigo){
    
        String query = "SELECT CODIGO,\n" +
                            "  FK_LABORATORIO,\n" +
                            "  FK_CONSULTOR,\n" +
                            "  FK_PROCESO_VERTIMIENTO,\n" +
                            "  MOTIVO_REPROGRAMACION,\n" +
                            "  ESTUVO_MONITOREO,\n" +
                            "  OBSERVACION_SUPERVISION,\n" +
                            "  FK_TECNICO_SUPERVISION, \n" +
                            "  FK_ESTADO,\n" +
                            "  HORA_INICIAL,\n" +
                            "  HORA_FINAL, \n" +
                            "  to_char(FECHA_MONITOREO,'DD/MM/YYYY') AS FECHA_MONITOREO,\n" +
                            "  JORNADA_PRODUCTIVA_DIAS,\n" +
                            "  JORNADA_PRODUCTIVA_HORAS,\n" +
                            "  OBSERVACION,\n" +
                            "  PRE_TRATAMIENTO,\n" +
                            "  FK_TIPO_LODO,\n" +
                            "  LODOS_ACTIVIDAD_PRODUCTIVA,\n" +
                            "  ENTIDAD_MANEJO_LODOS,\n" +
                            "  VALOR_TASA_RETRIBUTIVA,\n" +
                            "  DURACION_MONITOREO\n" +
                    "FROM TB_MONITOREOS  WHERE CODIGO = " + codigo;

        ResultSet rset = db.ejecutar(conn, query);

        return rset;
        
    }
    //-----------------------------------------------------------------------------
 
    public ResultSet obtenerPuntosMonitoreo(String codigoMonitoreo)
    {
        String query = "SELECT * FROM TB_PUNTOS_MONITOREOS WHERE FK_MONITOREO = " + codigoMonitoreo;
        
        ResultSet rset = db.ejecutar(conn, query);
        
        return rset;
    }
    
    public void desconectar()
    {
        db.desconectar(this.conn);
    }
    
}
