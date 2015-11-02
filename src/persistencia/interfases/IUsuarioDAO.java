package persistencia.interfases;

import javax.ejb.Local;

import dominio.Mensaje;
import dominio.Usuario;

@Local
public interface IUsuarioDAO {

	public Usuario altaUsuario(Usuario user);
		
	public Usuario buscarUsuario(String nick);
	
	public Usuario buscarUsuarioSocial(String idSocial);
	
	public Usuario buscarUsuarioEmail(String email);
	public boolean actualizarUsuario(Usuario user);
	public void eliminarUsuario(Usuario usu);
	public boolean persistirMensaje(Mensaje msj);
	public boolean actualizarMensaje(Mensaje msj);
	public boolean eliminarMensaje(Mensaje msj);
	public Mensaje buscarMensaje(long idMensaje);

}
