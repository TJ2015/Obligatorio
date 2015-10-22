package managedbeans;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import dominio.AV;
import dominio.Producto;
import negocio.IControladorAV;
import negocio.IControladorInventario;

@ManagedBean
@ViewScoped
//@SessionScoped
public class CategoriaBean implements Serializable {
	
	@EJB
	IControladorInventario cinv;
	
	@EJB
	IControladorAV cAV;
	
	private String nombre;
	
	private long idAV;
	
	private static final long serialVersionUID = 1L;
	
	
	private String nombreAV;
	
	
	public String getNombreAV() {
		return nombreAV;
	}
	public void setNombreAV(String nombreAV) {
		this.nombreAV = nombreAV;
	}

	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
		
	public IControladorInventario getCinv() {
		return cinv;
	}
	public void setCinv(IControladorInventario cinv) {
		this.cinv = cinv;
	}
	public long getIdAV() {
		return idAV;
	}
	public void setIdAV(long idAV) {
		this.idAV = idAV;
	}
	public CategoriaBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void crearCategoria(){
		try {
			
			HttpSession session = SesionBean.getSession();
			idAV = (long) session.getAttribute("idAV");
			
			if( cinv.crearCategoria(nombre, idAV)) { 	
				
				FacesContext.getCurrentInstance().getExternalContext().dispatch("/index.xhtml");
			} else {
				FacesContext.getCurrentInstance().getExternalContext().dispatch("/error.xhtml");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
	
	
	


