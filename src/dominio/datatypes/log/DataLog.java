package dominio.datatypes.log;

import java.util.Date;

public class DataLog {
	
	private String nickUsuario;
	private String nombreUsuario;
	private String apellidoUsuario;
	private String nombreAccion;
	private String nombreObjetivo;
	private long idObjetivo;
	private long idAccion;
	private long idReferencia;
	private String valor;
	private Date fecha;
	private String descripcionGeneral;

	
	public DataLog() {

	}
	
	public DataLog(long idRefencia, String nickUsuario, String nombreAccion, String nombreObjetivo, String valor) {
		this.idReferencia = idRefencia;
		this.nickUsuario = nickUsuario;
		this.nombreAccion = nombreAccion;
		this.nombreObjetivo = nombreObjetivo;
		this.valor = valor;
		this.fecha = new Date();		 
	}
	
	public String getNickUsuario() {
		return nickUsuario;
	}
	
	public void setNickUsuario(String nickUsuario) {
		this.nickUsuario = nickUsuario;
	}
	
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getApellidoUsuario() {
		return apellidoUsuario;
	}

	public void setApellidoUsuario(String apellidoUsuario) {
		this.apellidoUsuario = apellidoUsuario;
	}

	public String getNombreAccion() {
		return nombreAccion;
	}

	public void setNombreAccion(String nombreAccion) {
		this.nombreAccion = nombreAccion;
	}

	public String getNombreObjetivo() {
		return nombreObjetivo;
	}

	public void setNombreObjetivo(String nombreObjetivo) {
		this.nombreObjetivo = nombreObjetivo;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getDescripcionGeneral() {
		return descripcionGeneral;
	}

	public void setDescripcionGeneral(String descripcionGeneral) {
		this.descripcionGeneral = descripcionGeneral;
	}

	public long getIdObjetivo() {
		return idObjetivo;
	}

	public void setIdObjetivo(long idObjetivo) {
		this.idObjetivo = idObjetivo;
	}

	public long getIdAccion() {
		return idAccion;
	}

	public void setIdAccion(long idAccion) {
		this.idAccion = idAccion;
	}

	public long getIdReferencia() {
		return idReferencia;
	}

	public void setIdReferencia(long idReferencia) {
		this.idReferencia = idReferencia;
	}


	
}
