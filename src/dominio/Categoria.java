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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idCategoria;
	private String nombre;
	@OneToMany(mappedBy = "categoria", fetch = FetchType.LAZY)
	@ElementCollection
	private List<ProductoDescripcion> productos;
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



	public List<ProductoDescripcion> getProductos() {
		return productos;
	}



	public void setProductos(List<ProductoDescripcion> productos) {
		this.productos = productos;
	}


	public AV getAv() {
		return av;
	}

	public void setAv(AV av) {
		this.av = av;
	}

	
	
	

	   
}
