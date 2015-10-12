package managedbeans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;

import negocio.IControladorInventario;


public class ProductoBean implements Serializable {


	@EJB
	IControladorInventario cinv;
	
	private String nombre;
	private String descripcion;
	private double precio;
	private String categoria;
	private String atributosList;
	private static final long serialVersionUID = 1L;
	
	//para descripcion producto
		private long idAV;
	
	
		
	
	public ProductoBean(long idAV) {
			super();
			this.idAV = idAV;
		}


	public ProductoBean(){}
	
	
	
	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public double getPrecio() {
		return precio;
	}


	public void setPrecio(double precio) {
		this.precio = precio;
	}


	public String getCategoria() {
		return categoria;
	}


	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}


	public String getAtributosList() {
		return atributosList;
	}


	public void setAtributosList(String atributosList) {
		this.atributosList = atributosList;
	}


	public long getIdAV() {
		return idAV;
	}


	public void setIdAV(long idAV) {
		this.idAV = idAV;
	}


	public void crearProductoDescripción(){
		
		try {
			if( cinv.crearProductoDescripcion(nombre, descripcion, precio, categoria, atributosList, idAV))
			{
				FacesContext.getCurrentInstance().getExternalContext().dispatch("/bienvenida.xhtml");
			} else {
				FacesContext.getCurrentInstance().getExternalContext().dispatch("/error.xhtml");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

}
