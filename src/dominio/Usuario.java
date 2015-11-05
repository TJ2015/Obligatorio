package dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import dominio.datatypes.DataAV;
import dominio.datatypes.DataUsuario;
import dominio.datatypes.DataUsuarioSocial;

@Entity
@NamedQueries({ @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
		@NamedQuery(name = "Usuario.buscarPorNick", query = "SELECT u FROM Usuario u WHERE u.nick = :nick"),
		@NamedQuery(name = "Usuario.buscarPorEmail", query = "SELECT u FROM Usuario u WHERE u.email = :email"),
		@NamedQuery(name = "Usuario.buscarPorIdSocial", query = "SELECT u FROM Usuario u WHERE u.idSocial = :idSocial") })
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

	private String idSocial;

	@ManyToOne
	private TipoUsuario tipoUsuario;

	@OneToMany
	@ElementCollection
	@JoinTable(name = "av_usuarioCreador", joinColumns = @JoinColumn(name = "usuarioCreador") , inverseJoinColumns = @JoinColumn(name = "idUsuario", referencedColumnName = "idAV") ) // REVISAR
	private List<AV> AVs = new ArrayList<>();

	@ManyToMany
	@ElementCollection
	@JoinTable(name = "usuario_avcompartidos")
	private List<AV> AVcompartidos;

	@OneToMany
	@ElementCollection
	@JoinTable(name = "us_msj_enviados")
	private List<Mensaje> mensajesEnviados;

	@OneToMany
	@ElementCollection
	@JoinTable(name = "us_msj_recibidos")
	private List<Mensaje> mensajesRecibidos;

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

	public Usuario(DataUsuarioSocial usuarioSocial) {
		super();
		this.nombre = usuarioSocial.first_name;
		this.apellido = usuarioSocial.last_name;
		this.email = usuarioSocial.email;
		this.fechaNacimiento = new Date();
		this.idSocial = usuarioSocial.id;
	}

	public DataUsuario getDataUsuario() {
		DataUsuario dataUsuario = null;
		List<DataAV> lDataAlmacen = null;
		List<DataAV> lDataCompartidos = null;
		try {
			if (AVs != null) {
				lDataAlmacen = new ArrayList<>();
				for (AV almacen : AVs) {
					lDataAlmacen.add(almacen.getDataAV());
				}
			}
			if (AVcompartidos != null) {
				lDataCompartidos = new ArrayList<>();
				for (AV avs : AVcompartidos) {
					lDataCompartidos.add(avs.getDataAV());
				}
			}
			dataUsuario = new DataUsuario(nombre, apellido, nick, password, email, fechaNacimiento, lDataAlmacen,
					lDataCompartidos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataUsuario;
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

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
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

	public void addAV(AV av) {
		AVs.add(av);
	}

	public void removeAVCompartido(AV av) {
		this.AVcompartidos.remove(av);
	}

	public void removeAV(AV av) {
		this.AVs.remove(av);
	}

	public List<Mensaje> getMensajesEnviados() {
		return mensajesEnviados;
	}

	public void setMensajesEnviados(List<Mensaje> mensajesEnviados) {
		this.mensajesEnviados = mensajesEnviados;
	}

	public List<Mensaje> getMensajesRecibidos() {
		return mensajesRecibidos;
	}

	public void setMensajesRecibidos(List<Mensaje> mensajesRecibidos) {
		this.mensajesRecibidos = mensajesRecibidos;
	}

	public void addEnviado(Mensaje msj) {
		this.mensajesEnviados.add(msj);
	}

	public void removeEnviado(Mensaje msj) {
		this.mensajesEnviados.remove(msj);
	}

	public void addRecibido(Mensaje msj) {
		this.mensajesRecibidos.add(msj);
	}

	public void removeRecibido(Mensaje msj) {
		this.mensajesRecibidos.remove(msj);
	}

	public String getIdSocial() {
		return idSocial;
	}

	public void setIdSocial(String idSocial) {
		this.idSocial = idSocial;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apellido == null) ? 0 : apellido.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((fechaNacimiento == null) ? 0 : fechaNacimiento.hashCode());
		result = prime * result + ((nick == null) ? 0 : nick.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
		Usuario other = (Usuario) obj;
		if (apellido == null) {
			if (other.apellido != null)
				return false;
		} else if (!apellido.equals(other.apellido))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (fechaNacimiento == null) {
			if (other.fechaNacimiento != null)
				return false;
		} else if (!fechaNacimiento.equals(other.fechaNacimiento))
			return false;
		if (nick == null) {
			if (other.nick != null)
				return false;
		} else if (!nick.equals(other.nick))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}
	
}
