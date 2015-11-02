package dominio.datatypes;

public class DataAdministrador {

	private long id;
	private String nick;
	private String password;
	private String email;

	public DataAdministrador() {
		super();
	}

	public DataAdministrador(long id, String nick, String password, String email) {
		super();
		this.id = id;
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

}
