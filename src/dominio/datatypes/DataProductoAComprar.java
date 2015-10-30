package dominio.datatypes;

import java.io.Serializable;

public class DataProductoAComprar implements Serializable {

	private DataProducto producto;
	private int cantidad;

	public DataProductoAComprar() {
		super();
	}

	public DataProductoAComprar(DataProducto producto, int cantidad) {
		super();
		this.producto = producto;
		this.cantidad = cantidad;
	}

	public DataProducto getProducto() {
		return producto;
	}

	public void setProducto(DataProducto producto) {
		this.producto = producto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

}
