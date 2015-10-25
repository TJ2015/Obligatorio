package negocio;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import dominio.AV;
import dominio.Usuario;
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
	
	public long altaAV(String nombreAV, String usuarioCreador) {
		
		String usuarioCreador1 = FacesContext.getCurrentInstance().
				getExternalContext().getRequestParameterMap().get("hidden1");
		usuarioCreador = usuarioCreador1;
		
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
				
			    return av.getIdAV();
			    
			}
		}
		return -1;
		
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
	public AV traerAvPorNombre(String nombre) {
		//TODO AREGLAR ESTA BURRADA
		AV av = avDAO.traerAvPorNombre(nombre);
		return av;
	}

	@Override
	public void eliminarAV(long idAV) {
		
		AV av = avDAO.traerAV(idAV);
		
		String tenant = av.getUsuarioCreador().getNick() + "_" + av.getNombreAV();
		
		List<Usuario> usuarios = av.getUsuariosCompartidos();
		
		for( Usuario usu : usuarios ) {
			usu.removeAVCompartido(av);
			usuarioDAO.actualizarUsuario(usu);
		}
		
		Usuario usu = av.getUsuarioCreador();		
		usu.removeAV(av);
		
		usuarioDAO.actualizarUsuario(usu);
		avDAO.eliminarAV(tenant);
		
	}
	
}
