package persistencia.interfases;

import java.util.List;

import javax.ejb.Local;

import dominio.Venta;

@Local
public interface IVentaDAO {
	
	public boolean persistirVenta(Venta venta);
	public List<Venta> buscarListaVenta();

}
