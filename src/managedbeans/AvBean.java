package managedbeans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import dominio.AV;
import dominio.datatypes.DataAV;
import dominio.datatypes.DataUsuario;
import exceptions.NoExisteElAV;
import exceptions.NombreDeAVInvalido;
import negocio.interfases.IControladorAV;
import negocio.interfases.IControladorUsuario;

@ManagedBean
@SessionScoped
public class AvBean implements Serializable {
	
	@EJB
	IControladorAV cAV;
	@EJB
	IControladorUsuario cUsu;

	private long idAV;
	private String nickname;
	private String nombreAV;
	private String mensaje;
	private String usuarioCreador;
	private int cantUsuComp;
	private List<DataUsuario> usus=new ArrayList<>();
	
	public int getCantUsuComp() {
		return cantUsuComp;
	}

	public void setCantUsuComp(int cantUsuComp) {
		this.cantUsuComp = cantUsuComp;
	}

	public List<DataUsuario> getUsus() {
		return usus;
	}

	public void setUsus(List<DataUsuario> usus) {
		this.usus = usus;
	}

	

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

	public void agregarAV() {
		HttpSession session = SesionBean.getSession();
		String nick = (String) session.getAttribute("nickname");
		if (!(cAV.existeAVusuario(nombreAV, usuarioCreador))) {
			try {
				idAV = cAV.altaAV(nombreAV, nick);
				
			} catch (NombreDeAVInvalido e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			session.setAttribute("idAV", idAV);
			session.setAttribute("nombreAV", nick + "_" + nombreAV);
			session.setAttribute("AVs", cUsu.mostrarListaAv(nick));
			
			
			try {
				FacesContext.getCurrentInstance().getExternalContext().dispatch("/mostrarAV.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void compartirAV() throws NoExisteElAV {
		HttpSession session = SesionBean.getSession();
		long idAV= (long) session.getAttribute("idAV");
		if (cUsu.existeUsuarioNick(nickname)) {
			cAV.compartirAV(idAV, nickname);
			cantUsuComp++;
		}

	}

	public void eliminarAV() throws Exception {
		HttpSession session = SesionBean.getSession();
		long idAV= (long) session.getAttribute("idAV");
		String nickname= (String) session.getAttribute("nickname");
		
			cAV.eliminarAV(idAV);
			try {
				FacesContext.getCurrentInstance().getExternalContext().dispatch("/index.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		
			
				System.out.println(nickname + " " + idAV);
			
		}
		
	

	public void cancelarAccion() {

		try {
			FacesContext.getCurrentInstance().getExternalContext().dispatch("/index.xhtml");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void mostrarListaUsuariosCompartidos() throws NoExisteElAV 
	{
		try {
			HttpSession session = SesionBean.getSession();
			long idAV= (long) session.getAttribute("idAV");
			usus=cAV.traerAV(idAV).getUsuariosCompartidos();
			//session.setAttribute("usus",.toArray());
			FacesContext.getCurrentInstance().getExternalContext().dispatch("/usuarios_compartidos.xhtml");

			
		} catch (IOException e) {
			try {
				FacesContext.getCurrentInstance().getExternalContext().dispatch("/error.xhtml");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
			
	}

	
	

}
