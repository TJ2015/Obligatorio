package persistencia.implementacion;

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
import persistencia.interfases.IInventarioDAO;

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
		Session session = null;
		try {
			session = util.DBUtil.crearSession(tenant);			
			session.beginTransaction();
			session.persist(cat);
			session.getTransaction().commit();			
			
		} catch (Exception e) {
			if (session != null) {
				session.getTransaction().rollback();
			}
			e.printStackTrace();
		}
		finally{
			util.DBUtil.cerrarFabrica();
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
		Session session = null;
		try {
			session = util.DBUtil.crearSession(tenant);
			session.beginTransaction();
			session.persist(pd);
			session.getTransaction().commit();
			
		} catch (Exception e) {
			if (session != null) {
				session.getTransaction().rollback();
			}
			e.printStackTrace();
		}
		finally{
			util.DBUtil.cerrarFabrica();
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
		Session session = null;
		try {
			session = util.DBUtil.crearSession(tenant);
			session.beginTransaction();
			session.merge(cat);
			session.getTransaction().commit();
			
		} catch (Exception e) {
			if (session != null) {
				session.getTransaction().rollback();
			}
			e.printStackTrace();
		}
		finally{
			util.DBUtil.cerrarFabrica();
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
		Session session = null;
		try {
			session = util.DBUtil.crearSession(tenant);
			session.beginTransaction();
			session.merge(pd);
			session.getTransaction().commit();
		} 
		catch (Exception e) {
			if (session != null) {
				session.getTransaction().rollback();
			}
			e.printStackTrace();
		}
		finally{
			util.DBUtil.cerrarFabrica();
		}
	}

	@Override
	public Categoria buscarCategoria(String nombreCat, String tenant) {
		Categoria categoria = null;
		try {
			Session session = util.DBUtil.crearSession(tenant);
			session.beginTransaction();
			Query q = session.getNamedQuery("Categoria.buscarPorNombre").setParameter("nombre", nombreCat);
			session.getTransaction().commit();
		    Object resultado = q.uniqueResult();
			categoria = (Categoria) resultado;
			util.DBUtil.cerrarFabrica();
		} catch (Exception e) {
			System.out.println("No Existe la Categoria: " + nombreCat);
		}
		return categoria;
	}
	
	@Override
	public Categoria buscarCategoria(String nombreCat) {
		Categoria categoria = null;
		try {
			categoria = (Categoria) em.createNamedQuery("Categoria.buscarPorNombre")
					.setParameter("nombre", nombreCat)
					.getSingleResult();
		} catch (Exception e) {
			System.out.println("No Existe la Categoria: " + nombreCat);
		}
		return categoria;
	}

	@Override
	public void eliminarCategoria(Categoria cat) {
		try {
			em.merge(cat);
		} catch (Exception e) {
			System.out.println("Error en 'MERGE' al Eminiar la categoria" + cat);
		}
		try {
			em.remove(cat);
		} catch (Exception e) {
			System.out.println("Error en 'REMOVE' al Eminiar la categoria" + cat);
		}
		
	}

	@Override
	public void eliminarCategoria(Categoria cat, String tenant) 
	{
		Session session = null;
		try {
			session = util.DBUtil.crearSession(tenant);
			session.beginTransaction();
			session.merge(cat);
			session.delete(cat);
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session != null) {
				session.getTransaction().rollback();
			}
			e.printStackTrace();
		}
		finally{
			util.DBUtil.cerrarFabrica();
		}
	}

	@Override
	public Producto buscarProducto(String nombreProd) {
		return (Producto) em.createNamedQuery("Producto.buscarPorNombre").setParameter("nombre", nombreProd).getSingleResult();
	}

	@Override
	public Producto buscarProducto(String nombreProd, String tenant) 
	{
		Producto producto = null;
		try {
			Session session = util.DBUtil.crearSession(tenant);
			Query q = session.getNamedQuery("Categoria.buscarPorNombre").setParameter("nombre", nombreProd);
			producto = (Producto) q.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return producto;
	}

	@Override
	public void eliminarProducto(Producto prod) {
		em.merge(prod);
		em.remove(prod);
	}

	@Override
	public void eliminarProducto(Producto prod, String tenant) {
		Session session = null;
		try {
			session = util.DBUtil.crearSession(tenant);
			session.beginTransaction();
			session.merge(prod);
			session.delete(prod);
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session != null) {
				session.getTransaction().rollback();
			}
			e.printStackTrace();
		}
		finally{
			util.DBUtil.cerrarFabrica();
		}
		
	}

}


