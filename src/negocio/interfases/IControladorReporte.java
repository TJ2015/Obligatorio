package negocio.interfases;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import dominio.datatypes.DataReporte;
import dominio.datatypes.DataUsuario;

@Local
public interface IControladorReporte {

	/*********************************************************/
	public List<DataReporte> obtenerReportesUsuario(long idAlmacen, String tipoReporte );
	public void obtenerReportesUsuarioPorLapso(long idAlmacen, String tipoReporte, Date fechaInicio, Date fechaFinal );
	
	/*********************************************************/
	public void obtenerReportesAdministrador(DataUsuario dataUsuario, String tipoReporte );
}
