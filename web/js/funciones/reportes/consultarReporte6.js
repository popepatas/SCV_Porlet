/**
 *
 * @author jmrincon
 */

//VARIABLES
var _rangoInicial = null;
var _rangoFinal = null;
var dataSourceParam = null;
var contaParametros = 0;

var strRangoIni = "&rangoInicial";
var strRangoIni1 = "#rangoInicial";
var strRangoFin = "&rangoFinal";
var strRangoFin1 = "#rangoFinal";

$(document).on("ready", inicio);

function inicio(){
    //Se inicializa el validador
    $("#div_Reporte6").kendoValidator({
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
    
    var urlParam = servlet.consultar.paramFisQuim + '?opcion=1';
    dataSourceParam = new kendo.data.DataSource({transport: {read: {url: urlParam, dataType: "json"}}});
    $("#paramFisicoQuimico").kendoMultiSelect({
        dataSource: dataSourceParam,
        dataTextField: "descripcion",
        dataValueField: "codigo",
        maxSelectedItems: 3,
        change: function(e){
            contaParametros = this.value().length;
            var htmlrangosParam = "";
            for (var i = 1; i <= contaParametros; i++){
                htmlrangosParam += "<div class='contenedoUsoServicio'><div class='etiquetas'><label for='rangoInicial" + i + "'>Rango Inicial " + i + ":</label></div><div class='campos'><input type='text' id='rangoInicial" + i + "' name='rangoInicial" + i + "' required/></div></div>";
                htmlrangosParam += "<div class='contenedoUsoServicio'><div class='etiquetas'><label for='rangoFinal" + i + "'>Rango Final " + i + ":</label></div><div class='campos'><input type='text' id='rangoFinal" + i + "' name='rangoFinal" + i + "' required/></div></div>";
            }
            
            $("#rangosParam").html(htmlrangosParam);
            
            for (var i = 1; i <= contaParametros; i++){
                $("#rangoInicial" + i).kendoNumericTextBox({format: "#", decimals: 0, min: 0}).data("kendoNumericTextBox");
                $("#rangoFinal" + i).kendoNumericTextBox({format: "#", decimals: 0, min: 0}).data("kendoNumericTextBox");
            }
        }
    });
    
    //ModalBox
    $(document).on("click", ".logRazon", {tipoCampo:"Razón Social", opcion:2}, mostrarLogsCliente);
}

function limpiarFormulario(){
    campo.limpiar("fechaInicial");
    campo.limpiar("fechaFinal");
    campo.limpiar("numeroContrato");
    campo.limpiar("nitConsultar");
    campo.limpiar("actividadProductiva");
    campo.limpiar("usoServicio");
    campo.limpiar("usoServicio");
    campo.limpiar("paramFisicoQuimico");
    _rangoInicial.value($("#rangoInicial").val(null));
    _rangoFinal.value($("#rangoFinal").val(null));
    
    $('#grillaReporte6').data().kendoGrid.destroy();
    $('#grillaReporte6').empty();
}

function generarGrilla(){
    var multiselect = $("#paramFisicoQuimico").data("kendoMultiSelect").value();
    
    var url = servlet.consultar.reporte6; 
    
    var parametros = { 
        fechaInicial: campo.obtener("fechaInicial")
        , fechaFinal: campo.obtener("fechaFinal")
        , numeroContrato: campo.obtener("numeroContrato")
        , nit: campo.obtener("nitConsultar")
        , actividadProductiva: campo.obtener("actividadProductiva")
        , usoServicio: campo.obtener("usoServicio")
        , totalParametros: contaParametros
    };
    
    var rangosParametrosIni = "";
    for(var j = 1; j <= contaParametros; j++){
        strRangoIni1 = "#rangoInicial" + j;
        rangosParametrosIni += strRangoIni + j + "=" + $(strRangoIni1).data("kendoNumericTextBox").value();
    }
    
    var rangosParametrosFin = "";
    for(var j = 1; j <= contaParametros; j++){
        strRangoFin1 = "#rangoFinal" + j;
        rangosParametrosFin += strRangoFin + j + "=" + $(strRangoFin1).data("kendoNumericTextBox").value();
    }
    
    var dataSource = new kendo.data.DataSource({
        transport: { read: {
                url: url + '?paramFisicoQuimico=' + multiselect + rangosParametrosIni + rangosParametrosFin, 
                data: parametros, 
                dataType: "json",cache:false}},
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
                    , razonSocial: {type: "string",  editable: false }
                    , direccion: {type: "string", editable: false }
                    , barrio: {type: "string", editable: false }
                    , comuna: {type: "string", editable: false }
                    , usoServicio: {type: "string", editable: false }
                    , actividadProductiva: {type: "string", editable: false }
                    , puntoVertimiento: {type: "string", editable: false }
                    , jornada: {type: "string", editable: false }
                    , descParametro: {type: "string", editable: false }
                    , valorParametro: {type: "string", editable: false }
                }
            }
        }
    });
    
    $("#grillaReporte6").kendoGrid({
        height: 400,
        width: 800,
        autoSync: true,
        groupable: true,
        sortable: false,
        pageable: true,
        scrollable: false,
        dataSource: dataSource,
        toolbar: [{name: "Excel", imageClass: "k-icon k-i-tick"}],
        columns:[
           { field:"codigoProceso", title: "Código Informe", width: "100", groupable: false}
            , { field:"numeroContrato", title:"Contrato", width: "100"}
            , {field: "razonSocial", title: "Razon Social", width: "100", 
                template: "<span  class=\"logRazon k-link\">#: razonSocial # <input type=\"hidden\" value=\"#: codigoCliente#\"></span>"}
            , { field:"direccion", title:"Dirección", width: "150", groupable: false}
            , { field:"barrio", title:"Barrio", width: "100"}
            , { field:"comuna", title:"Comuna", width: "100", groupable: false}
            , { field:"usoServicio", title:"Uso Servicio", width: "100"}
            , { field:"actividadProductiva", title:"Act. Prod.", width: "100"}
            , { field:"puntoVertimiento", title:"Pto. Vertimiento", width: "100", groupable: false}
            , { field:"jornada", title:"Jornada", width: "100", groupable: false}
            , { field:"descParametro", title:"Parametro", width: "100"}
            , { field:"valorParametro", title:"Valor", width: "100", groupable: false}
        ]
    });
    
    $(".k-grid-toolbar", "#grillaReporte6").prepend("Opciones:");
    
    $(".k-grid-Excel", "#grillaReporte6").bind("click", function (ev) {
        exportGridToExcelReporte6();
    });
}

function exportGridToExcelReporte6() {
    var grid = $("#grillaReporte6").data("kendoGrid");
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