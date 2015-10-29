package persistencia;

import java.util.List;

import javax.ejb.Local;

import dominio.AV;
import dominio.Categoria;
import dominio.Producto;

@Local
public interface IInventarioDAO {

	public void persistirCategoria(Categoria cat);
	public void persistirCategoria(Categoria cat, String tenant);
	public void persistirProducto(Producto pd);
	public void persistirProducto(Producto pd, String tenant);
	public void actualizarCategoria(Categoria obj);
	public void actualizarCategoria(Categoria obj, String tenant);
	public void actualizarProducto(Producto pd);
	public void actualizarProducto(Producto pd, String tenant);
	public Categoria buscarCategoria(String nombreCat);
	public Categoria buscarCategoria(String nombreCat, String tenant);
	public Producto buscarProducto(String nombreProd);
	public Producto buscarProducto(String nombreProd, String tenant);
	public void eliminarCategoria(Categoria cat);
	public void eliminarCategoria(Categoria cat, String tenant);
	public void eliminarProducto(Producto cat);
	public void eliminarProducto(Producto cat, String tenant);
	public List <Categoria> buscarListaCategoriaspoAV(long idAV, String tenant);
	
	//public Producto encontrarProducto(String nombreProd, long idAV);
}
