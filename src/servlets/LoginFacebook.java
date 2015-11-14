package servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dominio.datatypes.DataUsuario;
import managedbeans.FacebookBean;
import negocio.interfases.IControladorUsuario;
import util.Url;


@WebServlet("/LoginFacebook")
public class LoginFacebook extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	IControladorUsuario controlUsuario;
       
    public LoginFacebook() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String code = request.getParameter("code");
		if (code == null || code.equals("")) {
			throw new RuntimeException("ERROR: No se obtuvo el parametro 'Code' desde Facebook.");
		}
		FacebookBean facebook = new FacebookBean();
		String accessToken = facebook.getAccessToken(code);

		facebook.setAccessToken(accessToken);
		String datos = facebook.obtenerDatosUsuario();
		
		DataUsuario dataUsuario = controlUsuario.loginSocial(datos, "facebook");
		try {
			if (dataUsuario != null) 
			{
				HttpSession session = request.getSession();
				session.setAttribute("dataUsuario", dataUsuario);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Url.redireccionarURL(dataUsuario != null ? "index" : "login");
		//response.sendRedirect(dataUsuario != null ? "index.xhtml" : "login.xhtml");
		//(HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false)
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}
	
	
	/*ServletOutputStream out = response.getOutputStream();
	out.println("<h1>Facebook Login using Java</h1>");
	out.println("<h2>Application Main Menu</h2>");
	out.println("<h3>Bienvenido " + usuario.first_name + " " + usuario.last_name + "</h3>");
	out.println("<h4>Su correo es: " + usuario.email + "</h4>");
	out.println("<h5>Su Id es: " + usuario.id + "</h5>");*/
}
