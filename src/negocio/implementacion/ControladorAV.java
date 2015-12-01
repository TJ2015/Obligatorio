package negocio.implementacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dominio.AV;
import dominio.Alerta;
import dominio.Condicion;
import dominio.Nota;
import dominio.Notificacion;
import dominio.Producto;
import dominio.Usuario;
import dominio.datatypes.DataAV;
import dominio.datatypes.DataAlerta;
import dominio.datatypes.DataNota;
import dominio.datatypes.DataNotificacion;
import dominio.log.Accion;
import dominio.log.Log;
import dominio.log.Objetivo;
import exceptions.NoExisteElAV;
import exceptions.NombreDeAVInvalido;
import exceptions.UsuarioNoEncontrado;
import negocio.interfases.IControladorAV;
import persistencia.implementacion.AvDAO;
import persistencia.implementacion.FabricaDAO;
import persistencia.implementacion.InventarioDAO;
import persistencia.implementacion.LogDAO;
import persistencia.interfases.IAvDAO;
import persistencia.interfases.IInventarioDAO;
import persistencia.interfases.ILogDAO;
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
	
	private ILogDAO logDAO = new LogDAO();
	private IAvDAO avDAOTenant = new AvDAO();
	private IInventarioDAO invDAOTenant = new InventarioDAO();

	public long altaAV(String nombreAV, String usuarioCreador) throws NombreDeAVInvalido {

		if (!seguridad.Validacion.esAlfaNumerico(nombreAV))
			throw new exceptions.NombreDeAVInvalido();

		Usuario usu = usuarioDAO.buscarUsuario(usuarioCreador);

		if (usu != null) {
			if (!(this.existeAVusuario(nombreAV, usuarioCreador))) {
				AV av = new AV(nombreAV, usu, "skin-blue");
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
	public void marcarNotificacionComoLeida(long idNoti,long idAV) throws Exception {
		String tenant = getTenant(idAV);
		if (tenant != null) {
			avDAOTenant.open(tenant);
			Notificacion noti = avDAOTenant.buscarNotificacion(idNoti, tenant);
			noti.setLeido(true);
			avDAOTenant.actualizarNotificacion(noti, tenant);
			avDAOTenant.close(tenant);

		} else {
			throw new Exception("No existe un AV con id: " + idAV);
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

	@Override
	public void crearAlerta(String producto, String condicion, long idAV) throws NoExisteElAV {
		String tenant = getTenant(idAV);
		if (tenant != null) {
			Condicion cond = util.Serializador.convertirCondicionAString(condicion);
			
			invDAOTenant.open(tenant);
			Producto prod = invDAOTenant.buscarProducto(producto, tenant);
			
			if( prod != null ) {
				Alerta alerta = new Alerta(cond, prod);
				avDAOTenant.open(tenant);
				avDAOTenant.persistirAlerta(alerta);
				avDAOTenant.close(tenant);
			}
			
			invDAOTenant.close(tenant);
		} else {
			throw new exceptions.NoExisteElAV();
		}
	}

	@Override
	public void eliminarAlerta(long idAlerta, long idAV) throws NoExisteElAV {
		String tenant = getTenant(idAV);
		if (tenant != null) {
			avDAOTenant.open(tenant);
			Alerta alerta = avDAOTenant.buscarAlerta(idAlerta);
			avDAOTenant.eliminarAlerta(alerta);
			avDAOTenant.close(tenant);
		} else {
			throw new exceptions.NoExisteElAV();
		}
	}

	@Override
	public List<DataAlerta> listaDeAlertas(long idAV) throws NoExisteElAV {
		List<DataAlerta> da = new ArrayList<>();
		String tenant = getTenant(idAV);
		if (tenant != null) {
			avDAOTenant.open(tenant);
			List<Alerta> alertas = avDAOTenant.getAllAlerta();
			da = new ArrayList<>();
			
			for( Alerta a : alertas ) {
				da.add(a.getDataAlerta());
			}
			
			avDAOTenant.close(tenant);

		} else {
			throw new exceptions.NoExisteElAV();
		}
		return da;
	}

	@Override
	public List<DataNotificacion> listaNotificacionesNoLeidas(long idAV) throws NoExisteElAV {
		String tenant = getTenant(idAV);
		if (tenant != null) {
			avDAOTenant.open(tenant);
			List<Notificacion> notis = avDAOTenant.buscarNotificacionesNoLeidas();
			List<DataNotificacion> dns = new ArrayList<>();
			for( Notificacion n : notis ) {
				dns.add(n.getDataNotificacion());
			}
			avDAOTenant.close(tenant);
			return dns;			
		} else {
			throw new exceptions.NoExisteElAV();
		}
	}

	@Override
	public void modificarColorAV(long idAV, String color) {
		try {
			AV av = avDAO.traerAV(idAV);
			if (av != null && !color.equals(av.getColor())) {
				av.setColor(color);
				avDAO.actualizarAV(av);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<String> listaDeMovimientosAV(long idAV, int cant) {
		
		List<String> movs = new ArrayList<>();
		
		ILogDAO logDAOMaster = FabricaDAO.getLogDAO();
		
		String tenant = getTenant(idAV);
		if (tenant != null) {
			
			logDAOMaster.openTenant("master");
			logDAO.openTenant(tenant);
			
			
			List<Accion> acciones = logDAOMaster.getAllAccion();
			List<Objetivo> objetivos = logDAOMaster.getAllObjetivo();
			
			List<Log> logs = logDAO.getAllLogs();
			
			Collections.sort(logs, new Comparator<Log>(){
			    public int compare(Log s1, Log s2) {
			        return s1.getFecha().compareTo(s2.getFecha());
			    }
			});
			int cont = 1;
			for( Log log : logs ) {
				Objetivo obj = encontrarObjetivo(objetivos, log.getIdObjetivo());
				Accion acc = encontrarAccion(acciones, log.getIdAccion());
				
				movs.add(log.getFecha().toString() + ": " + log.getUsuario() + " " + 
						acc.getDescripcion().substring(3) +  obj.getDescripcion().substring(3) + " - " + log.getValor());
				
				if( (cant > 0)&&(cont == cant) )
					break;
				
				cont++;
			}
			
			logDAO.closeTenant(tenant);
			logDAOMaster.closeTenant("master");
		}
		
		return movs;
	}
	
	private Accion encontrarAccion(List<Accion> lista, long id) {
		for(Accion a : lista) {
			if( a.getId() == id )
				return a;
		}
		
		return null;
	}
	
	private Objetivo encontrarObjetivo(List<Objetivo> lista, long id) {
		for(Objetivo o : lista) {
			if( o.getId() == id )
				return o;
		}
		
		return null;
	}

}
