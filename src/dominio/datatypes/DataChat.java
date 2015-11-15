package dominio.datatypes;

import java.io.Serializable;

import com.google.gson.Gson;


public class DataChat implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String nickUsuario;
	private Long idAV;
	private String fecha;
	
	private String mensaje;
	private String nombreCompleto;
	private boolean soyYo;
	
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

	
	
	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	
	public boolean isSoyYo() {
		return soyYo;
	}

	public void setSoyYo(boolean soyYo) {
		this.soyYo = soyYo;
	}	
	
	public DataChat(){}
	
	public DataChat(String nickUsuario, String nombre, long idAV, String fecha){
		this.nickUsuario = nickUsuario;
		this.nombreCompleto = nombre;
		this.idAV = idAV;
		this.fecha = fecha;
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
