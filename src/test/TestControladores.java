package test;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import dominio.datatypes.DataNota;
import dominio.datatypes.DataNotificacion;
import negocio.IControladorAV;
import negocio.IControladorInventario;
import negocio.IControladorUsuario;

@Local
@Stateless
public class TestControladores {
	
	@EJB
	IControladorAV cAV;
	@EJB
	IControladorUsuario cUsu;
	@EJB
	IControladorInventario cInv;
	
	
	public boolean testCrearNota() {
		try {
			String texto = "Nota de prueba";
			cAV.crearNota(texto, "test", 1);
			List<DataNota> notas = cAV.getNotas(1);
			
			for( DataNota dn : notas ) {
				if( dn.getTexto().equals(texto) )
					return true;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean testCrearNotifiacion() {
		String texto = "Notificacion de prueba";
		try {
			cAV.crearNotificacion(texto, 1);
			List<DataNotificacion> notis = cAV.getNotificaciones(1);
			
			for( DataNotificacion dn : notis ) {
				if( dn.getTexto().equals(texto) )
					return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;		
	}

}
