package dominio;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import dominio.datatypes.DataNota;

/**
 * Entity implementation class for Entity: Nota
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Nota.buscarPorId", query="SELECT n FROM Nota n WHERE n.idNota =:idNota"),
	@NamedQuery(name="Nota.getAll", query="SELECT n FROM Nota n")
})
public class Nota implements Serializable {

	   
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idNota;
	private String texto;
	private String usuario;
	
	private static final long serialVersionUID = 1L;

	public Nota() {
		super();
	}
	
	public Nota(String texto, String usuario) {
		this.texto = texto;
		this.usuario = usuario;
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
	
	public DataNota getDataNota() {
		return new DataNota(texto, usuario, idNota);
	}
   
}
