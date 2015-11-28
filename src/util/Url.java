package util;

import java.net.URL;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

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
	
	public static String obtenerActualURL(HttpServletRequest request){
		String url = null;
		try {
			URL reconstructedURL = new URL(request.getScheme(),
		                               request.getServerName(),
		                               request.getServerPort(),
		                               "/Obligatorio/");
			url = reconstructedURL.toString();
		} catch (Exception e) {
			url = "http://sapito-obligatorio.rhcloud.com/Obligatorio/";
		}
		System.out.println(url);
		return url;
	}
}
