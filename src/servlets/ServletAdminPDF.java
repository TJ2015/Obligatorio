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
import dominio.datatypes.DataVenta;
import negocio.interfases.IControladorUsuario;
import negocio.interfases.IControladorVenta;
import util.TablasPDF;


@WebServlet("/ServletAdminPDF")
public class ServletAdminPDF extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@EJB
	private IControladorUsuario cUsuario;
	
	@EJB
	private IControladorVenta cVenta;
	
    public ServletAdminPDF() {
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
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
				case "listaVentas":
					crearReporteGanancia(request, response);
					break;
				}
			}else{
				response.sendRedirect("login.xhtml");
			}
			
		} catch (Exception e) {
			response.sendRedirect("login.xhtml");
		}
	}
	
	
	private void mostrarPDF(HttpServletRequest request, HttpServletResponse response, ByteArrayOutputStream baos){
		try {
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Pragma", "public");
			response.setContentType("application/pdf");
			response.setContentLength(baos.size());
			
			OutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();
		} catch (Exception e) {
			System.out.println("Error al mostar el PDF");
		}
	}
	
	
	@SuppressWarnings({ "rawtypes" })
	private void crearReporteListausuarios(HttpServletRequest request, HttpServletResponse response){
		try{
        	Document document = new Document(); 
        	ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);
            document.open();
            int cantidadColumna = 6;
            PdfPTable tabla = new PdfPTable(cantidadColumna);  
            tabla.setWidthPercentage(100);
            
            List<DataUsuario> lDataUsuario = cUsuario.getUsuarios();
            
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
					int totalPremium = 0;
					int totalFree = 0;
					String tipoUsuario = null;
					for (Iterator iterator = lDataUsuario.iterator(); iterator.hasNext();) {
						DataUsuario dataUsuario = (DataUsuario) iterator.next();
						tabla.addCell(TablasPDF.crearCelda(dataUsuario.getNick()));
						tabla.addCell(TablasPDF.crearCelda(dataUsuario.getEmail()));
						tabla.addCell(TablasPDF.crearCelda(dataUsuario.getNombre()));
						tabla.addCell(TablasPDF.crearCelda(dataUsuario.getApellido()));
						tabla.addCell(TablasPDF.crearCelda(dataUsuario.getFechaNacimiento().toString()));
						if (dataUsuario.isMembresia()) {
							totalPremium++;
							tipoUsuario = "PREIMUM";
						}
						else{
							totalFree++;
							tipoUsuario = "FREE";
						}
						tabla.addCell(TablasPDF.crearCelda(tipoUsuario));						
					}

					tabla.addCell(TablasPDF.crearCabecera("TOTAL DE USUARIO FREE", 5));
					tabla.addCell(TablasPDF.crearCabecera(Integer.toString(totalFree)));
					tabla.addCell(TablasPDF.crearCabecera("TOTAL DE USUARIOS PREMIUM", 5));
					tabla.addCell(TablasPDF.crearCabecera(Integer.toString(totalPremium)));
					tabla.addCell(TablasPDF.crearCabecera("TOTAL DE USUARIOS", 5));
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
            
            mostrarPDF(request, response, baos);
            
        }catch(Exception e)
        {
        	e.printStackTrace();
            System.err.println("Ocurrio un error al crear el archivo");
        }
	}

	@SuppressWarnings("rawtypes")
	private void crearReporteGanancia(HttpServletRequest request, HttpServletResponse response)
	{
		try {
			Document document = new Document(); 
        	ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);
            document.open();
            int cantidadColumna = 6;
            PdfPTable tabla = new PdfPTable(cantidadColumna);
            tabla.setWidthPercentage(100);
            
			List<DataVenta> lVentas = cVenta.obtenerVentas();
            if (lVentas != null) 
            {
                tabla.addCell(TablasPDF.crearTitulo("Ganancias por Membresias", cantidadColumna));
				if (lVentas.size() > 0) {
					tabla.addCell(TablasPDF.crearCabecera("Nick"));
					tabla.addCell(TablasPDF.crearCabecera("Nombre"));
					tabla.addCell(TablasPDF.crearCabecera("Apellido"));
					tabla.addCell(TablasPDF.crearCabecera("Correo"));
					tabla.addCell(TablasPDF.crearCabecera("Fecha"));
					tabla.addCell(TablasPDF.crearCabecera("Monto"));
					int montoTotal = 0;
					for (Iterator iterator = lVentas.iterator(); iterator.hasNext();) {
						DataVenta dataVenta = (DataVenta) iterator.next();
						tabla.addCell(TablasPDF.crearCelda(dataVenta.getDataUsuario().getNick()));
						tabla.addCell(TablasPDF.crearCelda(dataVenta.getDataUsuario().getNombre()));
						tabla.addCell(TablasPDF.crearCelda(dataVenta.getDataUsuario().getApellido()));
						tabla.addCell(TablasPDF.crearCelda(dataVenta.getDataUsuario().getEmail()));
						tabla.addCell(TablasPDF.crearCelda(dataVenta.getFecha().toString()));
						tabla.addCell(TablasPDF.crearCelda("$ " + dataVenta.getValor()));
						montoTotal += dataVenta.getValor();
					}
					tabla.addCell(TablasPDF.crearCabecera("CANTIDAD DE VENTAS", 5));
					tabla.addCell(TablasPDF.crearCabecera(Integer.toString(lVentas.size())));
					tabla.addCell(TablasPDF.crearCabecera("TOTAL EN VENTAS", 5));
					tabla.addCell(TablasPDF.crearCabecera("$ " + Integer.toString(montoTotal)));
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
            
            mostrarPDF(request, response, baos);
			
		} catch (Exception e) {
			System.out.println("Error al generar el reporte de ventas");
		}
	}

}
