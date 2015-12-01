package negocio.implementacion;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.primefaces.model.UploadedFile;

import dominio.AV;
import dominio.Alerta;
import dominio.Atributo;
import dominio.Categoria;
import dominio.Condicion;
import dominio.Notificacion;
import dominio.Producto;
import dominio.ProductoAComprar;
import dominio.datatypes.DataCategoria;
import dominio.datatypes.DataProducto;
import dominio.datatypes.DataProductoAComprar;
import dominio.datatypes.log.DataLog;
import exceptions.NoExisteElAV;
import exceptions.NoExisteElProducto;
import exceptions.NoExisteElProductoAComprar;
import exceptions.YaExisteElProductoAComprar;
import negocio.interfases.IControladorAlgoritmos;
import negocio.interfases.IControladorInventario;
import negocio.interfases.IControladorLog;
import persistencia.implementacion.FabricaDAO;
import persistencia.interfases.IAvDAO;
import persistencia.interfases.IInventarioDAO;
import util.Imagenes;

@Stateless
public class ControladorInventario implements IControladorInventario {
	@EJB // BORRAR CUANDO NO SE USE
	private IAvDAO avDAO;

	private IInventarioDAO invDAO = FabricaDAO.getInventarioDAO();
	private IAvDAO	avDAOTenant = FabricaDAO.getAvDAO();
	
	@EJB
	private IControladorLog cLog;
	
	public ControladorInventario() {
	}

	@Override
	public boolean crearCategoria(String nickUsuario, String nombre, long idAV) {
		
		if ( existeCategoria(nombre, idAV) )
			return false;
		
		String tenant = getTenant(idAV);
		if (tenant != null) {
			invDAO.open(tenant);
			Categoria cate = invDAO.buscarCategoria(nombre, tenant);

			Categoria cat = new Categoria(nombre);
			invDAO.persistirCategoria(cat, tenant);
			invDAO.close(tenant);
			
			/************************************************************/
			long idCategoria = cat.getIdCategoria();
			DataLog dataLog = new DataLog(idCategoria, nickUsuario, IControladorLog.CREAR, IControladorLog.CATEGORIA, nombre);
			cLog.agregarLog(dataLog, tenant);
			/************************************************************/
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
	public void modificarNombreCategoria(String nickUsuario, String nombre, long idAV, String nuevoNombre) throws Exception {
		if (existeCategoria(nombre, idAV)) {
			String tenant = getTenant(idAV);
			if (tenant != null) {
				invDAO.open(tenant);
				Categoria cat = invDAO.buscarCategoria(nombre, tenant);
				cat.setNombre(nuevoNombre);
				invDAO.actualizarCategoria(cat, tenant);
				invDAO.close(tenant);
				
				/************************************************************/
				long idCategoria = cat.getIdCategoria();
				DataLog dataLog = new DataLog(idCategoria, nickUsuario, IControladorLog.MODIFICAR, IControladorLog.CATEGORIA, nombre + " por " + nuevoNombre);
				cLog.agregarLog(dataLog, tenant);
				/************************************************************/
			}
		} else {
			throw new Exception("No existe la categoría");
		}
	}

	@Override
	public void eliminarCategoria(String nickUsuario, String nombre, long idAV) throws Exception {
		if (existeCategoria(nombre, idAV)) {
			String tenant = getTenant(idAV);
			if (tenant != null) {
				
				invDAO.open(tenant);
				Categoria cat = invDAO.buscarCategoria(nombre, tenant);
				long idCategoria = cat.getIdCategoria();
				invDAO.eliminarCategoria(cat, tenant);
				invDAO.close(tenant);
				
				/************************************************************/
				DataLog dataLog = new DataLog(idCategoria, nickUsuario, IControladorLog.ELIMINAR, IControladorLog.CATEGORIA, nombre);
				cLog.agregarLog(dataLog, tenant);
				/************************************************************/
			}
		} else {
			throw new Exception("No existe la categoría");
		}
	}

	@Override
	public DataProducto crearProducto(String nickUsuario, String nombre, String descripcion, double precio, String categoria, String atributosList,
			long idAV, int stock, UploadedFile file) throws Exception {

		DataProducto dp = null;
		
		if( existeProducto(nombre, idAV) )
			return null;
		
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
			dp = prod.getDataProducto();
			invDAO.close(tenant);
			
			/************************************************************/
			long idProducto = prod.getIdProducto();
			DataLog dataLog = new DataLog(idProducto, nickUsuario, IControladorLog.CREAR, IControladorLog.PRODUCTO, prod.toString());
			cLog.agregarLog(dataLog, tenant);
			/************************************************************/
			
		}
		return dp;
	}

	@Override
	public void setStockProducto(String nickUsuario, String nombreProd, long idAV, int stock) {
		String tenant = getTenant(idAV);
		if (tenant != null) {
			invDAO.open(tenant);
			Producto prod = invDAO.buscarProducto(nombreProd, tenant);
			prod.setStock(stock);
			invDAO.actualizarProducto(prod, tenant);
			invDAO.close(tenant);
			
			avDAOTenant.open(tenant);
			List<Alerta> alertas = avDAOTenant.getAllAlerta();
			Condicion cond;
			int val;
			String condicion = "";
			boolean crear = false;
			for( Alerta a : alertas ) {
				if( a.getProd().getNombre().equals(nombreProd) ) {
					cond = a.getCond();
					if( cond.getAtributo().toLowerCase().equals("stock") ) {
						val = Integer.valueOf(cond.getValor());
						switch(cond.getCondicional()) {
						case MENOR:
							if( stock < val ) {
								condicion = "menor";
								crear = true;
							}
							break;
						case MENOR_O_IGUAL:
							if( stock <= val ) {
								condicion = "menor o igual";
								crear = true;
							}
							break;
						case IGUAL:
							if( stock == val ) {
								condicion = "igual";
								crear = true;
							}
							break;
						case MAYOR:
							if( stock > val ) {
								condicion = "mayor";
								crear = true;
							}
							break;
						case MAYOR_O_IGUAL:
							if( stock >= val ) {
								condicion = "mayor o igual";
								crear = true;
							}
							break;
						}
					}
					
					if( crear ) {
						Notificacion noti = new Notificacion("El stock del producto " + nombreProd + " es " + condicion + "!");
						avDAOTenant.persistirNotificacion(noti, tenant);
						avDAOTenant.close(tenant);
					}
				}
			}
			
		}
	}

	@Override
	public void cambiarCategoriaProducto(String nickUsuario, String categoria, String producto, long idAV) {
		String tenant = getTenant(idAV);
		if (tenant != null) {
			invDAO.open(tenant);
			Producto prod = invDAO.buscarProducto(producto, tenant);
			
			String produtco = prod.toString();
			
			Categoria catNueva = invDAO.buscarCategoria(categoria, tenant);
			Categoria catVieja = prod.getCategoria();

			List<Producto> prods = catVieja.getProductos();
			prods.remove(prod);
			prod.getCategoria().setProductos(prods);
			prod.setCategoria(catNueva);
			catNueva.addProducto(prod);
			
			/************************************************************/
			long idProducto = prod.getIdProducto();
			DataLog dataLog = new DataLog(idProducto, nickUsuario, IControladorLog.MODIFICAR, IControladorLog.PRODUCTO, producto + " por la " + catNueva.toString());
			cLog.agregarLog(dataLog, tenant);
			/************************************************************/

			invDAO.actualizarProducto(prod, tenant);
			invDAO.actualizarCategoria(catVieja, tenant);
			invDAO.actualizarCategoria(catNueva, tenant);
			invDAO.close(tenant);
		}
	}
	@Override
	public void cambiarImagenProducto(String nickUsuario, UploadedFile file, String producto, long idAV){
		String tenant = getTenant(idAV);
		if (tenant != null) {
			invDAO.open(tenant);
			Producto prod = invDAO.buscarProducto(producto, tenant);
			String produtco = prod.toString();
			
			byte[] imagen = Imagenes.convertirInputStreamToArrayByte(file);
			String nombreImagen = Imagenes.obtenerNombreImagen(file);
			
			prod.setBytesImagen(imagen);
			prod.setNombreImagen(nombreImagen);
			
			invDAO.actualizarProducto(prod, tenant);
			String productoNuevo = prod.toString();
			
			/************************************************************/
			long idProducto = prod.getIdProducto();
			DataLog dataLog = new DataLog(idProducto, nickUsuario, IControladorLog.MODIFICAR, IControladorLog.PRODUCTO, produtco + " por " + productoNuevo);
			cLog.agregarLog(dataLog, tenant);
			/************************************************************/
			invDAO.close(tenant);
		}
	}


	@Override
	public void modificarProducto(String nickUsuario, String nombreProd, long idAV, String nombre, String descripcion, double precio,
			String atributos) throws Exception {

		String tenant = getTenant(idAV);
		if (tenant != null) {
			invDAO.open(tenant);
			if ((invDAO.buscarProducto(nombre, tenant) == null)||(nombreProd.equals(nombre))) {
				
				Producto prod = invDAO.buscarProducto(nombreProd, tenant);
				String productoViejo = prod.toString();

				prod.setNombre(nombre);
				prod.setDescripcion(descripcion);
				prod.setPrecio(precio);

				List<Atributo> attrs = util.Serializador.convertirDesdeString(atributos);

				prod.setAtributosList(attrs);

				invDAO.actualizarProducto(prod, tenant);
				String productoNuevo = prod.toString();
				
				/************************************************************/
				long idProducto = prod.getIdProducto();
				DataLog dataLog = new DataLog(idProducto, nickUsuario, IControladorLog.MODIFICAR, IControladorLog.PRODUCTO, productoViejo + " por " + productoNuevo);
				cLog.agregarLog(dataLog, tenant);
				/************************************************************/
			} else {
				throw new Exception("Ya existe un producto con ese nombre.");
			}
			invDAO.close(tenant);
		}

	}

	@Override
	public boolean copiarProducto(String nickUsuario, String nombreProducto, long idAVOrigen, long idAVDestino) throws Exception {

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
					prodOriginal.getPrecio(), cat, attrs, prodOriginal.getStock(), prodOriginal.getBytesImagen(), prodOriginal.getNombreImagen());
			

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
	public boolean moverProducto(String nickUsuario, String producto, long idAVOrigen, long idAVDestino) throws Exception {
		try {
			copiarProducto(nickUsuario, producto, idAVOrigen, idAVDestino);
			eliminarProducto(nickUsuario, producto, idAVOrigen);
		} catch (Exception e) {
			throw new Exception("Error al mover el producto " + producto + ". Causa: " + e.getMessage());
		}

		return true;
	}

	@Override
	public boolean moverProductos(String nickUsuario, List<String> productos, long idAVOrigen, long idAVDestino) {
		boolean OK = true;
		for (String prod : productos) {
			try {
				moverProducto(nickUsuario, prod, idAVOrigen, idAVDestino);
			} catch (Exception e) {
				OK = false;
				e.printStackTrace();
			}
		}
		return OK;
	}

	@Override
	public void eliminarProducto(String nickUsuario, String nombre, long idAV) {

		String tenant = getTenant(idAV);
		IInventarioDAO invDAO = FabricaDAO.getInventarioDAO();
		
		if (tenant != null) {
			invDAO.open(tenant);
			Producto prod = invDAO.buscarProducto(nombre, tenant);

			Categoria cat = prod.getCategoria();
			cat.removeProducto(prod);
				
			ProductoAComprar pac = invDAO.buscarProductoDeListaPorProducto(prod.getIdProducto(), tenant);
			try {
				if( pac != null )
					eliminarProductoDeListaDeCompra(nickUsuario, idAV, prod.getIdProducto());
			} catch (Exception e) {
				
			}
			
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
	public void agregarEnListaDeCompra(String nickUsuario, long idAV, String producto, int cantidad)
			throws NoExisteElAV, NoExisteElProducto, YaExisteElProductoAComprar {
		agregarEnListaDeCompra(nickUsuario, idAV, producto, cantidad, false);
	}

	@Override
	public void agregarEnListaDeCompra(String nickUsuario, long idAV, String producto, int cantidad, boolean reemplazar)
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
						pacAux.setCantidad(cantidad);
					} else {
						pacAux.setCantidad(pacAux.getCantidad() + cantidad);
					}
					invDAO.actualizarProductoAComprar(pacAux);
				} else {
					ProductoAComprar pac = new ProductoAComprar(prod, cantidad);
					invDAO.persistirProductoAComprar(pac, tenant);
				}
				invDAO.close(tenant);
			} else {
				throw new exceptions.NoExisteElProducto();
			}

		} else {
			throw new exceptions.NoExisteElAV();
		}

	}

	@Override
	public void eliminarProductoDeListaDeCompra(String nickUsuario, long idAV, long idProdComp)
			throws NoExisteElAV, NoExisteElProductoAComprar {
		String tenant = getTenant(idAV);
		if (tenant != null) {
			invDAO.open(tenant);
			ProductoAComprar pac = invDAO.buscarProductoDeLista(idProdComp, tenant);
			if (pac != null) {
				String producto = pac.toString();
				invDAO.eliminarProductoAComprar(pac, tenant);
				
				/************************************************************/
				long idProducto = pac.getId();
				DataLog dataLog = new DataLog(idProducto, nickUsuario, IControladorLog.ELIMINAR_EN_LISTA, IControladorLog.PRODUCTO, producto);
				cLog.agregarLog(dataLog, tenant);
				/************************************************************/
			} else {
				throw new exceptions.NoExisteElProductoAComprar();
			}
			invDAO.close(tenant);
		} else {
			throw new exceptions.NoExisteElAV();
		}
	}

	@Override
	public void productoComprado(String nickUsuario, long idAV, long idProdComp) throws NoExisteElAV, NoExisteElProductoAComprar {
		
		String tenant = getTenant(idAV);
		IInventarioDAO invDAO = FabricaDAO.getInventarioDAO();
		if (tenant != null) {
			invDAO.open(tenant);
			ProductoAComprar pac = invDAO.buscarProductoDeListaPorProducto(idProdComp, tenant);
			eliminarProductoDeListaDeCompra(nickUsuario, idAV, pac.getId());
			Producto prod = pac.getProducto();
			String producto = pac.toString();
			setStockProducto(nickUsuario, prod.getNombre(), idAV, prod.getStock() + pac.getCantidad());
			
			/************************************************************/
			long idProducto = pac.getId();
			DataLog dataLog = new DataLog(idProducto, nickUsuario, IControladorLog.COMPRAR, IControladorLog.PRODUCTO, producto);
			cLog.agregarLog(dataLog, tenant);
			/************************************************************/
			
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
		return getProducto(nombre, "master");
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
	public List<DataProducto> getProductosOrdenadosPorStock(long idAV) {
		List<DataProducto> lDataProductos = null;
		try {
			String tenant = getTenant(idAV);
			if (tenant != null) {
				invDAO.open(tenant);
				List<Producto> lProductos = invDAO.getProductosOrdenadosPorStock(tenant);
				if (lProductos != null && lProductos.size() > 0) {
					lDataProductos = new ArrayList<>();
					for(Producto p : lProductos) {
						lDataProductos.add(p.getDataProducto());
					}
				}
				invDAO.close(tenant);
			}			
		} catch (Exception e) {
			System.out.println("Error al obtener la lista de productos ordenados por stock del Almacen " + idAV);
		}
		return lDataProductos;
	}

	@Override
	public List<DataProducto> recomendarProductos(String nickname) {
		IControladorAlgoritmos algo = new ControladorAlgoritmos();
		return algo.recomendar(nickname);
	}
	
	@Override
	public List<String> obtenerNombresProductos(String nickUsuario, String nombreAV){
		List<String> lNombres = null;
		try {
			String tenant = nickUsuario + "_" + nombreAV; 
			invDAO.open(tenant);
			lNombres = invDAO.buscarNombresProductos(tenant);
			invDAO.close(tenant);
		} catch (Exception e) {
			System.out.println("No se obtienen nombre de AV para el nick de Usuario " + nickUsuario);
		}
		return lNombres;
	}
	
	@Override
	public List<String> obtenerNombresProductosGenericos(){
		List<String> lNombres = null;
		try {
			lNombres = invDAO.buscarNombresProductosGenericos();
		} catch (Exception e) {
			System.out.println("No se obtienen nombre de Productos Genericos");
		}
		return lNombres;
	}

	@Override
	public boolean existeProducto(String nombre, long idAV) {
		Producto prod = null;
		String tenant = getTenant(idAV);
		if (tenant != null) {
			invDAO.open(tenant);
			prod = invDAO.buscarProducto(nombre, tenant);
			invDAO.close(tenant);
		}
		return prod != null;
	}
	
}
