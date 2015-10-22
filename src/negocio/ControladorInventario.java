package negocio;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dominio.AV;
import dominio.Atributo;
import dominio.Categoria;
import dominio.Producto;
import persistencia.IAvDAO;
import persistencia.IInventarioDAO;

@Stateless
public class ControladorInventario implements IControladorInventario {
	@EJB
	private IInventarioDAO invDAO;
	@EJB
	private IAvDAO avDAO;

	public ControladorInventario() {
	}

	@Override
	public boolean crearCategoria(String nombre, long idAV) {
		Categoria cat = new Categoria(nombre);
		
		if( existeCategoria(nombre, idAV)) {
			if( idAV > 0) {
				AV av = avDAO.traerAV(idAV);
				if( av != null) {
					
					invDAO.persistirCategoria(cat, av.getUsuarioCreador().getNick() + "_" + av.getNombreAV() );
				} else {
					return false;
				}
			} else {
				invDAO.persistirCategoria(cat);
			}
		} else {
			return false;
		}
			
		return true;
	}

	@Override
	public boolean existeCategoria(String nombre, long idAV) {
		
		if( idAV > 0) {
			AV av = avDAO.traerAV(idAV);
			
			if(av != null) {
				return invDAO.buscarCategoria(nombre, av.getUsuarioCreador().getNick() + "_" + av.getNombreAV()) != null;
			} else {
				return false;
			}
		} else {
			return invDAO.buscarCategoria(nombre) != null;
		}
	}

	@Override
	public void modificarNombreCategoria(String nombre, long idAV, String nuevoNombre) throws Exception {
		if( existeCategoria(nombre, idAV)) {
			if( idAV > 0) {
				AV av = avDAO.traerAV(idAV);
				if( av != null) {
					String tenant = av.getUsuarioCreador().getNick() + "_" + av.getNombreAV();
					Categoria cat = invDAO.buscarCategoria(nombre, tenant);
					cat.setNombre(nuevoNombre);
					invDAO.actualizarCategoria(cat, tenant);
				}
			} else {
				Categoria cat = invDAO.buscarCategoria(nombre);
				cat.setNombre(nuevoNombre);
				invDAO.persistirCategoria(cat);
			}
		} else {
			throw new Exception("No existe la categoría");
		}		
	}

	@Override
	public void eliminarCategoria(String nombre, long idAV) throws Exception {
		if( existeCategoria(nombre, idAV)) {
			if( idAV > 0) {
				AV av = avDAO.traerAV(idAV);
				if( av != null) {
					String tenant = av.getUsuarioCreador().getNick() + "_" + av.getNombreAV();
					Categoria cat = invDAO.buscarCategoria(nombre, tenant);
					invDAO.eliminarCategoria(cat, tenant);
				}
			} else {
				Categoria cat = invDAO.buscarCategoria(nombre);
				invDAO.eliminarCategoria(cat);
			}
		} else {
			throw new Exception("No existe la categoría");
		}
	}
	
	
	@Override
	public void crearProducto(String nombre, String descripcion, double precio, String categoria, String atributosList, long idAV, int stock) throws Exception{
		
		String tenant = "";
		Categoria cat = null;
		
		List<Atributo> attrs = util.Serializador.convertirDesdeString(atributosList);
		
		Producto prod = new Producto(nombre, descripcion, precio, cat, attrs,stock);
		prod.setIdAV(idAV);
		
		if( idAV > 0 ) {
			AV av = avDAO.traerAV(idAV);
			tenant = av.getUsuarioCreador().getNick() + "_" + av.getNombreAV();
			cat = invDAO.buscarCategoria(categoria, tenant);
			
			if(cat == null)
				throw new Exception("No existe la categoria '" + categoria + "'");
			
			prod.setCategoria(cat);
			invDAO.persistirProducto(prod, tenant);
			cat.addProducto(prod);
			invDAO.actualizarCategoria(cat, tenant);
			
		} else {
			cat = invDAO.buscarCategoria(categoria);
			
			if(cat == null)
				throw new Exception("No existe la categoria '" + categoria + "'");
			
			prod.setCategoria(cat);
			invDAO.persistirProducto(prod);
			cat.addProducto(prod);
			invDAO.actualizarCategoria(cat);
					
		}
	}

	@Override
	public void setStockProducto(String nombreProd, long idAV, int stock) {
		if( idAV > 0 ) {
			AV av = avDAO.traerAV(idAV);
			String tenant = av.getUsuarioCreador().getNick() + "_" + av.getNombreAV();
			
			Producto prod = invDAO.buscarProducto(nombreProd, tenant);
			prod.setStock(stock);
			
			invDAO.actualizarProducto(prod, tenant);
			
		} else {
			Producto prod = invDAO.buscarProducto(nombreProd);
			prod.setStock(stock);
			invDAO.actualizarProducto(prod);
		}
	}
	
	//TODO TERMINAR!
	@Override
	public void cambiarCategoriaProducto(String categoria, String producto, long idAV) {
		if( idAV > 0 ) {
			AV av = avDAO.traerAV(idAV);
			String tenant = av.getUsuarioCreador().getNick() + "_" + av.getNombreAV();
			
			Producto prod = invDAO.buscarProducto(producto, tenant);
			
			List<Producto> prods = prod.getCategoria().getProductos();
			
			for( Producto p : prods) {
				
			}
			
			invDAO.actualizarProducto(prod, tenant);
			
		} else {
			Producto prod = invDAO.buscarProducto(producto);
			invDAO.actualizarProducto(prod);
		}
	}

	@Override
	public boolean tienePermiso(String nickname, String idAV) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	public boolean crearCategoria2(String nombre, String nombreAV){
		
	Categoria cat = new Categoria(nombre);
		
		AV av = avDAO.traerAvPorNombre(nombreAV);
		
		
		if( av != null) {
			
			//cat.setAv(av);
			invDAO.persistirCategoria(cat);
			//av.addCategoria(cat);
			avDAO.actualizarAV(av);
		}
		
		return true;
	}

	@Override
	public void modificarProducto(long idProducto, long idAV, String nombre, String descripcion, double precio,
			Categoria categoria, List<String> atributos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean copiarProducto(String nombreProducto, long idAVOrigen, long idAVDestino) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean moverProductos(List<String> productos, long idAVOrigen, long idAVDestino) {
		// TODO Auto-generated method stub
		return false;
	}

}
