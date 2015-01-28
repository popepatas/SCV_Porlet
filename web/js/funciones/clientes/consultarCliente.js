
$(document).on("ready", inicio);

function inicio(){

    
   
    $("#ciiuConsultar").select2();
   //Se inicializa el validador
    $("#div_cliente").kendoValidator();

    //Eventos
    $("#consultar").on("click", generarGrilla);
    $("#nuevo").on("click",{onLoad:abrirModalBoxNuevo},CargarFormCliente);
    $("#limpiar").on("click",limpiarFormulario);
    $("#generarPDF").on("click", generarPDFNotificacion);
    
    $(document).on("click","#checkAll",function(){

        if(this.checked == true){
           $("[name='clientesChecked']").attr("checked",true);
        }else{
           $("[name='clientesChecked']").attr("checked",false);
        }

    });
     
    
    var urlLogs = servlet.consultar.logsCliente;
    $(document).on("click",".logRazon",{tipoCampo:"Razón Social",opcion:2},mostrarLogs);
    $(document).on("click",".logAct",{tipoCampo:"Actividad Económica",opcion:2},mostrarLogs);
    
    
   /* var cadena = "+" ; $("[name='clientesChecked']:checked").each(function(index,obj){
       $this = $(this);
      cadena += $this.attr("id")+"+";
  
    });*/
    
    //$("#grillaCliente").on("click",".Edit-Cliente",mostrarClienteEdicion);
    
  
    $("#nitConsultar").on("keypress", function(){
        
      return  campo.nit(event, this);
        
        
    });
    
    
    $("#comunaConsultar").kendoNumericTextBox({
        format: "#",
        min: 0
        
    });

    var urlAct = servlet.consultar.actEconomica;
    campo.lista.crear("ciiuConsultar",urlAct,{}); 

}
var generarPDFNotificacion = function(){
    
    //Obtenemos los clientes seleccionados
    var checkedIds = new Array();
    checkedIds = $(":checkbox:checked").map(function() {
        return this.id;
    }).get();
    
    if(checkedIds.length > 0){
        
        var url = "/SCV_Portlet/GenerarPdfServlet?opcion=1";
        var url2 = "/SCV_Portlet/GenerarPdfServlet?opcion=4";

        var jqxhr = $.post( url,{ clientes: "{"+checkedIds+"}" });
        
        jqxhr.done(function(){
            document.location.href=url2;
            //window.open(url2,"_blank");
        });
        
    }else{
        alert("Debe escoger al menos un cliente.");
    }
    
    

}

function limpiarFormulario(){
    
    campo.limpiar("nitConsultar");
    campo.limpiar("razonConsultar");
    campo.limpiar("ciiuConsultar");    
    campo.limpiar("direccionConsultar");
    campo.limpiar("barrioConsultar");
    campo.kendo.NumericTextBox("comunaConsultar").value("");;
    campo.limpiar("usoServicioConsultar");
       var ciuuSelect = $("#ciiuConsultar").data("select2");
        ciuuSelect.val("");
    
}

function CargarFormCliente(event){
      
    $("#modalBox").load("CrearCliente.jsp",function(response,status,xhrFormCliente){
            
            xhrFormCliente.done(function(){
                inicioClientes();        
                pntlla.iniciar2();
                event.data.onLoad();
            });
        
    });
}


function abrirModalBoxNuevo(){
        
       var modal = modalBox.abrir("modalBox",null,"800px", "Nuevo Cliente",null);
        modal.open();
 }


function abrirModalBoxCrearProceso(){
        
       var modal = modalBox.abrir("modalBox2",null,"800px", "Crear Proceso",null);
        modal.open();
 }


function abrirModalBoxEditar(){
    
    var modal = modalBox.abrir("modalBox",null,"800px", "Editar Cliente",null);
    modal.open();
    
}

var onCloseModalNuevo = function(){
  
    
};

var onCloseModalEditar = function(){
  
    
};


function mostrarClienteEdicion(parametros){
    
        var url = servlet.consultar.cliente;
      
        var xhr = $.ajax(
                       {
                           type:"POST",
                           data: parametros,
                           url: url,
                           cache :false
                       }
                    );
        //Cuando la información haya sido cargada
        xhr.done(function(response,status,xhr){
            var data = response;
            var xhrListaCiiu;
            //cargamos el Form de clientes
            $("#modalBox").load("CrearCliente.jsp",function(response,status,xhrFormCliente){
                 
                //Cuando el Form de clientes este listo
                //asignamos la información al formulario
                         xhrFormCliente.done(function(){    
                             
                            inicioClientes();
                            
                            xhrListaCiiu = campo.lista.crear("ciiu",servlet.consultar.actEconomica,{});
                            
                            xhrListaCiiu.done(function(response, status, xhr){
                                campo.asignar("ciiu",data.actividadEconomica);
                                var select = $("#ciiu").data("select2");
                                select.val(data.actividadEconomica);
                                 abrirModalBoxEditar();
                            });

                            campo.direccion.asignar("completaDireccion",data.direccion);                            
                            campo.kendo.NumericTextBox("telefonoDos").value(data.telefono2);
                            //campo.asignar("estadoVertimiento",data.estadoVertimiento);
                            campo.asignar("razon",data.razonSocial);
                            campo.asignar("representante",data.repLegal);
                            campo.kendo.NumericTextBox("telefono").value(data.telefono);                            

                            campo.asignar("emailSecundario",data.email2);
                            campo.asignar("codigo",data.codigo);
                            campo.kendo.NumericTextBox("comuna").value(data.comuna);
                            campo.asignar("usoServicio",data.usoServicio);
                            campo.asignar("email",data.email);
                            campo.asignar("nit",data.nit);
                            campo.asignar("barrio",data.barrio);                            
                            
                            campo.asignar("pagina",data.web);
                            pntlla.iniciar2() ; 
                            
                            $("#ciiu").select2();
                            
                           
                           
                   });
            });
         

        });
    
}

function mostrarLogs(event){
   
    var parametros = event.data;
        parametros.codigo = $(this).find("input[type='hidden']").val();
     var titulo = " Log "+ parametros.tipoCampo;
         
    $("#modalBox").load("LogsCliente.jsp", function(response, status, xhrGrillaLogs){
        
                xhrGrillaLogs.done(function(){

                  generarGrillaLogsCliente(parametros);
                    var modal = modalBox.abrir("modalBox",null,"800px",titulo ,null);
                    modal.open();

                });
                
        
    });
    
}


/*
 * Permite en eliminar un registro de la BD
 * @retrun vacio
 */

function eliminarRegistro(parametros){
    
    var url = servlet.eliminar.cliente;
    
    var grid = $("#grillaCliente").data("kendoGrid");
     if(confirm("¿Esta seguro que desea eliminar este registro?")){   
            var xhrEliminarRegistro =   $.ajax(
                                               {
                                                   type:"POST",
                                                   data: parametros,
                                                   url: url,
                                                   cache :false

                                               }
                                            );

                   xhrEliminarRegistro.done( function(data,status,xhr){

                           if(data.length >0){
                               if(data[0].error ==  0){
                                   grid.cancelChanges();  
                                   alert("El campo no puede ser eliminado, por que ya esta asignado en el sistema");
                               }
                           }else{
                                grid.cancelChanges();  
                           }

                    }); 
            }
}

function generarGrilla(){
    
    var grilla = $("#grillaCliente").data('kendoGrid'); 
    
    if(grilla != undefined){
       grilla.destroy();
    }
    
    var url = servlet.consultar.cliente; 
    
    var parametros = { 
            opcion:1,
            nit: campo.obtener("nitConsultar"),
            ciiu: campo.obtener("ciiuConsultar"),
            razonSocial: campo.obtener("razonConsultar"),
            direccion:  campo.obtener("direccionConsultar"),
            comuna: campo.kendo.NumericTextBox("comunaConsultar").value() ,            
            usoServicio: campo.obtener("usoServicioConsultar"),
            barrio: campo.obtener("barrioConsultar")
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
                                schema: {
                                        model: {
                                          id: "codigo",
                                          fields: {
                                            codigo: { editable: false },
                                            nit: { editable: false },
                                            actividadEconomica: { editable: false },
                                            razonSocial: { editable: false },
                                            direccion: { editable: false },
                                            barrio: { editable: false },
                                            comuna: { editable: false },                                            
                                            uso: { editable: false },
                                            descpActividad: { editable: false },
                                            codigoCiiu:{editable:false}
                                                                                   
                                            }
                                        }
                                }
                                
                       }
            
            );

    $("#grillaCliente").kendoGrid({
                        height: 430,
                        width:800,
                        autoSync: true,
                       
                        dataSource: dataSource,                        
                        columns:
                         [                            
                            //Columnas de la Grilla
                            { field:"nit" , title:"NIT" , width: "100px"},                            
                            { field:"razonSocial" , title:"Razón Social" , width: "150px",
                            template: "<span  class=\"logRazon cv-link\">#: razonSocial # <input type=\"hidden\" value=\"#: codigo#\"></span>"},
                            { field:"actividadEconomica" , title:"Actividad Económica" , width: "150px",
                            template: "<span class=\"logAct cv-link\">#:codigoCiiu# / #:actividadEconomica# <input type=\"hidden\" value=\"#: codigo#\"> </span>"},
                            { field:"direccion" , title:"Dirección" , width: "100px" },
                            { field:"barrio" , title:"Barrio" , width: "100px" },
                            { field:"comuna" , title:"Comuna" , width: "60px" },                                                        
                            { field:"usoServicio", title:"Uso del Servicio", width:"100px"},
                            {   title:  "<input id='checkAll' type='checkbox' class='check-box' />",
                                template: "<input id=\"#=codigo#\" type=\"checkbox\"  name=\"clientesChecked\" />",    
                                width:"35px"

                            },
                                                    
                            { command:  [//Botones de la ultima columna
                                          { 
                                             name:"edit",
                                             className: "Edit-Cliente",
                                             text:"Editar",
                                             click: function(e){
                                                 
                                                  var tr = $(e.target).closest("tr"); // Se obtine todo el tr que se va a editar          
                                                  var data = this.dataItem(tr); 
                                                
                                                  var parametros = {
                                                       codigo : data.codigo,
                                                       opcion : 2
                                                  };
                                                     mostrarClienteEdicion(parametros);  
                                             }
                                          },
                                          { 
                                            name:"destroy", 
                                            text:"Eliminar"                                           
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
                        remove: function(e){
                            
                                var codigo = e.model.id; 
                                var parametros = {
                                                    codigo :  codigo                                                        
                                                 };
                                                 
                                eliminarRegistro(parametros);                           

                        }
                    
                           
     });

}
