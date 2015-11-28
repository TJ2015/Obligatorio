package persistencia.implementacion;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import dominio.log.Accion;
import dominio.log.Objetivo;
import persistencia.interfases.IMovimientoLogDAO;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MovimientoLogDAO implements IMovimientoLogDAO{
	
	@javax.persistence.PersistenceContext(unitName="Obligatorio")
	private javax.persistence.EntityManager em;
	
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean persistirObjetivo(Objetivo objetivo) {
		boolean persistio = false;
		try {
			em.persist(objetivo);
			persistio = true;
		} catch (Exception e) {
			System.out.println("Error al guardar el objetivo");
		}
		return persistio;
	}



	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean persistirAccion(Accion accion) {
		boolean persistio = false;
		try {
			em.persist(accion);
			persistio = true;
		} catch (Exception e) {
			System.out.println("Error al guardar el accion");
		}
		return persistio;
	}


	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Accion buscarAccionPorNombre(String nombre) {
		Accion accion = null;
		try {
			accion = (Accion)em.createNamedQuery("Accion.buscarPorNombre", Accion.class)
			.setParameter("nombre", nombre)
			.getSingleResult();
		} catch (Exception e) {
			System.out.println("No existe la Accion " + nombre);
		}
		return accion;
	}



	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Objetivo buscarObjetivoPorNombre(String nombre) {
		Objetivo objetivo = null;
		try {
			objetivo = (Objetivo)em.createNamedQuery("Objetivo.buscarPorNombre", Objetivo.class)
			.setParameter("nombre", nombre)
			.getSingleResult();
		} catch (Exception e) {
			System.out.println("No existe el Objetivo " + nombre);
		}
		return objetivo;
	}


}
