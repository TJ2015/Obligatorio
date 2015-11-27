package util;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import exceptions.NoExisteElUsuario;
import exceptions.NombreDeAVInvalido;
import exceptions.YaExisteElUsuario;
import negocio.interfases.IControladorAV;
import negocio.interfases.IControladorInventario;
import negocio.interfases.IControladorLog;
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
	
	@EJB
	IControladorLog cLog;
	

	@PostConstruct
	void atStartup(){

		util.DBUtil.modificarBase("sapo_master");
		
		// CARGAR DATOS
		System.out.println("Cargando datos...");
		
		cUsu.crearNuevoTipo("comun");
		cUsu.crearNuevoTipo("facebook");
		
		cLog.agregarAccion("Crear", "Se crea ");
		cLog.agregarAccion("Modificar", "Se modifica ");
		cLog.agregarAccion("Eliminar", "Se elimina ");
		cLog.agregarAccion("Aumentar", "Se Aumenta el Stock a ");
		cLog.agregarAccion("Disminuir", "Se disminuye el Stock a ");

		cLog.agregarObjetivo("Producto", "El producto");
		cLog.agregarObjetivo("Categoria", "La Categoria");
		
		cUsu.registrarUsuario("1", "1", "1", "1", "1@1.com", new Date(), null);
		cUsu.registrarUsuario("2", "2", "2", "2", "2@2.com", new Date(), null);
		
		System.out.println("...Datos cargados con éxito!");
		
	}

	@PreDestroy
	void atShutdown() {

	}
}
