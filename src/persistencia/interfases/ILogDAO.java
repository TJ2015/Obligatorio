package persistencia.interfases;

import javax.ejb.Local;

import dominio.log.Log;

@Local
public interface ILogDAO {
	
	public void openTenant(String tenant);
	public void closeTenant(String tenant);
	
	public boolean persistirLog(Log log);
	
	
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
