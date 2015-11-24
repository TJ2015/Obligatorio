package servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import dominio.datatypes.DataUsuario;
import negocio.interfases.IControladorUsuario;
import util.DBUtil;


@WebServlet("/ServletImagen")
public class ServletImagen extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	@EJB
	IControladorUsuario cUsu;
	
    public ServletImagen() {
        super();
    }
    
    private HttpServletResponse mostrarImagen(String nick, HttpServletResponse response){
		DataUsuario dataUsuario = cUsu.getUsuario(nick);
		if (dataUsuario != null) {
			try {
				byte[] img = null;
				img = dataUsuario.getImagen();
				if( (img == null) || (img.length == 0)){
					InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("resources/default_profile.png");
					img = IOUtils.toByteArray(in);
				}
				response.reset();
				response.setContentType("image/jpeg");
				response.setHeader("Content-Disposition", "inline; filename=imagen.jpg");
				response.setHeader("Cache-control", "public");
				OutputStream o = response.getOutputStream();
				o.write(img);
				o.flush();
				o.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
    	return response;
    }
    
    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	
    	String nick = (String) req.getParameter("nick");
    	
    	if(nick != null) {    		
    		resp = mostrarImagen(nick, resp);
    	}

	}

}
