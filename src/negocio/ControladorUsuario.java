package negocio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dominio.AV;
import dominio.Mensaje;
import dominio.Usuario;
import dominio.datatypes.DataAV;
import dominio.datatypes.DataMensaje;
import exceptions.MensajeNoEncotrado;
import exceptions.UsuarioNoEncontrado;
import persistencia.IAvDAO;
import persistencia.IUsuarioDAO;

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
			String passEncriptado = seguridad.Encriptador.encriptar(pasword);
			Usuario usu = new Usuario(nombre, apellido, nick, passEncriptado, email, fechaNacimiento);
			return usuarioDAO.altaUsuario(usu);
		}
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
	public boolean login(String nickname, String password) {
		Usuario usu = usuarioDAO.buscarUsuario(nickname);
		
		if ( usu != null ) {
			
			if( seguridad.Encriptador.sonIguales(password, usu.getPassword()) ) {
				return true;
			}
		} 
		
		return false;
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
	public DataMensaje getMensajeRecibido(String nick, long id) throws MensajeNoEncotrado {
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

}
