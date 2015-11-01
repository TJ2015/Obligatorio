package persistencia.implementacion;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import dominio.Mensaje;
import dominio.TipoUsuario;
import dominio.Usuario;
import persistencia.interfases.IUsuarioDAO;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UsuarioDAO implements IUsuarioDAO {
	
	@javax.persistence.PersistenceContext(unitName="Obligatorio")
	private javax.persistence.EntityManager em;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean altaUsuario(Usuario usuario) {
		boolean seRegistro = false;
		try {
			em.persist(usuario);
			seRegistro = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return seRegistro;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Usuario buscarUsuario(String nick) 
	{
		Usuario usuario = null;
		try {
			//Se busca el usuario en la base 
			//usuario = em.find(Usuario.class, nick);
			usuario = em.createNamedQuery("Usuario.buscarPorNick", Usuario.class)
			.setParameter("nick", nick)
			.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usuario;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean actualizarUsuario(Usuario usuario) {
		boolean seActualizo = false;
		try {
			//Actualiza el en la base de datos.
			em.merge(usuario);
			
			seActualizo = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return seActualizo;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Usuario buscarUsuarioEmail(String email) {
		Usuario usuario = null;
		try {
			//Se busca el usuario en la base 
			//usuario = em.find(Usuario.class, nick);
			usuario = em.createNamedQuery("Usuario.buscarPorEmail", Usuario.class)
			.setParameter("email", email)
			.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usuario;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void eliminarUsuario(Usuario usu) {
		try {
			em.merge(usu);
			em.remove(usu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean persistirMensaje(Mensaje msj) {
		try {
			em.persist(msj);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean actualizarMensaje(Mensaje msj) {
		try {
			em.merge(msj);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean eliminarMensaje(Mensaje msj) {
		try {
			em.merge(msj);
			em.remove(msj);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Mensaje buscarMensaje(long idMensaje) {
		try {
			return em.find(Mensaje.class, idMensaje);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}	
	
	
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
	public TipoUsuario obtenerTipoUsuarioComun() {
		// TODO Auto-generated method stub
		return null;
	}
}
