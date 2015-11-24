package dominio.log;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Objetivo
 *
 */
@Entity

public class Objetivo implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String descripcion;
	private long idObjetivo; // id del objeto al que se le ejecuta una acción
	private static final long serialVersionUID = 1L;

	public Objetivo() {
	}

	public Objetivo(String descripcion, long idObjetivo) {
		this.descripcion = descripcion;
		this.idObjetivo = idObjetivo;
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

	public long getIdObjetivo() {
		return idObjetivo;
	}

	public void setIdObjetivo(long idObjetivo) {
		this.idObjetivo = idObjetivo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (int) (idObjetivo ^ (idObjetivo >>> 32));
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
		Objetivo other = (Objetivo) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (id != other.id)
			return false;
		if (idObjetivo != other.idObjetivo)
			return false;
		return true;
	}

}
