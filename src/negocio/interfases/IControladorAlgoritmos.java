package negocio.interfases;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import dominio.datatypes.DataProducto;

@Local
public interface IControladorAlgoritmos {

	public Map<String, Integer> obtenerProductosMasVendidos();
	
	public List<DataProducto> obtenerProductosConMenosStock(long idAV);
}
