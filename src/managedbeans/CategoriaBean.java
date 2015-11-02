package managedbeans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import dominio.AV;
import dominio.datatypes.DataCategoria;
import negocio.interfases.IControladorAV;
import negocio.interfases.IControladorInventario;

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
	private AV av;
	private List <DataCategoria> cats=new ArrayList<>();
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
	}

	public void crearCategoria() throws Exception{
		try {
			
			HttpSession session = SesionBean.getSession();
			idAV = (long) session.getAttribute("idAV");

			if( cinv.crearCategoria(nombre, idAV)) { 	
				
				FacesContext.getCurrentInstance().getExternalContext().dispatch("/verListaCategoria.xhtml");
			} else {
				FacesContext.getCurrentInstance().getExternalContext().dispatch("/error.xhtml");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<DataCategoria> getCats() {
		return cats;
	}
	public void setCats(List<DataCategoria> cats) {
		this.cats = cats;
	}
	public void mostrarListaCategoria() {
		try {
			DataCategoria cat1,cat2,cat3=null;
			
			HttpSession session = SesionBean.getSession();
			idAV = (long) session.getAttribute("idAV");
			cat1=new DataCategoria(1, "cat1",null);
			cat2=new DataCategoria(2, "cat2",null);
			cat3=new DataCategoria(3, "cat3",null);
			cats.add(cat1);
			cats.add(cat2);
			cats.add(cat3);	
					FacesContext.getCurrentInstance().getExternalContext().dispatch("/verListaCategoria.xhtml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	
}