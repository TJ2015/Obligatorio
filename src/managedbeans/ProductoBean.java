package managedbeans;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import negocio.IControladorInventario;


@ManagedBean
public class ProductoBean implements Serializable {


	@EJB
	IControladorInventario cinv;
	
	private String nombre;
	private String descripcion;
	private double precio;
	private String categoria;
	private String atributos;
	private int stock;
	private static final long serialVersionUID = 1L;
	
	//para descripcion producto
	private long idAV;
	
	public ProductoBean(long idAV) {
			super();
			this.idAV = idAV;
		}


	public ProductoBean(){}
	
	
	
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
			cinv.crearProducto(nombre, descripcion, precio, categoria, atributos, idAV,stock);
			//cinv.setStockProducto(nombre, idAV, stock);
			FacesContext.getCurrentInstance().getExternalContext().dispatch("/bienvenida.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				FacesContext.getCurrentInstance().getExternalContext().dispatch("/error.xhtml");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}

}
