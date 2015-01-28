package control.ExportarServlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author jmrincon
 */
@WebServlet(name = "exportarExcel", urlPatterns = {"/exportarExcel"})
public class exportarExcel extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");      
        OutputStream out = response.getOutputStream();
        try {
            JSONObject gridobj = new JSONObject((String)request.getParameter("griddata"));
            JSONArray rowsArr = gridobj.getJSONArray("rows");
            JSONArray columnsArr = gridobj.getJSONArray("cols");
            ArrayList<String> shownFieldsList = new ArrayList<String>();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Workbook wb = new XSSFWorkbook();
            CellStyle headerStyle = wb.createCellStyle();
            Font font = wb.createFont();
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
            headerStyle.setFont(font);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            CreationHelper createHelper = wb.getCreationHelper();
            CellStyle dateCellStyle = wb.createCellStyle();
            dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy HH:mm"));
            Sheet sh = wb.createSheet();
            Row headerRow = sh.createRow(0);
            headerRow.setHeight((short)(headerRow.getHeight()*2));
            int colIndex = 0;
            for(int n = 0; n < columnsArr.length(); n++) {
		JSONObject o = columnsArr.getJSONObject(n);
                if (!o.getString("title").equals("Opciones")) {
                    if(!o.has("hidden") || o.getBoolean("hidden")==Boolean.FALSE) {
                    shownFieldsList.add(o.getString("field"));
                    Cell cell = headerRow.createCell(colIndex);
                    cell.setCellValue(o.getString("title"));
                    cell.setCellStyle(headerStyle);
                    if(o.has("width") && !"null".equals(o.getString("width"))) {
                        sh.setColumnWidth(colIndex,32*Integer.parseInt(o.getString("width")));
                    } else {
                    	sh.setColumnWidth(colIndex,256*15);
                    }
                    colIndex++;
		}
                }
		/*if(!o.has("hidden") || o.getBoolean("hidden")==Boolean.FALSE) {
                    shownFieldsList.add(o.getString("field"));
                    Cell cell = headerRow.createCell(colIndex);
                    cell.setCellValue(o.getString("title"));
                    cell.setCellStyle(headerStyle);
                    if(o.has("width") && !"null".equals(o.getString("width"))) {
                        sh.setColumnWidth(colIndex,32*Integer.parseInt(o.getString("width")));
                    } else {
                    	sh.setColumnWidth(colIndex,256*15);
                    }
                    colIndex++;
		}*/
            }
            Pattern datePattern = Pattern.compile("[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]T[0-9][0-9]:[0-9][0-9]:[0-9][0-9].[0-9][0-9][0-9]Z");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            int rowIndex = 1;
            for(int n=0; n<rowsArr.length(); n++) {
                JSONObject rowObj = rowsArr.getJSONObject(n);
                Row row = sh.createRow(rowIndex++);
                colIndex = 0;
                for(String field : shownFieldsList) {
                    Cell cell = row.createCell(colIndex++);
                    if(rowObj.has(field)) {
                        Object obj = rowObj.get(field);
                        if(String.class.isInstance(obj)) {
                            if(datePattern.matcher((String)obj).find()) {
                                cell.setCellValue(dateFormat.parse(((String)obj).replace("T", " ").substring(0,"yyyy-MM-dd HH:mm:ss".length())));
                                cell.setCellStyle(dateCellStyle);
                            } else {
                                cell.setCellValue((String)obj);
                            }
                        } else if(Number.class.isInstance(obj)) {
                            cell.setCellValue(((Number)obj).doubleValue());
                        } else if(Date.class.isInstance(obj)) {
                            cell.setCellValue((Date)obj);
                            cell.setCellStyle(dateCellStyle);
                        }
                    }
                }
            }
            wb.write(baos);
            
            String nombreReporte = (String)request.getParameter("nombreReporte");
            String filename = nombreReporte + ".xlsx";
            response.setHeader("Content-Disposition", "attachment; filename=\""+filename+"\"");
            response.setContentType(URLConnection.guessContentTypeFromName(filename));
            response.setDateHeader("Last-Modified", new Date().getTime());
            response.setContentLength((int) baos.size());
            out.write(baos.toByteArray());
        } catch(Exception e) {
         throw new ServletException(e);
     } finally {
         out.close();
     }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
