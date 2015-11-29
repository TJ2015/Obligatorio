package servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import dominio.datatypes.DataUsuario;
import negocio.interfases.IControladorUsuario;
import util.TablasPDF;


@WebServlet("/ServletAdminPDF")
public class ServletAdminPDF extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@EJB
	private IControladorUsuario cUsuario;
	
    public ServletAdminPDF() {
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			String nickUsuario = (String)session.getAttribute("nickname");
			String nickAdmin = (String)session.getAttribute("admin");
			String tipoReporte = (String)session.getAttribute("tipoReporteAdmin");
			if (nickAdmin.equals(nickUsuario)) {
				switch (tipoReporte) {
				case "listaUsuarios":
					crearReporteListausuarios(request, response);
					break;
				}
			}else{
				response.sendRedirect("login.xhtml");
			}
			
		} catch (Exception e) {
			response.sendRedirect("login.xhtml");
		}
	}
	
	@SuppressWarnings({ "rawtypes" })
	private void crearReporteListausuarios(HttpServletRequest request, HttpServletResponse response){
		try{
			
			int cantidadColumna = 6;
			
        	Document document = new Document(); 
        	
        	ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);
        	
            document.open();
            
            List<DataUsuario> lDataUsuario = cUsuario.getUsuarios();
            
            PdfPTable tabla = new PdfPTable(cantidadColumna);                
            if (lDataUsuario != null) 
            {
                tabla.addCell(TablasPDF.crearTitulo("Lista de Usuarios Registrados", cantidadColumna));
				if (lDataUsuario.size() > 0) {
					tabla.addCell(TablasPDF.crearCabecera("Nick Usuario"));
					tabla.addCell(TablasPDF.crearCabecera("Email"));
					tabla.addCell(TablasPDF.crearCabecera("Nombre"));
					tabla.addCell(TablasPDF.crearCabecera("Apellido"));
					tabla.addCell(TablasPDF.crearCabecera("Fecha Nacimiento"));
					tabla.addCell(TablasPDF.crearCabecera("Estatus"));
					
					for (Iterator iterator = lDataUsuario.iterator(); iterator.hasNext();) {
						DataUsuario dataUsuario = (DataUsuario) iterator.next();
						tabla.addCell(TablasPDF.crearCelda(dataUsuario.getNick()));
						tabla.addCell(TablasPDF.crearCelda(dataUsuario.getEmail()));
						tabla.addCell(TablasPDF.crearCelda(dataUsuario.getNombre()));
						tabla.addCell(TablasPDF.crearCelda(dataUsuario.getApellido()));
						tabla.addCell(TablasPDF.crearCelda(dataUsuario.getFechaNacimiento().toString()));
						tabla.addCell(TablasPDF.crearCelda(dataUsuario.isMembresia() ? "PREIMUN" : "FREE"));						
					}
					
					tabla.addCell(TablasPDF.crearCabecera("TOTAL", 5));
					tabla.addCell(TablasPDF.crearCabecera(Integer.toString(lDataUsuario.size())));
				}
				else{
	            	tabla.addCell(TablasPDF.crearError("No hay datos en el reporte", cantidadColumna));
				}
			}
            else{
            	tabla.addCell(TablasPDF.crearError("No se genero correctamente el Reporte", cantidadColumna));
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
            
        }catch(Exception e)
        {
        	e.printStackTrace();
            System.err.println("Ocurrio un error al crear el archivo");
        }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
