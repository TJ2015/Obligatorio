package negocio.interfases;

import java.util.Date;

import javax.ejb.Local;

import dominio.datatypes.DataReportes;
import dominio.datatypes.DataUsuario;

@Local
public interface IControladorReporte {

	/*********************************************************/
	public DataReportes obtenerReportesUsuario(long idAlmacen, int tipoReporte );
	public void obtenerReportesUsuarioPorLapso(long idAlmacen, int tipoReporte, Date fechaInicio, Date fechaFinal );
	
	/*********************************************************/
	public void obtenerReportesAdministrador(DataUsuario dataUsuario, int tipoReporte );
}
