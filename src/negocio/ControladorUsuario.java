package negocio;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dominio.Usuario;
import dominio.datatypes.DataUsuario;
import persistencia.IUsuarioDAO;

/**
 * Session Bean implementation class ControladorUsuario
 */
@Stateless
public class ControladorUsuario implements IControladorUsuario {

	@EJB
	private IUsuarioDAO usuarioDAO;
    /**
     * Default constructor. 
     */
    public ControladorUsuario() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public boolean existeUsuarioNick(String nickname) {
		Usuario usu = usuarioDAO.buscarUsuario(nickname);
		return (usu != null);
	}

	@Override
	public boolean existeUsuarioEmail(String email) {
		Usuario usu = usuarioDAO.buscarUsuarioEmail(email);
		return (usu != null);
	}
	
	@Override
	public boolean registrarUsuario(String nombre, String apellido, String nick, String pasword, String email,
			Date fechaNacimiento) {
		if( existeUsuarioNick(nick)||existeUsuarioEmail(email)) {
			return false;
		} else {
			Usuario usu = new Usuario(nombre, apellido, nick, pasword, email, fechaNacimiento);
			return usuarioDAO.altaUsuario(usu);
		}
	}

	@Override
	public void modificarInfoUsuario(String nombre, String apellido, String nick, String pasword, String email,
			Date fechaNacimiento) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean login(String nickname, String password) {
		Usuario usu = usuarioDAO.buscarUsuario(nickname);
		
		if ( usu != null ) {
			if( usu.getPasword().equals(password) ) {
				return true;
			}
		} 
		
		return false;
	}

	@Override
	public void eliminarUsuario(String nickname) {
		// TODO Auto-generated method stub
		
	}

}
