package dominio.datatypes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dominio.AV;
import dominio.Usuario;

public class DataUsuario implements Serializable {
	
	private String nombre;
	private String apellido;
	private String nick;
	private String pasword;
	private String email;
	private Date fechaNacimiento;
	private List<DataAV> AVs = new ArrayList<>();
	private List<DataAV> AVsCompar = new ArrayList<>();
	
	public List<DataAV> getAVs() {
		return AVs;
	}
	public List<DataAV> getAVsCompar() {
		return AVsCompar;
	}
	public void setAVsCompar(List<DataAV> aVsCompar) {
		AVsCompar = aVsCompar;
	}
	public void setAVs(List<DataAV> aVs) {
		AVs = aVs;
	}
	public DataUsuario() {
	}
	public DataUsuario(String nombre, String apellido, String nick, String pasword, String email,
			Date fechaNacimiento,List <DataAV> avs,List <DataAV> avsComp) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.nick = nick;
		this.pasword = pasword;
		this.email = email;
		this.fechaNacimiento = fechaNacimiento;
		this.AVs=avs;
		this.AVsCompar=avsComp;
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
	public String getPasword() {
		return pasword;
	}
	public void setPasword(String pasword) {
		this.pasword = pasword;
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
		result = prime * result + ((apellido == null) ? 0 : apellido.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((fechaNacimiento == null) ? 0 : fechaNacimiento.hashCode());
		result = prime * result + ((nick == null) ? 0 : nick.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((pasword == null) ? 0 : pasword.hashCode());
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
		if (pasword == null) {
			if (other.pasword != null)
				return false;
		} else if (!pasword.equals(other.pasword))
			return false;
		return true;
	}
	
	
	
}
