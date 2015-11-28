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
	
	public boolean existeAV() {

		HttpSession session = SesionBean.getSession();
		long idAV = (long) session.getAttribute("idAV");

		if (cAV.existeAV(idAV)) {
			return true;
		} else {
			session.setAttribute("errorAV", "El AV que estaba viendo ha sido eliminado!");
			Url.redireccionarURL("usuario_sapo");
			return false;
		}

	}
	
	public void reset(String variable) {
		HttpSession session = SesionBean.getSession();
		session.removeAttribute(variable);
	}

}
