package dominio.datatypes;

public class DataNotificacion {

	private long idNotificacion;
	private String texto;
	private boolean leido;
	
	public DataNotificacion() {
		super();
	}

	public DataNotificacion(long idNotificacion, String texto, boolean leido) {
		super();
		this.idNotificacion = idNotificacion;
		this.texto = texto;
		this.leido = leido;
	}

	public long getIdNotificacion() {
		return idNotificacion;
	}

	public void setIdNotificacion(long idNotificacion) {
		this.idNotificacion = idNotificacion;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public boolean isLeido() {
		return leido;
	}

	public void setLeido(boolean leido) {
		this.leido = leido;
	}
		
}
