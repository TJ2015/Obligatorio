package dominio.log;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import dominio.datatypes.log.DataLog;


@Entity
public class Log implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private long idRefenecia;
	
	private String nickUsuario; // id del usuario que ejecuta la accion
	//@OneToOne
	private long idObjetivo;
	//@OneToOne
	private long idAccion;
	
	private String valor;
	
	private Date fecha;
	
	public Log() {
	}

	public Log(DataLog dataLog) {
		this.idRefenecia = dataLog.getIdReferencia();
		this.nickUsuario = dataLog.getNickUsuario();
		this.idAccion = dataLog.getIdAccion();
		this.idObjetivo = dataLog.getIdObjetivo();
		this.valor = dataLog.getValor();
		this.fecha = new Date();
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsuario() {
		return nickUsuario;
	}

	public void setUsuario(String nickUsuario) {
		this.nickUsuario = nickUsuario;
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

	public long getIdAccion() {
		return idAccion;
	}

	public void setIdAccion(long idAccion) {
		this.idAccion = idAccion;
	}

	public long getIdObjetivo() {
		return idObjetivo;
	}

	public void setIdObjetivo(long idObjetivo) {
		this.idObjetivo = idObjetivo;
	}

	public long getIdRefenecia() {
		return idRefenecia;
	}

	public void setIdRefenecia(long idRefenecia) {
		this.idRefenecia = idRefenecia;
	}

	


	
}
