package negocio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dominio.AV;
import dominio.Atributo;
import dominio.Categoria;
import dominio.Producto;
import dominio.ProductoAComprar;
import dominio.datatypes.DataCategoria;
import dominio.datatypes.DataProducto;
import dominio.datatypes.DataProductoAComprar;
import exceptions.NoExisteElAV;
import exceptions.NoExisteElProducto;
import exceptions.NoExisteElProductoAComprar;
import exceptions.YaExisteElProductoAComprar;
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

		if (!existeCategoria(nombre, idAV)) {
			if (idAV > 0) {
				AV av = avDAO.traerAV(idAV);
				if (av != null) {

					invDAO.persistirCategoria(cat, av.getUsuarioCreador().getNick() + "_" + av.getNombreAV());
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

		if (idAV > 0) {
			AV av = avDAO.traerAV(idAV);

			if (av != null) {
				return invDAO.buscarCategoria(nombre,
						av.getUsuarioCreador().getNick() + "_" + av.getNombreAV()) != null;
			} else {
				return false;
			}
		} else {
			return invDAO.buscarCategoria(nombre) != null;
		}
	}

	@Override
	public void modificarNombreCategoria(String nombre, long idAV, String nuevoNombre) throws Exception {
		if (existeCategoria(nombre, idAV)) {
			if (idAV > 0) {
				AV av = avDAO.traerAV(idAV);
				if (av != null) {
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
		if (existeCategoria(nombre, idAV)) {
			if (idAV > 0) {
				AV av = avDAO.traerAV(idAV);
				if (av != null) {
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
	public void crearProducto(String nombre, String descripcion, double precio, String categoria, String atributosList,
			long idAV, int stock) throws Exception {

		String tenant = "";
		Categoria cat = null;

		List<Atributo> attrs = util.Serializador.convertirDesdeString(atributosList);

		Producto prod = new Producto(nombre, descripcion, precio, cat, attrs, stock);

		if (idAV > 0) {
			AV av = avDAO.traerAV(idAV);
			tenant = av.getUsuarioCreador().getNick() + "_" + av.getNombreAV();
			cat = invDAO.buscarCategoria(categoria, tenant);

			if (cat == null)
				throw new Exception("No existe la categoria '" + categoria + "'");

			prod.setCategoria(cat);
			invDAO.persistirProducto(prod, tenant);
			cat.addProducto(prod);
			invDAO.actualizarCategoria(cat, tenant);

		} else {
			cat = invDAO.buscarCategoria(categoria);

			if (cat == null)
				throw new Exception("No existe la categoria '" + categoria + "'");

			prod.setCategoria(cat);
			invDAO.persistirProducto(prod);
			cat.addProducto(prod);
			invDAO.actualizarCategoria(cat);

		}
	}

	@Override
	public void setStockProducto(String nombreProd, long idAV, int stock) {
		if (idAV > 0) {
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
		if (idAV > 0) {
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

		if (idAV > 0) {
			AV av = avDAO.traerAV(idAV);
			tenant = av.getUsuarioCreador().getNick() + "_" + av.getNombreAV();
		} else {
			tenant = "master";
		}

		if (invDAO.buscarProducto(nombre, tenant) != null) {
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

		if (idAVOrigen == idAVDestino) {
			throw new Exception("El AV de origen y destino son el mismo.");
		}

		if (idAVOrigen > 0) {
			AV av = avDAO.traerAV(idAVOrigen);
			tenantOrigen = av.getUsuarioCreador().getNick() + "_" + av.getNombreAV();
		} else {
			tenantOrigen = "master";
		}

		if (idAVDestino > 0) {
			AV av = avDAO.traerAV(idAVDestino);
			tenantDestino = av.getUsuarioCreador().getNick() + "_" + av.getNombreAV();
		} else {
			tenantDestino = "master";
		}

		Producto prodOriginal = invDAO.buscarProducto(nombreProducto, tenantOrigen);

		Categoria cat = invDAO.buscarCategoria(prodOriginal.getCategoria().getNombre(), tenantDestino);

		if (cat == null) {
			cat = new Categoria(prodOriginal.getCategoria().getNombre());
			invDAO.persistirCategoria(cat, tenantDestino);
		}

		List<Atributo> attrs = util.Serializador.convertirDesdeString(prodOriginal.getAtributos());

		Producto prodNuevo = new Producto(prodOriginal.getNombre(), prodOriginal.getDescripcion(),
				prodOriginal.getPrecio(), cat, attrs, prodOriginal.getStock());
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
		for (String prod : productos) {
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

		if (idAV > 0) {
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

	@Override
	public List<DataCategoria> mostrarListaCategoria(long idAV) throws Exception {
		List<Categoria> cats = new ArrayList<>();
		List<DataCategoria> dcats = new ArrayList<>();
		DataCategoria dc = null;

		AV av = avDAO.traerAV(idAV);
		if (av != null) {
			String tenant = av.getUsuarioCreador().getNick() + "_" + av.getNombreAV();
			// cats = invDAO.buscarListaCategoriaspoAV(idAV, tenant);
			for (Categoria cat : cats) {
				dc = cat.getDataCategoria();
				dcats.add(dc);
			}

		}

		return dcats;
	}

	@Override
	public List<DataProducto> mostrarListaProducto(String nombreCat) throws Exception {
		// TODO hay q trear la categoria
		List<DataProducto> dprods = new ArrayList<>();
		/*
		 * if( cat != null) { String tenant = av.getUsuarioCreador().getNick() +
		 * "_" + av.getNombreAV(); //cats =
		 * invDAO.buscarListaProducto(nombreCat, tenant); for(Producto
		 * prod:prods){ dp=cat.getDataProducto(); dcats.add(dp); }
		 * 
		 * }
		 */

		return dprods;
	}
	
	@Override
	public void agregarEnListaDeCompra(long idAV, String producto, int cantidad) throws NoExisteElAV, NoExisteElProducto, YaExisteElProductoAComprar {
		agregarEnListaDeCompra(idAV, producto, cantidad, false);
	}
	
	@Override
	public void agregarEnListaDeCompra(long idAV, String producto, int cantidad, boolean reemplazar) throws NoExisteElAV, NoExisteElProducto, YaExisteElProductoAComprar {
		
		String tenant = getTenant(idAV);
		if( tenant != null) {
			Producto prod = invDAO.buscarProducto(producto, tenant);
			
			if( prod != null ) {
				
				List<ProductoAComprar> pacs = invDAO.getAllProductoAComprar(tenant);
				ProductoAComprar pacAux = null;
				
				for( ProductoAComprar p : pacs ) {
					if( p.getProducto().getIdProducto() == prod.getIdProducto() ) {
						pacAux = p;
						break;
					}
				}
				
				if( pacAux != null ) {
					if( reemplazar ) {
						invDAO.eliminarProductoAComprar(pacAux, tenant);
					} else {
						throw new exceptions.YaExisteElProductoAComprar();
					}
				} 
				
				ProductoAComprar pac = new ProductoAComprar(prod, cantidad);
				
				invDAO.persistirProductoAComprar(pac, tenant);
			} else {
				throw new exceptions.NoExisteElProducto();
			}
			
		} else {
			throw new exceptions.NoExisteElAV();
		}
		
	}

	@Override
	public void eliminarProductoDeListaDeCompra(long idAV, long idProdComp) throws NoExisteElAV, NoExisteElProductoAComprar {
		String tenant = getTenant(idAV);
		if( tenant != null) {
			ProductoAComprar pac = invDAO.buscarProductoDeLista(idProdComp, tenant);
			
			if( pac != null ) {
				invDAO.eliminarProductoAComprar(pac, tenant);
			} else {
				throw new exceptions.NoExisteElProductoAComprar();
			}
		} else {
			throw new exceptions.NoExisteElAV();
		}
	}
	
	@Override
	public void productoComprado(long idAV, long idProdComp) throws NoExisteElAV, NoExisteElProductoAComprar {
		String tenant = getTenant(idAV);
		ProductoAComprar pac = invDAO.buscarProductoDeLista(idProdComp, tenant);
		eliminarProductoDeListaDeCompra(idAV, idProdComp);
		Producto prod = pac.getProducto();
		setStockProducto(prod.getNombre(), idAV, prod.getStock() + pac.getCantidad());
	}

	@Override
	public List<DataProductoAComprar> getListaDeCompra(long idAV) throws NoExisteElAV {
		String tenant = getTenant(idAV);
		if( tenant != null) {
			List<ProductoAComprar> pacs = invDAO.getAllProductoAComprar(tenant);
			List<DataProductoAComprar> dpacs = new ArrayList<>();
			
			for( ProductoAComprar pc : pacs ) {
				dpacs.add(pc.getDataProductoAComprar());
			}
			
			return dpacs;
		} else {
			throw new exceptions.NoExisteElAV();
		}
	}

	@Override
	public DataProductoAComprar getProductoAComprar(long idAV, long idProdComp) throws NoExisteElAV, NoExisteElProductoAComprar {
		String tenant = getTenant(idAV);
		if( tenant != null) {
			ProductoAComprar pac = invDAO.buscarProductoDeLista(idProdComp, tenant);
			
			if( pac != null ) {
				return pac.getDataProductoAComprar();
			} else {
				throw new exceptions.NoExisteElProductoAComprar();
			}
		} else {
			throw new exceptions.NoExisteElAV();
		}
	}
	
	private String getTenant(long idAV) {
		AV av = avDAO.traerAV(idAV);
		if( av != null) {
			return av.getUsuarioCreador().getNick() + "_" + av.getNombreAV();
		} else {
			return null;
		}
	}
	
	@Override
	public DataProducto getProducto(String nombre) throws NoExisteElProducto {
		return getProducto(nombre, "sapo_master");
	}
	
	@Override
	public DataProducto getProducto(String nombre, long idAV) throws NoExisteElAV, NoExisteElProducto {
		String tenant = getTenant(idAV);
		if( tenant != null) {
			return getProducto(nombre, tenant);
		} else {
			throw new exceptions.NoExisteElAV();
		}
	}
	
	private DataProducto getProducto(String nombre, String tenant) throws NoExisteElProducto {
		Producto prod = invDAO.buscarProducto(nombre, tenant);

		if( prod != null ) {
			return prod.getDataProducto();
		} else {
			throw new exceptions.NoExisteElProducto();
		}
		
	}
}
