package negocio.interfases;

import java.util.List;

import javax.ejb.Local;

import dominio.datatypes.DataLog;

@Local
public interface IControladorLog {
	
	public void crearLog(String usuario, String objetivo, String accion, long idAV);
	public List<DataLog> listaLogUsuario(String usuario);
	public List<DataLog> listaLogAV(long idAV);
	public int cantidadCopiasProducto(String prod);
	public void existeObjetivo(String descripcion);
	public void existeAccion(String descripcion);
	
}
