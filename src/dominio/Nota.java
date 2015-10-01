package dominio;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Nota
 *
 */
@Entity

public class Nota implements Serializable {

	   
	@Id
	private long idNota;
	private String texto;
	private Usuario usuario;
	private static final long serialVersionUID = 1L;

	public Nota() {
		super();
	}
	
	public long getIdNota() {
		return this.idNota;
	}

	public void setIdNota(long idNota) {
		this.idNota = idNota;
	}   
	public String getTexto() {
		return this.texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}   
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
   
}
