package dominio.datatypes;

import java.io.Serializable;

public class DataNota implements Serializable {

	private static final long serialVersionUID = 1L;

	private String texto;
	private String usuario;
	private long idNota;

	public DataNota() {
	}

	public DataNota(String texto, String usuario, long idNota) {
		super();
		this.texto = texto;
		this.usuario = usuario;
		this.idNota = idNota;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public long getIdNota() {
		return idNota;
	}

	public void setIdNota(long idNota) {
		this.idNota = idNota;
	}

}
