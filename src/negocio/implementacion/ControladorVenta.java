package negocio.implementacion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dominio.Venta;
import dominio.datatypes.DataVenta;
import negocio.interfases.IControladorVenta;
import persistencia.interfases.IVentaDAO;

@Stateless
public class ControladorVenta implements IControladorVenta {

	@EJB
	private IVentaDAO ventaDAO;
	
	@Override
	public void agregarVenta(Venta venta) {
		try {
			ventaDAO.persistirVenta(venta);
		} catch (Exception e) {
			System.out.println("No se persiste la venta " + venta.toString());
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<DataVenta> obtenerVentas() {
		List<DataVenta> lDataVenta = null;
		try {
			List<Venta> lVenta = ventaDAO.buscarListaVenta();
			if (lVenta != null && lVenta.size() > 0) {
				lDataVenta = new ArrayList<DataVenta>();
				for (Iterator iterator = lVenta.iterator(); iterator.hasNext();) {
					Venta venta = (Venta) iterator.next();
					DataVenta dataVenta = new DataVenta(venta.getUsuario().getDataUsuario(), venta.getValor(), venta.getFecha());
					lDataVenta.add(dataVenta);
				}				
			}
		} catch (Exception e) {
			System.out.println("Error al obtener la lista de Ventas");
		}
		return lDataVenta;
	}
	
	
}
