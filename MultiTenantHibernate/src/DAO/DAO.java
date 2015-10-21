package DAO;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import dominio.Usuario;
import util.CurrentTenantIdentifierResolverImpl;
import util.DBUtil;

@Local
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DAO {
	
	public void persistir() {
		
		DBUtil.crearBase("concho");
		//CurrentTenantIdentifierResolverImpl._tenantIdentifier.set("concho");
		/*SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.withOptions().tenantIdentifier( "concho" ).openSession();
		//Session session = factory.openSession();
		session.beginTransaction();
		
		Usuario usu = new Usuario("Concho");
		
		session.persist(usu);
		session.getTransaction().commit();	*/	
	}

}
