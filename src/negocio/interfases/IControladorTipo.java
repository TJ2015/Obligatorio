package negocio.interfases;

import javax.ejb.Local;

@Local
public interface IControladorTipo 
{
	public boolean crearNuevoTipo(String descripcion);
	
}
