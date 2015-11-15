package negocio.interfases;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import dominio.Producto;
import dominio.datatypes.DataProducto;
import persistencia.interfases.IInventarioDAO;

public class AlgoritmoDeRecomendacionSimple implements AlgoritmoDeRecomendacion {

	@EJB
	private IInventarioDAO invDAO;

	public AlgoritmoDeRecomendacionSimple() {
		super();
	}

	@Override
	public List<DataProducto> recomendar(String usuario) {
		List<Producto> prods = invDAO.getAllProducto("master");
		List<DataProducto> dprods = new ArrayList<>();
		int i = 0;
		for (Producto prod : prods) {

			if (i > 5)
				break;

			dprods.add(prod.getDataProducto());
			i++;
		}

		return dprods;
	}

}
