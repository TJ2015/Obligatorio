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
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	private List<DataUsuario> usuariosCompartidos;
	//private List<DataNota> notas;
	private List<DataCategoria> categorias;
	private static final long serialVersionUID = 1L;


	public DataAV(String nombreAV, String nickname) {
		this.nombreAV = nombreAV;
		this.nickname = nickname;
		this.categorias = new ArrayList<>();
		
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

	

	/*public List<DataNota> getNotas() {
		return notas;
	}

	public void setNotas(List<DataNota> notas) {
		this.notas = notas;
	}*/
	
}
