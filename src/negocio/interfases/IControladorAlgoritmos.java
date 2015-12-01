package negocio.interfases;

import java.util.List;

import javax.ejb.Local;

import dominio.datatypes.DataProducto;
import dominio.datatypes.DataProductoVendido;

@Local
public interface IControladorAlgoritmos {
	
	public List<DataProducto> recomendar(String usuario);	
	
	public List<DataProductoVendido> obtenerProductosMasVendidos(int cantidad, boolean distinguir);
	
	public List<DataProducto> obtenerProductosConMenosStock(long idAV);
}
