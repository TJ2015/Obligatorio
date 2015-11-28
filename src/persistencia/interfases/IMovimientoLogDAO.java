package persistencia.interfases;

import javax.ejb.Local;

import dominio.log.Accion;
import dominio.log.Objetivo;

@Local
public interface IMovimientoLogDAO {
	
	public boolean persistirObjetivo(Objetivo objetivo);
	public boolean persistirAccion(Accion accion);
	
	public Accion buscarAccionPorNombre(String nombre);
	public Objetivo buscarObjetivoPorNombre(String nombre);
	

}
