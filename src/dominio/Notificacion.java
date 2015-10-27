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
	@NamedQuery(name="Notificacion.getAll", query="SELECT n FROM Notificacion n")
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
   
}
