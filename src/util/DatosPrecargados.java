package util;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import dominio.Usuario;
import persistencia.IAvDAO;
import persistencia.IInventarioDAO;
import persistencia.IUsuarioDAO;

@Startup
@Singleton
public class DatosPrecargados {
	
	@EJB
	IAvDAO avDAO;
	@EJB
	IInventarioDAO invDAO;
	@EJB
	IUsuarioDAO usuDAO;
	
	@PostConstruct 
    void atStartup() { 
		
		Usuario usu = new Usuario("Juan", "Perez", "jotape", "jotape", "jp@gmail.com", new Date());
		usuDAO.altaUsuario(usu);
		usu = new Usuario("Roberto", "Gomez", "robgom", "robgom", "rob@gmail.com", new Date());
		usuDAO.altaUsuario(usu);
		usu = new Usuario("Maria", "Lopez", "marlo", "marlo", "marlo@gmail.com", new Date());
		usuDAO.altaUsuario(usu);
		usu = new Usuario("Lucia", "Fernandez", "lucifer", "lucifer", "lucifer@gmail.com", new Date());
		usuDAO.altaUsuario(usu);
		
	}

    @PreDestroy
    void atShutdown() {
    	
    }
}