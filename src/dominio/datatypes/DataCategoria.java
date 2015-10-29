package dominio.datatypes;

import java.util.ArrayList;
import java.util.List;

public class DataCategoria {
	private String nombre;
	private List<DataProducto> productos = new ArrayList<>();
	private static final long serialVersionUID = 1L;
	

	
		public DataCategoria(String nombre,List<DataProducto> productos){
			this.nombre=nombre;
			this.productos=productos;
			
			
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

}
