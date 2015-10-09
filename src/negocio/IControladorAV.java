package negocio;

import javax.ejb.Local;

import dominio.datatype.DataAV;
import dominio.datatype.DataUsuario;

@Local
public interface IControladorAV {
	
	//un usu no puede tener 2AV mismo nombre
	public boolean existeAVusuario(String nombreAV, DataUsuario usuarioCreador);
	public DataAV altaAV(String nombreAV, DataUsuario usuarioCreador);
	public void modificarAV(String nombreAV, String nuevoNombreAV);//datos de estilo si se modifican
	public void eliminarAV(String nombreAV, DataUsuario usuarioCreador);
	
	
}
