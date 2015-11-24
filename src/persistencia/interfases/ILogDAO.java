package persistencia.interfases;

import javax.ejb.Local;

import dominio.log.Accion;
import dominio.log.Log;
import dominio.log.Objetivo;

@Local
public interface ILogDAO {
	
	public void persistirLog(Log log);
	public void persistirObjetivo(Objetivo obj);
	public void persistirAccion(Accion accion);
	public Log buscarLog(long idLog);
	public Objetivo buscarObjetivoPorDescripcion(String desc);
	public Accion buscarAccionPorDescripcion(String desc);
	public Log buscarLogPorUsuario(String nick);
	public Log buscarLogPorObjetivoDescripcion(String desc);
	public Log buscarLogPorAccionDescripcion(String desc);
	public Log buscarLogPorObjetivoId(long id);
	public Log buscarLogPorObjetivoIdDescripcion(String desc, long id);
	
}
