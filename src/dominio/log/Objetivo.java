package dominio.log;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@NamedQueries({ 
	@NamedQuery(name = "Objetivo.getAll", query = "SELECT o FROM Objetivo o"),
	@NamedQuery(name = "Objetivo.buscarPorNombre", query = "SELECT o FROM Objetivo o WHERE o.nombre = :nombre"),
})
public class Objetivo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String nombre;
	private String descripcion;

	public Objetivo() {
	}

	public Objetivo(String nombre, String descripcion) {
		this.setNombre(nombre.toUpperCase());
		this.descripcion = descripcion;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre.toUpperCase();;
	}
}
