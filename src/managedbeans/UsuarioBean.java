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

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import dominio.datatypes.DataAV;
import dominio.datatypes.DataUsuario;
import exceptions.NoExisteElAV;
import negocio.interfases.IControladorAV;
import negocio.interfases.IControladorUsuario;

@ManagedBean
@SessionScoped
public class UsuarioBean implements Serializable 
{
	private static final long serialVersionUID = 1L;

	@EJB
	IControladorUsuario cusu;
	@EJB
	IControladorAV cav;

	private String nombre;
	private String apellido;
	private String nick;
	private String password;
	private String email;
	private Date fechaNacimiento;
	private List<DataAV> AVs = new ArrayList<>();
	private DataUsuario dusu;
	private boolean logueado;
		
	public boolean isLogueado() {
		return logueado;
	}

	public void setLogueado(boolean logueado) {
		this.logueado = logueado;
	}
	
	private UploadedFile file;
	
	private StreamedContent imagen;
	
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

	public void login() throws IOException 
	{
		try {
			DataUsuario dataUsuario = cusu.login(nick, password);
			
			if (dataUsuario != null) {
				logueado=true;
				HttpSession session = SesionBean.getSession();
				session.setAttribute("nickname", nick);
				session.setAttribute("dataUsuario", dataUsuario);
				session.setAttribute("AVs", cusu.mostrarListaAv(nick));
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
		
	
	public void registroUsuario() 
	{
		try {
			dusu = cusu.registrarUsuario(nombre, apellido, nick, password, email, fechaNacimiento, file);
			if (dusu != null) {
				imagen = new DefaultStreamedContent(dusu.getImagen(), "image/jpg");
				logueado=true;
				HttpSession session = SesionBean.getSession();
				session.setAttribute("nickname", nick);
				session.setAttribute("dataUsuario", dusu);
				session.setAttribute("AVs", cusu.mostrarListaAv(nick));
				FacesContext.getCurrentInstance().getExternalContext().dispatch("/usuario_sapo.xhtml");
			} 
			else {
				FacesContext.getCurrentInstance().getExternalContext().dispatch("/error.xhtml");
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void limpiarBean()
	{
		this.file = null;
		this.imagen = null;
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

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public StreamedContent  getImagen() {
		return imagen;
	}

	public void setImagen(StreamedContent  imagen) {
		this.imagen = imagen;
	}
	
	public void verAV(long idAV) {
		HttpSession session = SesionBean.getSession();
		try {
			session.setAttribute("dAV", cav.traerAV(idAV));
			session.setAttribute("idAV", idAV);
		} catch (NoExisteElAV e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
