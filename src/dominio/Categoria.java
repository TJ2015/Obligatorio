package dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import dominio.datatypes.DataCategoria;
import dominio.datatypes.DataProducto;

import javax.persistence.CascadeType;

/**
 * Entity implementation class for Entity: Categoria
 *
 */
@Entity
@NamedQueries({ @NamedQuery(name = "Categoria.getAll", query = "SELECT c FROM Categoria c"),
		@NamedQuery(name = "Categoria.buscarPorNombre", query = "SELECT c FROM Categoria c WHERE c.nombre = :nombre"),
		@NamedQuery(name = "Categoria.traerTodasCats", query = "SELECT c FROM Categoria c") })
public class Categoria implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idCategoria;
	private String nombre;
	@OneToMany(cascade = { CascadeType.REMOVE })
	@ElementCollection
	@JoinTable(name = "categoria_productos", joinColumns = @JoinColumn(name = "categoria") , inverseJoinColumns = @JoinColumn(name = "idCategoria") )
	private List<Producto> productos = new ArrayList<>();
	private static final long serialVersionUID = 1L;

	public Categoria() {

	}

	public Categoria(String nombre) {
		this.nombre = nombre;
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

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	public void addProducto(Producto prod) {
		this.productos.add(prod);
	}

	public void removeProducto(Producto prod) {
		this.productos.remove(prod);
	}

	public DataCategoria getDataCategoria() {

		List<DataProducto> listDprod = new ArrayList<>();
		if (productos != null) {
			for (Producto prods : productos) {
				listDprod.add(prods.getDataProducto());
			}
		}
		return new DataCategoria(idCategoria, nombre, listDprod);

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idCategoria ^ (idCategoria >>> 32));
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categoria other = (Categoria) obj;
		if (idCategoria != other.idCategoria)
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Categoria [idCategoria=" + idCategoria + ", nombre=" + nombre + "]";
	}
	
	
	
}
