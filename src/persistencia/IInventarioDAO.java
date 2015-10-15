package persistencia;

import javax.ejb.Local;

import dominio.Categoria;
import dominio.Producto;

@Local
public interface IInventarioDAO {

	public void crearCategoria(Categoria cat);
	public void persistirProductoDescripcion(Producto pd);
	public void actualizarCategoria(Categoria obj);
	public void actualizarProductoDescripcion(Producto pd);
	//public Producto encontrarProducto(String nombreProd, long idAV);

}
