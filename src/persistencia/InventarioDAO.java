package persistencia;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.hibernate.Query;
import org.hibernate.Session;

import dominio.Categoria;
//import dominio.Usuario;
import dominio.Producto;
import dominio.ProductoAComprar;

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
	public void persistirProducto(Producto pd) {
		try {
			//Persiste un categoria a la base de datos
			em.persist(pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void persistirProducto(Producto pd, String tenant) {
		try {
			Session session = util.DBUtil.crearSession(tenant);
			
			session.beginTransaction();
			session.persist(pd);
			session.getTransaction().commit();
			
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
	public void actualizarCategoria(Categoria cat, String tenant) {
		try {
			Session session = util.DBUtil.crearSession(tenant);

			session.beginTransaction();
			session.merge(cat);
			session.getTransaction().commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void actualizarProducto(Producto pd) {
		try {
			//Persiste un categoria a la base de datos
			em.persist(pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void actualizarProducto(Producto pd, String tenant) {
		try {
			Session session = util.DBUtil.crearSession(tenant);
			
			session.beginTransaction();
			session.merge(pd);
			session.getTransaction().commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Categoria buscarCategoria(String nombreCat, String tenant) {
		Session session = util.DBUtil.crearSession(tenant);
		Query q = session.getNamedQuery("Categoria.buscarPorNombre").setParameter("nombre", nombreCat);
	    
		return (Categoria) q.uniqueResult();
	}
	@Override
	public List <Categoria> buscarListaCategoriaspoAV(long idAV,String tenant) {
		List <Categoria> cats= new ArrayList<>();
		Session session = util.DBUtil.crearSession(tenant);
		Query q = session.getNamedQuery("Categoria.traerTodasCats");
	   // cats=(List<Categoria>) q;//TODO 
		return cats;
	}
	
	@Override
	public Categoria buscarCategoria(String nombreCat) {
		return (Categoria) em.createNamedQuery("Categoria.buscarPorNombre").setParameter("nombre", nombreCat).getSingleResult();
	}

	@Override
	public void eliminarCategoria(Categoria cat) {
		em.merge(cat);
		em.remove(cat);
	}

	@Override
	public void eliminarCategoria(Categoria cat, String tenant) {
		Session session = util.DBUtil.crearSession(tenant);
		session.beginTransaction();
		session.merge(cat);
		session.delete(cat);
		session.getTransaction().commit();
	}

	@Override
	public Producto buscarProducto(String nombreProd) {
		return (Producto) em.createNamedQuery("Producto.buscarPorNombre").setParameter("nombre", nombreProd).getSingleResult();
	}

	@Override
	public Producto buscarProducto(String nombreProd, String tenant) {
		Session session = util.DBUtil.crearSession(tenant);
		Query q = session.getNamedQuery("Producto.buscarPorNombre").setParameter("nombre", nombreProd);
	    
		return (Producto) q.uniqueResult();
	}

	@Override
	public void eliminarProducto(Producto prod) {
		em.merge(prod);
		em.remove(prod);
	}

	@Override
	public void eliminarProducto(Producto prod, String tenant) {
		Session session = util.DBUtil.crearSession(tenant);
		session.beginTransaction();
		session.merge(prod);
		session.delete(prod);
		session.getTransaction().commit();
	}

	@Override
	public void persistirProductoAComprar(ProductoAComprar pac, String tenant) {
		Session session = util.DBUtil.crearSession(tenant);
		session.beginTransaction();
		session.persist(pac);
		session.getTransaction().commit();
	}

	@Override
	public ProductoAComprar buscarProductoDeLista(long idProdComp, String tenant) {
		Session session = util.DBUtil.crearSession(tenant);
		session.beginTransaction();
		return session.get(ProductoAComprar.class, idProdComp);
	}

	@Override
	public void eliminarProductoAComprar(ProductoAComprar pac, String tenant) {
		Session session = util.DBUtil.crearSession(tenant);
		session.beginTransaction();
		session.delete(pac);
		session.getTransaction().commit();
	}

	@Override
	public List<ProductoAComprar> getAllProductoAComprar(String tenant) {
		Session session = util.DBUtil.crearSession(tenant);
		Query q = session.getNamedQuery("ProductoAComprar.getAll");
	    
		return q.list();
	}

	@Override
	public ProductoAComprar buscarProductoDeListaPorProducto(Long idProducto, String tenant) {
		Session session = util.DBUtil.crearSession(tenant);
		Query q = session.getNamedQuery("ProductoAComprar.buscarPorProductoId").setParameter("idProd", idProducto);
		return (ProductoAComprar) q.uniqueResult();
	}

}


