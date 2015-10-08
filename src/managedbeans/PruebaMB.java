package managedbeans;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import negocio.IControladorUsuario;

@ManagedBean
public class PruebaMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB
	IControladorUsuario cusu;

	public PruebaMB() {
		super();
	}
	
	public String pruebaManejoDB() {
		util.DataBaseManager.setupTenant("Obligatorio", "PIJA666", "localhost");
		return "PIJA";
	}
	
}
