package persistencia.implementacion;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.hibernate.Query;
import org.hibernate.Session;

import dominio.ProductoAComprar;
import dominio.log.Accion;
import dominio.log.Log;
import dominio.log.Objetivo;
import persistencia.interfases.ILogDAO;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class LogDAO implements ILogDAO {

	private Session session;

	@Override
	public void openTenant(String tenant) {
		session = util.DBUtil.crearSession(tenant);
	}

	@Override
	public void closeTenant(String tenant) {
		session.getSessionFactory().close();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean persistirLog(Log log) {
		boolean crea = false;
		try {
			session.beginTransaction();
			session.persist(log);
			session.getTransaction().commit();
			crea = true;

		} catch (Exception e) {
			System.out.println("No se guarda el Log");
		}
		return crea;
	}

	@Override
	public List<Log> getAllLogs() {
		Query q = session.getNamedQuery("Log.findAll");
		return q.list();
	}

	@Override
	public Accion buscarAccionPorNombre(String nom) {
		Query q = session.getNamedQuery("Accion.buscarPorNombre").setParameter("nombre", nom);
		return (Accion) q.uniqueResult();
	}

	@Override
	public Objetivo buscarObjetivoPorNombre(String nom) {
		Query q = session.getNamedQuery("Objetivo.buscarPorNombre").setParameter("nombre", nom);
		return (Objetivo) q.uniqueResult();
	}

	@Override
	public List<Accion> getAllAccion() {
		Query q = session.getNamedQuery("Accion.findAll");
		return q.list();
	}

	@Override
	public List<Objetivo> getAllObjetivo() {
		Query q = session.getNamedQuery("Objetivo.findAll");
		return q.list();
	}

}
