
$(document).on("ready", inicio);

function inicio(){
 

    //Se inicializan los botones

    $("#consultar").on("click", generarGrilla);
    $('#limpiar').on("click", limpiarCampos);
        
       
    $("#fechaInicio,#fechaFin").kendoDatePicker({
         format: "dd/MM/yyyy"
    });

    campo.lista.crear("ciiu",servlet.consultar.actEconomica,{});
    

    $("#contratoBqa, #anioBqa").on('keypress', validarEscrituraNumero);
    $("#nitBqa").on('keypress', validarEscrituraNit);
    
}

var limpiarCampos = function(){
    campo.asignar('nroProceso','');
    campo.asignar('nitBqa','');
    campo.asignar('anioBqa','');
    campo.asignar('contratoBqa','');
    campo.asignar('ciiu','');
    campo.asignar('fechaInicio','');
    campo.asignar('fechaFin','');
}

/*
 * Función que valida que solo se ingresen números
 * @retrun vacio
 */

function validarEscrituraNumero () {
   
   return campo.numerico(event);
}

/*
 * Función que valida que lo ingresado tenga formato NIT
 * @retrun vacio
 */

function validarEscrituraNit () {
   
   return campo.nit(event,this);
}

var mostraAdvertencia = function(dataItem){
   
    var html = '';
    
    if(dataItem.valRQ === 'NO'){

          html = '<img src="../images/cancel.png" class="advertencia" />';
    } 
    return html;
};

function generarGrilla(){

    var url = servlet.consultar.procesoCV; 
    var grilla = $("#grillaProceso").data('kendoGrid'); 
    
    if(grilla != undefined){
       grilla.destroy();
    }
    
     
    
    var parametros = { 
            opcion:1,
            nroProceso: campo.obtener("nroProceso"),
            nitBqa: campo.obtener("nitBqa"),
            anio: campo.obtener("anioBqa"),
            contratoBqa: campo.obtener("contratoBqa"),
            ciiuBqa: campo.obtener("ciiu"),
            fechaInicio: campo.obtener("fechaInicio"),
            fechaFin: campo.obtener("fechaFin")
           
      };
    
    
    
    
    var dataSource = new kendo.data.DataSource(
                        
                        {                             
                            transport: {
                                    read: {
                                        url:url,
                                        data:parametros, 
                                        dataType: "json",
                                        cache:false
                                    }
                                },
                                batch: true,
                                pageSize: 100,
                                serverPaging: true,
                                schema: {
                                        model: {
                                          id: "codigoProceso",
                                          fields: {
                                            codigo: { editable: false },
                                            contrato:{editable:false},
                                            codigoProceso: { editable: false },
                                            nit: { editable: false },
                                            actividadEconomica: { editable: false },
                                            razonSocial: { editable: false },
                                            direccion: { editable: false },
                                            fechaProceso: { editable: false },
                                            estado: { editable: false },                                            
                                            descpActividad: { editable: false },
                                            tipoInforme: { editable: false },
                                            valRQ :{editable:false}
                                                                                   
                                            }
                                        }
                                }
                                
                       }
            
            );

    $("#grillaProceso").kendoGrid({
                        height: 430,
                        width:800,
                        autoSync: true,
                       
                        dataSource: dataSource,                        
                        columns:
                         [                            
                            //Columnas de la Grilla{{#alert  }} 
                                                     
                            { field:"codigoProceso", title:"Codigo Proceso", width:"80px" },
                            { field: "valRQ", title: "Advertencia", width: "80px",
                            template:function(dataItem){ return mostraAdvertencia(dataItem);}},
                            { field:"contrato", title:"Contrato", width:"80px" },                            
                            { field: "nit", title:"Nit", width: "110px" },                            
                            { field:"razonSocial" , title:"Razón Social" , width: "160px",
                            template: "<span class=\"logRazon cv-link\">#: razonSocial # <input type=\"hidden\" value=\"#: codigo#\"> </span>"},
                            { field: "fechaProceso", title:"Fecha de Creación", width: "120px" },
                            { field: "tipoInforme", title:"Tipo de Informe", width: "130px" },                            
                            { field: "estado", title:"Estado del Proceso", width: "120px" },                                                        
                            { field:"actividadEconomica" , title:"Actividad Económica" , width: "150px",
                            template: "<span class=\"logAct cv-link\">#: actividadEconomica # <input type=\"hidden\" value=\"#: codigo#\"> </span>"},
                                                    
                            { command:  [//Botones de la ultima columna
                                          { 
                                             name:"edit",
                                             className: "Edit-Cliente",
                                             text:"Editar",
                                             click: function(e){
                                                 
                                                  var tr = $(e.target).closest("tr"); // Se obtine todo el tr que se va a editar          
                                                  var data = this.dataItem(tr); 
                                                  
                                                  document.location.href="Proceso.jsp?proceso="+data.id+"#Proceso";
                                                  
                                                  var parametros = {
                                                       codigo : data.id,
                                                       opcion : 2
                                                  };
                                                     
                                             }
                                          },
                                          { 
                                            name:"destroy", 
                                            text:"Eliminar"
                                          }                                                   
                                        ],
                              title: "", 
                              width: "200px" 
                            }
                        ],
                       pageable: {
                           refresh: true
                       },                        
                       editable:{
                          
                          confirmation: false
                        },
                         remove: function(e){
                            var codigo = e.model.id; 
                                                  
                            var parametros = {
                                    codigo :codigo                                                          
                                };

                             eliminarCliente(parametros);
                                                 
                         }
                                
     });

}


function  eliminarCliente(parametros){
    
    var url = servlet.eliminar.procesoVertimiento;    
    var grid = $("#grillaProceso").data("kendoGrid");
       
    
     if(confirm("Esta operación no tiene reversa: ¿Esta seguro que desea eliminar este registro?")){   
       var xhr = $.ajax(
                {
                    type:"POST",
                    data: parametros,
                    url: url,
                    cache :false
                }
             );
     
          xhr.done(function(data,status,xh){
            
                if(data.error ==  0){
                    grid.cancelChanges();  
                    alert("El campo no puede ser eliminado.");
                }

                    
          });
     }
    
    
}