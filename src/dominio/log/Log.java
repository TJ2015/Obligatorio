package dominio.log;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Entity implementation class for Entity: Log
 *
 */
@Entity
public class Log implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String nickUsuario; // id del usuario que ejecuta la accion
	//@OneToOne
	private Objetivo objetivo;
	//@OneToOne
	private Accion accion;
	
	private String valor;
	
	private Date fecha;
	
	public Log() {
	}

	public Log(String nickUsuario, Objetivo objetivo, Accion accion) {
		this.nickUsuario = nickUsuario;
		this.objetivo = objetivo;
		this.accion = accion;
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

	public Objetivo getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(Objetivo objetivo) {
		this.objetivo = objetivo;
	}

	public Accion getAccion() {
		return accion;
	}

	public void setAccion(Accion accion) {
		this.accion = accion;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accion == null) ? 0 : accion.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((objetivo == null) ? 0 : objetivo.hashCode());
		result = prime * result + ((nickUsuario == null) ? 0 : nickUsuario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Log other = (Log) obj;
		if (accion == null) {
			if (other.accion != null)
				return false;
		} else if (!accion.equals(other.accion))
			return false;
		if (id != other.id)
			return false;
		if (objetivo == null) {
			if (other.objetivo != null)
				return false;
		} else if (!objetivo.equals(other.objetivo))
			return false;
		if (nickUsuario == null) {
			if (other.nickUsuario != null)
				return false;
		} else if (!nickUsuario.equals(other.nickUsuario))
			return false;
		return true;
	}


	
}
