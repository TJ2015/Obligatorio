package negocio;

import java.util.Date;

import javax.ejb.Local;

import dominio.datatypes.DataUsuario;

@Local
public interface IControladorUsuario {
	
	public boolean existeUsuario(String nickname, String email);
	public DataUsuario registrarUsuario(String nombre, String apellido, String nick, String pasword, String email, Date fechaNacimiento);
	public void modificarInfoUsuario(String nombre, String apellido, String nick, String pasword, String email, Date fechaNacimiento);
	public boolean login(String nickname, String password);
	
}