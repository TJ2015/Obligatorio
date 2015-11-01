package dominio;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
	@NamedQuery(name="TipoUsuario.findAll", query="SELECT u FROM TipoUsuario u"),
	@NamedQuery(name="TipoUsuario.obtenerTipoUsuarioParaLogin", query="SELECT u FROM TipoUsuario u WHERE u.idTipoUsuario = :idTipoUsuario")
})
public class TipoUsuario implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idTipoUsuario;
	private String descripcion;
	
	public TipoUsuario(){}
	
	public TipoUsuario(String descripcion)
	{
		this.descripcion = descripcion;
	}
	
	public TipoUsuario(long idTipoUsuario, String descripcion)
	{
		this.idTipoUsuario = idTipoUsuario;
		this.descripcion = descripcion;
	}
	
	public long getIdTipoUsuario() {
		return idTipoUsuario;
	}
	public void setIdTipoUsuario(long idTipoUsuario) {
		this.idTipoUsuario = idTipoUsuario;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "TipoUsuario [idTipoUsuario=" + idTipoUsuario + ", descripcion=" + descripcion + "]";
	}
	
	

}
