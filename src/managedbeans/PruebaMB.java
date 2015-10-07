package managedbeans;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import negocio.IControladorUsuario;

@ManagedBean
public class PruebaMB implements Serializable {

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
