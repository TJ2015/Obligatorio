package persistencia.implementacion;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.hibernate.Session;

import dominio.log.Log;
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

	

}
