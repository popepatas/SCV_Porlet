/**
 *
 * @author jmrincon
 */

//VARIABLES
var _comuna = null;

$(document).on("ready", inicio);

function inicio(){
    //Se inicializa el validador
    $("#div_Reporte3").kendoValidator({
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
    $("#nitConsultar").on("keypress", function() {return  campo.nit(event, this);});
    
    var filtro = "5,6,7";
    var estadosVisita = servlet.consultar.estados + '?opcion=3&filtro=' + filtro;
    campo.lista.crear("estadoVisita", estadosVisita, {});
    
    var urlAct = servlet.consultar.actEconomica;
    campo.lista.crear("actividadProductiva", urlAct, {});
    
    _comuna = $("#comuna").kendoNumericTextBox({format: "#", decimals: 0, min: 0}).data("kendoNumericTextBox");
    
    //ModalBox
    $(document).on("click", ".logRazon", {tipoCampo: "Razón Social", opcion: 2}, mostrarLogs);
    $(document).on("click", ".logAdjuntosVisita", {titulo: "Adjuntos Visita"}, mostrarAdjuntosVisita);
}

function limpiarFormulario(){
    campo.limpiar("fechaInicial");
    campo.limpiar("fechaFinal");
    campo.limpiar("estadoVisita");
    campo.limpiar("numeroContrato");
    campo.limpiar("nitConsultar");
    campo.limpiar("razonSocial");
    _comuna.value($("#comuna").val(null));
    campo.limpiar("actividadProductiva");
    campo.limpiar("usoServicio");
    
    $('#grillaReporte3').data().kendoGrid.destroy();
    $('#grillaReporte3').empty();
}

var dataSource;
function generarGrilla(){
    var url = servlet.consultar.reporte3; 
    
    var parametros = { 
        fechaInicial: campo.obtener("fechaInicial")
        , fechaFinal: campo.obtener("fechaFinal")
        , estadoVisita: campo.obtener("estadoVisita")
        , numeroContrato: campo.obtener("numeroContrato")
        , nit: campo.obtener("nitConsultar")
        , razonSocial: campo.obtener("razonSocial")
        , comuna: campo.obtener("comuna")
        , actividadProductiva: campo.obtener("actividadProductiva")
        , usoServicio: campo.obtener("usoServicio")
    };
    
    dataSource = new kendo.data.DataSource({
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
                    , notificado: {type: "string", editable: false }
                    , codigoVisita: {type: "string", editable: false }
                    , fechaVisita: {type: "string", editable: false }
                    , estadoVisita: {type: "string", editable: false }
                    , resultadoVisita: {type: "string", editable: false }
                    , nombreTecnico: {type: "string", editable: false }
                }
            }
        }
    });
    
    $("#grillaReporte3").kendoGrid({
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
            { title: "Opciones", width: "200", 
                command:  [
                    {name: "verAdjuntos", className: "Edit-Cliente", text: "Adjuntos", 
                        click: function(e){
                            var tr = $(e.target).closest("tr");
                            var data = this.dataItem(tr);
                            //document.location.href = "Proceso.jsp?proceso=" + data.id;
                            var parametros = {codigoVisita: data.codigoVisita};
                            mostrarAdjuntosVisitaOpciones(parametros);
                        }
                    }
                ]
            }
            , { field:"codigoProceso", title: "Código Proceso", width: "100", groupable: false}
            , { field:"numeroContrato", title:"Contrato", width: "100"}
            , { field:"nit", title:"NIT", width: "100"}
            , { field: "razonSocial", title: "Razon Social", width: "100", 
                template: "<span class=\"logRazon k-link\">#: razonSocial # <input type=\"hidden\" value=\"#: codigoCliente#\"></span>"}
            , { field:"actividadProductiva", title:"Act. Prod.", width: "100"}
            , { field:"jornadaLaboral", title:"Jornada Laboral", width: "100", groupable: false}
            , { field:"usoServicio", title:"Uso Servicio", width: "100"}
            , { field:"direccion", title:"Dirección", width: "100", groupable: false}
            , { field:"barrio", title:"Barrio", width: "100"}
            , { field:"comuna", title:"Comuna", width: "100", groupable: false}
            , { field:"telefono", title:"Teléfono", width: "100", groupable: false}
            , { field:"nombreContacto", title:"Contacto", width: "100", groupable: false}
            , { field:"email", title:"Email", width: "100", groupable: false}
            , { field:"tipoInforme", title:"Tipo Informe", width: "100"}
            , { field:"fechaVisita", title:"Fecha Visita", width: "100", groupable: false, 
                template: "<span class=\"logAdjuntosVisita k-link\">#: fechaVisita # <input type=\"hidden\" value=\"#: codigoVisita#\"></span>"}
            , { field:"estadoVisita", title:"Estado Visita", width: "100"}
            , { field:"resultadoVisita", title:"Resultado", width: "100", groupable: false}
            , { field:"nombreTecnico", title:"Tecnico", width: "100", groupable: false}
        ]
    });
    
    $(".k-grid-toolbar", "#grillaReporte3").prepend("Opciones:");
    
    $(".k-grid-Excel", "#grillaReporte3").bind("click", function (ev) {
        exportGridToExcelReporte3();
    });
}

function exportGridToExcelReporte3() {
    var grid = $("#grillaReporte3").data("kendoGrid");
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
        pageable: {refresh: true},
        editable: {confirmation: false},
        dataSource: dataSource,
        columns:[
            {field: "campo", title: "Campo", width: "100px"}
            , {field: "valorAnterior", title: "Valor Anterior", width: "100px"}
            , {field: "valorPosterior", title: "Valor Posterior", width: "100px"}
            , {field: "fechaModificacion", title: "Fecha Modificacion", width: "100px"}
        ]
    });
}

function mostrarAdjuntosVisitaOpciones(param){
    var titulo = " Adjuntos Visita";
    $("#modalBoxAdjuntosVisita").load("../reportes/LogsAdjuntosVisita.jsp", function(response, status, xhrGrillaLogs){
        xhrGrillaLogs.done(function(){
            generarGrillaAdjuntosVisita(param);
            var modal = modalBox.abrir("modalBoxAdjuntosVisita", null, "800px", titulo, null);
            modal.open();
        });
    });
}

function mostrarAdjuntosVisita(event){
    var parametros = event.data;
    parametros.codigoVisita = $(this).find("input[type='hidden']").val();
    var titulo = " Adjuntos Visita";
    $("#modalBoxAdjuntosVisita").load("../reportes/LogsAdjuntosVisita.jsp", function(response, status, xhrGrillaLogs){
        xhrGrillaLogs.done(function(){
            generarGrillaAdjuntosVisita(parametros);
            var modal = modalBox.abrir("modalBoxAdjuntosVisita", null, "800px", titulo, null);
            modal.open();
        });
    });
}

function generarGrillaAdjuntosVisita(param){
    var url = servlet.consultar.reporteAdjuntosVisita + '?codigoVisita=' + param.codigoVisita; 
    //var parametros = {codigoVisita: param.codigoVisita};
    var dataSourceVisitas = new kendo.data.DataSource({
        transport: {read: {url: url, /*data: parametros,*/ dataType: "json", type: "POST"}},
        //batch: true,
        pageSize: 20,
        schema: {
            model: {
                id: "codigoArchivo",
                fields: {
                    codigoArchivo: { editable: false},
                    codigoVisita: { editable: false },
                    nombreArchivo: { editable: false}
                }
            }
        }
    });
    
    $("#grillaAdjuntosVisita").kendoGrid({
        height: 430,
        width: 800,
        autoSync: true,
        pageable: {refresh: true},
        editable: {confirmation: false},
        dataSource: dataSourceVisitas,
        columns: [
            { title: "Opciones", width: "50", 
                command:  [
                    {name: "descargarAdjunto", className: "Edit-Cliente", text: "Descargar", 
                        click: function(e){
                            var tr = $(e.target).closest("tr");
                            var data = this.dataItem(tr);
                            var codigo = data.id;
                            descargarArchivoVisita(codigo);
                        }
                    }
                ]
            }
            //, { field: "codigoArchivo", title: "Codigo", width: "100"}
            //, { field: "codigoVisita", title: "Codigo", width: "50px"}
            , { field: "nombreArchivo", title: "Archivo", width: "50%"}
        ]
    });
}

function descargarArchivoVisita(codigo){
    var codigo = codigo;
    var url = servlet.cargaArchivos.descargar + "?opcion=1&codigoArchivo=" + codigo;
    window.open(url, "_blank"); 
}