package managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.HttpSession;

import org.primefaces.model.UploadedFile;

import dominio.datatypes.DataCategoria;
import dominio.datatypes.DataProducto;
import dominio.datatypes.DataProductoVendido;
import exceptions.NoExisteElAV;
import exceptions.NoExisteElProducto;
import negocio.interfases.IControladorAV;
import negocio.interfases.IControladorAlgoritmos;
import negocio.interfases.IControladorInventario;
import util.Url;

@ManagedBean
@RequestScoped
public class ProductoBean implements Serializable {
	@EJB
	IControladorInventario cinv;
	@EJB
	IControladorAV cAV;
	
	@EJB
	private IControladorAlgoritmos cAlgoritmos;

	private String nombre;
	private String nombreCat;
	private String descripcion;
	private double precio;
	private String categoria;
	private String atributos;
	private String prueba;
	private String nuevaCategoria;
	private int stock;
	private int cantProd;
	private boolean accionProd = false;
	private List<DataProducto> dprods = new ArrayList<>();
	private List<DataProducto> dprods2 = new ArrayList<>();
	private UploadedFile file;
	private UploadedFile newFile;
	private long idAV;
	private long idAvCopiar;
	private DataProducto dataProducto;
	List<String> nomProd = new ArrayList<>();

	public UploadedFile getNewFile() {
		return newFile;
	}

	public void setNewFile(UploadedFile newFile) {
		this.newFile = newFile;
	}

	public String getNuevaCategoria() {
		return nuevaCategoria;
	}

	public void setNuevaCategoria(String nuevaCategoria) {
		this.nuevaCategoria = nuevaCategoria;
	}

	public boolean isAccionProd() {
		return accionProd;
	}

	public void setAccionProd(boolean accionProd) {
		this.accionProd = accionProd;
	}

	public long getIdAvCopiar() {
		return idAvCopiar;
	}

	public void setIdAvCopiar(long idAvCopiar) {
		this.idAvCopiar = idAvCopiar;
	}

	public String getPrueba() {
		return prueba;
	}

	public void setPrueba(String prueba) {
		this.prueba = prueba;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	private static final long serialVersionUID = 1L;

	public List<DataProducto> getDprods2() {
		return dprods2;
	}

	public void setDprods2(List<DataProducto> dprods2) {
		this.dprods2 = dprods2;
	}

	public int verCantProd() {
		HttpSession session = SesionBean.getSession();
		cantProd = 0;
		long idAV = (long) session.getAttribute("idAV");
		try {
			for (DataCategoria cats : cinv.mostrarListaCategoria(idAV)) {
				cantProd += cats.getProductos().size();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cantProd;
	}

	public void setCantProd(int cantProd) {
		HttpSession session = SesionBean.getSession();
		cantProd = 0;
		long idAV = (long) session.getAttribute("idAV");
		try {
			for (DataCategoria cats : cinv.mostrarListaCategoria(idAV)) {
				cantProd += cats.getProductos().size();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<String> getNomProd() {
		return nomProd;
	}

	public void setNomProd(List<String> nomProd) {
		this.nomProd = nomProd;
	}

	public ProductoBean(long idAV) {
		super();
		this.idAV = idAV;
	}

	public ProductoBean() {
	}

	public int getCantProd() {
		return cantProd;
	}

	public List<DataProducto> getDprods() {
		return dprods;
	}

	public void setDprods(List<DataProducto> dprods) {
		this.dprods = dprods;
	}

	public String getNombreCat() {
		return nombreCat;
	}

	public void setNombreCat(String nombreCat) {
		this.nombreCat = nombreCat;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getAtributos() {
		return atributos;
	}

	public void setAtributos(String atributosList) {
		this.atributos = atributosList;
	}

	public long getIdAV() {
		return idAV;
	}

	public void setIdAV(long idAV) {
		this.idAV = idAV;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public void crearProductoDescripcion() {
		try {
			HttpSession session = SesionBean.getSession();

			long idAV = (long) session.getAttribute("idAV");
			dataProducto = cinv.crearProducto((String) session.getAttribute("nickname"), nombre, descripcion, precio,
					categoria, atributos, idAV, stock, file);

			if (dataProducto == null)
				Url.redireccionarURL("error");

		} catch (Exception e) {
			e.printStackTrace();
			Url.redireccionarURL("error");
		}
	}

	public void mostrarListaProducto() {
		HttpSession session = SesionBean.getSession();
		long idAV = (long) session.getAttribute("idAV");
		try {
			dprods = cinv.getCategoria(nombreCat, idAV).getProductos();
			Url.redireccionarURL("verListaProducto");
		} catch (Exception e) {
			Url.redireccionarURL("error");
			e.printStackTrace();
		}
	}

	public List<DataProducto> mostrarTodosProd() {
		HttpSession session = SesionBean.getSession();
		long idAV = (long) session.getAttribute("idAV");
		return cinv.getProductos(idAV);
	}

	public void cargarProducto() {
		try {
			HttpSession session = SesionBean.getSession();
			long idAV = (long) session.getAttribute("idAV");
			DataProducto dprod = cinv.getProducto(nombre, idAV);

			this.setDataProducto(dprod);
			Url.redireccionarURL("ver_producto");

		} catch (Exception e) {
			Url.redireccionarURL("error");
			e.printStackTrace();
		}
	}

	public void eliminarProducto(String prodEliminar) {
		try {
			HttpSession session = SesionBean.getSession();
			long idAV = (long) session.getAttribute("idAV");
			cinv.eliminarProducto((String) session.getAttribute("nickname"), prodEliminar, idAV);
		} catch (Exception e) {
			e.printStackTrace();
			Url.redireccionarURL("error");
		}
	}

	public DataProducto getDataProducto() {
		return dataProducto;
	}

	public void setDataProducto(DataProducto dataProducto) {
		this.dataProducto = dataProducto;
	}

	public void cargarDataProducto(String nombre) {
		try {
			HttpSession session = SesionBean.getSession();
			long idAV = (long) session.getAttribute("idAV");
			dataProducto = cinv.getProducto(nombre, idAV);
		} catch (NoExisteElAV | NoExisteElProducto e) {
			e.printStackTrace();
			Url.redireccionarURL("error");
		}
	}

	public String pruebaNom(String prue) {
		prueba = prue;
		return prueba;
	}

	public void aumentarStock(String prod) {
		HttpSession session = SesionBean.getSession();
		long idAV = (long) session.getAttribute("idAV");
		String nick = (String) session.getAttribute("nickname");

		try {
			DataProducto dataProducto = cinv.getProducto(prod, idAV);
			cinv.setStockProducto(nick, prod, idAV, dataProducto.getStock() + 1);
			mostrarTodosProd();
		} catch (Exception e) {
			Url.redireccionarURL("error");
		}
	}

	public void disminuirStock(String prod) {
		HttpSession session = SesionBean.getSession();
		long idAV = (long) session.getAttribute("idAV");
		String nick = (String) session.getAttribute("nickname");

		try {
			DataProducto dataProducto = cinv.getProducto(prod, idAV);
			cinv.setStockProducto(nick, prod, idAV, dataProducto.getStock() - 1);
			mostrarTodosProd();
		} catch (Exception e) {
			Url.redireccionarURL("error");
		}
	}

	public void copiarProducto(String nombreProducto) {

		HttpSession session = SesionBean.getSession();
		long idAV = (long) session.getAttribute("idAV");
		String nick = (String) session.getAttribute("nickname");

		try {
			accionProd = cinv.copiarProducto(nick, nombreProducto, idAV, idAvCopiar);
			if (accionProd) {
				Url.redireccionarURL("exito");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Url.redireccionarURL("error");

		}
	}

	public void agregarProductoGenerico(String nombreProducto) {

		HttpSession session = SesionBean.getSession();
		long idAV = (long) session.getAttribute("idAV");
		String nick = (String) session.getAttribute("nickname");
		try {
			accionProd = cinv.copiarProducto(nick, nombreProducto, -1, idAV);
			if (accionProd) {
				Url.redireccionarURL("exito");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Url.redireccionarURL("error");

		}

	}

	public void cortarProducto(String nombreProducto) {

		HttpSession session = SesionBean.getSession();

		long idAV = (long) session.getAttribute("idAV");
		String nick = (String) session.getAttribute("nickname");

		try {
			accionProd = cinv.moverProducto(nick, nombreProducto, idAV, idAvCopiar);
			if (accionProd) {
				Url.redireccionarURL("exito");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Url.redireccionarURL("error");
		}
	}

	public Map<String, String> cargarDatos(String nombreProd) {

		HttpSession session = SesionBean.getSession();
		
		long idAV = (long) session.getAttribute("idAV");
		Map<String, String> attrs = new HashMap<>();
		try {
			DataProducto dp = cinv.getProducto(nombreProd, idAV);
			nombre = dp.getNombre();
			categoria = dp.getCategoria();
			descripcion = dp.getDescripcion();
			precio = dp.getPrecio();
			stock = dp.getStock();
			attrs = dp.getAtributosList();
		} catch (NoExisteElAV | NoExisteElProducto e) {
			Url.redireccionarURL("exito");
			e.printStackTrace();
		}
		
		return attrs;
	}

	public void modificarProducto(String nombreProd) throws Exception {
		HttpSession session = SesionBean.getSession();
		long idAV = (long) session.getAttribute("idAV");
		String nick = (String) session.getAttribute("nickname");
		cinv.modificarProducto(nick, nombreProd, idAV, nombre, descripcion, precio, atributos);
		cinv.cambiarCategoriaProducto(nick, nuevaCategoria, nombreProd, idAV);
		cargarDataProducto(nombre);
	}

	public void cambiarImagenProducto(String producto) {
		HttpSession session = SesionBean.getSession();
		long idAV = (long) session.getAttribute("idAV");
		String nick = (String) session.getAttribute("nickname");
		cinv.cambiarImagenProducto(nick, newFile, producto, idAV);
	}

	public List<DataProductoVendido> obtenerTopProductos(int cantidad, boolean distinguir){
		List<DataProductoVendido> lProductos = null; 
		try {
			lProductos = cAlgoritmos.obtenerProductosMasVendidos(cantidad, distinguir);
		} catch (Exception e) {
			System.out.println("Error al obtener la lista de los productos mas utilizados");
		}
		return lProductos;
	}
	
}
