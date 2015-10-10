package dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.google.gson.Gson;

/**
 * Entity implementation class for Entity: Producto
 *
 */
@Entity
@Access(AccessType.FIELD)
public class ProductoDescripcion implements Serializable {

	   
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	public ProductoDescripcion() {
		super();
		this.stock = -1;
	}
	
	public ProductoDescripcion(String nombre, String descripcion, double precio, Categoria categoria,
			List<Atributo> atributosList) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.categoria = categoria;
		this.atributosList = atributosList;
		this.stock = -1;
	}

	public Long getIdProducto() {
		return this.idProducto;
	}	
	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}   
	public String getNombre() {
		return this.nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}   
	public String getDescripcion() {
		return this.descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}   
	public double getPrecio() {
		return this.precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	//PARA CONVERTIR A Y DESDE JSON - LO USA EL JPA, NO NOSOTROS!
	@Access(AccessType.PROPERTY)	
	@Column(name="atributos", nullable = false)
	public String getAtributos() {
		Gson gson = new Gson();
		return gson.toJson(atributosList);
	}
	public void setAtributos(String attrs) {
		if( !attrs.equals("null") ) {
			Gson gson = new Gson();
			Atributo[] auxArray = gson.fromJson(attrs, Atributo[].class);
			List<Atributo> auxList = new ArrayList<>(Arrays.asList(auxArray));
			this.atributosList = auxList;
		}
	}
	
	public List<Atributo> getAtributosList() {
		return atributosList;
	}
	public void setAtributosList(List<Atributo> atributosList) {
		this.atributosList = atributosList;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
	
}
