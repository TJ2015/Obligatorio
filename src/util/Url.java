package util;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public final class Url {

	public static void redireccionarURL(String url){
		try {
			redireccionarURL(url, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void redireccionarURL(String url, String parametros){
		try {
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			if (parametros != null && !parametros.isEmpty()) {
				parametros = "?" + parametros;
			}
			else{
				parametros = "";
			}
			context.redirect(context.getRequestContextPath() + "/" + url + ".xhtml" + parametros);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
