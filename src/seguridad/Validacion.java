package seguridad;

public class Validacion {

	public static boolean esAlfaNumerico(String texto) {
		char textArr[] = texto.toLowerCase().toCharArray();
		
		for( char c : textArr ) {
			if( !((c >= 48 && c <= 57) || (c >= 97 && c <= 122  )) ) {
				return false;
			}
		}
		
		return true;
	}
	
}
