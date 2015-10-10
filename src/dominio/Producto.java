package dominio;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Producto
 *
 */
@Entity
public class Producto extends ProductoDescripcion {
	
	private static final long serialVersionUID = 1L;
	
	public Producto() {
		super();
	}
	
	public Producto(String nombre, String descripcion, double precio, Categoria categoria,
			List<Atributo> atributosList) {
		super(nombre, descripcion, precio, categoria, atributosList);
	}
   
}
