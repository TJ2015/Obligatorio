package managedbeans;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import dominio.datatypes.DataAV;
import negocio.IControladorAV;
import negocio.IControladorUsuario;


@ManagedBean
@SessionScoped
public class AvBean implements Serializable{
	@EJB
	IControladorAV cAV;
	@EJB
	IControladorUsuario cUsu;
	
	private long idAV;
	private String nickname;
	private String nombreAV;
	private String mensaje; 
	private String usuarioCreador;

	public String getNombreAV() {
		return nombreAV;
	}

	public void setNombreAV(String nombreAV) {
		this.nombreAV = nombreAV;
	}
	
	public AvBean() {
		
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getUsuarioCreador() {
		return usuarioCreador;
	}

	public void setUsuarioCreador(String usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}
	  public long getIdAV() {
			return idAV;
		}

		public void setIdAV(long idAV) {
			this.idAV = idAV;
		}

		public String getNick() {
			return nickname;
		}

		public void setNick(String nickname) {
			this.nickname = nickname;
		}

		public List<DataAV> mostrarAV() {
		    	return null;
		    }
		
	public void agregarAV(){
		boolean creo=false;
	
		if (!(cAV.existeAVusuario(nombreAV, usuarioCreador))){
			creo=cAV.altaAV(nombreAV, usuarioCreador);
	 
			try {
				FacesContext.getCurrentInstance().getExternalContext().dispatch("/index.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public void compartirAV(){
		if(cUsu.existeUsuarioNick(nickname)){
			cAV.compartirAV(idAV, nickname);
			try {
				FacesContext.getCurrentInstance().getExternalContext().dispatch("/index.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
