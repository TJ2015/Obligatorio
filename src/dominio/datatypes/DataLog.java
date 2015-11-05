package dominio.datatypes;

import java.io.Serializable;
import java.util.List;

public class DataLog implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private List<DataLogEntry> entradas;

	public DataLog() {
		super();
	}

	public DataLog(long id, List<DataLogEntry> entradas) {
		super();
		this.id = id;
		this.entradas = entradas;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<DataLogEntry> getEntradas() {
		return entradas;
	}

	public void setEntradas(List<DataLogEntry> entradas) {
		this.entradas = entradas;
	}

}
