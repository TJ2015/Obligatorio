package util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ManejadorAtributosGenericos {
	/**
	 * Invoca el getter correspondiente al atributo attName sobre el objeto tgt.
	 * 
	 * @param tgt
	 *            El objeto a consultar
	 * @param attName
	 *            El nombre del atributo a devolver
	 * @return El valor del atributo, si existe, de lo contrario una excepcion
	 *         'NoExisteElAtributo'
	 * @throws NoSuchMethodException
	 */
	public static Object invokeGetter(Object tgt, String attName) throws NoSuchMethodException, NoSuchFieldException {
		boolean bool = false;
		try {
			Method mtd;
			try {
				// obtengo el nombre del getter para el atributo attName
				if (tgt.getClass().getDeclaredField(attName).getType().getName().equals("boolean")) {
					bool = true;
				}
				String getterName = getGetterName(attName, bool);
				// obtengo un puntero al metodo de acceso; el segundo argumento
				// (null) indica que,
				// de estar sobrecargado, me interesa la version del metodo que
				// no recibe argumentos

				mtd = tgt.getClass().getMethod(getterName, null);
			} catch (NoSuchMethodException | NoSuchFieldException e) {
				throw e;
			}

			// invoco al getter si pasarle ningun argumento

			return mtd.invoke(tgt, null);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * Dado el nombre de un atributo, retorna el nombre de su getter
	 * 
	 * @param attName
	 *            Nombre del atributo
	 * @return El nombre del metodo getter correspoddiente al atributo
	 */
	public static String getGetterName(String attName, boolean bool) {
		if (bool)
			return "is" + Character.toUpperCase(attName.charAt(0)) + attName.substring(1);

		return "get" + Character.toUpperCase(attName.charAt(0)) + attName.substring(1);
	}

}
