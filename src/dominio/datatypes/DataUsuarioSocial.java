package dominio.datatypes;

import java.io.Serializable;

public class DataUsuarioSocial implements Serializable {

	private static final long serialVersionUID = 1L;

	public String id;
	public String first_name;
	public String last_name;
	public String email;
	private DataPictureSocial picture;
	
	public DataPictureSocial getPicture() {
		return picture;
	}

	public void setPicture(DataPictureSocial picture) {
		this.picture = picture;
	}

	public String getFirst_name() {
		return first_name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
