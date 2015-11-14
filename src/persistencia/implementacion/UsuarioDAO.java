package persistencia.implementacion;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import dominio.Administrador;
import dominio.Mensaje;
import dominio.Usuario;
import persistencia.interfases.IUsuarioDAO;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UsuarioDAO implements IUsuarioDAO {

	@javax.persistence.PersistenceContext(unitName = "Obligatorio")
	private javax.persistence.EntityManager em;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Usuario altaUsuario(Usuario usuario) {
		try {
			em.persist(usuario);
		} catch (Exception e) {
			e.printStackTrace();
			usuario = null;
		}
		return usuario;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Usuario buscarUsuario(String nick) {
		Usuario usuario = null;
		try {
			usuario = em.createNamedQuery("Usuario.buscarPorNick", Usuario.class).setParameter("nick", nick)
					.getSingleResult();
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return usuario;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean actualizarUsuario(Usuario usuario) {
		boolean seActualizo = false;
		try {
			// Actualiza el en la base de datos.
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
			// Se busca el usuario en la base
			// usuario = em.find(Usuario.class, nick);
			usuario = em.createNamedQuery("Usuario.buscarPorEmail", Usuario.class).setParameter("email", email)
					.getSingleResult();
		} catch (Exception e) {
			// e.printStackTrace();
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
			// e.printStackTrace();
		}
		return null;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Administrador buscarAdmin(long nick) {
		try {
			return em.find(Administrador.class, nick);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return null;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Administrador buscarAdmin(String nick) {
		Administrador usuario = null;
		try {
			usuario = em.createNamedQuery("Administrador.buscarPorNick", Administrador.class).setParameter("nick", nick)
					.getSingleResult();
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return usuario;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void persistirAdmin(Administrador admin) {
		try {
			em.persist(admin);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void eliminarAdmin(Administrador admin) {
		try {
			em.merge(admin);
			em.remove(admin);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void actualizarAdmin(Administrador admin) {
		try {
			em.merge(admin);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Usuario buscarUsuarioSocial(String idSocial) {
		Usuario usuario = null;
		try {
			usuario = em.createNamedQuery("Usuario.buscarPorIdSocial", Usuario.class).setParameter("idSocial", idSocial)
					.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usuario;
	}

	@Override
	public List<Usuario> getAllUsuarios() {
		List<Usuario> usuarios = null;
		try {
			usuarios = em.createNamedQuery("Usuario.getAll", Usuario.class).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usuarios;
	}
}
