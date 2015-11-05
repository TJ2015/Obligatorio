package persistencia.implementacion;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.hibernate.Query;
import org.hibernate.Session;

import dominio.LogEntry;
import persistencia.interfases.ILogDAO;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class LogDAO implements ILogDAO {

	@Override
	public void persistirLogEntry(LogEntry le, String tenant) {
		Session session = util.DBUtil.crearSession(tenant);

		session.beginTransaction();
		session.persist(le);
		session.getTransaction().commit();
	}

	@Override
	public void eliminarLogEntry(LogEntry le, String tenant) {
		try {
			Session session = util.DBUtil.crearSession(tenant);

			session.beginTransaction();
			session.merge(le);
			session.delete(le);
			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public LogEntry getLogEntry(long id, String tenant) {
		Session session = util.DBUtil.crearSession(tenant);
		Query q = session.getNamedQuery("LogEntry.buscarPorId").setParameter("id", id);

		return (LogEntry) q.uniqueResult();
	}

	@Override
	public List<LogEntry> getLogEntryList(String tenant, int inicio, int fin) {
		Session session = util.DBUtil.crearSession(tenant);
		Query q = session.getNamedQuery("LogEntry.buscarPorId");

		return q.list();
	}

}
