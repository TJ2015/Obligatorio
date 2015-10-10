package negocio;

import java.util.List;

import dominio.Categoria;
import dominio.datatypes.DataCategoria;

public interface IControladorInventario {
	
	public boolean crearCategoria(String nombre, long idAV);
	public boolean crearCategoria(String nombre);
	public boolean existeCategoria(String nombre, String nombreAV);
	public void modificarNombreCategoria(String nombre, long idAV, long idCategoria);
	public void eliminarCategoria(String nombre, long idAV);
	
	public boolean crearProductoDescripcion(String nombre, String descripcion, double precio, String categoria, List<String> atributos, long idAV);
	public boolean crearProducto(String nombre, String descripcion, double precio, String categoria, List<String> atributos, long idAV);
	public boolean copiarProductoGenerico(long idProducto, long idAV);
	public void modificarProducto(long idProducto, long idAV, String nombre, String descripcion, double precio, String categoria, List<String> atributos);
	public void modificarProductoDescripcion(long idProducto, long idAV, String nombre, String descripcion, double precio, String categoria, List<String> atributos);
	
	public void setStockProducto(long idProducto, long idAV, int stock);
	
	public void cambiarCategoriaProducto(String categoria, String producto, String av);
	
	public boolean tienePermiso(String nickname, String idAV);
	 
}