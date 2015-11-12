package dominio;

import java.io.Serializable;
import javax.persistence.*;

import dominio.datatypes.DataAdministrador;

/**
 * Entity implementation class for Entity: Administrador
 *
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "Administrador.buscarPorNick", query = "SELECT a FROM Administrador a WHERE a.nick = :nick") })
public class Administrador implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String nick;
	private String password;
	private String email;

	private static final long serialVersionUID = 1L;

	public Administrador() {
		super();
	}

	public Administrador(String nick, String password, String email) {
		super();
		this.nick = nick;
		this.password = password;
		this.email = email;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Administrador [id=" + id + ", nick=" + nick + ", password=" + password + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((nick == null) ? 0 : nick.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
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
		Administrador other = (Administrador) obj;
		if (id != other.id)
			return false;
		if (nick == null) {
			if (other.nick != null)
				return false;
		} else if (!nick.equals(other.nick))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

	public DataAdministrador getDataAdministrador() {
		return new DataAdministrador(id, nick, password, email);
	}

}
