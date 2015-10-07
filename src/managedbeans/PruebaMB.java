package managedbeans;

import java.io.Serializable;
import java.util.Date;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import dominio.Usuario;
import negocio.IControladorUsuario;
import persistencia.IUsuarioDAO;

@ManagedBean
public class PruebaMB implements Serializable {

	@EJB
	IUsuarioDAO cusu;

	public PruebaMB() {
		super();
	}
	
	public String pruebaManejoDB() {
		//util.DataBaseManager.setupTenant("Obligatorio", "pepe666", "localhost");
		Usuario u = new Usuario("Pepito", "Gomez", "pepito666", "asd", "pepito@example.org", new Date());
		cusu.altaUsuario(u);
		return "Hola";
	}
	
}
