package test;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import com.sun.org.apache.xml.internal.resolver.Catalog;

import dominio.datatypes.DataAdministrador;
import dominio.datatypes.DataCategoria;
import dominio.datatypes.DataMensaje;
import dominio.datatypes.DataNota;
import dominio.datatypes.DataNotificacion;
import dominio.datatypes.DataProducto;
import dominio.datatypes.DataProductoAComprar;
import dominio.datatypes.DataUsuario;
import exceptions.MensajeNoEncotrado;
import exceptions.NoExisteElAV;
import exceptions.NoExisteElProducto;
import exceptions.NoExisteElProductoAComprar;
import exceptions.NoExisteElUsuario;
import exceptions.NoExisteLaCategoria;
import exceptions.NombreDeAVInvalido;
import exceptions.UsuarioNoEncontrado;
import exceptions.YaExisteElProductoAComprar;
import exceptions.YaExisteElUsuario;
import negocio.interfases.IControladorAV;
import negocio.interfases.IControladorInventario;
import negocio.interfases.IControladorUsuario;

@ManagedBean
public class PruebaMB implements Serializable {

	private Map<String, Boolean> tests;
	private boolean allok = true;
	private long ID_AV = 2;
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

	public boolean isAllok() {
		return allok;
	}

	public void setAllok(boolean allok) {
		this.allok = allok;
	}

	public void setTests() {
		cUsu.registrarUsuario("Test", "Run", "test", "test", "test@example.org", new Date());
		cUsu.registrarUsuario("Test", "Run 2", "test2", "test2", "test2@example.org", new Date());
		try {
			ID_AV = cAV.altaAV("testAV", "test");
		} catch (NombreDeAVInvalido e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		cInv.crearCategoria("testCat", ID_AV);
		try {
			cInv.crearProducto("testProd1", "producto de prueba 1", 111, "testCat", "attr1:val1;attr2:val2;", ID_AV, 10);
			cInv.crearProducto("testProd2", "producto de prueba 2", 222, "testCat", "attr1:val1;attr2:val2;attr3:val3;",
					ID_AV, 20);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void cleanTests() {
		util.DBUtil.eliminarTenant("test_testAV");
		cUsu.eliminarUsuario("test");
		cUsu.eliminarUsuario("test2");
	}

	public boolean testRegistrarUsuario() {
		DataUsuario du = cUsu.registrarUsuario("Test", "Run 4", "test4", "test4", "test4@example.org", new Date());

		if (du == null)
			return false;

		DataUsuario data = cUsu.getUsuario(du.getNick());

		if ((data != null))
			return true;

		return false;
	}

	public boolean testModificarInfoUsuario() {
		String nomN = "TestUser";
		String apeN = "Testing";
		String passN = "TestUser";
		String email = "testuser@example.com";
		Date fechaN = new Date();

		cUsu.modificarInfoUsuario(nomN, apeN, "test4", passN, email, fechaN);

		DataUsuario du = cUsu.getUsuario("test4");

		boolean OK = false;
		if (du == null)
			return false;

		if (du.getNombre().equals(nomN) && du.getApellido().equals(apeN) && du.getEmail().equals(email))
			OK = true;

		OK = OK && (cUsu.login("test4", passN) != null);

		return OK;
	}

	public boolean testEliminarUsuario() {

		cUsu.registrarUsuario("Test", "Run 3", "test3", "test3", "test3@example.org", new Date());
		long idAV = 0;
		try {
			idAV = cAV.altaAV("testAV2", "test3");
		} catch (NombreDeAVInvalido e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		cUsu.eliminarUsuario("test3");

		if (!cUsu.existeUsuarioNick("test3") && !cAV.existeAV(idAV))
			return true;

		return false;
	}

	public boolean testCrearNota() {
		try {
			String texto = "Nota de prueba";
			cAV.crearNota(texto, "test", ID_AV);
			List<DataNota> notas = cAV.getNotas(ID_AV);

			for (DataNota dn : notas) {
				if (dn.getTexto().equals(texto))
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
			cAV.crearNotificacion(texto, ID_AV);
			List<DataNotificacion> notis = cAV.getNotificaciones(ID_AV);

			for (DataNotificacion dn : notis) {
				if (dn.getTexto().equals(texto))
					return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean testEnviarMensaje() {
		cUsu.registrarUsuario("Test", "Run 2", "test2", "test2", "test2@example.org", new Date());
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

		for (DataMensaje dm : enviados) {
			if (dm.getMensaje().equals(mensaje)) {
				OK = true;
				break;
			}
		}

		for (DataMensaje dm : recibidos) {
			if (dm.getMensaje().equals(mensaje)) {
				OK = OK && true;
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
		for (DataMensaje dm : recibidos) {
			if (dm.getMensaje().equals(mensaje)) {
				if (dm.isLeido()) {
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
		if (dm != null)
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
		for (DataMensaje dm : recibidos) {
			if (dm.getMensaje().equals(mensaje)) {
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
		for (DataMensaje dm : recibidos) {
			if (dm.getMensaje().equals(mensaje)) {
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
		for (DataMensaje dm : enviados) {
			if (dm.getMensaje().equals(mensaje)) {
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
			cInv.agregarEnListaDeCompra(ID_AV, "testProd1", 5);
		} catch (NoExisteElAV | NoExisteElProducto | YaExisteElProductoAComprar e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}

		List<DataProductoAComprar> lista = null;
		try {
			lista = cInv.getListaDeCompra(ID_AV);
		} catch (NoExisteElAV e) {
			return false;
		}

		for (DataProductoAComprar dpac : lista) {
			if (dpac.getProducto().getNombre().equals("testProd1") && dpac.getCantidad() == 5) {
				OK = true;
			}
		}

		try {
			cInv.agregarEnListaDeCompra(ID_AV, "testProd1", 5);
		} catch (NoExisteElAV | NoExisteElProducto | YaExisteElProductoAComprar e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return OK && true;
		}
		return false;
	}

	public boolean testEliminarProductoDeListaDeCompra() {

		try {
			cInv.agregarEnListaDeCompra(ID_AV, "testProd2", 5);
		} catch (NoExisteElAV | NoExisteElProducto | YaExisteElProductoAComprar e1) {
			e1.printStackTrace();
			return false;
		}

		List<DataProductoAComprar> lista = null;
		try {
			lista = cInv.getListaDeCompra(ID_AV);
		} catch (NoExisteElAV e) {
			return false;
		}
		long id = 0;
		for (DataProductoAComprar dpac : lista) {
			if (dpac.getProducto().getNombre().equals("testProd2") && dpac.getCantidad() == 5) {
				id = dpac.getId();
				break;
			}
		}
		if (id == 0)
			return false;

		try {
			cInv.eliminarProductoDeListaDeCompra(ID_AV, id);
		} catch (NoExisteElAV | NoExisteElProductoAComprar e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		try {
			lista = cInv.getListaDeCompra(ID_AV);
		} catch (NoExisteElAV e) {
			return false;
		}
		for (DataProductoAComprar dpac : lista) {
			if (dpac.getProducto().getNombre().equals("testProd2") && dpac.getCantidad() == 5) {
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
			dp = cInv.getProducto(nomProd, ID_AV);
		} catch (NoExisteElAV | NoExisteElProducto e2) {
			e2.printStackTrace();
			return false;
		}
		int stock = dp.getStock();

		try {
			cInv.agregarEnListaDeCompra(ID_AV, nomProd, cant);
		} catch (NoExisteElAV | NoExisteElProducto | YaExisteElProductoAComprar e1) {
			e1.printStackTrace();
			return false;
		}

		List<DataProductoAComprar> lista = null;
		try {
			lista = cInv.getListaDeCompra(ID_AV);
		} catch (NoExisteElAV e) {
			return false;
		}
		long id = 0;
		for (DataProductoAComprar dpac : lista) {
			if (dpac.getProducto().getNombre().equals(nomProd) && dpac.getCantidad() == cant) {
				id = dpac.getId();
				break;
			}
		}
		if (id == 0)
			return false;

		try {
			cInv.productoComprado(ID_AV, id);
		} catch (NoExisteElAV | NoExisteElProductoAComprar e) {
			e.printStackTrace();
			return false;
		}

		try {
			lista = cInv.getListaDeCompra(ID_AV);
		} catch (NoExisteElAV e) {
			return false;
		}
		for (DataProductoAComprar dpac : lista) {
			if (dpac.getProducto().getNombre().equals(nomProd) && dpac.getCantidad() == cant) {
				return false;
			}
		}
		try {
			dp = cInv.getProducto(nomProd, ID_AV);
		} catch (NoExisteElAV | NoExisteElProducto e) {
			e.printStackTrace();
			return false;
		}

		if (dp.getStock() == stock + cant)
			return true;

		return false;
	}

	public boolean testRegistrarAdmin() {

		String nick = "adminTest";
		String pass = "adminTest";

		try {
			cUsu.registrarAdmin(nick, pass, "admintest@example.org");
		} catch (YaExisteElUsuario e1) {
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
			// e.printStackTrace();
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
			// e.printStackTrace();
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
			// e.printStackTrace();
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
			// e.printStackTrace();
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
			if (admin.getEmail().equals(emailNuevo))
				OK = true;
		} catch (NoExisteElUsuario e) {
			e.printStackTrace();
			return false;
		}

		try {
			return OK && cUsu.loginAdmin(nick, passNuevo);
		} catch (NoExisteElUsuario e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean testCrearCategoria() {
		String nombre = "catTest2";
		if( !cInv.crearCategoria(nombre, ID_AV) )
			return false;
		
		DataCategoria cat = null;
		
		try {
			cat = cInv.getCategoria(nombre, ID_AV);
		} catch (NoExisteElAV e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		if( (cat == null)&&(!cat.getNombre().equals(nombre)) )
			return false;
		
		if( !cInv.crearCategoria(nombre, 0) )
			return false;
		
		cat = null;
		
		try {
			cat = cInv.getCategoria(nombre, 0);
		} catch (NoExisteElAV e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		if( (cat == null)&&(!cat.getNombre().equals(nombre)) )
			return false;
		
		return true;
	}
	
	public boolean modificarNombreCategoria() {
		
		cInv.crearCategoria("categoriaPrueba", ID_AV);
		try {
			cInv.crearProducto("testProd3", "testProd3", 123, "categoriaPrueba", "attr1:val1;attr2:val2;", ID_AV, 10);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		try {
			cInv.modificarNombreCategoria("categoriaPrueba", ID_AV, "categoriaNombreNuevo");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		try {
			if( cInv.getCategoria("categoriaPrueba", ID_AV) != null )
				return false;
		} catch (NoExisteElAV e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		DataCategoria cat = null;
		try {
			cat = cInv.getCategoria("categoriaNombreNuevo", ID_AV);
		} catch (NoExisteElAV e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if( cat == null )
			return false;
		
		if( (cat.getProductos().size() > 0) && cat.getProductos().get(0).getNombre().equals("testProd3") )
			return true;
		
		return false;
	}
	
	public boolean testEliminarCategoria() {
		
		cInv.crearCategoria("categoriaPrueba2", ID_AV);
		try {
			cInv.eliminarCategoria("categoriaPrueba2", ID_AV);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DataCategoria cat = null;
		try {
			cat = cInv.getCategoria("categoriaPrueba2", ID_AV);
		} catch (NoExisteElAV e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if( cat != null )
			return false;
		
		cInv.crearCategoria("categoriaPrueba2", ID_AV);
		try {
			cInv.crearProducto("testProd4", "testProd4", 123, "categoriaPrueba2", "attr1:val1;attr2:val2;", ID_AV, 10);
			cInv.crearProducto("testProd5", "testProd5", 123, "categoriaPrueba2", "attr1:val1;attr2:val2;", ID_AV, 10);
			cInv.crearProducto("testProd6", "testProd6", 123, "categoriaPrueba2", "attr1:val1;attr2:val2;", ID_AV, 10);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		try {
			cInv.eliminarCategoria("categoriaPrueba2", ID_AV);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		cat = null;
		try {
			cat = cInv.getCategoria("categoriaPrueba2", ID_AV);
		} catch (NoExisteElAV e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if( cat == null )
			return true;
		
		return false;
	}
	
	public boolean testCopiarProducto() {
		return false;
	}
	
	public boolean testMoverProducto() {
		return false;
	}
	
	public boolean testMoverListaDeProductos() {
		return false;
	}
	
	public boolean testModificarProducto() {
		return false;
	}
	
	public boolean testSetStockProducto() {
		return false;
	}
	
	public boolean testCambiarCategoriaProducto() {
		return false;
	}
	
	public boolean testEliminarProducto() {
		return false;
	}
	
	public boolean testPersistirLog() {

		return false;
	}

	public void testSuitTrucho() {
		
		cleanTests();
		setTests();
		tests = new LinkedHashMap<>();
		/*
		tests.put("Registrar Usuario", testRegistrarUsuario());
		tests.put("Modificar Info de Usuario", testModificarInfoUsuario());
		tests.put("Eliminar Usuario", testEliminarUsuario());
		
		tests.put("Crear Nota", testCrearNota()); tests.put( "Crear Notificacion" , testCrearNotifiacion());
		tests.put("Enviar Mensaje", testEnviarMensaje()); 
		tests.put("Marcar Mensaje Como Leido", testMarcarMensajeComoLeido());
		tests.put("Eliminar Mensaje Recibido", testEliminarMensajeRecibido()); 
		tests.put("Eliminar Mensaje Enviado", testEliminarMensajeEnviado()); 
		tests.put("Eliminar Mensaje", testEliminarMensaje());
		tests.put("Agregar Producto A Lista De Compras", testAgregarEnListaDeCompra());
		tests.put("Eliminar Producto de Lista de Compras", testEliminarProductoDeListaDeCompra()); 
		tests.put("Producto Comprado", testProductoComprado()); 
		tests.put("Registrar Administrador", testRegistrarAdmin()); 
		tests.put("Eliminar Administrador", testEliminarAdmin()); 
		tests.put("Login Administrador", testLoginAdmin()); 
		tests.put("Modificar Administrador", testModificarAdmin());
		tests.put("Crear Categoria", testCrearCategoria());
		*/
		tests.put("Modificar Categoria", modificarNombreCategoria());
		tests.put("Eliminar Categoria", testEliminarCategoria());
		
		try {
			FacesContext.getCurrentInstance().getExternalContext().dispatch("/test_result.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
