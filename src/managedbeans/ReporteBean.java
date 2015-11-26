package managedbeans;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.servlet.http.HttpSession;

import dominio.datatypes.DataReporte;
import negocio.interfases.IControladorReporte;

@ManagedBean
@RequestScoped
public class ReporteBean {

	
	@EJB
	private IControladorReporte cReportes;
	
	
	public ReporteBean() {
		
	}
	
	/************************************************************/
	
	private String tipoReporte;
	
	private List<DataReporte> listaReportes;
	
	
	/*************************************************************/
	
	public String getTipoReporte() {
		return tipoReporte;
	}

	public void setTipoReporte(String tipoReporte) {
		this.tipoReporte = tipoReporte;
	}
	
	public List<DataReporte> getListaReportes() {
		return listaReportes;
	}
	
	public void setListaReportes(List<DataReporte> listaReportes) {
		this.listaReportes = listaReportes;
	}
	
	
	
	
	/*************************************************************************/
	
	public void GenerarReportes(){
		HttpSession session = SesionBean.getSession();
		long idAlmacen = (long)session.getAttribute("idAV");
		this.listaReportes = cReportes.obtenerReportesUsuario(idAlmacen, tipoReporte);
	}


}
