package dominio;

import java.io.Serializable;
import java.lang.String;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Usuario
 *
 */


@Entity
@NamedQueries({
	@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u"),
	@NamedQuery(name="Usuario.buscarPorNick", query="SELECT u FROM Usuario u WHERE u.nick = :nick"),
	@NamedQuery(name="Usuario.buscarPorEmail", query="SELECT u FROM Usuario u WHERE u.email = :email")
})
public class Usuario implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idUsuario;
	private String nombre;
	private String apellido;
	private String nick;
	private String password;
	private String email;
	private Date fechaNacimiento;
	
	@OneToMany(mappedBy = "usuarioCreador")
	@ElementCollection
	private List<AV> AVs;
	@ManyToMany(mappedBy = "usuariosCompartidos")
	@ElementCollection
	private List<AV> AVcompartidos;
	
	private static final long serialVersionUID = 1L;

	public Usuario() {
		super();
	}
	
	public Usuario(String nombre, String apellido, String nick, String pasword, String email, Date fechaNacimiento) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.nick = nick;
		this.password = pasword;
		this.email = email;
		this.fechaNacimiento = fechaNacimiento;
	}

	public long getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}  
	
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getApellido() {
		return this.apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}   
	public String getNick() {
		return this.nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}   
	
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}   
	public Date getFechaNacimiento() {
		return this.fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<AV> getAVs() {
		return AVs;
	}

	public void setAVs(List<AV> aVs) {
		AVs = aVs;
	}

	public List<AV> getAVcompartidos() {
		return AVcompartidos;
	}

	public void setAVcompartidos(List<AV> aVcompartidos) {
		AVcompartidos = aVcompartidos;
	}
   public void addAV(AV av){
	   AVs.add(av);
   }
}
