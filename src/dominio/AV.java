package dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import dominio.datatypes.DataAV;
import dominio.datatypes.DataUsuario;

/**
 * 
 * TSI-JEE - Grupo 04 Santiago Callejas Marianela Rodriguez Lautaro Acosta
 * Noelia Gonzalez Bryan Ferreira
 *
 */
@Entity
@NamedQueries({ 
	@NamedQuery(name = "AV.findAll", query = "SELECT a FROM AV a"),
	@NamedQuery(name = "AV.obtenerNombresAV", query = "SELECT a.nombreAV FROM AV a JOIN a.usuarioCreador u WHERE u.idUsuario = :idUsuario "),
	@NamedQuery(name = "AV.buscarPorId", query = "SELECT a FROM AV a WHERE a.idAV =:idAV"),
	@NamedQuery(name = "AV.buscarPorNombre", query = "SELECT a FROM AV a WHERE a.nombreAV =:nombreAV")
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
	
	private String color;

	public AV() {
	}

	public AV(String nombreAV, Usuario usuarioCreador, String color) {
		super();
		this.nombreAV = nombreAV;
		this.usuarioCreador = usuarioCreador;
		this.color = color;
	}

	public DataAV getDataAV() {
		String nombreUsu = usuarioCreador.getNombre();
		List<DataUsuario> listDusu = new ArrayList<>();
		for (Usuario usus : usuariosCompartidos) {
			listDusu.add(usus.getDataUsuario());
		}
		DataAV dav = new DataAV(nombreAV, nombreUsu, color, listDusu);
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
	
	public void addUsuarioCompartido(Usuario usu) {
		this.usuariosCompartidos.add(usu);
	}
	
	public void removeUsuarioCompartido(Usuario usu) {
		this.usuariosCompartidos.remove(usu);
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
				+ ", usuariosCompartidos=" + usuariosCompartidos + "]";
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

}
