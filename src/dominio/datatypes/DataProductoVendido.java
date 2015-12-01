package dominio.datatypes;

public class DataProductoVendido {

	private String nombreProducto;
	private int cantidad;
	

	public DataProductoVendido() {
		
	}

	public DataProductoVendido(String nombreProducto, int cantidad) {
		this.nombreProducto = nombreProducto;
		this.cantidad = cantidad;
	}
	
	public String getNombreProducto() {
		return nombreProducto;
	}
	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	
}
