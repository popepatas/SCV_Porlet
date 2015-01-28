/**
 *
 * @author jmrincon
 */

$(document).on("ready", inicio);

function inicio(){
    $("#fechaInicial,#fechaFinal").kendoDatePicker({format: "dd/MM/yyyy"});

    $("#excelDagma").on("click",generarExcelDagma);
}

var generarExcelDagma = function(){
  if(validar()){
        var fechaInicial = campo.obtener("fechaInicial");
        var fechaFinal = campo.obtener("fechaFinal");
        var url="/SCV_Portlet/excelDagma.jsp?xls=si&fi="+fechaInicial+"&ff="+fechaFinal;
        document.location.href = url;
    }
};

var validar = function(){
  if($("#fechaInicial").val() === ""){
      alert("Debe escoger una fecha inicial.");
      return false;
  }

  if($("#fechaFinal").val() === ""){
    alert("Debe escoger una fecha final.");  
    return false;
  }
  return true;
};

