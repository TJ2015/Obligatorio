package negocio.implementacion;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dominio.datatypes.log.DataLog;
import dominio.log.Accion;
import dominio.log.Log;
import dominio.log.Objetivo;
import negocio.interfases.IControladorLog;
import persistencia.implementacion.LogDAO;
import persistencia.interfases.ILogDAO;
import persistencia.interfases.IMovimientoLogDAO;

@Stateless
public class ControladorLog implements IControladorLog {

	@EJB
	private IMovimientoLogDAO movimeintoDAO;
	
	private ILogDAO logDAO = new LogDAO();
	
	
	
	@Override
	public boolean agregarAccion(String nombre, String descripcion){
		boolean agrego = false;
		try {
			if(!existeAccion(nombre)){
				movimeintoDAO.persistirAccion(new Accion(nombre, descripcion));
				agrego = true;
			}
		} catch (Exception e) {
			
		}
		return agrego;
	}
	
	public boolean existeAccion(String nombre){
		return (movimeintoDAO.buscarAccionPorNombre(nombre) != null);
	}
	
	public long obtenerIdAccion(String nombre){
		long idAccion = 0;
		try {
			Accion accion = movimeintoDAO.buscarAccionPorNombre(nombre);
			idAccion = accion.getId();
		} catch (Exception e) {
			System.out.println("No existe el Accion " + nombre);
		}
		return idAccion;
	}
	

	@Override
	public boolean agregarObjetivo(String nombre, String descripcion) {
		boolean agrego = false;
		try {
			if(!existeObjetivo(nombre)){
				movimeintoDAO.persistirObjetivo(new Objetivo(nombre, descripcion));
				agrego = true;
			}
		} catch (Exception e) {
			
		}
		return agrego;
	}
	
	public boolean existeObjetivo(String nombre){
		return (movimeintoDAO.buscarObjetivoPorNombre(nombre.toUpperCase()) != null);
	}
	
	public long obtenerIdObjetivo(String nombre){
		long idObjetivo = 0;
		try {
			Objetivo objetivo = movimeintoDAO.buscarObjetivoPorNombre(nombre);
			idObjetivo = objetivo.getId();
		} catch (Exception e) {
			System.out.println("No existe el objetivo " + nombre);
		}
		return idObjetivo;
	}

	@Override
	public boolean agregarLog(DataLog dataLog, String tenant) {
		boolean agrego = false;
		try {
			dataLog.setIdAccion(obtenerIdAccion(dataLog.getNombreAccion()));
			dataLog.setIdObjetivo(obtenerIdObjetivo(dataLog.getNombreObjetivo()));
			Log log = new Log(dataLog);
			logDAO.openTenant(tenant);
			logDAO.persistirLog(log);
			logDAO.closeTenant(tenant);
			agrego = true;
		} catch (Exception e) {
			System.out.println("No se agreg el LOG");
		}
		return agrego;
	}
	
	

}
