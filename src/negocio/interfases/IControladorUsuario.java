package negocio.interfases;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import org.primefaces.model.UploadedFile;

import dominio.datatypes.DataAV;
import dominio.datatypes.DataAdministrador;
import dominio.datatypes.DataMensaje;
import dominio.datatypes.DataUsuario;
import exceptions.MensajeNoEncotrado;
import exceptions.NoExisteElUsuario;
import exceptions.UsuarioNoEncontrado;
import exceptions.YaExisteElUsuario;

@Local
public interface IControladorUsuario {

	public boolean existeUsuarioNick(String nickname);

	public boolean existeUsuarioEmail(String nickname);

	public DataUsuario registrarUsuario(String nombre, String apellido, String nick, String password, String email,
			Date fechaNacimiento, UploadedFile file);

	public void modificarInfoUsuario(String nombre, String apellido, String nick, String password, String email,
			Date fechaNacimiento);

	public DataUsuario login(String nickname, String password);

	public void eliminarUsuario(String nickname);

	public List<DataAV> mostrarListaAv(String nickname);

	public boolean tienePermiso(String nickname, long idAV);

	public boolean enviarMensaje(String remitente, String destinatario, String mensaje, String asunto);

	public void marcarMensajeComoLeido(long idMensaje);

	public void eliminarMensajeRecibido(String usuario, long idMensaje) throws MensajeNoEncotrado;

	public void eliminarMensajeEnviado(String usuario, long idMensaje) throws MensajeNoEncotrado;

	public void eliminarMensaje(long id) throws MensajeNoEncotrado;

	public List<DataMensaje> getMensajesEnviados(String usuario, int offset, int cant) throws UsuarioNoEncontrado;

	public List<DataMensaje> getMensajesEnviados(String usuario) throws UsuarioNoEncontrado;

	public List<DataMensaje> getMensajesRecibidos(String usuario, int offset, int cant) throws UsuarioNoEncontrado;

	public List<DataMensaje> getMensajesRecibidos(String usuario) throws UsuarioNoEncontrado;
	
	public List<DataMensaje> getMensajesRecibidosNoLeidos(String usuario) throws UsuarioNoEncontrado;

	public DataMensaje getMensaje(long id) throws MensajeNoEncotrado;

	public DataMensaje getMensajeEnviado(String nick, long id) throws MensajeNoEncotrado;

	public DataMensaje getMensajeRecibido(String nick, long id) throws MensajeNoEncotrado;

	public DataAdministrador loginAdmin(String nick, String pass) throws NoExisteElUsuario;

	public void registrarAdmin(String nick, String pass, String email) throws YaExisteElUsuario;

	public void eliminarAdmin(String nick) throws NoExisteElUsuario;

	public void modificarAdmin(String nick, String pass, String email) throws NoExisteElUsuario;

	public DataAdministrador getAdmin(String nick) throws NoExisteElUsuario;

	public boolean crearNuevoTipo(String descripcion);

	public DataUsuario loginSocial(String datos, String redSocial);

	public DataUsuario getUsuario(String nickname);

	public List<DataUsuario> getUsuarios();

	public int listaUsuariosPremium();
	
	public List<DataAV> mostrarListaAvComparidos(String nickname);

	void modificarImgUsuario(String nick, UploadedFile file);

}