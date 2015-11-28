package managedbeans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;

import org.primefaces.model.UploadedFile;

import dominio.datatypes.DataAV;
import dominio.datatypes.DataAdministrador;
import dominio.datatypes.DataCategoria;
import dominio.datatypes.DataProducto;
import dominio.datatypes.DataUsuario;
import exceptions.NoExisteElAV;
import exceptions.NoExisteElProducto;
import exceptions.NoExisteElUsuario;
import exceptions.UsuarioNoEncontrado;
import exceptions.YaExisteElUsuario;
import negocio.interfases.IControladorAV;
import negocio.interfases.IControladorInventario;
import negocio.interfases.IControladorUsuario;
import util.Url;

@ManagedBean
@SessionScoped
public class AdminBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@EJB
	IControladorUsuario cusu;
	@EJB
	IControladorAV cAV;
	@EJB
	IControladorInventario cInv;

	private String nick;
	private String password;
	private String error;
	private boolean logueado = false;
	private String imagen;
	private String nickReg;
	private String emailReg;
	private DataUsuario usuario;
	private DataAV avUsu;
	private List<DataProducto> prodsUsu = new ArrayList<>();
	private boolean eliminarProducto;
	private String prodEliminar;
	private boolean eliminarAV;
	private String avEliminar;
	private String usuarioEliminar;
	private boolean eliminarUsuario;
	private String nombreCategoria;
	private String nombreProd;
	private String nombreCatProd;
	private String descripcionProd;
	private double precioProd;
	private String categoriaProd;
	private String atributosProd;
	private int stockProd;
	private int cantProdProd;
	private UploadedFile fileProd;
	private DataProducto prodVer;
	private String categoriaEliminar;
	private boolean eliminarCategoria;
	private DataCategoria categoriaVer;

	public AdminBean() {
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

	public DataCategoria getCategoriaVer() {
		return categoriaVer;
	}

	public void setCategoriaVer(DataCategoria categoriaVer) {
		this.categoriaVer = categoriaVer;
	}

	public DataProducto getProdVer() {
		return prodVer;
	}

	public void setProdVer(DataProducto prodVer) {
		this.prodVer = prodVer;
	}

	public UploadedFile getFileProd() {
		return fileProd;
	}

	public void setFileProd(UploadedFile fileProd) {
		this.fileProd = fileProd;
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

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public boolean isLogueado() {
		return logueado;
	}

	public void setLogueado(boolean logueado) {
		this.logueado = logueado;
	}

	public String getNickReg() {
		return nickReg;
	}

	public void setNickReg(String nickReg) {
		this.nickReg = nickReg;
	}

	public String getEmailReg() {
		return emailReg;
	}

	public void setEmailReg(String emailReg) {
		this.emailReg = emailReg;
	}

	public List<DataProducto> getProdsUsu() {
		return prodsUsu;
	}

	public void setProdsUsu(List<DataProducto> prodsUsu) {
		this.prodsUsu = prodsUsu;
	}

	public boolean isEliminarAV() {
		return eliminarAV;
	}

	public void setEliminarAV(boolean eliminarAV) {
		this.eliminarAV = eliminarAV;
	}

	public String getAvEliminar() {
		return avEliminar;
	}

	public void setAvEliminar(String avEliminar) {
		this.avEliminar = avEliminar;
	}

	public String getUsuarioEliminar() {
		return usuarioEliminar;
	}

	public void setUsuarioEliminar(String usuarioEliminar) {
		this.usuarioEliminar = usuarioEliminar;
	}

	public boolean isEliminarUsuario() {
		return eliminarUsuario;
	}

	public void setEliminarUsuario(boolean eliminarUsuario) {
		this.eliminarUsuario = eliminarUsuario;
	}

	public String getNombreCategoria() {
		return nombreCategoria;
	}

	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}

	public String getNombreProd() {
		return nombreProd;
	}

	public void setNombreProd(String nombreProd) {
		this.nombreProd = nombreProd;
	}

	public String getNombreCatProd() {
		return nombreCatProd;
	}

	public void setNombreCatProd(String nombreCatProd) {
		this.nombreCatProd = nombreCatProd;
	}

	public String getDescripcionProd() {
		return descripcionProd;
	}

	public void setDescripcionProd(String descripcionProd) {
		this.descripcionProd = descripcionProd;
	}

	public double getPrecioProd() {
		return precioProd;
	}

	public void setPrecioProd(double precioProd) {
		this.precioProd = precioProd;
	}

	public String getCategoriaProd() {
		return categoriaProd;
	}

	public void setCategoriaProd(String categoriaProd) {
		this.categoriaProd = categoriaProd;
	}

	public String getAtributosProd() {
		return atributosProd;
	}

	public void setAtributosProd(String atributosProd) {
		this.atributosProd = atributosProd;
	}

	public int getStockProd() {
		return stockProd;
	}

	public void setStockProd(int stockProd) {
		this.stockProd = stockProd;
	}

	public int getCantProdProd() {
		return cantProdProd;
	}

	public void setCantProdProd(int cantProdProd) {
		this.cantProdProd = cantProdProd;
	}

	public void login() throws IOException {
		try {
			DataAdministrador da = cusu.loginAdmin(nick, password);
			if (da != null) {
				HttpSession session = SesionBean.getSession();
				session.setAttribute("nickname", nick);
				logueado = true;
				error = null;
				String hash = util.Gravatar.md5Hex(da.getEmail());
				setImagen("http://www.gravatar.com/avatar/" + hash + "?d=retro");
				Url.redireccionarURL("backend/admin");
			} else {
				error = "Nombre de usuario o contraseña incorrecta.";
			}
		} catch (NoExisteElUsuario e) {
			error = "Nombre de usuario o contraseña incorrecta.";
		}
	}

	public void logout() throws IOException {
		HttpSession session = SesionBean.getSession();
		session.invalidate();
		Url.redireccionarURL("backend/login_admin");
	}

	public int cantUsu() {
		int cant = 0;

		if (logueado) {
			cant = cusu.getUsuarios().size();
		}

		return cant;
	}

	public int cantCat() {
		int cant = 0;

		if (logueado) {
			try {
				cant = cInv.mostrarListaCategoria(-1).size();
			} catch (Exception e) {
				// 505
				e.printStackTrace();
			}
		}

		return cant;
	}

	public int cantProd() {
		int cant = 0;

		if (logueado) {
			cant = cInv.getProductos(-1).size();
		}

		return cant;
	}

	public int cantMembresia() {
		int cant = 0;

		if (logueado) {
			cant = cusu.listaUsuariosPremium();
		}

		return cant;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public boolean isEliminarProducto() {
		return eliminarProducto;
	}

	public void setEliminarProducto(boolean eliminarProducto) {
		this.eliminarProducto = eliminarProducto;
	}

	public String getProdEliminar() {
		return prodEliminar;
	}

	public void setProdEliminar(String prodEliminar) {
		this.prodEliminar = prodEliminar;
	}

	public DataUsuario getUsuario() {
		return usuario;
	}

	public void setUsuario(DataUsuario usuario) {
		this.usuario = usuario;
	}

	public DataAV getAvUsu() {
		return avUsu;
	}

	public void setAvUsu(DataAV avUsu) {
		this.avUsu = avUsu;
	}

	public void registrarAdministrador() {
		try {
			cusu.registrarAdmin(nickReg, "", emailReg);
			Url.redireccionarURL("backend/admin");
		} catch (YaExisteElUsuario e) {
			error = "Ya existe un administrador con ese nickname/email";
		}

	}

	public List<DataUsuario> getUsuarios() {
		return cusu.getUsuarios();
	}

	public void getUsuario(String nickname) {
		HttpSession session = SesionBean.getSession();
		usuario = cusu.getUsuario(nickname);
		session.setAttribute("dataUsuario", usuario);
	}

	public void getAV(String nickname, String av) {
		try {
			avUsu = cAV.traerAVPorNombre(av, nickname);
			prodsUsu = cInv.getProductos(avUsu.getIdAV());
		} catch (UsuarioNoEncontrado e) {
			e.printStackTrace();
		}
	}

	public void prepararParaEliminarProducto(String prod) {
		prodEliminar = prod;
		eliminarProducto = true;
	}

	public void eliminarProducto() {
		eliminarProducto(false);
	}

	public void eliminarProducto(boolean generico) {
		eliminarProducto = false;

		if (!generico) {
			cInv.eliminarProducto(avUsu.getNickname(), prodEliminar, avUsu.getIdAV());
			try {
				avUsu = cAV.traerAVPorNombre(avUsu.getNombreAV(), avUsu.getNickname());
				prodsUsu = cInv.getProductos(avUsu.getIdAV());
			} catch (UsuarioNoEncontrado e) {
				e.printStackTrace();
			}
		} else {
			cInv.eliminarProducto(avUsu.getNickname(), prodEliminar, -1);
		}
		prodEliminar = null;
	}

	public void prepararParaEliminarAV(String av) {
		avEliminar = av;
		setEliminarAV(true);
	}

	public void eliminarAV() {
		setEliminarAV(false);
		getAV(usuario.getNick(), avEliminar);
		avEliminar = null;
		try {
			cAV.eliminarAV(avUsu.getIdAV());
			getUsuario(usuario.getNick());
		} catch (Exception e) {
			// MANDAR UN 505
			e.printStackTrace();
		}
	}

	public void prepararParaEliminarUsuario(String usu) {
		setUsuarioEliminar(usu);
		setEliminarUsuario(true);
	}

	public void eliminarUsuario() {
		setEliminarUsuario(false);
		cusu.eliminarUsuario(usuarioEliminar);
		usuarioEliminar = null;
	}

	public void cancelarEliminarUsuario() {
		usuarioEliminar = null;
		eliminarUsuario = false;
	}

	public void cancelarEliminarAV() {
		avEliminar = null;
		eliminarAV = false;
	}

	public void cancelarEliminarProducto() {
		prodEliminar = null;
		eliminarProducto = false;
	}

	public void crearCategoria() {
		if (logueado)
			cInv.crearCategoria(avUsu.getNickname(), nombreCategoria, -1);
	}

	public List<DataCategoria> mostrarListaCategoria() {
		List<DataCategoria> cats = new ArrayList<>();
		try {
			cats = cInv.mostrarListaCategoria(-1);
		} catch (Exception e) {
			// TODO 505
			e.printStackTrace();
		}
		return cats;
	}

	public void crearProducto() {
		try {
			cInv.crearProducto(avUsu.getNickname(), nombreProd, descripcionProd, precioProd, categoriaProd, atributosProd, -1, stockProd,
					fileProd);
		} catch (Exception e) {
			// TODO 505
			e.printStackTrace();
		}
	}

	public List<DataCategoria> cargarCategorias() {
		List<DataCategoria> categorias = new ArrayList<>();
		try {
			categorias = cInv.mostrarListaCategoria(-1);
		} catch (Exception e) {
			// TODO 505
			e.printStackTrace();
		}
		return categorias;
	}

	public List<DataProducto> cargarProductos() {
		List<DataProducto> productos = new ArrayList<>();
		productos = cInv.getProductos(-1);
		return productos;
	}

	public void cargarProducto(String prod) {
		try {
			prodVer = cInv.getProducto(prod);
		} catch (NoExisteElProducto e) {
			// TODO 505
			e.printStackTrace();
		}
	}

	public void prepararParaEliminarCategoria(String cat) {
		categoriaEliminar = cat;
		eliminarCategoria = true;
	}

	public void eliminarCategoria() {
		eliminarCategoria = false;
		try {
			cInv.eliminarCategoria(avUsu.getNickname(), categoriaEliminar, -1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		categoriaEliminar = null;
	}

	public void cancelarEliminarCategoria() {
		categoriaEliminar = null;
		eliminarCategoria = false;
	}

	public void cargarCategoria(String nombre) {
		try {
			categoriaVer = cInv.getCategoria(nombre, -1);
		} catch (NoExisteElAV e) {
			// TODO 505
			e.printStackTrace();
		}
	}

}
