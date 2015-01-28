/**
 *
 * @author jmrincon
 */

//VARIABLES
var _comuna = null;

$(document).on("ready", inicio);

function inicio() {
    //Se inicializa el validador
    $("#div_Reporte1").kendoValidator({
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
    $("#nitConsultar").on("keypress", function() {return  campo.nit(event, this);});

    var laboratorios = servlet.consultar.laboratorios + '?opcion=1';
    campo.lista.crear("laboratorio", laboratorios, {});

    var estadosProceso = servlet.consultar.estadosProcesos + '?opcion=1';
    campo.lista.crear("estadoProceso", estadosProceso, {});

    var urlAct = servlet.consultar.actEconomica;
    campo.lista.crear("actividadProductiva", urlAct, {});

    var tiposInforme = servlet.consultar.tiposInformeVertimientos + '?opcion=3';
    campo.lista.crear("tipoInforme", tiposInforme, {});
    
    _comuna = $("#comuna").kendoNumericTextBox({format: "#", decimals: 0, min: 0}).data("kendoNumericTextBox");
    
    //ModalBox
    $(document).on("click", ".logRazon", {tipoCampo:"Razón Social", opcion:2}, mostrarLogs);
}

function limpiarFormulario() {
    campo.limpiar("fechaInicial");
    campo.limpiar("fechaFinal");
    campo.limpiar("estadoProceso");
    campo.limpiar("numeroContrato");
    campo.limpiar("nitConsultar");
    campo.limpiar("razonSocial");
    _comuna.value($("#comuna").val(null));
    campo.limpiar("actividadProductiva");
    campo.limpiar("laboratorio");
    campo.limpiar("usoServicio");
    campo.limpiar("tipoInforme");
    
    $('#grillaReporte1').data().kendoGrid.destroy();
    $('#grillaReporte1').empty();
}

var dataSource;
function generarGrilla() {
    var url = servlet.consultar.reporte1;

    var parametros = {
        fechaInicial: campo.obtener("fechaInicial")
        , fechaFinal: campo.obtener("fechaFinal")
        , estadoProceso: campo.obtener("estadoProceso")
        , numeroContrato: campo.obtener("numeroContrato")
        , nit: campo.obtener("nitConsultar")
        , actividadProductiva: campo.obtener("actividadProductiva")
        , razonSocial: campo.obtener("razonSocial")
        , comuna: campo.obtener("comuna")
        , laboratorio: campo.obtener("laboratorio")
        , usoServicio: campo.obtener("usoServicio")
        , tipoInforme: campo.obtener("tipoInforme")
    };

    dataSource = new kendo.data.DataSource({
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
                    codigoProceso: {type: "string", editable: false}
                    , numeroContrato: {type: "string", editable: false}
                    , nit: {type: "string", editable: false}
                    , codigoCliente: {type: "string", editable: false}
                    , razonSocial: {type: "string", editable: false}
                    , actividadProductiva: {type: "string", editable: false}
                    , jornadaLaboral: {type: "string", editable: false}
                    , usoServicio: {type: "string", editable: false}
                    , direccion: {type: "string", editable: false}
                    , barrio: {type: "string", editable: false}
                    , comuna: {type: "string", editable: false}
                    , telefono: {type: "string", editable: false}
                    , nombreContacto: {type: "string", editable: false}
                    , email: {type: "string", editable: false}
                    , tipoInforme: {type: "string", editable: false}
                    , notificado: {type: "string", editable: false}
                    , estadoProceso: {type: "string", editable: false}
                    , labContacto: {type: "string", editable: false}
                    , fechaEntrega: {type: "string", editable: false}
                    , fechaDevolucion: {type: "string", editable: false}
                    , fechaEntregaDevolucion: {type: "string", editable: false}
                    , codigoEstadoActual: {type: "string", editable: false}
                    , ultimoEstado: {type: "string", editable: false}
                }
            }
        }
    });

    $("#grillaReporte1").kendoGrid({
        height: 400,
        width: 800,
        autoSync: true,
        groupable: true,
        sortable: false,
        pageable: true,
        scrollable: false,
        dataSource: dataSource,
        toolbar: [
            {name: "Excel", imageClass: "k-icon k-i-tick"}
        ],
        columns: [
            {field: "codigoProceso", title: "Codigo Proceso", width: "100", groupable: false}
            , {field: "numeroContrato", title: "Contrato", width: "100"}
            , {field: "nit", title: "NIT", width: "100"}
            , {field: "razonSocial", title: "Razon Social", width: "100", 
                template: "<span  class=\"logRazon k-link\">#: razonSocial # <input type=\"hidden\" value=\"#: codigoCliente#\"></span>"}
            , {field: "actividadProductiva", title: "Act. Prod.", width: "100"}
            , {field: "jornadaLaboral", title: "Jornada Laboral", width: "100", groupable: false}
            , {field: "usoServicio", title: "Uso Servicio", width: "100"}
            , {field: "direccion", title: "Direccion", width: "150", groupable: false}
            , {field: "barrio", title: "Barrio", width: "100"}
            , {field: "comuna", title: "Comuna", width: "100", groupable: false}
            , {field: "telefono", title: "Telefono", width: "100", groupable: false}
            , {field: "nombreContacto", title: "Contacto", width: "100", groupable: false}
            , {field: "email", title: "Email", width: "100", groupable: false}
            , {field: "tipoInforme", title: "Tipo Informe", width: "100"}
            , {field: "notificado", title: "Notificado", width: "100", groupable: false}
            , {field: "labContacto", title: "Laboratorio", width: "100"}
            , {field: "fechaEntrega", title: "Fecha Entrega", width: "100", groupable: false}
            , {field: "fechaDevolucion", title: "Fecha Devolucion", width: "100", groupable: false}
            , {field: "fechaEntregaDevolucion", title: "Entrega Complemento", width: "100", groupable: false}
        ]
    });

    $(".k-grid-toolbar", "#grillaReporte1").prepend("Opciones:");

    $(".k-grid-Excel", "#grillaReporte1").bind("click", function(ev) {
        exportGridToExcelReporte1();
    });
}

function exportGridToExcelReporte1() {
    var grid = $("#grillaReporte1").data("kendoGrid");
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

function mostrarLogs(event){
    var parametros = event.data;
    parametros.codigo = $(this).find("input[type='hidden']").val();
    var titulo = " Log "+ parametros.tipoCampo;
    $("#modalBox").load("../cliente/LogsCliente.jsp", function(response, status, xhrGrillaLogs){
        xhrGrillaLogs.done(function(){
            generarGrillaLogsCliente(parametros);
            var modal = modalBox.abrir("modalBox", null, "800px", titulo, null);
            modal.open();
        });
    });
}

function generarGrillaLogsCliente(param){
    var url = servlet.consultar.logsCliente;
    var parametros = {tipoCampo: param.tipoCampo, codigo: param.codigo};
    var dataSource = new kendo.data.DataSource({
        transport: {read: {url: url, data:parametros, dataType: "json",cache:false}},
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