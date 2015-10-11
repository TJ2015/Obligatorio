package negocio;

import java.util.Date;

import javax.ejb.Local;


@Local
public interface IControladorUsuario {
	
	public boolean existeUsuarioNick(String nickname);
	public boolean existeUsuarioEmail(String nickname);
	public boolean registrarUsuario(String nombre, String apellido, String nick, String password, String email, Date fechaNacimiento);
	public void modificarInfoUsuario(String nombre, String apellido, String nick, String password, String email, Date fechaNacimiento);
	public boolean login(String nickname, String password);
	public void eliminarUsuario(String nickname);
	
}