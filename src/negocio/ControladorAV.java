package negocio;

import javax.ejb.Stateless;
import dominio.AV;
import dominio.Usuario;
import dominio.datatypes.DataAV;
import dominio.datatypes.DataUsuario;


/**
 * Session Bean implementation class ControladorAV
 */
@Stateless
public class ControladorAV implements IControladorAV {

	
	public DataAV altaAV(String nombreAV, DataUsuario usuarioCreador) {
		DataAV av= new DataAV(nombreAV,usuarioCreador);
	    return av;
		
	}
	public boolean existeAVusuario(String nombreAV, DataUsuario usuarioCreador){
		return true;
		
	}
	//datos de estilo si se modifican
	public void modificarAV(String nombreAV, String nuevoNombreAV){
		
		
	}
	public void eliminarAV(String nombreAV, DataUsuario usuarioCreador){
		
	}



}
