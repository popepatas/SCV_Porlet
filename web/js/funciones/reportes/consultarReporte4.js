/**
 *
 * @author jmrincon
 */

//VARIABLES
var _comuna = null;

$(document).on("ready", inicio);

function inicio(){
    //Se inicializa el validador
    $("#div_Reporte4").kendoValidator({
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
    $(document).on("click", ".logRazon", {tipoCampo: "Razón Social", opcion: 2}, mostrarLogsCliente);
    $(document).on("click", ".logVisitas", {titulo: "Visitas", opcion: 2}, mostrarVisitas);
    $(document).on("click", ".logHistoralDagma", {titulo: "Historial Dagma", opcion: 2}, mostrarHistorialDagma);
    
}

function limpiarFormulario(){
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
    
    $('#grillaReporte4').data().kendoGrid.destroy();
    $('#grillaReporte4').empty();
}

var dataSource;
function generarGrilla(){
    var url = servlet.consultar.reporte4; 
    
    var parametros = { 
        fechaInicial: campo.obtener("fechaInicial")
        , fechaFinal: campo.obtener("fechaFinal")
        , estadoProceso: campo.obtener("estadoProceso")
        , numeroContrato: campo.obtener("numeroContrato")
        , nit: campo.obtener("nitConsultar")
        , razonSocial: campo.obtener("razonSocial")
        , comuna: campo.obtener("comuna")
        , actividadProductiva: campo.obtener("actividadProductiva")
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
                    , codigoEstadoActual: {type: "string", editable: false }
                    , ultimoEstado: {type: "string", editable: false }
                    , notificado: {type: "string", editable: false }
                    , codigoUltimaVisita: {type: "string", editable: false }
                    , fechaUltimaVisita: {type: "string", editable: false }
                    , codigoUltimoDagma: {type: "string", editable: false }
                    , fechaUltimoDagma: {type: "string", editable: false }
                    , fechaRadicacion: {type: "string", editable: false }
                    , labContacto: {type: "string", editable: false }
                }
            }
        }
    });
    
    $("#grillaReporte4").kendoGrid({
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
            { title: "Opciones", width: "300", 
                command:  [
                    {name: "verAdjuntos", className: "Edit-Cliente", text: "Adjuntos", 
                        click: function(e){
                            var tr = $(e.target).closest("tr");
                            var data = this.dataItem(tr);
                            //document.location.href = "Proceso.jsp?proceso=" + data.id;
                            var parametros = {codigoProceso: data.id};
                            mostrarAdjuntosProceso(parametros);
                        }
                    },
                    {name: "historialDagma", className: "Edit-Cliente", text: "Historial Dagma", 
                        click: function(e){
                            var tr = $(e.target).closest("tr");
                            var data = this.dataItem(tr);
                            codigo = data.codigoProceso;
                            cargarHistorialDagma(codigo);
                        }
                    }
                ]
            }
            , { field:"codigoProceso", title: "Código Proceso", width: "100", groupable: false}
            , { field:"numeroContrato", title:"Contrato", width: "100", groupable: false}
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
            , { field:"ultimoEstado", title:"Estado", width: "100"}
            , { field:"notificado", title:"Notificado", width: "100", groupable: false}
            , {field: "fechaUltimaVisita", title: "Ultima Visita", width: "100", groupable: false,
                template: "<span  class=\"logVisitas k-link\">#: fechaUltimaVisita # <input type=\"hidden\" value=\"#: codigoProceso#\"></span>"}
            , {field: "fechaUltimoDagma", title: "Ultimo Historial Dagma", width: "100", groupable: false,
                template: "<span  class=\"logHistoralDagma k-link\">#: fechaUltimoDagma # <input type=\"hidden\" value=\"#: codigoProceso#\"></span>"}
            , { field:"fechaRadicacion", title:"Fecha Radicacion", width: "100", groupable: false}
            , { field:"labContacto", title:"Lab. / Consultor", width: "100"}
        ]
    });
    
    $(".k-grid-toolbar", "#grillaReporte4").prepend("Opciones:");
    
    $(".k-grid-Excel", "#grillaReporte4").bind("click", function (ev) {
        exportGridToExcelReporte4();
    });
}

function exportGridToExcelReporte4() {
    var grid = $("#grillaReporte4").data("kendoGrid");
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
    var titulo = " Log " + parametros.tipoCampo;
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
        //batch: true,
        pageSize: 20,
        schema: {
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
            { title: "Opciones", width: "200", 
                command:  [
                    {name: "verAdjuntos", className: "Edit-Cliente", text: "Adjuntos", 
                        click: function(e){
                            var tr = $(e.target).closest("tr");
                            var data = this.dataItem(tr);
                            //document.location.href = "Proceso.jsp?proceso=" + data.id;
                            var parametros = {codigoVisita: data.codigoVisita};
                            mostrarAdjuntosVisita(parametros);
                        }
                    }
                ]
            }
            //, { field: "codigoVisita", title: "Codigo", width: "50"}
            //, { field: "codigoProceso", title: "Codigo", width: "50"}
            , { field: "nombreTecnico", title: "Técnico", width: "200"}
            , { field: "tipoVisita", title: "Tipo Visita", width: "100"}
            , { field: "motivoVisita", title: "Motivo", width: "100"}
            , { field: "resultadoVisita", title: "Resultado", width: "100"}
            , { field: "fechaVisita", title: "Fecha", width: "100"}
            , { field: "obseVisita", title: "Observacion", width: "100"}
            , { field: "estado", title: "Estado", width: "100"}
        ]
    });
}

//HistoralDagma
function mostrarHistorialDagma(event){
    var parametros = event.data;
    parametros.codigoProceso = $(this).find("input[type='hidden']").val();
    var titulo = parametros.titulo;
    $("#modalBoxHistorialDagma").load("../reportes/LogsHistorialDagma.jsp", function(response, status, xhrGrillaLogs){
        xhrGrillaLogs.done(function(){
            generarGrillaHistorialDagma(parametros);
            var modal = modalBox.abrir("modalBoxHistorialDagma", null, "800px", titulo, null);
            modal.open();
        });
    });
}

function generarGrillaHistorialDagma(param){
    var url = servlet.consultar.reporteHistorialDagmaProceso ; 
    var parametros = {codigoProceso: param.codigoProceso};
    var dataSourceHistorialDagma = new kendo.data.DataSource({
        transport: {read: {url: url, data: parametros, dataType: "json", type:"POST"}},
        //batch: true,
        pageSize: 20,
        schema: {
            model: {
                id: "codigoHistorialDagma",
                fields: {
                    codigoHistorialDagma: { editable: false},
                    codigoProceso: { editable: false },
                    radicadoHistorialDagma: { editable: false},
                    fechaHistorialDagma: { editable: false },
                    obseHistorialDagma: { editable: false}
                }
            }
        }
    });
    
    $("#grillaHistorialDagma").kendoGrid({
        height: 430,
        width: 800,
        autoSync: true,
        dataSource: dataSourceHistorialDagma,
        pageable: {refresh: true},
        columns: [
            { title: "Opciones", width: "200", 
                command:  [
                    {name: "verAdjuntos", className: "Edit-Cliente", text: "Adjuntos", 
                        click: function(e){
                            var tr = $(e.target).closest("tr");
                            var data = this.dataItem(tr);
                            //document.location.href = "Proceso.jsp?proceso=" + data.id;
                            var parametros = {codigoHistorialDagma: data.codigoHistorialDagma};
                            mostrarAdjuntosHistorialDagma(parametros);
                        }
                    }
                ]
            }
            //, { field: "codigoHistorialDagma", title: "Codigo", width: "50"}
            //, { field: "codigoProceso", title: "Codigo", width: "50"}
            , { field: "radicadoHistorialDagma", title: "Radicado", width: "200"}
            , { field: "obseHistorialDagma", title: "Observacion", width: "100"}
            , { field: "fechaHistorialDagma", title: "Fecha", width: "100"}
        ]
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
            { title: "Opciones", width: "100", 
                command:  [
                    {name: "descargarAdjunto", className: "Edit-Cliente", text: "Descargar", 
                        click: function(e){
                            var tr = $(e.target).closest("tr");
                            var data = this.dataItem(tr);
                            var codigoArchivo = data.codigoArchivo;
                            var codigoProceso = data.codigoProceso;
                            descargarArchivoProceso(codigoArchivo, codigoProceso);
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

function descargarArchivoProceso(codigoArchivo, codigoProceso){
    var url = servlet.cargaArchivos.descargar + "?opcion=2&codigoArchivo=" + codigoArchivo +"&codigoProceso="+codigoProceso;;
    window.open(url, "_blank"); 
}

function mostrarAdjuntosVisita(param){
    var titulo = " Adjuntos Visita";
    $("#modalBoxAdjuntosVisita").load("../reportes/LogsAdjuntosVisita.jsp", function(response, status, xhrGrillaLogs){
        xhrGrillaLogs.done(function(){
            generarGrillaAdjuntosVisita(param);
            var modal = modalBox.abrir("modalBoxAdjuntosVisita", null, "800px", titulo, null);
            modal.open();
        });
    });
}

function generarGrillaAdjuntosVisita(param){
    var url = servlet.consultar.reporteAdjuntosVisita + '?codigoVisita=' + param.codigoVisita; 
    //var parametros = {codigoVisita: param.codigoVisita};
    var dataSourceAdjuntosVisitas = new kendo.data.DataSource({
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
        width: 850,
        autoSync: true,
        pageable: {refresh: true},
        editable: {confirmation: false},
        dataSource: dataSourceAdjuntosVisitas,
        columns: [
            { title: "Opciones", width: "50", 
                command:  [
                    {name: "descargarAdjunto", className: "Edit-Cliente", text: "Descargar", 
                        click: function(e){
                            var tr = $(e.target).closest("tr");
                            var data = this.dataItem(tr);
                            var codigo = data.id;
                            descargarArchivoVisita(codigo);
                        },
                    }
                ]
            }
            //, { field: "codigoArchivo", title: "Codigo", width: "100"}
            //, { field: "codigoVisita", title: "Codigo", width: "50"}
            , { field: "nombreArchivo", title: "Archivo", width: "100"}
        ]
    });
}

function descargarArchivoVisita(codigo){
    var codigo = codigo;
    var url = servlet.cargaArchivos.descargar + "?opcion=1&codigoArchivo=" + codigo;
    window.open(url, "_blank"); 
}

//HistorialDagma
function mostrarAdjuntosHistorialDagma(param){
    var titulo = " Adjuntos Historial Dagma";
    $("#modalBoxAdjuntosHistorialDagma").load("../reportes/LogsAdjuntosHistorialDagma.jsp", function(response, status, xhrGrillaLogs){
        xhrGrillaLogs.done(function(){
            generarGrillaAdjuntosHistorialDagma(param);
            var modal = modalBox.abrir("modalBoxAdjuntosHistorialDagma", null, "800px", titulo, null);
            modal.open();
        });
    });
}

function generarGrillaAdjuntosHistorialDagma(param){
    var url = servlet.consultar.reporteAdjuntosHistorialDagma + '?codigoHistorialDagma=' + param.codigoHistorialDagma; 
    //var parametros = {codigoHistorialDagma: param.codigoHistorialDagma};
    var dataSourceAdjuntosHistorialDagma = new kendo.data.DataSource({
        transport: {read: {url: url, /*data: parametros,*/ dataType: "json", type: "POST"}},
        //batch: true,
        pageSize: 20,
        schema: {
            model: {
                id: "codigoArchivo",
                fields: {
                    codigoArchivo: { editable: false},
                    codigoHistorialDagma: { editable: false },
                    nombreArchivo: { editable: false}
                }
            }
        }
    });
    
    $("#grillaAdjuntosHistorialDagma").kendoGrid({
        height: 430,
        width: 800,
        autoSync: true,
        pageable: {refresh: true},
        editable: {confirmation: false},
        dataSource: dataSourceAdjuntosHistorialDagma,
        columns: [
            { title: "Opciones", width: "50", 
                command:  [
                    {name: "descargarAdjunto", className: "Edit-Cliente", text: "Descargar", 
                        click: function(e){
                            var tr = $(e.target).closest("tr");
                            var data = this.dataItem(tr);
                            var codigo = data.id;
                            var codigoHistorialDagma = data.codigoHistorialDagma;
                            descargarArchivoHistorialDagma(codigo, codigoHistorialDagma);
                        }
                    }
                ]
            }
            //, { field: "codigoArchivo", title: "Codigo", width: "100"}
            //, { field: "codigoHistorialDagma", title: "Codigo", width: "50"}
            , { field: "nombreArchivo", title: "Archivo", width: "100"}
        ]
    });
}

function descargarArchivoHistorialDagma(codigo, codigoHistorialDagma){
    var codigo = codigo;
    var url = servlet.cargaArchivos.descargar + "?opcion=3&codigoArchivo=" + codigo + "&codigoHistorialDagma=" + codigoHistorialDagma;
    window.open(url, "_blank"); 
}

    var cargarHistorialDagma = function(codigo){
     var url = "HistorialDagma.jsp";
     var titulo = "Historial Dagma, proceso - " + codigo;
       $("#modalBoxHistorial").load(url,function(response,status,xhr){

           xhr.done(function(){
                iniciarHistorialDagma(codigo);
                var kendoModalBox = modalBox.abrir("modalBoxHistorial",null,"700px",titulo, null);                                
                kendoModalBox.open();
           });
       });
    };