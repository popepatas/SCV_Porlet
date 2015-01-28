//Manejo de Overlay
 $(document).on("ready",function(){
     var $overlay = $(".overlay");
       $this = $(this);
        $(document).ajaxStart(function(){
           $overlay.show();
        });
      /* $(document).ajaxComplete(function(){
            $overlay.fadeOut(400);
        });*/
       $(document).ajaxStop(function(){
             $overlay.fadeOut(400);
       });
       
        $(document).ajaxError(function(){
            alert("Hubo problemas al cargar la pagina, por favor presione F5 para actualizar.");
              $overlay.fadeOut(400);
       });
});
/*elmto hace referencia a elemento*/
 var elmto = {

                /*
                 * Función deshabilita elementos
                 * @retrun vacio
                 */
                        deshabilitar: function (campo){

                            document.getElementById(campo).disabled = true;
                },
                /*
                 * Función habilita elementos
                 * @retrun vacio
                 */
                 habilitar: function(campo){

                            document.getElementById(campo).disabled = false;
                },

                /*
                 * Función oculta elementos
                 * @retrun vacio
                 */

                 ocultar: function (campo){
                     document.getElementById(campo).style.display = 'none';
                },           

                /*
                 * Función muestra elementos ocultos
                 * @retrun vacio
                 */

                 mostrar: function (campo){
                     document.getElementById(campo).style.display = '';
                }
 


 };

var msg ={

        guardar:{
               error: "Su registro no pudo ser guardado",
               exito:"Su registro ha sido almacenado exitosamente"
               
        },
        actualizar:{
             
        },
        eliminar:{
              error:"El registro no pudo ser eliminado debido a que ya ha sido asignado en alguna parte del sistema."
        },
        consultar:{
            error: "Se presento un problema por favor recarge la pagina."
        }
    
};


var servlet ={
        exportar:{
                excel: "/SCV_Portlet/exportarExcel",
                pdf: "/SCV_Portlet/exportarPDF"
        },      
        consultar:{
                    actEconomica: "/SCV_Portlet/SeleccionarActEconomica?opcion=1",
                    undMedida:"/SCV_Portlet/SeleccionarUnidadesMedida",
                    paramFisQuim : "/SCV_Portlet/SeleccionarParamfisicoquimicos",
                    tipoParmetros:"/SCV_Portlet/SeleccionarTiposPrmfisicoquimicos",
                    actvParam :  "/SCV_Portlet/SeleccionarActParamfisicoquimicos",
                    cliente: "/SCV_Portlet/SeleccionarClientes",     
                    documentoRequerido:"/SCV_Portlet/SeleccionarDocumentacionRequerida",
                    TiposContacto:"/SCV_Portlet/SeleccionarTiposContactos",
                    laboratorios: "/SCV_Portlet/SeleccionarLaboratorios",
                    consultores: "/SCV_Portlet/SeleccionarConsultores",
                    estadosProcesos: "/SCV_Portlet/SeleccionarEstadoProceso",
                    estados: "/SCV_Portlet/SeleccionarEstado",
                    tiposInformeVertimientos: "/SCV_Portlet/SeleccionarTiposInformeVertimientos",
                    tarifas: "/SCV_Portlet/SeleccionarTarifas",
                    puntosVertimiento:"/SCV_Portlet/SeleccionarPuntoVertimientoContrato",                    
                    tiempoBackup:"/SCV_Portlet/SeleccionarTiemposBackup",
                    motivosVisitas:"/SCV_Portlet/SeleccionarMotivosVisitas",
                    asigContrato:"/SCV_Portlet/SeleccionarAsociacionContratos",
                    logsCliente:"/SCV_Portlet/SeleccionarLogsCliente",
                    procesoCV:"/SCV_Portlet/SeleccionarProcesoVertimientos",
                    InfoGeneral:"/SCV_Portlet/SeleccionarInformacionGeneral",
                    fechasVisita:"/SCV_Portlet/SeleccionarFechasVisita",
                    tipoVisita:"/SCV_Portlet/SeleccionarTipoVisita",
                    tecnicos:"/SCV_Portlet/SeleccionarTecnicos",
                    visitasPorProceso:"/SCV_Portlet/SeleccionarVisitas",
                    excelVisitas:"/SCV_Portlet/GenerarExcelVisitas",
                    excelVisitasAdmon:"/SCV_Portlet/GenerarExcelVisitasAdmon",
                    verificaInfoCaracterizacion: "/SCV_Portlet/SeleccionarVerificacionInfoCaracterizacion",
                    monitoreos: "/SCV_Portlet/SeleccionarMonitoreos",
                    acreditacion:"/SCV_Portlet/SeleccionarAcreditacionParametros",
                    archivosInformesCargados:"/SCV_Portlet/SeleccionarArchivosInformesCargados",
                    archivosLodosInfoTec:"/SCV_Portlet/SeleccionarArchivosInfoTec",
                    archivosVisitasCargados:"/SCV_Portlet/SeleccionarArchivosVisita",
                    informacionProcesoSeco:"/SCV_Portlet/SeleccionarInformacionProcesoSeco",
                    informacionTecnica:"/SCV_Portlet/SeleccionarInformacionTecnica",
                    manejoLodos : "/SCV_Portlet/SeleccionarEntidadesLodos",
                    lodosInfoTec : "/SCV_Portlet/SeleccionarLodos",
                    devolucion : "/SCV_Portlet/SeleccionarDevolucionCaracterizacion",
                    contarParamIncumplidos : "/SCV_Portlet/ContarParametrosIncumplidos",
                    contarParam : "/SCV_Portlet/ContarParametros",
                    tasaRetributiva :"/SCV_Portlet/SeleccionarTasaRetributiva"
                    ,resultadoVisita : "/SCV_Portlet/SeleccionarResultadoVisita"
                    
                     //Reportes
                    , reporte1: "/SCV_Portlet/ConsultarReporte1"
                    , reporte2: "/SCV_Portlet/ConsultarReporte2"
                    , reporte3: "/SCV_Portlet/ConsultarReporte3"
                    , reporte4: "/SCV_Portlet/ConsultarReporte4"
                    , reporte5: "/SCV_Portlet/ConsultarReporte5"
                    , reporte6: "/SCV_Portlet/ConsultarReporte6"
                    , reporte7: "/SCV_Portlet/ConsultarReporte7"
                    , reporteVisitasProceso: "/SCV_Portlet/ConsultarVisitasProceso"
                    , reporteMonitoreosProceso: "/SCV_Portlet/ConsultarMonitoreosProceso"
                    , reporteHistorialDagmaProceso: "/SCV_Portlet/ConsultarHistorialDagmaProceso"
                    , reporteAdjuntosProceso: "/SCV_Portlet/ConsultarAdjuntosProceso"
                    , reporteAdjuntosVisita: "/SCV_Portlet/ConsultarAdjuntosVisita"
                    , reporteAdjuntosHistorialDagma: "/SCV_Portlet/ConsultarAdjuntosHistorialDagma"
                    , historialDagma : "/SCV_Portlet/ConsultarHistorialDagma"
                    , excelHistorialDagma : "/SCV_Portlet/GenerarExcelHistorialDagma"
                    , puntosMonitoreo : "/SCV_Portlet/SeleccionarPuntosMonitoreo"
                    , roles : "/SCV_Portlet/SeleccionarRoles"
                    , usuarios : "/SCV_Portlet/SeleccionarUsuarios"
                    , permisos: "/SCV_Portlet/SeleccionarPermisos"
                    
                    
                    
                                        
                    //Dashboard
                    , dashboard1: "/SCV_Portlet/ConsultarDashboard1"
                    , dashboard2: "/SCV_Portlet/ConsultarDashboard2"
                    , dashboard3: "/SCV_Portlet/ConsultarDashboard3"
                    , dashboard4: "/SCV_Portlet/ConsultarDashboard4"
                    , dashboard5: "/SCV_Portlet/ConsultarDashboard5"
                    
                                        
                  },
        insertar:{
                    undMedida:"/SCV_Portlet/InsertarUnidadesMedida",
                    paramFisQuim : "/SCV_Portlet/InsertarPrmfisicoquimicos",
                    tipoParmetros:"/SCV_Portlet/InsertarTiposPrmfisicoquimicos",
                    actvParam :  "/SCV_Portlet/InsertarActParamFisicoquimicos",
                    cliente: "/SCV_Portlet/InsertarCliente",
                    documentoRequerido:"/SCV_Portlet/InsertarDocumentacionRequerida",
                    estadoProceso: "/SCV_Portlet/",
                    TiposContacto:"/SCV_Portlet/InsertarTiposContactos",
                    laboratorios: "/SCV_Portlet/InsertarLaboratorios",
                    tecnicos:"/SCV_Portlet/InsertarTecnicos",
                    consultores: "/SCV_Portlet/InsertarConsultores",
                    tiposInformeVertimientos: "/SCV_Portlet/InsertarTiposInformeVertimientos",                    
                    puntosVertimiento:"/SCV_Portlet/InsertarPuntoVertimientoContrato",
                    motivosVisitas:"/SCV_Portlet/InsertarMotivosVisitas",
                    asigContrato:"/SCV_Portlet/InsertarAsociacionContratos",
                    procesoCV:"/SCV_Portlet/InsertarProcesoVertimiento",
                    InfoGeneral:"/SCV_Portlet/RegistarInformacionGeneral",
                    visitas:"/SCV_Portlet/InsertarVisita",
                    supervisiones:"/SCV_Portlet/RegistrarSuperviciones",
                    resultRupervisiones:"/SCV_Portlet/InsertarResultadoSupervision",
                    resultadoVisitas:"/SCV_Portlet/InsertarResultadoVisita",
                    GuardaMonitoreo:"/SCV_Portlet/InsertarProgramacionMonitoreo",
                    verificarRequisitos:"/SCV_Portlet/InsertarVerificacionInfoCaracterizacion",
                    informeProcesoSeco:"/SCV_Portlet/RegistrarProcesoSeco",
                    informacionTecnica:"/SCV_Portlet/InsertarInformacionTecnica",
                    manejoLodos : "/SCV_Portlet/InsertarEntidadLodos", 
                    historialDagma : "/SCV_Portlet/InsertarHistorialDagma",
                    insertarLodos : "/SCV_Portlet/InsertarLodos",
                    devolucion : "/SCV_Portlet/InsertarDevolucionCaracterizacion",
                    tasaRetributiva :"/SCV_Portlet/RegistrarTasaRetibutiva",
                    usuarios: "/SCV_Portlet/InsertarUsuario",
                    permisos:"/SCV_Portlet/InsertarPermisos"
                    
                    
        },
        eliminar:{
                    undMedida:"/SCV_Portlet//EliminarUnidadesMedida",
                    paramFisQuim : "/SCV_Portlet/EliminarParamfisicoquimicos",
                    tipoParmetros:"/SCV_Portlet/EliminarTiposPrmfisicoquimicos",
                    actvParam :  "/SCV_Portlet/EliminarActParamfisicoquimicos",
                    cliente: "/SCV_Portlet/EliminarCliente",
                    documentoRequerido:"/SCV_Portlet/EliminarDocumentacionRequerida",
                    estadoProceso: "/SCV_Portlet/",
                    tecnicos:"/SCV_Portlet/EliminarTecnicos",
                    TiposContacto: "/SCV_Portlet/EliminarTiposContactos",
                    laboratorios: "/SCV_Portlet/EliminarLaboratorios",
                    consultores: "/SCV_Portlet/EliminarConsultores",
                    tiposInformeVertimientos: "/SCV_Portlet/EliminarTiposInformeVertimientos",
                    puntosVertimiento:"/SCV_Portlet/EliminarPuntoVertimiento",
                    motivosVisitas:"/SCV_Portlet/EliminarMotivosVisitas",
                    asigContrato:"/SCV_Portlet/EliminarAsociacionContrato",
                    manejarLodos : "/SCV_Portlet/EliminarEntidadLodos",
                    historialDagma : "/SCV_Portlet/EliminarHistorialDagma",
                    usuarios: "/SCV_Portlet/EliminarUsuarios",
                    procesoVertimiento:"/SCV_Portlet/EliminarProcesoVertimientos"
                    
        },
        actualizar:{
                    undMedida:"/SCV_Portlet/ActualizarUnidadesMedida",
                    paramFisQuim : "/SCV_Portlet/ActualizarParamfisicoquimicos",
                    tipoParmetros:"/SCV_Portlet/ActualizarTiposPrmfisicoquimicos",
                    actvParam :  "/SCV_Portlet/ActualizarActParamfisicoquimicos",
                    cliente: "/SCV_Portlet/ActualizarCliente",
                    estadoProceso: "/SCV_Portlet/",
                    documentoRequerido:"/SCV_Portlet/ActualizarDocumentacionRequerida",
                    tecnicos:"/SCV_Portlet/ActualizarTecnicos",
                    TiposContacto:  "/SCV_Portlet/ActualizarTiposContactos",
                    laboratorios: "/SCV_Portlet/ActualizarLaboratorios",
                    consultores: "/SCV_Portlet/ActualizarConsultores",
                    estadosProcesos: "/SCV_Portlet/ActualizarEstadosProcesos",
                    tiposInformeVertimientos: "/SCV_Portlet/ActualizarTiposInformesVertimientos",
                    tarifas:"/SCV_Portlet/ActualizarTarifas",
                    puntosVertimiento:"/SCV_Portlet/ActualizarPuntoVertimiento",
                    tiempoBackup:"/SCV_Portlet/ActualizarTiemposBackup",
                    motivosVisitas:"/SCV_Portlet/ActualizarMotivosVisitas",
                    asigContrato:"/SCV_Portlet/ActualizarAsociacionContratos",                                   
                    InfoGeneral :"/SCV_Portlet/ActualizarInformacionGeneral",
                    visitas: "/SCV_Portlet/ActualizarVisita",
                    manejoLodos : "/SCV_Portlet/ActualizarEntidadLodos",
                    historialDagma : "/SCV_Portlet/ActualizarHistorialDagma",
                    usuarios: "/SCV_Portlet/ActualizarUsuarios"
        },
        finalizar:{
            procesoVertimiento:"/SCV_Portlet/FinalizarProcesoVertimientos"
        },
        validar:{
            
        },
        
        cargaArchivos:{
            
            subir:"/SCV_Portlet/CargarArchivoServlet",
            eliminar:"/SCV_Portlet/EliminarArchivoServlet",
            descargar:"/SCV_Portlet/DescargarArchivoServlet",
            eliminarAnexosInformes:"/SCV_Portlet/EliminarAnexoInformes",
            eliminarAnexosMonitoreo:"/SCV_Portlet/EliminarAnexosMonitoreo",
            eliminarAnexosLodos : "/SCV_Portlet/EliminarAnexosLodos"
        }
    
};


var modalBox = {
        abrir : function(contenedor,alto,ancho, titulo, onClose, afterOpen){            

                    var $contenedor = $("#"+contenedor);
                    var parametros = {
                                      width: ancho,
                                      title: titulo,
                                      close: onClose,                                      
                                      modal:true,
                                      activate: afterOpen
                    };
                    
                    var $modal;
                         if (!$contenedor.data("kendoWindow")) {
                              $contenedor.kendoWindow();
                              $modal =$contenedor.data("kendoWindow");                              
                              $modal.center();
                          }
                              $modal = $contenedor.data("kendoWindow");
                              if($modal){
                                $modal.setOptions(parametros);
                              }
                              
                          
                        
                         return   $modal;
                    },
                    
             cerrar: function(contenedor){
                  var $contenedor = $("#"+contenedor).data("kendoWindow");
                  
                  if ($contenedor) {
                              $contenedor.close();                              
                  } 
                  
                 
             }
                    
    
};

 var campo = {

    /*
    * Función que  permite obtener el valor de un campo
    * @retrun String
        * @param  campo,  este siempre representa el id del campo
    */

    obtener: function(campo){
            var temp = document.getElementById(campo);
            if(temp.type === "checkbox"){
                temp =  temp.checked;
            }else {
                temp = temp.value;
            }
            return temp
    },
        /*
    * Función que  permite enviar un al valor de un campo
    * @retrun Array (objeto)
        * @param  nombre,  es la atributo nombre
    */
        obtenerxNombre:function(nombre){
            
            return document.getElementsByName(nombre);
    },
        
        /*
    * Función que  permite obtener el valor de un campo
    * @retrun String o Booleano
        * @param  campo,  este siempre representa el id del campo
    */
    asignar: function(campo,valor){
            
           var temp = document.getElementById(campo);
           
        if(valor != null || valor != undefined){
            if(temp.type == "checkbox"){
                temp.checked = valor;
            }else {
                temp.value = valor;
                
            }
        }
    },
         /*
    * Función que  permite formatear un tipo DATE en dd/mm/yyy
    * @retrun Strign
        * @param  date,  debe ser un tipo Date()
    */
        fecha:{
             formatear: function(date){
                 
                var mes  = date.getMonth() +1;
                var dia  = date.getDate();
                var tiempo = "";
                var fecha = "";
                if (parseInt(mes) <10 && parseInt(mes)  > 0){
                      mes = '0' + mes ;
                }

                if (parseInt(dia) <10 && parseInt(dia)  > 0){
                      dia = '0' + dia ;
                }

                fecha =  dia+'/'+mes+'/'+date.getFullYear();   
                return fecha;
             }
            
            
        },
        
         /*
    * Función que  permite obtener el valor de un campo
    * @retrun vacio
        * @param  campo,  este siempre representa el id del campo
    */
        
        limpiar: function(campo){
           var temp = document.getElementById(campo);
           if(temp.type === "checkbox"){
               temp.checked = false;
           }else {
               temp.value = '';
           }
        },
        convertirBool: function(texto){
            var temp;
            
                if(  texto == 'NO'){
                    temp = false;
                }else if(texto == 'SI'){
                    temp =  true;
                }
                return temp;
               
        },
    /*
    * Función que  permite  solo la escritura de solo numeros
    * @retrun Boolean
    */
    decimal :function(numero){
            var  patron = /^([0-9]*\.?[0-9]+|[0-9]+\.?[0-9]*)?$/;
            return patron.test(numero);
            
            
    },
    numerico: function(e)   {
        if (e.which)
        {
            if(e.which!=8 && e.which!=9 && (e.which<48 || e.which>57))
                return false;
        }
        else if(e.keyCode)
        {
            if(e.keyCode!=8 && e.keyCode!=9 && (e.keyCode<48 || e.keyCode>57))
                return false;
        }
        return true;
    },
        
        check:function(campo){
            
            var checkeado = document.getElementById(campo).checked;
            var retorno = 'NO';
            
            if(checkeado){
                
                retorno = 'SI'
                
            }
            
            return retorno;
            
            
        },      
        
        kendo:{
            NumericTextBox: function(campo){ 
                
                return $("#"+campo).data("kendoNumericTextBox");
                
            },
            Dropdownlist: function(campo){
               return $("#"+campo).data("kendoDropDownList");
            },
            DatePicker: function(campo){
               return $("#"+campo).data("kendoDatePicker");
            }
        },
        
    /*
    * Función que  permite solo la escritura del formato NIT
    * @retrun Boolean
    */
    nit : function (e, obj){// e = evento
                 var temp;
                    if (e.which){
                        if(e.which == 45){
                            temp = obj.value;
                            if(temp.indexOf("-") != -1){
                                return false;
                            }else{
                                return true;
                            }
                        }
                        if(e.which != 8 && e.which != 9 && (e.which < 48 || e.which > 57)){
                            return false;
                        }
                    }
                    else if(e.keyCode){
                        if(e.keyCode == 45){
                            temp = obj.value;
                            if(temp.indexOf("-") != -1){
                                return false;
                            }else{
                                return true;
                            }
                        }
                        if(e.keyCode != 8 && e.keyCode != 9 && (e.keyCode < 48 || e.keyCode > 57)){
                            return false;
                        }
                    }
                    return true;
                },
    /*
    * Función que  no permite permite los espacios en el campo
    * @retrun Boolean
    */          
    sinEspacios: function(e, obj,enie) { // e = evento
        if (e.which){
        
            if( (e.which >= 48 && e.which <= 57) || (e.which >= 65 && e.which <= 90 ) || (e.which >= 97 && e.which <= 122 )  || e.which == 8 || e.which == 9 ){
                return true;
            }else if(enie == true && (e.which == 209 || e.which == 241)){//excepcion ñ y Ñ 
              return true;
            }
        }
        else if(e.keyCode){
           
            if( (e.keyCode >= 48 && e.keyCode <= 57) || (e.keyCode >= 65 && e.keyCode <= 90 ) || (e.keyCode >= 97 && e.keyCode <= 122 )  || e.keyCode == 8 || e.keyCode == 9 ){
                return true;

            }else if(enie == true && (e.keyCode == 209 || e.keyCode == 241)){//excepcion ñ y Ñ 
              return true;
            }
        }
        return false;
    },
    
        validarNit : function (campo){

          var nit    =  document.getElementById(campo);
              nit    =  nit.value;
          var large  =  nit.length;
           
           if(nit == ''){
             return false
           }else{
                for (var i = 0; i < large; i++) {

                     if( !nit[i].match('[0-9]')){

                        if(i < large-2){
                          return false;
                        }else if (i == large-1){
                          
                          return false;
                        }    

                     }else if (i==large-2 && nit[i] != '-') {
                          
                          return false;
                        }          
                }
          }     
          return true;
    },
        
        hora:{
            
            validar:function(texto){
                 var patron =  /^(0?[1-9]|1[0-2]):[0-5][0-9] (a|p|A|P)(m|M)$/;
                 return patron.test(texto);
                
            },
            comparar: function(sHora1, sHora2, igual, formato) { 

                        var arHora1 = sHora1.split(":"); 
                        var arHora2 = sHora2.split(":"); 
                        var arAmfm1,arAmfm2;
                        var amfm1, amfm2;
                        var hh1, mm1, hh2 , mm2;
                        
                        if(formato == 'HH12'){
                            // Obtener horas y minutos (hora 1) 
                                 hh1 = parseInt(arHora1[0],10);
                                 arAmfm1 = arHora1[1].split(" ");
                                 mm1 = parseInt(arAmfm1[0],10); 
                                 amfm1 = arAmfm1[1];

                                // Obtener horas y minutos (hora 2) 
                                 hh2 = parseInt(arHora2[0],10); 
                                 arAmfm2 = arHora2[1].split(" ");
                                 mm2 = parseInt(arAmfm2[0],10); 
                                 amfm2 = arAmfm2[1];
                          
                         
                            
                        }else{
                                // Obtener horas y minutos (hora 1) 
                                 hh1 = parseInt(arHora1[0],10); 
                                 mm1 = parseInt(arHora1[1],10); 

                                // Obtener horas y minutos (hora 2) 
                                 hh2 = parseInt(arHora2[0],10); 
                                 mm2 = parseInt(arHora2[1],10); 
                         }

                        // Comparar 
                        if (hh1<hh2 || (hh1==hh2 && mm1<mm2)) 
                            return false; 
                        else if (hh1>hh2 || (hh1==hh2 && mm1>mm2)) 
                            return true; 
                        else  
                            return igual; 
            } 
            
        }
     
        ,
        
        direccion:{ 
               obtener :function(nombre){
                       
                      var dirParcial = campo.obtenerxNombre(nombre);
                      var direccion  =  dirParcial[0].value +' '+ dirParcial[1].value + " # "
                                        + dirParcial[2].value +' - '+ dirParcial[3].value;
                      var ubicacion = "";
                      var principal = "";
                      var secun1 = "";
                      var secun2 = "";
                      
                        if(dirParcial[0].value != ''){
                            ubicacion = dirParcial[0].value;
                        }
                        
                        if(dirParcial[1].value != ''){
                            principal = ' '+ dirParcial[1].value;
                        }
                        
                        if(dirParcial[2].value != ''){
                            secun1 = " # " + dirParcial[2].value;
                        }
                        
                        if(dirParcial[3].value != ''){
                            secun2 = ' - '+ dirParcial[3].value;
                        }
                         direccion =  ubicacion + principal + secun1+ secun2;           
                        
                      return direccion;
               },
               asignar: function(nombre, valor){
                       //Obtenemos la primera parte de la direccion
                        var cmpsDirecciones = campo.obtenerxNombre(nombre);
                       //Sacamos el tipo de ubicacion que siempre deben ser 3 letras
                       if(valor != '' && valor  != null & valor != undefined){
                                var ubicacion = valor.substr(0,3);
                                //obtenemos el resto de la direccion
                                var valorRestante = valor.substr(3).trim().split("#");
                                //Sacamos la parte principal de la dirección
                                var principal = valorRestante[0].trim();

                                //Sacamos la parte Secundaria de la dirección
                                var secundarios = valorRestante[1].split("-");

                                //asignamos
                                cmpsDirecciones[0].value = ubicacion;
                                cmpsDirecciones[1].value = principal;
                                cmpsDirecciones[2].value = secundarios[0].trim();
                                cmpsDirecciones[3].value = secundarios[1].trim();
                         }
                        
               },
               limpiar : function(nombre){
                   var dirParcial = campo.obtenerxNombre(nombre);
                   
                    for(i=0; i < dirParcial.length; i++){ 
                        dirParcial[i].value ="";
                    }
                    
               },
               validar : function(contendor,obligatorio){
                            var vacio = 0;                           
                            var oblg = obligatorio != undefined? obligatorio : true;
                            
                           $("[data-validacionDireccion-msg]").each(function(index,obj){
                                   if(obj.value == ''){  
                                        vacio++;
                                   }
                           });

                           if (contendor.is("[data-validacionDireccion-msg]") && vacio > 0){   
                               
                               if(oblg == false && vacio == 4){
                                  
                                   return true;                                    
                               }else{
                                  return false;
                               } 
                           }

                           return true;
                }
        },
        
        lista:{
            /*
    * Función que  no permite permite los espacios en el campo
        * @parm id, id del Select
        * @parm url, es el servlet al que apunta la lista
        * @parm parametros, los datos que se le envian al servlet
    * @retrun jqXhr, un objeto Promise() de jQuery
    */
            crear:function(id,url,parametros){
                        var opciones = '<option></option>';
                        var $campo = $('#'+id);
                        var xhr;
                     
                        var construir = function(data){

                               var array = data;

                               for(var i=0; i<array.length ; i++){

                                    opciones +="<option value='"+data[i].codigo+"'>"+data[i].descripcion+"</option>";
                               }  
                           $campo.html(opciones);

                       };
                if($campo.length > 0){
                   xhr =  $.post( url, parametros, construir);
                }

               return xhr;
            },
            construir:function(data){
                        var opciones = '<option></option>';
                        
                        var array = data;

                        for(var i=0; i<array.length ; i++){

                             opciones +="<option value='"+data[i].codigo+"'>"+data[i].descripcion+"</option>";
                        }  
                        return opciones ;

                       
            },
            
            asignar: function(id,value){
                $("#"+id).val(""+value+"");
            }
            
        }

 }


/*hace pntlla refencia a pantalla */
var pntlla = {
            
            /*
             * Función que maneja la vista de los botones en pantalla
             * @retrun vacio
             */     
            iniciar : function() {

                         var codigo = document.getElementById('codigo').value;

                        if(codigo != ""){
                            elmto.ocultar('guardar');
                            elmto.mostrar('modificar');
                            

                        }else{
                            elmto.mostrar('guardar');
                            elmto.ocultar('modificar');
                        }
            },
                        iniciar2:function(){
                            
                             var codigo = document.getElementById('codigo').value;

                        if(codigo != ""){
                            elmto.ocultar('guardar');
                            elmto.mostrar('modificar');
                                                
                            

                        }else{
                            elmto.mostrar('guardar');
                            elmto.ocultar('modificar');
                                                
                        }
                        }

};



var checkSeleccionados = function (vector){
    var tam = 1;
    var i = 0;
    var cadena = ""; 
    var coma;
    var vctr = document.getElementsByName(vector);
    var count=0;
    if(vctr.length){
        tam = vctr.length;
        for(i=0; i < tam; i++){
            if(vctr[i].checked == true){
                coma = count===0&&tam>0?'':','; 
                cadena =   cadena +coma +vctr[i].value   ;
                count++
            }
        }
    }
   return '['+cadena+']';
};

var JsonDinamico =  function (base, ns_string, valor) {
                var parts = ns_string.split('.');
                var pl;
                var i;
                var val = valor || null ;
                pl = parts.length;
                for (i = 0; i < pl; i++) {                    
                    if (typeof base[parts[i]] == 'undefined') {
                        base[parts[i]] = val;
                    }
                    base = base[parts[i]];
                }
                return base;

            };




