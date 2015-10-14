package dominio.datatypes;

import java.util.ArrayList;
import java.util.List;

import dominio.datatypes.DataCategoria;
//import dominio.DataNota;
import dominio.datatypes.DataUsuario;

public class DataAV {
	
	private long idAV;
	private String nombreAV;
	private String nickname;
	private List<DataUsuario> usuariosCompartidos;
	//private List<DataNota> notas;
	private List<DataCategoria> categorias=new ArrayList<>();
	private static final long serialVersionUID = 1L;


	public DataAV(String nombreAV, String nickname,List<DataCategoria> categorias) {
		this.nombreAV = nombreAV;
		this.nickname = nickname;
		this.categorias = new ArrayList<>();
		
	}
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public long getIdAV() {
		return idAV;
	}

	public List<DataCategoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<DataCategoria> categorias) {
		this.categorias = categorias;
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

	

	/*public List<DataNota> getNotas() {
		return notas;
	}

	public void setNotas(List<DataNota> notas) {
		this.notas = notas;
	}*/
	
}
