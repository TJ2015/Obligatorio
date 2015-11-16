package util;

import java.util.ArrayList;
import java.util.List;

import dominio.Atributo;
import dominio.Condicion;
import dominio.Condicion.Condicional;

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

	public static Condicion convertirCondicionAString(String condicion) {
		String[] aux1 = condicion.split("["), 
				aux2;
		String attr, val;
		Condicional cond = null;
		Condicion condi = null;
		
		if( aux1.length > 1 ) {			
			aux2 = aux1[1].split("]");
			if( aux2.length > 1 ) { 
				attr = aux1[0];
				switch(aux2[0]) {
					case "<":
						cond = Condicional.MENOR;
						break;
					case "<=":
						cond = Condicional.MENOR_O_IGUAL;
						break;
					case "=":
						cond = Condicional.IGUAL;
						break;
					case ">=":
						cond = Condicional.MAYOR_O_IGUAL;
						break;
					case ">":
						cond = Condicional.MAYOR;
					break;
				}
				
				val = aux2[1];
				
				condi = new Condicion(attr, cond, val);
			}
			
		}
		
		return condi;
	}
}
