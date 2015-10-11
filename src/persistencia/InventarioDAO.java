package persistencia;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import dominio.Categoria;
//import dominio.Usuario;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class InventarioDAO implements IInventarioDAO {
		
	@javax.persistence.PersistenceContext(unitName="Obligatorio")
	private javax.persistence.EntityManager em;
		
		@Override
		@TransactionAttribute(TransactionAttributeType.REQUIRED)
		public void crearCategoria(Categoria cat){
			boolean seCreo = false;
				try {
					//Persiste un categoria a la base de datos
					em.persist(cat);
					seCreo = true;
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}

		
	}


