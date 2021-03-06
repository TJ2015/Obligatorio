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

	void atStartup() {
		/*
		if (!cUsu.existeUsuarioNick("1")) {
			// util.DBUtil.modificarBase("sapo_master");
			System.out.println("Cargando datos...");

			cUsu.crearNuevoTipo("comun");
			cUsu.crearNuevoTipo("facebook");
					
			cLog.agregarAccion("CREAR", "Se crea ");

			cLog.agregarAccion("MODIFICAR", "Se modifica ");
			cLog.agregarAccion("MODIFICAR_STOCK", "Se modifica el Stock del ");
			cLog.agregarAccion("ELIMINAR", "Se elimina ");
			cLog.agregarAccion("ELIMINAR_EN_LISTA", "Se elimina de la lista de compra el ");
			cLog.agregarAccion("AUMENTAR", "Se Aumenta el Stock a ");
			cLog.agregarAccion("DISMINUIR", "Se disminuye el Stock a ");
			cLog.agregarAccion("MOVER", "Se mueve ");
			cLog.agregarAccion("COMPRAR", "Se compra el ");

			cUsu.registrarUsuario("Lautaro", "Acosta", "lautaroa14", "lautaroa14", "lautaroa14@gmail.com", new Date(),
					null);
			cUsu.registrarUsuario("Santiago", "Callejas", "sancagon87", "sancagon87", "sancagon87@gmail.com",
					new Date(), null);
			cUsu.registrarUsuario("1", "1", "1", "1", "1@1.com", new Date(), null);
			cUsu.registrarUsuario("2", "2", "2", "2", "2@2.com", new Date(), null);

			cLog.agregarObjetivo("PRODUCTO", "El producto");
			cLog.agregarObjetivo("CATEGORIA", "La Categoria");

			try {
				long idHeladera = cAV.altaAV("Heladera", "1");
				cAV.altaAV("Garage", "1");
				cAV.compartirAV(idHeladera, "2");

				cInv.crearCategoria("1", "Lacteos", idHeladera);
				cInv.crearCategoria("1", "Verduras", idHeladera);

				cInv.crearProducto("1", "Leche", "1 Litro de leche ultra pasteurizada.", 15, "Lacteos", "Marca:Conaprole;",
						idHeladera, 2, null);
				cInv.crearProducto("1", "Yogurt", "1 Litro de Yogurt de Frutilla", 20, "Lacteos",
						"Marca:Parmalat;Sabor:Frutilla;", idHeladera, 1, null);
				cInv.crearProducto("1", "Lechuga", "Una cabeza de lechuga.", 10, "Verduras", "Color:Verde;", idHeladera, 2,
						null);
				
				cAV.crearNotificacion("Bienvenido a SAPo! Ahora que creaste un av, empieza a crear categorias y productos!", 1);
				
			} catch (NombreDeAVInvalido e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("...Datos cargados con �xito!");
		}
		 */
	}

	@PreDestroy
	void atShutdown() {
	}
}
