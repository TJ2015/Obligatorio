package negocio.implementacion;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.primefaces.model.UploadedFile;

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
import negocio.interfases.AlgoritmoDeRecomendacion;
import negocio.interfases.AlgoritmoDeRecomendacionSimple;
import negocio.interfases.IControladorInventario;
import persistencia.implementacion.InventarioDAO;
import persistencia.interfases.IAvDAO;
import persistencia.interfases.IInventarioDAO;
import util.Imagenes;

@Stateless
public class ControladorInventario implements IControladorInventario {
	@EJB // BORRAR CUANDO NO SE USE
	private IAvDAO avDAO;

	private IInventarioDAO invDAO = new InventarioDAO();

	public ControladorInventario() {
	}

	@Override
	public boolean crearCategoria(String nombre, long idAV) {
		Categoria cat = new Categoria(nombre);

		String tenant = getTenant(idAV);
		if (tenant != null) {
			invDAO.open(tenant);
			Categoria cate = invDAO.buscarCategoria(nombre, tenant);
			if (cate != null)
				return false;

			invDAO.persistirCategoria(cat, tenant);
			invDAO.close(tenant);
		} else {
			return false;
		}

		return true;
	}

	@Override
	public boolean existeCategoria(String nombre, long idAV) {

		boolean existe = false;

		String tenant = getTenant(idAV);
		if (tenant != null) {
			invDAO.open(tenant);
			existe = invDAO.buscarCategoria(nombre, tenant) != null;
			invDAO.close(tenant);
		}

		return existe;
	}

	@Override
	public void modificarNombreCategoria(String nombre, long idAV, String nuevoNombre) throws Exception {
		if (existeCategoria(nombre, idAV)) {
			String tenant = getTenant(idAV);
			if (tenant != null) {
				invDAO.open(tenant);
				Categoria cat = invDAO.buscarCategoria(nombre, tenant);
				cat.setNombre(nuevoNombre);
				invDAO.actualizarCategoria(cat, tenant);
				invDAO.close(tenant);
			}
		} else {
			throw new Exception("No existe la categoría");
		}
	}

	@Override
	public void eliminarCategoria(String nombre, long idAV) throws Exception {
		if (existeCategoria(nombre, idAV)) {
			String tenant = getTenant(idAV);
			if (tenant != null) {
				invDAO.open(tenant);
				Categoria cat = invDAO.buscarCategoria(nombre, tenant);
				invDAO.eliminarCategoria(cat, tenant);
				invDAO.close(tenant);
			}
		} else {
			throw new Exception("No existe la categoría");
		}
	}

	@Override
	public void crearProducto(String nombre, String descripcion, double precio, String categoria, String atributosList,
			long idAV, int stock, UploadedFile file) throws Exception {

		String tenant = getTenant(idAV);
		if (tenant != null) {
			
			Categoria cat = null;
			List<Atributo> attrs = util.Serializador.convertirDesdeString(atributosList);
			byte[] imagen = Imagenes.convertirInputStreamToArrayByte(file);
			String nombreImagen = Imagenes.obtenerNombreImagen(file);
			
			Producto prod = new Producto(nombre, descripcion, precio, cat, attrs, stock, imagen, nombreImagen);
			invDAO.open(tenant);
			cat = invDAO.buscarCategoria(categoria, tenant);

			if (cat == null)
				throw new Exception("No existe la categoria '" + categoria + "'");

			prod.setCategoria(cat);
			invDAO.persistirProducto(prod, tenant);
			cat.addProducto(prod);
			invDAO.actualizarCategoria(cat, tenant);
			invDAO.close(tenant);
		}
	}

	@Override
	public void setStockProducto(String nombreProd, long idAV, int stock) {
		String tenant = getTenant(idAV);
		if (tenant != null) {
			invDAO.open(tenant);
			Producto prod = invDAO.buscarProducto(nombreProd, tenant);
			prod.setStock(stock);
			invDAO.actualizarProducto(prod, tenant);
			invDAO.close(tenant);
		}
	}

	@Override
	public void cambiarCategoriaProducto(String categoria, String producto, long idAV) {
		String tenant = getTenant(idAV);
		if (tenant != null) {
			invDAO.open(tenant);
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
			invDAO.close(tenant);
		}
	}

	@Override
	public void modificarProducto(String nombreProd, long idAV, String nombre, String descripcion, double precio,
			String atributos) throws Exception {

		String tenant = getTenant(idAV);
		if (tenant != null) {
			invDAO.open(tenant);
			if (invDAO.buscarProducto(nombre, tenant) == null) {
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
			invDAO.close(tenant);
		}

	}

	@Override
	public boolean copiarProducto(String nombreProducto, long idAVOrigen, long idAVDestino) throws Exception {

		if (idAVOrigen == idAVDestino) {
			throw new Exception("El AV de origen y destino son el mismo.");
		}

		String tenantOrigen = getTenant(idAVOrigen);
		String tenantDestino = getTenant(idAVDestino);

		if ((tenantOrigen != null) && (tenantDestino != null)) {

			invDAO.open(tenantOrigen);

			Producto prodOriginal = invDAO.buscarProducto(nombreProducto, tenantOrigen);
			invDAO.open(tenantDestino);

			if (prodOriginal == null)
				throw new exceptions.NoExisteElProducto();

			Categoria cat = invDAO.buscarCategoria(prodOriginal.getCategoria().getNombre(), tenantDestino);

			if (cat == null) {
				cat = new Categoria(prodOriginal.getCategoria().getNombre());
				invDAO.persistirCategoria(cat, tenantDestino);
			}

			List<Atributo> attrs = util.Serializador.convertirDesdeString(prodOriginal.getAtributos());

			Producto prodNuevo = new Producto(prodOriginal.getNombre(), prodOriginal.getDescripcion(),
					prodOriginal.getPrecio(), cat, attrs, prodOriginal.getStock());

			invDAO.persistirProducto(prodNuevo, tenantDestino);
			cat.addProducto(prodNuevo);
			invDAO.actualizarCategoria(cat, tenantDestino);

			invDAO.close(tenantOrigen);
			invDAO.close(tenantDestino);

			return true;
		}

		return false;
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

		String tenant = getTenant(idAV);
		if (tenant != null) {
			invDAO.open(tenant);
			Producto prod = invDAO.buscarProducto(nombre, tenant);

			Categoria cat = prod.getCategoria();
			cat.removeProducto(prod);

			invDAO.actualizarCategoria(cat, tenant);
			invDAO.eliminarProducto(prod, tenant);
			invDAO.close(tenant);
		}

	}

	@Override
	public List<DataCategoria> mostrarListaCategoria(long idAV) throws Exception {
		List<Categoria> cats = new ArrayList<>();
		List<DataCategoria> dcats = new ArrayList<>();
		DataCategoria dc = null;

		String tenant = getTenant(idAV);
		if (tenant != null) {
			invDAO.open(tenant);
			cats = invDAO.buscarListaCategoriaspoAV(idAV, tenant);
			for (Categoria cat : cats) {
				dc = cat.getDataCategoria();
				dcats.add(dc);
			}
			invDAO.close(tenant);
		}

		return dcats;
	}

	@Override
	public DataCategoria getCategoria(String nombreCat, long idAV) throws NoExisteElAV {
		String tenant = getTenant(idAV);
		DataCategoria dataCat = null;
		if (tenant != null) {
			invDAO.open(tenant);
			Categoria cat = invDAO.buscarCategoria(nombreCat, tenant);

			if (cat != null)
				dataCat = cat.getDataCategoria();

			invDAO.close(tenant);
		} else {
			throw new exceptions.NoExisteElAV();
		}

		return dataCat;
	}

	@Override
	public void agregarEnListaDeCompra(long idAV, String producto, int cantidad)
			throws NoExisteElAV, NoExisteElProducto, YaExisteElProductoAComprar {
		agregarEnListaDeCompra(idAV, producto, cantidad, false);
	}

	@Override
	public void agregarEnListaDeCompra(long idAV, String producto, int cantidad, boolean reemplazar)
			throws NoExisteElAV, NoExisteElProducto, YaExisteElProductoAComprar {

		String tenant = getTenant(idAV);
		if (tenant != null) {
			invDAO.open(tenant);
			Producto prod = invDAO.buscarProducto(producto, tenant);

			if (prod != null) {

				List<ProductoAComprar> pacs = invDAO.getAllProductoAComprar(tenant);
				ProductoAComprar pacAux = null;

				for (ProductoAComprar p : pacs) {
					if (p.getProducto().getIdProducto() == prod.getIdProducto()) {
						pacAux = p;
						break;
					}
				}

				if (pacAux != null) {
					if (reemplazar) {
						invDAO.eliminarProductoAComprar(pacAux, tenant);
					} else {
						throw new exceptions.YaExisteElProductoAComprar();
					}
				}

				ProductoAComprar pac = new ProductoAComprar(prod, cantidad);

				invDAO.persistirProductoAComprar(pac, tenant);
				invDAO.close(tenant);
			} else {
				throw new exceptions.NoExisteElProducto();
			}

		} else {
			throw new exceptions.NoExisteElAV();
		}

	}

	@Override
	public void eliminarProductoDeListaDeCompra(long idAV, long idProdComp)
			throws NoExisteElAV, NoExisteElProductoAComprar {
		String tenant = getTenant(idAV);
		if (tenant != null) {
			invDAO.open(tenant);
			ProductoAComprar pac = invDAO.buscarProductoDeLista(idProdComp, tenant);

			if (pac != null) {
				invDAO.eliminarProductoAComprar(pac, tenant);
			} else {
				throw new exceptions.NoExisteElProductoAComprar();
			}
			invDAO.close(tenant);
		} else {
			throw new exceptions.NoExisteElAV();
		}
	}

	@Override
	public void productoComprado(long idAV, long idProdComp) throws NoExisteElAV, NoExisteElProductoAComprar {
		String tenant = getTenant(idAV);
		if (tenant != null) {
			invDAO.open(tenant);
			ProductoAComprar pac = invDAO.buscarProductoDeLista(idProdComp, tenant);
			eliminarProductoDeListaDeCompra(idAV, idProdComp);
			Producto prod = pac.getProducto();
			setStockProducto(prod.getNombre(), idAV, prod.getStock() + pac.getCantidad());
			invDAO.close(tenant);
		}
	}

	@Override
	public List<DataProductoAComprar> getListaDeCompra(long idAV) throws NoExisteElAV {
		String tenant = getTenant(idAV);
		if (tenant != null) {
			invDAO.open(tenant);
			List<ProductoAComprar> pacs = invDAO.getAllProductoAComprar(tenant);
			List<DataProductoAComprar> dpacs = new ArrayList<>();

			for (ProductoAComprar pc : pacs) {
				dpacs.add(pc.getDataProductoAComprar());
			}
			invDAO.close(tenant);
			return dpacs;
		} else {
			throw new exceptions.NoExisteElAV();
		}
	}

	@Override
	public DataProductoAComprar getProductoAComprar(long idAV, long idProdComp)
			throws NoExisteElAV, NoExisteElProductoAComprar {
		DataProductoAComprar dpac = null;
		String tenant = getTenant(idAV);
		if (tenant != null) {
			invDAO.open(tenant);
			ProductoAComprar pac = invDAO.buscarProductoDeLista(idProdComp, tenant);

			if (pac != null) {
				dpac = pac.getDataProductoAComprar();
			} else {
				throw new exceptions.NoExisteElProductoAComprar();
			}
			invDAO.close(tenant);
		} else {
			throw new exceptions.NoExisteElAV();
		}

		return dpac;
	}

	private String getTenant(long idAV) {
		if (idAV > 0) {
			AV av = avDAO.traerAV(idAV);
			if (av != null) {
				return av.getUsuarioCreador().getNick() + "_" + av.getNombreAV();
			} else {
				return null;
			}
		} else {
			return "master";
		}
	}

	@Override
	public DataProducto getProducto(String nombre) throws NoExisteElProducto {
		return getProducto(nombre, "sapo_master");
	}

	@Override
	public DataProducto getProducto(String nombre, long idAV) throws NoExisteElAV, NoExisteElProducto {
		String tenant = getTenant(idAV);
		if (tenant != null) {
			return getProducto(nombre, tenant);
		} else {
			throw new exceptions.NoExisteElAV();
		}
	}

	private DataProducto getProducto(String nombre, String tenant) throws NoExisteElProducto {
		DataProducto dp = null;
		invDAO.open(tenant);
		Producto prod = invDAO.buscarProducto(nombre, tenant);

		if (prod != null) {
			dp = prod.getDataProducto();
		} else {
			throw new exceptions.NoExisteElProducto();
		}
		invDAO.close(tenant);
		return dp;
	}

	@Override
	public List<DataProducto> getProductos(long idAV) {
		List<Producto> prods = null;
		List<DataProducto> dprods = new ArrayList<>();
		String tenant = getTenant(idAV);
		if (tenant != null) {
			invDAO.open(tenant);
			prods = invDAO.getAllProducto(tenant);
			for( Producto p : prods ) {
				dprods.add(p.getDataProducto());
			}
			invDAO.close(tenant);
		}
		return dprods;
	}

	@Override
	public List<DataProducto> recomendarProductos(String nickname) {
		AlgoritmoDeRecomendacion algo = new AlgoritmoDeRecomendacionSimple();
		return algo.recomendar(nickname);
	}
	
}
