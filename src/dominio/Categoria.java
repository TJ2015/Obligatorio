package dominio;

import java.io.Serializable;
import java.lang.String;
import java.util.List;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Categoria
 *
 */
@Entity

public class Categoria implements Serializable {

	   
	@Id
	private long idCategoria;
	private String nombre;
	private List<Producto> productos;
	private static final long serialVersionUID = 1L;

	public Categoria() {
		super();
	}   
	public long getIdCategoria() {
		return this.idCategoria;
	}

	public void setIdCategoria(long idCategoria) {
		this.idCategoria = idCategoria;
	}   
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}   
	public List getProductos() {
		return this.productos;
	}

	public void setProductos(List productos) {
		this.productos = productos;
	}
   
}
