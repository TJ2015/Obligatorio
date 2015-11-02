package negocio.interfases;
import java.util.List;

import javax.ejb.Local;

import dominio.datatypes.DataCategoria;
import dominio.datatypes.DataProducto;
import dominio.datatypes.DataProductoAComprar;
import exceptions.NoExisteElAV;
import exceptions.NoExisteElProducto;
import exceptions.NoExisteElProductoAComprar;
import exceptions.YaExisteElProductoAComprar;

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
	public DataCategoria getCategoria(String nombreCat, long idAV) throws NoExisteElProducto;
	
	public DataProducto getProducto(String nombre, long idAV) throws NoExisteElAV, NoExisteElProducto;
	public DataProducto getProducto(String nombre) throws NoExisteElProducto;
	
	public void agregarEnListaDeCompra(long idAV, String producto, int cantidad) throws NoExisteElAV, NoExisteElProducto, YaExisteElProductoAComprar;
	public void agregarEnListaDeCompra(long idAV, String producto, int cantidad, boolean reemplazar) throws NoExisteElAV, NoExisteElProducto, YaExisteElProductoAComprar;
	public void eliminarProductoDeListaDeCompra(long idAV, long idProdComp) throws NoExisteElAV, NoExisteElProductoAComprar;
	public void productoComprado(long idAV, long idProdComp) throws NoExisteElAV, NoExisteElProductoAComprar;
	public List<DataProductoAComprar> getListaDeCompra(long idAV) throws NoExisteElAV;
	public DataProductoAComprar getProductoAComprar(long idAV, long idProdComp) throws NoExisteElAV, NoExisteElProductoAComprar;
	
}