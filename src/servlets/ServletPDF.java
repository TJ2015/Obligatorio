package servlets;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import dominio.datatypes.DataReporte;
import dominio.datatypes.DataReportes;


@WebServlet("/ServletPDF")
public class ServletPDF extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final static int CANTIDAD_COLUMNA = 2;
	
    public ServletPDF() { }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	/**************************************************************/

	@SuppressWarnings("rawtypes")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
        try{
        	Document document = new Document(); 
        	
        	ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);
        	
            document.open();
            
            HttpSession session = request.getSession();
            DataReportes dataReportes = (DataReportes)session.getAttribute("reporteGenerado");
            
            PdfPTable tabla = new PdfPTable(CANTIDAD_COLUMNA);                
            if (dataReportes != null && dataReportes.getTipoReporte() != null && dataReportes.getListaReporte() != null) {
                tabla.addCell(crearTitulo(dataReportes.getTipoReporte().getDescripcion()));
				if (dataReportes.getListaReporte().size() > 0) {
					tabla.addCell(crearCabecera("Nombre Producto"));
					tabla.addCell(crearCabecera(dataReportes.getTipoReporte().getNombre()));
					for (Iterator iReporte = dataReportes.getInterator(); iReporte.hasNext();) {
						DataReporte dataReporte = (DataReporte) iReporte.next();
						tabla.addCell(crearCelda(dataReporte.getNombre()));
						tabla.addCell(crearCeldaDato(dataReporte.getValor()));
					}
					tabla.addCell(crearCabecera("TOTAL"));
					tabla.addCell(crearCabecera(dataReportes.getTotal()));
				}
				else{
	            	tabla.addCell(crearError("No hay datos en el reporte"));
				}
			}
            else{
            	tabla.addCell(crearError("No se genero correctamente el Reporte"));
            }
                  
            document.add(tabla);
            document.close();
            
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setContentType("application/pdf");
            response.setContentLength(baos.size());
            
            OutputStream os = response.getOutputStream();
            baos.writeTo(os);
            os.flush();
            os.close();
            
            //Desktop.getDesktop().open(document);
            //Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler reporte.pdf");
            
        }catch(Exception e)
        {
        	e.printStackTrace();
            System.err.println("Ocurrio un error al crear el archivo");
        }
	}
	
	@SuppressWarnings("rawtypes")
	public static void GenerarReportePDF(DataReportes dataReportes)
	{
        try{
        	Document document = new Document(); 
            PdfWriter.getInstance(document, new FileOutputStream("reporte.pdf"));
            document.open();
            
            PdfPTable tabla = new PdfPTable(CANTIDAD_COLUMNA);                
            if (dataReportes != null && dataReportes.getListaReporte() != null) {
                tabla.addCell(crearTitulo(dataReportes.getTipoReporte().getDescripcion()));
				if (dataReportes.getListaReporte().size() > 0) {
					tabla.addCell(crearCabecera("Nombre Producto"));
					tabla.addCell(crearCabecera(dataReportes.getTipoReporte().getNombre()));
					for (Iterator iReporte = dataReportes.getInterator(); iReporte.hasNext();) {
						DataReporte dataReporte = (DataReporte) iReporte.next();
						tabla.addCell(crearCelda(dataReporte.getNombre()));
						tabla.addCell(crearCeldaDato(dataReporte.getValor()));
					}
					tabla.addCell(crearCabecera("TOTAL"));
					tabla.addCell(crearCabecera(dataReportes.getTotal()));
				}
				else{
	            	tabla.addCell(crearError("No hay datos en el reporte"));
				}
			}
            else{
            	tabla.addCell(crearError("No se genero correctamente el Reporte"));
            }
                  
            document.add(tabla);
            document.close();
            
            //Desktop.getDesktop().open(document);
            //Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler reporte.pdf");
            
        }catch(Exception e)
        {
        	e.printStackTrace();
            System.err.println("Ocurrio un error al crear el archivo");
        }
    }
	
	
	private static PdfPCell crearCelda(String dato){
		PdfPCell celda = new PdfPCell(new Paragraph(dato, FontFactory.getFont("arial", 12, Font.NORMAL, BaseColor.BLACK))); 
    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
    	celda.setPadding (5.0f);
    	return celda;
	}
	
	private static PdfPCell crearCeldaDato(String dato){
		PdfPCell celda = new PdfPCell(new Paragraph(dato, FontFactory.getFont("arial", 12, Font.NORMAL, BaseColor.BLACK))); 
    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
    	celda.setPadding (5.0f);
    	celda.setPaddingRight(15.0f);
    	return celda;
	}
	
	private static PdfPCell crearCabecera(String dato){
		PdfPCell celda = new PdfPCell(new Paragraph(dato, FontFactory.getFont("arial", 14, Font.NORMAL, BaseColor.RED))); 
    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
    	celda.setPadding (8.0f);
    	celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
    	return celda;
	}
	
	private static PdfPCell crearTitulo(String dato){
		PdfPCell celda = new PdfPCell(new Paragraph(dato, FontFactory.getFont("arial", 20, Font.BOLD, BaseColor.BLUE))); 
    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
    	celda.setPadding (12.0f);
    	celda.setColspan(CANTIDAD_COLUMNA);
    	return celda;
	}
	
	private static PdfPCell crearError(String dato){
		PdfPCell celda = new PdfPCell(new Paragraph(dato, FontFactory.getFont("arial", 20, Font.BOLD, BaseColor.RED))); 
    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
    	celda.setPadding (12.0f);
    	celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
    	celda.setColspan(CANTIDAD_COLUMNA);
    	return celda;
	}
   
	
}
