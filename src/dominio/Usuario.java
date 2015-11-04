package dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
@NamedQueries({
	@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u"),
	@NamedQuery(name="Usuario.buscarPorNick", query="SELECT u FROM Usuario u WHERE u.nick = :nick"),
	@NamedQuery(name="Usuario.buscarPorEmail", query="SELECT u FROM Usuario u WHERE u.email = :email"),
	@NamedQuery(name="Usuario.buscarPorIdSocial", query="SELECT u FROM Usuario u WHERE u.idSocial = :idSocial")
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
	
	@Column(length=1294967295)
	private byte[] bytesImagen;
	private String nombreImagen;
	
	private String idSocial;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private TipoUsuario tipoUsuario;
	
	@OneToMany
	@ElementCollection
	@JoinTable(name = "av_usuarioCreador",
		joinColumns = @JoinColumn(name = "usuarioCreador") ,
		inverseJoinColumns = @JoinColumn (name = "idUsuario", referencedColumnName ="idAV"))//REVISAR
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

	public Usuario() 
	{
		super();
	}
	
	public Usuario(String nombre, String apellido, String nick, String pasword, String email, Date fechaNacimiento, byte[] bytesImagen, String nombreImagen) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.nick = nick;
		this.password = pasword;
		this.email = email;
		this.fechaNacimiento = fechaNacimiento;
		this.bytesImagen = bytesImagen;
		this.nombreImagen = nombreImagen;
	}
	
	public Usuario(DataUsuarioSocial usuarioSocial) {
		super();
		this.nombre = usuarioSocial.first_name;
		this.apellido = usuarioSocial.last_name;
		this.email = usuarioSocial.email;
		this.fechaNacimiento = new Date();
		this.idSocial = usuarioSocial.id;
	}

	public DataUsuario getDataUsuario() 
	{
		DataUsuario dataUsuario = null;
		List<DataAV> lDataAlmacen = null;
		List<DataAV>lDataCompartidos = null;
		try {
			if (AVs != null) {
				lDataAlmacen = new ArrayList<>();
				for(AV almacen:AVs){
					lDataAlmacen.add(almacen.getDataAV());
				}
			}
			if (AVcompartidos != null) {
				lDataCompartidos = new ArrayList<>();
				for(AV avs:AVcompartidos){
					lDataCompartidos.add(avs.getDataAV());
				}
			}
			dataUsuario = new DataUsuario(nombre, apellido, nick, password, email, fechaNacimiento, lDataAlmacen, lDataCompartidos);
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
	public void addAV(AV av){
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

	public byte[] getBytesImagen() {
		return bytesImagen;
	}

	public void setBytesImagen(byte[] bytesImagen) {
		this.bytesImagen = bytesImagen;
	}

	public String getNombreImagen() {
		return nombreImagen;
	}

	public void setNombreImagen(String nombreImagen) {
		this.nombreImagen = nombreImagen;
	}
	
}
