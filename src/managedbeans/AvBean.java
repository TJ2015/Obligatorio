package managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import negocio.IControladorAV;
import negocio.IControladorUsuario;
import dominio.datatypes.*;


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

		public void setIdAv(long idAV) {
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
			this.mensaje = "AV creado";
		}
		else
			this.mensaje = "Ya tienes un AV con ese nombre";
	}
	
	public void compartirAV(){
		if(cUsu.existeUsuarioNick(nickname)){
			cAV.compartirAV(idAV, nickname);
		}
		
	}

}
