package util;

import java.util.HashMap;
import java.util.Map;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;

public class PayPalUtil {

	private static String CLIENT_ID = "AVH8v4ZcxNb_IR2r94k_AdI7Jmd4EWD2JHgTNcmx6HnD_UK0Zmi1bdNVAW0ve2iksgTqnSXl200oUS8f";
	private static String SECRET = "EFIWwvA9M4gjbGLtpzMPJCX0ex7Mt-O9Yvp9JhC7I4T3vGcLhvYI0mB37sBthzEv9sn-mJQFdOWtPGT6";
	
	static public APIContext prepararCliente() {
		APIContext apiContext = null;
		String accessToken = "";
		Map<String, String> config = new HashMap<>();
		config.put("mode", "sandbox");
		config.put("http.ConnectionTimeOut", "1000");
		config.put("log.FileName", ""); // Para activar logs especificar un archivo.
		config.put("log.LogLevel", "FINE");
		config.put("validation.level", "log");
		try {
			accessToken = new OAuthTokenCredential(CLIENT_ID, SECRET, config).getAccessToken();
		} catch (PayPalRESTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			apiContext = new APIContext(accessToken);
			apiContext.setConfigurationMap(config);
		} catch (Exception e) {
			// TODO 505
			e.printStackTrace();
		}
		
		return apiContext;
	}
}
