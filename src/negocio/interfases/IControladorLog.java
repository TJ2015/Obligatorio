package negocio.interfases;

import javax.ejb.Local;

import dominio.datatypes.log.DataLog;

@Local
public interface IControladorLog {
	
	public String CREAR = "CREAR";
	public String MODIFICAR = "MODIFICAR";
	public String MODIFICAR_STOCK = "MODIFICAR_STOCK";
	public String ELIMINAR = "ELIMINAR";
	public String ELIMINAR_EN_LISTA = "ELIMINAR_EN_LISTA";
	public String MOVER = "MOVER";
	public String COMPRAR = "COMPRAR";

	public String PRODUCTO = "PRODUCTO";
	public String CATEGORIA = "CATEGORIA";

	public boolean agregarAccion(String nombre, String descripcion);
	public boolean agregarObjetivo(String nombre, String descripcion);
	
	public boolean agregarLog(DataLog dataLog, String tenant);
	/*
	public void crearLog(String usuario, String objetivo, String accion, long idAV);
	public List<DataLog> listaLogUsuario(String usuario);
	public List<DataLog> listaLogAV(long idAV);
	public int cantidadCopiasProducto(String prod);
	public void existeObjetivo(String descripcion);
	public void existeAccion(String descripcion);
	*/
}
