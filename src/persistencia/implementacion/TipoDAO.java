package persistencia.implementacion;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import dominio.Mensaje;
import dominio.TipoUsuario;
import dominio.Usuario;
import dominio.datatypes.DataMensaje;
import persistencia.interfases.ITipoDAO;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class TipoDAO implements ITipoDAO {

	
	@javax.persistence.PersistenceContext(unitName="Obligatorio")
	private javax.persistence.EntityManager em;
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public TipoUsuario altaTipoUsuario(TipoUsuario tipoUsuario) 
	{
		try {
			em.persist(tipoUsuario);
			System.out.println(tipoUsuario);
		} catch (Exception e) {
			e.printStackTrace();
			tipoUsuario = null;
		}
		return tipoUsuario;
	}

	@Override
	public boolean modificarTipoUsuario(TipoUsuario tipoUsuario) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean eliminarTipoUsuario(TipoUsuario tipoUsuario) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean eliminarTipoUsuario(long idTipoUsuario) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public TipoUsuario obtenerTipoUsuarioParaLogin() 
	{
		TipoUsuario tipoUsuario = null;
		long idUsuarioComun = 1;
		try {
			tipoUsuario = em.createNamedQuery("TipoUsuario.obtenerTipoUsuarioParaLogin", TipoUsuario.class)
			.setParameter("idTipoUsuario", idUsuarioComun)
			.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tipoUsuario;
	}

	@Override
	public TipoUsuario obtenerTipoUsuarioFacbook() {
		// TODO Auto-generated method stub
		return null;
	}

}
