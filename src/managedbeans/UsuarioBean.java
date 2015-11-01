package managedbeans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import dominio.datatypes.DataAV;
import dominio.datatypes.DataUsuario;
import negocio.interfases.IControladorUsuario;

@ManagedBean
@SessionScoped
public class UsuarioBean implements Serializable 
{
	private static final long serialVersionUID = 1L;

	@EJB
	IControladorUsuario cusu;

	private String nombre;
	private String apellido;
	private String nick;
	private String password;
	private String email;
	private Date fechaNacimiento;
	private List<DataAV> AVs = new ArrayList<>();
	private DataUsuario dusu;

	private boolean logueado = false;

	public UsuarioBean() {

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public List<DataAV> getAVs() {
		return AVs;
	}

	public void setAVs(List<DataAV> aVs) {
		AVs = aVs;
	}

	public DataUsuario getDusu() {
		return dusu;
	}

	public void setDusu(DataUsuario dusu) {
		this.dusu = dusu;
	}

	public boolean isLogueado() {
		return logueado;
	}

	public void setLogueado(boolean logueado) {
		this.logueado = logueado;
	}

	public void login() throws IOException {
		try {
			if (cusu.login(nick, password)) {
				logueado = true;
				HttpSession session = SesionBean.getSession();
				session.setAttribute("nickname", nick);

				FacesContext.getCurrentInstance().getExternalContext().dispatch("/av_crear.xhtml");
			} else {
				FacesContext.getCurrentInstance().getExternalContext().dispatch("/error.xhtml");
			}
		}

		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void logout() {

		HttpSession session = SesionBean.getSession();
		session.invalidate();

		// logueado=false;

	}

	public void registroUsuario() {
		try {
			if (cusu.registrarUsuario(nombre, apellido, nick, password, email, fechaNacimiento)) {
				logueado = true;

				// FacesContext.getCurrentInstance().getExternalContext().dispatch("/datosSesionUsuario.xhtml");

				// dusu=cusu.mostraListaAv(nick);
				// AVs=dusu.getAVs();

				// FacesContext.getCurrentInstance().getExternalContext().dispatch("/login.xhtml");
				FacesContext.getCurrentInstance().getExternalContext().dispatch("/index.xhtml");

			} else {
				FacesContext.getCurrentInstance().getExternalContext().dispatch("/error.xhtml");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void mostrarListaAV() {
		try {
			AVs = cusu.mostrarListaAv(nick);

			FacesContext.getCurrentInstance().getExternalContext().dispatch("/verListaAV.xhtml");

		} catch (IOException e) {
			try {
				FacesContext.getCurrentInstance().getExternalContext().dispatch("/error.xhtml");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
