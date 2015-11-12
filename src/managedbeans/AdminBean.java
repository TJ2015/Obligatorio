package managedbeans;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import dominio.datatypes.DataAdministrador;
import exceptions.NoExisteElUsuario;
import exceptions.YaExisteElUsuario;
import negocio.interfases.IControladorUsuario;

@ManagedBean
@SessionScoped
public class AdminBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@EJB
	IControladorUsuario cusu;

	private String nick;
	private String password;
	private String error;
	private boolean logueado = false;
	private String imagen;
	private String nickReg;
	private String emailReg;

	public AdminBean() {
		super();
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public boolean isLogueado() {
		return logueado;
	}

	public void setLogueado(boolean logueado) {
		this.logueado = logueado;
	}
	
	public String getNickReg() {
		return nickReg;
	}

	public void setNickReg(String nickReg) {
		this.nickReg = nickReg;
	}

	public String getEmailReg() {
		return emailReg;
	}

	public void setEmailReg(String emailReg) {
		this.emailReg = emailReg;
	}

	public void login() throws IOException {
		try {
			DataAdministrador da = cusu.loginAdmin(nick, password);
			if (da != null) {
				HttpSession session = SesionBean.getSession();
				session.setAttribute("nickname", nick);
				logueado = true;
				error = null;
				String hash = util.Gravatar.md5Hex(da.getEmail());
				setImagen("http://www.gravatar.com/avatar/" + hash + "?d=retro");
				FacesContext.getCurrentInstance().getExternalContext().redirect("admin.xhtml");
			} else {
				error = "Nombre de usuario o contraseña incorrecta.";
			}
		} catch (NoExisteElUsuario e) {
			error = "Nombre de usuario o contraseña incorrecta.";
		}
	}

	public void logout() throws IOException {
		HttpSession session = SesionBean.getSession();
		session.invalidate();
		FacesContext.getCurrentInstance().getExternalContext().redirect("login_admin.xhtml");
	}

	public int cantUsu() {
		return 44;
	}

	public int cantCat() {
		return 0;
	}

	public int cantProd() {
		return 0;
	}

	public int cantMembresia() {
		return 0;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	
	public void registrarAdministrador() {
		try {
			cusu.registrarAdmin(nickReg, "", emailReg);
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("admin.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (YaExisteElUsuario e) {
			error = "Ya existe un administrador con ese nickname/email";
		}
	}

}
