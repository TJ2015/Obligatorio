package managedbeans;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;



@ManagedBean
@SessionScoped
public class FacebookBean implements Serializable 
{
	
	private static final long serialVersionUID = 1L;
	
	public static final String FB_APP_ID = "1149280135099672"; 
	public static final String FB_APP_SECRET = "b941542f1ec36169afbe391e5317b0e1";
	public static final String REDIRECT_URI = "http://localhost:8080/Obligatorio/LoginFacebook";
	
	public static final String PERMISOS_DE_SCOPE = "&scope=public_profile,user_friends,email";

	public static final String DATOS_DE_FIELDS = "fields=first_name%2Clast_name%2Cemail%2Cpicture";
	
	public static final String Y_DATOS_DE_FIELDS = "&" + DATOS_DE_FIELDS;
	public static final String DATOS_DE_FIELDS_Y = DATOS_DE_FIELDS + "&";

	private String accessToken;
		
	public String obtenerUrlLogin()
	{
		StringBuilder urlLogin = new StringBuilder();
		try {
			urlLogin.append("http://www.facebook.com/dialog/oauth?client_id=");
			urlLogin.append(FB_APP_ID);
			urlLogin.append("&redirect_uri=");
			urlLogin.append(URLEncoder.encode(REDIRECT_URI, "UTF-8"));
			urlLogin.append(PERMISOS_DE_SCOPE);
		} 
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return urlLogin.toString();
	}

	public String getAccessToken(String code) 
	{
		if (accessToken == null) 
		{
			URL urlGraph;
			try {
				urlGraph = new URL(obtenerUrlGraph(code));
			} 
			catch (MalformedURLException e) {
				e.printStackTrace();
				throw new RuntimeException("Invalid code received " + e);
			}
			URLConnection conexion;
			StringBuffer token = null;
			try {
				conexion = urlGraph.openConnection();
				BufferedReader datos = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
				String inputLine;
				token = new StringBuffer();
				while ((inputLine = datos.readLine()) != null){
					token.append(inputLine + "\n");
				}
				datos.close();
			} 
			catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("No se obtiene la conexion a Facebook " + e);
			}

			accessToken = token.toString();
			if (accessToken.startsWith("{")) {
				throw new RuntimeException("ERROR: En Token es invalido: " + accessToken);
			}
		}
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String obtenerUrlGraph(String code)
	{
		StringBuilder urlGraph = new StringBuilder();
		try {
			urlGraph.append("https://graph.facebook.com/oauth/access_token?client_id=");
			urlGraph.append(FB_APP_ID);
			urlGraph.append("&redirect_uri=");
			urlGraph.append(URLEncoder.encode(REDIRECT_URI, "UTF-8"));
			urlGraph.append("&client_secret=");
			urlGraph.append(FB_APP_SECRET);
			urlGraph.append(PERMISOS_DE_SCOPE);
			urlGraph.append(Y_DATOS_DE_FIELDS);
			urlGraph.append("&code=");
			urlGraph.append(code);
			
		} 
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return urlGraph.toString();
	}
	
	public String obtenerDatosUsuario()
	{
		String graph = null;
		try {
			//Es IMPORTANTE respetar el orden, primero los dtaos y despues el Token
			String urlGraph = "https://graph.facebook.com/me?" + DATOS_DE_FIELDS_Y + accessToken;
			URL url = new URL(urlGraph);
			URLConnection conexion = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
			String inputLine;
			StringBuffer datos = new StringBuffer();
			while ((inputLine = in.readLine()) != null){
				datos.append(inputLine + "\n");
			}
			in.close();
			graph = datos.toString();
		} 
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("ERROR al obtener los datos de Facebook. " + e);
		}
		return graph;
	}
}
