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
import dominio.datatypes.DataNota;
import dominio.datatypes.DataNotificacion;
import dominio.datatypes.DataUsuario;
import exceptions.NoExisteElAV;
import exceptions.NombreDeAVInvalido;
import negocio.interfases.IControladorAV;
import negocio.interfases.IControladorUsuario;
import util.Url;

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
	private String textNotificacion;
	private String textNota;
	private long idNota;
	private List<DataNotificacion> listNotificacion=new ArrayList<>();
	private List<DataNota> listNotas=new ArrayList<>();
	private List<DataUsuario> usus = new ArrayList<>();
	public AvBean() {

	}

	
	public List<DataNotificacion> getListNotificacion() {
		return listNotificacion;
	}


	public void setListNotificacion(List<DataNotificacion> listNotificacion) {
		this.listNotificacion = listNotificacion;
	}


	public String getTextNotificacion() {
		return textNotificacion;
	}


	public void setTextNotificacion(String textNotificacion) {
		this.textNotificacion = textNotificacion;
	}


	public List<DataNota> getListNotas() {
		return listNotas;
	}


	public void setListNotas(List<DataNota> listNotas) {
		this.listNotas = listNotas;
	}


	public String getTextNota() {
		return textNota;
	}


	public void setTextNota(String textNota) {
		this.textNota = textNota;
	}


	public long getIdNota() {
		return idNota;
	}


	public void setIdNota(long idNota) {
		this.idNota = idNota;
	}


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

	public void agregarAV() throws Exception {
		HttpSession session = SesionBean.getSession();
		String nick = (String) session.getAttribute("nickname");
		if (!(cAV.existeAVusuario(nombreAV, usuarioCreador))) {
			try {
				idAV = cAV.altaAV(nombreAV, nick);
			} catch (NombreDeAVInvalido e1) {
				e1.printStackTrace();
			}
			session.setAttribute("idAV", idAV);
			session.setAttribute("AVs", cUsu.mostrarListaAv(nick));

			try {
				session.setAttribute("dAV", cAV.traerAV(idAV));

			} catch (NoExisteElAV e1) {
				e1.printStackTrace();
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
		try {
			HttpSession session = SesionBean.getSession();
			long idAV= (long) session.getAttribute("idAV");
			String nickname= (String) session.getAttribute("nickname");
			cAV.eliminarAV(idAV);
			Url.redireccionarURL("index");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	

	public void cancelarAccion() {
		Url.redireccionarURL("index");
	}
	
	
	public void mostrarListaUsuariosCompartidos() throws NoExisteElAV 
	{
		try {
			HttpSession session = SesionBean.getSession();
			long idAV= (long) session.getAttribute("idAV");
			usus=cAV.traerAV(idAV).getUsuariosCompartidos();
			//session.setAttribute("usus",.toArray());
			Url.redireccionarURL("usuarios_compartidos");

			
		} catch (Exception e) {
			Url.redireccionarURL("error");
			e.printStackTrace();
		}
			
	}


	public void crearNota() throws Exception  {
		HttpSession session = SesionBean.getSession();
		String nickname= (String) session.getAttribute("nickname");
		long idAV1= (long) session.getAttribute("idAV");
		cAV.crearNota(textNota, nickname, idAV1);
		listNotas= cAV.getNotas(idAV1);
		textNota=null;
	}
	public List<DataNota> mostrarNotas() throws Exception {
		HttpSession session = SesionBean.getSession();
		long idAV1= (long) session.getAttribute("idAV");
		listNotas= cAV.getNotas(idAV1);
		return listNotas;

	}
	
	public void eliminarNota() throws Exception  {
		HttpSession session = SesionBean.getSession();
		long idAV1= (long) session.getAttribute("idAV");
		cAV.eliminarNota(idAV1, idNota);
	}
	public void crearNotificacion() throws Exception  {
		HttpSession session = SesionBean.getSession();
		long idAV1= (long) session.getAttribute("idAV");
		cAV.crearNotificacion(textNotificacion, idAV1);
		listNotificacion= cAV.getNotificaciones(idAV1);
		textNotificacion=null;
	}
	public List<DataNotificacion> mostrarNotificaciones() throws Exception {
		HttpSession session = SesionBean.getSession();
		long idAV1= (long) session.getAttribute("idAV");
		listNotificacion= cAV.getNotificaciones(idAV1);
		return listNotificacion;

	}
	public void eliminarNotificacion() throws Exception  {
		HttpSession session = SesionBean.getSession();
		long idAV1= (long) session.getAttribute("idAV");
		cAV.crearNotificacion(textNotificacion, idAV1);
	}
}
