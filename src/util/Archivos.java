package util;

import java.io.File;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public final class Archivos 
{
	
	public static void crearPDF() 
	{
		try {
			File pdf = new File("sol.pdf");
			FileOutputStream archivo = new FileOutputStream(pdf);
			Document documento = new Document();
			PdfWriter.getInstance(documento, archivo);
			documento.open();
			documento.add(new Paragraph("Hola Mundo!"));
			documento.close();
			pdf.createNewFile();
		} catch (Exception e) {
			System.out.println("Error");
		}
		
	}
}
