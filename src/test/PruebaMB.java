package test;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean
public class PruebaMB implements Serializable {

	private Map<String, Boolean> tests;
	
	@EJB
	TestControladores cTest;

	public PruebaMB() {
		super();
	}
	
	public Map<String, Boolean> getTests() {
		return tests;
	}

	public void setTests(Map<String, Boolean> tests) {
		this.tests = tests;
	}

	public void testSuitTrucho() {
		tests = new HashMap<>();
		tests.put("Crear Nota", cTest.testCrearNota());
		tests.put("Crear Notificacion" , cTest.testCrearNotifiacion());
		tests.put("Enviar Mensaje" , cTest.testEnviarMensaje());
		tests.put("Marcar Mensaje Como Leido" , cTest.testMarcarMensajeComoLeido());
		tests.put("Eliminar Mensaje Recibido" , cTest.testEliminarMensajeRecibido());
		tests.put("Eliminar Mensaje Enviado" , cTest.testEliminarMensajeEnviado());
		try {
			FacesContext.getCurrentInstance().getExternalContext().dispatch("/test_result.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
