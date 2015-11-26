package negocio.implementacion;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dominio.datatypes.DataProducto;
import dominio.datatypes.DataReporte;
import dominio.datatypes.DataUsuario;
import negocio.interfases.IControladorInventario;
import negocio.interfases.IControladorReporte;

@Stateless
public class ControladorReporte implements IControladorReporte {

	@EJB
	private IControladorInventario cInventario;
	
	
	@Override
	public List<DataReporte> obtenerReportesUsuario(long idAlmacen, String tipoReporte) {
		
		List<DataReporte> lReportes = new ArrayList<DataReporte>(); 
		try {
			if (idAlmacen > 0) {
				List<DataProducto> lProductos = cInventario.getProductos(idAlmacen);
				for(Iterator<DataProducto> i = lProductos.iterator(); i.hasNext(); ) {
				    DataProducto dataProducto = i.next();
				    System.out.println(dataProducto.toString());
				    lReportes.add(obtenerReportePorTipo(dataProducto, tipoReporte));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lReportes;
	}
	
	private DataReporte obtenerReportePorTipo(DataProducto dataProducto, String tipoReporte){
		DataReporte dataReporte = null;
		try {
			switch (tipoReporte) {
			case "1": //Reportes por cantidad
				dataReporte = new DataReporte(dataProducto.getNombre(), Integer.toString(dataProducto.getStock()));
				break;
			
			case "2": //Reportes por precio total
				dataReporte = new DataReporte(dataProducto.getNombre(), Double.toString(dataProducto.getStock() * dataProducto.getPrecio()));
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataReporte;
	}

	@Override
	public void obtenerReportesUsuarioPorLapso(long idAlmacen, String tipoReporte, Date fechaInicio,
			Date fechaFinal) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void obtenerReportesAdministrador(DataUsuario dataUsuario, String tipoReporte) {
		// TODO Auto-generated method stub
		
	}

}
