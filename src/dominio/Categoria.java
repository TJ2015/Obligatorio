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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Entity implementation class for Entity: Categoria
 *
 */
@Entity
public class Categoria implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idCategoria;
	private String nombre;
	@OneToMany(fetch = FetchType.LAZY)
	@ElementCollection
	@JoinTable(name = "categoria_productos",
	    joinColumns = @JoinColumn(name = "categoria"),
	    inverseJoinColumns = @JoinColumn (name = "idCategoria"))
	private List<Producto> productos = new ArrayList<>();
	private static final long serialVersionUID = 1L;
	
	//MARIANELA
	@ManyToOne
	private AV av;

	public Categoria(){
		
	}
	
	public Categoria(String nombre){
		this.nombre=nombre;
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

	public AV getAv() {
		return av;
	}

	public void setAv(AV av) {
		this.av = av;
	}

	public void addProducto(Producto prod) {
		this.productos.add(prod);
	}

}
