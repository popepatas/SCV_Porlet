
$(document).on("ready", inicio);

function inicio(){
 	
   

   //Se inicializa el validador
   $("#div_clienteConContratos").kendoValidator();

    
    $("#consultar").on("click", generarGrilla);
    
    $("#limpiar").on("click", limpiarClienteContrato);
    
     $("#contrato").on("keypress", function(){
      
        return  campo.numerico(event);
        
        
    });
    
    $("#nit").on("keypress", function(){
        
      return  campo.nit(event, this);
        
        
    });
    
       

}


function crearProceso(parametros){
    
      document.location.href = "../proceso/CrearProcesoGeneral.jsp?contrato="+parametros.contrato+"&nit="+parametros.nit+"#crearProceso";
    
}

function limpiarClienteContrato(){
    
     campo.limpiar("nitBqa");
     campo.limpiar("contratoBqa");      
    
}

function generarGrilla(){

    var url = servlet.consultar.procesoCV; 
    
    var parametros = { 
            opcion:1,
            nitBqa: campo.obtener("nitBqa"),
            contratoBqa: campo.obtener("contratoBqa")            
      };
    
    
        
    var dataSource = new kendo.data.DataSource(
                        
                        {                             
                            transport: {
                                    read: {
                                        url:url,
                                        data:parametros, 
                                        dataType: "json",
                                        cache:false
                                        //,type:"POST"
                                    }
                                },
                                batch: true, 
                    resizable: true,
                                pageSize: 100,
                                serverPaging: true,
                                schema: {
                                        total: function(response) {  
                                            if(response.length>0){
                                               return response[0].total;
                                            }
                                         },
                                        model: {
                                          contrato: "contrato",
                                          nit : "nit",
                                          fields: {
                                            razonSocial: { editable: false },
                                            nit: { editable: false },
                                            actividadEconomica: { editable: false },
                                            contrato: { editable: false },
                                            fechaProceso: { editable: false }                                                                                   
                                           }
                                        }
                                }
                                
                       }
            
            );

    $("#grillaClienteConContratos").kendoGrid({
                        height: 430,
                        width:800,
                        autoSync: true,
                       
                        dataSource: dataSource,                        
                        columns:
                         [                            
                            //Columnas de la Grilla
                            { field:"razonSocial" , title:"Razón Social" , width: "150px" },                            
                            { field:"nit" , title:"NIT" , width: "150px" },
                            { field:"actividadEconomica" , title:"Actividad Económica" , width: "150px" },
                            { field:"contrato" , title:"Contrato" , width: "150px" },
                            { field:"fechaProceso" , title:"Fecha Inicio del Proceso" , width: "150px" },
                                                    
                            { command:  [//Botones de la ultima columna
                                          { 
                                             name:"NewProcess",
                                             text:"NuevoProceso",
                                             click: function(e){
                                                 
                                                  var tr = $(e.target).closest("tr"); // Se obtine todo el tr que se va a editar          
                                                  var data = this.dataItem(tr); 
                                                                                                  
                                                     crearProceso(data);  
                                             }
                                          }                                        
                                        ],
                              title: "", 
                              width: "150px" 
                            }
                        ],
                       pageable: {
                           refresh: true
                       },                        
                       editable:{
                          
                          confirmation: false
                        }
                           
     });

}
