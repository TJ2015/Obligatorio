package persistencia;

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
	public Categoria buscarCategoria(String nombreCat, String tenant);
	public Categoria buscarCategoria(String nombreCat);
	//public Producto encontrarProducto(String nombreProd, long idAV);
}
