package negocio.interfases;

import java.util.Map;

import javax.ejb.Local;

@Local
public interface IControladorAlgoritmos {

	public Map<String, Integer> obtenerProductosMasVendidos();
}
