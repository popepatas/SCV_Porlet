/**
 *
 * @author jmrincon
 */

//VARIABLES
var _lodoInicial = null;
var _lodoFinal = null;
var _tasaInicial = null;
var _tasaFinal = null;

$(document).on("ready", inicio);

function inicio(){
    //Se inicializa el validador
    $("#div_Reporte7").kendoValidator({
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
    $("#limpiar").on("click", limpiarFormulario);
    
    //Campos
    $("#fechaInicial,#fechaFinal").kendoDatePicker({format: "dd/MM/yyyy"});
    $("#nitConsultar").on("keypress", function(){return  campo.nit(event, this);});
    
    var urlAct = servlet.consultar.actEconomica;
    campo.lista.crear("actividadProductiva", urlAct, {});
    
    _lodoInicial = $("#lodoInicial").kendoNumericTextBox({format: "#", decimals: 0, min: 0}).data("kendoNumericTextBox");
    _lodoFinal = $("#lodoFinal").kendoNumericTextBox({format: "#", decimals: 0, min: 0}).data("kendoNumericTextBox");
    
    _tasaInicial = $("#tasaInicial").kendoNumericTextBox({format: "#", decimals: 0, min: 0}).data("kendoNumericTextBox");
    _tasaFinal = $("#tasaFinal").kendoNumericTextBox({format: "#", decimals: 0, min: 0}).data("kendoNumericTextBox");
    
    //ModalBox
    $(document).on("click", ".logRazon", {tipoCampo:"Razón Social", opcion:2}, mostrarLogsCliente);
    $(document).on("click", ".logMonitoreos", {titulo: "Monitoreos", opcion: 2}, mostrarMonitoreos);
}

function limpiarFormulario(){
    campo.limpiar("fechaInicial");
    campo.limpiar("fechaFinal");
    campo.limpiar("numeroContrato");
    campo.limpiar("nitConsultar");
    campo.limpiar("actividadProductiva");
    campo.limpiar("usoServicio");
    _lodoInicial.value($("#lodoInicial").val(null));
    _lodoFinal.value($("#lodoFinal").val(null));
    _tasaInicial.value($("#tasaInicial").val(null));
    _tasaFinal.value($("#tasaFinal").val(null));
    
    $('#grillaReporte7').data().kendoGrid.destroy();
    $('#grillaReporte7').empty();
}

function generarGrilla(){
    var url = servlet.consultar.reporte7; 
    
    var parametros = { 
        fechaInicial: campo.obtener("fechaInicial")
        , fechaFinal: campo.obtener("fechaFinal")
        , numeroContrato: campo.obtener("numeroContrato")
        , nit: campo.obtener("nitConsultar")
        , actividadProductiva: campo.obtener("actividadProductiva")
        , usoServicio: campo.obtener("usoServicio")
        , lodoInicial: campo.obtener("lodoInicial")
        , lodoFinal: campo.obtener("lodoFinal") 
        , tasaInicial: campo.obtener("tasaInicial")
        , tasaFinal: campo.obtener("tasaFinal") 
    };
    
    var dataSource = new kendo.data.DataSource({
        transport: { read: {url: url, data: parametros, dataType: "json",cache:false}},
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
                    , codigoCliente: {type: "string",  editable: false }
                    , nit: {type: "string", editable: false }
                    , razonSocial: {type: "string",  editable: false }
                    , direccion: {type: "string", editable: false }
                    , barrio: {type: "string", editable: false }
                    , comuna: {type: "string", editable: false }
                    , usoServicio: {type: "string", editable: false }
                    , actividadProductiva: {type: "string", editable: false }
                    , volumenLodo: {type: "string", editable: false }
                    , tasaRetributiva: {type: "string", editable: false }
                }
            }
        }
    });
    
    $("#grillaReporte7").kendoGrid({
        height: 400,
        width: 800,
        autoSync: true,
        groupable: true,
        sortable: false,
        pageable: true,
        scrollable: false,
        dataSource: dataSource,
        toolbar: [
            { name: "Excel", imageClass: "k-icon k-i-tick", }
        ],
        columns:[
           { field:"codigoProceso", title: "Código Informe", width: "100", groupable: false}
            , { field:"numeroContrato", title:"Contrato", width: "100"}
            , { field:"nit", title:"NIT", width: "100"}
            , {field: "razonSocial", title: "Razon Social", width: "100", 
                template: "<span  class=\"logRazon k-link\">#: razonSocial # <input type=\"hidden\" value=\"#: codigoCliente#\"></span>"}
            , { field:"usoServicio", title:"Uso Servicio", width: "100"}
            , { field:"actividadProductiva", title:"Act. Prod.", width: "100"}
            , { field:"volumenLodo", title:"Volumen Lodo", width: "100", groupable: false}
            , { field:"tasaRetributiva", title:"Tasa Retributiva", width: "100", groupable: false}
        ]
    });
    
    $(".k-grid-toolbar", "#grillaReporte7").prepend("Opciones:");
    
    $(".k-grid-Excel", "#grillaReporte7").bind("click", function (ev) {
        exportGridToExcelReporte7();
    });
}

function exportGridToExcelReporte7() {
    var grid = $("#grillaReporte7").data("kendoGrid");
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