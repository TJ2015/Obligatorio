package persistencia;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.management.Query;
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

		
		
		
		
		


}
	
	

