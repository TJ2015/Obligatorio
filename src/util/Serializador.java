package util;

import java.util.ArrayList;
import java.util.List;

import dominio.Atributo;

public class Serializador {
	
	public static String convertirAString(List<Atributo> list) {
		
		String res = "";
		
		for( Atributo a : list ) {
			res += a.toString();
		}
		
		return res;		
	}
	
	public static List<Atributo> convertirDesdeString(String str) {
		List<Atributo> attrs = new ArrayList<>();
		
		//Atributos de la forma nombre1:valor;nombre2:valor;nombre3:valor;
		String[] atrString = str.split(";");
		for(String as : atrString) {
			if( as.length() > 1) {
				String[] aux = as.split(":");
				attrs.add( new Atributo(aux[0], aux[1]) );
			}
		}
		
		return attrs;
	}
}
