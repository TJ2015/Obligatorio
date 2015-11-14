package negocio.implementacion;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dominio.AV;
import dominio.Nota;
import dominio.Notificacion;
import dominio.Usuario;
import dominio.datatypes.DataAV;
import dominio.datatypes.DataLogEntry;
import dominio.datatypes.DataNota;
import dominio.datatypes.DataNotificacion;
import exceptions.NoExisteElAV;
import exceptions.NombreDeAVInvalido;
import exceptions.UsuarioNoEncontrado;
import negocio.interfases.IControladorAV;
import persistencia.implementacion.AvDAO;
import persistencia.interfases.IAvDAO;
import persistencia.interfases.IUsuarioDAO;

/**
 * Session Bean implementation class ControladorAV
 */
@Stateless
public class ControladorAV implements IControladorAV {

	@EJB
	private IUsuarioDAO usuarioDAO;
	@EJB
	private IAvDAO avDAO;

	private IAvDAO avDAOTenant = new AvDAO();

	public long altaAV(String nombreAV, String usuarioCreador) throws NombreDeAVInvalido {

		if (!seguridad.Validacion.esAlfaNumerico(nombreAV))
			throw new exceptions.NombreDeAVInvalido();

		Usuario usu = usuarioDAO.buscarUsuario(usuarioCreador);

		if (usu != null) {
			if (!(this.existeAVusuario(nombreAV, usuarioCreador))) {
				AV av = new AV(nombreAV, usu);
				av.setUsuarioCreador(usu);// usu
				avDAO.altaAV(av);
				usu.addAV(av);
				usuarioDAO.actualizarUsuario(usu);

				return av.getIdAV();
			}
		}
		return -1;

	}

	public boolean existeAVusuario(String nombreAV, String usuarioCreador) {
		Usuario usu = usuarioDAO.buscarUsuario(usuarioCreador);
		if (usu != null) {
			List<AV> listaav = usu.getAVs();
			for (AV i : listaav) {
				if (i.getNombreAV() == nombreAV) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean existeAV(long idAV) {
		boolean existe = false;
		try {
			existe = (this.avDAO.buscarAV(idAV));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return existe;
	}

	public void mostrarAVxUsuario(String usuario, String AV) {
	}

	public void compartirAV(long idAV, String nickname) {
		Usuario usu = usuarioDAO.buscarUsuario(nickname);
		AV av = avDAO.traerAV(idAV);

		List<Usuario> usuComp = av.getUsuariosCompartidos();
		usuComp.add(usu);
		av.setUsuariosCompartidos(usuComp);

		List<AV> avsComp = usu.getAVcompartidos();
		avsComp.add(av);
		usu.setAVcompartidos(avsComp);

		usuarioDAO.actualizarUsuario(usu);
		avDAO.actualizarAV(av);

	}

	@Override
	public void eliminarAV(long idAV) throws Exception {

		AV av = avDAO.traerAV(idAV);
		String tenant = getTenant(idAV);
		if (tenant != null) {
			List<Usuario> usuarios = av.getUsuariosCompartidos();

			for (Usuario usu : usuarios) {
				usu.removeAVCompartido(av);
				usuarioDAO.actualizarUsuario(usu);
			}

			Usuario usu = av.getUsuarioCreador();
			usu.removeAV(av);
			usuarioDAO.actualizarUsuario(usu);
			avDAO.eliminarAV(tenant, av);
		} else {
			throw new Exception("No existe un AV con id: " + idAV);
		}

	}

	@Override
	public void crearNota(String texto, String usuario, long idAV) throws Exception {
		if (idAV > 0) {
			String tenant = getTenant(idAV);
			if (tenant != null) {
				avDAOTenant.open(tenant);
				Nota nota = new Nota(texto, usuario);
				avDAOTenant.persistirNota(nota, tenant);
				avDAOTenant.close(tenant);
			} else {
				throw new Exception("No existe un AV con id: " + idAV);
			}
		} else {
			throw new Exception("Valor de idAV invalido: " + idAV);
		}
	}

	@Override
	public void eliminarNota(long idAV, long idNota) throws Exception {
		if (idAV > 0) {
			String tenant = getTenant(idAV);
			if (tenant != null) {
				avDAOTenant.open(tenant);
				avDAOTenant.eliminarNota(idNota, tenant);
				avDAOTenant.close(tenant);
			} else {
				throw new Exception("No existe un AV con id: " + idAV);
			}
		} else {
			throw new Exception("Valor de idAV invalido: " + idAV);
		}
	}

	@Override
	public void crearNotificacion(String texto, long idAV) throws Exception {
		if (idAV > 0) {
			String tenant = getTenant(idAV);
			if (tenant != null) {
				avDAOTenant.open(tenant);
				Notificacion noti = new Notificacion(texto);
				avDAOTenant.persistirNotificacion(noti, tenant);
				avDAOTenant.close(tenant);
			} else {
				throw new Exception("No existe un AV con id: " + idAV);
			}
		} else {
			throw new Exception("Valor de idAV invalido: " + idAV);
		}
	}

	@Override
	public void modificarNotificacion(long idAV, long idNoti, String texto, boolean leido) throws Exception {
		if (idAV > 0) {
			String tenant = getTenant(idAV);
			if (tenant != null) {
				avDAOTenant.open(tenant);
				Notificacion noti = avDAOTenant.buscarNotificacion(idNoti, tenant);
				noti.setTexto(texto);
				noti.setLeido(leido);
				avDAOTenant.actualizarNotificacion(noti, tenant);
				avDAOTenant.close(tenant);
			} else {
				throw new Exception("No existe un AV con id: " + idAV);
			}
		} else {
			throw new Exception("Valor de idAV invalido: " + idAV);
		}
	}

	@Override
	public void eliminarNotificacion(long idAV, long idNoti) throws Exception {
		if (idAV > 0) {
			String tenant = getTenant(idAV);
			if (tenant != null) {
				avDAOTenant.open(tenant);
				avDAOTenant.eliminarNotificacion(idNoti, tenant);
				avDAOTenant.close(tenant);
			} else {
				throw new Exception("No existe un AV con id: " + idAV);
			}
		} else {
			throw new Exception("Valor de idAV invalido: " + idAV);
		}
	}

	private String getTenant(long idAV) {
		AV av = avDAO.traerAV(idAV);
		if (av != null) {
			return av.getUsuarioCreador().getNick() + "_" + av.getNombreAV();
		} else {
			return null;
		}
	}

	@Override
	public List<DataNota> getNotas(long idAV) throws Exception {
		if (idAV > 0) {
			String tenant = getTenant(idAV);
			if (tenant != null) {
				avDAOTenant.open(tenant);
				List<Object> notas = avDAOTenant.getAllNotas(tenant);
				List<DataNota> datas = new ArrayList<>();

				for (Object o : notas) {
					Nota n = (Nota) o;
					datas.add(n.getDataNota());
				}
				avDAOTenant.close(tenant);
				return datas;
			} else {
				throw new Exception("No existe un AV con id: " + idAV);
			}
		} else {
			throw new Exception("Valor de idAV invalido: " + idAV);
		}
	}

	@Override
	public List<DataNotificacion> getNotificaciones(long idAV) throws Exception {
		if (idAV > 0) {
			String tenant = getTenant(idAV);
			if (tenant != null) {
				avDAOTenant.open(tenant);
				List<Object> notis = avDAOTenant.getAllNotificaciones(tenant);
				List<DataNotificacion> datas = new ArrayList<>();

				for (Object o : notis) {
					Notificacion n = (Notificacion) o;
					datas.add(n.getDataNotificacion());
				}
				avDAOTenant.close(tenant);
				return datas;
			} else {
				throw new Exception("No existe un AV con id: " + idAV);
			}
		} else {
			throw new Exception("Valor de idAV invalido: " + idAV);
		}
	}

	@Override
	public DataAV traerAVPorNombre(String nombre, String nick) throws UsuarioNoEncontrado {
		Usuario usu = usuarioDAO.buscarUsuario(nick);

		if (usu == null)
			throw new exceptions.UsuarioNoEncontrado();

		for (AV av : usu.getAVs()) {
			if (av.getNombreAV().equals(nombre)) {
				return av.getDataAV();
			}
		}

		return null;
	}

	@Override
	public DataAV traerAV(long idAV) throws NoExisteElAV {
		AV av = avDAO.traerAV(idAV);

		if (av == null)
			throw new exceptions.NoExisteElAV();

		return av.getDataAV();
	}

	@Override
	public void descompartirAV(long idAV, String nickUsuario) throws NoExisteElAV {

		AV av = avDAO.traerAV(idAV);

		if (av != null) {
			List<Usuario> compas = av.getUsuariosCompartidos();
			Usuario compa = null;
			for (Usuario usu : compas) {
				if (usu.getNick().equals(nickUsuario)) {
					compa = usu;
					break;
				}
			}
			av.removeUsuarioCompartido(compa);
			compa.removeAVCompartido(av);

			avDAO.actualizarAV(av);
			usuarioDAO.actualizarUsuario(compa);

		} else {
			throw new exceptions.NoExisteElAV();
		}
	}

}
