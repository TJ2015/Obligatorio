package managedbeans;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import dominio.ProductoDescripcion;
import negocio.IControladorAV;
import negocio.IControladorInventario;

@ManagedBean
@ViewScoped
public class CategoriaBean implements Serializable {
	
	@EJB
	IControladorInventario cinv;
	//@EJB
	//IControladorAV cAV;
	
	private String nombre;
	private String nombreAV;
	
	
	private static final long serialVersionUID = 1L;
	
	
	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	public String getNombreAV() {
		return nombreAV;
	}
	public void setNombreAV(String nombreAV) {
		this.nombreAV = nombreAV;
	}
	
	public CategoriaBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	/*public void crearCategoria(){
		
		
			try {
				if( cinv.crearCategoria(nombre) ) {
	
					FacesContext.getCurrentInstance().getExternalContext().dispatch("/bienvenida.xhtml");
				} else {
					FacesContext.getCurrentInstance().getExternalContext().dispatch("/error.xhtml");
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/

	
	
	

	public void crearCategoria(){
		try {
			if( cinv.crearCategoria(nombre, nombreAV) ) {

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
	
	
	


