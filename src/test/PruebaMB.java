package test;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import dominio.datatypes.DataAdministrador;
import dominio.datatypes.DataMensaje;
import dominio.datatypes.DataNota;
import dominio.datatypes.DataNotificacion;
import dominio.datatypes.DataProducto;
import dominio.datatypes.DataProductoAComprar;
import exceptions.MensajeNoEncotrado;
import exceptions.NoExisteElAV;
import exceptions.NoExisteElProducto;
import exceptions.NoExisteElProductoAComprar;
import exceptions.NoExisteElUsuario;
import exceptions.UsuarioNoEncontrado;
import exceptions.YaExisteElProductoAComprar;
import exceptions.YaExisteElUsuario;
import negocio.interfases.IControladorAV;
import negocio.interfases.IControladorInventario;
import negocio.interfases.IControladorUsuario;

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
		String mensaje = "Mensaje de prueba 5";
		cUsu.enviarMensaje("test", "test2", mensaje);
		
		List<DataMensaje> enviados;
		try {
			enviados = cUsu.getMensajesEnviados("test");
		} catch (UsuarioNoEncontrado e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
		long id = 0;
		for( DataMensaje dm : enviados ) {
			if( dm.getMensaje().equals(mensaje) ) {
				id = dm.getId();
				break;
			}
		}
		try {
			cUsu.eliminarMensaje(id);
		} catch (MensajeNoEncotrado e) {
			return false;
		}
		DataMensaje dm = null;
		try {
			dm = cUsu.getMensaje(id);
		} catch (MensajeNoEncotrado e) {
			e.printStackTrace();
			return true;
		}
		
		return false;
	}
	
	public boolean testAgregarEnListaDeCompra() {
		boolean OK = false;
		try {
			cInv.agregarEnListaDeCompra(1, "testProd1", 5);
		} catch (NoExisteElAV | NoExisteElProducto | YaExisteElProductoAComprar e1) {
			// TODO Auto-generated catch block
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
				OK = true;
			}
		}
		
		try {
			cInv.agregarEnListaDeCompra(1, "testProd1", 5);
		} catch (NoExisteElAV | NoExisteElProducto | YaExisteElProductoAComprar e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return OK&&true;
		}
		return false;
	}
	
	public boolean testEliminarProductoDeListaDeCompra() {

		try {
			cInv.agregarEnListaDeCompra(1, "testProd2", 5);
		} catch (NoExisteElAV | NoExisteElProducto | YaExisteElProductoAComprar e1) {
			e1.printStackTrace();
			return false;
		}
		
		List<DataProductoAComprar> lista = null;
		try {
			lista = cInv.getListaDeCompra(1);
		} catch (NoExisteElAV e) {
			return false;
		}
		long id = 0;
		for( DataProductoAComprar dpac : lista ) {
			if( dpac.getProducto().getNombre().equals("testProd2") && dpac.getCantidad() == 5 ) {
				id = dpac.getId();
				break;
			}
		}
		if( id == 0 )
			return false;
		
		try {
			cInv.eliminarProductoDeListaDeCompra(1, id);
		} catch (NoExisteElAV | NoExisteElProductoAComprar e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		try {
			lista = cInv.getListaDeCompra(1);
		} catch (NoExisteElAV e) {
			return false;
		}
		for( DataProductoAComprar dpac : lista ) {
			if( dpac.getProducto().getNombre().equals("testProd2") && dpac.getCantidad() == 5 ) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean testProductoComprado() {
		String nomProd = "testProd2";
		int cant = 5;
		DataProducto dp = null;
		try {
			dp = cInv.getProducto(nomProd, 1);
		} catch (NoExisteElAV | NoExisteElProducto e2) {
			e2.printStackTrace();
			return false;
		}
		int stock = dp.getStock();
		
		try {
			cInv.agregarEnListaDeCompra(1, nomProd, cant);
		} catch (NoExisteElAV | NoExisteElProducto | YaExisteElProductoAComprar e1) {
			e1.printStackTrace();
			return false;
		}
		
		List<DataProductoAComprar> lista = null;
		try {
			lista = cInv.getListaDeCompra(1);
		} catch (NoExisteElAV e) {
			return false;
		}
		long id = 0;
		for( DataProductoAComprar dpac : lista ) {
			if( dpac.getProducto().getNombre().equals(nomProd) && dpac.getCantidad() == cant ) {
				id = dpac.getId();
				break;
			}
		}
		if( id == 0 )
			return false;
		
		try {
			cInv.productoComprado(1, id);
		} catch (NoExisteElAV | NoExisteElProductoAComprar e) {
			e.printStackTrace();
			return false;
		}
		
		try {
			lista = cInv.getListaDeCompra(1);
		} catch (NoExisteElAV e) {
			return false;
		}
		for( DataProductoAComprar dpac : lista ) {
			if( dpac.getProducto().getNombre().equals(nomProd) && dpac.getCantidad() == cant ) {
				return false;
			}
		}
		try {
			dp = cInv.getProducto(nomProd, 1);
		} catch (NoExisteElAV | NoExisteElProducto e) {
			e.printStackTrace();
			return false;
		}
		
		if( dp.getStock() == stock+cant )
			return true;
		
		return false;
	}
	
	public boolean testRegistrarAdmin() {

		String nick = "adminTest";
		String pass = "adminTest";
		
		try {
			cUsu.registrarAdmin(nick, pass, "admintest@example.org");
		} catch ( YaExisteElUsuario e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
		
		try {
			cUsu.getAdmin(nick);
		} catch (NoExisteElUsuario e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean testEliminarAdmin() {
		String nick = "adminTest";
		String pass = "adminTest";
		
		try {
			cUsu.registrarAdmin(nick, pass, "admintest@example.org");
		} catch (YaExisteElUsuario e) {
			//e.printStackTrace();
		}
		
		try {
			cUsu.eliminarAdmin(nick);
		} catch (NoExisteElUsuario e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
		
		try {
			cUsu.getAdmin(nick);
		} catch (NoExisteElUsuario e) {
			//e.printStackTrace();
			return true;
		}
				
		return false;
	}
	
	public boolean testLoginAdmin() {
		String nick = "adminTest";
		String pass = "adminTest";
		
		try {
			cUsu.registrarAdmin(nick, pass, "admintest@example.org");
		} catch (YaExisteElUsuario e) {
			//e.printStackTrace();
		}
		
		try {
			return cUsu.loginAdmin(nick, pass);
		} catch (NoExisteElUsuario e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean testModificarAdmin() {
		String email = "admintest@example.org";
		String pass = "adminTest";
		String nick = "adminTest";
		
		String passNuevo = "admintest2";
		String emailNuevo = "admintest_nuevo@example.org";
		
		try {
			cUsu.registrarAdmin(nick, pass, email);
		} catch (YaExisteElUsuario e) {
			//e.printStackTrace();
		}
		
		try {
			cUsu.modificarAdmin(nick, passNuevo, emailNuevo);
		} catch (NoExisteElUsuario e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
		boolean OK = false;
		DataAdministrador admin = null;
		
		try {
			admin = cUsu.getAdmin("adminTest");
			if( admin.getEmail().equals(emailNuevo) )
				OK = true;
		} catch (NoExisteElUsuario e) {
			e.printStackTrace();
			return false;
		}
		
		try {
			return OK&&cUsu.loginAdmin(nick, passNuevo);
		} catch (NoExisteElUsuario e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	public void testSuitTrucho() {
		tests = new LinkedHashMap<>();
		tests.put("Crear Nota", testCrearNota());
		tests.put("Crear Notificacion" , testCrearNotifiacion());
		tests.put("Enviar Mensaje" , testEnviarMensaje());
		tests.put("Marcar Mensaje Como Leido" , testMarcarMensajeComoLeido());
		tests.put("Eliminar Mensaje Recibido" , testEliminarMensajeRecibido());
		tests.put("Eliminar Mensaje Enviado" , testEliminarMensajeEnviado());
		tests.put("Eliminar Mensaje", testEliminarMensaje());
		tests.put("Agregar Producto A Lista De Compras", testAgregarEnListaDeCompra());
		tests.put("Eliminar Producto de Lista de Compras", testEliminarProductoDeListaDeCompra());
		tests.put("Producto Comprado", testProductoComprado());
		tests.put("Registrar Administrador", testRegistrarAdmin());
		tests.put("Eliminar Administrador", testEliminarAdmin());
		tests.put("Login Administrador", testLoginAdmin());
		tests.put("Modificar Administrador", testModificarAdmin());
		
		try {
			FacesContext.getCurrentInstance().getExternalContext().dispatch("/test_result.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
