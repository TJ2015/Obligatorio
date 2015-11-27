package dominio.log;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Accion
 *
 */
@Entity
@NamedQueries({ 
	@NamedQuery(name = "Accion.getAll", query = "SELECT a FROM Accion a"),
	@NamedQuery(name = "Accion.buscarPorNombre", query = "SELECT a FROM Accion a WHERE a.nombre = :nombre"),
})
public class Accion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String nombre;
	private String descripcion;

	public Accion() {
	}

	public Accion(String nombre, String descripcion) {
		this.descripcion = descripcion;
		this.nombre = nombre.toUpperCase();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre.toUpperCase();
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
