package negocio;

import dominio.AV;
import dominio.Atributo;
import dominio.Categoria;
import persistencia.IAvDAO;
import persistencia.IInventarioDAO;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class ControladorInventario implements IControladorInventario {
	@EJB
	private IInventarioDAO invDAO;
	@EJB
	private IAvDAO avDAO;

	public ControladorInventario() {
	}

	@Override
	public boolean crearCategoria(String nombre, String nombreAV) {
		
		Categoria cat=new Categoria(nombre);
		invDAO.crearCategoria(cat);
		
		/*AV av= avDAO.traerAV(nombreAV);
		List <Categoria> listcat=av.getCategorias();
		listcat.add(cat);//nuevo
		av.setCategorias(listcat);	
		
		/*if(existeCategoria(nombre,nombreAV)){
			return false;
		}
		else{
			//Crear categoria
			if(!existeCategoria(nombre,nombreAV)){
				Categoria cat=new Categoria(nombre);
				invDAO.crearCategoria(cat);
				if (avDAO.buscarAV(nombreAV)){
					AV av= avDAO.traerAV(nombreAV);
					List <Categoria> listcat=av.getCategorias();
					listcat.add(cat);//nuevo
					av.setCategorias(listcat);	
					
				}
			
			}
			
			
		}*/
		
		// TODO Auto-generated method stub
		return true;
	}

	/*@Override
	public boolean crearCategoria(String nombre) {
		// TODO Auto-generated method stub
		return false;
	}*/

	@Override
	public boolean existeCategoria(String nombre, String nombreAV) {
		//if (nombreAV!= "0"){
			AV av= avDAO.traerAV(nombreAV);
			if(av!=null){
				List <Categoria> listCat=av.getCategorias();
				for(Categoria i:listCat){
					if(i.getNombre()==nombre){
						return true;
					}
				}
			} else return false;
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
	public boolean crearProductoDescripcion(String nombre, String descripcion, double precio, Categoria categoria, List<Atributo> atributosList, long idAV){
		return false;
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean crearProducto(String nombre, String descripcion, double precio, Categoria categoria,
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
			Categoria categoria, List<String> atributos) {
		// TODO Auto-generated method stub

	}

	@Override
	public void modificarProductoDescripcion(long idProducto, long idAV, String nombre, String descripcion,
			double precio, Categoria categoria, List<String> atributos) {
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
