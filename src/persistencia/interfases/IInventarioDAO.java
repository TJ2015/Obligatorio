package persistencia.interfases;

import java.util.List;

import javax.ejb.Local;

import dominio.Categoria;
import dominio.Producto;
import dominio.ProductoAComprar;

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

	public List<Categoria> buscarListaCategoriaspoAV(long idAV, String tenant);

	public void persistirProductoAComprar(ProductoAComprar pac, String tenant);

	public ProductoAComprar buscarProductoDeLista(long idProdComp, String tenant);

	public void eliminarProductoAComprar(ProductoAComprar pac, String tenant);

	public List<ProductoAComprar> getAllProductoAComprar(String tenant);

	public ProductoAComprar buscarProductoDeListaPorProducto(Long idProducto, String tenant);

	void open(String tenant);

	void close(String tenant);

	public List<Producto> getAllProducto(String tenant);

	public void actualizarProductoAComprar(ProductoAComprar pacAux);
	
	/*********************************************************************/
	
	public List<String> buscarNombresProductos(String tenant);
	public List<String> buscarNombresProductosGenericos();

	
}
