package persistencia.interfases;

import javax.ejb.Local;

import dominio.TipoUsuario;

@Local
public interface ITipoDAO {
	
	public TipoUsuario altaTipoUsuario(TipoUsuario tipoUsuario);
	public boolean modificarTipoUsuario(TipoUsuario tipoUsuario);
	public boolean eliminarTipoUsuario(TipoUsuario tipoUsuario);
	public boolean eliminarTipoUsuario(long idTipoUsuario);
	public TipoUsuario obtenerTipoUsuarioParaLogin();
	public TipoUsuario obtenerTipoUsuarioSocial(String redSocial);
	
}
