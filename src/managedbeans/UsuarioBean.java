package managedbeans;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import negocio.IControladorUsuario;


@ManagedBean
@SessionScoped
public class UsuarioBean implements Serializable {
	
	@EJB
	IControladorUsuario cusu;
	
	private String nombre;
	private String apellido;
	private String nick;
	private String password;
	private String email;
	private Date fechaNacimiento;
	
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
	public boolean isLogueado() {
		return logueado;
	}
	public void setLogueado(boolean logueado) {
		this.logueado = logueado;
	}
	
	public void login() {
		try {
			if( cusu.login(nick, password) ) {
				logueado = true;
				FacesContext.getCurrentInstance().getExternalContext().dispatch("/bienvenida.xhtml");
			} else {
				FacesContext.getCurrentInstance().getExternalContext().dispatch("/error.xhtml");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void registroUsuario() {
		try {
			if( cusu.registrarUsuario(nombre, apellido, nick, password, email, fechaNacimiento) ) {
				logueado = true;
				FacesContext.getCurrentInstance().getExternalContext().dispatch("/bienvenida.xhtml");
				
			} else {
				FacesContext.getCurrentInstance().getExternalContext().dispatch("/error.xhtml");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
