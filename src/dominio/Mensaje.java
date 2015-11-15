package dominio;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import dominio.datatypes.DataMensaje;

/**
 * Entity implementation class for Entity: Mensaje
 *
 */
@NamedQueries({ 
	@NamedQuery(name = "Mensaje.buscarNoLeidos", query = "SELECT m FROM Mensaje m WHERE m.leido = false"),
	})
@Entity
public class Mensaje implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String mensaje;
	private String asunto;
	private Date fecha;
	@ManyToOne
	private Usuario remitente;
	@ManyToOne
	private Usuario destinatario;
	private boolean leido;
	
	public Mensaje() {
		super();
	}

	public Mensaje(String mensaje, Date fecha,String asunto) {
		super();
		this.mensaje = mensaje;
		this.fecha = fecha;
		this.asunto=asunto;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Usuario getRemitente() {
		return remitente;
	}

	public void setRemitente(Usuario remitente) {
		this.remitente = remitente;
	}

	public Usuario getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(Usuario destinatario) {
		this.destinatario = destinatario;
	}
	
	public boolean isLeido() {
		return leido;
	}

	public void setLeido(boolean leido) {
		this.leido = leido;
	}

	@Override
	public String toString() {
		return "Mensaje [id=" + id + ", texto=" + mensaje + ", fecha=" + fecha + ", remitente=" + remitente
				+ ", destinatario=" + destinatario + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((destinatario == null) ? 0 : destinatario.hashCode());
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((remitente == null) ? 0 : remitente.hashCode());
		result = prime * result + ((mensaje == null) ? 0 : mensaje.hashCode());
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
		Mensaje other = (Mensaje) obj;
		if (destinatario == null) {
			if (other.destinatario != null)
				return false;
		} else if (!destinatario.equals(other.destinatario))
			return false;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (id != other.id)
			return false;
		if (remitente == null) {
			if (other.remitente != null)
				return false;
		} else if (!remitente.equals(other.remitente))
			return false;
		if (mensaje == null) {
			if (other.mensaje != null)
				return false;
		} else if (!mensaje.equals(other.mensaje))
			return false;
		return true;
	}

	public DataMensaje getDataMensaje() {
		return new DataMensaje(id, mensaje, fecha, remitente.getNick(), destinatario.getNick(), leido, asunto);
	}

}
