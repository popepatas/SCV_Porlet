/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.ProcesoVertimientosDelegates;

import java.sql.Connection;
import java.sql.ResultSet;
import modelo.DbManager;

/**
 *
 * @author illustrato
 */
public class SeleccionarVisitas {
    
    private DbManager db;
    private String filaInicio;
    private String filaFin ;
    private String tipoVisita;
    private String fechaInicial;
    private String fechaFinal;
    private String codigoProceso;
    private String estadoVisita;
    private String contrato;
    private String nit;
    private String razonSocial; 
    private String programacion;
    private String comuna;
    private Connection conn;
    private String motivoVisita;
    private String direccion;
    /**
     * 
     * Obtiene la informacion de visitas administracion
     * 
     * @param filaInicio
     * @param filaFin
     * @param tipoVisita
     * @param fechaInicial
     * @param fechaFinal
     * @param codigoProceso
     * @param estadoVisita
     * @param contrato
     * @param nit
     * @param razonSocial    
     */
    public SeleccionarVisitas( String filaInicio, String filaFin, String tipoVisita, String fechaInicial, String fechaFinal,String codigoProceso, String estadoVisita, String contrato, String nit, String razonSocial, String motivoVisita){        
        
        this.db = new DbManager();
        this.filaInicio = filaInicio;
        this.filaFin = filaFin;
        this.tipoVisita = tipoVisita;
        this.fechaInicial = fechaInicial;
        this.fechaFinal = fechaFinal;
        this.codigoProceso = codigoProceso;
        this.estadoVisita = estadoVisita;
        this.contrato = contrato;
        this.nit = nit;
        this.razonSocial = razonSocial;
        this.motivoVisita = motivoVisita;
        conn = db.conectar();
    }
    
    /**
     * 
     * Obtiene la informacion de visitas Pendientes
     * 
     * @param filaInicio
     * @param filaFin
     * @param codigoProceso
     * @param contrato
     * @param nit
     * @param razonSocial
     * @param comuna
      */
    public SeleccionarVisitas( String filaInicio, String filaFin, String codigoProceso, String contrato, String nit, String razonSocial, String comuna, String direccion){        
        
        this.db = new DbManager();
        this.filaInicio = filaInicio;
        this.filaFin = filaFin;
        this.codigoProceso = codigoProceso;        
        this.contrato = contrato;
        this.nit = nit;
        this.razonSocial = razonSocial;        
        this.comuna = comuna;
        this.direccion = direccion;
        conn = db.conectar();
    }


    
    public SeleccionarVisitas() {
        this.db = new DbManager();
        conn = db.conectar();
    }
    
    
   
     public ResultSet getVisitas(){
         
        String query = getQueryBusqueda();
        
        ResultSet rset = db.ejecutar(conn, query);
                
        return rset;
       
    }
     
      public ResultSet getVisitasPendientes(){
         
        String query = getQueryBusquedaAdmon();
        
        ResultSet rset = db.ejecutar(conn, query);
                
        return rset;
       
    }
       private String getQueryBusquedaAdmon(){
         String footer = "  ORDER BY  ASE.FECHA_CREACION DESC \n";
         String header = "";
         String body = "";
         String query = "";
         
                if(!filaInicio.equals("") && !filaFin.equals("") && filaInicio != null && filaFin != null ) { 
                     
                         footer += ") WHERE rnum >= "+filaInicio+" AND rnum <= "+filaFin;

                         header = "SELECT * FROM (";
                 }

                       body =   "SELECT\n" +
                                "	PV.CODIGO AS CODPROCESO,\n" +
                                "	PV.FK_CONTRATO AS CONTRATO,\n" +
                                "	CLI.NIT,\n" +
                                "	UPPER(CLI.RAZON_SOCIAL) AS RAZON_SOCIAL,\n" +
                                "	CLI.COMUNA,\n" +
                                "       CLI.DIRECCION, \n" +
                                "       ROW_NUMBER() OVER (ORDER BY ASE.FECHA_CREACION DESC) AS rnum\n" +
                                "FROM\n" +
                                "	 TB_PROCESOS_VERTIMIENTOS PV\n" +
                                "	INNER JOIN  TB_ASESORIAS ASE ON ASE.FK_PROCESO_VERTIMIENTO = PV.CODIGO\n" +
                                "	INNER JOIN  TB_CLIENTES CLI ON CLI.CODIGO = PV.FK_CLIENTE\n" +
                                "	\n" +
                                "WHERE\n" +
                                "	PV.CODIGO IS NOT NULL AND\n" +
                                "      ASE.REQUIERE_VISITA = 'SI'\n" ;

                    
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
                        body += " AND  UPPER(CLI.DIRECCION)  LIKE  UPPER('%" + direccion +"%')";
                    }
                    
                    query = header + body + footer;
                    
                    return query;
        }
     
      private String getQueryBusqueda(){
         String footer = "  ORDER BY VIS.FECHA_VISITA DESC \n";
         String header = "";
         String body = "";
         String query = "";
         
                if(!filaInicio.equals("") && !filaFin.equals("") && filaInicio != null && filaFin != null ) { 
                     
                         footer += ") WHERE rnum >= "+filaInicio+" AND rnum <= "+filaFin;

                         header = "SELECT * FROM (";
                 }

                       body =   "SELECT\n" +
                                    "PV.CODIGO AS CODPROCESO,\n" +
                                    "VIS.CODIGO,\n" +
                                    "TEC.NOMBRES,\n" +
                                    "TEC.APELLIDOS,\n" +
                                    "to_char(VIS.FECHA_VISITA,'DD/MM/YYYY') AS FECHA_VISITA,\n" +
                                    "MOTIVO.DESCRIPCION AS MOVITO,\n" +
                                    "TV.DESCRIPCION AS TIPOVISITA,\n" +
                                    "EST.DESCRIPCION AS ESTADO,\n" +
                                    " ROW_NUMBER() OVER (ORDER BY VIS.FECHA_VISITA DESC) AS rnum,\n" +
                                    "(CASE WHEN RESULTADO IS NULL THEN 'SIN RESULTADO' ELSE 'VER RESULTADO' END) AS Resultado,\n" +
                                    " COUNT(*) OVER() AS total_rows,\n" +
                                    "PV.FK_CONTRATO AS CONTRATO,\n" +
                                    "CLI.NIT ||' / '||UPPER(CLI.RAZON_SOCIAL) AS RAZON_SOCIAL,\n" +
                                    "(TEC2.NOMBRES ||' '|| TEC2.APELLIDOS) AS TECNICO_VISITO\n" +
                                    "FROM\n" +
                                    " TB_PROCESOS_VERTIMIENTOS PV\n" +
                                    "INNER JOIN  TB_VISITAS VIS ON PV.CODIGO = VIS.FK_PROCESO_VERTIMIENTO\n" +
                                    "INNER JOIN  TB_TIPOS_VISITAS TV ON TV.CODIGO = VIS.FK_TIPO_VISITA\n" +
                                    "INNER JOIN  TB_TECNICOS TEC ON TEC.CODIGO = VIS.FK_TECNICO\n" +
                                    "INNER JOIN  TB_ESTADOS EST ON EST.CODIGO = VIS.FK_ESTADO\n" +
                                    "INNER JOIN  TB_MOTIVOS_VISITAS MOTIVO ON MOTIVO.CODIGO = VIS.FK_MOTIVO_VISITA\n" +
                                    "INNER JOIN  TB_CLIENTES CLI ON CLI.CODIGO = PV.FK_CLIENTE\n" +
                                    "LEFT JOIN  TB_TECNICOS TEC2 ON TEC2.CODIGO = VIS.FK_TECNICO_VISITO\n" +
                                    "WHERE\n" +
                                    "PV.CODIGO IS NOT NULL \n";
                     
                    if(!fechaInicial.equals("") && fechaInicial != null){

                         body += " AND VIS.FECHA_VISITA BETWEEN to_date('"+fechaInicial+"','DD/MM/YYYY') AND to_date('"+fechaFinal+"','DD/MM/YYYY')";

                    }
                    
                    if(!estadoVisita.equals("") && estadoVisita != null){     
                        body += " AND VIS.FK_ESTADO  = " + estadoVisita;
                    }
                    
                    
                    
                    if(!motivoVisita.equals("") && motivoVisita!=null){
                        body += " AND FK_MOTIVO_VISITA = " + motivoVisita;
                    }

                    
                    if(!tipoVisita.equals("") && tipoVisita != null){

                        body += " AND TV.CODIGO = " + tipoVisita;

                    }
                       
                    if(!codigoProceso.equals("") && codigoProceso != null){
                    
                              body += " AND PV.CODIGO = " + codigoProceso;
                    }
                       
                        
                    if(!contrato.equals("") && contrato != null){     
                        body += " AND PV.FK_CONTRATO  = " + contrato;
                    }
                    
                    if(!razonSocial.equals("") && razonSocial != null){     
                        body += " AND UPPER(CLI.RAZON_SOCIAL)  LIKE  UPPER('%" + razonSocial+"%')";
                    }
                    
                    if(!nit.equals("") && nit != null){     
                        body += " AND CLI.NIT  LIKE '" + nit+"%'";
                    }
                   
                     
                    query = header + body + footer;
                    
                    return query;
        }
      
      
      
      
      
      public ResultSet getContadorVisitasPendientes(String codigoProceso){
                
                String body;
                String query;
                              query =  "SELECT\n" +
                                       "    COUNT(PV.CODIGO ) AS COUNT	\n" +
                                       "FROM\n" +
                                       "	 TB_PROCESOS_VERTIMIENTOS PV\n" +
                                       "	INNER JOIN  TB_ASESORIAS ASE ON ASE.FK_PROCESO_VERTIMIENTO = PV.CODIGO\n" +
                                       "	INNER JOIN  TB_CLIENTES CLI ON CLI.CODIGO = PV.FK_CLIENTE	\n" +
                                       "WHERE\n" +
                                       "	PV.CODIGO IS NOT NULL AND\n" +
                                       "        ASE.REQUIERE_VISITA = 'SI'\n" ;

               if(!codigoProceso.equals("") && codigoProceso != null){

                           query += " AND PV.CODIGO = " + codigoProceso;
               }                
                  
                     
               query += "  ORDER BY ASE.FECHA_CREACION DESC";
               
               
               ResultSet rset = db.ejecutar(conn, query);
               return rset;   
                    
                    
        }
      
      public ResultSet getVisita(String codigo){
          
          String query = "SELECT * FROM TB_VISITAS WHERE CODIGO = " + codigo;
          ResultSet rset = db.ejecutar(conn, query);
          return rset;
      }
      
      public void desconectar()
      {
          db.desconectar(conn);
      }
  }