package negocio.interfases;

import java.util.List;

import dominio.datatypes.DataProducto;

public interface AlgoritmoDeRecomendacion {

	public List<DataProducto> recomendar(String usuario);	
	
}
