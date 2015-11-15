package dominio.datatypes;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DataProducto implements Serializable {

	private static final long serialVersionUID = 1L;

	private long idProducto;
	private String nombre;
	private String descripcion;
	private double precio;
	private int stock;
	private String categoria;
	private Map<String, String> atributosList = new HashMap<>();
	private byte[] imagen;

	public DataProducto(long idProducto, String nombre, String descripcion, double precio, int stock, String categoria,
			Map<String, String> atributosList, byte[] imagen) {
		super();
		this.idProducto = idProducto;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.stock = stock;
		this.categoria = categoria;
		this.atributosList = atributosList;
		this.imagen = imagen;
	}

	public DataProducto() {
		super();
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(long idProducto) {
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

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Map<String, String> getAtributosList() {
		return atributosList;
	}

	public void setAtributosList(Map<String, String> atributosList) {
		this.atributosList = atributosList;
	}

}
