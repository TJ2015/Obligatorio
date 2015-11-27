package managedbeans;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.servlet.http.HttpSession;

import dominio.datatypes.DataReportes;
import negocio.interfases.IControladorReporte;

@ManagedBean
@RequestScoped
public class ReporteBean {

	
	@EJB
	private IControladorReporte cReportes;
	
	
	public ReporteBean() {
		
	}
	
	/************************************************************/
	
	private int tipoReporte;
	
	private int tipoReporteActual;
	
	private DataReportes dataReportes;
	
	/*************************************************************/
	
	public IControladorReporte getcReportes() {
		return cReportes;
	}
	
	public void setcReportes(IControladorReporte cReportes) {
		this.cReportes = cReportes;
	}
	
	public int getTipoReporte() {
		return tipoReporte;
	}
	
	public void setTipoReporte(int tipoReporte) {
		this.tipoReporte = tipoReporte;
	}
	
	public int getTipoReporteActual() {
		return tipoReporteActual;
	}
	
	public void setTipoReporteActual(int tipoReporteActual) {
		this.tipoReporteActual = tipoReporteActual;
	}
	
	public DataReportes getDataReportes() {
		return dataReportes;
	}
	
	public void setDataReportes(DataReportes dataReportes) {
		this.dataReportes = dataReportes;
	}
	
	
	
	
	/*************************************************************************/
	
	public void GenerarReportes(){
		HttpSession session = SesionBean.getSession();
		long idAlmacen = (long)session.getAttribute("idAV");
		this.dataReportes = cReportes.obtenerReportesUsuario(idAlmacen, tipoReporte);
		session.setAttribute("reporteGenerado", this.dataReportes);
	}



}
