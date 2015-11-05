package negocio.implementacion;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.primefaces.model.UploadedFile;

import com.google.gson.Gson;

import dominio.AV;
import dominio.Mensaje;
import dominio.TipoUsuario;
import dominio.Usuario;
import dominio.datatypes.DataAV;
import dominio.datatypes.DataMensaje;
import dominio.datatypes.DataUsuario;
import dominio.datatypes.DataUsuarioSocial;
import exceptions.MensajeNoEncotrado;
import exceptions.UsuarioNoEncontrado;
import negocio.interfases.IControladorAV;
import negocio.interfases.IControladorUsuario;
import persistencia.interfases.IAvDAO;
import persistencia.interfases.ITipoDAO;
import persistencia.interfases.IUsuarioDAO;
import util.Imagenes;
import util.Mensajeria;

/**
 * Session Bean implementation class ControladorUsuario
 */
@Stateless
public class ControladorUsuario implements IControladorUsuario {

	@EJB
	private IUsuarioDAO usuarioDAO;
	@EJB
	private IAvDAO avDAO;
	@EJB
	IControladorAV cAV;

	@EJB
	private ITipoDAO tipoDao;
	
	
    public ControladorUsuario() { }

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
	public DataUsuario registrarUsuario(String nombre, String apellido, String nick, String pasword, String email, Date fechaNacimiento, UploadedFile file) 
	{
		DataUsuario dataUsuario = null;
		try {
			if(!existeUsuarioNick(nick) && !existeUsuarioEmail(email)) {
				String passEncriptado = seguridad.Encriptador.encriptar(pasword);
				Usuario usu = new Usuario(nombre, apellido, nick, passEncriptado, email, fechaNacimiento, Imagenes.convertirInputStreamToArrayByte(file), Imagenes.obtenerNombreImagen(file));
				usu.setTipoUsuario(tipoDao.obtenerTipoUsuarioParaLogin());
				dataUsuario = usuarioDAO.altaUsuario(usu).getDataUsuario();
			}
		} catch (Exception e) {
			e.fillInStackTrace();
		}
		return dataUsuario;
	}
	

	@Override
	public void modificarInfoUsuario(String nombre, String apellido, String nick, String password, String email,
			Date fechaNacimiento) {
		
		Usuario usu = usuarioDAO.buscarUsuario(nick);
		usu.setApellido(apellido);
		usu.setNombre(nombre);
		usu.setEmail(email);
		usu.setPassword(password);
		usu.setFechaNacimiento(fechaNacimiento);
		
		usuarioDAO.actualizarUsuario(usu);
	}

	@Override
	public DataUsuario login(String nickname, String password) 
	{
		DataUsuario dataUsuario = null;
		try {
			Usuario usuario = usuarioDAO.buscarUsuario(nickname);
			if(usuario != null && seguridad.Encriptador.sonIguales(password, usuario.getPassword()) ) {
				dataUsuario = usuario.getDataUsuario();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataUsuario;
	}
	
	@Override
	public DataUsuario loginSocial(String datos, String redSocial) 
	{
		DataUsuario usuarioLogueado = null;
		try {
			Gson gson = new Gson();
			DataUsuarioSocial usuarioSocial = gson.fromJson(datos, DataUsuarioSocial.class);
			Usuario usuario = usuarioDAO.buscarUsuarioSocial(usuarioSocial.id);
			if (usuario == null) {
				usuario = new Usuario(usuarioSocial);
				usuario.setTipoUsuario(tipoDao.obtenerTipoUsuarioSocial(redSocial));
				usuario = usuarioDAO.altaUsuario(usuario);
		        if (usuario != null) {
		        	StringBuilder mensaje = new StringBuilder();
		        	mensaje.append(String.format("<h2>Bienvenid@ a SAPo %s</h2>", usuario.getNombre()));
		        	mensaje.append(String.format("<p>Ingrese a %s</p>", "Cambiar esto por la URL Verdadera"));
		        	mensaje.append("<br/><br/>Saludos<br/>El Equipo de SAPo");
					new Mensajeria().enviarCorreo(usuario.getEmail(), "SAPo - Bienvenido", mensaje.toString());
				}	
			}
			if (usuario != null) {
				usuarioLogueado = usuario.getDataUsuario();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usuarioLogueado;
	}

	@Override
	public void eliminarUsuario(String nickname) {
		Usuario usu = usuarioDAO.buscarUsuario(nickname);
		
		List<AV> avs = usu.getAVs();
		
		for( AV av : avs ) {
			cAV.eliminarAV(av.getIdAV());
		}
		
		usuarioDAO.eliminarUsuario(usu);
	}
	
	@Override
	public List <DataAV> mostrarListaAv(String nickname) {
		Usuario usu = usuarioDAO.buscarUsuario(nickname);
		
		return usu.getDataUsuario().getAVs();	
		
	}

	@Override
	public boolean tienePermiso(String nickname, long idAV) {
		
		Usuario usu = usuarioDAO.buscarUsuario(nickname);
		List<AV> avs = usu.getAVs();
		List<AV> avComp = usu.getAVcompartidos();
		
		for( AV av : avs ) {
			if( av.getIdAV() == idAV ) {
				return true;
			}
		}
		
		for( AV av : avComp ) {
			if( av.getIdAV() == idAV ) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean enviarMensaje(String remitente, String destinatario, String mensaje) {
		if( existeUsuarioNick(remitente) && existeUsuarioNick(destinatario) ) {
			Usuario rem = usuarioDAO.buscarUsuario(remitente);
			Usuario dest = usuarioDAO.buscarUsuario(destinatario);
			Mensaje msj = new Mensaje(mensaje, new Date());
			
			msj.setDestinatario(dest);
			msj.setRemitente(rem);
			
			if( usuarioDAO.persistirMensaje(msj) ) {
				rem.addEnviado(msj);
				dest.addRecibido(msj);
				
				usuarioDAO.actualizarUsuario(rem);
				usuarioDAO.actualizarUsuario(dest);
				
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public void marcarMensajeComoLeido(long idMensaje) {
		Mensaje msj = usuarioDAO.buscarMensaje(idMensaje);
		msj.setLeido(true);
		usuarioDAO.actualizarMensaje(msj);
	}

	@Override
	public void eliminarMensajeRecibido(String usuario, long idMensaje) throws MensajeNoEncotrado {
		Usuario usu = usuarioDAO.buscarUsuario(usuario);
		List<Mensaje> rec = usu.getMensajesRecibidos();
		
		Mensaje msj = null;
		for( Mensaje m : rec ) {
			if( m.getId() == idMensaje ) {
				msj = m;
				break;
			}
		}
		if( msj != null ) {
			usu.removeRecibido(msj);
			usuarioDAO.actualizarUsuario(usu);
		} else {
			throw new exceptions.MensajeNoEncotrado();
		}
		
	}
	
	
	@Override
	public void eliminarMensajeEnviado(String usuario, long idMensaje) throws MensajeNoEncotrado {
		Usuario usu = usuarioDAO.buscarUsuario(usuario);
		List<Mensaje> rec = usu.getMensajesEnviados();
		
		Mensaje msj = null;
		for( Mensaje m : rec ) {
			if( m.getId() == idMensaje ) {
				msj = m;
				break;
			}
		}
		if( msj != null ) {
			usu.removeEnviado(msj);
			usuarioDAO.actualizarUsuario(usu);
		} else {
			throw new exceptions.MensajeNoEncotrado();
		}
	}
	
	@Override
	public List<DataMensaje> getMensajesEnviados(String usuario, int offset, int cant) throws UsuarioNoEncontrado {		
		Usuario usu = usuarioDAO.buscarUsuario(usuario);
		
		if( usu != null ) {
			List<Mensaje> mensajes = usu.getMensajesEnviados();
			List<Mensaje> msjs = new ArrayList<>();
			for( Mensaje m : mensajes ) {
				msjs.add(m);
			}
			return getMensajes(msjs, offset, cant);
		} else {
			throw new exceptions.UsuarioNoEncontrado();
		}		
	}

	@Override
	public List<DataMensaje> getMensajesEnviados(String usuario) throws UsuarioNoEncontrado {
			return getMensajesEnviados(usuario, 0, 0);
	}

	@Override
	public List<DataMensaje> getMensajesRecibidos(String usuario, int offset, int cant) throws UsuarioNoEncontrado {
		Usuario usu = usuarioDAO.buscarUsuario(usuario);
		
		if( usu != null ) {
			List<Mensaje> mensajes = usu.getMensajesRecibidos();
			List<Mensaje> msjs = new ArrayList<>();
			for( Mensaje m : mensajes ) {
				msjs.add(m);
			}
			return getMensajes(msjs, offset, cant);
		} else {
			throw new exceptions.UsuarioNoEncontrado();
		}	
	}

	@Override
	public List<DataMensaje> getMensajesRecibidos(String usuario) throws UsuarioNoEncontrado {
		return getMensajesRecibidos(usuario, 0, 0);
	}

	private List<DataMensaje> getMensajes(List<Mensaje> msjs, int offset, int cant) {
		List<DataMensaje> mensajes = new ArrayList<>();
		int ini = 0, fin = msjs.size();
		
		if( (offset >= 0 && offset < msjs.size() - 2) ) {
			ini = offset;
		} else {
			ini = 0;
		}
		
		if( cant > 0 ) {
			fin = cant;
		} else {
			fin = msjs.size() - 1;
		}
		
		int i = ini;
		while( (i <= fin)&&(i <= msjs.size() - 1) ) {
			mensajes.add(msjs.get(i).getDataMensaje());
			i++;
		}
		
		return mensajes;
	}
	@Override
	public DataMensaje getMensaje(long id) throws MensajeNoEncotrado {
		Mensaje msj = usuarioDAO.buscarMensaje(id);
		
		if( msj != null ) {
			return msj.getDataMensaje();
		} else {
			throw new exceptions.MensajeNoEncotrado();
		}
	}

	@Override
	public DataMensaje getMensajeEnviado(String nick, long id) throws MensajeNoEncotrado {
		Usuario usu = usuarioDAO.buscarUsuario(nick);
		DataMensaje res = null;
		for( Mensaje m : usu.getMensajesEnviados() ) {
			if( m.getId() == id ) {
				res = m.getDataMensaje();
				break;
			}
		}
		return res;
	}

	@Override
	public DataMensaje getMensajeRecibido(String nick, long id) throws MensajeNoEncotrado 
	{
		Usuario usu = usuarioDAO.buscarUsuario(nick);
		DataMensaje res = null;
		for( Mensaje m : usu.getMensajesRecibidos() ) {
			if( m.getId() == id ) {
				res = m.getDataMensaje();
				break;
			}
		}
		return res;
	}	
	
	
	@Override
	public boolean crearNuevoTipo(String descripcion) 
	{
		boolean seCrea = false;
		try {
			TipoUsuario tipoUsuario = tipoDao.altaTipoUsuario(new TipoUsuario(descripcion));
			System.out.println(tipoUsuario);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return seCrea;
	}
}
