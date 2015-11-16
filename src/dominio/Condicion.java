package dominio;

public class Condicion {
	
	public enum Condicional {
		MAYOR,
		IGUAL,
		MENOR,
		MAYOR_O_IGUAL,
		MENOR_O_IGUAL
	}
	
	private String atributo;
	private Condicional condicional;
	private String valor;
	
	public Condicion(String atributo, Condicional condicional, String valor) {
		super();
		this.atributo = atributo;
		this.condicional = condicional;
		this.valor = valor;
	}

	public Condicion() {
		super();
	}

	public String getAtributo() {
		return atributo;
	}

	public void setAtributo(String atributo) {
		this.atributo = atributo;
	}

	public Condicional getCondicional() {
		return condicional;
	}

	public void setCondicional(Condicional condicional) {
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
		Condicion other = (Condicion) obj;
		if (atributo == null) {
			if (other.atributo != null)
				return false;
		} else if (!atributo.equals(other.atributo))
			return false;
		if (condicional != other.condicional)
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		String cond = "";
		switch(condicional) {
			case MENOR:
				cond = "<";
				break;
			case MAYOR:
				cond = ">";
				break;
			case IGUAL:
				cond = "=";
				break;
			case MENOR_O_IGUAL:
				cond = "<=";
				break;
			case MAYOR_O_IGUAL:
				cond = ">=";
				break;
		}
		return atributo + "[" + cond + "]" + valor + ";";
	}
	
}
