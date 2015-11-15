package servlets;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dominio.datatypes.DataUsuario;
import util.Imagenes;


@WebServlet("/ServletImagen")
public class ServletImagen extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public ServletImagen() {
        super();
    }
    // SI EL PARAMETRO TIPO =  1 ES DESARROLLADOR
    // SI EL PARAMETRO TIPO =  0 ES CLIENTE
    // SI ES PARAMETRO TIPO = -1 ES UN JUEGO
    
    private HttpServletResponse mostrarImagen(HttpServletRequest request, HttpServletResponse response){
    	try {
			HttpSession session = request.getSession();
			if (session != null) {
				DataUsuario dataUsuario = (DataUsuario) session.getAttribute("dataUsuario");
				if (dataUsuario != null) {
					byte[] img = null;
					img = dataUsuario.getImagen();
					if(img != null){
						response.reset();
						response.setContentType("image/jpeg");
						response.setHeader("Content-Disposition", "inline; filename=imagen.jpg");
						response.setHeader("Cache-control", "public");
						OutputStream o = response.getOutputStream();
						o.write(img);
						o.flush();
						o.close();
					}
				}
			}
		} catch (Exception e) {
			
		}
    	return response;
    }
    
    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	resp = mostrarImagen(req, resp);
	}
	@Override
	protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp = mostrarImagen(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp = mostrarImagen(req, resp);
	}
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp = mostrarImagen(req, resp);
	}
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp = mostrarImagen(req, resp);
	}
	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp = mostrarImagen(req, resp);
	}
	@Override
	protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp = mostrarImagen(req, resp);
	}
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp = mostrarImagen(req, resp);
	}
	
	

}
