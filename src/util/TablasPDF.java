package util;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;

public final class TablasPDF {
	
	public static PdfPCell crearCelda(String dato){
		PdfPCell celda = new PdfPCell(new Paragraph(dato, FontFactory.getFont("arial", 8, Font.NORMAL, BaseColor.BLACK))); 
    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
    	celda.setPadding (2.0f);
    	celda.setPaddingBottom(4.0f);
    	celda.setPaddingTop(4.0f);
    	return celda;
	}
	
	public static PdfPCell crearCeldaDato(String dato){
		PdfPCell celda = new PdfPCell(new Paragraph(dato, FontFactory.getFont("arial", 10, Font.NORMAL, BaseColor.BLACK))); 
    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
    	celda.setPadding (2.0f);
    	celda.setPaddingRight(15.0f);
    	return celda;
	}
	
	public static PdfPCell crearCabecera(String dato){
		PdfPCell celda = new PdfPCell(new Paragraph(dato, FontFactory.getFont("arial", 10, Font.NORMAL, BaseColor.RED))); 
    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
    	celda.setPadding (8.0f);
    	celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
    	return celda;
	}
	
	public static PdfPCell crearCabecera(String dato, int tamanioFila){
		PdfPCell celda = new PdfPCell(new Paragraph(dato, FontFactory.getFont("arial", 10, Font.NORMAL, BaseColor.RED))); 
    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
    	celda.setPadding (8.0f);
    	celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
    	celda.setColspan(tamanioFila);
    	return celda;
	}
	
	public static PdfPCell crearTitulo(String dato, int cantidadColumna){
		PdfPCell celda = new PdfPCell(new Paragraph(dato, FontFactory.getFont("arial", 14, Font.BOLD, BaseColor.BLUE))); 
    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
    	celda.setPadding (12.0f);
    	celda.setColspan(cantidadColumna);
    	return celda;
	}
	
	public static PdfPCell crearError(String dato, int cantidadColumna){
		PdfPCell celda = new PdfPCell(new Paragraph(dato, FontFactory.getFont("arial", 14, Font.BOLD, BaseColor.RED))); 
    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
    	celda.setPadding (12.0f);
    	celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
    	celda.setColspan(cantidadColumna);
    	return celda;
	}
   

}
