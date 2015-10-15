package negocio;
import java.util.List;

import javax.ejb.Local;

import dominio.Atributo;
import dominio.Categoria;

@Local
public interface IControladorInventario {
	
	public boolean crearCategoria(String nombre, long idAV);
	public boolean existeCategoria(String nombre, long idAV);
	public void modificarNombreCategoria(String nombre, long idAV, long idCategoria);
	public void eliminarCategoria(String nombre, long idAV);
	public void crearProducto(String nombre, String descripcion, double precio, String categoria, String atributosList, long idAV, int stock) throws Exception;
	public boolean copiarProductoGenerico(long idProducto, long idAV);
	public void modificarProducto(long idProducto, long idAV, String nombre, String descripcion, double precio, String categoria, List<String> atributos);
	public void modificarProductoDescripcion(long idProducto, long idAV, String nombre, String descripcion, double precio, String categoria, List<String> atributos);
	public void setStockProducto(String nombreProd, long idAV, int stock);
	public void cambiarCategoriaProducto(String categoria, String producto, String av);
	public boolean tienePermiso(String nickname, String idAV);
	 
}