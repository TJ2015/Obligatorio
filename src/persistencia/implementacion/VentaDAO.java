package persistencia.implementacion;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import dominio.Venta;
import persistencia.interfases.IVentaDAO;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class VentaDAO implements IVentaDAO{

	
	@javax.persistence.PersistenceContext(unitName="Obligatorio")
	private javax.persistence.EntityManager em;
	
	
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean persistirVenta(Venta venta) {
		boolean persistio = false;
		try {
			em.persist(venta);
			persistio = true;
		} catch (Exception e) {

		}
		return persistio;
	}
	
	@Override
	public List<Venta> buscarListaVenta() {
		List<Venta> lVenta = null;
		try {
			lVenta = em.createNamedQuery("Venta.getAll", Venta.class).getResultList();
		} catch (Exception e) {
		}
		return lVenta;
	}

}
