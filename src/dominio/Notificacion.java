package dominio;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Notificacion
 *
 */
@Entity

public class Notificacion implements Serializable {

	   
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idNotificacion;
	private String texto;
	private boolean leido;
	private static final long serialVersionUID = 1L;

	public Notificacion() {
		super();
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
   
}
