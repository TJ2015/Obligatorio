package persistencia;

import javax.ejb.Local;

import dominio.Usuario;

@Local
public interface IUsuarioDAO {

	public boolean altaUsuario(Usuario user);
	public Usuario buscarUsuario(String nick);
	public Usuario buscarUsuarioEmail(String email);
	public boolean actualizarUsuario(Usuario user);
	
}
