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
    $("#div_Dashboard1").kendoValidator({
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
    $("#consultar").on("click", enviarFormulario);
    $("#limpiar").on("click", limpiarFormulario);
    
    //Campos
    var arrClientes = servlet.consultar.cliente + '?opcion=1';
    campo.lista.crear("clientes", arrClientes, {});
    
    _rangoInicial = $("#rangoInicial").kendoNumericTextBox({format: "#", decimals: 0, min: 0}).data("kendoNumericTextBox");
    _rangoFinal = $("#rangoFinal").kendoNumericTextBox({format: "#", decimals: 0, min: 0}).data("kendoNumericTextBox");
}

function enviarFormulario(){
    var validador = $("#div_Dashboard1").data("kendoValidator");
    if (validador.validate()) {
        generarChart();
    }
}

function limpiarFormulario() {
    campo.limpiar("clientes");
    _rangoInicial.value($("#rangoInicial").val(null));
    _rangoFinal.value($("#rangoFinal").val(null));
    $("#chartDashboard1").data("kendoChart").dataSource.data([]);
}

var dataSource;
function generarChart() {
    var url = servlet.consultar.dashboard1;
    
    dataSource = new kendo.data.DataSource({
        transport: {
            read: {
                url: url + "?codigoCliente=" + campo.obtener("clientes") 
                        + "&rangoInicial=" + campo.obtener("rangoInicial") 
                        + "&rangoFinal=" + campo.obtener("rangoFinal"), 
                dataType: "json"}},
        sort: {field: "mesProceso", dir: "asc"}, 
        group: {field: "nombLodo", dir: "asc"}
    });
    
    $("#chartDashboard1").kendoChart({
        dataSource: dataSource,
        title: {text: "Cantidad de Caracterizaciones por Empresa y Rango de Tipo de Lodo"},
        legend: {position: "right"},
        series: [{type: "line", field: "cantidadProcesos", style: "smooth"}],
        chartArea: {background: ""}, 
        categoryAxis: {field: "mesProceso", majorGridLines: {visible: true}},
        valueAxis: {labels: {format: "N0"}, majorUnit: 5, line: {visible: true}},
        tooltip: {visible: true, template: "#= series.name #: #= value #"}
    });
}
