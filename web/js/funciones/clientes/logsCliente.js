


function inicioLogsCliente(){
 	
   
    generarGrilla();
}



function generarGrillaLogsCliente(param){

    var url = servlet.consultar.logsCliente; 
    
    
    var parametros = {             
            tipoCampo: param.tipoCampo,            
            codigo: param.codigo
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
                                pageSize: 20,
                                schema: {
                                        model: {
                                          
                                          fields: {
                                            campo: { editable: false },
                                            valorAnterior: { editable: false },
                                            valorPosterior: { editable: false },                                            
                                            fechaModificacion: { editable: false }
                                                                                   
                                            }
                                        }
                                }
                                
                       }
            
            );

    $("#grillaLogsCliente").kendoGrid({
                        height: 430,
                        width:800,
                        autoSync: true,
                       
                        dataSource: dataSource,                        
                        columns:
                         [                            
                            //Columnas de la Grilla
                            { field:"campo" , title:"Campo" , width: "90px" },                            
                            { field:"valorAnterior" , title:"Anterior" , width: "100px" },
                            { field:"valorPosterior" , title:"Actual" , width: "100px" },
                            { field:"fechaModificacion" , title:"Fecha Modificación" , width: "100px" }  
                           
                        ],
                       pageable: {
                           refresh: true
                       }
                    
                           
     });

}