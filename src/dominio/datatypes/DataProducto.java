package dominio.datatypes;

import java.io.Serializable;
import java.util.List;

import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import dominio.Atributo;
import dominio.Categoria;

public class DataProducto implements Serializable {
	
	private String nombre;
	private String descripcion;
	private double precio;
	private int stock;
	private String nomcategoria;
	public DataProducto(String nombre, String descripcion, double precio, int stock, String nomcategoria) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.stock = stock;
		this.nomcategoria = nomcategoria;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public String getCategoria() {
		return nomcategoria;
	}
	public void setCategoria(String categoria) {
		this.nomcategoria = categoria;
	}

}
