package dominio.datatypes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import dominio.Usuario;

public class DataAV implements Serializable {

	private static final long serialVersionUID = 1L;

	private long idAV;
	private String nombreAV;
	private String nickname;
	private String color;
	private List<DataUsuario> usuariosCompartidos = new ArrayList<>();
	private List<DataCategoria> categorias;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public DataAV(String nombreAV, String nickname, String color, List<DataUsuario>ususComp) {
		this.nombreAV = nombreAV;
		this.nickname = nickname;
		this.usuariosCompartidos=ususComp;
		this.setColor(color);
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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

}
