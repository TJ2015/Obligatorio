package negocio.implementacion;

import javax.ejb.EJB;

import dominio.TipoUsuario;
import negocio.interfases.IControladorTipo;
import persistencia.interfases.ITipoDAO;

public class ControladorTipo implements IControladorTipo
{
	@EJB
	private ITipoDAO tipoDao;
	
	@Override
	public boolean crearNuevoTipo(String descripcion) 
	{
		boolean seCrea = false;
		try {
			TipoUsuario tipoUsuario = tipoDao.altaTipoUsuario(new TipoUsuario(descripcion));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return seCrea;
	}
	

}
