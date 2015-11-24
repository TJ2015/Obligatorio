package negocio.implementacion;

import java.util.List;

import dominio.datatypes.DataLog;
import negocio.interfases.IControladorLog;

public class ControladorLog implements IControladorLog {

	@Override
	public void crearLog(String usuario, String objetivo, String accion, long idAV) {
		
	}

	@Override
	public List<DataLog> listaLogUsuario(String usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DataLog> listaLogAV(long idAV) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int cantidadCopiasProducto(String prod) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void existeObjetivo(String descripcion) {
		// TODO Auto-generated method stub

	}

	@Override
	public void existeAccion(String descripcion) {
		// TODO Auto-generated method stub

	}

}
