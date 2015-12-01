package persistencia.interfases;

import java.util.List;

import javax.ejb.Local;

import dominio.log.Accion;
import dominio.log.Log;
import dominio.log.Objetivo;

@Local
public interface ILogDAO {
	
	public void openTenant(String tenant);
	public void closeTenant(String tenant);
	
	public boolean persistirLog(Log log);
	
	public List<Log> getAllLogs();
	public List<Accion> getAllAccion();
	public List<Objetivo> getAllObjetivo();
	
	
	public Accion buscarAccionPorNombre(String nom);
	public Objetivo buscarObjetivoPorNombre(String nom);
	
	/*
	public Log buscarLogPorObjetivoDescripcion(String desc);
	public Log buscarLog(long idLog);
	public Log buscarLogPorUsuario(String nick);
	public Log buscarLogPorObjetivoId(long id);
	public Log buscarLogPorObjetivoIdDescripcion(String desc, long id);
	*/
	
}
