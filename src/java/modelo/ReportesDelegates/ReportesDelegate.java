package modelo.ReportesDelegates;

import configuracion.Queries;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import modelo.DbManager;

/**
 *
 * @author jmrincon
 */
public class ReportesDelegate {
    //Atributos
    private String fechaInicial;
    private String fechaFinal;
    private String estadoProceso;
    private String numeroContrato;
    private String nit;
    private String actividadProductiva;
    private String razonSocial;
    private String comuna;
    private String laboratorio;
    private String usoServicio;
    private String tipoInforme;
    
    private String codigoProceso;
    private String codigoVisita;
    
    private DbManager db;
    private Connection conn;

    public ReportesDelegate(String fechaInicial, String fechaFinal, String estadoProceso, String numeroContrato, String nit, 
            String actividadProductiva, String razonSocial, String comuna, String laboratorio, String usoServicio, String tipoInforme) {
        this.fechaInicial = fechaInicial;
        this.fechaFinal = fechaFinal;
        this.estadoProceso = estadoProceso;
        this.numeroContrato = numeroContrato;
        this.nit = nit;
        this.actividadProductiva = actividadProductiva;
        this.razonSocial = razonSocial;
        this.comuna = comuna;
        this.laboratorio = laboratorio;
        this.usoServicio = usoServicio;
        this.tipoInforme = tipoInforme;
        db = new DbManager();
        conn = db.conectar();
    }
    
    public ReportesDelegate(){
        db = new DbManager();
        conn = db.conectar();
    }
    
    public ResultSet obtenerUltimoMonitoreoXProceso(String codigoProceso) throws Exception{
        String query = "SELECT mo.FK_PROCESO_VERTIMIENTO codigoProceso"
                + ", mo.CODIGO codigoMonitoreo" 
                + ", NVL(lab.CONTACTO, '...') labContacto" 
                + ", mo.ESTUVO_MONITOREO tuvoMonitoreo" 
                + ", mo.OBSERVACION_SUPERVISION obseSupervision" 
                + ", te.NOMBRES || te.APELLIDOS nombreTecnico" 
                + ", TO_CHAR(mo.FECHA_MONITOREO, 'DD/MM/YYYY') fechaUltimoMonitoreo" 
                + ", mo.OBSERVACION obseMonitoreo" 
                + " FROM TB_MONITOREOS mo " 
                + "LEFT OUTER JOIN TB_LABORATORIOS_CONSULTOR lab ON lab.CODIGO = mo.FK_LABORATORIO AND lab.FK_TIPO_ENTIDAD = 1 " 
                + "LEFT OUTER JOIN TB_TECNICOS te ON te.CODIGO = mo.FK_TECNICO_SUPERVISION " 
                + "WHERE mo.FK_ESTADO = 1 " 
                + "AND mo.FK_PROCESO_VERTIMIENTO = " + codigoProceso 
                + " AND mo.FECHA_MONITOREO = (Select MAX(y.FECHA_MONITOREO) from TB_MONITOREOS y WHERE y.FK_PROCESO_VERTIMIENTO = " + codigoProceso + ") ";
        //System.err.println("obtenerUltimoMonitoreoXProceso: " + query);
        ResultSet rset = db.ejecutar(conn, query);
        return rset;
    }
    
    public ResultSet obtenerUltimoEstadoXProceso(String codigoProceso) throws Exception{
        String query = "SELECT lpv.FK_PROCESO_VERTIMIENTO codigoProceso"
                + ", lpv.FK_ESTADO_ANTERIOR codigoEstadoAnterior" 
                + ", eAntPro.DESCRIPCION estadoAnterior" 
                + ", lpv.FK_ESTADO_ACTUAL codigoEstadoActual" 
                + ", eActPro.DESCRIPCION estadoActual" 
                + ", TO_CHAR(to_date(lpv.FECHA_MODIFICACION), 'DD/MM/YYYY') fechaModificacion" 
                + " FROM TB_LOGS_PROCESOS_VERTIMIENTOS lpv " 
                + "LEFT OUTER JOIN TB_ESTADOS_PROCESOS eActPro ON eActPro.CODIGO = lpv.FK_ESTADO_ACTUAL " 
                + "LEFT OUTER JOIN TB_ESTADOS_PROCESOS eAntPro ON eAntPro.CODIGO = lpv.FK_ESTADO_ANTERIOR " 
                + "WHERE lpv.FK_PROCESO_VERTIMIENTO = " + codigoProceso 
                + " AND lpv.FECHA_MODIFICACION = (Select MAX(y.FECHA_MODIFICACION) from TB_LOGS_PROCESOS_VERTIMIENTOS y WHERE y.FK_PROCESO_VERTIMIENTO = " + codigoProceso  + ") ";
        //System.err.println("reporteEstado: " + query);
        ResultSet rset = db.ejecutar(conn, query);
        return rset;
    }
    
    public ResultSet obtenerUltimaVisitaXProceso(String codigoProceso) throws Exception{
        String query = "SELECT vi.FK_PROCESO_VERTIMIENTO codigoProceso"
                + ", vi.CODIGO codigoVisita" 
                + ", vi.OBSERVACIONES obseVisita" 
                + ", te.NOMBRES || te.APELLIDOS nombreTecnico" 
                + ", TO_CHAR(vi.FECHA_VISITA, 'DD/MM/YYYY') fechaUltimaVisita" 
                + ", tvi.DESCRIPCION tipoVisita" 
                + ", movi.DESCRIPCION motivoVisita" 
                + " FROM TB_VISITAS vi " 
                + "LEFT OUTER JOIN TB_TECNICOS te ON te.CODIGO = vi.FK_TECNICO " 
                + "LEFT OUTER JOIN TB_TIPOS_VISITAS tvi ON tvi.CODIGO = vi.FK_TIPO_VISITA " 
                + "LEFT OUTER JOIN TB_MOTIVOS_VISITAS movi ON movi.CODIGO = vi.FK_MOTIVO_VISITA " 
                + "WHERE vi.FK_PROCESO_VERTIMIENTO = " + codigoProceso 
                //+ "AND vi.FK_ESTADO = 1"
                + " AND vi.FECHA_VISITA = (Select MAX(y.FECHA_VISITA) from TB_VISITAS y WHERE y.FK_PROCESO_VERTIMIENTO = " + codigoProceso  + ") ";
        //System.err.println("obtenerUltimaVisitaXProceso: " + query);
        ResultSet rset = db.ejecutar(conn, query);
        return rset;
    }
    
    public ResultSet obtenerUltimoHistorialDagmaXProceso(String codigoProceso) throws Exception{
        String query = "SELECT hd.FK_PROCESO_VERTIMIENTO codigoProceso" 
                + ", hd.CODIGO codigoHistorial" 
                + ", hd.RADICADO radicadoHistorial" 
                + ", TO_CHAR(hd.FECHA_RADICADO, 'DD/MM/YYYY') fechaRadicadoHistorial" 
                + ", hd.OBSERVACION obseHistorial" 
                + " FROM TB_HISTORIALES_DAGMA hd "
                + "WHERE hd.FK_PROCESO_VERTIMIENTO = " + codigoProceso 
                + " AND hd.FECHA_RADICADO = (Select MAX(y.FECHA_RADICADO) from TB_HISTORIALES_DAGMA y WHERE y.FK_PROCESO_VERTIMIENTO = " + codigoProceso  + ")";
        //System.err.println("reporteHistorial: " + query);
        ResultSet rset = db.ejecutar(conn, query);
        return rset;
    }
    
    public ResultSet ejecutarReporte1(String filaInicio, String filaFin) throws Exception{
        String query = getReporte1(filaInicio, filaFin);
        ResultSet rset = db.ejecutar(conn, query);
        return rset;
    }
    
    public ResultSet ejecutarReporte2(String filaInicio, String filaFin) throws Exception{
        String query = getReporte2(filaInicio, filaFin);
        ResultSet rset = db.ejecutar(conn, query);
        return rset;
    }
    
    public ResultSet ejecutarReporte3(String filaInicio, String filaFin) throws Exception{
        String query = getReporte3(filaInicio, filaFin);
        ResultSet rset = db.ejecutar(conn, query);
        return rset;
    }
    
    public ResultSet ejecutarReporte4(String filaInicio, String filaFin) throws Exception{
        String query = getReporte4(filaInicio, filaFin);
        ResultSet rset = db.ejecutar(conn, query);
        return rset;
    }
    
    public ResultSet ejecutarReporte5(String filaInicio, String filaFin) throws Exception{
        String query = getReporte5(filaInicio, filaFin);
        ResultSet rset = db.ejecutar(conn, query);
        return rset;
    }
    
    public ResultSet ejecutarReporte6(String[] paramFisicoQuimico, String[] rangoInicial, String[] rangoFinal, String filaInicio, String filaFin) throws Exception{
        String query = getReporte6(paramFisicoQuimico, rangoInicial, rangoFinal, filaInicio, filaFin);
        ResultSet rset = db.ejecutar(conn, query);
        return rset;
    }
    
    public ResultSet ejecutarReporte7(String lodoInicial, String lodoFinal, String tasaInicial, String tasaFinal, String filaInicio, String filaFin) throws Exception{
        String query = getReporte7(lodoInicial, lodoFinal, tasaInicial, tasaFinal, filaInicio, filaFin);
        ResultSet rset = db.ejecutar(conn, query);
        return rset;
    }
    
    private String getReporte1(String filaInicio, String filaFin){
        String query = "SELECT * FROM (SELECT pv.codigo codigoProceso" 
                + ", con.CONTRATO numeroContrato" 
                + ", cl.NIT" 
                + ", pv.FK_CLIENTE codigoCliente" 
                + ", cl.RAZON_SOCIAL razonSocial" 
                + ", ae.CODIGO || ' - ' || ae.DESCRIPCION actividadProductiva" 
                + ", 'Dias: ' || mo.JORNADA_PRODUCTIVA_DIAS || ' - Horas: ' || mo.JORNADA_PRODUCTIVA_HORAS jornadaLaboral" 
                + ", NVL(us.DESCRIPCION, '') usoServicio" 
                + ", NVL(cl.DIRECCION, '') direccion" 
                + ", NVL(cl.BARRIO, '') barrio" 
                + ", NVL(cL.COMUNA, '') comuna" 
                + ", NVL(cl.TELEFONO, '') telefono" 
                + ", NVL(cl.NOMBRE_REPRESENTANTE_LEGAL, '...') nombreContacto" 
                + ", NVL(cl.EMAIL, '') email" 
                + ", ti.DESCRIPCION tipoInforme" 
                + ", '...' notificado" 
                + ", NVL(lab.NOMBRES, '...') labContacto" 
                + ", NVL(cons.NOMBRES || cons.APELLIDOS, '...') nombConsultor" 
                + ", TO_CHAR(mo.FECHA_MONITOREO, 'DD/MM/YYYY') fechaEntrega" 
                + ", TO_CHAR(dev.FECHA_DEVOLUCION, 'DD/MM/YYYY') fechaDevolucion" 
                + ", TO_CHAR(dev.FECHA_ENTREGA_DEVOLUCION, 'DD/MM/YYYY') fechaEntregaDevolucion" 
                + ", ROW_NUMBER() OVER (ORDER BY pv.FECHA_PROCESO DESC) AS rnum" 
                + ", COUNT(*) OVER() AS total_rows" 
                + " FROM TB_CLIENTES cl " 
                + "LEFT OUTER JOIN TB_ACTIVIDADES_ECONOMICAS ae ON ae.CODIGO = cl.FK_ACTIVIDAD_ECONOMICA " 
                + "LEFT OUTER JOIN TB_TIPOS_USOS_SERVICIOS us ON us.CODIGO = cl.FK_USO_SERVICIO " 
                + "JOIN TB_PROCESOS_VERTIMIENTOS pv ON pv.FK_CLIENTE = cl.codigo " 
                + "LEFT OUTER JOIN TB_DEVOLUCIONES dev ON dev.FK_PROCESO_VERTIMIENTO = pv.CODIGO " 
                + "JOIN TB_TIPOS_INFORMES ti ON ti.CODIGO = pv.FK_TIPO_INFORME AND pv.FK_TIPO_INFORME IN (3, 4) " 
                + "JOIN TB_CONTRATOS con ON con.CONTRATO = pv.FK_CONTRATO " 
                + "LEFT OUTER JOIN TB_MONITOREOS mo ON mo.FK_PROCESO_VERTIMIENTO = pv.CODIGO AND mo.FK_ESTADO = 1 " 
                + "LEFT OUTER JOIN TB_LABORATORIOS_CONSULTOR lab ON lab.CODIGO = mo.FK_LABORATORIO AND lab.FK_TIPO_ENTIDAD = 1 " 
                + "LEFT OUTER JOIN TB_LABORATORIOS_CONSULTOR cons ON cons.CODIGO = mo.FK_LABORATORIO AND cons.FK_TIPO_ENTIDAD = 2 " 
                + "WHERE 1 = 1 " + "AND pv.FK_TIPO_INFORME IS NOT NULL ";
        
        if (fechaInicial != null && !fechaInicial.equals("") && fechaFinal != null && !fechaFinal.equals("")) {
            query += " AND pv.FECHA_PROCESO BETWEEN '" + fechaInicial + "' AND '" + fechaFinal + "' ";
        }
        
        if (estadoProceso != null && !estadoProceso.equals("")) {
            query += " AND pv.FK_ESTADO_PROCESO = " + estadoProceso;
        }
        
        if(numeroContrato != null && !numeroContrato.equals("")){
            query += " AND pv.FK_CONTRATO = " + numeroContrato;
        }
        
        if(nit != null && !nit.equals("")){
            query += " AND cl.NIT LIKE '%" + nit.toUpperCase() + "%'";
        }
        
        if(actividadProductiva != null && !actividadProductiva.equals("")){
            query += " AND cl.FK_ACTIVIDAD_ECONOMICA = " + actividadProductiva;
        }
        
        if(razonSocial != null && !razonSocial.equals("")){
            query += " AND UPPER(cl.RAZON_SOCIAL) LIKE UPPER('%" + razonSocial.toUpperCase() + "%')";
        }
        
        if(comuna != null && !comuna.equals("")){
            query += " AND cl.COMUNA = " + comuna;
        }
        
        if(laboratorio != null && !laboratorio.equals("")){
            query += " AND mo.FK_LABORATORIO = " + laboratorio;
        }
        
        if(usoServicio != null && !usoServicio.equals("")){
            query += " AND cl.FK_USO_SERVICIO = " + usoServicio;
        }
        
        if(tipoInforme != null && !tipoInforme.equals("")){
            query += " AND pv.FK_TIPO_INFORME = " + tipoInforme;
        }
        
        query += " ORDER BY pv.FECHA_PROCESO DESC " 
                + ") WHERE rnum >= " + filaInicio + " AND rnum <= " + filaFin;
        
        //System.err.println("reporte1: " + query);
        return query;
    }
    
    private String getReporte2(String filaInicio, String filaFin){
        String query = "SELECT * FROM (SELECT pv.codigo codigoProceso" 
                + ", con.CONTRATO numeroContrato" 
                + ", cl.NIT" 
                + ", pv.FK_CLIENTE codigoCliente" 
                + ", cl.RAZON_SOCIAL razonSocial" 
                + ", ae.CODIGO || ' - ' || ae.DESCRIPCION actividadProductiva" 
                + ", 'Dias: ' || mo.JORNADA_PRODUCTIVA_DIAS || ' - Horas: ' || mo.JORNADA_PRODUCTIVA_HORAS jornadaLaboral" 
                + ", NVL(us.DESCRIPCION, '') usoServicio" 
                + ", NVL(cl.DIRECCION, '') direccion" 
                + ", NVL(cl.BARRIO, '') barrio" 
                + ", NVL(cL.COMUNA, '') comuna" 
                + ", NVL(cl.TELEFONO, '') telefono" 
                + ", NVL(cl.NOMBRE_REPRESENTANTE_LEGAL, '...') nombreContacto" 
                + ", NVL(cl.EMAIL, '') email" 
                + ", ti.DESCRIPCION tipoInforme" 
                + ", eproc.DESCRIPCION estadoProceso" 
                + ", TO_CHAR(pv.FECHA_PROCESO, 'DD/MM/YYYY') fechaProceso" 
                + ", TO_CHAR(mo.FECHA_MONITOREO, 'DD/MM/YYYY') fechaMonitoreo" 
                + ", NVL(lab.NOMBRES, '...') labContacto" 
                + ", estAnt.DESCRIPCION estadoAnterior" 
                + ", estAct.DESCRIPCION estadoActual" 
                + ", lpv.FECHA_MODIFICACION fechaModificacion" 
                + ", ROW_NUMBER() OVER (ORDER BY pv.FECHA_PROCESO DESC) AS rnum" 
                + ", COUNT(*) OVER() AS total_rows" 
                + " FROM TB_CLIENTES cl " 
                + "LEFT OUTER JOIN TB_ACTIVIDADES_ECONOMICAS ae ON ae.CODIGO = cl.FK_ACTIVIDAD_ECONOMICA " 
                + "LEFT OUTER JOIN TB_TIPOS_USOS_SERVICIOS us ON us.CODIGO = cl.FK_USO_SERVICIO " 
                + "JOIN TB_PROCESOS_VERTIMIENTOS pv ON pv.FK_CLIENTE = cl.codigo " 
                + "LEFT OUTER JOIN TB_ESTADOS_PROCESOS eproc ON eproc.codigo = pv.FK_ESTADO_PROCESO " 
                + "LEFT OUTER JOIN TB_TIPOS_INFORMES ti ON ti.CODIGO = pv.FK_TIPO_INFORME " 
                + "JOIN TB_LOGS_PROCESOS_VERTIMIENTOS lpv ON lpv.FK_PROCESO_VERTIMIENTO = pv.CODIGO " 
                + "LEFT OUTER JOIN TB_ESTADOS_PROCESOS estAnt ON estAnt.CODIGO = lpv.FK_ESTADO_ANTERIOR " 
                + "LEFT OUTER JOIN TB_ESTADOS_PROCESOS estAct ON estAct.CODIGO = lpv.FK_ESTADO_ACTUAL " 
                + "LEFT OUTER JOIN TB_MONITOREOS mo ON mo.FK_PROCESO_VERTIMIENTO = pv.CODIGO AND mo.FK_ESTADO = 1 " 
                + "JOIN TB_CONTRATOS con ON con.CONTRATO = pv.FK_CONTRATO " 
                + "LEFT OUTER JOIN TB_LABORATORIOS_CONSULTOR lab ON lab.CODIGO = mo.FK_LABORATORIO AND lab.FK_TIPO_ENTIDAD = 1 " 
                + "WHERE 1 = 1 ";
        
        if (fechaInicial != null && !fechaInicial.equals("") && fechaFinal != null && !fechaFinal.equals("")) {
            query += " AND pv.FECHA_PROCESO BETWEEN '" + fechaInicial + "' AND '" + fechaFinal + "' ";
        }
        
        if (estadoProceso != null && !estadoProceso.equals("")) {
            query += " AND pv.FK_ESTADO_PROCESO = " + estadoProceso;
        }
        
        if(numeroContrato != null && !numeroContrato.equals("")){
            query += " AND pv.FK_CONTRATO = " + numeroContrato;
        }
        
        if(nit != null && !nit.equals("")){
            query += " AND cl.NIT LIKE '%" + nit.toUpperCase() + "%'";
        }
        
        if(actividadProductiva != null && !actividadProductiva.equals("")){
            query += " AND cl.FK_ACTIVIDAD_ECONOMICA = " + actividadProductiva;
        }
        
        if(razonSocial != null && !razonSocial.equals("")){
            query += " AND UPPER(cl.RAZON_SOCIAL) LIKE UPPER('%" + razonSocial.toUpperCase() + "%')";
        }
        
        if(comuna != null && !comuna.equals("")){
            query += " AND cl.COMUNA = " + comuna;
        }
        
        if(laboratorio != null && !laboratorio.equals("")){
            query += " AND mo.FK_LABORATORIO = " + laboratorio;
        }
        
        if(usoServicio != null && !usoServicio.equals("")){
            query += " AND cl.FK_USO_SERVICIO = " + usoServicio;
        }
        
        if(tipoInforme != null && !tipoInforme.equals("")){
            query += " AND pv.FK_TIPO_INFORME = " + tipoInforme;
        }
        
        query += " ORDER BY pv.CODIGO, pv.FECHA_PROCESO, lpv.FECHA_MODIFICACION DESC " 
                + ") WHERE rnum >= " + filaInicio + " AND rnum <= " + filaFin;
        //System.err.println("reporte2: " + query);
        return query;
    }
    
    private String getReporte3(String filaInicio, String filaFin){
        String query = "SELECT * FROM (SELECT pv.codigo codigoProceso" 
                + ", con.CONTRATO numeroContrato" 
                + ", cl.NIT" 
                + ", pv.FK_CLIENTE codigoCliente" 
                + ", cl.RAZON_SOCIAL razonSocial" 
                + ", ae.CODIGO || ' - ' || ae.DESCRIPCION actividadProductiva" 
                + ", 'Dias: ' || mo.JORNADA_PRODUCTIVA_DIAS || ' - Horas: ' || mo.JORNADA_PRODUCTIVA_HORAS jornadaLaboral" 
                + ", NVL(us.DESCRIPCION, '') usoServicio" 
                + ", NVL(cl.DIRECCION, '') direccion" 
                + ", NVL(cl.BARRIO, '') barrio" 
                + ", NVL(cL.COMUNA, '') comuna" 
                + ", NVL(cl.TELEFONO, '') telefono" 
                + ", NVL(cl.NOMBRE_REPRESENTANTE_LEGAL, '...') nombreContacto" 
                + ", NVL(cl.EMAIL, '') email" 
                + ", ti.DESCRIPCION tipoInforme" 
                + ", vi.CODIGO codigoVisita" 
                + ", TO_CHAR(vi.FECHA_VISITA, 'DD/MM/YYYY') fechaVisita" 
                //+ ", CASE WHEN vi.RESULTADO != null THEN estV.DESCRIPCION ELSE 'Pendiente' END estadoVisita" 
                + ", estV.DESCRIPCION estadoVisita" 
                //+ ", CASE WHEN vi.RESULTADO != null THEN vi.RESULTADO ELSE '' END resultadoVisita" 
                + ", vi.RESULTADO resultadoVisita" 
                + ", te.NOMBRES || te.APELLIDOS nombreTecnico" 
                + ", ROW_NUMBER() OVER (ORDER BY vi.FECHA_VISITA DESC) AS rnum" 
                + ", COUNT(*) OVER() AS total_rows" 
                + " FROM TB_CLIENTES cl " 
                + "LEFT OUTER JOIN TB_ACTIVIDADES_ECONOMICAS ae ON ae.CODIGO = cl.FK_ACTIVIDAD_ECONOMICA " 
                + "LEFT OUTER JOIN TB_TIPOS_USOS_SERVICIOS us ON us.CODIGO = cl.FK_USO_SERVICIO " 
                + "LEFT OUTER JOIN TB_PROCESOS_VERTIMIENTOS pv ON pv.FK_CLIENTE = cl.codigo " 
                + "LEFT OUTER JOIN TB_TIPOS_INFORMES ti ON ti.CODIGO = pv.FK_TIPO_INFORME " 
                + "JOIN TB_CONTRATOS con ON con.CONTRATO = pv.FK_CONTRATO " 
                + "LEFT OUTER JOIN TB_MONITOREOS mo ON mo.FK_PROCESO_VERTIMIENTO = pv.CODIGO AND mo.FK_ESTADO = 1 " 
                + "JOIN TB_VISITAS vi ON vi.FK_PROCESO_VERTIMIENTO = pv.CODIGO " 
                + "LEFT OUTER JOIN TB_ESTADOS estV ON estV.codigo = vi.FK_ESTADO " 
                + "LEFT OUTER JOIN TB_TECNICOS te ON te.CODIGO = vi.FK_TECNICO " 
                + "WHERE 1 = 1 ";
        
        if (fechaInicial != null && !fechaInicial.equals("") && fechaFinal != null && !fechaFinal.equals("")) {
            query += " AND vi.FECHA_VISITA BETWEEN '" + fechaInicial + "' AND '" + fechaFinal + "' ";
        }
        
        if(estadoProceso != null && !estadoProceso.equals("")){
            query += " AND vi.FK_ESTADO = " + estadoProceso;
        }
        
        if(numeroContrato != null && !numeroContrato.equals("")){
            query += " AND pv.FK_CONTRATO = " + numeroContrato;
        }
        
        if(nit != null && !nit.equals("")){
            query += " AND cl.NIT LIKE '%" + nit.toUpperCase() + "%'";
        }
        
        if(actividadProductiva != null && !actividadProductiva.equals("")){
            query += " AND cl.FK_ACTIVIDAD_ECONOMICA = " + actividadProductiva;
        }
        
        if(razonSocial != null && !razonSocial.equals("")){
            query += " AND UPPER(cl.RAZON_SOCIAL) LIKE UPPER('%" + razonSocial.toUpperCase() + "%')";
        }
        
        if(comuna != null && !comuna.equals("")){
            query += " AND cl.COMUNA = " + comuna;
        }
        
        if(usoServicio != null && !usoServicio.equals("")){
            query += " AND cl.FK_USO_SERVICIO = " + usoServicio;
        }
        
        query += " ORDER BY vi.FECHA_VISITA DESC " 
                + ") WHERE rnum >= " + filaInicio + " AND rnum <= " + filaFin;
        
        //System.err.println("reporte3: " + query);
        return query;
    }
    
    private String getReporte4(String filaInicio, String filaFin){
        String query = "SELECT * FROM (SELECT pv.codigo codigoProceso" 
                + ", con.CONTRATO numeroContrato" 
                + ", cl.NIT" 
                + ", pv.FK_CLIENTE codigoCliente" 
                + ", cl.RAZON_SOCIAL razonSocial" 
                + ", ae.CODIGO || ' - ' || ae.DESCRIPCION actividadProductiva" 
                + ", 'Dias: ' || mo.JORNADA_PRODUCTIVA_DIAS || ' - Horas: ' || mo.JORNADA_PRODUCTIVA_HORAS jornadaLaboral" 
                + ", NVL(us.DESCRIPCION, '') usoServicio" 
                + ", NVL(cl.DIRECCION, '') direccion" 
                + ", NVL(cl.BARRIO, '') barrio" 
                + ", NVL(cL.COMUNA, '') comuna" 
                + ", NVL(cl.TELEFONO, '') telefono" 
                + ", NVL(cl.NOMBRE_REPRESENTANTE_LEGAL, '...') nombreContacto" 
                + ", NVL(cl.EMAIL, '') email" 
                + ", ti.DESCRIPCION tipoInforme" 
                + ", eproc.CODIGO codigoEstadoProceso" 
                + ", eproc.DESCRIPCION estadoProceso" 
                + ", '...' notificado" 
                + ", TO_CHAR(ase.FECHA_ASESORIA, 'DD/MM/YYYY') fechaAsesoria" 
                + ", TO_CHAR(mo.FECHA_MONITOREO, 'DD/MM/YYYY') fechaMonitoreo" 
                + ", NVL(TO_CHAR(pv.FECHA_RADICACION, 'DD/MM/YYYY'), '...') fechaRadicacion" 
                + ", NVL(lab.NOMBRES, '...') labContacto" 
                + ", ROW_NUMBER() OVER (ORDER BY pv.CODIGO DESC) AS rnum" 
                + ", COUNT(*) OVER() AS total_rows" 
                + " FROM TB_CLIENTES cl " 
                + "LEFT OUTER JOIN TB_ACTIVIDADES_ECONOMICAS ae ON ae.CODIGO = cl.FK_ACTIVIDAD_ECONOMICA " 
                + "LEFT OUTER JOIN TB_TIPOS_USOS_SERVICIOS us ON us.CODIGO = cl.FK_USO_SERVICIO " 
                + "JOIN TB_PROCESOS_VERTIMIENTOS pv ON pv.FK_CLIENTE = cl.codigo " 
                + "LEFT OUTER JOIN TB_ESTADOS_PROCESOS eproc ON eproc.codigo = pv.FK_ESTADO_PROCESO " 
                + "LEFT OUTER JOIN TB_TIPOS_INFORMES ti ON ti.CODIGO = pv.FK_TIPO_INFORME " 
                + "JOIN TB_CONTRATOS con ON con.CONTRATO = pv.FK_CONTRATO " 
                + "LEFT OUTER JOIN TB_ASESORIAS ase ON ase.FK_PROCESO_VERTIMIENTO = pv.CODIGO " 
                + "LEFT OUTER JOIN TB_MONITOREOS mo ON mo.FK_PROCESO_VERTIMIENTO = pv.CODIGO AND mo.FK_ESTADO = 1 " 
                + "LEFT OUTER JOIN TB_LABORATORIOS_CONSULTOR lab ON lab.CODIGO = mo.FK_LABORATORIO AND lab.FK_TIPO_ENTIDAD = 1 " 
                + "WHERE 1 = 1 ";
        
        if (fechaInicial != null && !fechaInicial.equals("") && fechaFinal != null && !fechaFinal.equals("")) {
            query += " AND pv.FECHA_PROCESO BETWEEN '" + fechaInicial + "' AND '" + fechaFinal + "' ";
        }
        
        if (estadoProceso != null && !estadoProceso.equals("")) {
            query += " AND pv.FK_ESTADO_PROCESO = " + estadoProceso;
        }
        
        if(numeroContrato != null && !numeroContrato.equals("")){
            query += " AND pv.FK_CONTRATO = " + numeroContrato;
        }
        
        if(nit != null && !nit.equals("")){
            query += " AND cl.NIT LIKE '%" + nit.toUpperCase() + "%'";
        }
        
        if(actividadProductiva != null && !actividadProductiva.equals("")){
            query += " AND cl.FK_ACTIVIDAD_ECONOMICA = " + actividadProductiva;
        }
        
        if(razonSocial != null && !razonSocial.equals("")){
            query += " AND UPPER(cl.RAZON_SOCIAL) LIKE UPPER('%" + razonSocial.toUpperCase() + "%')";
        }
        
        if(comuna != null && !comuna.equals("")){
            query += " AND cl.COMUNA = " + comuna;
        }
        
        if(laboratorio != null && !laboratorio.equals("")){
            query += " AND mo.FK_LABORATORIO = " + laboratorio;
        }
        
        if(usoServicio != null && !usoServicio.equals("")){
            query += " AND cl.FK_USO_SERVICIO = " + usoServicio;
        }
        
        if(tipoInforme != null && !tipoInforme.equals("")){
            query += " AND pv.FK_TIPO_INFORME = " + tipoInforme;
        }
        
        query += " ORDER BY pv.CODIGO DESC " 
                + ") WHERE rnum >= " + filaInicio + " AND rnum <= " + filaFin;
        
        //System.err.println("reporte4: " + query);
        return query;
    }
    
    private String getReporte5(String filaInicio, String filaFin){
        /*String query = "SELECT * FROM (SELECT DISTINCT pv.codigo codigoProceso" 
                + ", pv.FECHA_PROCESO fechaProceso" 
                + ", con.CONTRATO numeroContrato" 
                + ", cl.NIT" 
                + ", pv.FK_CLIENTE codigoCliente" 
                + ", cl.RAZON_SOCIAL razonSocial" 
                + ", ae.DESCRIPCION actividadProductiva" 
                + ", 'Dias: ' || mo.JORNADA_PRODUCTIVA_DIAS || ' - Horas: ' || mo.JORNADA_PRODUCTIVA_HORAS jornadaLaboral" 
                + ", NVL(us.DESCRIPCION, '') usoServicio" 
                + ", NVL(cl.DIRECCION, '') direccion" 
                + ", NVL(cl.BARRIO, '') barrio" 
                + ", NVL(cL.COMUNA, '') comuna" 
                + ", NVL(cl.TELEFONO, '') telefono" 
                + ", NVL(cl.NOMBRE_REPRESENTANTE_LEGAL, '...') nombreContacto" 
                + ", NVL(cl.EMAIL, '') email" 
                + ", ti.DESCRIPCION tipoInforme" 
                + ", NVL(lab.NOMBRES, '...') labContacto" 
                + ", mo.ESTUVO_MONITOREO tuvoSupervision" 
                + ", (SELECT count(*) FROM TB_MONITOREOS x WHERE x.FK_PROCESO_VERTIMIENTO = pv.CODIGO AND mo.FECHA_MONITOREO > sysdate) cantidadMonitoreo" 
                + ", (SELECT count(*) FROM TB_VISITAS vi where vi.FK_PROCESO_VERTIMIENTO = pv.CODIGO AND vi.FK_ESTADO IN (1)) cantidadVisitas " 
                + ", ROW_NUMBER() OVER (ORDER BY pv.FECHA_PROCESO DESC) AS rnum" 
                + ", COUNT(*) OVER(ORDER BY pv.FECHA_PROCESO DESC) AS total_rows" 
                + " FROM TB_PROCESOS_VERTIMIENTOS pv " 
                + "LEFT OUTER JOIN TB_TIPOS_INFORMES ti ON ti.CODIGO = pv.FK_TIPO_INFORME " 
                + "JOIN TB_MONITOREOS mo ON mo.FK_PROCESO_VERTIMIENTO = pv.CODIGO AND mo.FECHA_MONITOREO > sysdate " 
                + "LEFT OUTER JOIN TB_LABORATORIOS_CONSULTOR lab ON lab.CODIGO = mo.FK_LABORATORIO AND lab.FK_TIPO_ENTIDAD = 1 " 
                + "JOIN TB_CLIENTES cl ON cl.CODIGO = pv.FK_CLIENTE " 
                + "LEFT OUTER JOIN TB_TIPOS_USOS_SERVICIOS us ON us.CODIGO = cl.FK_USO_SERVICIO " 
                + "LEFT OUTER JOIN TB_ACTIVIDADES_ECONOMICAS ae ON ae.CODIGO = cl.FK_ACTIVIDAD_ECONOMICA " 
                + "JOIN TB_CONTRATOS con ON con.CONTRATO = pv.FK_CONTRATO " 
                + "WHERE pv.FK_ESTADO_PROCESO IN (9) ";*/
        
        String query = "SELECT DISTINCT pv.codigo codigoProceso" 
                + ", pv.FECHA_PROCESO fechaProceso" 
                + ", con.CONTRATO numeroContrato" 
                + ", cl.NIT" 
                + ", pv.FK_CLIENTE codigoCliente" 
                + ", cl.RAZON_SOCIAL razonSocial" 
                + ", ae.DESCRIPCION actividadProductiva" 
                + ", 'Dias: ' || mo.JORNADA_PRODUCTIVA_DIAS || ' - Horas: ' || mo.JORNADA_PRODUCTIVA_HORAS jornadaLaboral" 
                + ", NVL(us.DESCRIPCION, '') usoServicio" 
                + ", NVL(cl.DIRECCION, '') direccion" 
                + ", NVL(cl.BARRIO, '') barrio" 
                + ", NVL(cL.COMUNA, '') comuna" 
                + ", NVL(cl.TELEFONO, '') telefono" 
                + ", NVL(cl.NOMBRE_REPRESENTANTE_LEGAL, '...') nombreContacto" 
                + ", NVL(cl.EMAIL, '') email" 
                + ", ti.DESCRIPCION tipoInforme" 
                + ", NVL(lab.NOMBRES, '...') labContacto" 
                + ", mo.ESTUVO_MONITOREO tuvoSupervision" 
                + ", mo.CODIGO codigoMonitoreo" 
                + ", TO_CHAR(mo.FECHA_MONITOREO, 'DD/MM/YYYY') fechaUltimoMonitoreo" 
                + ", (SELECT count(*) FROM TB_MONITOREOS x WHERE x.FK_PROCESO_VERTIMIENTO = pv.CODIGO AND mo.FECHA_MONITOREO > sysdate) cantidadMonitoreo" 
                + ", (SELECT count(*) FROM TB_VISITAS vi where vi.FK_PROCESO_VERTIMIENTO = pv.CODIGO AND vi.FK_ESTADO IN (1)) cantidadVisitas" 
                + " FROM TB_PROCESOS_VERTIMIENTOS pv " 
                + "LEFT OUTER JOIN TB_TIPOS_INFORMES ti ON ti.CODIGO = pv.FK_TIPO_INFORME " 
                + "JOIN TB_MONITOREOS mo ON mo.FK_PROCESO_VERTIMIENTO = pv.CODIGO AND mo.FK_ESTADO = 1 " 
                + "LEFT OUTER JOIN TB_LABORATORIOS_CONSULTOR lab ON lab.CODIGO = mo.FK_LABORATORIO AND lab.FK_TIPO_ENTIDAD = 1 " 
                + "JOIN TB_CLIENTES cl ON cl.CODIGO = pv.FK_CLIENTE " 
                + "LEFT OUTER JOIN TB_TIPOS_USOS_SERVICIOS us ON us.CODIGO = cl.FK_USO_SERVICIO " 
                + "LEFT OUTER JOIN TB_ACTIVIDADES_ECONOMICAS ae ON ae.CODIGO = cl.FK_ACTIVIDAD_ECONOMICA " 
                + "JOIN TB_CONTRATOS con ON con.CONTRATO = pv.FK_CONTRATO " 
                + "WHERE pv.FK_ESTADO_PROCESO IN (9) ";
        
        if (fechaInicial != null && !fechaInicial.equals("") && fechaFinal != null && !fechaFinal.equals("")) {
            query += " AND mo.FECHA_MONITOREO BETWEEN '" + fechaInicial + "' AND '" + fechaFinal + "' ";
        }
        
        if (estadoProceso != null && !estadoProceso.equals("")) {
            query += " AND pv.FK_ESTADO_PROCESO = " + estadoProceso;
        }
        
        if(numeroContrato != null && !numeroContrato.equals("")){
            query += " AND pv.FK_CONTRATO = " + numeroContrato;
        }
        
        if(nit != null && !nit.equals("")){
            query += " AND cl.NIT LIKE '%" + nit.toUpperCase() + "%'";
        }
        
        if(actividadProductiva != null && !actividadProductiva.equals("")){
            query += " AND cl.FK_ACTIVIDAD_ECONOMICA = " + actividadProductiva;
        }
        
        if(razonSocial != null && !razonSocial.equals("")){
            query += " AND UPPER(cl.RAZON_SOCIAL) LIKE UPPER('%" + razonSocial + "%')";
        }
        
        if(comuna != null && !comuna.equals("")){
            query += " AND cl.COMUNA = " + comuna;
        }
        
        if(usoServicio != null && !usoServicio.equals("")){
            query += " AND cl.FK_USO_SERVICIO = " + usoServicio;
        }
        
        /*query += " ORDER BY pv.FECHA_PROCESO DESC " 
                + ") WHERE rnum >= " + filaInicio + " AND rnum <= " + filaFin;*/
        
        query += " ORDER BY pv.FECHA_PROCESO DESC ";
        
        //System.err.println("reporte5: " + query);
        return query;
    }
    
    private String getReporte6(String[] paramFisicoQuimico, String[] rangoInicial, String[] rangoFinal, String filaInicio, String filaFin){
        String query = "SELECT * FROM (SELECT pv.codigo codigoProceso" 
                + ", con.CONTRATO numeroContrato" 
                + ", cl.CODIGO codigoCliente" 
                + ", cl.NIT" 
                + ", cl.RAZON_SOCIAL razonSocial" 
                + ", NVL(cl.DIRECCION, '') direccion" 
                + ", NVL(cl.BARRIO, '') barrio" 
                + ", NVL(cL.COMUNA, '') comuna" 
                + ", NVL(us.DESCRIPCION, '') usoServicio" 
                + ", ae.CODIGO || ' - ' || ae.DESCRIPCION actividadProductiva" 
                + ", ptoV.DESCRIPCION  puntoVertimiento" 
                + ", jor.JORNADA jornada" 
                + ", pa.DESCRIPCION descParametro" 
                + ", detjor.VALOR valorParametro" 
                + ", ROW_NUMBER() OVER (ORDER BY pv.FECHA_PROCESO DESC) AS rnum" 
                + ", COUNT(*) OVER(ORDER BY pv.FECHA_PROCESO DESC) AS total_rows" 
                + " FROM TB_CLIENTES cl " 
                + "LEFT OUTER JOIN TB_ACTIVIDADES_ECONOMICAS ae ON ae.CODIGO = cl.FK_ACTIVIDAD_ECONOMICA " 
                + "LEFT OUTER JOIN TB_TIPOS_USOS_SERVICIOS us ON us.CODIGO = cl.FK_USO_SERVICIO " 
                + "LEFT OUTER JOIN TB_PROCESOS_VERTIMIENTOS pv ON pv.FK_CLIENTE = cl.codigo " 
                + "LEFT OUTER JOIN TB_ESTADOS_PROCESOS eproc ON eproc.codigo = pv.FK_ESTADO_PROCESO " 
                + "JOIN TB_TIPOS_INFORMES ti ON ti.CODIGO = pv.FK_TIPO_INFORME AND ti.CODIGO = 4 " 
                + "JOIN TB_CONTRATOS con ON con.CONTRATO = pv.FK_CONTRATO " 
                + "JOIN TB_ASESORIAS ase ON ase.FK_PROCESO_VERTIMIENTO = pv.CODIGO " 
                + "JOIN TB_MONITOREOS mo ON mo.FK_PROCESO_VERTIMIENTO = pv.CODIGO AND mo.FK_ESTADO = 1 " 
                + "JOIN TB_PUNTOS_MONITOREOS ptomo ON ptomo.FK_MONITOREO = mo.CODIGO " 
                + "JOIN TB_JORNADAS_PUNTOS_MONITOREOS jor ON jor.FK_PUNTO_MONITOREO = ptomo.CODIGO " 
                + "JOIN TB_DETALLES_JORNADAS detjor ON detjor.FK_JORNADA_PUTOS_MONITOREO = jor.CODIGO " 
                + "JOIN TB_PARAM_FISICOQUIMICOS pa ON pa.CODIGO = detjor.FK_PARAM_FISICOQUIMICO " 
                + "JOIN TB_PUNTOS_VERTIMIENTOS ptoV ON ptoV.CODIGO = detjor.FK_PUNTO_MONITOREO " 
                + "WHERE 1 = 1 ";
        
        if (fechaInicial != null && !fechaInicial.equals("") && fechaFinal != null && !fechaFinal.equals("")) {
            query += " AND pv.FECHA_PROCESO BETWEEN '" + fechaInicial + "' AND '" + fechaFinal + "' ";
        }
        
        if(numeroContrato != null && !numeroContrato.equals("")){
            query += " AND pv.FK_CONTRATO = " + numeroContrato;
        }
        
        if(nit != null && !nit.equals("")){
            query += " AND cl.NIT LIKE '%" + nit.toUpperCase() + "%'";
        }
        
        if(actividadProductiva != null && !actividadProductiva.equals("")){
            query += " AND cl.FK_ACTIVIDAD_ECONOMICA = " + actividadProductiva;
        }
        
        if(usoServicio != null && !usoServicio.equals("")){
            query += " AND cl.FK_USO_SERVICIO = " + usoServicio;
        }
        
        if(paramFisicoQuimico != null && paramFisicoQuimico.length > 1){
            query += " AND (";
            for (int i = 0; i < paramFisicoQuimico.length; i++) {
                if (i == 0) {
                    query += "(detjor.FK_PARAM_FISICOQUIMICO = " + paramFisicoQuimico[i] 
                        + " AND detjor.VALOR BETWEEN " + rangoInicial[i] + " AND " + rangoFinal[i] + ")";
                }else{
                    query += " OR (detjor.FK_PARAM_FISICOQUIMICO = " + paramFisicoQuimico[i] 
                        + " AND detjor.VALOR BETWEEN " + rangoInicial[i] + " AND " + rangoFinal[i] + ")";
                }
            }
            query += ") ";
        }
        
        query += " ORDER BY mo.FECHA_MONITOREO DESC " 
                + ") WHERE rnum >= " + filaInicio + " AND rnum <= " + filaFin;
        
        System.err.println("reporte6: " + query);
        return query;
    }
    
    private String getReporte7(String lodoInicial, String lodoFinal, String tasaInicial, String tasaFinal, String filaInicio, String filaFin){
        String query = "SELECT * FROM (SELECT pv.codigo codigoProceso" 
                + ", con.CONTRATO numeroContrato" 
                + ", cl.CODIGO codigoCliente" 
                + ", cl.NIT" 
                + ", cl.RAZON_SOCIAL razonSocial" 
                + ", NVL(cl.DIRECCION, '') direccion" 
                + ", NVL(cl.BARRIO, '') barrio" 
                + ", NVL(cL.COMUNA, '') comuna" 
                + ", NVL(us.DESCRIPCION, '') usoServicio" 
                + ", ae.CODIGO || ' - ' || ae.DESCRIPCION actividadProductiva" 
                + ", eml.VOLUMEN volumenLodo" 
                + ", mo.VALOR_TASA_RETRIBUTIVA tasaRetributiva" 
                + ", ROW_NUMBER() OVER (ORDER BY pv.FECHA_PROCESO DESC) AS rnum" 
                + ", COUNT(*) OVER(ORDER BY pv.FECHA_PROCESO DESC) AS total_rows" 
                + " FROM TB_CLIENTES cl " 
                + "LEFT OUTER JOIN TB_ACTIVIDADES_ECONOMICAS ae ON ae.CODIGO = cl.FK_ACTIVIDAD_ECONOMICA " 
                + "LEFT OUTER JOIN TB_TIPOS_USOS_SERVICIOS us ON us.CODIGO = cl.FK_USO_SERVICIO " 
                + "LEFT OUTER JOIN TB_PROCESOS_VERTIMIENTOS pv ON pv.FK_CLIENTE = cl.codigo " 
                + "LEFT OUTER JOIN TB_ESTADOS_PROCESOS eproc ON eproc.codigo = pv.FK_ESTADO_PROCESO " 
                + "JOIN TB_TIPOS_INFORMES ti ON ti.CODIGO = pv.FK_TIPO_INFORME AND ti.CODIGO = 4 " 
                + "JOIN TB_CONTRATOS con ON con.CONTRATO = pv.FK_CONTRATO " 
                + "JOIN TB_MONITOREOS mo ON mo.FK_PROCESO_VERTIMIENTO = pv.CODIGO AND mo.FK_ESTADO = 1 " 
                + "JOIN TB_TIPOS_LODOS tl ON tl.CODIGO = mo.FK_TIPO_LODO " 
                + "JOIN TB_ENTIDADES_MANEJOS_LODOS eml ON eml.FK_MONITOREO = mo.CODIGO " 
                + "WHERE 1 = 1 ";
        
        if (fechaInicial != null && !fechaInicial.equals("") && fechaFinal != null && !fechaFinal.equals("")) {
            query += " AND pv.FECHA_PROCESO BETWEEN '" + fechaInicial + "' AND '" + fechaFinal + "' ";
        }
        
        if(numeroContrato != null && !numeroContrato.equals("")){
            query += " AND pv.FK_CONTRATO = " + numeroContrato;
        }
        
        if(nit != null && !nit.equals("")){
            query += " AND cl.NIT LIKE '%" + nit.toUpperCase() + "%'";
        }
        
        if(actividadProductiva != null && !actividadProductiva.equals("")){
            query += " AND cl.FK_ACTIVIDAD_ECONOMICA = " + actividadProductiva;
        }
        
        if(usoServicio != null && !usoServicio.equals("")){
            query += " AND cl.FK_USO_SERVICIO = " + usoServicio;
        }
        
        if (lodoInicial != null && !lodoInicial.equals("") && lodoFinal != null && !lodoFinal.equals("")) {
            query += "AND eml.VOLUMEN BETWEEN '" + lodoInicial + "' AND '" + lodoFinal + "' ";
        }
        
        if (tasaInicial != null && !tasaInicial.equals("") && tasaFinal != null && !tasaFinal.equals("")) {
            query += "AND mo.VALOR_TASA_RETRIBUTIVA BETWEEN '" + tasaInicial + "' AND '" + tasaFinal + "' ";
        }
        
        query += " ORDER BY pv.FECHA_PROCESO DESC " 
                + ") WHERE rnum >= " + filaInicio + " AND rnum <= " + filaFin;
        
        //System.err.println("reporte7: " + query);
        return query;
    }
    
    public ResultSet obtenerMonitoreosXProceso(String codigoProceso) throws Exception{
        String query = "SELECT mo.FK_PROCESO_VERTIMIENTO codigoProceso"
                + ", mo.CODIGO codigoMonitoreo" 
                + ", NVL(lab.CONTACTO, '...') labContacto" 
                + ", mo.ESTUVO_MONITOREO tuvoMonitoreo" 
                + ", te.NOMBRES || te.APELLIDOS nombreTecnico" 
                + ", mo.OBSERVACION_SUPERVISION obseSupervision" 
                + ", TO_CHAR(mo.FECHA_MONITOREO, 'DD-MM-YYYY') fechaUltimoMonitoreo" 
                + ", mo.OBSERVACION obseMonitoreo" 
                + " FROM TB_MONITOREOS mo " 
                + "LEFT OUTER JOIN TB_LABORATORIOS_CONSULTOR lab ON lab.CODIGO = mo.FK_LABORATORIO AND lab.FK_TIPO_ENTIDAD = 1 " 
                + "LEFT OUTER JOIN TB_TECNICOS te ON te.CODIGO = mo.FK_TECNICO_SUPERVISION " 
                + "WHERE mo.FK_ESTADO = 1 " 
                + " AND mo.FK_PROCESO_VERTIMIENTO = " + codigoProceso 
                + "ORDER BY mo.FECHA_MONITOREO";
        //System.err.println("obtenerMonitoreosXProceso: " + query);
        ResultSet rset = db.ejecutar(conn, query);
        return rset;
    }
    
    public ResultSet obtenerVisitasXProceso(String codigoProceso) throws Exception{
        String query = "SELECT vi.CODIGO codigoVisita" 
                + ", vi.FK_PROCESO_VERTIMIENTO codigoProceso"
                + ", te.NOMBRES || te.APELLIDOS nombreTecnico" 
                + ", tvi.DESCRIPCION tipoVisita" 
                + ", movi.DESCRIPCION motivoVisita" 
                + ", vi.RESULTADO resultadoVisita" 
                + ", TO_CHAR(vi.FECHA_VISITA, 'DD/MM/YYYY') fechaVisita" 
                + ", vi.OBSERVACIONES obseVisita" 
                + ", es.DESCRIPCION estadoVisita" 
                + " FROM TB_VISITAS vi " 
                + "LEFT OUTER JOIN TB_TECNICOS te ON te.CODIGO = vi.FK_TECNICO " 
                + "LEFT OUTER JOIN TB_TIPOS_VISITAS tvi ON tvi.CODIGO = vi.FK_TIPO_VISITA " 
                + "LEFT OUTER JOIN TB_ESTADOS es ON es.CODIGO = vi.FK_ESTADO " 
                + "LEFT OUTER JOIN TB_MOTIVOS_VISITAS movi ON movi.CODIGO = vi.FK_MOTIVO_VISITA " 
                + "WHERE vi.FK_PROCESO_VERTIMIENTO = " + codigoProceso 
                + " ORDER BY vi.FECHA_VISITA DESC";
        //System.err.println("obtenerVisitasXProceso: " + query);
        ResultSet rset = db.ejecutar(conn, query);
        return rset;
    }
    
    public ResultSet obtenerHistorialDagmaXProceso(String codigoProceso) throws Exception{
        String query = "SELECT hd.CODIGO codigoHistorialDagma" 
                + ", hd.FK_PROCESO_VERTIMIENTO codigoProceso"
                + ", hd.RADICADO radicadoHistorialDagma" 
                + ", TO_CHAR(hd.FECHA_RADICADO, 'DD/MM/YYYY') fechaHistorialDagma" 
                + ", hd.OBSERVACION obseHistorialDagma" 
                + " FROM TB_HISTORIALES_DAGMA hd " 
                + "WHERE hd.FK_PROCESO_VERTIMIENTO = " + codigoProceso 
                + " ORDER BY hd.FECHA_RADICADO DESC";
        //System.err.println("obtenerVisitasXProceso: " + query);
        ResultSet rset = db.ejecutar(conn, query);
        return rset;
    }
    
    public ResultSet obtenerAdjuntosXProceso(String codigoProceso) throws Exception{
        String query = "SELECT adj.CODIGO codigoAdjunto" 
                + ", adj.FK_PROCESO_VERTIMIENTO codigoProceso" 
                + ", adj.NOMBRE_ARCHIVO nombreArchivo" 
                + ", TO_CHAR(adj.FECHA_CREACION, 'DD/MM/YYYY') fechaArchivo" 
                + " FROM TB_ARCHIVOS_ADJUNTOS_INFORMES adj " 
                + "WHERE adj.FK_PROCESO_VERTIMIENTO = " + codigoProceso 
                + " ORDER BY adj.FECHA_CREACION";
        //System.err.println("obtenerAdjuntosXProceso: " + query);
        ResultSet rset = db.ejecutar(conn, query);
        return rset;
    }
    
    public ResultSet obtenerAdjuntosXVisita(String codigoVisita) throws Exception{
        String query = "SELECT adj.CODIGO codigoAdjunto" 
                + ", adj.FK_VISITA codigoVisita" 
                + ", adj.NOMBRE_ARCHIVO nombreArchivo"
                + " FROM TB_ARCHIVOS_ADJUNTOS_VISITAS adj " 
                + "WHERE adj.FK_VISITA = " + codigoVisita;
        //System.err.println("obtenerAdjuntosXVisita: " + query);
        ResultSet rset = db.ejecutar(conn, query);
        return rset;
    }
    
    //HistorialDagma
    public ResultSet obtenerAdjuntosXHistorialDagma(String codigoHistorialDagma) throws Exception{
        String query = "SELECT adj.CODIGO codigoAdjunto" 
                + ", adj.FK_HISTORIA_DAGMA codigoHistorialDagma" 
                + ", adj.NOMBRE_ARCHIVO nombreArchivo"
                + " FROM TB_ARCHIVOS_HIST_DAGMA adj " 
                + "WHERE adj.FK_HISTORIA_DAGMA = " + codigoHistorialDagma;
        //System.err.println("obtenerAdjuntosXHistorialDagma: " + query);
        ResultSet rset = db.ejecutar(conn, query);
        return rset;
    }
    
    public ResultSet obtenerArchivoHistorialDagma(Integer codigoHistorialDagma, Integer codigoArchivo){
        String query = "SELECT adj.CODIGO codigoAdjunto" 
                + ", adj.FK_HISTORIA_DAGMA codigoHistorialDagma" 
                + ", adj.NOMBRE_ARCHIVO nombreArchivo"
                + ", adj.ARCHIVO archivo"
                + " FROM TB_ARCHIVOS_HIST_DAGMA adj " 
                + "WHERE adj.FK_HISTORIA_DAGMA = " + codigoHistorialDagma 
                + " AND adj.CODIGO = " + codigoArchivo;
        //System.err.println("obtenerArchivoHistorialDagma: " + query);
        ResultSet rset = db.ejecutar(conn, query);
        return rset;
       
    }
    
    public ResultSet obtenerHistorialDagma(String codigo){
        String query = "SELECT CODIGO, FK_PROCESO_VERTIMIENTO, to_char(FECHA_RADICADO,'dd/mm/yyyy') AS FECHA_RADICADO, RADICADO FROM TB_HISTORIALES_DAGMA WHERE FK_PROCESO_VERTIMIENTO = "+codigo;
        ResultSet rset = db.ejecutar(conn, query);
        return rset;
    }
    
    public ResultSet obtenerRegistroDagma(String codigoHistorial){
        String query = "SELECT CODIGO, OBSERVACION, FK_TIPO_RADICADO, to_char(FECHA_RADICADO,'dd/mm/yyyy') AS FECHA_RADICADO, RADICADO FROM TB_HISTORIALES_DAGMA WHERE CODIGO = " + codigoHistorial;
        ResultSet rset = db.ejecutar(conn, query);
        return rset;
    }
    
    public ResultSet obtenerHistExcelDagma(String codigoProceso){
      String query = Queries.getString("SelHistorialExcelDagma") +" " + codigoProceso; 
      ResultSet rset = db.ejecutar(conn, query);
      return rset;
    }
    
    public int insertarHistorialDagma(String radicado, String fechaRadicado, String observacion,
                                        int proceso, int tipoRadicado) throws Exception{
       
        CallableStatement callableStatement = null;

        //Conectamos con la bd.
        Connection conn = db.conectar();

        try{

            //Preparamos y ejecutamos el procedimiento.
            callableStatement = conn.prepareCall(Queries.
                    getString("SpInsertarHistoriaDagma"));

            //Preparamos los parametros            
            callableStatement.setString(1, radicado);
            callableStatement.setString(2, fechaRadicado);
            callableStatement.setString(3, observacion);
            callableStatement.setInt(4, proceso);
            callableStatement.setInt(5, tipoRadicado);
            
            //Obtenemos el paramertro de salida el SP
            callableStatement.registerOutParameter(6, java.sql.Types.INTEGER);

            //Ejecutamos el procedimiento
            callableStatement.execute();
            
            //Almacenamos el paramertro de salida en la variable error          
            int codigoHistorial =  callableStatement.getInt(6);

            //Cerramos la conexion
            db.desconectar(conn);

            return codigoHistorial;
            
        }catch(Exception e){

            db.desconectar(conn);
            throw e;

        }
    }
    
    public void actualizarRegistroDagma(String radicado, String fechaRadicado, int procesoVertimiento, int tipoRadicado, int codigoHistorial, String observacion){
        
        //Conectamos con la bd.
        CallableStatement callableStatement;
        Connection conn = db.conectar();

        try{

            //Preparamos y ejecutamos el procedimiento.
            callableStatement = conn.prepareCall(Queries.
                    getString("ActualizarHistDagma"));

            //Preparamos los parametros            
            callableStatement.setString(1, radicado);
            callableStatement.setString(2, fechaRadicado);
            callableStatement.setInt(3, procesoVertimiento);
            callableStatement.setInt(4, tipoRadicado);
            callableStatement.setString(5, observacion);
            callableStatement.setInt(6, codigoHistorial);
            
            

            //Ejecutamos el procedimiento
            callableStatement.execute();
            

            //Cerramos la conexion
            db.desconectar(conn);
            
        }catch(Exception e){

            db.desconectar(conn);

        }
    }
    
    public int eliminarHistorialDagma(int codigoHistorial) throws Exception{

        CallableStatement callableStatement = null;

        //Conectamos con la bd.
        Connection conn = db.conectar();

        try{

            //Preparamos y ejecutamos el procedimiento.
            callableStatement = conn.prepareCall(Queries.
                    getString("SpEliminarHistDagma"));

            //Preparamos los parametros            
            callableStatement.setInt(1, codigoHistorial);
                      
            //Obtenemos el paramertro de salida el SP
            callableStatement.registerOutParameter(2, java.sql.Types.INTEGER);

            //Ejecutamos el procedimiento
            callableStatement.execute();
            
            //Almacenamos el paramertro de salida en la variable error          
            int error =  callableStatement.getInt(2);

            //Cerramos la conexion
            db.desconectar(conn);
            
            return error;

        }catch(Exception e){

            db.desconectar(conn);
            throw e;

        }
    }
        
    public void desconectar(){
        db.desconectar(this.conn);
    }
}