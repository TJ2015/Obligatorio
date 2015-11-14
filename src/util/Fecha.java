package util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Fecha {

	
	public static String convertirParaChat(Date fecha){
		return convertirParaChat(fecha, "dd/MM/yyyy");
	}
	
	public static String convertirParaChat(Date fecha, String formato){
		String resultado = null;
		try {	
			DateFormat convertir = new SimpleDateFormat(formato);
			resultado = convertir.format(fecha);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultado;
	}
}
