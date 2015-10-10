package negocio;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import dominio.AV;
import dominio.Usuario;
import dominio.datatypes.DataAV;
import dominio.datatypes.DataUsuario;
import persistencia.IAvDAO;
import persistencia.IUsuarioDAO;


/**
 * Session Bean implementation class ControladorAV
 */
@Stateless
public class ControladorAV implements IControladorAV {

	@EJB
	private IUsuarioDAO usuarioDAO;
	private IAvDAO avDAO;
	
	
	public boolean altaAV(String nombreAV, String usuarioCreador) {
		//String nombreUsu=usuarioCreador.getNombre();
		if (!(this.existeAVusuario(nombreAV, usuarioCreador))){
			Usuario usu=usuarioDAO.buscarUsuario(usuarioCreador);
			AV av= new AV(nombreAV,usu);
		    return true;
		}
		return false;
	}
	
	//El usuario ya tiene un Av con ese nombre?
	public boolean existeAVusuario(String nombreAV, String usuarioCreador){
		boolean existe = false;
		try {
			//Si nos devuelve un av, existe es igual a true 
			AV av=this.avDAO.traerAV(nombreAV);
			String usuCreadorAV=av.getUsuarioCreador().getNombre();
			existe=(usuCreadorAV ==usuarioCreador);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return existe;
		
	}
	public boolean existeAV(String nombreAV){
		boolean existe = false;
		try {
			existe=(this.avDAO.buscarAV(nombreAV));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return existe;
	}

	//datos de estilo si se modifican
	public void modificarAV(String nombreAV, String nuevoNombreAV){
		//TERMINARRRRR
		
	}
	public void eliminarAV(String nombreAV){
		AV av=null;
		av=this.avDAO.traerAV(nombreAV);
		//TERMINARRRRRR
		
	}


	


}
