package dominio.datatypes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataAV implements Serializable {

	private static final long serialVersionUID = 1L;

	private long idAV;
	private String nombreAV;
	private String nickname;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	private List<DataUsuario> usuariosCompartidos;
	private List<DataCategoria> categorias;

	public DataAV(String nombreAV, String nickname) {
		this.nombreAV = nombreAV;
		this.nickname = nickname;
		this.setCategorias(new ArrayList<>());

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

	public List<DataCategoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<DataCategoria> categorias) {
		this.categorias = categorias;
	}

	public List<DataUsuario> getUsuariosCompartidos() {
		return usuariosCompartidos;
	}

	public void setUsuariosCompartidos(List<DataUsuario> usuariosCompartidos) {
		this.usuariosCompartidos = usuariosCompartidos;
	}

}
