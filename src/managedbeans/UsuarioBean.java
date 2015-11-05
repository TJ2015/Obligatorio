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

	public void login() throws IOException 
	{
		try {
			DataUsuario dataUsuario = cusu.login(nick, password);
			if (dataUsuario != null) {
				logueado = true;
				HttpSession session = SesionBean.getSession();
				session.setAttribute("nickname", nick);
				session.setAttribute("dataUsuario", dataUsuario);
				FacesContext.getCurrentInstance().getExternalContext().dispatch("/usuario_sapo.xhtml");
			} else {
				FacesContext.getCurrentInstance().getExternalContext().dispatch("/error.xhtml");
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void logout() throws IOException 
	{
		HttpSession session = SesionBean.getSession();
		logueado=false;
		FacesContext.getCurrentInstance().getExternalContext().dispatch("/index.xhtml");
		session.invalidate();
	}

	public void registroUsuario() {
		try {
			DataUsuario dataUsuario = cusu.registrarUsuario(nombre, apellido, nick, password, email, fechaNacimiento);
			if (dataUsuario != null) {
				logueado = true;
				FacesContext.getCurrentInstance().getExternalContext().dispatch("/index.xhtml");
			} 
			else {
				FacesContext.getCurrentInstance().getExternalContext().dispatch("/error.xhtml");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void mostrarListaAV() 
	{
		try {
			HttpSession session = SesionBean.getSession();
			session.setAttribute("AVs", cusu.mostrarListaAv(nick));
			AVs = cusu.mostrarListaAv(nick);

			FacesContext.getCurrentInstance().getExternalContext().dispatch("/verListaAV.xhtml");

		} catch (IOException e) {
			try {
				FacesContext.getCurrentInstance().getExternalContext().dispatch("/error.xhtml");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	public boolean existeUsuarioLogeado()
	{
		boolean existeUsuario = false;
		try {
			HttpSession session = SesionBean.getSession();
			DataUsuario dataUsuario = (DataUsuario)session.getAttribute("dataUsuario");
			existeUsuario = dataUsuario != null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return existeUsuario;
	}

}
