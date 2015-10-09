package negocio;

import java.util.Date;

import javax.ejb.Stateless;

import dominio.datatypes.DataUsuario;

/**
 * Session Bean implementation class ControladorUsuario
 */
@Stateless
public class ControladorUsuario implements IControladorUsuario {

    /**
     * Default constructor. 
     */
    public ControladorUsuario() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public boolean existeUsuario(String nickname, String email) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DataUsuario registrarUsuario(String nombre, String apellido, String nick, String pasword, String email,
			Date fechaNacimiento) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void modificarInfoUsuario(String nombre, String apellido, String nick, String pasword, String email,
			Date fechaNacimiento) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean login(String nickname, String password) {
		// TODO Auto-generated method stub
		return false;
	}

}
