package persistencia.interfases;

import javax.ejb.Local;

import dominio.log.Accion;
import dominio.log.Log;
import dominio.log.Objetivo;

@Local
public interface ILogDAO {
	
	public boolean persistirLog(Log log);
	public boolean persistirObjetivo(Objetivo objetivo);
	public boolean persistirAccion(Accion accion);
	
	public Accion buscarAccionPorNombre(String nombre);
	public Objetivo buscarObjetivoPorNombre(String nombre);
	
	public boolean crearLog(Log log, String tenant);
	
	/*
	public Log buscarLogPorObjetivoDescripcion(String desc);
	public Log buscarLog(long idLog);
	public Objetivo buscarObjetivoPorDescripcion(String desc);
	public Accion buscarAccionPorDescripcion(String desc);
	public Log buscarLogPorUsuario(String nick);
	public Log buscarLogPorObjetivoId(long id);
	public Log buscarLogPorObjetivoIdDescripcion(String desc, long id);
	*/
	
}
