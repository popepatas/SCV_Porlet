
inicioInsertarResultadoVisita = function(codigo)
{
    var code = codigo;
    
   var enviarFormularioResultado = function()
    {
        var validador = $("#div_registrar_resultado_visita").data("kendoValidator");
        var status     = $(".status");

                 if (validador.validate()) {  
                    if(confirm("¿Esta seguro que desea Guardar el resultado?")){
                        guardarRegistroResultado();
                    }

                } 
    };

    urlSubir = servlet.cargaArchivos.subir+"?opcion=1&codigo="+codigo;
    urlEliminar = "/SCV_Portlet/EliminarArchivoServlet";
    
    $("#files").kendoUpload({
        async: {
            saveUrl: urlSubir,
            removeUrl: urlEliminar,
            autoUpload: true
        }
    });

    obtenerParametrosResultado = function()
    {

          parametros = {                                                        
                                tecnicos   : campo.obtener("tecnicos"),
                                resultado : campo.obtener("resultado"), 
                                codigoResultado : code,
                                chkResultado : campo.check("chkResultado")

                          };

        return parametros;
    };
    
    var mostrarResultadoVisita = function(){
        var url = servlet.consultar.resultadoVisita;
        $.post( url, { codigoVisita: codigo}, function( data ) {
            campo.asignar("tecnicos",data[0].tecnicoVisito);
            campo.asignar("resultado",data[0].resultado);
            
            var reprogramar = false;
            if(data[0].reprogramar === 'SI'){
                reprogramar = true;
            }
            
            campo.asignar("chkResultado",reprogramar);
        });
    }
    
    
    guardarRegistroResultado = function()
    {
        parametros = obtenerParametrosResultado();
        
        url = servlet.insertar.resultadoVisitas;
        result = $.post( url, parametros);
        
        result.done(function(){
            
            alert( "El resultado de la visita fue registrado exitosamente." );
            modalBox.cerrar("modalBox");
            generarGrillaVisitas();
            
        });

        result.fail(function() {
            alert( "Lo sentimos un error inesperado ha ocurrido." );
        });

        
    };
    
    urlTec = servlet.consultar.tecnicos;
    campo.lista.crear("tecnicos",urlTec,{opcion:1});
    
    $("#div_registrar_resultado_visita").kendoValidator({
        
        messages:{
            required:"Ingrese un valor para este campo."
        }
    });
    $("#guadarResultado").on("click", enviarFormularioResultado);
    mostrarResultadoVisita();
};