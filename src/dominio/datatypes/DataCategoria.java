package dominio.datatypes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataCategoria implements Serializable {

	private static final long serialVersionUID = 1L;

	private long idCategoria;
	private String nombre;
	private List<DataProducto> productos = new ArrayList<>();

	public DataCategoria() {
		super();
	}

	public DataCategoria(long idCategoria, String nombre, List<DataProducto> productos) {
		super();
		this.idCategoria = idCategoria;
		this.nombre = nombre;
		this.productos = productos;
	}

	public long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<DataProducto> getProductos() {
		return productos;
	}

	public void setProductos(List<DataProducto> productos) {
		this.productos = productos;
	}

}
