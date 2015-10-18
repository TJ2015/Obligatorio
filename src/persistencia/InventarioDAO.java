package persistencia;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import dominio.Categoria;
//import dominio.Usuario;
import dominio.Producto;
import dominio.Usuario;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class InventarioDAO implements IInventarioDAO {
		
	@javax.persistence.PersistenceContext(unitName="Obligatorio")
	private javax.persistence.EntityManager em;
		
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void crearCategoria(Categoria cat){
		try {
			//Persiste un categoria a la base de datos
			em.persist(cat);
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void eliminarCategoria(Categoria cat) {
		try {
			em.remove(cat);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void persistirProductoDescripcion(Producto pd) {
		try {
			//Persiste un categoria a la base de datos
			em.persist(pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void actualizarCategoria(Categoria obj) {
		try {
			//Persiste un categoria a la base de datos
			em.persist(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void actualizarProductoDescripcion(Producto pd) {
		try {
			//Persiste un categoria a la base de datos
			em.persist(pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void eliminarProducto(Producto pd) {
		try {
			//Persiste un categoria a la base de datos
			em.remove(pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	

	@Override
	public Producto encontrarProducto(String nombreProd, long idProd) {
		Producto prod = null;
		try {
			//Se busca el usuario en la base 
			//usuario = em.find(Usuario.class, nick);
			prod = em.createNamedQuery("Producto.buscarPorId", Producto.class)
			.setParameter("nombreProd", nombreProd)
			.setParameter("idProd", idProd)
			.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prod;
	}

	@Override
	public Categoria encontrarCategoria(long idCat) {
		Categoria cat = null;
		try {
			//Se busca el usuario en la base 
			//usuario = em.find(Usuario.class, nick);
			cat = em.createNamedQuery("Categoria.buscarPorId", Categoria.class)
			.setParameter("idCat", idCat)
			.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cat;
	}
}


