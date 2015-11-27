package negocio.implementacion;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dominio.datatypes.DataProducto;
import dominio.datatypes.DataReporte;
import dominio.datatypes.DataReportes;
import dominio.datatypes.DataTipoReporte;
import dominio.datatypes.DataUsuario;
import negocio.interfases.IControladorInventario;
import negocio.interfases.IControladorReporte;
import util.GeneradorPDF;

@Stateless
public class ControladorReporte implements IControladorReporte {

	@EJB
	private IControladorInventario cInventario;
	
	private Map<Integer, DataTipoReporte> TIPO_REPORTE = new HashMap<Integer, DataTipoReporte>();
	
	public ControladorReporte() {
		TIPO_REPORTE.put(1, new DataTipoReporte("Cantidad", "Reportes por cantidad"));
		TIPO_REPORTE.put(2, new DataTipoReporte("Precio", "Reportes por Precio"));
	}
	
	private DataTipoReporte obtenerTipoReporte(int tipo){
		return TIPO_REPORTE.get(tipo);
	}
	
	@Override
	public DataReportes obtenerReportesUsuario(long idAlmacen, int tipoReporte) 
	{
		DataReportes dataReportes = null; 
		try {
			if (idAlmacen > 0) {
				List<DataProducto> lProductos = cInventario.getProductos(idAlmacen);
				if (lProductos != null && lProductos.size() > 0) {
					dataReportes = new DataReportes(obtenerTipoReporte(tipoReporte));
					double valorTotal = 0;
					for(Iterator<DataProducto> i = lProductos.iterator(); i.hasNext(); ) {
						DataProducto dataProducto = i.next();
						double valor = obtenerValorReportePorTipo(dataProducto, tipoReporte);
						valorTotal += valor;
						dataReportes.addListaReporte(new DataReporte(dataProducto.getNombre(), obtenerTextoValor(valor, tipoReporte)));
					}
					dataReportes.setTotal(obtenerTextoValor(valorTotal, tipoReporte));
				}
			}
			GeneradorPDF.GenerarReportePDF(dataReportes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataReportes;
	}
	
	private double obtenerValorReportePorTipo(DataProducto dataProducto, int tipoReporte){
		double valor = 0;
		try {
			switch (tipoReporte) {
			case 1: //Reportes por cantidad
				valor = (double)dataProducto.getStock();
				break;
			
			case 2: //Reportes por precio total
				valor = ((double)dataProducto.getStock() * dataProducto.getPrecio());
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return valor;
	}
	
	private String obtenerTextoValor(double valor, int tipoReporte)
	{
		String textoValor = null;
		switch (tipoReporte) {
		case 1:
			textoValor = Double.toString(valor) + " Unidades";
			break;
		
		case 2:
			textoValor = "$ " + Double.toString(valor);
			break;
		}
		return textoValor;
	}
	
	
	

	@Override
	public void obtenerReportesUsuarioPorLapso(long idAlmacen, int tipoReporte, Date fechaInicio,
			Date fechaFinal) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void obtenerReportesAdministrador(DataUsuario dataUsuario, int tipoReporte) {
		// TODO Auto-generated method stub
		
	}

}
