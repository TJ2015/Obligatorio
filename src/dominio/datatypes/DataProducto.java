package dominio.datatypes;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import dominio.Categoria;

public class DataProducto implements Serializable {

	private Long idProducto;
	private String nombre;
	private String descripcion;
	private double precio;
	private int stock;
	private Categoria categoria;
	private Map<String, String> atributosList = new HashMap<>();
	private long idAV;
	
	public DataProducto(Long idProducto, String nombre, String descripcion, double precio, int stock,
			Categoria categoria, Map<String, String> atributosList, long idAV) {
		super();
		this.idProducto = idProducto;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.stock = stock;
		this.categoria = categoria;
		this.atributosList = atributosList;
		this.idAV = idAV;
	}

	public DataProducto() {
		super();
	}

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

	public Map<String, String> getAtributosList() {
		return atributosList;
	}

	public void setAtributosList(Map<String, String> atributosList) {
		this.atributosList = atributosList;
	}

	public long getIdAV() {
		return idAV;
	}

	public void setIdAV(long idAV) {
		this.idAV = idAV;
	}
		
}
