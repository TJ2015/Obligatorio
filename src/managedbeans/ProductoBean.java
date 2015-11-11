package managedbeans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.SessionBean;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import dominio.datatypes.DataCategoria;
import dominio.datatypes.DataProducto;
import exceptions.NoExisteElAV;
import exceptions.NoExisteElProducto;
import negocio.interfases.IControladorInventario;

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
	List<String> nomProd=new ArrayList<>();


	private static final long serialVersionUID = 1L;

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

	// para descripcion producto
	private long idAV;

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
			cinv.crearProducto(nombre, descripcion, precio, categoria, atributos, idAV,stock);
			//cinv.setStockProducto(nombre, idAV, stock);
			FacesContext.getCurrentInstance().getExternalContext().dispatch("/ver_producto.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				FacesContext.getCurrentInstance().getExternalContext().dispatch("/error.xhtml");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
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
					nomProd.add(prods.getNombre());
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
}
