package dominio.datatypes;

public class DataTipoReporte {
	
	private String descripcion;
	
	private String nombre;

	public DataTipoReporte() {
		
	}
	
	public DataTipoReporte(String nombre, String descripcion) {
		this.nombre = nombre;
		this.descripcion = descripcion;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	

}
