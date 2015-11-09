package managedbeans;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;

import dominio.AV;
import dominio.datatypes.DataAV;
import negocio.interfases.IControladorAV;
import negocio.interfases.IControladorUsuario;
import util.Url;

@ManagedBean
@SessionScoped
public class AvBean implements Serializable {

	private static final long serialVersionUID = 1L;
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

	public void agregarAV() {

		try {
			HttpSession session = SesionBean.getSession();
			String nick = (String) session.getAttribute("nickname");
			if (!(cAV.existeAVusuario(nombreAV, usuarioCreador))) {
				idAV = cAV.altaAV(nombreAV, nick);
				session.setAttribute("idAV", idAV);
				Url.redireccionarURL("categoria_crear");
				// FacesContext.getCurrentInstance().getExternalContext().dispatch("/categoria_crear.xhtml");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void compartirAV() {
		try {
			if (cUsu.existeUsuarioNick(nickname)) {
				cAV.compartirAV(idAV, nickname);
				Url.redireccionarURL("index");
			} else {
				Url.redireccionarURL("error");
			}
		} catch (Exception e) {
			Url.redireccionarURL("error");
			e.printStackTrace();
		}
	}

	public long traerIdAV() {
		AV av = cAV.traerAvPorNombre(nombreAV);
		idAV = av.getIdAV();
		return idAV;

	}

	public void eliminarAV() {
		try {
			cAV.eliminarAV(idAV);
			Url.redireccionarURL("index");
		} catch (Exception e) {
			Url.redireccionarURL("error");
			e.printStackTrace();
		}
	}

	public void cancelarAccion() {
		try {
			Url.redireccionarURL("index");
		} catch (Exception e) {
			Url.redireccionarURL("error");
			e.printStackTrace();
		}
	}

}
