package dominio.datatypes;

public class DataAlerta {

	private DataProducto producto;
	private String atributo;
	private String condicional;
	private String valor;
	private long id;

	public DataAlerta() {
	}

	public DataAlerta(DataProducto producto, String atributo, String condicional, String valor, long id) {
		super();
		this.producto = producto;
		this.atributo = atributo;
		this.condicional = condicional;
		this.valor = valor;
		this.id = id;
	}

	public DataProducto getProducto() {
		return producto;
	}

	public void setProducto(DataProducto producto) {
		this.producto = producto;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAtributo() {
		return atributo;
	}

	public void setAtributo(String atributo) {
		this.atributo = atributo;
	}

	public String getCondicional() {
		return condicional;
	}

	public void setCondicional(String condicional) {
		this.condicional = condicional;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((atributo == null) ? 0 : atributo.hashCode());
		result = prime * result + ((condicional == null) ? 0 : condicional.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((producto == null) ? 0 : producto.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataAlerta other = (DataAlerta) obj;
		if (atributo == null) {
			if (other.atributo != null)
				return false;
		} else if (!atributo.equals(other.atributo))
			return false;
		if (condicional == null) {
			if (other.condicional != null)
				return false;
		} else if (!condicional.equals(other.condicional))
			return false;
		if (id != other.id)
			return false;
		if (producto == null) {
			if (other.producto != null)
				return false;
		} else if (!producto.equals(other.producto))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}

}
