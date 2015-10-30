package negocio;
import java.util.List;

import javax.ejb.Local;

import dominio.Atributo;
import dominio.Categoria;
import dominio.datatypes.DataCategoria;
import dominio.datatypes.DataProducto;

@Local
public interface IControladorInventario {
	
	public boolean crearCategoria(String nombre, long idAV);
	public boolean existeCategoria(String nombre, long idAV);
	public void modificarNombreCategoria(String nombre, long idAV, String nuevoNombre) throws Exception;
	public void eliminarCategoria(String nombre, long idAV) throws Exception;
	public void crearProducto(String nombre, String descripcion, double precio, String categoria, String atributosList, long idAV, int stock) throws Exception;
	public boolean copiarProducto(String nombreProducto, long idAVOrigen, long idAVDestino) throws Exception;
	public boolean moverProducto(String producto, long idAVOrigen, long idAVDestino) throws Exception;
	public boolean moverProductos(List<String> productos, long idAVOrigen, long idAVDestino);
	public void modificarProducto(String nombreProd, long idAV, String nombre, String descripcion, double precio, String atributos) throws Exception;
	public void setStockProducto(String nombreProd, long idAV, int stock);
	public void cambiarCategoriaProducto(String nuevaCategoria, String producto, long idAV);
	public void eliminarProducto(String nombre, long idAV);
	public List<DataCategoria> mostrarListaCategoria(long idAV) throws Exception;
	public List<DataProducto> mostrarListaProducto(String nombreCat) throws Exception;
	
}