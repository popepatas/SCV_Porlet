/**
 *
 * @author jmrincon
 */

//VARIABLES
var _comuna = null;

$(document).on("ready", inicio);

function inicio(){
    //Se inicializa el validador
    $("#div_Reporte2").kendoValidator({
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
    
    var laboratorios = servlet.consultar.laboratorios + '?opcion=1';
    campo.lista.crear("laboratorio", laboratorios, {});
    
    var estadosProceso = servlet.consultar.estadosProcesos + '?opcion=1';
    campo.lista.crear("estadoProceso", estadosProceso, {});
    
    var urlAct = servlet.consultar.actEconomica;
    campo.lista.crear("actividadProductiva", urlAct, {});
    
    var tiposInforme = servlet.consultar.tiposInformeVertimientos+ '?opcion=1';
    campo.lista.crear("tipoInforme", tiposInforme, {});
    
    _comuna = $("#comuna").kendoNumericTextBox({format: "#", decimals: 0, min: 0}).data("kendoNumericTextBox");
    
    //ModalBox
    $(document).on("click", ".logRazon", {tipoCampo:"Razón Social", opcion:2}, mostrarLogs);
    $(document).on("click", ".logVistas", {titulo: "Visitas", opcion: 2}, mostrarVisitas);
}

function limpiarFormulario(){
    campo.limpiar("fechaInicial");
    campo.limpiar("fechaFinal");
    campo.limpiar("estadoProceso");
    campo.limpiar("numeroContrato");
    campo.limpiar("nitConsultar");
    campo.limpiar("razonSocial");
    _comuna.value($("#comuna").val(null));
    campo.limpiar("laboratorio");
    campo.limpiar("actividadProductiva");
    campo.limpiar("usoServicio");
    campo.limpiar("tipoInforme");
    
    $('#grillaReporte2').data().kendoGrid.destroy();
    $('#grillaReporte2').empty();
}

var dataSource;
function generarGrilla(){
    var url = servlet.consultar.reporte2; 
    
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
                    , ultimoEstado: {type: "string", editable: false }
                    , codigoUltimaVisita: {type: "string", editable: false }
                    , fechaUltimaVisita: {type: "string", editable: false }
                    , fechaProceso: {type: "string", editable: false }
                    , fechaMonitoreo: {type: "string", editable: false }
                    , labContacto: {type: "string", editable: false }
                    , estadoAnterior: {type: "string", editable: false }
                    , estadoActual: {type: "string", editable: false }
                    , fechaModificacion: {type: "string", editable: false }
                }
            }
        }
    });
    
    $("#grillaReporte2").kendoGrid({
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
            { title: "Opciones", width: "200", 
                command:  [
                    {name: "verAdjuntos", className: "Edit-Cliente", text: "Adjuntos", 
                        click: function(e){
                            var tr = $(e.target).closest("tr");
                            var data = this.dataItem(tr);
                            //document.location.href = "Proceso.jsp?proceso=" + data.id;
                            var parametros = {codigoProceso: data.id};
                            mostrarAdjuntosProceso(parametros, data.codigoProceso);
                        }
                    }
                ]
            }
            , { field:"codigoProceso", title: "Código", width: "100", groupable: false}
            , { field:"numeroContrato", title:"Contrato", width: "100", groupable: false}
            , { field:"nit", title:"NIT", width: "100", groupable: false}
            , {field: "razonSocial", title: "Razon Social", width: "100", 
                template: "<span  class=\"logRazon k-link\">#: razonSocial # <input type=\"hidden\" value=\"#: codigoCliente#\"></span>"}
            , { field:"actividadProductiva", title:"Act. Prod.", width: "100", groupable: false}
            , { field:"jornadaLaboral", title:"Jornada Laboral", width: "100", groupable: false}
            , { field:"usoServicio", title:"Uso Servicio", width: "100", groupable: false}
            , { field:"direccion", title:"Dirección", width: "150", groupable: false}
            , { field:"barrio", title:"Barrio", width: "100", groupable: false}
            , { field:"comuna", title:"Comuna", width: "100", groupable: false}
            , { field:"telefono", title:"Telefono", width: "100", groupable: false}
            , { field:"nombreContacto", title:"Contacto", width: "100", groupable: false}
            , { field:"email", title:"Email", width: "100", groupable: false}
            , { field:"tipoInforme", title:"Tipo Informe", width: "100", groupable: false}
            , { field:"ultimoEstado", title:"Ultimo Estado", width: "100", groupable: false}
            , {field: "fechaUltimaVisita", title: "Ultima Visita", width: "100", groupable: false,
                template: "<span  class=\"logVistas k-link\">#: fechaUltimaVisita # <input type=\"hidden\" value=\"#: codigoProceso#\"></span>"}
            , { field:"fechaProceso", title:"Fecha Proceso", width: "100", groupable: false}
            , { field:"fechaMonitoreo", title:"Fecha Monitoreo", width: "100", groupable: false}
            , { field:"labContacto", title:"Laboratorio", width: "100", groupable: false}
            , { field:"estadoActual", title:"Estado Actual", width: "100", groupable: false}
            , { field:"estadoAnterior", title:"Estado Anterior", width: "100", groupable: false}
            , { field:"fechaModificacion", title:"Fecha Modificacion", width: "100", groupable: false}
        ]
    });
    
    $(".k-grid-toolbar", "#grillaReporte2").prepend("Opciones:");
    
    $(".k-grid-Excel", "#grillaReporte2").bind("click", function (ev) {
        exportGridToExcelReporte2();
    });
}

function mostrarAdjuntosProceso(parametro){
    var titulo = " Adjuntos Proceso";
    $("#modalBoxAdjuntosProceso").load("../reportes/LogsAdjuntosProceso.jsp", function(response, status, xhrGrillaLogs){
        xhrGrillaLogs.done(function(){
            generarGrillaAdjuntosProceso(parametro);
            var modal = modalBox.abrir("modalBoxAdjuntosProceso", null, "800px", titulo, null);
            modal.open();
        });
    });
}

function generarGrillaAdjuntosProceso(param){
    var url = servlet.consultar.reporteAdjuntosProceso; 
    var parametros = {codigoProceso: param.codigoProceso};
    var dataSourceVisitas = new kendo.data.DataSource({
        transport: {read: {url: url, data: parametros, dataType: "json", type:"POST"}},
        batch: true,
        pageSize: 20,
        schema: {
            model: {
                id: "codigoArchivo",
                fields: {
                    codigoArchivo: { editable: false},
                    codigoProceso: { editable: false },
                    nombreArchivo: { editable: false},
                    fechaArchivo: { editable: false }
                }
            }
        }
    });
    
    $("#grillaAdjuntos").kendoGrid({
        height: 430,
        width: 800,
        autoSync: true,
        dataSource: dataSourceVisitas,
        pageable: {refresh: true},
        columns: [
            { title: "Opciones", width: "50", 
                command:  [
                    {name: "descargarAdjunto", className: "Edit-Cliente", text: "Descargar", 
                        click: function(e){
                            var tr = $(e.target).closest("tr");
                            var data = this.dataItem(tr);
                            var codigo = data.id;
                            var codigoProceso = data.codigoProceso;
                            descargarArchivoProceso(codigo, codigoProceso);
                        }
                    }
                ]
            }
            //, { field: "codigoArchivo", title: "Codigo", width: "50px"}
            //, { field: "codigoProceso", title: "Codigo", width: "50px"}
            , { field: "nombreArchivo", title: "Archivo", width: "200"}
            , { field: "fechaArchivo", title: "Fecha Creacion", width: "100"}
        ]
    });
}

function descargarArchivoProceso(codigo, codigoProceso){
    var codigo = codigo;
    var url = servlet.cargaArchivos.descargar + "?opcion=2&codigoArchivo=" + codigo +"&codigoProceso="+codigoProceso;;
    window.open(url, "_blank"); 
}

function exportGridToExcelReporte2() {
    var grid = $("#grillaReporte2").data("kendoGrid");
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

function mostrarVisitas(event){
    var parametros = event.data;
    parametros.codigoProceso = $(this).find("input[type='hidden']").val();
    var titulo = parametros.titulo;
    $("#modalBoxVisitas").load("../reportes/LogsVisitas.jsp", function(response, status, xhrGrillaLogs){
        xhrGrillaLogs.done(function(){
            generarGrillaVisitas(parametros);
            var modal = modalBox.abrir("modalBoxVisitas", null, "800px", titulo, null);
            modal.open();
        });
    });
}

function generarGrillaVisitas(param){
    var url = servlet.consultar.reporteVisitasProceso ; 
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
                id: "codigoVisita",
                fields: {
                    codigoVisita: { editable: false},
                    codigoProceso: { editable: false },
                    nombreTecnico: { editable: false},
                    tipoVisita: { editable: false },
                    motivoVisita: { editable: false},
                    resultadoVisita: { editable: false},
                    fechaVisita: { editable: false },
                    obseVisita: { editable: false },
                    estadoVisita: { editable: false }
                }
            }
        }
    });
    
    $("#grillaVisitas").kendoGrid({
        height: 430,
        width:800,
        autoSync: true,
        dataSource: dataSourceVisitas,
        pageable: {refresh: true},
        columns: [
            { field: "codigoVisita", title: "Codigo", width: "50px"}
            //, { field: "codigoVisita", title: "Codigo", width: "50px"}
            , { field: "nombreTecnico", title: "Técnico", width: "200px"}
            , { field: "tipoVisita", title: "Tipo Visita", width: "160px"}
            , { field: "motivoVisita", title: "Motivo", width: "160px"}
            , { field: "resultadoVisita", title: "Resultado", width: "160px"}
            , { field: "fechaVisita", title: "Fecha", width: "160px"}
            , { field: "obseVisita", title: "Observacion", width: "160px"}
            , { field: "estado", title: "Estado", width: "160px"}
        ]
    });
}