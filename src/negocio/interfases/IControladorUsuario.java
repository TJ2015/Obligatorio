package negocio.interfases;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import dominio.datatypes.DataAV;
import dominio.datatypes.DataMensaje;
import exceptions.MensajeNoEncotrado;
import exceptions.UsuarioNoEncontrado;


@Local
public interface IControladorUsuario {
	
	public boolean existeUsuarioNick(String nickname);
	public boolean existeUsuarioEmail(String nickname);
	public boolean registrarUsuario(String nombre, String apellido, String nick, String password, String email, Date fechaNacimiento);
	public void modificarInfoUsuario(String nombre, String apellido, String nick, String password, String email, Date fechaNacimiento);
	public boolean login(String nickname, String password);
	public void eliminarUsuario(String nickname);
	public List <DataAV> mostrarListaAv(String nickname);
	public boolean tienePermiso(String nickname, long idAV);
	
	public boolean enviarMensaje(String remitente, String destinatario, String mensaje );
	public void marcarMensajeComoLeido(long idMensaje);
	public void eliminarMensajeRecibido(String usuario, long idMensaje) throws MensajeNoEncotrado;
	public void eliminarMensajeEnviado(String usuario, long idMensaje) throws MensajeNoEncotrado;
	public List<DataMensaje> getMensajesEnviados(String usuario, int offset, int cant) throws UsuarioNoEncontrado;
	public List<DataMensaje> getMensajesEnviados(String usuario) throws UsuarioNoEncontrado;
	public List<DataMensaje> getMensajesRecibidos(String usuario, int offset, int cant) throws UsuarioNoEncontrado;
	public List<DataMensaje> getMensajesRecibidos(String usuario) throws UsuarioNoEncontrado;
	public DataMensaje getMensaje(long id) throws MensajeNoEncotrado;
	public DataMensaje getMensajeEnviado(String nick, long id) throws MensajeNoEncotrado;
	public DataMensaje getMensajeRecibido(String nick, long id) throws MensajeNoEncotrado;
	
	public boolean crearNuevoTipo(String descripcion);
	
}