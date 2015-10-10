package negocio;

import java.util.List;

import dominio.Categoria;

public class ControladorInventario implements IControladorInventario {

	@Override
	public boolean crearCategoria(String nombre, long idAV) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean crearCategoria(String nombre) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existeCategoria(String nombre, String nombreAV) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void modificarNombreCategoria(String nombre, long idAV, long idCategoria) {
		// TODO Auto-generated method stub

	}

	@Override
	public void eliminarCategoria(String nombre, long idAV) {
		// TODO Auto-generated method stub

	}
	
	
	@Override
	public boolean crearProductoDescripcion(String nombre, String descripcion, double precio, String categoria,
			List<String> atributos, long idAV) {
		
		return false;
	}

	@Override
	public boolean crearProducto(String nombre, String descripcion, double precio, String categoria,
			List<String> atributos, long idAV) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean copiarProductoGenerico(long idProducto, long idAV) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void modificarProducto(long idProducto, long idAV, String nombre, String descripcion, double precio,
			String categoria, List<String> atributos) {
		// TODO Auto-generated method stub

	}

	@Override
	public void modificarProductoDescripcion(long idProducto, long idAV, String nombre, String descripcion,
			double precio, String categoria, List<String> atributos) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setStockProducto(long idProducto, long idAV, int stock) {
		// TODO Auto-generated method stub

	}

	@Override
	public void cambiarCategoriaProducto(String categoria, String producto, String av) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean tienePermiso(String nickname, String idAV) {
		// TODO Auto-generated method stub
		return false;
	}

}
