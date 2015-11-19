package managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.HttpSession;

import org.primefaces.model.UploadedFile;

import dominio.datatypes.DataCategoria;
import dominio.datatypes.DataProducto;
import exceptions.NoExisteElAV;
import exceptions.NoExisteElProducto;
import negocio.interfases.IControladorInventario;
import util.Url;

@ManagedBean
@RequestScoped
public class ProductoBean implements Serializable {
	@EJB
	IControladorInventario cinv;

	private String nombre;
	private String nombreCat;
	private String descripcion;
	private double precio;
	private String categoria;
	private String atributos;
	private int stock;
	private int cantProd;
	private List<DataProducto> dprods = new ArrayList<>();
	private List<DataProducto> dprods2 = new ArrayList<>();	
	private UploadedFile file;
	private long idAV;
	private DataProducto dataProducto;
	
	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}
	

	List<String> nomProd=new ArrayList<>();


	private static final long serialVersionUID = 1L;
	public List<DataProducto> getDprods2() {
		return dprods2;
	}

	public void setDprods2(List<DataProducto> dprods2) {
		this.dprods2 = dprods2;
	}

	public int getCantProd() {
		HttpSession session = SesionBean.getSession();
		cantProd = 0;
		long idAV = (long) session.getAttribute("idAV");
		try {
			for(DataCategoria cats : cinv.mostrarListaCategoria(idAV)){
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
			for(DataCategoria cats : cinv.mostrarListaCategoria(idAV)){
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

	public void crearProductoDescripción() {
		try {
			HttpSession session = SesionBean.getSession();
			long idAV = (long) session.getAttribute("idAV");
			dataProducto = cinv.crearProducto(nombre, descripcion, precio, categoria, atributos, idAV, stock, file);
			
			if( dataProducto == null )
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
	

	public void mostrarTodosProd(){
		try {
			HttpSession session = SesionBean.getSession();
			long idAV = (long) session.getAttribute("idAV");
			List <DataCategoria> dcats= new ArrayList<>();
			List <DataProducto> dprodT= new ArrayList<>();
			dcats=cinv.mostrarListaCategoria(idAV);
			
			for(DataCategoria cats:dcats){
				dprodT=cats.getProductos();
				for(DataProducto prods:dprodT){
					dprods2.add(prods);
				}
			}
			Url.redireccionarURL("verListaProducto");
		} catch (Exception e) {
			Url.redireccionarURL("error");
			e.printStackTrace();
		}
	}
	
	public void cargarProducto() {
		try {
			HttpSession session = SesionBean.getSession();
			long idAV = (long) session.getAttribute("idAV");
			DataProducto dprod = cinv.getProducto(nombre, idAV);
			
			this.setDataProducto(dprod);
			Url.redireccionarURL("ver_producto");
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public void eliminarProducto(String name) throws Exception {
		try {
			HttpSession session = SesionBean.getSession();
			long idAV= (long) session.getAttribute("idAV");
			cinv.eliminarProducto(name, idAV);
			Url.redireccionarURL("index");
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
	
}
