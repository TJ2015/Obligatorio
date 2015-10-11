package persistencia;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import dominio.AV;
import dominio.Usuario;
import persistencia.AvDAO;

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
				seRegistro = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return seRegistro;
		}
		
		public AV traerAV(String nombreAV){
			AV av = null;
			try {
				
				av = em.createNamedQuery("Av.buscarPorNombre", AV.class)
				.setParameter("nombreAV", nombreAV)
				.getSingleResult();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return av;
		}
		
		

		@Override
		public boolean buscarAV(String nombreAV) {//TODO existe
			
			boolean existe=false;
			//AV av = null;
			existe=(traerAV(nombreAV)!=null);
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


}
	
	

