package managedbeans;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import dominio.Atributo;
import dominio.Categoria;
import negocio.IControladorInventario;


public class ProductoBean implements Serializable {


	@EJB
	IControladorInventario cinv;
	
	private Long idProducto;
	private String nombre;
	private String descripcion;
	private double precio;
	private int stock;
	@ManyToOne
	private Categoria categoria;

	// TODO agregar imagen
	@Transient
	private List<Atributo> atributosList;
	private static final long serialVersionUID = 1L;
	
	//para descripcion producto
		private long idAV;
	
	
		
	
	public ProductoBean(long idAV) {
			super();
			this.idAV = idAV;
		}


	public ProductoBean(){}
	
	
	
	
	
	

	
	public Long getIdProducto() {
		return idProducto;
	}


	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
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


	public int getStock() {
		return stock;
	}


	public void setStock(int stock) {
		this.stock = stock;
	}


	public Categoria getCategoria() {
		return categoria;
	}


	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}


	public List<Atributo> getAtributosList() {
		return atributosList;
	}


	public void setAtributosList(List<Atributo> atributosList) {
		this.atributosList = atributosList;
	}


	public long getIdAV() {
		return idAV;
	}


	public void setIdAV(long idAV) {
		this.idAV = idAV;
	}

	

	public void crearProductoDescripción(){
		
		try {
			if( cinv.crearProductoDescripcion(nombre, descripcion, precio, categoria, atributosList, idAV))
			{
				FacesContext.getCurrentInstance().getExternalContext().dispatch("/bienvenida.xhtml");
			} else {
				FacesContext.getCurrentInstance().getExternalContext().dispatch("/error.xhtml");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	

}
