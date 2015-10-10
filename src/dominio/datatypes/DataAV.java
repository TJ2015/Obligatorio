package dominio.datatypes;

import java.util.ArrayList;
import java.util.List;

import dominio.datatypes.DataCategoria;
//import dominio.DataNota;
import dominio.datatypes.DataUsuario;

public class DataAV {
	
	private long idAV;
	private String nombreAV;
	private DataUsuario usuarioCreador;
	private List<DataUsuario> usuariosCompartidos;
	//private List<DataNota> notas;
	private List<DataCategoria> categorias;
	private static final long serialVersionUID = 1L;


	public DataAV(String nombreAV, DataUsuario usuarioCreador) {
		this.nombreAV = nombreAV;
		this.usuarioCreador = usuarioCreador;
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

	public DataUsuario getUsuarioCreador() {
		return usuarioCreador;
	}

	public void setUsuarioCreador(DataUsuario usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}

	/*public List<DataNota> getNotas() {
		return notas;
	}

	public void setNotas(List<DataNota> notas) {
		this.notas = notas;
	}*/
	
}
