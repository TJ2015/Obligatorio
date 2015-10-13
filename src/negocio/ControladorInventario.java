package negocio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dominio.AV;
import dominio.Atributo;
import dominio.Categoria;
import dominio.Producto;
import persistencia.AvDAO;
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
		
		AV av = avDAO.traerAV(idAV);
		
		if( av != null) {
			
			cat.setAv(av);
			invDAO.crearCategoria(cat);
			av.addCategoria(cat);
			avDAO.actualizarAV(av);
		}
		
		return true;
	}

	/*@Override
	public boolean crearCategoria(String nombre) {
		// TODO Auto-generated method stub
		return false;
	}*/

	@Override
	public boolean existeCategoria(String nombre, long idAV) {
		//if (nombreAV!= "0"){
		AV av = avDAO.traerAV(idAV);
		if(av != null) {
			List <Categoria> listCat = av.getCategorias();
			for(Categoria i : listCat) {
				if(i.getNombre() == nombre) {
					return true;
				}
			}
		}
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
	public void crearProducto(String nombre, String descripcion, double precio, String categoria, String atributosList, long idAV, int stock) throws Exception{
		
		AV av = avDAO.traerAV(idAV);
		
		List<Categoria> cats = av.getCategorias();
		Categoria cat = null;
		for(Categoria c : cats ) {
			if( c.getNombre().equals(categoria)) {
				cat = c;
			}
		}
		
		if(cat == null)
			throw new Exception("No existe la categoria '" + categoria + "'");
		
		List<Atributo> attrs = util.Serializador.convertirDesdeString(atributosList);
			
		Producto prod = new Producto(nombre, descripcion, precio, cat, attrs,stock);
		prod.setCategoria(cat);
		
		prod.setIdAV(idAV);
			
		invDAO.persistirProductoDescripcion(prod);
		
		cat.addProducto(prod);
		invDAO.actualizarCategoria(cat);
		
	}

	@Override
	public boolean copiarProductoGenerico(long idProducto, long idAV) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void modificarProducto(long idProducto, long idAV, String nombre, String descripcion, double precio,
			Categoria categoria, List<String> atributos) {
		// TODO Auto-generated method stub

	}

	@Override
	public void modificarProductoDescripcion(long idProducto, long idAV, String nombre, String descripcion,
			double precio, Categoria categoria, List<String> atributos) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setStockProducto(String nombreProd, long idAV, int stock) {
		
		AV av= avDAO.traerAV(idAV);
		
		List<Categoria> cat;
		cat = av.getCategorias();
		
		List<Producto> prod=null;
		String nombreProducto;
				
		for(Categoria c: cat){
			for(Producto p: prod){
				nombreProducto=p.getNombre();
				if(nombreProducto==nombreProd){
					p.setStock(stock);
				}
			}	
			
		}
		
		
		//prod.setStock(stock);		
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
