package dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import dominio.datatypes.DataAV;

/**
 * 
 * Santiago Callejas
 * Marianela Rodriguez
 * Lautaro Acosta
 * Noelia Gonzalez
 * Bryan Ferreira
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name="AV.findAll", query="SELECT u FROM AV u"),
	@NamedQuery(name="AV.buscarPorId", query="SELECT av FROM AV av WHERE av.idAV =:idAV"),
	@NamedQuery(name="AV.buscarPorNombre", query="SELECT av FROM AV av WHERE av.nombreAV =:nombreAV")
})
public class AV implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idAV;
	private String nombreAV;
	@ManyToOne
	private Usuario usuarioCreador;
	@ElementCollection
	@ManyToMany
	@JoinTable(name = "av_usuarioscompartidos")
	private List<Usuario> usuariosCompartidos = new ArrayList<>();
	
	private static final long serialVersionUID = 1L;
	
	public AV() {
	}
	
	public AV(String nombreAV, Usuario usuarioCreador) {
		super();
		this.nombreAV = nombreAV;
		this.usuarioCreador = usuarioCreador;
	}

	public DataAV getDataAV() {
		String nombreUsu= usuarioCreador.getNombre();
		DataAV dav=new DataAV(nombreAV, nombreUsu);
		dav.setIdAV(idAV);
		return dav;
	}

	public long getIdAV() {
		return idAV;
	}

	public void setIdAV(long idAV) {
		this.idAV = idAV;
	}

	public String getNombreAV() {
		return nombreAV;
	}

	public void setNombreAV(String nombreAV) {
		this.nombreAV = nombreAV;
	}

	public Usuario getUsuarioCreador() {
		return usuarioCreador;
	}

	public void setUsuarioCreador(Usuario usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}
	
	public List<Usuario> getUsuariosCompartidos() {
		return usuariosCompartidos;
	}

	public void setUsuariosCompartidos(List<Usuario> usuariosCompartidos) {
		this.usuariosCompartidos = usuariosCompartidos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idAV ^ (idAV >>> 32));
		result = prime * result + ((nombreAV == null) ? 0 : nombreAV.hashCode());
		result = prime * result + ((usuarioCreador == null) ? 0 : usuarioCreador.hashCode());
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
		AV other = (AV) obj;
		if (idAV != other.idAV)
			return false;
		if (nombreAV == null) {
			if (other.nombreAV != null)
				return false;
		} else if (!nombreAV.equals(other.nombreAV))
			return false;
		if (usuarioCreador == null) {
			if (other.usuarioCreador != null)
				return false;
		} else if (!usuarioCreador.equals(other.usuarioCreador))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AV [idAV=" + idAV + ", nombreAV=" + nombreAV + ", usuarioCreador=" + usuarioCreador
				+ ", usuariosCompartidos=" + usuariosCompartidos + ", notas=" + notas + "]";
	}
	
}
