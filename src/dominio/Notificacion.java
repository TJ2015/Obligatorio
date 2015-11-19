package dominio;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import dominio.datatypes.DataNotificacion;

/**
 * Entity implementation class for Entity: Notificacion
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Notificacion.buscarPorId", query="SELECT n FROM Notificacion n WHERE n.idNotificacion =:idNotificacion"),
	@NamedQuery(name="Notificacion.getAll", query="SELECT n FROM Notificacion n"),
	@NamedQuery(name="Notificacion.getAllNoLeido", query="SELECT n FROM Notificacion n WHERE n.leido = false")
})
public class Notificacion implements Serializable {

	   
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idNotificacion;
	private String texto;
	private boolean leido = false;
	private static final long serialVersionUID = 1L;

	public Notificacion() {
	}
	
	public Notificacion(String texto) {
		this.texto = texto;
		this.leido = false;
	}


	public long getIdNotificacion() {
		return this.idNotificacion;
	}

	public void setIdNotificacion(long idNotificacion) {
		this.idNotificacion = idNotificacion;
	}   
	public String getTexto() {
		return this.texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}   
	public boolean getLeido() {
		return this.leido;
	}

	public void setLeido(boolean leido) {
		this.leido = leido;
	}
	
	public DataNotificacion getDataNotificacion() {
		return new DataNotificacion(idNotificacion, texto, leido);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idNotificacion ^ (idNotificacion >>> 32));
		result = prime * result + (leido ? 1231 : 1237);
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
		Notificacion other = (Notificacion) obj;
		if (idNotificacion != other.idNotificacion)
			return false;
		if (leido != other.leido)
			return false;
		if (texto == null) {
			if (other.texto != null)
				return false;
		} else if (!texto.equals(other.texto))
			return false;
		return true;
	}
	   
}
