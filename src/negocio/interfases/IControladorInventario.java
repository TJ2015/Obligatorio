package negocio.interfases;
import java.util.List;

import javax.ejb.Local;

import org.primefaces.model.UploadedFile;

import dominio.datatypes.DataCategoria;
import dominio.datatypes.DataProducto;
import dominio.datatypes.DataProductoAComprar;
import exceptions.NoExisteElAV;
import exceptions.NoExisteElProducto;
import exceptions.NoExisteElProductoAComprar;
import exceptions.NoExisteLaCategoria;
import exceptions.YaExisteElProductoAComprar;

@Local
public interface IControladorInventario {
	
	public boolean crearCategoria(String nickUsuario, String nombre, long idAV);
	public boolean existeCategoria(String nombre, long idAV);
	public boolean existeProducto(String nombre, long idAV);
	public void modificarNombreCategoria(String nickUsuario, String nombre, long idAV, String nuevoNombre) throws Exception;
	public void eliminarCategoria(String nickUsuario, String nombre, long idAV) throws Exception;
	public DataProducto crearProducto(String nickUsuario, String nombre, String descripcion, double precio, String categoria, String atributosList, long idAV, int stock, UploadedFile file) throws Exception;
	public boolean copiarProducto(String nickUsuario, String nombreProducto, long idAVOrigen, long idAVDestino) throws Exception;
	public boolean moverProducto(String nickUsuario, String producto, long idAVOrigen, long idAVDestino) throws Exception;
	public boolean moverProductos(String nickUsuario, List<String> productos, long idAVOrigen, long idAVDestino);
	public void modificarProducto(String nickUsuario, String nombreProd, long idAV, String nuevoNombre, String descripcion, double precio, String atributos) throws Exception;
	public void setStockProducto(String nickUsuario, String nombreProd, long idAV, int stock);

	public void cambiarCategoriaProducto(String nickUsuario, String nuevaCategoria, String producto, long idAV);
	public void eliminarProducto(String nickUsuario, String nombre, long idAV);
	public List<DataCategoria> mostrarListaCategoria(long idAV) throws Exception;
	public DataCategoria getCategoria(String nombreCat, long idAV) throws NoExisteElAV;
	
	public DataProducto getProducto(String nombre, long idAV) throws NoExisteElAV, NoExisteElProducto;
	public DataProducto getProducto(String nombre) throws NoExisteElProducto;
	
	public void agregarEnListaDeCompra(String nickUsuario, long idAV, String producto, int cantidad) throws NoExisteElAV, NoExisteElProducto, YaExisteElProductoAComprar;
	public void agregarEnListaDeCompra(String nickUsuario, long idAV, String producto, int cantidad, boolean reemplazar) throws NoExisteElAV, NoExisteElProducto, YaExisteElProductoAComprar;
	public void eliminarProductoDeListaDeCompra(String nickUsuario, long idAV, long idProdComp) throws NoExisteElAV, NoExisteElProductoAComprar;
	public void productoComprado(String nickUsuario, long idAV, long idProdComp) throws NoExisteElAV, NoExisteElProductoAComprar;
	public List<DataProductoAComprar> getListaDeCompra(long idAV) throws NoExisteElAV;
	public DataProductoAComprar getProductoAComprar(long idAV, long idProdComp) throws NoExisteElAV, NoExisteElProductoAComprar;
	
	public List<DataProducto> getProductos(long idAV);
	
	public List<DataProducto> recomendarProductos(String nickname);
	void cambiarImagenProducto(String nickUsuario, UploadedFile file, String producto, long idAV); 

	public List<String> obtenerNombresProductos(String nickUsuario, String nombreAV);
	public List<String> obtenerNombresProductosGenericos();

	
}