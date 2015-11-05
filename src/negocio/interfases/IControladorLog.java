package negocio.interfases;

import javax.ejb.Local;

import dominio.datatypes.DataLog;

@Local
public interface IControladorLog {
	
	public void registrarEnLog(String idAV, String texto);
	public DataLog getLogAV(String idAV);
	
}
