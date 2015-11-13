package dominio.datatypes;

import java.io.Serializable;

import com.google.gson.Gson;


public class DataChat implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String nickUsuario;
	private Long idAV;
	private String fecha;
	private byte[] imagen;
	
	public String getNickUsuario() {
		return nickUsuario;
	}

	public void setNickUsuario(String nickUsuario) {
		this.nickUsuario = nickUsuario;
	}

	public Long getIdAV() {
		return idAV;
	}

	public void setIdAV(Long idAV) {
		this.idAV = idAV;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public DataChat(){}
	
	public DataChat(String nickUsuario, long idAV, String fecha, byte[] imagen){
		this.nickUsuario = nickUsuario;
		this.idAV = idAV;
		this.fecha = fecha;
		this.imagen = imagen;
	}
	
	public String serializar(){
		String datosSerializados = null;
		try {
			Gson gson = new Gson();
			datosSerializados = gson.toJson(this);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return datosSerializados;
	}
}