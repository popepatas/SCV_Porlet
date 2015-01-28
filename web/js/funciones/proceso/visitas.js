function inicioVisitas(){
 

    //Se inicializan los botones
    $("#consultar").on("click", generarGrillaVisitas);
    $("#limpiar").on("click",limpiarFormularioVisitas);
    $("#generar").on("click",generarExcel);
    $("#cancelarCrearVisita").on("click",cargarRegistrarVisita2);
    $("#promVisita").on("click",{tipoVisita:2},mostrarCalendario);
     $(document).on("click",".verResultado",abrirArchivosVisitaCargados);
    $("#fechaInicio,#fechaFin").kendoDatePicker({
         format: "dd/MM/yyyy"
    });
        
    campo.lista.crear("tipoVisitaConsultar",servlet.consultar.tipoVisita,{opcion:1});;
    campo.lista.crear("motivoVisita2",servlet.consultar.motivosVisitas, {opcion:1});

}


generarExcel = function(){
    
    var url = servlet.consultar.excelVisitas;
    var codigoProceso;
    
    if( campo.obtener("codigoProcesoVisita") == ''){
        codigoProceso = campo.obtener("codigoProceso");
        
    }
            
    var parametros = {
                       codigoProceso: codigoProceso,
                       tipoVisita : campo.obtener("tipoVisitaConsultar"),
                       fechaInicio : campo.obtener("fechaInicio"),
                       fechaFin : campo.obtener("fechaFin"),
                       estadoVisita : campo.obtener("estadoVisita"),
                       contrato: campo.obtener("contratoVisita"),
                       nit: campo.obtener("nitVisita"),
                       razonSocial: campo.obtener("razonSocialVisita"),
                       motivoVisita : campo.obtener("motivoVisita2")
                       

                    };
                    
    $.post( url, parametros, abrirExcel);
    
    
    
};

abrirExcel = function(){
  
    document.location.href = '/SCV_Portlet/sources/Visitas.xls';
    
};

function limpiarFormularioVisitas(){
    
    campo.limpiar("tipoVisitaConsultar");
    campo.limpiar("fechaInicio");
    campo.limpiar("fechaFin");
    campo.limpiar("estadoVisita");
    campo.limpiar("motivoVisita2");
}
/* Se carga el formulario de registrar visita y se le asignan sus validaciones*/            
 var cargarRegistrarVisita2 = function(fecha){
     var url = "RegistrarVisita.jsp";
     var titulo = "Registrar Visita";
       $("#modalBox2").load(url,function(response,status,xhr){
           
           xhr.done(function(){
               
                inicioCrearVisita();
                var kendoModalBox = modalBox.abrir("modalBox2",null,"600px",titulo, null);                                
                kendoModalBox.open();
                $("#contenedorFechaVisita").show();
           });
       });
   };
   
   
var vistaResultVisita = function(parametros)
{
    
    var url = "ResultadoVisita.jsp";
    var tituloResultado = "Registrar Resultado de Visita - " + parametros.codigo;
    
    
    $("#modalBox").load(url,function(response,status,xhr)
    {

        xhr.done(function()
        {
             var codigoVisita = parametros.codigo;   
             
             inicioInsertarResultadoVisita(codigoVisita);
             var kendoModalBox = modalBox.abrir("modalBox",null,"600px",tituloResultado, null);                                
             kendoModalBox.open();
             
        });
    });
};



var vistaActualizarVisita = function(parametros)
{
    
    var url = "ActualizarVisita.jsp";
    var titulo = "Actualizar Visita";
    
    
    $("#modalBox").load(url,function(response,status,xhr)
    {

        xhr.done(function()
        {

             inicioActualizarVisita(parametros);      
             var kendoModalBox = modalBox.abrir("modalBox",null,"600px",titulo, null);                                
             kendoModalBox.open();
        });
    });
};



var generarGrillaVisitas = function(){

    var url = servlet.consultar.visitasPorProceso+"?opcion=1" ; 
    
     var codigoProceso;
    
    if( campo.obtener("codigoProcesoVisita") == ''){
        codigoProceso = campo.obtener("codigoProceso");
        
    }
    
     var grilla = $("#grillaVisitas").data('kendoGrid'); 
    
    if(grilla != undefined){
       grilla.destroy();
    }
            
    var parametros = {
                       codigoProceso: codigoProceso,
                       tipoVisita : campo.obtener("tipoVisitaConsultar"),
                       fechaInicio : campo.obtener("fechaInicio"),
                       fechaFin : campo.obtener("fechaFin"),
                       estadoVisita : campo.obtener("estadoVisita"),
                       contrato: campo.obtener("contratoVisita"),
                       nit: campo.obtener("nitVisita"),
                       razonSocial: campo.obtener("razonSocialVisita"),
                       motivoVisita: campo.obtener("motivoVisita2")
                       

                    };
    
    // Sirver para establecer los parametros que se van a enviar o recibir al backend
    var dataSource = new kendo.data.DataSource(
                        
                        {    
                            //Se establece a donde y que sele va a enviar al backend
                            transport: {
                                    read: {
                                        url:url,
                                         data:parametros, 
                                        dataType: "json",
                                        type:"POST",
                                        cache:false
                                    }
                                },
                                batch: true,
                                pageSize: 20,
                                serverPaging:true,
                                // que es lo que se va a recibir del backend
                                schema: {
                                        total: function(response) {  
                                            if(response.length>0){
                                               return response[0].total;
                                            }
                                        },
                                        model: {
                                          id: "codigo",
                                          fields: {
                                             codigo: { editable: false, nullable: true},
                                             nombre: { editable: false },
                                             apellidos: { editable: false, nullable: true},
                                             fecha_visita: { editable: false },
                                             motivo: { editable: false, nullable: true},
                                             estado: { editable: false },
                                             tipovisita: { editable: false },
                                             resultado : {editable:false},
                                             tecnicoVisito : {editable:false}
                                            }
                                        }
                                }
                                
                       }
            
            );

    $("#grillaVisitas").kendoGrid({
                        height: 430,
                        width:800,
                        autoSync: true,
                       
                        dataSource: dataSource,                        
                        columns:
                         [  
                            { field: "nombre", title: "Técnico", 
                               template:"#= nombre# #= apellidos# ", width: "200px" },                            
                            { field: "fecha_visita", title: "Fecha de visita", width: "160px" }, 
                            { field: "tipovisita", title: "Tipo Visita", width: "160px" }, 
                            { field: "motivo", title: "Motivo", width: "160px"}, 
                            { field: "resultado", title: "Resultado", width: "160px",
                                template:"<div data-codvisita='#=codigo#' class='cv-link verResultado'> #=resultado# </div>"
                            }, 
                            { field: "tecnicoVisito", title: "Tecnico Visitó", width:"160px"},
                            { field: "estado", title: "Estado", width: "160px" },
                           
                            { command:  [//Botones de la ultima columna
                                          { //Boton editar
                                             name:"edit",
                                             text:"Editar",
                                             click: function(e){
                                                 
                                                 // Se obtine todo el tr que se va a editar          
                                                  var tr = $(e.target).closest("tr"); 
                                                  var data = this.dataItem(tr); 
                                                
                                                  var param = {
                                                       codigo : data.id,
                                                       opcion : 2
                                                  };
                                                     vistaActualizarVisita(param);  
                                             }
                                          } ,                                     
                                          { //Boton Registrar Resultado
                                             name:"Resultado",
                                             text:"Resultado",
                                             click: function(e){
                                                 
                                                 // Se obtine todo el tr que se va a editar          
                                                  var tr = $(e.target).closest("tr"); 
                                                  var data = this.dataItem(tr); 
                                                
                                                  var parametros = {
                                                       codigo : data.id,
                                                       opcion : 2
                                                  };
                                                     vistaResultVisita(parametros);  
                                             }
                                          }    
                                        ],
                              title: "", 
                              width: "180px" 
                            }
                        ],
                       pageable: {
                           refresh: true
                       },                        
                       editable:{
                          
                          confirmation: false
                        },
                        //Funcion eliminar
                        remove: function(e){
                                              

                        }
                    
                           
     });



};



var abrirArchivosVisitaCargados = function(){
    
    var $this = $(this);
     
    var codigo = $this.data("codvisita");
    
    generarGrillaAchivosVisitas(codigo);
      var kendoModalBox =    modalBox.abrir("contenedorArchivosSubidoVisita",null,"500px", "Archivos Cargados", null);       
       kendoModalBox.open();
       kendoModalBox.center();
 
    
};

var DescargarArchivoVisita = function (codigo){
    
    
    var codigo = codigo;
    var url = servlet.cargaArchivos.descargar;
        url += "?opcion=1&codigoArchivo="+codigo;
    window.open(url, "_blank"); 
    
};

var eliminarAnexoVisita = function(codigoArchivo){
    
    var url = servlet.cargaArchivos.eliminar;
    var xhrEliminar;
    var parametros = {
                       opcion : 1,
                       codigoArchivo : codigoArchivo
                     };
    //Te trae el datatype
      var grilla = $("#grillaArchivosSubidoVisita").data('kendoGrid'); 
      
           
    
     if(confirm("¿Esta seguro que desea eliminar este registro?")){   
         
           xhrEliminar = $.ajax({
                                type:"POST",
                                data: parametros,
                                url: url,
                                cache :false
                            });
            
            xhrEliminar.done(function(data){
                        
                        if(data.error ==  0){
                            grid.cancelChanges();  
                            alert("No se pudo eliminar el registro.");
                            
                        }
                        
                       if(grilla != undefined){
                              grilla.destroy();
                       }
             });
     }
    
};



var generarGrillaAchivosVisitas = function(codigoVisita){

    var url = servlet.consultar.archivosVisitasCargados; 
    
    var grilla = $("#grillaArchivosSubidoVisita").data('kendoGrid'); 
    
    if(grilla != undefined){
       grilla.destroy();
    }
            
    var parametros = {
                       codigoVisita: codigoVisita,
                       opcion : 1
                    };
    
    // Sirver para establecer los parametros que se van a enviar o recibir al backend
    var dataSource = new kendo.data.DataSource(
                        
                        {    
                            //Se establece a donde y que sele va a enviar al backend
                            transport: {
                                    read: {
                                        url:url,
                                         data:parametros, 
                                        dataType: "json",
                                        type:"POST"
                                    }
                                },
                                batch: true,
                                pageSize: 20,
                                serverPaging:true,
                                // que es lo que se va a recibir del backend
                                schema: {
                                        total: function(response) {  
                                            if(response.length>0){
                                               return response[0].total;
                                            }
                                        },
                                        model: {
                                          id: "codigo",
                                          fields: {
                                             codigo: { editable: false, nullable: true},
                                             nombreArchivo: { editable: false }                            
                                            }
                                        }
                                }
                                
                       }
            
            );

    $("#grillaArchivosSubidoVisita").kendoGrid({
                        
                        width:600,
                        autoSync: true,
                       
                        dataSource: dataSource,                        
                        columns:
                         [  
                                                     
                            { field: "nombreArchivo", title: "Nombre Archivo", width: "160px" },
                           
                            { command:  [//Botones de la ultima columna
                                          { //Boton editar
                                             name:"download",
                                             text:"Descargar",
                                             click: function(e){
                                                  
                                                  var tr = $(e.target).closest("tr"); // Se obtine todo el tr que se va a editar          
                                                  var data = this.dataItem(tr); 
                                                  
                                                  var codigo = data.id;                                                 
                                                  DescargarArchivoVisita(codigo);
                                             }
                                          }/*,
                                          { 
                                            name:"destroy", 
                                            text:"Eliminar"                                           
                                          }*/    
                                        ],
                              title: "", 
                              width: "180px" 
                            }
                        ],
                       pageable: {
                           refresh: true
                       },                        
                       editable:{
                          
                          confirmation: false
                        },
                        //Funcion eliminar
                        remove: function(e){
                            
                                var codigo = e.model.id; 
                                eliminarAnexoVisita(codigo);                           

                        }
                    
                           
     });



};
