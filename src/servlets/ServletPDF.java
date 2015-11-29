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

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import dominio.datatypes.DataReporte;
import dominio.datatypes.DataReportes;
import util.TablasPDF;


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
                tabla.addCell(TablasPDF.crearTitulo(dataReportes.getTipoReporte().getDescripcion(), CANTIDAD_COLUMNA));
				if (dataReportes.getListaReporte().size() > 0) {
					tabla.addCell(TablasPDF.crearCabecera("Nombre Producto"));
					tabla.addCell(TablasPDF.crearCabecera(dataReportes.getTipoReporte().getNombre()));
					for (Iterator iReporte = dataReportes.getInterator(); iReporte.hasNext();) {
						DataReporte dataReporte = (DataReporte) iReporte.next();
						tabla.addCell(TablasPDF.crearCelda(dataReporte.getNombre()));
						tabla.addCell(TablasPDF.crearCeldaDato(dataReporte.getValor()));
					}
					tabla.addCell(TablasPDF.crearCabecera("TOTAL"));
					tabla.addCell(TablasPDF.crearCabecera(dataReportes.getTotal()));
				}
				else{
	            	tabla.addCell(TablasPDF.crearError("No hay datos en el reporte", CANTIDAD_COLUMNA));
				}
			}
            else{
            	tabla.addCell(TablasPDF.crearError("No se genero correctamente el Reporte", CANTIDAD_COLUMNA));
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
                tabla.addCell(TablasPDF.crearTitulo(dataReportes.getTipoReporte().getDescripcion(), CANTIDAD_COLUMNA));
				if (dataReportes.getListaReporte().size() > 0) {
					tabla.addCell(TablasPDF.crearCabecera("Nombre Producto"));
					tabla.addCell(TablasPDF.crearCabecera(dataReportes.getTipoReporte().getNombre()));
					for (Iterator iReporte = dataReportes.getInterator(); iReporte.hasNext();) {
						DataReporte dataReporte = (DataReporte) iReporte.next();
						tabla.addCell(TablasPDF.crearCelda(dataReporte.getNombre()));
						tabla.addCell(TablasPDF.crearCeldaDato(dataReporte.getValor()));
					}
					tabla.addCell(TablasPDF.crearCabecera("TOTAL"));
					tabla.addCell(TablasPDF.crearCabecera(dataReportes.getTotal()));
				}
				else{
	            	tabla.addCell(TablasPDF.crearError("No hay datos en el reporte", CANTIDAD_COLUMNA));
				}
			}
            else{
            	tabla.addCell(TablasPDF.crearError("No se genero correctamente el Reporte", CANTIDAD_COLUMNA));
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
	
	
	
	
}
