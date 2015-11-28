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
import dominio.datatypes.DataCategoria;
import dominio.datatypes.DataNota;
import dominio.datatypes.DataNotificacion;
import dominio.datatypes.DataProducto;
import dominio.datatypes.DataProductoAComprar;
import dominio.datatypes.DataUsuario;
import exceptions.NoExisteElAV;
import exceptions.NoExisteElProducto;
import exceptions.NoExisteElProductoAComprar;
import exceptions.NombreDeAVInvalido;
import exceptions.YaExisteElProductoAComprar;
import negocio.interfases.IControladorAV;
import negocio.interfases.IControladorInventario;
import negocio.interfases.IControladorUsuario;
import util.Url;

@ManagedBean
@SessionScoped
public class AvBean implements Serializable {
	
	@EJB
	IControladorAV cAV;
	@EJB
	IControladorUsuario cUsu;
	@EJB
	IControladorInventario cInv;

	private long idAV;
	private String nickname;
	private String nombreAV;
	private String mensaje;
	private String usuarioCreador;
	private int cantUsuComp;
	private String textNotificacion;
	private String textNota;
	private long idNota;
	private String nombreProducto;
	private int cantidad;
	private boolean reemplazar;
	private List<DataNotificacion> listNotificacionNoLeida=new ArrayList<>();
	private String categoriaEliminar;
	private boolean eliminarCategoria;
	private DataProductoAComprar dPrdAComp;
	private List<DataProductoAComprar> listCompra;
	private List<DataNotificacion> listNotificacion=new ArrayList<>();
	private List<DataNota> listNotas=new ArrayList<>();
	private List<DataUsuario> usus = new ArrayList<>();
	private List<DataCategoria> cats = new ArrayList<>();
	
	public AvBean() {

	}
	
	public List<DataCategoria> getCats() {
		return cats;
	}

	public void setCats(List<DataCategoria> cats) {
		this.cats = cats;
	}

	public String getCategoriaEliminar() {
		return categoriaEliminar;
	}

	public void setCategoriaEliminar(String categoriaEliminar) {
		this.categoriaEliminar = categoriaEliminar;
	}

	public boolean isEliminarCategoria() {
		return eliminarCategoria;
	}

	public void setEliminarCategoria(boolean eliminarCategoria) {
		this.eliminarCategoria = eliminarCategoria;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public boolean isReemplazar() {
		return reemplazar;
	}

	public void setReemplazar(boolean reemplazar) {
		this.reemplazar = reemplazar;
	}

	public List<DataNotificacion> getListNotificacionNoLeida() {
		return listNotificacionNoLeida;
	}


	public void setListNotificacionNoLeida(List<DataNotificacion> listNotificacionNoLeida) {
		this.listNotificacionNoLeida = listNotificacionNoLeida;
	}

	public DataProductoAComprar getdPrdAComp() {
		return dPrdAComp;
	}


	public void setdPrdAComp(DataProductoAComprar dPrdAComp) {
		this.dPrdAComp = dPrdAComp;
	}


	public List<DataProductoAComprar> getListCompra() {
		return listCompra;
	}


	public void setListCompra(List<DataProductoAComprar> listCompra) {
		this.listCompra = listCompra;
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
			nombreAV=null;
		}
	}

	public void compartirAV() throws NoExisteElAV {
		HttpSession session = SesionBean.getSession();
		long idAV= (long) session.getAttribute("idAV");
		
		if (cUsu.existeUsuarioNick(nickname)) {
			cantUsuComp=cUsu.getUsuario(nickname).getAVsCompar().size();
			cAV.compartirAV(idAV, nickname);
			cantUsuComp++;
		}

	}
	public void descompartirAV(String nickname) throws NoExisteElAV {
		HttpSession session = SesionBean.getSession();
		long idAV= (long) session.getAttribute("idAV");
		cAV.descompartirAV(idAV, nickname);

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
	public void marcarNotificacionComoLeida(long idNoti ) throws Exception{
		HttpSession session = SesionBean.getSession();
		long idAV= (long) session.getAttribute("idAV");
		cAV.getNotificaciones(idAV);
		cAV.marcarNotificacionComoLeida(idNoti, idAV);
	}
	public List<DataNotificacion> mostrarNotificacionesNoLeidas() throws Exception {
		HttpSession session = SesionBean.getSession();
		long idAV1= (long) session.getAttribute("idAV");
		listNotificacionNoLeida= cAV.listaNotificacionesNoLeidas(idAV1);
		return listNotificacion;

	}
	public void eliminarNotificacion() throws Exception  {
		HttpSession session = SesionBean.getSession();
		long idAV1= (long) session.getAttribute("idAV");
		cAV.crearNotificacion(textNotificacion, idAV1);
	}
	public List<DataProductoAComprar> mostrarListaDeCompra() throws NoExisteElAV {
		HttpSession session = SesionBean.getSession();
		long idAV1= (long) session.getAttribute("idAV");
		listCompra=cInv.getListaDeCompra(idAV1);
		return listCompra;
	}
	public void cargarProductoAComprar(long idProdComp) {
		try {
			HttpSession session = SesionBean.getSession();
			long idAV = (long) session.getAttribute("idAV");
			DataProductoAComprar dProdC=cInv.getProductoAComprar(idAV, idProdComp);
			this.setdPrdAComp(dProdC);
			Url.redireccionarURL("ver_productoAComprar");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void agregarEnListaDeCompra( String producto,boolean reemplazar) throws NoExisteElAV, NoExisteElProducto, YaExisteElProductoAComprar{
		HttpSession session = SesionBean.getSession();
		long idAV1= (long) session.getAttribute("idAV");
		cInv.agregarEnListaDeCompra(idAV1, producto, cantidad, reemplazar);
		
	}
	public void eliminarEnListaDeCompra() throws NoExisteElAV, NoExisteElProducto, YaExisteElProductoAComprar{
		HttpSession session = SesionBean.getSession();
		long idAV1= (long) session.getAttribute("idAV");
		 DataProducto dprod=cInv.getProducto(nombreProducto, idAV1);
		try {
			cInv.eliminarProductoDeListaDeCompra(idAV1, dprod.getIdProducto());
		} catch (NoExisteElProductoAComprar e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public String pruebaNom(String prue){
		nombreProducto = prue;
		return nombreProducto;
	}
	
	public void prepararParaEliminar(String nombre) {
		categoriaEliminar = nombre;
		eliminarCategoria = true;
	}
	
	public void eliminarCategoria() {
		HttpSession session = SesionBean.getSession();
		idAV = (long) session.getAttribute("idAV");
		try {
			cInv.eliminarCategoria(categoriaEliminar, idAV);
			mostrarListaCategoria();
			categoriaEliminar = null;
			eliminarCategoria = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cancelarEliminar() {
		categoriaEliminar = null;
		eliminarCategoria = false;
	}
	
	public List<DataCategoria> mostrarListaCategoria() {
		try {
			HttpSession session = SesionBean.getSession();
			idAV = (long) session.getAttribute("idAV");
			cats = cInv.mostrarListaCategoria(idAV);
		} catch (Exception e) {
			// TODO 505
			e.printStackTrace();
		}
		return cats;
	}
	public void productoComprado(String nomProd) {
		HttpSession session = SesionBean.getSession();
		long idAV1= (long) session.getAttribute("idAV");
		 DataProducto dprod=null;
		try {
			dprod = cInv.getProducto(nomProd, idAV1);
		} catch (NoExisteElAV | NoExisteElProducto e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			try {
				cInv.productoComprado(idAV1, dprod.getIdProducto());
			} catch (NoExisteElAV e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NoExisteElProductoAComprar e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
