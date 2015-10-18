package dominio.datatypes;

	import java.io.Serializable;
	import java.util.ArrayList;
	import java.util.List;

	import javax.persistence.ElementCollection;
	import javax.persistence.Entity;
	import javax.persistence.FetchType;
	import javax.persistence.GeneratedValue;
	import javax.persistence.GenerationType;
	import javax.persistence.Id;
	import javax.persistence.JoinColumn;
	import javax.persistence.JoinTable;
	import javax.persistence.ManyToOne;
	import javax.persistence.OneToMany;


	public class DataCategoria implements Serializable {

		
		private long idCategoria;
		private String nombre;
		private List<DataProducto> productos = new ArrayList<>();
		private static final long serialVersionUID = 1L;
		private DataAV av;
		private long idAV;

		public DataCategoria(String nombre,List<DataProducto> productos,long idAV){
			this.nombre=nombre;
			this.productos=productos;
			this.idAV=idAV;
			
		}

		public long getIdCategoria() {
			return idCategoria;
		}

		public void setIdCategoria(long idCategoria) {
			this.idCategoria = idCategoria;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public List<DataProducto> getProductos() {
			return productos;
		}

		public void setProductos(List<DataProducto> productos) {
			this.productos = productos;
		}

		public DataAV getAv() {
			return av;
		}

		public void setAv(DataAV av) {
			this.av = av;
		}
		
	
}
