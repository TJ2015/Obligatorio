package util;

import java.util.ArrayList;
import java.util.List;

import dominio.Atributo;
import dominio.Condicion;
import dominio.Condicion.Condicional;

public class Serializador {

	public static String convertirAString(List<Atributo> list) {

		String res = "";

		for (Atributo a : list) {
			res += a.toString();
		}

		return res;
	}

	public static List<Atributo> convertirDesdeString(String str) {
		List<Atributo> attrs = new ArrayList<>();

		// Atributos de la forma nombre1:valor;nombre2:valor;nombre3:valor;
		String[] atrString = str.split(";");
		for (String as : atrString) {
			if (as.length() > 1) {
				String[] aux = as.split(":");
				attrs.add(new Atributo(aux[0], aux[1]));
			}
		}

		return attrs;
	}

	public static Condicion convertirCondicionAString(String condicion) {
		String[] aux1 = condicion.split(":"), aux2;
		String attr, val;
		Condicional cond = null;
		Condicion condi = null;

		if (aux1.length == 3) {
				attr = aux1[0];
				switch (aux1[1]) {
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

				val = aux1[2];

				condi = new Condicion(attr, cond, val);
		}

		return condi;
	}
}
