package dominio.datatypes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataReportes {
	
	private DataTipoReporte tipoReporte;
	private List<DataReporte> listaReporte;
	private List<DataReporteAdmin> listaReporteAdmin;
	
	private String total;
	
	public DataReportes() {
		this.listaReporte = new ArrayList<DataReporte>();
	}
	
	public DataReportes(DataTipoReporte tipo) {
		this.listaReporte = new ArrayList<DataReporte>();
		this.tipoReporte = tipo;
	}
	
	public DataReportes(ArrayList<DataReporte> aDataReporte, DataTipoReporte tipo) {
		this.listaReporte = aDataReporte;
		this.tipoReporte = tipo;
	}
	
	public DataTipoReporte getTipoReporte() {
		return tipoReporte;
	}
	
	public void setTipoReporte(DataTipoReporte tipoReporte) {
		this.tipoReporte = tipoReporte;
	}
	
	public List<DataReporte> getListaReporte() {
		return listaReporte;
	}
	
	public void setListaReporte(List<DataReporte> listaReporte) {
		this.listaReporte = listaReporte;
	}
	
	public void addListaReporte(DataReporte dataReporte){
		this.listaReporte.add(dataReporte);
	}
	
	public void removeListaReporte(DataReporte dataReporte){
		this.listaReporte.remove(dataReporte);
	}
	
	@SuppressWarnings("rawtypes")
	public Iterator getInterator(){
		return this.listaReporte.iterator();
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}
}
