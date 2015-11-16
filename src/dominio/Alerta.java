package dominio;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Alerta
 *
 */
@Entity
@Access(AccessType.FIELD)
public class Alerta implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	@Transient
	private Condicion cond;
	private Producto prod;
	
	public Alerta() {
	}
	
	public Alerta(Condicion cond, Producto prod) {
		this.cond = cond;
		this.prod = prod;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Condicion getCond() {
		return cond;
	}

	public void setCond(Condicion cond) {
		this.cond = cond;
	}

	public Producto getProd() {
		return prod;
	}

	public void setProd(Producto prod) {
		this.prod = prod;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cond == null) ? 0 : cond.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((prod == null) ? 0 : prod.hashCode());
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
		Alerta other = (Alerta) obj;
		if (cond == null) {
			if (other.cond != null)
				return false;
		} else if (!cond.equals(other.cond))
			return false;
		if (id != other.id)
			return false;
		if (prod == null) {
			if (other.prod != null)
				return false;
		} else if (!prod.equals(other.prod))
			return false;
		return true;
	}

	@Access(AccessType.PROPERTY)
	@Column(name = "atributos", nullable = false)
	public String getCondicion() {
		return cond.toString();
	}
	
	
	public void setCondicion(String condicion) {
		cond = util.Serializador.convertirCondicionAString(condicion);
	}
	
}
