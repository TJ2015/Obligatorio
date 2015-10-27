package dominio.datatypes;

import java.io.Serializable;
import java.util.Date;

public class DataMensaje implements Serializable {

	private long id;
	private String mensaje;
	private Date fecha;
	private String remitente;
	private String destinatario;
	private boolean leido;

	public DataMensaje() {
		super();
	}

	public DataMensaje(long id, String mensaje, Date fecha, String remitente, String destinatario, boolean leido) {
		super();
		this.id = id;
		this.mensaje = mensaje;
		this.fecha = fecha;
		this.remitente = remitente;
		this.destinatario = destinatario;
		this.leido = leido;
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

	public String getRemitente() {
		return remitente;
	}

	public void setRemitente(String remitente) {
		this.remitente = remitente;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public boolean isLeido() {
		return leido;
	}

	public void setLeido(boolean leido) {
		this.leido = leido;
	}

}
