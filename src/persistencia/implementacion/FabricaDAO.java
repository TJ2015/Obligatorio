package persistencia.implementacion;

public class FabricaDAO {

	public static InventarioDAO getInventarioDAO() {
		return new InventarioDAO();
	}
	
	public static AvDAO getAvDAO() {
		return new AvDAO();
	}
	
	public static LogDAO getLogDAO() {
		return new LogDAO();
	}
	
	public static MovimientoLogDAO getMovimientoLogDAO() {
		return new MovimientoLogDAO();
	}
	
	public static TipoDAO getTipoDAO() {
		return new TipoDAO();
	}
	
	public static UsuarioDAO getUsuarioDAO() {
		return new UsuarioDAO();
	}
	
}
