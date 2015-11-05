package persistencia.interfases;

import java.util.List;

import javax.ejb.Local;

import dominio.AV;
import dominio.Nota;
import dominio.Notificacion;

@Local
public interface IAvDAO {
	public AV traerAV(long id);

	public boolean altaAV(AV av);

	public boolean buscarAV(long id);

	public boolean actualizarAV(AV av);

	public AV traerAvPorNombre(String nombre);

	public void eliminarAV(String tenant, AV av);

	public void persistirNota(Nota nota, String tenant);

	public Nota buscarNota(long idNota, String tenant);

	public void eliminarNota(long idNota, String tenant);

	public void persistirNotificacion(Notificacion noti, String tenant);

	public Notificacion buscarNotificacion(long idNoti, String tenant);

	public void actualizarNotificacion(Notificacion noti, String tenant);

	public void eliminarNotificacion(long idNoti, String tenant);

	public List<Object> getAllNotas(String tenant);

	public List<Object> getAllNotificaciones(String tenant);

}
