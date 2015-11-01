package dominio;

import java.io.Serializable;
import javax.persistence.*;

import dominio.datatypes.DataProductoAComprar;

/**
 * Entity implementation class for Entity: ListaCompra
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name="ProductoAComprar.getAll", query="SELECT pc FROM ProductoAComprar pc")
})
public class ProductoAComprar implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	private Producto producto;
	private int cantidad; 
	
	private static final long serialVersionUID = 1L;

	public ProductoAComprar() {
	}

	public ProductoAComprar(Producto producto, int cantidad) {
		super();
		this.producto = producto;
		this.cantidad = cantidad;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public String toString() {
		return "ProductoAComprar [id=" + id + ", producto=" + producto + ", cantidad=" + cantidad + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cantidad;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((producto == null) ? 0 : producto.hashCode());
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
		ProductoAComprar other = (ProductoAComprar) obj;
		if (cantidad != other.cantidad)
			return false;
		if (id != other.id)
			return false;
		if (producto == null) {
			if (other.producto != null)
				return false;
		} else if (!producto.equals(other.producto))
			return false;
		return true;
	}

	public DataProductoAComprar getDataProductoAComprar() {
		return new DataProductoAComprar(id, producto.getDataProducto(), cantidad);
	}
	
}
