package persistencia;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.hibernate.Session;

import dominio.AV;
import dominio.Nota;
import dominio.Notificacion;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AvDAO implements IAvDAO {
	
		@javax.persistence.PersistenceContext(unitName="Obligatorio")
		private javax.persistence.EntityManager em;

		@Override
		@TransactionAttribute(TransactionAttributeType.REQUIRED)
		public boolean altaAV(AV av) {
			boolean seRegistro = false;
			try {
				em.persist(av);
				String idTenant = "SAPo_" + av.getUsuarioCreador().getNick() + "_" + av.getNombreAV();
				util.DBUtil.crearBase(idTenant);
				
				seRegistro = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return seRegistro;
		}
		
		@Override
		@TransactionAttribute(TransactionAttributeType.REQUIRED)
		public AV traerAV(long id){
			AV av = null;
			try {
				av = em.createNamedQuery("AV.buscarPorId", AV.class)
				.setParameter("idAV", id)
				.getSingleResult();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return av;
		}
		
		

		@Override
		public boolean buscarAV(long id) {//TODO existe
			
			boolean existe=false;
			//AV av = null;
			existe=(traerAV(id)!=null);
			return existe;
		}

		@Override
		public boolean actualizarAV(AV av) {
			boolean seActualizo = false;
			try {
				//Actualiza el en la base de datos.
				em.merge(av);
				seActualizo = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return seActualizo;
		}
		
		
		
		//INVENTO MARIANELA
		public AV traerAvPorNombre(String nombre){
			AV av = null;
			try {
				
				av = em.createNamedQuery("AV.buscarPorNombre", AV.class)
				.setParameter("nombreAV", nombre)
				.getSingleResult();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return av;
			
		}

		
		//falta implementar
		public boolean bajaAV(AV av) {
			boolean baja = false;
			try {
				//em.persist(av);
				String idTenant = "SAPo_" + av.getUsuarioCreador().getNick() + "_" + av.getNombreAV();
				//util.DBUtil.crearBase(idTenant);
				
				baja = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return baja;
		}

		@Override
		public void eliminarAV(String tenant) {
			util.DBUtil.eliminarTenant(tenant);
		}

		@Override
		public void persistirNota(Nota nota, String tenant) {
			try {
				Session session = util.DBUtil.crearSession(tenant);
				session.beginTransaction();
				session.persist(nota);
				session.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public Nota buscarNota(long idNota, String tenant) {
			Session session = util.DBUtil.crearSession(tenant);
			org.hibernate.Query q = session.getNamedQuery("Nota.buscarPorId").setParameter("idNota", idNota);
		    
			return (Nota) q.uniqueResult();
		}

		@Override
		public void eliminarNota(long idNota, String tenant) {
			try {
				Nota nota = buscarNota(idNota, tenant);
				if( nota != null ) {
					Session session = util.DBUtil.crearSession(tenant);
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
				Session session = util.DBUtil.crearSession(tenant);
				session.beginTransaction();
				session.persist(noti);
				session.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public Notificacion buscarNotificacion(long idNoti, String tenant) {
			Session session = util.DBUtil.crearSession(tenant);
			org.hibernate.Query q = session.getNamedQuery("Notificacion.buscarPorId").setParameter("idNotificacion", idNoti);
		    
			return (Notificacion) q.uniqueResult();
		}

		@Override
		public void actualizarNotificacion(Notificacion noti, String tenant) {
			try {
				Session session = util.DBUtil.crearSession(tenant);
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
				if( noti != null ) {
					Session session = util.DBUtil.crearSession(tenant);
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
			Session session = util.DBUtil.crearSession(tenant);
			org.hibernate.Query q = session.getNamedQuery("Nota.getAll");
		    
			return q.list();
		}

		@Override
		public List<Object> getAllNotificaciones(String tenant) {
			Session session = util.DBUtil.crearSession(tenant);
			org.hibernate.Query q = session.getNamedQuery("Notificacion.getAll");
		    
			return q.list();
		}
		

}
