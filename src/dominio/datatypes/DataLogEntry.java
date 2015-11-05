package dominio.datatypes;

import java.io.Serializable;
import java.util.Date;

public class DataLogEntry implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private Date fecha;
	private String texto;

	public DataLogEntry() {
		super();
	}

	public DataLogEntry(long id, Date fecha, String texto) {
		super();
		this.id = id;
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

}
