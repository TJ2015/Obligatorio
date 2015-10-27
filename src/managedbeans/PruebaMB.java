package managedbeans;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import test.TestControladores;

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
		try {
			FacesContext.getCurrentInstance().getExternalContext().dispatch("/test_result.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
