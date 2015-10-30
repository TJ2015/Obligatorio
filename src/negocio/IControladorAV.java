package negocio;

import java.util.List;

import javax.ejb.Local;

import dominio.AV;
import dominio.datatypes.DataProductoAComprar;
import exceptions.NoExisteElAV;
import dominio.datatypes.DataNota;
import dominio.datatypes.DataNotificacion;

@Local
public interface IControladorAV {
	

	public long altaAV(String nombreAV, String usuarioCreador);
	public boolean existeAV(long idAV);
	public boolean existeAVusuario(String nombreAV, String usuarioCreador);
	public void eliminarAV(long idAV);
	public void compartirAV(long idAV, String nickUsuario);	
	public AV traerAvPorNombre(String nombre);
	public AV traerAV(long idAV); 
	public void crearNota(String texto, String usuario, long idAV) throws Exception;
	public void eliminarNota(long idAV, long idNota) throws Exception;
	public List<DataNota> getNotas(long idAV) throws Exception;
	
	public void crearNotificacion(String texto, long idAV) throws Exception;
	public void modificarNotificacion(long idAV, long idNoti, String texto, boolean leido) throws Exception;
	public void eliminarNotificacion(long idAV, long idNoti) throws Exception;
	public List<DataNotificacion> getNotificaciones(long idAV) throws Exception;
	
}
