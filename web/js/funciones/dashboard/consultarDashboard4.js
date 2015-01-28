/**
 *
 * @author jmrincon
 */

//VARIABLES
var _rangoInicial = null;
var _rangoFinal = null;

$(document).on("ready", inicio);

function inicio() {
    //Se inicializa el validador
    $("#div_Dashboard4").kendoValidator({
        rules: {
            /*Evita la validación incorrecta del formato de la fecha*/
            date: function (input) {
                var attr = input.attr("data-role");
                if( attr === "datepicker"){
                    var fecha = kendo.parseDate(input.val(), "dd/MM/yyyy");
                    return fecha instanceof Date;
                }
                return true;
            }
        },
        messages:{
            required:"Ingrese un valor para este campo"
        }
    });
    
    //Eventos
    $("#consultar").on("click", enviarFormulario);
    $("#limpiar").on("click", limpiarFormulario);
    
    //Campos
    var arrClientes = servlet.consultar.cliente + '?opcion=1';
    campo.lista.crear("clientes", arrClientes, {});
    
    var tiposParam = '6,7';
    var parametrosFisicos = servlet.consultar.paramFisQuim + '?opcion=3&tipoParam=' + tiposParam;
    campo.lista.crear("paramFisicoQuimico", parametrosFisicos, {});
    
    _rangoInicial = $("#rangoInicial").kendoNumericTextBox({format: "#", decimals: 0, min: 0}).data("kendoNumericTextBox");
    _rangoFinal = $("#rangoFinal").kendoNumericTextBox({format: "#", decimals: 0, min: 0}).data("kendoNumericTextBox");
}

function enviarFormulario(){
    var validador = $("#div_Dashboard4").data("kendoValidator");
    if (validador.validate()) {
        generarChart();
    }
}

function limpiarFormulario() {
    campo.limpiar("numeroContrato");
    campo.limpiar("clientes");
    campo.limpiar("paramFisicoQuimico");
    _rangoInicial.value($("#rangoInicial").val(null));
    _rangoFinal.value($("#rangoFinal").val(null));
    $("#chartDashboard4").data("kendoChart").dataSource.data([]);
}

var dataSource;
function generarChart() {
    var url = servlet.consultar.dashboard4;
    
    dataSource = new kendo.data.DataSource({
        transport: {
            read: {
                url: url + "?paramFisicoQuimico=" + campo.obtener("paramFisicoQuimico")
                        + "&numeroContrato=" + campo.obtener("numeroContrato") 
                        + "&codigoCliente=" + campo.obtener("clientes")
                        + "&rangoInicial=" + campo.obtener("rangoInicial") 
                        + "&rangoFinal=" + campo.obtener("rangoFinal"), 
                dataType: "json"}},
        sort: {field: "mesJornada", dir: "asc"}, 
        group: {field: "nombParametro", dir: "asc"}
    });
    
    $("#chartDashboard4").kendoChart({
        dataSource: dataSource,
        title: {text: "Evolución Parametro"},
        //seriesDefaults: {type: "line", style: "smooth"},
        series: [{type: "line", field: "valorJornada", style: "smooth"}],
        chartArea: {background: ""}, 
        categoryAxis: {field: "mesJornada", majorGridLines: {visible: false}},
        valueAxis: {labels: {format: "N0"}, majorUnit: 5, line: {visible: true}},
        tooltip: {visible: true, template: "#= series.name #: #= value #"}
    });
}
