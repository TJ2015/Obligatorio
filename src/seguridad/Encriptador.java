package seguridad;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Encriptador {
	
	//Algoritmo a usar en la encriptación. 
	//Posibles valores: MD2, MD5, SHA-1, SHA-256, SHA-384, SHA-512
	public static String ALGORITMO = "SHA-384";
	
	/***
     * Encripta un mensaje de texto mediante algoritmo de resumen de mensaje.
     * @param mensaje texto a encriptar
     * @return mensaje encriptado según algoritmo ALGORITMO
     */
	public static String encriptar(String mensaje){
        byte[] digest = null;
        byte[] buffer = mensaje.getBytes();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITMO);
            messageDigest.reset();
            messageDigest.update(buffer);
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Error creando Digest");
        }
        return pasarAHexadecimal(digest);
	}
	
	/***
     * Convierte un arreglo de bytes a String usando valores hexadecimales
     * @param digest arreglo de bytes a convertir
     * @return String creado a partir de <code>digest</code>
     */
    private static String pasarAHexadecimal(byte[] digest){
        String hash = "";
        for(byte aux : digest) {
            int b = aux & 0xff;
            if (Integer.toHexString(b).length() == 1) hash += "0";
            hash += Integer.toHexString(b);
        }
        return hash;
    }
    
    /***
     * Compara un texto plano con un texto encriptado 
     * @param texto texto sin encriptar
     * @param encriptado texto encriptado por funcion encriptar()
     * @return True si son iguales, False de lo contrario
     */
    public static boolean sonIguales(String texto, String encriptado) {
    	return encriptado.equals(encriptar(texto));    	
    }
    
    public static String randomString(String chars, int length) {
  	  Random rand = new Random();
  	  StringBuilder buf = new StringBuilder();
  	  for (int i=0; i<length; i++) {
  	    buf.append(chars.charAt(rand.nextInt(chars.length())));
  	  }
  	  return buf.toString();
  	}

	
}
