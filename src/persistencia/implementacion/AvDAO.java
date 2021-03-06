package persistencia.implementacion;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.hibernate.Session;

import dominio.AV;
import dominio.Alerta;
import dominio.Nota;
import dominio.Notificacion;
import dominio.Usuario;
import persistencia.implementacion.AvDAO;
import persistencia.interfases.IAvDAO;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AvDAO implements IAvDAO {

	@javax.persistence.PersistenceContext(unitName = "Obligatorio")
	private javax.persistence.EntityManager em;

	private Session session;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean altaAV(AV av) {
		boolean seRegistro = false;
		try {
			em.persist(av);
			String idTenant = "sapo_" + av.getUsuarioCreador().getNick() + "_" + av.getNombreAV();
			util.DBUtil.crearBase(idTenant);

			seRegistro = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return seRegistro;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public AV traerAV(long id) {
		AV av = null;
		try {
			av = em.createNamedQuery("AV.buscarPorId", AV.class).setParameter("idAV", id).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return av;
	}

	@Override
	public boolean buscarAV(long id) {// TODO existe

		boolean existe = false;
		// AV av = null;
		existe = (traerAV(id) != null);
		return existe;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean actualizarAV(AV av) {
		boolean seActualizo = false;
		try {
			// Actualiza el en la base de datos.
			em.merge(av);
			seActualizo = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return seActualizo;
	}

	// INVENTO MARIANELA
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public AV traerAvPorNombre(String nombre) {
		AV av = null;
		try {

			av = em.createNamedQuery("AV.buscarPorNombre", AV.class).setParameter("nombreAV", nombre).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return av;

	}

	// falta implementar
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean bajaAV(AV av) {
		boolean baja = false;
		try {
			// em.persist(av);
			String idTenant = "sapo_" + av.getUsuarioCreador().getNick() + "_" + av.getNombreAV();
			// util.DBUtil.crearBase(idTenant);

			baja = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return baja;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void eliminarAV(String tenant, AV av) {
		util.DBUtil.eliminarTenant(tenant);
		try {
			em.merge(av);
			em.remove(av);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void persistirNota(Nota nota, String tenant) {
		try {
			session.beginTransaction();
			session.persist(nota);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Nota buscarNota(long idNota, String tenant) {
		org.hibernate.Query q = session.getNamedQuery("Nota.buscarPorId").setParameter("idNota", idNota);

		return (Nota) q.uniqueResult();
	}

	@Override
	public void eliminarNota(long idNota, String tenant) {
		try {
			Nota nota = buscarNota(idNota, tenant);
			if (nota != null) {
				session.beginTransaction();
				session.merge(nota);
				session.delete(nota);
				session.getTransaction().commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void persistirNotificacion(Notificacion noti, String tenant) {
		try {
			session.beginTransaction();
			session.persist(noti);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Notificacion buscarNotificacion(long idNoti, String tenant) {
		org.hibernate.Query q = session.getNamedQuery("Notificacion.buscarPorId").setParameter("idNotificacion",
				idNoti);

		return (Notificacion) q.uniqueResult();
	}

	@Override
	public void actualizarNotificacion(Notificacion noti, String tenant) {
		try {
			session.beginTransaction();
			session.merge(noti);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void eliminarNotificacion(long idNoti, String tenant) {
		try {
			Notificacion noti = buscarNotificacion(idNoti, tenant);
			if (noti != null) {
				session.beginTransaction();
				session.merge(noti);
				session.delete(noti);
				session.getTransaction().commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Object> getAllNotas(String tenant) {
		org.hibernate.Query q = session.getNamedQuery("Nota.getAll");

		return q.list();
	}

	@Override
	public List<Object> getAllNotificaciones(String tenant) {
		org.hibernate.Query q = session.getNamedQuery("Notificacion.getAll");

		return q.list();
	}

	@Override
	public void open(String tenant) {
		session = util.DBUtil.crearSession(tenant);
	}

	@Override
	public void close(String tenant) {
		session.getSessionFactory().close();
	}

	@Override
	public void persistirAlerta(Alerta alerta) {
		try {
			session.beginTransaction();
			session.persist(alerta);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Alerta buscarAlerta(long idAlerta) {
		try {
			return session.get(Alerta.class, idAlerta);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void eliminarAlerta(Alerta alerta) {
		try {
			session.beginTransaction();
			session.merge(alerta);
			session.delete(alerta);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Alerta> getAllAlerta() {
		org.hibernate.Query q = session.getNamedQuery("Alerta.getAll");
		return q.list();
	}

	@Override
	public List<Notificacion> buscarNotificacionesNoLeidas() {
		org.hibernate.Query q = session.getNamedQuery("Notificacion.getAllNoLeido");
		return q.list();
	}

}
