package negocio.implementacion;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dominio.datatypes.log.DataLog;
import dominio.log.Accion;
import dominio.log.Objetivo;
import negocio.interfases.IControladorLog;
import persistencia.interfases.ILogDAO;

@Stateless
public class ControladorLog implements IControladorLog {

	
	@EJB
	private ILogDAO logDao;
	
	
	
	@Override
	public boolean agregarAccion(String nombre, String descripcion){
		boolean agrego = false;
		try {
			if(!existeAccion(nombre)){
				logDao.persistirAccion(new Accion(nombre, descripcion));
				agrego = true;
			}
		} catch (Exception e) {
			
		}
		return agrego;
	}
	
	public boolean existeAccion(String nombre){
		return (logDao.buscarAccionPorNombre(nombre) != null);
	}
	

	@Override
	public boolean agregarObjetivo(String nombre, String descripcion) {
		boolean agrego = false;
		try {
			if(!existeObjetivo(descripcion)){
				logDao.persistirObjetivo(new Objetivo(nombre, descripcion));
				agrego = true;
			}
		} catch (Exception e) {
			
		}
		return agrego;
	}
	
	public boolean existeObjetivo(String nombre){
		return (logDao.buscarObjetivoPorNombre(nombre.toUpperCase()) != null);
	}

	@Override
	public boolean agregarLog(DataLog dataLog, String tenant) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
