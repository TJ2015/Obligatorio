package persistencia;

import javax.ejb.Local;

import dominio.Administrador;
import dominio.Mensaje;
import dominio.Usuario;

@Local
public interface IUsuarioDAO {

	public boolean altaUsuario(Usuario user);
	public Usuario buscarUsuario(String nick);
	public Usuario buscarUsuarioEmail(String email);
	public boolean actualizarUsuario(Usuario user);
	public void eliminarUsuario(Usuario usu);

	public boolean persistirMensaje(Mensaje msj);
	public boolean actualizarMensaje(Mensaje msj);
	public boolean eliminarMensaje(Mensaje msj);
	public Mensaje buscarMensaje(long idMensaje);
	
	public Administrador buscarAdmin(String nick);
	public Administrador buscarAdmin(long nick);
	public void persistirAdmin(Administrador admin);
	public void eliminarAdmin(Administrador admin);
	public void actualizarAdmin(Administrador admin);
	
}
