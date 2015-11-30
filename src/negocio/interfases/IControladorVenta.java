package negocio.interfases;

import java.util.List;

import javax.ejb.Local;

import dominio.Venta;
import dominio.datatypes.DataVenta;

@Local
public interface IControladorVenta {
	
	public void agregarVenta(Venta venta);
	
	public List<DataVenta> obtenerVentas();

}
