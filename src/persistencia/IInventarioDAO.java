package persistencia;

import javax.ejb.Local;

import dominio.Categoria;

@Local
public interface IInventarioDAO {

	public void crearCategoria(Categoria cat);

}
