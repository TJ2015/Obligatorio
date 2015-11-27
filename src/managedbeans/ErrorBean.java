package managedbeans;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import negocio.interfases.IControladorAV;
import util.Url;

@ManagedBean
@RequestScoped
public class ErrorBean {

	@EJB
	IControladorAV cAV;
	
	private String error;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
	public boolean existeAV() {

		HttpSession session = SesionBean.getSession();
		long idAV = (long) session.getAttribute("idAV");

		if (cAV.existeAV(idAV)) {
			return true;
		} else {
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			error = "El AV que estaba viendo ha sido eliminado!";
			Url.redireccionarURL("usuario_sapo");
			return false;
		}

	}

}
