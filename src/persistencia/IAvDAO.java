package persistencia;

import javax.ejb.Local;

import dominio.AV;

@Local
public interface IAvDAO {
	public AV traerAV(String nombreAV);
	public boolean altaAV(AV av);
	public boolean buscarAV(String nombreAV);
	public boolean actualizarAV(AV av);
}
