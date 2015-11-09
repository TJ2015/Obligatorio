package util;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

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

		try {
			util.DBUtil.modificarBase("sapo_master");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			// CARGAR DATOS
			System.out.println("Cargando datos...");

			cUsu.crearNuevoTipo("comun");
			cUsu.crearNuevoTipo("facebook");

			cUsu.registrarUsuario("1", "1", "1", "1", "1@1.com", new Date(), null);
			cUsu.registrarUsuario("2", "2", "2", "2", "2@2.com", new Date(), null);

			System.out.println("...Datos cargados con éxito!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@PreDestroy
	void atShutdown() {

	}
}