package managedbeans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.model.UploadedFile;

import dominio.datatypes.DataAV;
import dominio.datatypes.DataAlerta;
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
import negocio.interfases.IControladorAlgoritmos;
import negocio.interfases.IControladorInventario;
import negocio.interfases.IControladorUsuario;
import util.Mensajeria;
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
	
	@EJB
	private IControladorAlgoritmos cAlgoritmos;

	private long idAV;
	private String nickname;
	private String nombreAV;
	private String mensaje;
	private String usuarioCreador;
	private List<String> cantUsuComp;
	private String textNotificacion;
	private String textNota;
	private long idNota;
	private String nombreProducto;
	private String nombreUsuComp;
	private int cantidad;
	private boolean reemplazar;
	private String categoriaEliminar;
	private boolean eliminarCategoria;
	private DataProductoAComprar dPrdAComp;
	private List<DataProductoAComprar> listCompra;
	private List<DataNota> listNotas = new ArrayList<>();
	private List<DataUsuario> usus = new ArrayList<>();
	private List<DataCategoria> catsGene = new ArrayList<>();
	private List<DataCategoria> cats = new ArrayList<>();
	private String errorAV;
	private long avEliminar = 0;
	private boolean eliminarAV = false;
	private long prodCompraEliminar;
	private boolean eliminarCompra=false;
	private boolean eliminarUsuComp=false;
	private DataProducto dprodGen;
	private UploadedFile newFile;
	private String nombreCat;
	private boolean cortar;
	private long idAVDestino;

	private String alertaProd;
	private String alertaCond;
	private String alertaVal;

	private String estilo = "skin-blue";

	public AvBean() {

	}
	
	public String getAlertaProd() {
		return alertaProd;
	}

	public void setAlertaProd(String alertaProd) {
		this.alertaProd = alertaProd;
	}

	public String getAlertaCond() {
		return alertaCond;
	}

	public void setAlertaCond(String alertaCond) {
		this.alertaCond = alertaCond;
	}

	public String getAlertaVal() {
		return alertaVal;
	}

	public void setAlertaVal(String alertaVal) {
		this.alertaVal = alertaVal;
	}

	public long getIdAVDestino() {
		return idAVDestino;
	}

	public void setIdAVDestino(long idAVDestino) {
		this.idAVDestino = idAVDestino;
	}

	public boolean getCortar() {
		return cortar;
	}

	public void setCortar(boolean cortar) {
		this.cortar = cortar;
	}

	public String getNombreCat() {
		return nombreCat;
	}

	public void setNombreCat(String nombreCat) {
		this.nombreCat = nombreCat;
	}

	public boolean isEliminarUsuComp() {
		return eliminarUsuComp;
	}

	public void setEliminarUsuComp(boolean eliminarUsuComp) {
		this.eliminarUsuComp = eliminarUsuComp;
	}

	public String getNombreUsuComp() {
		return nombreUsuComp;
	}

	public void setNombreUsuComp(String nombreUsuComp) {
		this.nombreUsuComp = nombreUsuComp;
	}

	public long getProdCompraEliminar() {
		return prodCompraEliminar;
	}

	public void setProdCompraEliminar(long prodCompraEliminar) {
		this.prodCompraEliminar = prodCompraEliminar;
	}

	public boolean isEliminarCompra() {
		return eliminarCompra;
	}

	public void setEliminarCompra(boolean eliminarCompra) {
		this.eliminarCompra = eliminarCompra;
	}

	public UploadedFile getNewFile() {
		return newFile;
	}

	public void setNewFile(UploadedFile newFile) {
		this.newFile = newFile;
	}

	public DataProducto getDprodGen() {
		return dprodGen;
	}

	public void setDprodGen(DataProducto dprodGen) {
		this.dprodGen = dprodGen;
	}

	public List<DataCategoria> getCatsGene() {
		return catsGene;
	}

	public void setCatsGene(List<DataCategoria> catsGene) {
		this.catsGene = catsGene;
	}

	public String getErrorAV() {
		return errorAV;
	}

	public void setErrorAV(String errorAV) {
		this.errorAV = errorAV;
	}

	public long getAvEliminar() {
		return avEliminar;
	}

	public void setAvEliminar(long avEliminar) {
		this.avEliminar = avEliminar;
	}

	public boolean isEliminarAV() {
		return eliminarAV;
	}

	public void setEliminarAV(boolean eliminarAV) {
		this.eliminarAV = eliminarAV;
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

	public List<String> getCantUsuComp() {
		return cantUsuComp;
	}

	public void setCantUsuComp(List<String> cantUsuComp) {
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

		boolean miembro = false;
		String email = "correo";
		if (!(cAV.existeAVusuario(nombreAV, usuarioCreador))) {
			miembro = cUsu.getUsuario(nick).isMembresia();
			email = ((DataUsuario) session.getAttribute("dataUsuario")).getEmail();
			System.out.println(email);
			int cantAV = cantAvPorUsuario();
			try {
				if ((cantAV < 2) || (miembro)) {
					idAV = cAV.altaAV(nombreAV, nick);
					if (cantAV == 1)
						cAV.crearNotificacion(
								"Ya has creado tu segundo Almacen Virtual! No podrás crear más mientras seas un usuario gratuito. Compra la membresía de SAPo y podrás crear todos los que quieras!",
								idAV);
				} else if ((cantAV > 2) || (!miembro)) {
					Url.redireccionarURL("paypal");
					Mensajeria enviar = new Mensajeria();
					enviar.enviarCorreo(email, "SAPo - Límite de cuenta",
							"Aviso de límite de cuenta. Usted ya cuenta con 2 AV en el sistema o perdió su membresía.<br> Lo invitamos a comprar la misma y disfrutar de AV ilimitados");
				} else {
					if (miembro) {
						idAV = cAV.altaAV(nombreAV, nick);
					} else {
						Url.redireccionarURL("paypal");
						Mensajeria enviar = new Mensajeria();
						enviar.enviarCorreo(email, "SAPo - Límite de cuenta",
								"Aviso de límite de cuenta. Usted ya cuenta con 2 AV en el sistema o perdió su membresía.<br> Lo invitamos a comprar la misma y disfrutar de AV ilimitados");
					}
				}

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
			nombreAV = null;
		}
	}

	/* MARIANELA 291115 */

	public int cantAvPorUsuario() {
		HttpSession session = SesionBean.getSession();
		String nick = (String) session.getAttribute("nickname");
		return cUsu.mostrarListaAv(nick).size();

	}
	/* MARIANELA 291115 */

	public void compartirAV() throws NoExisteElAV {
		HttpSession session = SesionBean.getSession();
		long idAV = (long) session.getAttribute("idAV");

		if (cUsu.existeUsuarioNick(nickname)) {
			cAV.compartirAV(idAV, nickname);
		}
	}

	public int cantUsuariosCompartidos() {
		HttpSession session = SesionBean.getSession();
		long idAV = (long) session.getAttribute("idAV");
		try {
			return cAV.traerAV(idAV).getUsuariosCompartidos().size();
		} catch (NoExisteElAV e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;

	}

	public void descompartirAV(String nickname) throws NoExisteElAV {
		HttpSession session = SesionBean.getSession();
		long idAV = (long) session.getAttribute("idAV");
		cAV.descompartirAV(idAV, nickname);
		


	}

	public void eliminarAV() throws Exception {
		try {
			cAV.eliminarAV(avEliminar);
			cancelarEliminarAV();
		} catch (IOException e) {
			e.printStackTrace();
			Url.redireccionarURL("error");
		}
	}

	public void cancelarAccion() {
		Url.redireccionarURL("index");
	}

	public void mostrarListaUsuariosCompartidos() throws NoExisteElAV {
		try {
			HttpSession session = SesionBean.getSession();
			long idAV = (long) session.getAttribute("idAV");
			usus = cAV.traerAV(idAV).getUsuariosCompartidos();
			// session.setAttribute("usus",.toArray());
			Url.redireccionarURL("usuarios_compartidos");

		} catch (Exception e) {
			Url.redireccionarURL("error");
			e.printStackTrace();
		}

	}

	public void crearNota() throws Exception {
		HttpSession session = SesionBean.getSession();
		String nickname = (String) session.getAttribute("nickname");
		long idAV1 = (long) session.getAttribute("idAV");
		cAV.crearNota(textNota, nickname, idAV1);
		listNotas = cAV.getNotas(idAV1);
		textNota = null;
	}

	public List<DataNota> mostrarNotas() throws Exception {
		HttpSession session = SesionBean.getSession();
		long idAV1 = (long) session.getAttribute("idAV");
		listNotas = cAV.getNotas(idAV1);
		return listNotas;

	}

	public void eliminarNota() throws Exception {
		HttpSession session = SesionBean.getSession();
		long idAV1 = (long) session.getAttribute("idAV");
		cAV.eliminarNota(idAV1, idNota);
	}

	public void marcarNotificacionComoLeida(long idNoti) throws Exception {
		HttpSession session = SesionBean.getSession();
		long idAV = (long) session.getAttribute("idAV");
		cAV.getNotificaciones(idAV);
		cAV.marcarNotificacionComoLeida(idNoti, idAV);
	}

	public void eliminarNotificacion() throws Exception {
		HttpSession session = SesionBean.getSession();
		long idAV1 = (long) session.getAttribute("idAV");
		cAV.crearNotificacion(textNotificacion, idAV1);
	}

	public List<DataProductoAComprar> mostrarListaDeCompra() throws NoExisteElAV {
		HttpSession session = SesionBean.getSession();
		long idAV1 = (long) session.getAttribute("idAV");
		listCompra = cInv.getListaDeCompra(idAV1);
		return listCompra;
	}

	public void cargarProductoAComprar(long idProdComp) {
		try {
			HttpSession session = SesionBean.getSession();
			long idAV = (long) session.getAttribute("idAV");
			DataProductoAComprar dProdC = cInv.getProductoAComprar(idAV, idProdComp);
			this.setdPrdAComp(dProdC);
			Url.redireccionarURL("ver_productoAComprar");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void agregarEnListaDeCompra(String producto, boolean reemplazar)
			throws NoExisteElAV, NoExisteElProducto, YaExisteElProductoAComprar {
		HttpSession session = SesionBean.getSession();
		long idAV1 = (long) session.getAttribute("idAV");
		cInv.agregarEnListaDeCompra((String) session.getAttribute("nickname"), idAV1, producto, cantidad, reemplazar);

	}

	public void eliminarEnListaDeCompra() {
		eliminarCompra = false;
		HttpSession session = SesionBean.getSession();
		long idAV1 = (long) session.getAttribute("idAV");
		try {
			cInv.eliminarProductoDeListaDeCompra((String) session.getAttribute("nickname"), idAV1, prodCompraEliminar);
			prodCompraEliminar = 0;
			eliminarCompra = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		prodCompraEliminar = 0;
		eliminarCompra = false;
	}

	public String pruebaNom(String prue) {
		nombreProducto = prue;
		return nombreProducto;
	}
	public String pruebaNomCat(String prue) {
		nombreCat = prue;
		return nombreCat;
	}
	public String levantarNombreUsuComp(String prue) {
		nombreUsuComp = prue;
		return nombreUsuComp;
	}

	public UploadedFile levantarImg(UploadedFile file){
		newFile = file;
		return newFile;
	}
	
	public void prepararParaEliminar(String nombre) {
		categoriaEliminar = nombre;
		eliminarCategoria = true;
	}

	public void eliminarCategoria() {
		HttpSession session = SesionBean.getSession();
		idAV = (long) session.getAttribute("idAV");
		try {
			cInv.eliminarCategoria((String) session.getAttribute("nickname"), categoriaEliminar, idAV);
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
	public void prepararParaEliminarUsuComp(String nombre) {
		nombreUsuComp = nombre;
		eliminarUsuComp = true;
	}

	public void eliminarUsuComp() {
		HttpSession session = SesionBean.getSession();
		idAV = (long) session.getAttribute("idAV");
		try {
			cAV.descompartirAV(idAV, nombreUsuComp);
			mostrarListaUsuariosCompartidos();
			nombreUsuComp = null;
			eliminarUsuComp = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cancelarEliminarUsuComp() {
		nombreUsuComp = null;
		eliminarUsuComp = false;
	}
	public void prepararParaCortarCat(String nombre) {
		nombreCat = nombre;
		cortar = true;
	}

	public void cortarCat() {
		HttpSession session = SesionBean.getSession();
		long idAV = (long) session.getAttribute("idAV");
		String nick = (String) session.getAttribute("nickname");
		DataCategoria cat;
		try {
			cat = cInv.getCategoria(nombreCat, idAV);
			List<DataProducto> productos=cat.getProductos();
			List<String> nombProds=new ArrayList<>();
			for (DataProducto p:productos){
				nombProds.add(p.getNombre());
				
			}
			cInv.moverProductos(nick, nombProds, idAV, idAVDestino);
			nombreCat = null;
			cortar = false;
		} catch (NoExisteElAV e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void cancelarCortarCat() {
		nombreCat = null;
		cortar = false;
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

	public List<DataCategoria> mostrarListaCategoriaGenerica() {

		try {
			catsGene = cInv.mostrarListaCategoria(-1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return catsGene;
	}

	public boolean existeAV() {

		HttpSession session = SesionBean.getSession();
		long idAV = (long) session.getAttribute("idAV");

		if (cAV.existeAV(idAV)) {
			return true;
		} else {
			errorAV = "El AV que estaba viendo ha sido eliminado!";
			Url.redireccionarURL("usuario_sapo");
			return false;
		}
	}

	public void productoComprado(String prod) {
		HttpSession session = SesionBean.getSession();
		long idAV1 = (long) session.getAttribute("idAV");
		DataProducto dprod = null;
		try {
			dprod = cInv.getProducto(prod, idAV1);
		} catch (NoExisteElAV | NoExisteElProducto e1) {
			e1.printStackTrace();
		}
		try {
			try {
				cInv.productoComprado((String) session.getAttribute("nickname"), idAV1, dprod.getIdProducto());
			} catch (NoExisteElAV e) {
				e.printStackTrace();
			}
		} catch (NoExisteElProductoAComprar e) {
			e.printStackTrace();
		}

	}
	
	public List<DataProducto> obtenerProductosConBajoStock(){
		List<DataProducto> lProductos = null;
		try {
			HttpSession session = SesionBean.getSession();
			long idAV = (long) session.getAttribute("idAV");
			lProductos = cAlgoritmos.obtenerProductosConMenosStock(idAV);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lProductos;
	}
	
	
	public void agregarProductoConBajoStock(boolean reemplazar){
		try {
			Map<String, String> value = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			String nombreProducto = value.get("modal_productoSeleccionado:productoSeleccionado");
			int cantidadStock =  Integer.parseInt(value.get("modal_productoSeleccionado:cantidadProducto"));
			HttpSession session = SesionBean.getSession();
			long idAV1= (long) session.getAttribute("idAV");
			cInv.agregarEnListaDeCompra((String)session.getAttribute("nickname"), idAV1, nombreProducto, cantidadStock, reemplazar);
		} catch (Exception e) {
			System.out.println("Error al agreguegar el producto a la lista de compra");
		}
	}
	


	public void cargarDataProductoGenerico(String nombre) {
		try {
			dprodGen = cInv.getProducto(nombre, -1);
		} catch (NoExisteElAV | NoExisteElProducto e) {
			e.printStackTrace();
			Url.redireccionarURL("error");
		}
	}

	public void prepararEliminarAV(long idEliminar) {
		avEliminar = idEliminar;
		eliminarAV = true;
	}

	public void cancelarEliminarAV() {
		avEliminar = 0;
		eliminarAV = false;
	}

	public void prepararEliminarCompra(long prod) {
		prodCompraEliminar = prod;
		eliminarCompra = true;
	}

	public void cancelarEliminarCompra() {
		prodCompraEliminar = 0;
		eliminarCompra = false;
	}

	public List<DataNotificacion> mostrarNotificacionesNoLeidas() {
		HttpSession session = SesionBean.getSession();
		long idAV = (long) session.getAttribute("idAV");
		List<DataNotificacion> notis = new ArrayList<>();
		try {
			notis = cAV.listaNotificacionesNoLeidas(idAV);
		} catch (NoExisteElAV e) {
			Url.redireccionarURL("error");
		}
		return notis;
	}

	public List<DataNotificacion> mostrarNotificaciones() {
		HttpSession session = SesionBean.getSession();
		long idAV = (long) session.getAttribute("idAV");
		List<DataNotificacion> notis = new ArrayList<>();

		try {
			notis = cAV.getNotificaciones(idAV);
		} catch (Exception e) {
			Url.redireccionarURL("error");
		}
		return notis;
	}
	
	public List<DataNotificacion> mostrarNotificacionesTodas() {
		HttpSession session = SesionBean.getSession();
		String nick = (String) session.getAttribute("nickname");
		List<DataNotificacion> notis = new ArrayList<>();
		List<DataAV> avs = cUsu.mostrarListaAv(nick);
		
		for( DataAV av : avs ) {
			try {
				notis.addAll(cAV.getNotificaciones(av.getIdAV()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return notis;
	}
	
	public List<DataNotificacion> mostrarNotificacionesNoLeidasTodas() {
		HttpSession session = SesionBean.getSession();
		String nick = (String) session.getAttribute("nickname");
		List<DataNotificacion> notis = new ArrayList<>();
		List<DataAV> avs = cUsu.mostrarListaAv(nick);
		
		for( DataAV av : avs ) {
			try {
				notis.addAll(cAV.listaNotificacionesNoLeidas(av.getIdAV()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return notis;
	}
	
	public List<DataAlerta> cargarAlertas() {
		HttpSession session = SesionBean.getSession();
		long idAV = (long) session.getAttribute("idAV");
		
		List<DataAlerta> da = new ArrayList<>();
		
		try {
			da = cAV.listaDeAlertas(idAV);
		} catch (NoExisteElAV e) {
			e.printStackTrace();
		}
		
		return da;
	}
	
	public void crearAlerta() {
		HttpSession session = SesionBean.getSession();
		long idAV = (long) session.getAttribute("idAV");
		try {
			cAV.crearAlerta(alertaProd, "stock:" + alertaCond + ":" + alertaVal, idAV);
		} catch (NoExisteElAV e) {
			Url.redireccionarURL("error");
		}
	}
	
	public String getEstilo() {
		return estilo;
	}

	public void setEstilo(String estilo) {
		this.estilo = estilo;
	}
	
	public void establecerEstilo(){
		try {
			Map<String, String> value = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			String colorSeleccioando = value.get("estilosPaginaTotal:colorSeleccionado");
			if (!this.estilo.equals(colorSeleccioando)) {
				estilo = colorSeleccioando;
				if (estilo != null) {
					HttpSession session = SesionBean.getSession();
					long idAV = (long) session.getAttribute("idAV");
					cAV.modificarColorAV(idAV, colorSeleccioando);
				}
			}
		} catch (Exception e) {
			System.out.println("Error al agreguegar el producto a la lista de compra");
		}
	}
	
	public void mostrarColor(){
		try {
			HttpSession session = SesionBean.getSession();
			long idAV = (long) session.getAttribute("idAV");
			DataAV dataAV = cAV.traerAV(idAV);
			this.estilo = dataAV.getColor();
		} catch (Exception e) {
			estilo = "skin-blue";
		}
	}
	public List<String> cargarMovimientosAV() {
		return cargarMovimientosAV(0);
	}
	public List<String> cargarMovimientosAV(int cant) {
		HttpSession session = SesionBean.getSession();
		long idAV = (long) session.getAttribute("idAV");
		
		return cAV.listaDeMovimientosAV(idAV, cant);
	}
	
	public Map<String, List<String>> cargarMovimientosTodos() {
		return cargarMovimientosTodos();
	}
	
	public Map<String, List<String>> cargarMovimientosTodos(int cant) {
		HttpSession session = SesionBean.getSession();
		String nick = (String) session.getAttribute("nickname");
		Map<String, List<String>> ret = new HashMap<>();
		List<DataAV> avs = cUsu.mostrarListaAv(nick);
		
		for( DataAV av : avs ) {
			session.setAttribute("idAV", av.getIdAV());
			ret.put(av.getNombreAV(), cargarMovimientosAV(cant));
		}
		
		return ret;
	}
	
	public int getCantMov() {
		return cargarMovimientosAV().size();
	}
	
	public void marcarNotificacionesComoLeidasAV() {
		HttpSession session = SesionBean.getSession();
		long idAV = (long) session.getAttribute("idAV");
		
		try {
			List<DataNotificacion> notis = cAV.listaNotificacionesNoLeidas(idAV);
			
			for( DataNotificacion not : notis ) {
				try {
					cAV.marcarNotificacionComoLeida(not.getIdNotificacion(), idAV);
				} catch (Exception e) {
					Url.redireccionarURL("error");
					e.printStackTrace();
					break;
				}
			}
			
		} catch (NoExisteElAV e) {
			Url.redireccionarURL("error");
			e.printStackTrace();
		}
	}
	
	public void marcarNotificacionesComoLeidasTodas() {
		
		HttpSession session = SesionBean.getSession();
		String nick = (String) session.getAttribute("nickname");
		List<DataAV> avs = cUsu.mostrarListaAv(nick);
		
		for( DataAV av : avs ) {
			session.setAttribute("idAV", av.getIdAV());
			marcarNotificacionesComoLeidasAV();
		}
		
	}

}
