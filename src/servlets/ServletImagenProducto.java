package servlets;

import java.io.IOException;
import java.io.OutputStream;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dominio.datatypes.DataProducto;
import exceptions.NoExisteElAV;
import exceptions.NoExisteElProducto;
import negocio.interfases.IControladorInventario;
import negocio.interfases.IControladorUsuario;


@WebServlet("/ServletImagen")
public class ServletImagenProducto extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	@EJB
	IControladorUsuario cUsu;
	@EJB
	IControladorInventario cInv;
	
    public ServletImagenProducto() {
        super();
    }
    // SI EL PARAMETRO TIPO =  1 ES DESARROLLADOR
    // SI EL PARAMETRO TIPO =  0 ES CLIENTE
    // SI ES PARAMETRO TIPO = -1 ES UN JUEGO
    
    private HttpServletResponse mostrarImagen(String nombre, long idAV, HttpServletResponse response){
		DataProducto dp = null;
		boolean cancelar = false;
		try {
			dp = cInv.getProducto(nombre, idAV);
		} catch (Exception e1) {
			cancelar = true;
		}
		
		if (dp != null && !cancelar) {
			try {
				byte[] img = null;
				img = dp.getImagen();
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
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
    	return response;
    }
    
    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	
    	String nombre = (String) req.getParameter("nombre");
    	String av = req.getParameter("av");
    	
    	if((nombre != null)&&(av != null)) {    
    		long idAV = Long.valueOf(av);
    		resp = mostrarImagen(nombre, idAV, resp);
    	}

	}

}
