package negocio;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import dominio.AV;
import dominio.Usuario;
import dominio.datatypes.DataAV;
import dominio.datatypes.DataCategoria;
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
				//Long idUsuario = usu.getIdUsuario();
				List <AV> listaav=usu.getAVs();//nuevo
				listaav.add(av);//nuevo
				av.setUsuarioCreador(usu);//usu
				usu.setAVs(listaav);
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
			
		
		
	
	public boolean existeAV(long idAV){
		boolean existe = false;
		try {
			existe=(this.avDAO.buscarAV(idAV));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return existe;
	}

	//datos de estilo si se modifican
	public void modificarAV(String nombreAV, String nuevoNombreAV){
		//TERMINARRRRR
		
	}
	public void eliminarAV(long idAV){
		AV av=null;
		//av=this.avDAO.traerAV(nombreAV);
		//TERMINARRRRRR
		
	}


	public void mostrarAVxUsuario(String usuario, String AV){
		
		
	}
	public void compartirAV(long idAV, String nickname){
		Usuario usu=usuarioDAO.buscarUsuario(nickname);
		AV av=avDAO.traerAV(idAV);
		
		List <Usuario> usuComp=av.getUsuariosCompartidos();
		usuComp.add(usu);
		av.setUsuariosCompartidos(usuComp);
		
		List <AV> avsComp=usu.getAVcompartidos();
		avsComp.add(av);
		usu.setAVcompartidos(avsComp);
		
		usuarioDAO.actualizarUsuario(usu);
		avDAO.actualizarAV(av);
		
	}

	@Override
	public List <DataCategoria> mostrarListaCat(long idAv) {
		AV av= avDAO.traerAV(idAv);		
		return av.getDataAV().getCategorias();

	}	


}
