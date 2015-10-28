package test;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import dominio.datatypes.DataMensaje;
import dominio.datatypes.DataNota;
import dominio.datatypes.DataNotificacion;
import exceptions.MensajeNoEncotrado;
import exceptions.UsuarioNoEncontrado;
import negocio.IControladorAV;
import negocio.IControladorInventario;
import negocio.IControladorUsuario;

@Local
@Stateless
public class TestControladores {
	
	@EJB
	IControladorAV cAV;
	@EJB
	IControladorUsuario cUsu;
	@EJB
	IControladorInventario cInv;
	
	
	public boolean testCrearNota() {
		try {
			String texto = "Nota de prueba";
			cAV.crearNota(texto, "test", 1);
			List<DataNota> notas = cAV.getNotas(1);
			
			for( DataNota dn : notas ) {
				if( dn.getTexto().equals(texto) )
					return true;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean testCrearNotifiacion() {
		String texto = "Notificacion de prueba";
		try {
			cAV.crearNotificacion(texto, 1);
			List<DataNotificacion> notis = cAV.getNotificaciones(1);
			
			for( DataNotificacion dn : notis ) {
				if( dn.getTexto().equals(texto) )
					return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;		
	}
	
	public boolean testEnviarMensaje() {
		String mensaje = "Mensaje de prueba";
		cUsu.enviarMensaje("test", "test2", mensaje);
		
		boolean OK = false;
		
		List<DataMensaje> enviados;
		try {
			enviados = cUsu.getMensajesEnviados("test");
		} catch (UsuarioNoEncontrado e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		List<DataMensaje> recibidos;
		try {
			recibidos = cUsu.getMensajesRecibidos("test2");
		} catch (UsuarioNoEncontrado e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		for( DataMensaje dm : enviados ) {
			if( dm.getMensaje().equals(mensaje)) {
				OK = true;
				break;
			}
		}
		
		for( DataMensaje dm : recibidos ) {
			if( dm.getMensaje().equals(mensaje)) {
				OK = OK&&true;
				break;
			}
		}
		
		return OK;
	}
	
	public boolean testMarcarMensajeComoLeido() {
		
		String mensaje = "Mensaje de prueba 2";
		cUsu.enviarMensaje("test", "test2", mensaje);
				
		List<DataMensaje> recibidos;
		try {
			recibidos = cUsu.getMensajesRecibidos("test2");
		} catch (UsuarioNoEncontrado e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		long id = 0;
		for( DataMensaje dm : recibidos ) {
			if( dm.getMensaje().equals(mensaje) ) {
				if(dm.isLeido()) {
					return false;
				}
				id = dm.getId();
				break;
			}
		}
		
		cUsu.marcarMensajeComoLeido(id);		
		DataMensaje dm = null;
		try {
			dm = cUsu.getMensaje(id);
		} catch (MensajeNoEncotrado e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		if( dm != null )
			return dm.isLeido();
		
		return false;
	}
	
	public boolean testEliminarMensajeRecibido() {
		
		String mensaje = "Mensaje de prueba 3";
		cUsu.enviarMensaje("test", "test2", mensaje);
				
		List<DataMensaje> recibidos;
		try {
			recibidos = cUsu.getMensajesRecibidos("test2");
		} catch (UsuarioNoEncontrado e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
		long id = 0;
		for( DataMensaje dm : recibidos ) {
			if( dm.getMensaje().equals(mensaje) ) {
				id = dm.getId();
				break;
			}
		}
		
		try {
			cUsu.eliminarMensajeRecibido("test2", id);
		} catch (MensajeNoEncotrado e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		DataMensaje dm = null;
		try {
			dm = cUsu.getMensaje(id);
		} catch (MensajeNoEncotrado e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return dm == null;
	}
	public boolean testEliminarMensajeEnviado() {
		
		String mensaje = "Mensaje de prueba 3";
		cUsu.enviarMensaje("test", "test2", mensaje);
				
		List<DataMensaje> recibidos;
		try {
			recibidos = cUsu.getMensajesRecibidos("test2");
		} catch (UsuarioNoEncontrado e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
		long id = 0;
		for( DataMensaje dm : recibidos ) {
			if( dm.getMensaje().equals(mensaje) ) {
				id = dm.getId();
				break;
			}
		}
		
		try {
			cUsu.eliminarMensajeRecibido("test2", id);
		} catch (MensajeNoEncotrado e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		DataMensaje dm = null;
		try {
			dm = cUsu.getMensaje(id);
		} catch (MensajeNoEncotrado e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return dm == null;
	}
}
