package dominio;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import dominio.datatypes.DataLogEntry;

/**
 * Entity implementation class for Entity: Entrada
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name="LogEntry.buscarPorId", query="SELECT l FROM LogEntry l WHERE l.id =:id"),
	@NamedQuery(name="LogEntry.getAll", query="SELECT l FROM LogEntry l")
})
public class LogEntry implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private Date fecha;
	private String texto;

	private static final long serialVersionUID = 1L;

	public LogEntry() {
	}

	public LogEntry(Date fecha, String texto) {
		super();
		this.fecha = fecha;
		this.texto = texto;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((texto == null) ? 0 : texto.hashCode());
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
		LogEntry other = (LogEntry) obj;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (texto == null) {
			if (other.texto != null)
				return false;
		} else if (!texto.equals(other.texto))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return fecha + ": " + texto;
	}

	public DataLogEntry getDataLogEntry() {
		return new DataLogEntry(id, fecha, texto);
	}

}
