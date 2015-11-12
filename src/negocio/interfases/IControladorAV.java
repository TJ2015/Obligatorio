package negocio.interfases;

import java.util.List;

import javax.ejb.Local;

import dominio.datatypes.DataAV;
import dominio.datatypes.DataLogEntry;
import dominio.datatypes.DataNota;
import dominio.datatypes.DataNotificacion;
import exceptions.NoExisteElAV;
import exceptions.NombreDeAVInvalido;
import exceptions.UsuarioNoEncontrado;

@Local
public interface IControladorAV {

	public long altaAV(String nombreAV, String usuarioCreador) throws NombreDeAVInvalido;

	public boolean existeAV(long idAV);

	public boolean existeAVusuario(String nombreAV, String usuarioCreador);

	public void eliminarAV(long idAV) throws Exception;

	public void compartirAV(long idAV, String nickUsuario);

	public void descompartirAV(long idAV, String nickUsuario) throws NoExisteElAV;

	public DataAV traerAVPorNombre(String nombre, String nick) throws UsuarioNoEncontrado;

	public DataAV traerAV(long idAV) throws NoExisteElAV;

	public void crearNota(String texto, String usuario, long idAV) throws Exception;

	public void eliminarNota(long idAV, long idNota) throws Exception;

	public List<DataNota> getNotas(long idAV) throws Exception;

	public void crearNotificacion(String texto, long idAV) throws Exception;

	public void modificarNotificacion(long idAV, long idNoti, String texto, boolean leido) throws Exception;

	public void eliminarNotificacion(long idAV, long idNoti) throws Exception;

	public List<DataNotificacion> getNotificaciones(long idAV) throws Exception;

}
