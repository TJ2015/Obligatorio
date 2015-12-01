package dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

import dominio.datatypes.DataProducto;

/**
 * Entity implementation class for Entity: Producto
 */
@Entity
@Access(AccessType.FIELD)
@NamedQueries({
		@NamedQuery(name = "Producto.buscarPorNombre", query = "SELECT p FROM Producto p WHERE p.nombre = :nombre"),
		@NamedQuery(name = "Producto.getAll", query = "SELECT p FROM Producto p"), 
		@NamedQuery(name = "Producto.obtenerNombreProductos", query = "SELECT p.nombre FROM Producto p"),
		@NamedQuery(name = "Producto.obtenerProductosOrdenadosPorStockASC", query = "SELECT p FROM Producto p ORDER BY p.stock ASC"),
})
public class Producto implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idProducto;
	private String nombre;
	private String descripcion;
	private double precio;
	private int stock;
	@ManyToOne
	private Categoria categoria;

	@Column(length = 1294967295)
	private byte[] bytesImagen;
	private String nombreImagen;

	@Transient
	private List<Atributo> atributosList = new ArrayList<>();

	private static final long serialVersionUID = 1L;

	public Producto() {
		super();
		this.stock = -1;
	}

	public Producto(String nombre, String descripcion, double precio, Categoria categoria, List<Atributo> atributosList,
			int stock) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.categoria = categoria;
		this.atributosList = atributosList;
		this.stock = stock;
	}
	/// Crea producto con Imagen
	public Producto(String nombre, String descripcion, double precio, Categoria categoria, List<Atributo> atributosList,
			int stock, byte[] bytesImagen, String nombreImagen) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.categoria = categoria;
		this.atributosList = atributosList;
		this.stock = stock;
		this.bytesImagen = bytesImagen;
		this.nombreImagen = nombreImagen;
	}

	public byte[] getBytesImagen() {
		return bytesImagen;
	}

	public void setBytesImagen(byte[] bytesImagen) {
		this.bytesImagen = bytesImagen;
	}

	public String getNombreImagen() {
		return nombreImagen;
	}

	public void setNombreImagen(String nombreImagen) {
		this.nombreImagen = nombreImagen;
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

	// PARA CONVERTIR A Y DESDE JSON - LO USA EL JPA, NO NOSOTROS!
	@Access(AccessType.PROPERTY)
	@Column(name = "atributos", nullable = false)
	public String getAtributos() {
		return util.Serializador.convertirAString(atributosList);
	}
	
	public void setAtributos(String attrs) {
		if (!attrs.equals("null")) {
			this.atributosList = util.Serializador.convertirDesdeString(attrs);
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

	public DataProducto getDataProducto() {
		Map<String, String> attr = new HashMap<>();

		for (Atributo a : atributosList) {
			attr.put(a.getNombre(), a.getValor());
		}

		return new DataProducto(idProducto, nombre, descripcion, precio, stock, categoria.getNombre(), attr,
				bytesImagen);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((atributosList == null) ? 0 : atributosList.hashCode());
		result = prime * result + Arrays.hashCode(bytesImagen);
		result = prime * result + ((categoria == null) ? 0 : categoria.hashCode());
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((idProducto == null) ? 0 : idProducto.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((nombreImagen == null) ? 0 : nombreImagen.hashCode());
		long temp;
		temp = Double.doubleToLongBits(precio);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + stock;
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
		Producto other = (Producto) obj;
		if (atributosList == null) {
			if (other.atributosList != null)
				return false;
		} else if (!atributosList.equals(other.atributosList))
			return false;
		if (!Arrays.equals(bytesImagen, other.bytesImagen))
			return false;
		if (categoria == null) {
			if (other.categoria != null)
				return false;
		} else if (!categoria.equals(other.categoria))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (idProducto == null) {
			if (other.idProducto != null)
				return false;
		} else if (!idProducto.equals(other.idProducto))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (nombreImagen == null) {
			if (other.nombreImagen != null)
				return false;
		} else if (!nombreImagen.equals(other.nombreImagen))
			return false;
		if (Double.doubleToLongBits(precio) != Double.doubleToLongBits(other.precio))
			return false;
		if (stock != other.stock)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Producto [id=" + idProducto + ", nombre=" + nombre + ", descripcion=" + descripcion
				+ ", precio=" + precio + ", stock=" + stock + ", categoria=" + categoria  + "]";
	}

	
}
