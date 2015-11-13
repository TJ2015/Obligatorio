package managedbeans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.model.UploadedFile;

import dominio.datatypes.DataCategoria;
import dominio.datatypes.DataProducto;
import exceptions.NoExisteElAV;
import exceptions.NoExisteElProducto;
import negocio.interfases.IControladorInventario;
import util.Url;

@ManagedBean
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
	private long idAV;
	
	private UploadedFile file;
	
	
	List<String> nomProd=new ArrayList<>();


	private static final long serialVersionUID = 1L;
	
	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}
	public List<DataProducto> getDprods2() {
		return dprods2;
	}

	public void setDprods2(List<DataProducto> dprods2) {
		this.dprods2 = dprods2;
	}

	public int getCantProd() {
		return cantProd;
	}

	public void setCantProd(int cantProd) {
		this.cantProd = cantProd;
	}

	public List<String> getNomProd() {
		return nomProd;
	}

	public void setNomProd(List<String> nomProd) {
		this.nomProd = nomProd;
	}
	private DataProducto dataProducto;

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
		HttpSession session = SesionBean.getSession();
		long idAV = (long) session.getAttribute("idAV");

		try {
			cinv.crearProducto(nombre, descripcion, precio, categoria, atributos, idAV,stock, file);
			//cinv.setStockProducto(nombre, idAV, stock);
			Url.redireccionarURL("ver_producto");
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
			FacesContext.getCurrentInstance().getExternalContext().dispatch("/verListaProducto.xhtml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			try {
				FacesContext.getCurrentInstance().getExternalContext().dispatch("/error.xhtml");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	public void mostrarTodosProd(){
		HttpSession session = SesionBean.getSession();
		long idAV = (long) session.getAttribute("idAV");
		List <DataCategoria> dcats= new ArrayList<>();
		List <DataProducto> dprodT= new ArrayList<>();
		try {
			dcats=cinv.mostrarListaCategoria(idAV);
			
			for(DataCategoria cats:dcats){
				dprodT=cats.getProductos();
				for(DataProducto prods:dprodT){
					dprods2.add(prods);
					cantProd++;
				}
			}
			FacesContext.getCurrentInstance().getExternalContext().dispatch("/verListaProducto.xhtml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			try {
				FacesContext.getCurrentInstance().getExternalContext().dispatch("/error.xhtml");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	public void cargarProducto() {
		HttpSession session = SesionBean.getSession();
		long idAV = (long) session.getAttribute("idAV");
		/*String nombre = FacesContext.getCurrentInstance().
				getExternalContext().getRequestParameterMap().get("hidden1");*/
		
		try {
			DataProducto dprod = cinv.getProducto(nombre, idAV);
			
			this.dataProducto = dprod;
			FacesContext.getCurrentInstance().getExternalContext().dispatch("/ver_producto.xhtml");
			
		} catch (NoExisteElAV | NoExisteElProducto | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void eliminarProducto(String name) throws Exception {
		HttpSession session = SesionBean.getSession();
		long idAV= (long) session.getAttribute("idAV");
		Locale locale = new Locale(name);
			cinv.eliminarProducto(name, idAV);
			try {
				FacesContext.getCurrentInstance().getExternalContext().dispatch("/index.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	
}
