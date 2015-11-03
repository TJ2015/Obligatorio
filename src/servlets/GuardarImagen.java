package servlets;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;

/**
 * Servlet implementation class GuardarImagen
 */
@WebServlet("/GuardarImagen")
public class GuardarImagen extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GuardarImagen() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		File directorio = new File("C://sqlite//jar//001"); 
		directorio.mkdir();
		
		System.out.println("entra al servlet de ServletUploadFile");
		String url="C://sqlite//jar//001"; // UBICACION DONDE SE GUARDAN LOS ARCHIVOS
		//BOLEANO QUE INDICA SI LO QUE SE ENVIA ES EN FORMA DE MUTIPART O SEA EN VARIAS PARTES
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		
		ServletRequestContext src = new ServletRequestContext(request);

		if (isMultipart) {
			System.out.println("ES MULTIPART");
			DiskFileItemFactory factory = new DiskFileItemFactory(); //SE CREA LA FABRICA
		    factory.setSizeThreshold(1024); //IDENTIFICA LA TASA DE TRANSFERENCIA - FLUJO DE TRNSFERENCIA
		    factory.setRepository(new File(url)); // EL REPOSITORIO INDICA DONDE SE VA A ALMACENAR EL ARCHIVO EN NUESTRO CASO ES LA VARIABLE URL
			
		    //SE CREA EL SERVLET FILE UPLOAD CON LA FABRICA ANTES CREADA
		    ServletFileUpload upload = new ServletFileUpload(factory);
		    
		    try {
		        //SE CREA LA LISTA DE ITEMS 
				List<FileItem> items = upload.parseRequest(src);
				//SE RECORRE LA LISTA
		        for(FileItem item : items){
		        	System.out.println(url+item.getName());
		        	File file = new File(url, item.getName()); //SE CREA UN FILE CON LA URL + EL NOMBRE DEL ARCHIVO
		        	item.write(file); //SE ESCRIBE EL ARCHIVO FILE
		        }
		        
		    }catch (Exception e) {
				e.printStackTrace();
			}
		}
		else{
			System.out.println("NO ES MULTIPLIPART");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
