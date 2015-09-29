package dominio;

import java.io.Serializable;
import java.lang.String;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: AV
 *
 */
@Entity

public class AV implements Serializable {

	   
	@Id
	private long idAV;
	private String nombreAV;
	private Usuario usuarioCreador;
	private List<Nota> notas;
	private List<Categoria> categorias;
	private static final long serialVersionUID = 1L;

	public AV() {
		super();
	}   
	public long getIdAV() {
		return this.idAV;
	}

	public void setIdAV(long idAV) {
		this.idAV = idAV;
	}   
	public String getNombreAV() {
		return this.nombreAV;
	}

	public void setNombreAV(String nombreAV) {
		this.nombreAV = nombreAV;
	}   
	public Usuario getUsuarioCreador() {
		return this.usuarioCreador;
	}

	public void setUsuarioCreador(Usuario usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}
   
}
