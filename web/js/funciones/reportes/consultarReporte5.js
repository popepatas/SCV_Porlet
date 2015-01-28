/**
 *
 * @author jmrincon
 */

//VARIABLES
var _comuna = null;

$(document).on("ready", inicio);

function inicio(){
    //Se inicializa el validador
    $("#div_Reporte5").kendoValidator({
        rules: {
            /*Evita la validación incorrecta del formato de la fecha*/
            date: function (input) {
                var attr = input.attr("data-role");
                if( attr === "datepicker"){
                    var fecha = kendo.parseDate(input.val(), "dd/MM/yyyy");
                    return fecha instanceof Date;
                }
                return true;
            }/*,
            customRule1: function(input) {
             if (input.is("[name=rangoFinal]") && input.is("[name=rangoFinal]").val() < input.is("[name=rangoInicial]").val() ) {
                 return false;
             }
             return true;
           }*/
        },
        messages:{
            customRule1: "El Rango Final no puede ser mayor al Rango Inicial",
            customRule2: "El Rango de Años no puede ser mayor a 10 años",
            required:"Ingrese un valor para este campo"
        }
    });
    
    //Eventos
    $("#consultar").on("click", generarGrilla);
    $("#limpiar").on("click",limpiarFormulario);
    
    //Campos
    $("#fechaInicial,#fechaFinal").kendoDatePicker({format: "dd/MM/yyyy"});
    $("#nitConsultar").on("keypress", function(){return  campo.nit(event, this);});
    
    /*var estadosProceso = servlet.consultar.estadosProcesos + '?opcion=1';
    campo.lista.crear("estadoProceso", estadosProceso, {});*/
    
    var urlAct = servlet.consultar.actEconomica;
    campo.lista.crear("actividadProductiva", urlAct, {});
    
    /*var tiposInforme = servlet.consultar.tiposInformeVertimientos+ '?opcion=1';
    campo.lista.crear("tipoInforme", tiposInforme, {});*/
    
    _comuna = $("#comuna").kendoNumericTextBox({format: "#", decimals: 0, min: 0}).data("kendoNumericTextBox");
    
    //ModalBox
    $(document).on("click", ".logRazon", {tipoCampo:"Razón Social", opcion:2}, mostrarLogsCliente);
    $(document).on("click", ".logMonitoreos", {titulo: "Monitoreos", opcion: 2}, mostrarMonitoreos);
}

function limpiarFormulario(){
    campo.limpiar("fechaInicial");
    campo.limpiar("fechaFinal");
    campo.limpiar("numeroContrato");
    campo.limpiar("nitConsultar");
    campo.limpiar("razonSocial");
    _comuna.value($("#comuna").val(null));
    campo.limpiar("actividadProductiva");
    campo.limpiar("usoServicio");
    
    $('#grillaReporte5').data().kendoGrid.destroy();
    $('#grillaReporte5').empty();
}

function generarGrilla(){
    var url = servlet.consultar.reporte5; 
    
    var parametros = { 
        fechaInicial: campo.obtener("fechaInicial")
        , fechaFinal: campo.obtener("fechaFinal")
        , numeroContrato: campo.obtener("numeroContrato")
        , nit: campo.obtener("nitConsultar")
        , razonSocial: campo.obtener("razonSocial")
        , comuna: campo.obtener("comuna")
        , actividadProductiva: campo.obtener("actividadProductiva")
        , usoServicio: campo.obtener("usoServicio")
    };
    
    var dataSource = new kendo.data.DataSource({
        transport: { read: {url: url, data: parametros, dataType: "json"}},
        batch: true,
        resizable: true,
        pageSize: 20,
        serverPaging: true,
        schema: {
            total: function(response) {
                if(response.length > 0){
                    return response[0].total;
                }
            },
            model: {
                id: "codigoProceso",
                fields: {
                    codigoProceso: {type: "string", editable: false }
                    , numeroContrato: {type: "string", editable: false }
                    , nit: {type: "string", editable: false }
                    , codigoCliente: {type: "string", editable: false}
                    , razonSocial: {type: "string",  editable: false }
                    , actividadProductiva: {type: "string", editable: false }
                    , jornadaLaboral: {type: "string", editable: false }
                    , usoServicio: {type: "string", editable: false }
                    , direccion: {type: "string", editable: false }
                    , barrio: {type: "string", editable: false }
                    , comuna: {type: "string", editable: false }
                    , telefono: {type: "string", editable: false }
                    , nombreContacto: {type: "string", editable: false }
                    , email: {type: "string", editable: false }
                    , tipoInforme: {type: "string", editable: false }
                    , estadoProceso: {type: "string", editable: false }
                    , labContacto: {type: "string", editable: false }
                    , tuvoSupervision: {type: "string", editable: false }
                    , codigoUltimoMonitoreo: {type: "string", editable: false }
                    , fechaUltimoMonitoreo: {type: "string", editable: false }
                }
            }
        }
    });
    
    $("#grillaReporte5").kendoGrid({
        height: 400,
        width: 800,
        autoSync: true,
        groupable: true,
        sortable: false,
        pageable: true,
        scrollable: false,
        dataSource: dataSource,
        toolbar: [{ name: "Excel", imageClass: "k-icon k-i-tick"}],
        columns:[
           { field:"codigoProceso", title: "Código Proceso", width: "100", groupable: false}
            , { field:"numeroContrato", title:"Contrato", width: "100"}
            , { field:"nit", title:"NIT", width: "100"}
            , {field: "razonSocial", title: "Razon Social", width: "100", 
                template: "<span  class=\"logRazon k-link\">#: razonSocial # <input type=\"hidden\" value=\"#: codigoCliente#\"></span>"}
            , { field:"actividadProductiva", title:"Act. Prod.", width: "100"}
            , { field:"jornadaLaboral", title:"Jornada Laboral", width: "100", groupable: false}
            , { field:"usoServicio", title:"Uso Servicio", width: "100"}
            , { field:"direccion", title:"Dirección", width: "150", groupable: false}
            , { field:"barrio", title:"Barrio", width: "100"}
            , { field:"comuna", title:"Comuna", width: "100", groupable: false}
            , { field:"telefono", title:"Teléfono", width: "100", groupable: false}
            , { field:"nombreContacto", title:"Contacto", width: "100", groupable: false}
            , { field:"email", title:"Email", width: "100", groupable: false}
            , { field:"tipoInforme", title:"Tipo Informe", width: "100"}
            , { field:"labContacto", title:"Lab. / Consultor", width: "100", groupable: false}
            , { field:"tuvoSupervision", title:"Supervision", width: "100", groupable: false}
            , {field: "fechaUltimoMonitoreo", title: "Fecha Ultimo Monitoreo", width: "100", groupable: false,
                template: "<span  class=\"logMonitoreos k-link\">#: fechaUltimoMonitoreo # <input type=\"hidden\" value=\"#: codigoProceso#\"></span>"}
        ]
    });
    
    $(".k-grid-toolbar", "#grillaReporte5").prepend("Opciones:");
    
    $(".k-grid-Excel", "#grillaReporte5").bind("click", function (ev) {
        exportGridToExcelReporte5();
    });
}

function exportGridToExcelReporte5() {
    var grid = $("#grillaReporte5").data("kendoGrid");
    var currentPage = grid.dataSource.page();
    var allPages = new Array();
    for (var n = 1; n <= grid.dataSource.totalPages(); n++) {
        grid.dataSource.page(n);
        var view = grid.dataSource.view();
        for (var x = 0; x < view.length; x++) {
            allPages.push(view[x]);
        }
    }
    $("#excelExportGridData").val(JSON.stringify({"rows": allPages, "cols": grid.columns}));
    $("#exportToExcelForm").submit();
    grid.dataSource.page(currentPage);
}

function mostrarLogsCliente(event){
    var parametros = event.data;
    parametros.codigo = $(this).find("input[type='hidden']").val();
    var titulo = " Log "+ parametros.tipoCampo;
    $("#modalBoxCliente").load("../cliente/LogsCliente.jsp", function(response, status, xhrGrillaLogs){
        xhrGrillaLogs.done(function(){
            generarGrillaLogsCliente(parametros);
            var modal = modalBox.abrir("modalBoxCliente", null, "800px", titulo, null);
            modal.open();
        });
    });
}

function generarGrillaLogsCliente(param){
    var url = servlet.consultar.logsCliente;
    var parametros = {tipoCampo: param.tipoCampo, codigo: param.codigo};
    var dataSource = new kendo.data.DataSource({
        transport: {read: {url: url, data:parametros, dataType: "json"}},
        batch: true,
        pageSize: 20,
        schema: {
            model: {
                fields: {
                    campo: { editable: false }
                    , valorAnterior: { editable: false }
                    , valorPosterior: { editable: false }
                    , fechaModificacion: { editable: false }
                }
            }
        }
    });

    $("#grillaLogsCliente").kendoGrid({
        height: 430,
        width:800,
        autoSync: true,
        dataSource: dataSource,
        columns:[
            {field: "campo", title: "Campo", width: "100px"}
            , {field: "valorAnterior", title: "Valor Anterior", width: "100px"}
            , {field: "valorPosterior", title: "Valor Posterior", width: "100px"}
            , {field: "fechaModificacion", title: "Fecha Modificacion", width: "100px"}
        ],
        pageable: {refresh: true}
    });
}

function mostrarMonitoreos(event){
    var parametros = event.data;
    parametros.codigoProceso = $(this).find("input[type='hidden']").val();
    var titulo = parametros.titulo;
    $("#modalBoxMonitoreos").load("../reportes/LogsMonitoreos.jsp", function(response, status, xhrGrillaLogs){
        xhrGrillaLogs.done(function(){
            generarGrillaMonitoreos(parametros);
            var modal = modalBox.abrir("modalBoxMonitoreos", null, "800px", titulo, null);
            modal.open();
        });
    });
}

function generarGrillaMonitoreos(param){
    var url = servlet.consultar.reporteMonitoreosProceso ; 
    var parametros = {codigoProceso: param.codigoProceso};
    var dataSourceVisitas = new kendo.data.DataSource({
        transport: {read: {url: url, data: parametros, dataType: "json", type:"POST"}},
        batch: true,
        pageSize: 20,
        schema: {
            total: function(response) {
                if(response.length > 0){
                    return response[0].total;
                }
            },
            model: {
                id: "codigoMonitoreo",
                fields: {
                    codigoMonitoreo: { editable: false},
                    codigoProceso: { editable: false },
                    labContacto: { editable: false},
                    tuvoMonitoreo: { editable: false },
                    nombreTecnico: { editable: false},
                    obseSupervision: { editable: false},
                    fechaUltimoMonitoreo: { editable: false },
                    obseMonitoreo: { editable: false }
                }
            }
        }
    });
    
    $("#grillaMonitoreos").kendoGrid({
        height: 430,
        width:800,
        autoSync: true,
        dataSource: dataSourceVisitas,
        pageable: {refresh: true},
        columns: [
            { field: "codigoMonitoreo", title: "Codigo", width: "50px"}
            //, { field: "codigoProceso", title: "Codigo", width: "50px"}
            , { field: "labContacto", title: "Laboratorio", width: "200px"}
            , { field: "tuvoMonitoreo", title: "Monitoreado", width: "160px"}
            , { field: "nombreTecnico", title: "Tecnico", width: "160px"}
            , { field: "obseSupervision", title: "Obse. Supervision", width: "160px"}
            , { field: "fechaUltimoMonitoreo", title: "Fecha", width: "160px"}
            , { field: "obseMonitoreo", title: "Observacion", width: "160px"}
        ]
    });
}