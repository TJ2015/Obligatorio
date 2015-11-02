package util;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import dominio.Usuario;
import exceptions.NombreDeAVInvalido;
import negocio.IControladorAV;
import negocio.IControladorInventario;
import negocio.IControladorUsuario;
import persistencia.IAvDAO;
import persistencia.IInventarioDAO;
import persistencia.IUsuarioDAO;

@Startup
@Singleton
public class DatosPrecargados {

	@EJB
	IControladorUsuario cUsu;
	@EJB
	IControladorAV cAV;
	@EJB
	IControladorInventario cInv;

	@PostConstruct
	void atStartup() {

		util.DBUtil.modificarBase("sapo_master");
		
		// CARGAR DATOS
		System.out.println("Cargando datos...");
		util.DBUtil.eliminarTenant("test_testAV");
		
		cUsu.registrarUsuario("Juan", "Perez", "jotape", "jotape", "jp@gmail.com", new Date());
		cUsu.registrarUsuario("Roberto", "Gomez", "robgom", "robgom", "rob@gmail.com", new Date());
		cUsu.registrarUsuario("Maria", "Lopez", "marlo", "marlo", "marlo@gmail.com", new Date());
		cUsu.registrarUsuario("Lucia", "Fernandez", "lucifer", "lucifer", "lucifer@gmail.com", new Date());

		cUsu.registrarUsuario("Test", "Run", "test", "test", "test@example.org", new Date());
		cUsu.registrarUsuario("Test", "Run 2", "test2", "test2", "test2@example.org", new Date());

		cUsu.registrarUsuario("1", "1", "1", "1", "1@1.com", new Date());
		cUsu.registrarUsuario("2", "2", "2", "2", "2@2.com", new Date());

		try {
			cAV.altaAV("testAV", "test");
		} catch (NombreDeAVInvalido e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		cInv.crearCategoria("testCat", 1);
		try {
			cInv.crearProducto("testProd1", "producto de prueba 1", 111, "testCat", "attr1:val1;attr2:val2;", 1, 10);
			cInv.crearProducto("testProd2", "producto de prueba 2", 222, "testCat", "attr1:val1;attr2:val2;attr3:val3;",
					1, 20);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("...Datos cargados con éxito!");
		
	}

	@PreDestroy
	void atShutdown() {

	}
}