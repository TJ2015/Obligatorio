package util;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import exceptions.NombreDeAVInvalido;
import negocio.interfases.IControladorAV;
import negocio.interfases.IControladorInventario;
import negocio.interfases.IControladorUsuario;

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
		
		cUsu.crearNuevoTipo("comun");
		cUsu.crearNuevoTipo("facebook");
		
		cUsu.registrarUsuario("Juan", "Perez", "jotape", "jotape", "jp@gmail.com", new Date());
		cUsu.registrarUsuario("Roberto", "Gomez", "robgom", "robgom", "rob@gmail.com", new Date());
		cUsu.registrarUsuario("Maria", "Lopez", "marlo", "marlo", "marlo@gmail.com", new Date());
		cUsu.registrarUsuario("Lucia", "Fernandez", "lucifer", "lucifer", "lucifer@gmail.com", new Date());

		cUsu.registrarUsuario("1", "1", "1", "1", "1@1.com", new Date());
		cUsu.registrarUsuario("2", "2", "2", "2", "2@2.com", new Date());
		
		System.out.println("...Datos cargados con éxito!");
		
	}

	@PreDestroy
	void atShutdown() {

	}
}