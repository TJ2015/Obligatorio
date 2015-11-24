package dominio.log;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Log
 *
 */
@Entity
public class Log implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String usuario; // id del usuario que ejecuta la accion
	@OneToOne
	private Objetivo objetivo;
	@OneToOne
	private Accion accion;

	public Log() {
	}

	public Log(String usuario, Objetivo objetivo, Accion accion) {
		this.usuario = usuario;
		this.objetivo = objetivo;
		this.accion = accion;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Objetivo getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(Objetivo objetivo) {
		this.objetivo = objetivo;
	}

	public Accion getAccion() {
		return accion;
	}

	public void setAccion(Accion accion) {
		this.accion = accion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accion == null) ? 0 : accion.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((objetivo == null) ? 0 : objetivo.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
		Log other = (Log) obj;
		if (accion == null) {
			if (other.accion != null)
				return false;
		} else if (!accion.equals(other.accion))
			return false;
		if (id != other.id)
			return false;
		if (objetivo == null) {
			if (other.objetivo != null)
				return false;
		} else if (!objetivo.equals(other.objetivo))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

}
