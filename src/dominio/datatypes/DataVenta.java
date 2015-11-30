package dominio.datatypes;

import java.util.Date;

public class DataVenta {


	private long id;
	private DataUsuario dataUsuario;
	private int valor;
	private Date fecha;
	
	public DataVenta() {
	}
	
	public DataVenta(DataUsuario dataUsuario, int valor, Date fecha) {
		this.dataUsuario = dataUsuario;
		this.valor = valor;
		this.fecha = new Date();
		this.fecha = fecha;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public DataUsuario getDataUsuario() {
		return dataUsuario;
	}

	public void setDataUsuario(DataUsuario dataUsuario) {
		this.dataUsuario = dataUsuario;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return "DataVenta [Usuario=" + dataUsuario.toString() + ", valor=" + valor + ", fecha=" + fecha + "]";
	}

	
	
}
