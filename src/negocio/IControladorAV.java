package negocio;

import javax.ejb.Local;

import dominio.datatypes.DataUsuario;
import dominio.AV;
import dominio.Usuario;
import dominio.datatypes.DataAV;

@Local
public interface IControladorAV {
	

	public boolean altaAV(String nombreAV, String usuarioCreador);
	public boolean existeAV(String nombreAV);
	//un usu no puede tener 2AV mismo nombre
	public boolean existeAVusuario(String nombreAV, String usuarioCreador);
	public void modificarAV(String nombreAV, String nuevoNombreAV);//datos de estilo si se modifican
	public void eliminarAV(String nombreAV);
	public void compartirAV(long idAV, String nickUsuario);
	
	
}
