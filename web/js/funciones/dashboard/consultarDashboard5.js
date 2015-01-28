/**
 *
 * @author jmrincon
 */

//VARIABLES
var _rangoInicial, _rangoFinal = null;
var _rangoParamInicial,_rangoParamFinal = null;

$(document).on("ready", inicio);

function inicio() {
    //Se inicializa el validador
    $("#div_Dashboard5").kendoValidator({
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
    
    _rangoParamInicial = $("#rangoParamInicial").kendoNumericTextBox({format: "#", decimals: 0, min: 0}).data("kendoNumericTextBox");
    _rangoParamFinal = $("#rangoParamFinal").kendoNumericTextBox({format: "#", decimals: 0, min: 0}).data("kendoNumericTextBox");
    
    _rangoInicial = $("#rangoInicial").kendoNumericTextBox({format: "#", decimals: 0, min: 0}).data("kendoNumericTextBox");
    _rangoFinal = $("#rangoFinal").kendoNumericTextBox({format: "#", decimals: 0, min: 0}).data("kendoNumericTextBox");
}

function enviarFormulario(){
    var validador = $("#div_Dashboard5").data("kendoValidator");
    if (validador.validate()) {
        generarChart();
    }
}

function limpiarFormulario() {
    campo.limpiar("clientes");
    
    _rangoParamInicial.value($("#rangoParamInicial").val(null));
    _rangoParamFinal.value($("#rangoParamFinal").val(null));
    
    _rangoInicial.value($("#rangoInicial").val(null));
    _rangoFinal.value($("#rangoFinal").val(null));
    $("#chartDashboard5").data("kendoChart").dataSource.data([]);
}

var dataSource;
function generarChart() {
    var url = servlet.consultar.dashboard5;
    
    dataSource = new kendo.data.DataSource({
        transport: {
            read: {
                url: url + "?codigoCliente=" + campo.obtener("clientes") 
                        + "&rangoDBOInicial=" + campo.obtener("rangoParamInicial") 
                        + "&rangoDBOFinal=" + campo.obtener("rangoParamFinal") 
                        + "&rangoInicial=" + campo.obtener("rangoInicial") 
                        + "&rangoFinal=" + campo.obtener("rangoFinal"), 
                dataType: "json"}},
        sort: {field: "mesJornada", dir: "asc"}, 
        group: {field: "nombParametro", dir: "asc"}
    });
    
    $("#chartDashboard5").kendoChart({
        dataSource: dataSource,
        title: {text: "Evolución de DBO por Cliente y Rango"},
        series: [{type: "line", field: "cantidadProcesos", style: "smooth"}],
        chartArea: {background: ""}, 
        categoryAxis: {field: "mesProceso"}
    });
}
