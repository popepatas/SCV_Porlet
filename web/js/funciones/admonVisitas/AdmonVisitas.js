$(document).on("ready",inicioVisitasAdmon);

function inicioVisitasAdmon(){
    $("#contenedorTipoVisitaConsultar,#contenedorTipoVisita,#contenedorMotivoVisita2,#promVisita").hide();
    
    $("#contenedorContratoVisita,#contenedorCodigoProcesoVisita,#contenedorNitVisita,#contenedorRazonSocialVisita,#contenedorDireccionVisita,#contenedorComunaVisita").show();
    //Se inicializan los botones
    $("#consultar").on("click", generarGrillaAdmonVisitas);
    $("#limpiar").on("click",limpiarFormularioAdmonVisitas);
    $("#generar").on("click",generarExcelAdmonVisita);
    $("#cancelarCrearVisita").on("click",cargarRegistrarVisita2);
       
    $("#fechaInicio,#fechaFin").kendoDatePicker({
         format: "dd/MM/yyyy"
    });
        
    campo.lista.crear("tipoVisitaConsultar",servlet.consultar.tipoVisita,{opcion:1});
    

}

var obtenerParametroAdmonVisita = function(){
    
  
       var parametros = {
                       codigoProceso: campo.obtener("codigoProcesoVisita"),
                       direccion : campo.obtener("direccionVisita"),
                       fechaInicio : campo.obtener("fechaInicio"),
                       fechaFin : campo.obtener("fechaFin"),                       
                       contrato: campo.obtener("contratoVisita"),
                       nit: campo.obtener("nitVisita"),
                       razonSocial: campo.obtener("razonSocialVisita"),                       
                       comuna: campo.obtener("comuna")

    };
    
    return parametros;
    
};


var generarExcelAdmonVisita = function(){
    
    var url = servlet.consultar.excelVisitasAdmon;
            
    var parametros = obtenerParametroAdmonVisita();
                    
    var xhr = $.post( url, parametros, abrirExcel);
    
    
    
};

abrirExcel = function(){
  
    window.open('/SCV_Portlet/sources/VisitasPendientes.xls');
    
};

function limpiarFormularioAdmonVisitas(){
    
    
    campo.limpiar("fechaInicio");
    campo.limpiar("fechaFin");    
    campo.limpiar("codigoProcesoVisita");
    campo.limpiar("contratoVisita");
    campo.limpiar("fechaFin");
    campo.limpiar("nitVisita");
    campo.limpiar("razonSocialVisita");
    campo.limpiar("direccionVisita");
    campo.limpiar("comuna");
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
   
   
var vistaResultAdmonVisita = function(e)
{
    // Se obtine todo el tr que se va a editar          
     var tr = $(e.target).closest("tr"); 
     var data = this.dataItem(tr); 

     var parametros = {
          codigo : data.id,
          opcion : 2
     };

    
    var url = "../proceso/ResultadoVisita.jsp";
    var titulo = "Registrar Resultado de Visita - " + parametros.codigo;
    
    
    $("#modalBox").load(url,function(response,status,xhr)
    {

        xhr.done(function()
        {
             var codigoVisita = parametros.codigo;   
             
             inicioInsertarResultadoVisita(codigoVisita);
             var kendoModalBox = modalBox.abrir("modalBox",null,"600px",titulo, null);                                
             kendoModalBox.open();
             
        });
    });
};



var vistaActualizarVisita = function(parametros)
{
    
   var url = "../proceso/ActualizarVisita.jsp";
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


var generarGrillaAdmonVisitas = function(){

    var url = servlet.consultar.visitasPorProceso+"?opcion=3" ; 
    
    var parametros = obtenerParametroAdmonVisita();
    
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
                                pageSize: 50,
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
                                             contrato:{editable:false},
                                             codigoProceso:{editable:false},
                                             razon_social:{editable:false},
                                             direccion: {editable:false},
                                             comuna:{editable:false},                                             
                                             nit:{editable:false}
                                                                                  
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
                            { field: "codigoProceso", title: "Código Proceso", width:"90px"},
                            { field: "contrato", title: "Contrato", width:"130px"},
                            { field: "nit", title: "NIT", width:"160px"},
                            { field: "razon_social", title: "Razón Social", width:"200px"},
                            { field: "comuna", title: "Comuna", width: "100px" },                                                      
                            { field: "direccion", title: "Dirección", width: "160px" }//,                            
                           /* { command:  [//Botones de la ultima columna
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
                              width: "190px" 
                            }*/
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



}

