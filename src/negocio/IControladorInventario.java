package negocio;
import java.util.List;

import javax.ejb.Local;

import dominio.Atributo;
import dominio.Categoria;

@Local
public interface IControladorInventario {
	
	public boolean crearCategoria(String nombre, long idAV);
	
	public boolean crearCategoria2(String nombre, String nombreAV);
	
	public boolean existeCategoria(String nombre, long idAV);
	public void modificarNombreCategoria(String nombre, long idAV, String nuevoNombre) throws Exception;
	public void eliminarCategoria(String nombre, long idAV) throws Exception;
	public void crearProducto(String nombre, String descripcion, double precio, String categoria, String atributosList, long idAV, int stock) throws Exception;
	public boolean copiarProducto(String nombreProducto, long idAVOrigen, long idAVDestino);
	public boolean moverProductos(List<String> productos, long idAVOrigen, long idAVDestino);
	public void modificarProducto(long idProducto, long idAV, String nombre, String descripcion, double precio, Categoria categoria, List<String> atributos);
	public void setStockProducto(String nombreProd, long idAV, int stock);
	public void cambiarCategoriaProducto(String nuevaCategoria, String producto, long idAV);
	public boolean tienePermiso(String nickname, String idAV);
}