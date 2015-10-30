package test;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import dominio.datatypes.DataMensaje;
import dominio.datatypes.DataNota;
import dominio.datatypes.DataNotificacion;
import dominio.datatypes.DataProductoAComprar;
import exceptions.MensajeNoEncotrado;
import exceptions.NoExisteElAV;
import exceptions.UsuarioNoEncontrado;
import negocio.IControladorAV;
import negocio.IControladorInventario;
import negocio.IControladorUsuario;

@ManagedBean
public class PruebaMB implements Serializable {

	private Map<String, Boolean> tests;
	@EJB
	IControladorAV cAV;
	@EJB
	IControladorUsuario cUsu;
	@EJB
	IControladorInventario cInv;

	public PruebaMB() {
		super();
	}
	
	public Map<String, Boolean> getTests() {
		return tests;
	}

	public void setTests(Map<String, Boolean> tests) {
		this.tests = tests;
	}
	
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
			dm = cUsu.getMensajeRecibido("test2", id);
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
			recibidos = cUsu.getMensajesEnviados("test");
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
			cUsu.eliminarMensajeEnviado("test", id);
		} catch (MensajeNoEncotrado e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		DataMensaje dm = null;
		try {
			dm = cUsu.getMensajeEnviado("test", id);
		} catch (MensajeNoEncotrado e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return dm == null;
	}
	
	public boolean testEliminarMensaje() {
		//TODO terminar
		return false;
	}
	
	public boolean testAgregarEnListaDeCompra() {
		try {
			cInv.agregarEnListaDeCompra(1, "testProd1", 5);
		} catch (NoExisteElAV e1) {
			e1.printStackTrace();
			return false;
		}
		
		List<DataProductoAComprar> lista = null;
		try {
			lista = cInv.getListaDeCompra(1);
		} catch (NoExisteElAV e) {
			return false;
		}
		
		for( DataProductoAComprar dpac : lista ) {
			if( dpac.getProducto().getNombre().equals("testProd1") && dpac.getCantidad() == 5 ) {
				return true;
			}
		}
		
		return false;
	}

	public void testSuitTrucho() {
		tests = new HashMap<>();
		tests.put("Crear Nota", testCrearNota());
		tests.put("Crear Notificacion" , testCrearNotifiacion());
		tests.put("Enviar Mensaje" , testEnviarMensaje());
		tests.put("Marcar Mensaje Como Leido" , testMarcarMensajeComoLeido());
		tests.put("Eliminar Mensaje Recibido" , testEliminarMensajeRecibido());
		tests.put("Eliminar Mensaje Enviado" , testEliminarMensajeEnviado());
		tests.put("Agregar Producto A Lista De Compras", testAgregarEnListaDeCompra());
		
		try {
			FacesContext.getCurrentInstance().getExternalContext().dispatch("/test_result.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
