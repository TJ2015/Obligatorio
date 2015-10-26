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
		
		if( !existeCategoria(nombre, idAV)) {
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
	
	@Override
	public void cambiarCategoriaProducto(String categoria, String producto, long idAV) {
		if( idAV > 0 ) {
			AV av = avDAO.traerAV(idAV);
			String tenant = av.getUsuarioCreador().getNick() + "_" + av.getNombreAV();
			
			Producto prod = invDAO.buscarProducto(producto, tenant);
			Categoria catNueva = invDAO.buscarCategoria(categoria, tenant);
			Categoria catVieja = prod.getCategoria();
			
			List<Producto> prods = catVieja.getProductos();
			prods.remove(prod);
			
			prod.getCategoria().setProductos(prods);
			
			prod.setCategoria(catNueva);
			catNueva.addProducto(prod);
			
			invDAO.actualizarProducto(prod, tenant);
			invDAO.actualizarCategoria(catVieja, tenant);
			invDAO.actualizarCategoria(catNueva, tenant);
			
		} else {
			Producto prod = invDAO.buscarProducto(producto);
			Categoria catNueva = invDAO.buscarCategoria(categoria);
			Categoria catVieja = prod.getCategoria();
			
			List<Producto> prods = catVieja.getProductos();
			prods.remove(prod);
			prod.getCategoria().setProductos(prods);
			prod.setCategoria(catNueva);
			catNueva.addProducto(prod);
			
			invDAO.actualizarProducto(prod);
		}
	}	

	@Override
	public void modificarProducto(String nombreProd, long idAV, String nombre, String descripcion, double precio,
			String atributos) throws Exception {
		
		String tenant;
		
		if( idAV > 0 ) {
			AV av = avDAO.traerAV(idAV);
			tenant = av.getUsuarioCreador().getNick() + "_" + av.getNombreAV();
		} else {
			tenant = "master";
		}
		
		if( invDAO.buscarProducto(nombre, tenant) != null ) {
			Producto prod = invDAO.buscarProducto(nombreProd, tenant);
			
			prod.setNombre(nombre);
			prod.setDescripcion(descripcion);
			prod.setPrecio(precio);
			
			List<Atributo> attrs = util.Serializador.convertirDesdeString(atributos);
			
			prod.setAtributosList(attrs);
			
			invDAO.actualizarProducto(prod, tenant);
		} else {
			throw new Exception("Ya existe un producto con ese nombre.");
		}
		
	}

	@Override
	public boolean copiarProducto(String nombreProducto, long idAVOrigen, long idAVDestino) throws Exception {
		String tenantOrigen;
		String tenantDestino;
		
		if( idAVOrigen == idAVDestino ) {
			throw new Exception("El AV de origen y destino son el mismo.");
		}
		
		if( idAVOrigen > 0 ) {
			AV av = avDAO.traerAV(idAVOrigen);
			tenantOrigen = av.getUsuarioCreador().getNick() + "_" + av.getNombreAV();
		} else {
			tenantOrigen = "master";
		}
		
		if( idAVDestino > 0 ) {
			AV av = avDAO.traerAV(idAVDestino);
			tenantDestino = av.getUsuarioCreador().getNick() + "_" + av.getNombreAV();
		} else {
			tenantDestino = "master";
		}
		
		Producto prodOriginal = invDAO.buscarProducto(nombreProducto, tenantOrigen);
		
		Categoria cat = invDAO.buscarCategoria(prodOriginal.getCategoria().getNombre(), tenantDestino);
		
		if( cat == null ) {
			cat = new Categoria(prodOriginal.getCategoria().getNombre());
			invDAO.persistirCategoria(cat, tenantDestino);
		}
		
		List<Atributo> attrs = util.Serializador.convertirDesdeString(prodOriginal.getAtributos());
		
		Producto prodNuevo = new Producto(prodOriginal.getNombre(),prodOriginal.getDescripcion(), prodOriginal.getPrecio(), cat, attrs, prodOriginal.getStock());
		cat.addProducto(prodNuevo);
		
		invDAO.persistirProducto(prodNuevo, tenantDestino);
		invDAO.actualizarCategoria(cat, tenantDestino);
				
		return true;
	}

	@Override
	public boolean moverProducto(String producto, long idAVOrigen, long idAVDestino) throws Exception {
		try {
			copiarProducto(producto, idAVOrigen, idAVDestino);
			eliminarProducto(producto, idAVOrigen);
		} catch (Exception e) {
			throw new Exception("Error al mover el producto " + producto + ". Causa: " + e.getMessage());
		}
		
		return true;
	}
	
	@Override
	public boolean moverProductos(List<String> productos, long idAVOrigen, long idAVDestino) {
		boolean OK = true;
		for( String prod : productos ) {
			try {
				moverProducto(prod, idAVOrigen, idAVDestino);
			} catch (Exception e) {
				OK = false;
				e.printStackTrace();
			}
		}
		return OK;
	}

	@Override
	public void eliminarProducto(String nombre, long idAV) {
		
		String tenant;
		
		if( idAV > 0 ) {
			AV av = avDAO.traerAV(idAV);
			tenant = av.getUsuarioCreador().getNick() + "_" + av.getNombreAV();
		} else {
			tenant = "master";
		}
		
		Producto prod = invDAO.buscarProducto(nombre, tenant);
		
		Categoria cat = prod.getCategoria();
		cat.removeProducto(prod);
		
		invDAO.actualizarCategoria(cat, tenant);
		invDAO.eliminarProducto(prod, tenant);

	}

}
