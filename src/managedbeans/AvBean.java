package managedbeans;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import negocio.IControladorAV;

@ManagedBean
@SessionScoped
public class AvBean implements Serializable{
	@EJB
	IControladorAV cAV;
	
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

	public void agregarAV(){
	boolean creo=false;
		
		if (!(cAV.existeAVusuario(nombreAV, usuarioCreador))){
			creo=cAV.altaAV(nombreAV, usuarioCreador);
			this.mensaje = "AV creado";
		}
		else
			this.mensaje = "Ya tienes un AV con ese nombre";
	}
	
	
	

}
