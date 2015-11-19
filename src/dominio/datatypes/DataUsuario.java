package dominio.datatypes;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataUsuario implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nombre;
	private String apellido;
	private String nick;
	private String email;
	private Date fechaNacimiento;
	private List<String> AVs = new ArrayList<>();
	private List<String> AVsCompar = new ArrayList<>();
	private List <Long> AVsComparId = new ArrayList<>();
	private String nombreImagen;
	private InputStream imagenIS;
	private byte[] imagen;
	private Date fechaRegistro;
	private boolean membresia;

	public List<String> getAVs() {
		return AVs;
	}

	public List<String> getAVsCompar() {
		return AVsCompar;
	}

	public void setAVsCompar(List<String> aVsCompar) {
		AVsCompar = aVsCompar;
	}

	public void setAVs(List<String> aVs) {
		AVs = aVs;
	}

	public DataUsuario() {
	}

	public DataUsuario(String nombre, String apellido, String nick, String email, Date fechaNacimiento,
			List<String> aVs, List<String> aVsCompar, String nombreImagen, InputStream imagen, Date fechaRegistro,
			boolean membresia, List <Long> AVsComparId) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.nick = nick;
		this.email = email;
		this.fechaNacimiento = fechaNacimiento;
		AVs = aVs;
		AVsCompar = aVsCompar;
		this.nombreImagen = nombreImagen;
		this.fechaRegistro = fechaRegistro;
		this.membresia = membresia;
		this.AVsComparId=AVsComparId;
	}
	
	public List<Long> getAVsComparId() {
		return AVsComparId;
	}

	public void setAVsComparId(List<Long> aVsComparId) {
		AVsComparId = aVsComparId;
	}

	public DataUsuario(String nombre, String apellido, String nick, String email, Date fechaNacimiento,
			List<String> aVs, List<String> aVsCompar, String nombreImagen, byte[] imagen, Date fechaRegistro,
			boolean membresia) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.nick = nick;
		this.email = email;
		this.fechaNacimiento = fechaNacimiento;
		AVs = aVs;
		AVsCompar = aVsCompar;
		this.nombreImagen = nombreImagen;
		this.fechaRegistro = fechaRegistro;
		this.imagen = imagen;
		this.membresia = membresia;
	}
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((AVs == null) ? 0 : AVs.hashCode());
		result = prime * result + ((AVsCompar == null) ? 0 : AVsCompar.hashCode());
		result = prime * result + ((apellido == null) ? 0 : apellido.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((fechaNacimiento == null) ? 0 : fechaNacimiento.hashCode());
		result = prime * result + ((fechaRegistro == null) ? 0 : fechaRegistro.hashCode());
		result = prime * result + ((imagen == null) ? 0 : imagen.hashCode());
		result = prime * result + ((nick == null) ? 0 : nick.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((nombreImagen == null) ? 0 : nombreImagen.hashCode());
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
		DataUsuario other = (DataUsuario) obj;
		if (AVs == null) {
			if (other.AVs != null)
				return false;
		} else if (!AVs.equals(other.AVs))
			return false;
		if (AVsCompar == null) {
			if (other.AVsCompar != null)
				return false;
		} else if (!AVsCompar.equals(other.AVsCompar))
			return false;
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
		if (fechaRegistro == null) {
			if (other.fechaRegistro != null)
				return false;
		} else if (!fechaRegistro.equals(other.fechaRegistro))
			return false;
		if (imagen == null) {
			if (other.imagen != null)
				return false;
		} else if (!imagen.equals(other.imagen))
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
		if (nombreImagen == null) {
			if (other.nombreImagen != null)
				return false;
		} else if (!nombreImagen.equals(other.nombreImagen))
			return false;
		return true;
	}

	public InputStream getImagenIS() {
		return imagenIS;
	}

	public void setImagenIS(InputStream imagenIS) {
		this.imagenIS = imagenIS;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public String getNombreImagen() {
		return nombreImagen;
	}

	public void setNombreImagen(String nombreImagen) {
		this.nombreImagen = nombreImagen;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public boolean isMembresia() {
		return membresia;
	}

	public void setMembresia(boolean membresia) {
		this.membresia = membresia;
	}

}
