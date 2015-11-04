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

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idNota ^ (idNota >>> 32));
		result = prime * result + ((texto == null) ? 0 : texto.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
		Nota other = (Nota) obj;
		if (idNota != other.idNota)
			return false;
		if (texto == null) {
			if (other.texto != null)
				return false;
		} else if (!texto.equals(other.texto))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}
	
}
