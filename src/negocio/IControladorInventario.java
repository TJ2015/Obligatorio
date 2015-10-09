package negocio;

import java.util.List;

import dominio.Categoria;
import dominio.datatypes.DataCategoria;

public interface IControladorInventario {
	
	public boolean crearCategoria(String nombre, String nombreAV);
	public boolean crearCategoria(String nombre);
	public boolean existeCategoria(String nombre);
	public void cambiarCategoriaProducto(String categoria, String producto, String av);
	
	public DataCategoria eliminarCategoria(String categoria, String av);
	public boolean crearProducto(String nombre, String descripcion, double precio, Categoria categoria, List<String> atributos);
	
}