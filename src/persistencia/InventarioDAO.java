package persistencia;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.hibernate.Session;

import dominio.Categoria;
//import dominio.Usuario;
import dominio.Producto;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class InventarioDAO implements IInventarioDAO {
		
	@javax.persistence.PersistenceContext(unitName="Obligatorio")
	private javax.persistence.EntityManager em;
		
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void persistirCategoria(Categoria cat){
		try {
			//Persiste un categoria a la base de datos
			em.persist(cat);
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void persistirCategoria(Categoria cat, String tenant){
		try {
			Session session = util.DBUtil.crearSession(tenant);
			
			session.beginTransaction();
			session.persist(cat);
			session.getTransaction().commit();
			
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
	
	/*@Override
	public Producto encontrarProducto(String nombreProd, long idAV) {
		Producto prod = null;
		try {
			//Se busca el usuario en la base 
			//usuario = em.find(Usuario.class, nick);
			prod = em.createNamedQuery("Producto.buscarPorId", Producto.class)
			.setParameter("nombreProd", nombreProd)
			.setParameter("idAV", idAV)
			.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prod;
	}*/

}


