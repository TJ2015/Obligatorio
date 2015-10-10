package negocio;

import java.util.List;

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
	@EJB
	private IAvDAO avDAO;
	

	//TODO ARREGLAR JOIN
	public boolean altaAV(String nombreAV, String usuarioCreador) {
		//String nombreUsu=usuarioCreador.getNombre();
		Usuario usu=usuarioDAO.buscarUsuario(usuarioCreador);
		if (usu!=null){
			if (!(this.existeAVusuario(nombreAV, usuarioCreador))){
				AV av= new AV(nombreAV,usu);
				usu.addAV(av);
				avDAO.altaAV(av);
				usuarioDAO.actualizarUsuario(usu);	
			    return true;
			}
		}
		return false;
		
	}
	
	//El usuario ya tiene un Av con ese nombre?
	public boolean existeAVusuario(String nombreAV, String usuarioCreador){
		boolean existe = false;
		
			Usuario usu=usuarioDAO.buscarUsuario(usuarioCreador);
			if (usu!=null){
				List <AV> listaav=usu.getAVs();
				for(AV i:listaav){
					if(i.getNombreAV()==nombreAV){
						return true;
					}
				}
			}	
			return false;
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
