package persistencia;

import javax.ejb.Local;

import dominio.AV;

@Local
public interface IAvDAO {
	public AV traerAV(long id);
	public boolean altaAV(AV av);
	public boolean buscarAV(long id);
	public boolean actualizarAV(AV av);
}
