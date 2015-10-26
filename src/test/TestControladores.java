package test;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import negocio.IControladorAV;
import negocio.IControladorUsuario;
import persistencia.IAvDAO;

@Local
@Stateless
public class TestControladores {
	
	@EJB
	IControladorAV cAV;
	@EJB
	IControladorUsuario cUsu;
	@EJB
	IAvDAO avDAO;
	
	public boolean testCrearNota() {
		boolean OK = true;
		try {
			cAV.crearNota("Nota de prueba", "test", 1);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			OK = false;
		}
		return OK;
	}

}
