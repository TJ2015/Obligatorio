package managedbeans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import dominio.AV;
import dominio.datatypes.DataAV;
import dominio.datatypes.DataUsuario;
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
	private  List<DataAV> AVs=new ArrayList<>();
	private DataUsuario dusu;
	
	private boolean logueado = false;
	
	// para manejar las sesiones MARIANELA
	  private final HttpServletRequest httpServletRequest;
	  private final FacesContext faceContext;
	  private FacesMessage facesMessage;
	
	
	
	
	public UsuarioBean() {
		// SE AGREGÓ PARA MANEJAR LAS SESIONES
		 	faceContext=FacesContext.getCurrentInstance();
	        httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
	      
	        if(httpServletRequest.getSession().getAttribute("sessionUsuario")!=null)
	        {
	            nick=httpServletRequest.getSession().getAttribute("sessionUsuario").toString();
	        }
	        
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




	public void login() {
		try {
			if( cusu.login(nick, password) ) {
				logueado = true;
				
				//lo nuevo para sesiones
				 httpServletRequest.getSession().setAttribute("sessionUsuario", nick);
		            facesMessage=new FacesMessage(FacesMessage.SEVERITY_INFO, "Acceso Correcto", null);
		            faceContext.addMessage(null, facesMessage);
		         // fin de lo nuevo en sesiones 
				
				
				FacesContext.getCurrentInstance().getExternalContext().dispatch("/av_crear.xhtml");			
				
			} else {
				
				//lo nuevo para la sesión
				 facesMessage=new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario o contraseña incorrecto", null);
		            faceContext.addMessage(null, facesMessage);
		          // fin de lo nuevo  para la sesión		
				
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
				//dusu=cusu.mostraListaAv(nick);
				 //AVs=dusu.getAVs();
				
				FacesContext.getCurrentInstance().getExternalContext().dispatch("/index.xhtml");
				
			} else {
				FacesContext.getCurrentInstance().getExternalContext().dispatch("/error.xhtml");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void mostrarListaAV(){
		try {
			AVs=cusu.mostrarListaAv(nick);
			 
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
	
	public void logout() throws IOException
    {
        httpServletRequest.getSession().removeAttribute("sessionUsuario");
        facesMessage=new FacesMessage(FacesMessage.SEVERITY_INFO, "Sesión finalizada correctamente", null);
        faceContext.addMessage(null, facesMessage);
        FacesContext.getCurrentInstance().getExternalContext().dispatch("/login.xhtml");
        
    }
	
	
	
	
	
}
